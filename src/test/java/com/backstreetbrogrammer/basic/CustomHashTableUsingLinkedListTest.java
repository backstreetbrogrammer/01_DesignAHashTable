package com.backstreetbrogrammer.basic;

import com.backstreetbrogrammer.CustomHashTable;
import com.backstreetbrogrammer.basic.CustomHashTableUsingLinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class CustomHashTableUsingLinkedListTest {

    private CustomHashTable<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new CustomHashTableUsingLinkedList<>(5);
    }

    @Test
    @DisplayName("When the key is null, then throw exception for get()")
    void whenKeyIsNull_thenThrowExceptionForGetMethod() {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> hashTable.get(null));
        assertEquals(exception.getMessage(), "Key must not be null");
    }

    @Test
    @DisplayName("When the key is null, then throw exception for put()")
    void whenKeyIsNull_thenThrowExceptionForPutMethod() {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> hashTable.put(null, 5));
        assertEquals(exception.getMessage(), "Key must not be null");
    }

    @Test
    @DisplayName("When the key is null, then throw exception for remove()")
    void whenKeyIsNull_thenThrowExceptionForRemoveMethod() {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> hashTable.remove(null));
        assertEquals(exception.getMessage(), "Key must not be null");
    }

    @Test
    @DisplayName("When the Value is null, then throw exception for put()")
    void whenValueIsNull_thenThrowExceptionForPutMethod() {
        final Throwable exception = assertThrows(IllegalArgumentException.class, () -> hashTable.put("Rishi", null));
        assertEquals(exception.getMessage(), "Value must not be null");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Rishi", "John", "Bob", "Malcolm", "Joshua"})
    @DisplayName("Test put() and get() methods with one input at a time")
    void testPutAndGetMethodsWithOneInputAtATime(String input) {
        hashTable.put(input, input.length());
        int value = hashTable.get(input);
        assertEquals(input.length(), value);
    }

    @Test
    @DisplayName("Test put() and get() methods with multiple inputs")
    void testPutAndGetMethodsWithMultipleInputs() {
        final String[] inputs = new String[]{"Rishi", "John", "Bob", "Malcolm", "Joshua"};
        for (String input : inputs) {
            hashTable.put(input, input.length());
            int value = hashTable.get(input);
            assertEquals(input.length(), value);
        }
    }

    @Test
    @DisplayName("Test put() and remove() methods with multiple inputs")
    void testPutAndRemoveMethodsWithMultipleInputs() {
        final String[] inputs = new String[]{"Rishi", "John", "Bob", "Malcolm", "Joshua", "Christy"};
        for (String input : inputs) {
            hashTable.put(input, input.length());
            int value = hashTable.get(input);
            assertEquals(input.length(), value);
        }
        hashTable.remove("Bob");
        assertNull(hashTable.get("Bob"));
    }

    @Test
    @DisplayName("Test remove() method from head in collided index")
    void testRemoveMethodFromHead() {
        final String[] inputs = new String[]{"Rishi", "John", "Bob", "Malcolm", "Joshua", "Christy"};
        for (String input : inputs) {
            hashTable.put(input, input.length());
            int value = hashTable.get(input);
            assertEquals(input.length(), value);
        }
        hashTable.remove("Christy");
        assertNull(hashTable.get("Christy"));
    }

    @Test
    @DisplayName("Test remove() method from tail in collided index")
    void testRemoveMethodFromTail() {
        final String[] inputs = new String[]{"Rishi", "John", "Bob", "Malcolm", "Joshua", "Christy"};
        for (String input : inputs) {
            hashTable.put(input, input.length());
            int value = hashTable.get(input);
            assertEquals(input.length(), value);
        }
        hashTable.remove("Bob");
        assertNull(hashTable.get("Bob"));
    }
}
