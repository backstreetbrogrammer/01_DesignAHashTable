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
        return node == null ? null : node.value;
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
            node.next = arr.get(index);
            node.next.prev = node;
        }
        arr.set(index, node);
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
        return Math.abs(key.hashCode() % arr.size());
    }

}
