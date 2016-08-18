package me.khalit.qDrop.implementation.interfaces;

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
    float getChance();
    List<Material> getTools();
    int getExperience();
    HashMap<Integer, Integer> getAmounts();
    HashMap<Float, Float> getHeights();
    Material getBlock();
    List<Biome> getBiomes();
    String getMessage();
    FortuneIdentifier getFortunes();

    // leveling
    int getLevelPoints();
    int getLevelRequirement();

    // setters
    void setItem(ItemStack item);
    void setChance(float chance);
    void setTools(List<Material> tools);
    void setExperience(int experience);
    void setAmounts(HashMap<Integer, Integer> amounts);
    void setHeights(HashMap<Float, Float> heights);
    void setBlock(Material block);
    void setBiomes(List<Biome> biomes);
    void setMessage(String message);
    void setFortunes(FortuneIdentifier fortune);

    // leveling
    void setLevelPoints(int levelPoints);
    void setLevelRequirement(int levelRequirement);

}
