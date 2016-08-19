package me.khalit.qDrop.configuration;

import me.khalit.qDrop.DropAssembly;
import me.khalit.qDrop.Main;
import me.khalit.qDrop.implementation.DropImpl;
import me.khalit.qDrop.implementation.interfaces.Drop;
import me.khalit.qDrop.utils.keys.KeyPair;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

/**
 * Created by KHaliT on 18.08.2016.
 */
public class ConfigurationSectionReader {

    private final ConfigurationSection configurationSection;

    public ConfigurationSectionReader(String section) {
        this.configurationSection =
                Main.getInstance().getConfig().getConfigurationSection(section);
    }

    public Drop toObject(ConfigurationSection section) {
        Drop drop = new DropImpl(section.getName());



        KeyPair<Integer, Integer> amounts = new KeyPair<>();
        amounts.set(section.getInt("amounts.start"), section.getInt("amounts.end"));
        drop.setAmounts(amounts);

        KeyPair<Double, Double> heights = new KeyPair<>();
        heights.set(section.getDouble("heights.start"), section.getDouble("heights.end"));
        drop.setHeights(heights);

        return drop;
    }

    public int loadToAssembly() {
        int index = 0;
        for (String name : configurationSection.getKeys(false)) {
            ConfigurationSection section = configurationSection.getConfigurationSection(name);

            DropAssembly.add(toObject(section));
            index++;
        }
        return index;
    }

}
