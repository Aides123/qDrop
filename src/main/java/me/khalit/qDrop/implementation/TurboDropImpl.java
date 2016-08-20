package me.khalit.qDrop.implementation;

import me.khalit.qDrop.Main;
import me.khalit.qDrop.implementation.interfaces.TurboDrop;
import me.khalit.qDrop.utils.Util;
import me.khalit.qDrop.utils.managers.TurboDropManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by KHaliT on 20.08.2016.
 */
public class TurboDropImpl implements TurboDrop {

    private static long time;
    private static OfflinePlayer who;

    public TurboDropImpl(OfflinePlayer who, long time) {
        this.who = who;
        this.time = time;
    }

    @Override
    public OfflinePlayer getWho() {
        return who;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public boolean isEnabled() {
        if (TurboDropManager.getCurrentTurboDrop() == null) return false;
        long currentTime = System.currentTimeMillis();
        if (time == 0) return false;

        if (time > currentTime)
            return true;

        return false;
    }

    @Override
    public String timeRemaining() {
        return parseTime((time - System.currentTimeMillis()));
    }

    @Override
    public String parseTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        return sdf.format(date);
    }

    @Override
    public void start() {
        System.out.println( (int)TimeUnit.MILLISECONDS.toSeconds((time - System.currentTimeMillis())) * 20); // returns good, 30s, 40s etc

        for (Player p : Bukkit.getOnlinePlayers()) {
            BossBar bar = BossBarAPI.addBar(p,
                    new TextComponent(Util.fixColors(Main.getInstance().getConfig()
                            .getString("turbo-bar-message").replace("{WHO}", who.getName()))),
                    BossBarAPI.Color.valueOf(Main.getInstance().getConfig().getString("turbo-bar-color")),
                    BossBarAPI.Style.PROGRESS,
                    1.0f,
                    (int)TimeUnit.MILLISECONDS.toSeconds((time - System.currentTimeMillis())) * 20,
                    1);


        }
        TurboDropManager.setCurrentTurboDrop(this);
    }
}
