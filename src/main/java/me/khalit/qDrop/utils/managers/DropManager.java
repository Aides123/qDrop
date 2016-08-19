package me.khalit.qDrop.utils.managers;

import me.khalit.qDrop.implementation.interfaces.Drop;
import me.khalit.qDrop.utils.Util;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.List;

/**
 * Created by KHaliT on 19.08.2016.
 */
public class DropManager {

    public void breakBlock(Player player, Block block) {
        PlayerInventory inv = player.getInventory();
        ItemStack tool = inv.getItem(inv.getHeldItemSlot());

        // TODO all functionality
    }

    public List<ItemStack> getDropableItems() {
        // TODO all functionality
        return null;
    }

    public int calculateFortune(Drop drop, int amount, ItemStack item) {
        int fortuneLevel = item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        int modifiedAmount = amount;
        HashMap<Integer, Integer> fortunes = drop.getFortunes();
        for (int k : fortunes.keySet()) {
            if (k == fortuneLevel) {
                int key = k;
                int value = fortunes.get(k);
                modifiedAmount += value;
            }
        }
        return modifiedAmount;
    }

    public void calculateDurability(Player player, ItemStack item) {
        int durabilityLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
        short durability = item.getDurability();
        if (item.getType().getMaxDurability() == 0) {
            return;
        }
        if (durabilityLevel == 0) {
            item.setDurability((short) (durability + 1));
            return;
        }
        if (durability == item.getType().getMaxDurability()) {
            breakItem(player, item);
            return;
        }
        boolean primitiveDurabilityIncreament = Util.getChance(100 / durabilityLevel);
        if (primitiveDurabilityIncreament) {
            if (durability != item.getType().getMaxDurability()) {
                item.setDurability((short) (durability + 1));
                return;
            }
            breakItem(player, item);
        }
    }

    public void breakItem(Player player, ItemStack item) {
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
        player.getInventory().removeItem(item);
    }
}
