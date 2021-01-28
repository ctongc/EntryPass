package datastructure;

import java.util.Arrays;
import java.util.Objects;

/**
 * A hashtable implementation of map, demonstration purpose, generic type is provided.
 * supported operations:
 * size(), isEmpty(), clear(), put(K key, V value), get(K key)
 * containsKey(K key), containsValue(V value) // check if the desired value is in the map. O(n)
 * remove(K key) - 麻烦 不提别写了
 */
class MyHashMap<K, V> {

    static class MyEntry<K, V> { // if static, I can create an entry although there's no instance of hashmap
        private final K key;
        private V value;
        private MyEntry<K, V> next;

        MyEntry(final K key, final V value) { // don't need generic <K, V> here
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(final V value) {
            this.value = value;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private MyEntry<K, V>[] array; // the bucket
    private int size; // how many key-value pairs are actually stored in the HashMap
    private final float loadFactor; // determine when to rehash

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR); // use this to call another constructor
    }

    public MyHashMap(final int cap, final float loadFactor) {
        if (cap <= 0) {
            throw new IllegalArgumentException("cap can not be <= 0");
        }
        this.array = (MyEntry<K, V>[]) new MyEntry[cap]; // 注意array创建的时候不能带有generic new MyEntry<K, V>[cap]
        this.size = 0;
        this.loadFactor = loadFactor;
    }

    public boolean containsKey(final K key) {
        // get the index of the key
        int index = getIndex(key);
        MyEntry<K, V> entry = array[index];
        while (entry != null) {
            // check if the key equals
            // note that entry.key and key both possibly to be null
            if (equalsKey(entry.getKey(), key)) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    public boolean containsValue(final V value) {
        if (isEmpty()) { // special case
            return false;
        }
        for (MyEntry<K, V> entry : array) {
            while (entry != null) {
                // check if the value equals
                // note that entry.value and value both possibly to be null
                if (equalsValue(entry.getValue(), value)) {
                    return true;
                }
                entry = entry.next;
            }

        }
        return false;
    }

    /* if the key does not exists in the HashMap, return null */
    public V get(final K key) {
        // same as containsKey
        // get the index of the key
        int index = getIndex(key);
        MyEntry<K, V> entry = array[index];
        while (entry != null) {
            // check if the key equals
            // entry.key and key both possibly to be null
            if (equalsKey(entry.getKey(), key)) {
                return entry.getValue();
            }
            entry = entry.next;
        }
        return null;
    }

    /* insert/update
     * if the key already exists in the HashMap, return the old corresponding value
     * if key does not exists in the HashMap, return null */
    public V put(final K key, final V value) {
        int index = getIndex(key);
        MyEntry<K, V> head = array[index];
        MyEntry<K, V> cur = head;
        // update
        while (cur != null) {
            // check if the key equals
            if (equalsKey(cur.getKey(), key)) {
                V oldValue = cur.getValue();
                cur.setValue(value);
                return oldValue;
            }
            cur = cur.next;
        }
        // otherwise, insert
        MyEntry<K, V> newEntry = new MyEntry<>(key, value);
        newEntry.next = head; // trick here, since head could be null
        array[index] = newEntry; // order matters!!
        size++; // don't forgot
        if (needRehashing()) {
            rehashing();
        }
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        Arrays.fill(array, null);
        size = 0;
    }

    /* return the hash# of the key, non-negative */
    private int hash(final K key) {
        // null key
        if (key == null) {
            return 0;
        }
        int hashNumber = key.hashCode();
        // return hashNumber >= 0 ? hashNumber : -hashNumber; might overflow since int = [-2^31, 2^31 - 1]
        // java's % return remainder rather than modules, the remainder can be negative
        return hashNumber & 0x7FFFFFFF; // guarantee non-negative 01111111 1...1 1...1 1...1
    }

    /* return the corresponding index of array */
    private int getIndex(final K key) {
        return hash(key) % array.length;
    }

    private boolean equalsKey(final K k1, final K k2) {
        // k1, k2 all possibly to be null
        if (k1 == null && k2 == null) {
            return true;
        }
        if (k1 == null || k2 == null) {
            return false;
        }
        return k1.equals(k2);
    }

    private boolean equalsValue(final V v1, final V v2) {
        // same as equalsKey, return (v1 == v2) || (v1 != null && v1.equals(v2));
        return Objects.equals(v1, v2);
    }

    private boolean needRehashing() {
        return size >= loadFactor * array.length;
    }

    private void rehashing() {
        // new double size array
        // for each node in the old array: do the put() operation to the the new larger array
        // hence need to iterate each node within all single linked list
        MyEntry<K, V>[] oldArray = array;
        array = (MyEntry<K, V>[]) new MyEntry[array.length * 2];
        for (MyEntry<K, V> entry : oldArray) {
            while (entry != null) {
                // put(entry.key, entry.value);
                MyEntry<K, V> nextEntry = entry.next;
                int newIndex = getIndex(entry.getKey());
                // array[newIndex] could have entry, or could be null, so same trick as put
                entry.next = array[newIndex];
                array[newIndex] = entry;
                entry = nextEntry;
            }
        }
    }

    /* if the key exists, remove the <key, value> from the HashMap and return the value
     * if key does not exists in the HashMap, return null */
    private V remove(final K key) {
        // 1. get index
        // 2. delete operation on the linked list
        // 3. size--
        int index = getIndex(key);
        MyEntry<K, V> pre = array[index];
        MyEntry<K, V> entry = array[index];
        while (entry != null) {
            if (equalsKey(entry.key, key)) {
                V value = entry.value;
                pre.next = entry.next;
                entry.next = null;
                size--;
                return value;
            }
            pre = entry;
            entry = entry.next;
        }
        return null;
    }

    /* It is backed up by a HashMap instance */
    static class MyHashSet<K> {
        private final MyHashMap<K, Object> map;
        // special object used for all the existing keys
        private static final Object PRESENT = new Object();

        public MyHashSet() {
            this.map = new MyHashMap<>();
        }

        public boolean contains(K key) {
            return map.containsKey(key);
        }

        public boolean add(K key) {
            return map.put(key, PRESENT) == null;
        }
    }
}
