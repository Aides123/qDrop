package me.khalit.qDrop.utils.managers;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.implementation.UserImpl;
import me.khalit.qDrop.implementation.interfaces.User;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KHaliT on 19.08.2016.
 */
public class UserManager {

    private static final List<User> users = new ArrayList<>();

    public static List<User> getUsers() {
        return users;
    }

    public static User loadUser(Player player) {
        User user = getUser(player);
        try {
            PreparedStatement stat = Main.getSQL().getConnection().prepareStatement(
                    "SELECT * FROM users WHERE uuid=?");
            stat.setString(1, user.getUUID().toString());

            ResultSet rs = Main.getSQL().executeSelect(stat);
            while (rs.next()) {
                user.setLevelPoints(rs.getInt("levelPoints"));
                user.setLevel(rs.getInt("level"));
                user.setDropMultipiler(rs.getFloat("dropMultipiler"));
            }
            if (!getUsers().contains(user)) {
                getUsers().add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static User getUser(Player player) {
        for (User user : getUsers()) {
            if (user.getUUID().equals(player.getUniqueId())) return user;
        }
        return new UserImpl(player);
    }

    public static User getUserPossibleNull(Player player) {
        for (User user : getUsers()) {
            if (user.getUUID().equals(player.getUniqueId())) return user;
        }
        return null;
    }
}
