package me.khalit.qDrop.listeners.async;

import me.khalit.qDrop.implementation.interfaces.User;
import me.khalit.qDrop.utils.managers.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.PluginManager;

/**
 * Created by KHaliT on 20.08.2016.
 */
public class AsyncPlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String f = e.getFormat();
        User u = UserManager.getUser(p);

        f = f.replace("{LEVEL}", String.valueOf(u.getLevel()));
        f = f.replace("{LEVELPOINTS}", String.valueOf(u.getLevelPoints()));
        f = f.replace("{DROPMULTIPILER}", String.valueOf(u.getDropMultipiler()));

        e.setFormat(f);
    }
}
