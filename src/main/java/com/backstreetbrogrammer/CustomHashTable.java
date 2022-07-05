package com.backstreetbrogrammer;

public interface CustomHashTable<K,V> {

    V get(K key);

    void put(K key, V value);

    void remove(K key);

}
