package me.khalit.qDrop.gui;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.implementation.interfaces.Drop;
import me.khalit.qDrop.implementation.interfaces.User;
import me.khalit.qDrop.utils.Util;
import me.khalit.qDrop.utils.managers.DropManager;
import me.khalit.qDrop.utils.managers.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by KHaliT on 20.08.2016.
 */
public class GUICreator {

    public static void showGUI(GUIType type, Player player) {
        switch (type) {
            case DROP: {
                User u = UserManager.getUser(player);
                System.out.println(u.isCobblestoneEnabled());
                List<Drop> dropList = DropManager.getDrops();
                Drop[] contents = dropList.toArray(new Drop[dropList.size()]);

                Inventory inv = Bukkit.createInventory(null, getInventorySize(contents.length),
                        Util.fixColors(Main.getInstance().getConfig().getString("gui.drop.title")));

                for (int i = 0; i < contents.length; i++) {
                    Drop d = contents[i];
                    ItemStack item = d.getItem();
                    ItemMeta meta = item.getItemMeta();
                    item.setAmount(1);
                    for (Enchantment ench : d.getItem().getEnchantments().keySet()) {
                        meta.addEnchant(ench, d.getItem().getEnchantmentLevel(ench), true);
                    }
                    meta.setDisplayName(Util.fixColors(
                            Main.getInstance().getConfig().getString("gui.drop.item-color") + d.getName()));

                    meta.setLore(Util.fixColoredList(generateLores(GUIType.DROP, d, u, d.isDisabled(u))));
                    System.out.println(meta.getDisplayName());

                    item.setItemMeta(meta);
                    inv.setItem(i, item);
                }
                String ac = Main.getInstance().getConfig().getString("cobblestone-active");

                ItemStack cobble = new ItemStack(Material.COBBLESTONE, 1);
                ItemMeta meta = cobble.getItemMeta();
                meta.setLore(Util.fixColoredList(Arrays.asList(ac
                        .replace("&activecolor", DropManager.getColor(u.isCobblestoneEnabled()))
                        .replace("{ACTIVE}", !u.isCobblestoneEnabled() ? "✖" : "✔"))));
                meta.setDisplayName(Util.fixColors(Main.getInstance().getConfig().getString("gui.drop.item-color") + "Cobblestone"));
                meta.addEnchant(Enchantment.DURABILITY, 9281, true);
                cobble.setItemMeta(meta);

                inv.setItem(getInventorySize(contents.length) - 1, cobble);

                player.openInventory(inv);
            }
        }
    }

    public static List<String> generateLores(GUIType type, Drop d, User u, boolean itemDisabled) {
        switch (type) {
            case DROP: {
                List<String> loresRaw = Main.getInstance().getConfig().getStringList("gui.drop.lores");
                List<String> lores = new ArrayList<>();
                for (String s : loresRaw) {
                    lores.add(s
                            .replace("&activecolor", DropManager.getColor(!itemDisabled))
                            .replace("&lvlcolor", DropManager.getColor(DropManager.canDrop(d, u)))
                            .replace("{REQUIREDLEVEL}", String.valueOf(d.getLevelRequirement()))
                            .replace("{ITEM}", d.getItem().getType().toString().toLowerCase().replace("_", " "))
                            .replace("{CHANCE}", String.valueOf(d.getChance()))
                            .replace("{AMOUNT-START}", String.valueOf(d.getAmounts().getKey()))
                            .replace("{AMOUNT-END}", String.valueOf(d.getAmounts().getValue()))
                            .replace("{HEIGHTS-START}", String.valueOf(d.getHeights().getKey()))
                            .replace("{HEIGHTS-END}", String.valueOf(d.getHeights().getValue()))
                            .replace("{EXP}", String.valueOf(d.getExperience()))
                            .replace("{BLOCK}", String.valueOf(d.getBlock().toString().toLowerCase().replace("_", " ")))
                            .replace("{BIOMES}", Util.listToString(Util.biomesToList(d.getBiomes())))
                            .replace("{TOOLS}", Util.listToString(Util.toolsToList(d.getTools())))
                            .replace("{LEVELPOINTS}", String.valueOf(d.getLevelPoints()))
                            .replace("{ACTIVE}", itemDisabled ? "✖" : "✔"));
                }
                return lores;
            }
        }
        return null;
    }

    public static int getInventorySize(int contentSize) {
        int[] numbers = GUISizes.getSizes();

        for (int i = 0; i < numbers.length; i++){
            if(contentSize < numbers[i]){
                return numbers[i];
            }
        }
        return 9;
    }
}
