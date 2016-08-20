package me.khalit.qDrop.listeners;

import me.khalit.qDrop.Main;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

/**
 * Created by KHaliT on 20.08.2016.
 */
public class EntityExplodeListener implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        List<Block> blocks = e.blockList();
        List<String> prevention = Main.getInstance().getConfig().getStringList("blocks-dig-prevent");
        for (Block b : blocks) {
            for (String s : prevention) {
                if (b.getType().toString().equals(s)) {
                    blocks.remove(b);
                    break;
                }
            }
        }
    }
}
