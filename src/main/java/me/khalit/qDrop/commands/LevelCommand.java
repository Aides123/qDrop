package me.khalit.qDrop.commands;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.implementation.interfaces.User;
import me.khalit.qDrop.utils.Util;
import me.khalit.qDrop.utils.managers.UserManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by KHaliT on 20.08.2016.
 */
public class LevelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!(s instanceof Player))
        {
            Util.sendMessage(s, Main.getInstance().getConfig().getString("only-player"));
            return true;
        }
        Player p = (Player)s;
        User u = UserManager.getUser(p);
        if (!p.hasPermission("qdrop.level")) {
            Util.sendMessage(p, Main.getInstance().getConfig().getString("no-permission"));
            return true;
        }
        double multipiler = Main.getInstance().getConfig().getDouble("points-upgrade-multipiler");
        int nextLevelIn = (int)Math.round(u.getLevel() * multipiler);
        Util.sendMessage(p, Main.getInstance().getConfig().getString("level-contents")
                    .replace("{LEVEL}", String.valueOf(u.getLevel()))
                    .replace("{LEVELPOINTS}", String.valueOf(u.getLevelPoints()))
                    .replace("{RANKUP}", String.valueOf(nextLevelIn))
                    .replace("{DROPMULTI}", String.valueOf(Util.round(u.getDropMultipiler(), 2))));
        return true;
    }
}
