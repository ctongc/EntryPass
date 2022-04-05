package datastructures;

import java.util.HashMap;
import java.util.Map;

/**
 * each node contains a <key, value> pair, and it is also a doubly linked list node
 */
class LRUNode<K, V> {
    LRUNode<K, V> next;
    LRUNode<K, V> prev;
    K key;
    V value;

    LRUNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    void update(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

/**
 * Least recently used cache that provides set() and get() operation
 * return null if not exists
 */
public class LRUCache<K, V> {
    // record the head and tail of the doubly linked list all the time
    private LRUNode<K, V> head;
    private LRUNode<K, V> tail;

    // maintains the relationship of key and its corresponding node
    // in the doubly linked list, need to be updated in every action
    private final Map<K, LRUNode<K, V>> map;

    // limit is the max capacity of the cache
    private final int limit;

    public LRUCache(int limit) {
        this.limit = limit;
        this.map = new HashMap<>();
    }

    public void set(K key, V value) {
        LRUNode<K, V> node = map.get(key);
        if (node != null) {
            // 1. if the key already in the cache, we need to update its value
            //    and move it to head (most recent position)
            node.value = value;
            remove(node);
        } else if (map.size() < limit) {
            // 2. if the key is not in the cache, and we still have space,
            //    we can put a new node to head
            node = new LRUNode<>(key, value);
        } else {
            // 3. if the key is not in the cache, and we don't have space,
            //    we need to remove the tail and reuse the node let it maintain
            //    the new <Key, Value> and put it to head
            node = tail;
            remove(node);
            node.update(key, value);
        }

        bringAhead(node);
    }

    public V get(K key) {
        LRUNode<K, V> node = map.get(key);
        if (node == null) {
            return null;
        }

        // even it is a read operation to the node, it is still a write operation to
        // the doubly linked list, so we need to move the node to head
        remove(node);
        bringAhead(node);

        return node.value;
    }

    private void bringAhead(LRUNode<K, V> node) {
        // update map!
        map.put(node.key, node);

        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
    }

    /**
     * remove the node from the Doubly linked list
     * but not eliminate the node
     */
    private LRUNode<K, V> remove(LRUNode<K, V> LRUNode) {
        // update map!
        map.remove(LRUNode.key);

        if (LRUNode.prev != null) {
            LRUNode.prev.next = LRUNode.next;
        }
        if (LRUNode.next != null) {
            LRUNode.next.prev = LRUNode.prev;
        }
        if (head == LRUNode) {
            head = LRUNode.next;
        }
        if (tail == LRUNode) {
            tail = LRUNode.prev;
        }
        LRUNode.prev = null;
        LRUNode.next = null;

        return LRUNode;
    }
}