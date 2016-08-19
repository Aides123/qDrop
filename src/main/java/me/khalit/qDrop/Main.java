package me.khalit.qDrop;

import me.khalit.qDrop.configuration.Configuration;
import me.khalit.qDrop.configuration.ConfigurationSectionReader;
import me.khalit.qDrop.databases.MySQL;
import me.khalit.qDrop.databases.SQLite;
import me.khalit.qDrop.implementation.interfaces.Database;
import me.khalit.qDrop.listeners.BlockBreakListener;
import me.khalit.qDrop.listeners.PlayerJoinListener;
import me.khalit.qDrop.utils.managers.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by KHaliT on 18.08.2016.
 */
public class Main extends JavaPlugin {

    private static final String PREFIX = "[qDrop] ";
    private static final Logger LOG = Bukkit.getLogger();

    private static Database database;
    private static Configuration drops;
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static Configuration getDrops() {
        return drops;
    }

    public static Database getSQL() {
        return database;
    }

    @Override
    public void onLoad() {
        LOG.info(PREFIX + "Instantiating...");
        instance = this;
        drops = new Configuration("drops.yml");
    }

    @Override
    public void onEnable() {
        LOG.info(PREFIX + "Loading resources...");
        LOG.info(PREFIX + "Saving default configuration and reloading it...");
        saveDefaultConfig();
        drops.saveDefault();
        drops.reload();
        LOG.info(PREFIX + "Connecting to database and creating tables...");
        if (!getConfig().getBoolean("sqlite", true)) {
            database = new MySQL(getConfig().getString("mysql.host"),
                    getConfig().getInt("mysql.port"),
                    getConfig().getString("mysql.user"),
                    getConfig().getString("mysql.pass"),
                    getConfig().getString("mysql.name"));
        } else {
            database = new SQLite();
        }
        try {
            database.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOG.info(PREFIX + "Loading online users...");
        for (Player p : Bukkit.getOnlinePlayers()) {
            UserManager.loadUser(p);
        }
        LOG.info(PREFIX + "Registering listeners...");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new BlockBreakListener(), this);
        LOG.info(PREFIX + "Loading drops...");
        ConfigurationSectionReader reader = new ConfigurationSectionReader("drops");
        int amount = reader.loadToAssembly();
        LOG.info(PREFIX + "Loaded " + amount + " drop(s)!");
    }

}
