package me.khalit.qDrop;

import me.khalit.qDrop.configuration.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by KHaliT on 18.08.2016.
 */
public class Main extends JavaPlugin {

    private static final String PREFIX = "[qDrop] ";
    private static final Logger LOG = Bukkit.getLogger();

    private static Configuration drops;
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static Configuration getDrops() {
        return drops;
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
    }

}
