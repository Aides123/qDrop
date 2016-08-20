package me.khalit.qDrop;

import me.khalit.qDrop.commands.DropCommand;
import me.khalit.qDrop.commands.LevelCommand;
import me.khalit.qDrop.commands.TurboDropCommand;
import me.khalit.qDrop.configuration.Configuration;
import me.khalit.qDrop.configuration.ConfigurationSectionReader;
import me.khalit.qDrop.databases.MySQL;
import me.khalit.qDrop.databases.SQLite;
import me.khalit.qDrop.gui.GUICreator;
import me.khalit.qDrop.gui.GUIType;
import me.khalit.qDrop.implementation.interfaces.Database;
import me.khalit.qDrop.implementation.interfaces.User;
import me.khalit.qDrop.listeners.*;
import me.khalit.qDrop.listeners.async.AsyncPlayerChatListener;
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
        pm.registerEvents(new AsyncPlayerChatListener(), this);

        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new BlockBreakListener(), this);
        pm.registerEvents(new EntityExplodeListener(), this);
        pm.registerEvents(new LevelChangeListener(), this);
        pm.registerEvents(new LevelPointChangeListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
        LOG.info(PREFIX + "Registering commands...");
        getCommand("turbodrop").setExecutor(new TurboDropCommand());
        getCommand("drop").setExecutor(new DropCommand());
        getCommand("level").setExecutor(new LevelCommand());
        LOG.info(PREFIX + "Loading drops...");
        ConfigurationSectionReader reader = new ConfigurationSectionReader("drops");
        int amount = reader.loadToAssembly();
        LOG.info(PREFIX + "Loaded " + amount + " drop(s)!");
    }

    public void onDisable() {
        for (User u : UserManager.getUsers()) {
            u.update();
        }
    }

}
