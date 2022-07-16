package com.backstreetbrogrammer.lru;

import com.backstreetbrogrammer.CustomHashTable;
import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;


/**
 * LRU cache algorithm
 * <p>
 * 1. Inserting key,value pair: Create a new LinkedList node with key, value and insert into head of linked list.
 * Insert key -> node mapping into hash table.
 * <p>
 * 2. Get value by key: Lookup node in hash table and return node value.
 * Then update most recently used item by moving the node to front (head) of linked list.
 * Hash table does NOT need to be updated.
 * <p>
 * 3. Finding least recently used: Least recently used item will be found at the end of the linked list.
 * <p>
 * 4. Eviction: Remove tail of linked list. Get key from linked list node and remove key from hash table.
 *
 * @param <K>
 * @param <V>
 */
public class LRUCacheUsingLinkedListAndHashTable<K, V> implements CustomHashTable<K, V> {

    // Doubly LinkedList
    private static class LinkedListNode<K, V> {
        public LinkedListNode<K, V> next;
        public LinkedListNode<K, V> prev;
        public K key;
        public V value;

        public LinkedListNode(final K key, final V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private Map<K, LinkedListNode<K, V>> hashTable = new HashMap<>();
    private LinkedListNode<K, V> listHeadNode, listTailNode;

    public LRUCacheUsingLinkedListAndHashTable(final int capacity) {
        this.capacity = capacity;
    }


    @Override
    public V get(final K key) {
        Preconditions.checkArgument(key != null, "Key must not be null");

        final LinkedListNode<K, V> node = hashTable.get(key);
        if (node == null) {
            return null;
        }

        // Move to front of linked list to mark as most recently used
        if (node != listHeadNode) {
            removeFromLinkedList(node);
            insertAtFrontOfLinkedList(node);
        }

        return node.value;
    }

    @Override
    public void put(final K key, final V value) {
        Preconditions.checkArgument(key != null, "Key must not be null");
        Preconditions.checkArgument(value != null, "Value must not be null");

        // remove if key is already present
        remove(key);

        // if full, remove least recently used item from cache
        if (hashTable.size() >= capacity && listTailNode != null) {
            remove(listTailNode.key);
        }

        // insert new node
        LinkedListNode<K, V> node = new LinkedListNode<>(key, value);
        insertAtFrontOfLinkedList(node);
        hashTable.put(key, node);
    }

    @Override
    public void remove(final K key) {
        Preconditions.checkArgument(key != null, "Key must not be null");

        final LinkedListNode<K, V> node = hashTable.get(key);
        removeFromLinkedList(node);
        hashTable.remove(key);
    }

    private void removeFromLinkedList(final LinkedListNode<K, V> node) {
        if (node == null) {
            return;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }
        if (node == listTailNode) {
            listTailNode = node.prev;
        }
        if (node == listHeadNode) {
            listHeadNode = node.next;
        }
    }

    private void insertAtFrontOfLinkedList(final LinkedListNode<K, V> node) {
        if (listHeadNode == null) {
            listHeadNode = node;
            listTailNode = node;
        } else {
            listHeadNode.prev = node;
            node.next = listHeadNode;
            listHeadNode = node;
        }
    }
}
