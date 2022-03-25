package crosstraining;

import java.util.*;

public class C4BinarySearchSlidingWindow {
    /**
     * K-th smallest in Two Sorted Arrays
     * Given two sorted arrays of integers, find the Kth-smallest number.
     *
     * Time = O(loga + logb) // a = a.length, b = b.length
     * Space = O(loga)
     */
    public int kthSmallestInTwoSortedArrays(int[] a, int[] b, int k) {
        /* Assumptions
         * 1. The two given arrays are not null and at least one of them is not empty
         * 2. 1 <= k <= total lengths of the two sorted arrays */
        return findKthSmallest(a, 0, b, 0, k);
    }
    /**
     * In the sub-array of a starting from index aLeft, and
     * sub-array of b starting from index bLeft, find the
     * kth-smallest among these two sub-arrays */
    private int findKthSmallest(int[] a, int aLeft, int[] b, int bLeft, int k) {
        /* three base cases:
         * 1. we already eliminate all the elements in a
         * 2. we already eliminate all the elements in b
         * 3. when k is reduced to 1, don't miss this base case
         * the reason why we have this as base case is in the following
         * logic we need k >= 2 to make it work */
        if (aLeft >= a.length) {
            return b[bLeft + k - 1];
        }
        if (bLeft >= b.length) {
            return a[aLeft + k - 1];
        }
        if (k == 1) {
            return Math.min(a[aLeft], b[bLeft]);
        }
        /* we compare the k/2-th element in a's sub-array
         * and the k/2-th element in b's sub-array
         * to determine which k/2 partition can be surely included
         * in the smallest k elements */
        int aMid = aLeft + k / 2 - 1;
        int bMid = bLeft + k / 2 - 1;
        int aVal = aMid >= a.length ? Integer.MIN_VALUE : a[aMid];
        int bVal = bMid >= b.length ? Integer.MIN_VALUE : b[aMid];
        if (aVal <= bVal) {
            return findKthSmallest(a, aMid + 1, b, bLeft, k - k / 2);
        } else {
            return findKthSmallest(a, aLeft, b, bMid + 1, k - k / 2);
        }
    }

    /**
     * Maximum Values Of Size K Sliding Windows
     * Given an integer array A and a sliding window of size K, find the maximum value
     * of each window as it slides from left to right.
     *
     * e.g. A = {1, 2, 3, 2, 4, 2, 1}, K = 3
     * the windows are {{1,2,3}, {2,3,2}, {3,2,4}, {2,4,2}, {4,2,1}},
     * and the maximum values of each K-sized sliding window are [3, 3, 4, 4, 4]
     *
     * Time = O(n)
     * Space = (k)
     */
    public List<Integer> maxWindows(int[] array, int k) {
        // assume k >= 1, k <= array.length
        if (array == null || array.length == 0) {
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList<>();
        Deque<Integer> maxIndex = new ArrayDeque<>(k); // descending, peekFirst() is the max value's index

        for (int i = 0; i < array.length; i++) {
            // deal with new incoming element
            while (!maxIndex.isEmpty() && array[i] >= array[maxIndex.peekLast()]) {
                maxIndex.pollLast();
            }
            maxIndex.offerLast(i);

            // deal with head value is out of the sliding window
            if (maxIndex.peekFirst() == i - k) {
                maxIndex.pollFirst();
            }

            // update result
            if (i >= k - 1) {
                result.add(array[maxIndex.peekFirst()]);
            }
        }
        return result;
    }

    /**
     * First Non-Repeating Character In Stream
     * Given a stream of characters, find the first non-repeating character from stream.
     * You need to tell the first non-repeating character in O(1) time at any moment.
     *
     * Implement two methods of the class:
     * read() - read one character from the stream
     * firstNonRepeating() - return the first non-repeating character from the stream
     * at any time when calling the method, return null if there does not exist such characters
     *
     * e.g. a   b   c   a   c   c    b
     * sol: a   a   a   b   b   b   null
     */
    public class FirstNonRepeatingCharacter {
        static class Node {
            Node prev;
            Node next;
            Character ch;

            Node(Character ch) {
                this.ch = ch;
            }
        }

        // record the head and tail of the list all the time
        // only the characters appearing just once will be in
        // the doubly linked list
        private Node head;
        private Node tail;

        // for any character, it could be in three state:
        // 1. not existed yet, it will not be singled Map or repeated set
        // 2. only exists once, it will be in singled Map and there will be
        //    a corresponding node in the list
        // 3. exists more than once, it will be in the repeated Set, and
        //    it will be removed from the list
        private final HashMap<Character, Node> nonRepeated;
        private final HashSet<Character> repeated;

        public FirstNonRepeatingCharacter() {
            // using sentinel node to eliminate some corner cases
            nonRepeated = new HashMap<>();
            repeated = new HashSet<>();
        }

        public void read(char ch) {
            // if the character already exists more than once, we just ignore
            if (repeated.contains(ch)) {
                return;
            }

            Node node = nonRepeated.get(ch);
            if (node == null) {
                // if the character appears for the first time,
                // it should be added to the list and added to
                // the nonRepeated
                node = new Node(ch);
                add(node);
            } else {
                // if the character is already in the nonRepeated map,
                // we should remove it from the map and list and put it into the repeated set
                remove(node);
            }
        }

        public Character firstNonRepeating() {
            if (head == null) {
                return null;
            }
            return head.ch;
        }

        private void add(Node node) {
            nonRepeated.put(node.ch, node);
            if (head == null) {
                head = node;
                tail = node;
                return;
            }

            tail.next = node;
            node.prev = tail;
            node.next = null;
            tail = tail.next;
        }

        private Node remove(Node node) {
            if (node.prev != null) {
                node.prev.next = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            }
            if (node == head) {
                head = node.next;
            }
            if (node == tail) {
                tail = node.prev;
            }
            node.prev = null;
            node.next = null;
            nonRepeated.remove(node.ch);
            repeated.add(node.ch);

            return node;
        }
    }

    public static void main(String[] args) {
        C4BinarySearchSlidingWindow ins = new C4BinarySearchSlidingWindow();
        ins.maxWindows(new int[]{1,2,3,4,5,6,7,8,9,1,1},2);
    }
}
