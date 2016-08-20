package me.khalit.qDrop.listeners;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.gui.GUICreator;
import me.khalit.qDrop.gui.GUIType;
import me.khalit.qDrop.implementation.interfaces.Drop;
import me.khalit.qDrop.implementation.interfaces.User;
import me.khalit.qDrop.utils.Util;
import me.khalit.qDrop.utils.managers.DropManager;
import me.khalit.qDrop.utils.managers.UserManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by KHaliT on 20.08.2016.
 */
public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().getTopInventory().getName()
                .equalsIgnoreCase(Util.fixColors(
                        Main.getInstance().getConfig().getString("gui.drop.title")))) {
            return;
        }

        e.setCancelled(true);

        int index = e.getSlot();
        ItemStack i = e.getCurrentItem();
        if (i == null) {
            return;
        }
        ItemMeta m = e.getCurrentItem().getItemMeta();
        if (m == null) {
            return;
        }
        Player p = (Player)e.getWhoClicked();
        User u = UserManager.getUser(p);
        if (e.getSlot() > DropManager.getDrops().size()) {
            if (m.getEnchants().containsKey(Enchantment.DURABILITY)) {
                if (m.getEnchants().get(Enchantment.DURABILITY) == 9281) {
                    if(u.isCobblestoneEnabled())
                        u.setCobblestoneEnabled(false);
                    else {
                        u.setCobblestoneEnabled(true);
                    }
                    p.closeInventory();
                    GUICreator.showGUI(GUIType.DROP, p);
                }
            }
            return;
        }
        Drop d = DropManager.getDrops().get(e.getSlot());

        boolean active = d.isDisabled(u) ? false : true;
        if (active) u.addDisabledDrop(d);
        else {
            u.removeDisabledDrop(d);
        }
        p.closeInventory();
        GUICreator.showGUI(GUIType.DROP, p);
    }
}
