package me.khalit.qDrop;

import me.khalit.qDrop.implementation.interfaces.Drop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KHaliT on 18.08.2016.
 */
public class DropAssembly {

    private static final List<Drop> drops = new ArrayList<>();

    public static List<Drop> getDrops() {
        return drops;
    }

    public static void add(Drop drop) {
        drops.add(drop);
    }

    public static void remove(Drop drop) {
        if (drops.contains(drop))
            drops.add(drop);
    }
}
