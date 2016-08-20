package me.khalit.qDrop.implementation;

import me.khalit.qDrop.implementation.interfaces.Drop;
import me.khalit.qDrop.implementation.interfaces.User;
import me.khalit.qDrop.utils.keys.KeyPair;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

/**
 * Created by KHaliT on 18.08.2016.
 */
public class DropImpl implements Drop {

    private final String name;
    private ItemStack item;
    private double chance;
    private List<Material> tools;
    private int experience;
    private KeyPair<Integer, Integer> amounts;
    private KeyPair<Double, Double> heights;
    private Material block;
    private List<Biome> biomes;
    private String message;
    private HashMap<Integer, Integer> fortune;
    private int levelPoints;
    private int levelRequirement;

    public DropImpl(String name) {
        this.name = name;

        // values will be replaced with values from config using ConfigurationSectionReader

        // amount prototype calculator: (random amount * fortuneMultipiler) * dropMultipiler
        // dropMultipiler = @GOTO UserImpl
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public double getChance() {
        return chance;
    }

    @Override
    public List<Material> getTools() {
        return tools;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public KeyPair<Integer, Integer> getAmounts() {
        return amounts;
    }

    @Override
    public KeyPair<Double, Double> getHeights() {
        return heights;
    }

    @Override
    public Material getBlock() {
        return block;
    }

    @Override
    public List<Biome> getBiomes() {
        return biomes;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HashMap<Integer, Integer> getFortunes() {
        return fortune;
    }

    @Override
    public int getLevelPoints() {
        return levelPoints;
    }

    @Override
    public int getLevelRequirement() {
        return levelRequirement;
    }

    @Override
    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public void setChance(double chance) {
        this.chance = chance;
    }

    @Override
    public void setTools(List<Material> tools) {
        this.tools = tools;
    }

    @Override
    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public void setAmounts(KeyPair<Integer, Integer> amounts) {
        this.amounts = amounts;
    }

    @Override
    public void setHeights(KeyPair<Double, Double> heights) {
        this.heights = heights;
    }

    @Override
    public void setBlock(Material block) {
        this.block = block;
    }

    @Override
    public void setBiomes(List<Biome> biomes) {
        this.biomes = biomes;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setFortunes(HashMap<Integer, Integer> fortune) {
        this.fortune = fortune;
    }

    @Override
    public void setLevelPoints(int levelPoints) {
        this.levelPoints = levelPoints;
    }

    @Override
    public void setLevelRequirement(int levelRequirement) {
        this.levelRequirement = levelRequirement;
    }

    @Override
    public boolean isDisabled(User user) {
        if (user.getDisabledDrops() == null) {
            return false;
        }
        for (Drop d : user.getDisabledDrops()) {
            if (this.equals(d)) {
                return true;
            }
        }
        return false;
    }
}
