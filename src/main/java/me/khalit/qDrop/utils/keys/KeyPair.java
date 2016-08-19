package me.khalit.qDrop.utils.keys;

/**
 * Created by KHaliT on 19.08.2016.
 */
public class KeyPair<K, V> {

    private K key;
    private V value;

    public KeyPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public KeyPair() { }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void set(K key, V value) {
        this.key = key;
        this.value = value;
    }

}
