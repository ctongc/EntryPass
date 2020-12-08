package algorithms;

import java.util.*;

/**
 * A hashtable implementation of map, demonstration purpose, generic type is provided.
 * supported operations:
 * size(), isEmpty(), clear(), put(K key, V value), get(K key)
 * containsKey(K key), containsValue(V value) // check if the desired value is in the map. O(n)
 * remove(K key) - 麻烦 不提别写了
 */
class MyHashMap<K, V> {
    public static class MyEntry<K, V> { // if static, I can create an entry although there's no instance of hashmap
        private final K key;
        private V value;
        private MyEntry<K, V> next;

        MyEntry(K key, V value) { // don't need generic <K, V> here
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }
        public V getValue() {
            return value;
        }
        public void setValue(V value) {
            this.value = value;
        }
    }

    public static final int DEFAULT_CAPACITY = 16;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private MyEntry<K, V>[] array; // the bucket
    private int size; // how many key-value pairs are actually stored in the HashMap
    private float loadFactor; // determine when to rehash

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int cap, float loadFactor) {
        if (cap <= 0) {
            throw new IllegalArgumentException("cap can not be <= 0");
        }
        this.array = (MyEntry<K, V>[]) new MyEntry[cap]; // 注意array创建的时候不能带有generic new MyEntry<K, V>[cap]
        this.size = 0;
        this.loadFactor = loadFactor;
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

    // return the hash# of the key, non-negative
    private int hash(K key) {
        // null key
        if (key == null) {
            return 0;
        }
        int hashNumber = key.hashCode();
        // postprocessing to make the hashNumber non-negative
        // return hashNumber >= 0 ? hashNumber : -hashNumber; might overflow since int = [-2^31, 2^31 - 1]
        // java's % return remainder rather than modules, the remainder can be negative
        return hashNumber & 0x7FFFFFFF; // guarantee non-negative 01111111 1...1 1...1 1...1
    }

    private int getIndex(K key) {
        // return the corresponding index of array
        return hash(key) % array.length;
    }

    private boolean equalsKey(K k1, K k2) {
        // k1, k2 all possibly to be null
        if (k1 == null && k2 == null) {
            return true;
        }
        if (k1 == null || k2 == null) {
            return false;
        }
        return k1.equals(k2);
    }

    private boolean equalsValue(V v1, V v2) { // overload必须parameter个数不一样！！！！
        // same as equalsKey, but actually can be written as
        return v1 == v2 || v1 != null && v1.equals(v2);
    }

    public boolean containsKey(K key) {
        // get the index of the key
        int index = getIndex(key);
        MyEntry<K, V> entry = array[index];
        while (entry != null) {
            // check if the key equals
            // entry.key and key both possibly to be null
            if (equalsKey(entry.getKey(), key)) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    public boolean containsValue(V value) {
        // special case
        if(isEmpty()) {
           return false;
        }
        for (MyEntry<K, V> entry : array) {
            while (entry != null) {
                // check if the value equals
                // entry.value and value both possibly to be null
                if (equalsValue(entry.getValue(), value)) {
                    return true;
                }
                entry = entry.next;
            }

        }
        return false;
    }

    // if the key does not exists in the HashMap, return null
    public V get(K key) {
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

    // insert/update
    // if the key already exists in the HashMap, return the old corresponding value
    // if key does not exists in the HashMap, return null
    public V put(K key, V value) {
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

    private boolean needRehashing() {
        return size >= loadFactor * array.length;
    }

    private void rehashing() {
        // new double size array
        // for each node in the old array: do the put() operation to the the new larger array
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

    // if the key exists, remove the <key, value> from the HashMap adn return the value
    // if key does not exists in the HashMap, return null
    private V remove(K key) {
        // get index
        // delete operation on the linked list
        // size--
        int index = getIndex(key);
        MyEntry<K, V> pre = array[index];
        MyEntry<K, V> entry = array[index];
        while(entry != null) {
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
}

// It is backed up by a HashMap instance
class MyHashSet<K> {
    private MyHashMap<K, Object> map;
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

class TopKComparator implements Comparator<Map.Entry<String, Integer>> {

    @Override
    public int compare(Map.Entry<String, Integer> s1, Map.Entry<String, Integer> s2) {
        if (s1.getValue() == s2.getValue()) {
            return 0;
        }
        return s1.getValue() < s2.getValue()? -1 : 1;
    }
}

public class HashTableBasics {

    private HashTableBasics() {
    }

    /**
     * Top K Frequent Words
     * Given a composition with different kinds of words
     * return a list of the top K most frequent words in the composition.
     *
     * Time = O(nlogk) if we don't use heapify
     * Space = O(n)
     */
    public String[] topKFrequent(String[] combo, int k) {
        if (combo == null || combo.length == 0) {
            return new String[0];
        }
        Map<String, Integer> lookup = new HashMap<>();
        for (String s : combo) {
            lookup.put(s, lookup.getOrDefault(s, 0) + 1);
        }
        // elements in the minHeap is the answer
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(k, new TopKComparator());
        for (Map.Entry<String, Integer> e : lookup.entrySet()) {
            if (minHeap.size() < k) {
                minHeap.offer(e);
            } else if (e.getValue() > minHeap.peek().getValue()) {
                minHeap.poll();
                minHeap.offer(e);
            }
        }
        String[] result = new String[minHeap.size()];
        for(int i = minHeap.size() - 1; i >= 0; i--) {
            result[i] = minHeap.poll().getKey();
        }
        return result;
    }

    /**
     * Missing Number I
     * Given an integer array of size N - 1, containing all the numbers from 1 to N except one, find the missing number.
     * Using Hashset
     * Time =  O(n) in average
     * Space = O(n)
     */
    public int findMissingNumber(int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }
        Set<Integer> set = new HashSet<>();
        for (int i : array) {
            set.add(i);
        }
        int n = array.length + 1;
        // i <= n --> i < n so return n after for loop
        for (int i = 1; i < n; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }
        return n;
    }

    /**
     * Using boolean array
     * Time =  O(n)
     * Space = O(n)
     */
    public int findMissingNumber2(int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }
        boolean[] ifExist = new boolean[array.length + 1];
        for (int i : array) {
            ifExist[i - 1] = true;
        }
        for (int i = 0; i < ifExist.length; i++) {
            if (!ifExist[i]) {
                return i + 1;
            }
        }
        return 0;
    }

    /**
     * Using sum
     * Time =  O(n)
     * Space = O(1)
     */
    public int findMissingNumber3(int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }
        int realSum = 0;
        for (int i : array) {
            realSum += i;
        }
        int n = array.length + 1;
        int expectedSum = (1 + n) * n / 2;
        return expectedSum - realSum;
    }

    /**
     * Using bit operation
     * 0 ^ x = x, x ^ x = 0
     * Time: O(n)
     * Space: O(1)
     */
    public int findMissingNumber4(int[] array) {
        if (array == null || array.length == 0) {
            return 1;
        }
        int xorResult = 0;
        // num1 XOR num2 XOR … XOR numn => xorResult
        for (int i : array) {
            xorResult ^= i;
        }
        // xorResult XOR 1 XOR 2 XOR … XOR n => final result
        int n = array.length + 1;
        for (int i = 1; i <= n; i++) {
            xorResult ^= i;
        }
        return xorResult;
    }

    /**
     * Common Numbers Of Two Sorted Arrays
     * Find all numbers that appear in both of two sorted arrays (the two arrays are all sorted in ascending order).
     * using Hashset
     *
     * Time = O(max(m, n)
     * Space = O(min(m, n))
     */
    public List<Integer> findCommonNumbers(List<Integer> A, List<Integer> B) {
        // assumption: 1 both list are not null
        // 2 there could be duplicated elements
        List<Integer> list = new ArrayList<>();
        if (A.isEmpty() || B.isEmpty()) {
            return list;
        }
        Map<Integer, Integer> mapA = new HashMap<>();
        for (int i : A) {
            mapA.put(i, mapA.getOrDefault(i, 0) + 1);
        }
        Map<Integer, Integer> mapB = new HashMap<>();
        for (int i : B) {
            mapB.put(i, mapB.getOrDefault(i, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : mapA.entrySet()) {
            // if (mapB.containsKey(entry.getKey())) {
            //    int appear = Math.min(mapB.get(entry.getKey()), entry.getValue());
            //    for(int i = 0; i < appear; i++) {
            //        list.add(entry.getValue());
            //    }
            //}
            // above will do two look ups: mapB.containsKey() and mapB.get()
            // below will do only one look up
            Integer countInMapB = mapB.get(entry.getKey());
            if (countInMapB != null) {
                int appear = Math.min(countInMapB, entry.getValue());
                for (int i = 0; i < appear; i++) {
                    list.add(entry.getKey());
                }
            }
        }
        return list;
    }

    /**
     * Using double pointer
     * 当 O(m) == O(n) 时，选用此方法
     * Time O(m + n)
     * Space O(1)
     */
    public List<Integer> findCommonNumbers2(List<Integer> A, List<Integer> B) {
        // assumption: 1 both list are not null
        // 2 there could be duplicated elements
        List<Integer> list = new ArrayList<>();
        if (A.isEmpty() || B.isEmpty()) {
            return list;
        }
        int indexA = 0;
        int indexB = 0;
        while (indexA < A.size() && indexB < B.size()) {
            if (A.get(indexA) < B.get(indexB)) {
                indexA++;
            } else if (A.get(indexA) > B.get(indexB)) {
                indexB++;
            } else {
                // A.get(indexA) == B.get(indexB)
                list.add(A.get(indexA));
                indexA++;
                indexB++;
            }
        }
        // indexA == A.size() || indexB == B.size() then no common numbers left
        return list;
    }

    /**
     * Binary search
     * 当 m <<< n时 用此方法 因为此时 O(mlogn) < O(m + n)
     * Time = O(m log n)
     * Space = O(1)
     */
    public List<Integer> findCommonNumbers3(List<Integer> A, List<Integer> B) {
        // assumption: 1 both list are not null
        // 2 there could be duplicated elements
        List<Integer> list = new ArrayList<>();
        if (A.isEmpty() || B.isEmpty()) {
            return list;
        }
        // Micro-optimization: if size of a > size of b, swap(a, b)
        for (int i : B) {
            int find = binarySearch(A, i);
            if (find != -1) {
                // find i in A
                list.add(i);
                A.remove(new Integer(i)); // remove new Integer(i) from a in case duplicated
            }
        }
        return list;
    }

    private int binarySearch(List<Integer> list, int target) {
        int left = 0;
        int right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) == target) return target;
            else if (list.get(mid) > target) right = mid - 1;
            else left = mid + 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        HashTableBasics solution = new HashTableBasics();
        String[] s = new String[]{"d","a","c","b","d","a","b","b","a","d","d","a","d"};
        solution.topKFrequent(s,3);
        int[] array = {1, 2, 3, 4, 6};
        System.out.println(solution.findMissingNumber(array));
        System.out.println(solution.findMissingNumber2(array));
        System.out.println(solution.findMissingNumber3(array));
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        int[] array1 = {1, 1, 2, 2, 3, 8, 9, 9};
        int[] array2 = {1, 1, 2, 8, 8, 9};
        for (int i : array1) {
            list1.add(i);
        }
        for (int i : array2) {
            list2.add(i);
        }
        System.out.println(solution.findCommonNumbers3(list1, list2).toString());
    }
}
