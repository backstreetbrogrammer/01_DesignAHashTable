package com.backstreetbrogrammer;

import com.google.common.base.Preconditions;

import java.util.ArrayList;

public class CustomHashTableUsingLinkedList<K, V> implements CustomHashTable<K, V> {

    // Doubly LinkedList
    private static class LinkedListNode<K, V> {
        public LinkedListNode<K, V> next;
        public LinkedListNode<K, V> prev;
        public K key;
        public V value;

        public LinkedListNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private ArrayList<LinkedListNode<K, V>> arr;

    public CustomHashTableUsingLinkedList(int capacity) {
        arr = new ArrayList<>();
        arr.ensureCapacity(capacity);
        for (int i = 0; i < capacity; i++) {
            arr.add(null);
        }
    }

    @Override
    public V get(K key) {
        Preconditions.checkArgument(key != null, "Key must not be null");
        LinkedListNode<K, V> node = getNodeForKey(key);
        V value = null;
        if (node == null) {
            // may be indexed using linear probing - will always be head
            for (LinkedListNode<K, V> lln : arr) {
                if (lln != null && lln.key == key) {
                    value = lln.value;
                    break;
                }
            }
        } else {
            value = node.value;
        }
        return value;
    }

    @Override
    public void put(K key, V value) {
        Preconditions.checkArgument(key != null, "Key must not be null");
        LinkedListNode<K, V> node = getNodeForKey(key);
        if (node != null) { // already there
            node.value = value; // just update the value
            return;
        }

        node = new LinkedListNode<>(key, value);
        int index = getIndexForKey(key);
        if (arr.get(index) != null) { // collision
            int n = getAvailableIndexAfterLinearProbing();
            if (n == -1) { // no available index
                node.next = arr.get(index);
                node.next.prev = node;
            } else {
                index = n;
            }
        }
        arr.set(index, node);
    }

    private int getAvailableIndexAfterLinearProbing() {
        int idx = -1;
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) == null) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    @Override
    public void remove(K key) {
        Preconditions.checkArgument(key != null, "Key must not be null");
        LinkedListNode<K, V> node = getNodeForKey(key);
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            // Removing head - update
            int hashKey = getIndexForKey(key);
            arr.set(hashKey, node.next);
        }

        if (node.next != null) { // not a tail
            node.next.prev = node.prev;
        }
    }

    private LinkedListNode<K, V> getNodeForKey(K key) {
        int index = getIndexForKey(key);
        LinkedListNode<K, V> current = arr.get(index);
        while (current != null) {
            if (current.key == key) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    private int getIndexForKey(K key) {
        // mask off the sign bit - turn 32-bit number into a 31-bit positive integer
        return (key.hashCode() & 0x7fffffff) % arr.size();
    }

}
