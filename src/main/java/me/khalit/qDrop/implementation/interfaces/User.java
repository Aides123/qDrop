package me.khalit.qDrop.implementation.interfaces;

import java.util.List;
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

    // non-saveable
    List<Drop> getDisabledDrops();

    // setters
    void setLevel(int level);
    void setDropMultipiler(float dropMultipiler);
    void setLevelPoints(int levelPoints);
    void setDisabledDrops(List<Drop> drops);
    void addDisabledDrop(Drop drop);
    void removeDisabledDrop(Drop drop);

    void update();
    void insert();
}
