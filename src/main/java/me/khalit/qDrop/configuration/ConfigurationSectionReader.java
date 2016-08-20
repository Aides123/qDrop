package me.khalit.qDrop.configuration;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.implementation.DropImpl;
import me.khalit.qDrop.implementation.interfaces.Drop;
import me.khalit.qDrop.utils.Parser;
import me.khalit.qDrop.utils.keys.KeyPair;
import me.khalit.qDrop.utils.managers.DropManager;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by KHaliT on 18.08.2016.
 */
public class ConfigurationSectionReader {

    private final ConfigurationSection configurationSection;

    public ConfigurationSectionReader(String section) {
        this.configurationSection =
                Main.getDrops().getFileConfiguration().getConfigurationSection(section);
    }

    public Drop toObject(ConfigurationSection section) {
        Drop drop = new DropImpl(section.getName());

        ItemStack item = Parser.getParsedItem(section.getString("item"));
        drop.setItem(item);

        double chance = section.getDouble("chance");
        drop.setChance(chance);

        List<String> toolsRaw = section.getStringList("tools");
        List<Material> tools = new ArrayList<>();
        for (String tool : toolsRaw) {
            tools.add(Material.matchMaterial(tool));
        }
        drop.setTools(tools);

        int experience = section.getInt("experience");
        drop.setExperience(experience);

        KeyPair<Integer, Integer> amounts = new KeyPair<>();
        amounts.set(section.getInt("amounts.start"), section.getInt("amounts.end"));
        drop.setAmounts(amounts);

        KeyPair<Double, Double> heights = new KeyPair<>();
        heights.set(section.getDouble("heights.start"), section.getDouble("heights.end"));
        drop.setHeights(heights);

        Material block = Material.matchMaterial(section.getString("block"));
        drop.setBlock(block);

        List<String> biomesRaw = section.getStringList("biomes");
        List<Biome> biomes = new ArrayList<>();
        if (!biomesRaw.isEmpty()) {
            for (String biome : biomesRaw) {
                biomes.add(Biome.valueOf(biome));
            }
        } else {
            biomes = Arrays.stream(Biome.values()).collect(Collectors.toList());
        }
        drop.setBiomes(biomes);

        String message = section.getString("message");
        drop.setMessage(message);

        HashMap<Integer, Integer> fortunes = Parser.getParsedFortunes(section.getStringList("fortunes"));
        drop.setFortunes(fortunes);

        int levelPoints = section.getInt("level.points");
        drop.setLevelPoints(levelPoints);

        int levelRequirement = section.getInt("level.requirement");
        drop.setLevelRequirement(levelRequirement);

        return drop;
    }

    public int loadToAssembly() {
        int index = 0;
        for (String name : configurationSection.getKeys(false)) {
            ConfigurationSection section = configurationSection.getConfigurationSection(name);

            DropManager.add(toObject(section));
            index++;
        }
        return index;
    }

}
