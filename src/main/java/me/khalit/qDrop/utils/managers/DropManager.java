package me.khalit.qDrop.utils.managers;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.implementation.TurboDropImpl;
import me.khalit.qDrop.implementation.interfaces.Drop;
import me.khalit.qDrop.implementation.interfaces.User;
import me.khalit.qDrop.utils.Util;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;

import java.util.*;

/**
 * Created by KHaliT on 19.08.2016.
 */
public class DropManager {

    private static final List<Drop> drops = new ArrayList<>();

    public static List<Drop> getDrops() {
        return drops;
    }

    public static void add(Drop drop) {
        drops.add(drop);
    }

    public static void remove(Drop drop) {
        if (drops.contains(drop))
            drops.add(drop);
    }

    public static void breakBlock(Player p, Block b) {
        User u = UserManager.getUser(p);

        PlayerInventory inv = p.getInventory();
        ItemStack tool = inv.getItem(inv.getHeldItemSlot());
        if (tool == null)
            tool = new ItemStack(Material.AIR, 1);

        boolean silktouch = tool.containsEnchantment(Enchantment.SILK_TOUCH);

        List<ItemStack> toDrop = new ArrayList<>();

        for (Drop d : drops) {
            if (d.isDisabled(u)) {
                continue;
            }
            if (!b.getType().equals(d.getBlock())) {
                continue;
            }
            double chance = d.getChance();
            if ((TurboDropManager.getCurrentTurboDrop() != null) && (TurboDropManager.getCurrentTurboDrop().isEnabled())) {
                chance = d.getChance() + Main.getInstance().getConfig().getDouble("turbo-chance-increase");
            }
            if (!Util.getChance(chance / 100)) {
                continue;
            }
            if (d.getLevelRequirement() > u.getLevel()) {
                continue;
            }
            if (!d.getTools().contains(tool.getType())) {
                continue;
            }
            if (!d.getBiomes().contains(b.getBiome())) {
                continue;
            }
            int y = b.getLocation().getBlockY();
            if ((y <= d.getHeights().getKey()) || (y >= d.getHeights().getValue())) {
                continue;
            }
            int amountRawRaw = Util.randomInt(d.getAmounts().getKey(), d.getAmounts().getValue());
            int amountRaw = calculateFortune(d, amountRawRaw, tool);

            int amount = (int)Math.round(amountRaw * u.getDropMultipiler());

            p.giveExp(d.getExperience());
            u.setLevelPoints(u.getLevelPoints() + d.getLevelPoints());

            ItemStack toAdd = d.getItem();
            ItemMeta clear = toAdd.getItemMeta();
            clear.setLore(new ArrayList<>());
            clear.setDisplayName(null);
            toAdd.setItemMeta(clear);
            toAdd.setAmount(amount);
            toDrop.add(toAdd);

            if (!d.getMessage().equals("")) {
                Util.sendMessage(p, d.getMessage().replace("{AMOUNT}", String.valueOf(amount)));
            }
        }

        List<ItemStack> dropable = getDropableItems(b, tool);
        for (ItemStack i : dropable) {
            toDrop.add(i);
        }

        if (!u.isCobblestoneEnabled()) {
            if (toDrop.contains(new ItemStack(Material.COBBLESTONE))) {
                toDrop.remove(new ItemStack(Material.COBBLESTONE));
            }
        }

        ItemStack[] toDropArray = new ItemStack[toDrop.size()];
        for (int i = 0; i < toDrop.size(); i++) {
            toDropArray[i] = toDrop.get(i);
        }
        HashMap<Integer, ItemStack> itemsToDrop = inv.addItem(toDropArray);
        for (Map.Entry<Integer, ItemStack> item : itemsToDrop.entrySet()) {
            b.getWorld().dropItemNaturally(b.getLocation(), item.getValue());
        }
        calculateDurability(p, tool);

        b.setType(Material.AIR);
    }

