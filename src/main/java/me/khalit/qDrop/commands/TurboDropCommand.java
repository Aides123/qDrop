package me.khalit.qDrop.commands;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.implementation.TurboDropImpl;
import me.khalit.qDrop.implementation.interfaces.TurboDrop;
import me.khalit.qDrop.implementation.interfaces.User;
import me.khalit.qDrop.utils.Util;
import me.khalit.qDrop.utils.managers.TurboDropManager;
import me.khalit.qDrop.utils.managers.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by KHaliT on 20.08.2016.
 */
public class TurboDropCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!(s instanceof Player))
        {
            Util.sendMessage(s, Main.getInstance().getConfig().getString("only-player"));
            return true;
        }
        Player p = (Player)s;
        User u = UserManager.getUser(p);
        if (!p.hasPermission("qdrop.turbo")) {
            Util.sendMessage(p, Main.getInstance().getConfig().getString("no-permission"));
            return true;
        }
        if (args.length == 0) {
            if ((TurboDropManager.getCurrentTurboDrop() != null) && (TurboDropManager.getCurrentTurboDrop().isEnabled())) {
                TurboDrop turbo = TurboDropManager.getCurrentTurboDrop();
                List<String> turboEnabled = Main.getInstance().getConfig().getStringList("turbo-enabled");
                for (String str : turboEnabled) {
                    Util.sendMessage(p, str
                            .replace("{WHO}", turbo.getWho().getName())
                            .replace("{TIME}", turbo.timeRemaining())
                            .replace("{CHANCE}", String.valueOf(Main.getInstance().getConfig().getDouble("turbo-chance-increase"))));
                }
                return true;
            }
            List<String> turboDisabled= Main.getInstance().getConfig().getStringList("turbo-disabled");
            for (String str : turboDisabled) {
                Util.sendMessage(p, str
                        .replace("{CHANCE}", String.valueOf(Main.getInstance().getConfig().getDouble("turbo-chance-increase"))));
            }
        }
        else if (args.length == 2) {
            if (!p.hasPermission("qdrop.turbo.start")) {
                Util.sendMessage(p, Main.getInstance().getConfig().getString("no-permission"));
                return true;
            }
            OfflinePlayer who = Bukkit.getOfflinePlayer(args[0]);
            TurboDrop turbo = null;
            try {
                turbo = new TurboDropImpl(who, (Integer.valueOf(args[1]) * 1000) + System.currentTimeMillis());
            } catch (NumberFormatException e) {
                Util.sendMessage(p, "&cYou need to enter a <time> variable, which is integer!");
                return true;
            }
            turbo.start();
            List<String> turboEnabled = Main.getInstance().getConfig().getStringList("turbo-enabled");
            for (String str : turboEnabled) {
                Util.sendMessage(p, str
                        .replace("{WHO}", who.getName())
                        .replace("{TIME}", String.valueOf(turbo.timeRemaining()))
                        .replace("{CHANCE}", String.valueOf(Main.getInstance().getConfig().getDouble("turbo-chance-increase"))));
            }
        }
        return true;
    }
}
