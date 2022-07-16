package com.backstreetbrogrammer.lru;

import com.backstreetbrogrammer.CustomHashTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LRUCacheUsingLinkedListAndHashTableTest {

    private CustomHashTable<String, Integer> lruCache;

    @BeforeEach
    void setUp() {
        lruCache = new LRUCacheUsingLinkedListAndHashTable<>(5);
    }

    @Test
    @DisplayName("When the key is null, then throw exception for get()")
    void whenKeyIsNull_thenThrowExceptionForGetMethod() {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> lruCache.get(null));
        assertEquals(exception.getMessage(), "Key must not be null");
    }

    @Test
    @DisplayName("When the key is null, then throw exception for put()")
    void whenKeyIsNull_thenThrowExceptionForPutMethod() {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> lruCache.put(null, 5));
        assertEquals(exception.getMessage(), "Key must not be null");
    }

    @Test
    @DisplayName("When the key is null, then throw exception for remove()")
    void whenKeyIsNull_thenThrowExceptionForRemoveMethod() {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> lruCache.remove(null));
        assertEquals(exception.getMessage(), "Key must not be null");
    }

    @Test
    @DisplayName("When the Value is null, then throw exception for put()")
    void whenValueIsNull_thenThrowExceptionForPutMethod() {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> lruCache.put("Rishi", null));
        assertEquals(exception.getMessage(), "Value must not be null");
    }

    // TODO: write more tests specific to LRU eviction
}
