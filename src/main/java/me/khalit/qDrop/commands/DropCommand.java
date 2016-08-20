package me.khalit.qDrop.commands;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.gui.GUICreator;
import me.khalit.qDrop.gui.GUIType;
import me.khalit.qDrop.utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by KHaliT on 20.08.2016.
 */
public class DropCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!(s instanceof Player))
        {
            Util.sendMessage(s, Main.getInstance().getConfig().getString("only-player"));
            return true;
        }
        Player p = (Player)s;
        if (!p.hasPermission("qdrop.drop")) {
            Util.sendMessage(p, Main.getInstance().getConfig().getString("no-permission"));
            return true;
        }
        GUICreator.showGUI(GUIType.DROP, p);
        return true;
    }
}
