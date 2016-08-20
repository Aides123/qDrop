package me.khalit.qDrop.implementation.interfaces;

import org.bukkit.OfflinePlayer;

/**
 * Created by KHaliT on 20.08.2016.
 */
public interface TurboDrop {

    OfflinePlayer getWho();
    long getTime();

    boolean isEnabled();
    String timeRemaining();
    String parseTime(long time);

    void start();

}
