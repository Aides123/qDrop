package me.khalit.qDrop.listeners;

import me.khalit.qDrop.implementation.interfaces.User;
import me.khalit.qDrop.utils.managers.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by KHaliT on 19.08.2016.
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        User user = UserManager.getUserPossibleNull(p);
        if (user == null) {
            user = UserManager.loadUser(p);
            user.insert();
        }


    }
}
