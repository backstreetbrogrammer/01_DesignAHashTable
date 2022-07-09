package com.backstreetbrogrammer.basic;

import com.backstreetbrogrammer.CustomHashTable;
import com.google.common.base.Preconditions;

import java.util.ArrayList;

public class CustomHashTableUsingBinarySearchTree<K extends Comparable<K>, V> implements CustomHashTable<K, V> {

    // Binary Search Tree
    private static class BinarySearchTreeNode<K extends Comparable<K>, V> {
        public BinarySearchTreeNode<K, V> left;
        public BinarySearchTreeNode<K, V> right;
        public K key;
        public V value;

        public BinarySearchTreeNode(final K key, final V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final ArrayList<BinarySearchTreeNode<K, V>> arr;

    public CustomHashTableUsingBinarySearchTree(final int capacity) {
        arr = new ArrayList<>();
        arr.ensureCapacity(capacity);
        for (int i = 0; i < capacity; i++) {
            arr.add(null);
        }
    }

    @Override
    public V get(final K key) {
        Preconditions.checkArgument(key != null, "Key must not be null");

        final BinarySearchTreeNode<K, V> node = getNodeForKey(key);
        V value = null;
        if (node == null) {
            // may be indexed using linear probing - will always be head
            for (final BinarySearchTreeNode<K, V> bstn : arr) {
                if ((bstn != null) && (bstn.key.compareTo(key) == 0)) {
                    value = bstn.value;
                    break;
                }
            }
        } else {
            value = node.value;
        }
        return value;
    }

    @Override
    public void put(final K key, final V value) {
        Preconditions.checkArgument(key != null, "Key must not be null");
        Preconditions.checkArgument(value != null, "Value must not be null");

        BinarySearchTreeNode<K, V> node = getNodeForKey(key);
        if (node != null) { // already there
            node.value = value; // just update the value
            return;
        }

        node = new BinarySearchTreeNode<>(key, value);
        int index = getIndexForKey(key);
        if (arr.get(index) != null) { // collision
            final int n = getAvailableIndexAfterLinearProbing();
            if (n == -1) { // no available index
                BinarySearchTreeNode<K, V> root = arr.get(index);
                root = put(key, value, root); // insert into subtree
                arr.set(index, root);
            } else {
                index = n;
                arr.set(index, node); // insert at head
            }
        } else {
            arr.set(index, node);
        }
    }

    @Override
    public void remove(final K key) {
        Preconditions.checkArgument(key != null, "Key must not be null");

        final BinarySearchTreeNode<K, V> node = getNodeForKey(key);
        if (node != null) {
            // find the root node
            final int idx = getIndexForKey(key);
            BinarySearchTreeNode<K, V> root = arr.get(idx);
            root = remove(key, root);
            arr.set(idx, root);
        }
    }

    private BinarySearchTreeNode<K, V> remove(final K key, BinarySearchTreeNode<K, V> node) {
        if (node == null) {
            return null;
        }

        final int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(key, node.left);
        } else if (cmp > 0) {
            node.right = remove(key, node.right);
        } else {
            if (node.right == null) {
                return node.left;
            }
            if (node.left == null) {
                return node.right;
            }
            final BinarySearchTreeNode<K, V> tmp = node;
            node = min(tmp.right);
            node.right = removeMin(tmp.right);
            node.left = tmp.left;
        }

        return node;
    }

    private BinarySearchTreeNode<K, V> removeMin(final BinarySearchTreeNode<K, V> node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = removeMin(node.left);

        return node;
    }

    private BinarySearchTreeNode<K, V> min(final BinarySearchTreeNode<K, V> node) {
        if (node.left == null) {
            return node;
        }

        return min(node.left);
    }

    private BinarySearchTreeNode<K, V> put(final K key, final V value, final BinarySearchTreeNode<K, V> node) {
        if (node == null) {
            return new BinarySearchTreeNode<>(key, value);
        }

        final int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(key, value, node.left);
        } else if (cmp > 0) {
            node.right = put(key, value, node.right);
        } else {
            node.value = value;
        }

        return node;
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

    private BinarySearchTreeNode<K, V> getNodeForKey(final K key) {
        final int index = getIndexForKey(key);
        final BinarySearchTreeNode<K, V> current = arr.get(index);
        return (current == null) ? null : getNodeForKey(key, current);
    }

    private BinarySearchTreeNode<K, V> getNodeForKey(final K key, final BinarySearchTreeNode<K, V> node) { // collision
        if (node == null) {
            return null;
        }

        final int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return getNodeForKey(key, node.left);
        } else if (cmp > 0) {
            return getNodeForKey(key, node.right);
        } else {
            return node;
        }
    }

    private int getIndexForKey(final K key) {
        // mask off the sign bit - turn 32-bit number into a 31-bit positive integer
        return (key.hashCode() & 0x7fffffff) % arr.size();
    }

}
