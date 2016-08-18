package me.khalit.qDrop;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by KHaliT on 18.08.2016.
 */
public class Main extends JavaPlugin {

    private static final String PREFIX = "[qDrop] ";
    private static final Logger LOG = Bukkit.getLogger();

    private static Main instance;

    public static Main getInstance() {
        LOG.info(PREFIX + "Instantiating...");
        return instance;
    }

    @Override
    public void onEnable() {
        LOG.info(PREFIX + "Loading resources...");
    }

}
