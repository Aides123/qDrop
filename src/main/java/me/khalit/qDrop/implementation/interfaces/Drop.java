package me.khalit.qDrop.implementation.interfaces;

import me.khalit.qDrop.utils.keys.KeyPair;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

/**
 * Created by KHaliT on 18.08.2016.
 */
public interface Drop {

    String getName();
    ItemStack getItem();
    double getChance();
    List<Material> getTools();
    int getExperience();
    KeyPair<Integer, Integer> getAmounts();
    KeyPair<Double, Double> getHeights();
    Material getBlock();
    List<Biome> getBiomes();
    String getMessage();
    HashMap<Integer, Integer> getFortunes();

    // leveling
    int getLevelPoints();
    int getLevelRequirement();

    // setters
    void setItem(ItemStack item);
    void setChance(double chance);
    void setTools(List<Material> tools);
    void setExperience(int experience);
    void setAmounts(KeyPair<Integer, Integer> amounts);
    void setHeights(KeyPair<Double, Double> heights);
    void setBlock(Material block);
    void setBiomes(List<Biome> biomes);
    void setMessage(String message);
    void setFortunes(HashMap<Integer, Integer>fortune);

    // leveling
    void setLevelPoints(int levelPoints);
    void setLevelRequirement(int levelRequirement);

}
