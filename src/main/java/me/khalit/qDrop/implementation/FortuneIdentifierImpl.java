package me.khalit.qDrop.implementation;

import me.khalit.qDrop.implementation.interfaces.FortuneIdentifier;

import java.util.HashMap;
import java.util.List;

/**
 * Created by KHaliT on 18.08.2016.
 */
public class FortuneIdentifierImpl implements FortuneIdentifier {

    private List<HashMap<Integer, Integer>> fortune;

    public void add(int fortuneLevel, int multipiler) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(fortuneLevel, multipiler);
        fortune.add(map);
    }

    public void set(List<HashMap<Integer, Integer>> list) {
        fortune = list;
    }

    @Override
    public List<HashMap<Integer, Integer>> getList() {
        return fortune;
    }
}