    public static List<ItemStack> getDropableItems(Block b, ItemStack tool) {
        List<ItemStack> items = new ArrayList<>();

        short data = b.getData();
        Material type = b.getType();
        int amount = 1;

        switch (type) {
            case POTATO: {
                data = b.getState().getData().getData();
                switch (data) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6: {
                        amount = 1;
                        break;
                    }
                    case 7: {
                        amount = Util.randomInt(1, 4);
                        break;
                    }
                }
                items.add(new ItemStack(Material.POTATO_ITEM, amount));
                break;
            }
            case CARROT: {
                data = b.getState().getData().getData();
                switch (data) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6: {
                        amount = 1;
                        break;
                    }
                    case 7: {
                        amount = Util.randomInt(1, 4);
                        break;
                    }
                }
                items.add(new ItemStack(Material.CARROT_ITEM, amount));
                break;
            }
            case SUGAR_CANE_BLOCK: {
                amount = 1;
                items.add(new ItemStack(Material.SUGAR_CANE, amount));
                break;
            }
            case CHORUS_PLANT: {
                amount = 1;
                items.add(new ItemStack(Material.CHORUS_FRUIT));
                break;
            }
            case NETHER_WARTS: {
                NetherWarts warts = (NetherWarts)b.getState().getData();
                amount = warts.getState().equals(NetherWartsState.RIPE) ? (Util.randomInt(0, 2) + 2) : 1;

                items.add(new ItemStack(Material.NETHER_STALK, amount));
                break;
            }
            case PUMPKIN_STEM: {
                items.add(new ItemStack(Material.PUMPKIN_SEEDS, Util.randomInt(1, 3)));
                break;
            }
            case MELON_STEM: {
                items.add(new ItemStack(Material.MELON_SEEDS, Util.randomInt(1, 2)));
                break;
            }
            case COCOA: {
                CocoaPlant cocoa = (CocoaPlant)b.getState().getData();
                amount = cocoa.getSize().equals(CocoaPlant.CocoaPlantSize.LARGE) ? 3 : 1;

                items.add(new ItemStack(Material.COCOA, amount));
                break;
            }
            case CROPS: {
                Crops wheat = (Crops)b.getState().getData();
                int a = 1;
                if (wheat.getState() == CropState.RIPE) {
                    items.add(new ItemStack(Material.WHEAT, Util.randomInt(1, 2)));
                    a = 1 + Util.randomInt(0, 2);
                }
                items.add(new ItemStack(Material.SEEDS, a));
                break;
            }
            case WOOD_BUTTON:
            case TRIPWIRE:
            case LEVER:
            case STONE_BUTTON:
            case DIODE_BLOCK_ON:
            case DIODE_BLOCK_OFF:
            case IRON_DOOR:
            case REDSTONE_COMPARATOR_OFF:
            case REDSTONE_COMPARATOR_ON:
            case DAYLIGHT_DETECTOR:
            case DOUBLE_PLANT:
            case REDSTONE_WIRE:
            case WOODEN_DOOR:
            case REDSTONE_ORE: {
                items.addAll(b.getDrops(tool));
                break;
            }
            default: {
                if (tool.containsEnchantment(Enchantment.SILK_TOUCH)) {
                    if (!b.getType().isBlock()) {
                        break;
                    }
                    items.add(new ItemStack(b.getType(), 1, (short)b.getData()));
                    break;
                }
                items.addAll(b.getDrops(tool));
            }
        }
        return items;
    }

    public static int calculateFortune(Drop drop, int amount, ItemStack item) {
        if (!item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
            return amount;
        }
        int fortuneLevel = item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        int modifiedAmount = amount;
        HashMap<Integer, Integer> fortunes = drop.getFortunes();
        for (int k : fortunes.keySet()) {
            if (k == fortuneLevel) {
                int key = k;
                int value = fortunes.get(k);
                modifiedAmount *= value;
                break;
            }
        }
        return modifiedAmount;
    }

    public static void calculateDurability(Player player, ItemStack item) {
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

    public static void breakItem(Player player, ItemStack item) {
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
        player.getInventory().removeItem(item);
    }

    public static boolean canDrop(Drop d, User u) {
        return d.getLevelRequirement() <= u.getLevel();
    }

    public static String getColor(boolean active) {
        if (active)
            return "&a";

        return "&c";
    }
}
