package com.backstreetbrogrammer.lru;

import com.backstreetbrogrammer.CustomHashTable;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheUsingLinkedHashMap<K, V> implements CustomHashTable<K, V> {
    private final LinkedHashMap<K, V> hashTable;

    public LRUCacheUsingLinkedHashMap(final int capacity) {
        this.hashTable = new LinkedHashMap<>(capacity, 1.0f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return this.size() > capacity;
            }
        };
    }

    @Override
    public V get(K key) {
        return hashTable.get(key);
    }

    @Override
    public void put(K key, V value) {
        // don't update if key is already present
        hashTable.putIfAbsent(key, value);
    }

    @Override
    public void remove(K key) {
        hashTable.remove(key);
    }
}
