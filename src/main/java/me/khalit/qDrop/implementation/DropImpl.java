package me.khalit.qDrop.implementation;

import me.khalit.qDrop.implementation.interfaces.Drop;
import me.khalit.qDrop.implementation.interfaces.FortuneIdentifier;
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
    private float chance;
    private List<Material> tools;
    private int experience;
    private HashMap<Integer, Integer> amounts;
    private HashMap<Float, Float> heights;
    private Material block;
    private List<Biome> biomes;
    private String message;
    private FortuneIdentifier fortune;
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
    public float getChance() {
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
    public HashMap<Integer, Integer> getAmounts() {
        return amounts;
    }

    @Override
    public HashMap<Float, Float> getHeights() {
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
    public FortuneIdentifier getFortunes() {
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
    public void setChance(float chance) {
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
    public void setAmounts(HashMap<Integer, Integer> amounts) {
        this.amounts = amounts;
    }

    @Override
    public void setHeights(HashMap<Float, Float> heights) {
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
    public void setFortunes(FortuneIdentifier fortune) {
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
}
