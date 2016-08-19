package me.khalit.qDrop.databases;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.implementation.interfaces.Database;
import org.bukkit.Bukkit;

import java.sql.*;

/**
 * Created by xWildfire on 02.06.2016.
 */
public class MySQL implements Database {

    private final String user, pass;

    public String driver;
    public String database_url;

    public Connection connection = null;

    public MySQL(String host, int port, String user, String pass, String name) {
        this.user = user;
        this.pass = pass;
        // setting variables
        driver = "com.mysql.jdbc.Driver";
        database_url = "jdbc:mysql://" + host + ":" + port + "/" + name;

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

    public Connection getConnection() {
        // connecting
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(database_url, user, pass);
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
