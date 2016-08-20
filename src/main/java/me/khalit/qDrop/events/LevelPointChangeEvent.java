package me.khalit.qDrop.events;

import me.khalit.qDrop.implementation.interfaces.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by KHaliT on 20.08.2016.
 */
public class LevelPointChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private static int level;
    private static int levelPoints;
    private static Player player;
    private static User user;

    public LevelPointChangeEvent(int level, int levelPoints, User user) {
        this.level = level;
        this.levelPoints = levelPoints;
        this.player = player;
        this.user = user;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public int getLevel() {
        return level;
    }

    public int getLevelPoints() {
        return levelPoints;
    }

    public Player getPlayer() {
        return player;
    }

    public User getUser() {
        return user;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
