package me.khalit.qDrop.implementation;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.events.LevelChangeEvent;
import me.khalit.qDrop.events.LevelPointChangeEvent;
import me.khalit.qDrop.implementation.interfaces.Drop;
import me.khalit.qDrop.implementation.interfaces.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
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
    private List<Drop> disabledDrops;
    private boolean cobblestoneEnabled;

    public UserImpl(String name, UUID uuid) {
        this.uuid = uuid;
        this.name = name;
        this.level = 0;
        this.levelPoints = 0;
        this.dropMultipiler = 1.0f;
    }

    public UserImpl(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.level = 0;
        this.levelPoints = 0;
        this.dropMultipiler = 1.0f;
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
    public boolean isCobblestoneEnabled() {
        return cobblestoneEnabled;
    }

    @Override
    public List<Drop> getDisabledDrops() {
        return disabledDrops;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        Bukkit.getPluginManager().callEvent(new LevelChangeEvent(this.level, levelPoints, this));
    }

    @Override
    public void setDropMultipiler(float dropMultipiler) {
        this.dropMultipiler = dropMultipiler;
    }

    @Override
    public void setLevelPoints(int levelPoints) {
        this.levelPoints = levelPoints;
        Bukkit.getPluginManager().callEvent(new LevelPointChangeEvent(level, this.levelPoints, this));
    }

    @Override
    public void setDisabledDrops(List<Drop> drops) {
        this.disabledDrops = drops;
    }

    @Override
    public void addDisabledDrop(Drop drop) {
        this.disabledDrops.add(drop);
    }

    @Override
    public void removeDisabledDrop(Drop drop) {
        this.disabledDrops.remove(drop);
    }

    @Override
    public void setCobblestoneEnabled(boolean cobblestoneEnabled) {
        this.cobblestoneEnabled = cobblestoneEnabled;
    }

    @Override
    public void update() {
        try {
            try (PreparedStatement stat = Main.getSQL().getConnection().prepareStatement(
                    "UPDATE `users` SET `levelPoints`=?,`level`=?,`dropMultipiler`=? WHERE `uuid`=? AND `name`=?")) {
                stat.setInt(1, levelPoints);
                stat.setInt(2, level);
                stat.setFloat(3, dropMultipiler);
                stat.setString(4, uuid.toString());
                stat.setString(5, name);
                stat.execute();
                stat.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert() {
        try {
            try (PreparedStatement stat = Main.getSQL().getConnection().prepareStatement(
                    "INSERT INTO `users` (`uuid`, `name`, `levelPoints`, `level`, `dropMultipiler`) VALUES (?, ?, ?, ?, ?)")) {
                stat.setString(1, uuid.toString());
                stat.setString(2, name);
                stat.setInt(3, 0);
                stat.setInt(4, 0);
                stat.setFloat(5, 1.0f);
                stat.execute();
                stat.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasPlayedBefore() {
        try {
            PreparedStatement stat = Main.getSQL().getConnection().prepareStatement(
                    "SELECT uuid FROM users WHERE uuid=?");
            stat.setString(1, uuid.toString());
            ResultSet rs = stat.executeQuery();

            if (rs.getString("uuid") == null) {
                return false;
            }

            stat.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
