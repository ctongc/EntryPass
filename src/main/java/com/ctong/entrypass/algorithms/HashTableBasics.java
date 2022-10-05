package com.ctong.entrypass.algorithms;

import java.util.*;

public class HashTableBasics {
    /**
     * Top K Frequent Words
     * Given a composition with different kinds of words, return a list of the top K
     * most frequent words in the composition.
     *
     * Time = O(nlogk) if we don't use heapify
     * Space = O(n)
     */
    public String[] topKFrequentWords(String[] combo, int k) {
        if (combo == null || combo.length == 0) {
            return new String[0];
        }

        Map<String, Integer> lookup = new HashMap<>();
        for (String s : combo) {
            lookup.put(s, lookup.getOrDefault(s, 0) + 1);
        }
        // elements in the minHeap is the answer
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(k,
                Comparator.comparingInt(Map.Entry::getValue));
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
     * Given an integer array of size N - 1, containing all the numbers from 1 to N
     * except one, find the missing number.
     *
     * Using Hashset
     * Time = O(n) in average
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
     * Using boolean array.
     *
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
     * Using sum.
     *
     * Time = O(n)
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
     * Using bit operation.
     * 0 ^ x = x, x ^ x = 0
     *
     * Time = O(n)
     * Space = O(1)
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
     * Find all numbers that appear in both of two sorted arrays (the two arrays are
     * all sorted in ascending order).
     * Using Hashset
     *
     * Time = O(max(m, n)
     * Space = O(min(m, n))
     */
    public List<Integer> findCommonNumbers(List<Integer> A, List<Integer> B) {
        /* Assumption: 1. both list are not null
         * 2. there could be duplicated elements */
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
            // }
            // above will do two look-ups: mapB.containsKey() and mapB.get()
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
     *
     * Time = O(m + n)
     * Space = O(1)
     */
    public List<Integer> findCommonNumbers2(List<Integer> A, List<Integer> B) {
        /* Assumption: 1. both list are not null
         * 2. there could be duplicated elements */
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
        /* Assumption: 1. both list are not null
         * 2. there could be duplicated elements */
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
                A.remove(Integer.valueOf(i)); // remove new Integer(i) from A in case duplicated
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
        String[] topK = solution.topKFrequentWords(s,3);
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
