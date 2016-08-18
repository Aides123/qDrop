package me.khalit.qDrop.implementation.interfaces;

import java.util.UUID;

/**
 * Created by KHaliT on 18.08.2016.
 */
public interface User {

    UUID getUUID();
    String getName();

    // more stats/saveables coming soon

    int getLevel();
    float getDropMultipiler();
    int getLevelPoints();

    // setters
    void setLevel(int level);
    void setDropMultipiler(float dropMultipiler);
    void setLevelPoints(int levelPoints);

}
