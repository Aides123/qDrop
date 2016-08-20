package me.khalit.qDrop.listeners;

import me.khalit.qDrop.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Iterator;
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
        Iterator<Block> blocks = e.blockList().iterator();
        List<String> prevention = Main.getInstance().getConfig().getStringList("blocks-dig-prevent");

        while (blocks.hasNext()) {
            Block b = blocks.next();
            for (String prevent : prevention) {
                if (b.getType().toString().equals(prevent)) {
                    blocks.remove();
                    b.setType(Material.AIR);
                }
            }
        }
    }
}
