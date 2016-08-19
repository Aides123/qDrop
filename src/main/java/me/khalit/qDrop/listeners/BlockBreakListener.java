package me.khalit.qDrop.listeners;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.implementation.interfaces.User;
import me.khalit.qDrop.utils.Parser;
import me.khalit.qDrop.utils.Util;
import me.khalit.qDrop.utils.managers.DropManager;
import me.khalit.qDrop.utils.managers.UserManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;
import java.util.List;

/**
 * Created by KHaliT on 19.08.2016.
 */
public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        Player p = e.getPlayer();
        User u = UserManager.getUser(p);

        if (e.isCancelled()) {
            return;
        }
        if (!p.getGameMode().equals(GameMode.SURVIVAL)) {
            return;
        }

        List<String> prevention = Main.getInstance().getConfig().getStringList("blocks-dig-prevent");
        List<String> levelsRaw = Main.getInstance().getConfig().getStringList("blocks-level-points");

        HashMap<Material, Integer> levels = Parser.getParsedLevelBlocks(levelsRaw);
        for (Material mat : levels.keySet()) {
            if (b.getType().equals(mat)) {
                int points = levels.get(mat);

                u.setLevelPoints(u.getLevelPoints() + points);
                break;
            }
        }

        for (String prevent : prevention) {
            if (b.getType().toString().equals(prevent)) {
                Util.sendMessage(p, Main.getInstance().getConfig().getString("blocks-dig-prevent-message"));
                b.setType(Material.AIR);
                e.setCancelled(true);
                return;
            }
        }
        DropManager.breakBlock(p, b);
        e.setCancelled(true);
    }
}
