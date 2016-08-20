package me.khalit.qDrop.listeners;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.events.LevelChangeEvent;
import me.khalit.qDrop.events.LevelPointChangeEvent;
import me.khalit.qDrop.implementation.interfaces.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by KHaliT on 20.08.2016.
 */
public class LevelPointChangeListener implements Listener {

    @EventHandler
    public void onLevelPointChange(LevelPointChangeEvent e) {
        Player p = e.getPlayer();
        User u = e.getUser();
        int level = e.getLevel();
        int levelPoints = e.getLevelPoints();

        double multipiler = Main.getInstance().getConfig().getDouble("points-upgrade-multipiler");
        int nextLevelIn = (int)Math.round(level * multipiler);

        if (levelPoints >= nextLevelIn) {
            level++;
            levelPoints = 0;
            u.setLevel(level);
            u.setLevelPoints(levelPoints);
        }

    }
}
