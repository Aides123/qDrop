package me.khalit.qDrop.listeners;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.events.LevelChangeEvent;
import me.khalit.qDrop.implementation.interfaces.User;
import me.khalit.qDrop.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

/**
 * Created by KHaliT on 20.08.2016.
 */
public class LevelChangeListener implements Listener {

    @EventHandler
    public void onLevelChange(LevelChangeEvent e) {
        List<String> levelsBroadcast = Main.getInstance().getConfig().getStringList("levels-broadcast");
        String levelsMessage = Main.getInstance().getConfig().getString("levels-broadcast-message");
        String level = String.valueOf(e.getUser().getLevel());

        e.getUser().setDropMultipiler(e.getUser().getDropMultipiler() + 0.1f);

        for (String lvl : levelsBroadcast) {
            if (level.equals(lvl)) {
                Bukkit.broadcastMessage(Util.fixColors(levelsMessage
                        .replace("{LEVEL}", level))
                        .replace("{PLAYER}", e.getUser().getName()));
            }
        }
    }
}
