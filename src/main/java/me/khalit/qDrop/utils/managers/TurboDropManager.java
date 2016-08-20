package me.khalit.qDrop.utils.managers;

import me.khalit.qDrop.implementation.interfaces.TurboDrop;

/**
 * Created by KHaliT on 20.08.2016.
 */
public class TurboDropManager {

    private static TurboDrop currentTurboDrop;

    public static TurboDrop getCurrentTurboDrop() {
        return currentTurboDrop;
    }

    public static void setCurrentTurboDrop(TurboDrop turboDrop) {
        currentTurboDrop = turboDrop;
    }

}
