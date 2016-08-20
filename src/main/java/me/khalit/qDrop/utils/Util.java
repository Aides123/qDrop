package me.khalit.qDrop.utils;

import com.google.common.collect.Lists;
import me.khalit.qDrop.Main;
import org.apache.commons.io.IOUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by KHaliT on 19.08.2016.
 */
public class Util {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    private static final StringBuilder sb = new StringBuilder();
    private static final Random random = new Random();

    private Util() {
    }

    public static String fixColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> fixColoredList(List<String> coloredList) {
        final List<String> list = Lists.newArrayList();
        for (String str : coloredList) list.add(fixColors(str));
        return list;
    }

    public static String readUrl(String url) throws Exception {
        InputStream in = new URL(url).openStream();

        try {
            return IOUtils.toString(in);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public static List<String> biomesToList(List<Biome> biomes) {
        List<String> list = new ArrayList<>();
        for (Biome b : biomes) {
            if (list.size() > 3) {
                list.add(Main.getInstance().getConfig().getString("more"));
                break;
            }
            list.add(b.toString().toLowerCase().replace("_", " "));
        }
        return list;
    }

    public static List<String> toolsToList(List<Material> tools) {
        List<String> list = new ArrayList<>();
        for (Material t : tools) {
            if (list.size() > 3) {
                list.add(Main.getInstance().getConfig().getString("more"));
                break;
            }
            list.add(t.toString().toLowerCase().replace("_", " "));
        }
        return list;
    }


    public static String listToString(List<String> list) {
        sb.setLength(0);
        for (String s : list) {
            sb.append(", " + s);
        }
        if (sb.length() == 0) {
            sb.append(Main.getInstance().getConfig().getString("empty"));
        }
        return sb.toString().replaceFirst(", ", "");
    }

    public static boolean isFieldList(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    public static void sendMessage(Player p, String s) {
        p.sendMessage(fixColors(s));
    }

    public static void sendMessage(CommandSender sender, String s) {
        sender.sendMessage(fixColors(s));
    }

    public static boolean getChance(double chance) {
        return (Math.random() < chance);
    }

    public static String formattedTime(long time) {
        return dateFormat.format(new Date(time));
    }

    public static double round(double d, int places) {
        BigDecimal bdec = new BigDecimal(d);
        bdec = bdec.setScale(places, RoundingMode.HALF_UP);
        return bdec.doubleValue();
    }

    public static int randomInt(int min, int max) {
        int i = random.nextInt((max - min) + 1) + min;
        return i;
    }
}
