package me.khalit.qDrop.databases;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.implementation.interfaces.Database;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * Created by KHaliT on 19.08.2016.
 */
public class SQLite implements Database {

    private File file = new File(Main.getInstance().getDataFolder(), "sqlite.db");

    public String driver;
    public String database_url;

    public Connection connection = null;

    public SQLite() {
        // setting variables
        driver = "org.sqlite.JDBC";
        if (!Main.getInstance().getDataFolder().exists()) {
            Main.getInstance().getDataFolder().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        database_url = "jdbc:sqlite:" + file.getAbsolutePath();

        // searching for driver
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeSelect(PreparedStatement stat) {
        ResultSet result = null;
        try {
            result = stat.executeQuery();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void executeUpdate(PreparedStatement stat) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            try {
                stat.execute();
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Connection getConnection() {
        // connecting
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(database_url);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return connection;
    }

    @Override
    public void createTables() throws SQLException {
        String query =
                "CREATE TABLE IF NOT EXISTS users (" +
                        "uuid VARCHAR(36) NOT NULL," +
                        "name TINYTEXT NOT NULL," +
                        "levelPoints INT NOT NULL," +
                        "level INT NOT NULL," +
                        "dropMultipiler DECIMAL(9,2))";
        PreparedStatement stat = getConnection().prepareStatement(query);
        stat.execute(); // synchronized
        stat.close();
    }
}
