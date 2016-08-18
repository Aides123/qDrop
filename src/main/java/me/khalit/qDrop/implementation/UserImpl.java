package me.khalit.qDrop.implementation;

import me.khalit.qDrop.implementation.interfaces.User;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by KHaliT on 18.08.2016.
 */
public class UserImpl implements User {

    private final UUID uuid;
    private final String name;
    private int level;
    private float dropMultipiler;
    private int levelPoints;

    public UserImpl(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UserImpl(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public float getDropMultipiler() {
        return dropMultipiler;
    }

    @Override
    public int getLevelPoints() {
        return levelPoints;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void setDropMultipiler(float dropMultipiler) {
        this.dropMultipiler = dropMultipiler;
    }

    @Override
    public void setLevelPoints(int levelPoints) {
        this.levelPoints = levelPoints;
    }
}
