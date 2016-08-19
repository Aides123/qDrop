package me.khalit.qDrop.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

/**
 * Created by KHaliT on 19.08.2016.
 */
public class Parser {

    private Parser() { }

    public static HashMap<Material, Integer> getParsedLevelBlocks(List<String> values) {
        HashMap<Material, Integer> map = new HashMap<>();
        for (String val : values) {
            String[] split = val.split(":");
            Material key = Material.matchMaterial(split[0]);
            int value = Integer.valueOf(split[1]);

            map.put(key, value);
        }
        return map;
    }

    public static HashMap<Integer, Integer> getParsedFortunes(List<String> values) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (String val : values) {
            String[] split = val.split(":");
            int key = Integer.valueOf(split[0]);
            int value = Integer.valueOf(split[1]);

            map.put(key, value);
        }
        return map;
    }

    public static ItemStack getParsedItem(String syntax) {
        String[] args = syntax.split(" ");
        ItemStack itemstack = getParsedItemData(args[0]);
        ItemMeta itemmeta = itemstack.getItemMeta();
        if ((args.length >= 2) && (!args[1].equalsIgnoreCase("*"))) {
            itemmeta.setDisplayName(Util.fixColors(args[2].replace("_", " ")));
        }
        if ((args.length >= 3) && (!args[2].equalsIgnoreCase("*"))) {
            String[] loreLines = args[2].split(";");
            List<String> lore = Lists.newArrayList();
            for (String s : loreLines) {
                lore.add(s.replace("_", " "));
            }
            itemmeta.setLore(Util.fixColoredList(lore));
        }
        itemstack.setItemMeta(itemmeta);
        if ((args.length >= 4) && (!args[3].equalsIgnoreCase("*"))) {
            String[] enchantments = args[3].split(";");
            for (String s : enchantments) {
                addEnchant(s, itemstack);
            }
        }
        return itemstack;
    }

    public static void addEnchant(String syntax, ItemStack is) {
        String[] args = syntax.split(":");
        Enchantment ench = EnchantmentDatabase.enchants.get(args[0]);
        int power = Integer.valueOf(args[1]);
        is.addUnsafeEnchantment(ench, power);
        return;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack getParsedItemData(String item) {
        ItemStack itemR = null;
        try {
            short data;
            String[] split;

            if (!item.contains(":")) {
                split = new String[]{item};
                data = 0;
            } else {
                split = item.split(":");
                data = split[1].length() < 5 && split[1].length() > 0 ? Short.parseShort(split[1]) : 0;
            }

            String namer = split[0];

            if (StringUtils.isNumeric(namer)) {
                int id = namer.length() < 5 && namer.length() > 0 ? Integer.parseInt(namer) : 0;
                itemR = new ItemStack(Material.getMaterial(id), 1, data);
            } else itemR = new ItemStack(Material.matchMaterial(namer.toUpperCase()), 1, data);

        } catch (Exception e) {
            return null;
        }

        return itemR;
    }
}
