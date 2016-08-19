package me.khalit.qDrop.configuration;

import me.khalit.qDrop.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KHaliT on 19.08.2016.
 */
public class Configuration {

    private static List<Configuration> dataFiles = new ArrayList<>();
    public static List<Configuration> getDataFiles() {
        return dataFiles;
    }

    private final File file;
    private FileConfiguration fileConfiguration;

    public Configuration(String fileName, String directory) {
        this.file = new File(Main.getInstance().getDataFolder()
                + "/" + directory + "/" + fileName);
    }

    public Configuration(String fileName) {
        this.file = new File(Main.getInstance().getDataFolder()
                + "/" + fileName);
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getFileConfiguration() {
        if (fileConfiguration == null) {
            fileConfiguration =
                    YamlConfiguration.loadConfiguration(file);
        }
        return fileConfiguration;
    }

    public void saveDefault() {
        if (!file.exists()) {
            Main.getInstance().saveResource(file.getName(), false);
        }
    }

    public void save() {
        if (fileConfiguration == null || file == null) {
            return;
        }
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        Reader stream = null;
        try {
            stream = new InputStreamReader(
                    Main.getInstance().getResource(file.getName()), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (stream != null) {
            YamlConfiguration conf =
                    YamlConfiguration.loadConfiguration(file);
            fileConfiguration.setDefaults(conf);
        }
    }
}
