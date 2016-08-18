package me.khalit.qDrop.configuration;

import me.khalit.qDrop.DropAssembly;
import me.khalit.qDrop.Main;
import me.khalit.qDrop.implementation.DropImpl;
import me.khalit.qDrop.implementation.interfaces.Drop;
import org.bukkit.configuration.ConfigurationSection;

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

        // TODO Object settings
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
