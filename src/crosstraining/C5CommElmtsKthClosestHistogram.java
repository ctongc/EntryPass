package crosstraining;

import java.util.*;

public class C5CommElmtsKthClosestHistogram {
    public C5CommElmtsKthClosestHistogram() {
    }

    /**
     * Find all common elements in 3 sorted arrays.
     * Time = O(n)
     * Space = O(1)
     */
    public List<Integer> commonElements(int[] a, int[] b, int[] c) {
        // assumptions: a, b, c != null
        List<Integer> result = new ArrayList<>();
        int ai = 0; // pointer of a[], b[], c[]
        int bi = 0;
        int ci = 0;
        // move the smallest pointer to find the common
        while (ai < a.length && bi < b.length && ci < c.length) {
            if (a[ai] == b[bi] && b[bi] == c[ci]) {
                result.add(a[ai]);
                ai++;
                bi++;
                ci++;
            } else if (a[ai] <= b[bi] && a[ai] <= c[ci]) {
                ai++;
            } else if (b[bi] <= a[ai] && b[bi] <= c[ci]) {
                bi++;
            } else {
                ci++;
            }
        }
        return result;
    }

    /**
     * Largest Product Of Length
     * Given a dictionary containing many words, find the largest product of two words’ lengths,
     * such that the two words do not share any common characters.
     * e.g
     * dictionary = [“abcde”, “abcd”, “ade”, “xy”], the largest product is 5 * 2 = 10 (by choosing “abcde” and “xy”)
     * Time = O(n * (n + m)) // m is the average length of string
     */
    public int largestLengthProduct(String[] dict) {
        // assumptions:
        // 1 The words only contains characters of 'a' to 'z'
        // 2 The dictionary is not null and does not contains null string, and has at least two strings
        // 3 If there is no such pair of words, just return 0

        // algorithm:
        // iterate from the largest product pairs, check each pair if valid using bit mask
        Arrays.sort(dict, Comparator.reverseOrder());
        int[] bitMasks = new int[dict.length];
        for (int i = 0; i < dict.length; i++) {
            int bitMask = 0;
            String s = dict[i];
            for (int j = 0; j < s.length(); j++) {
                // the 26 characters 'a' to 'z' are mapped to 0 - 25th bit
                // use (char - 'a') since their values are in a consecutive range
                // if char exists in the word, we set the bit at corresponding index to 1
                bitMask |= 1 << (s.charAt(j) - 'a');
            }
            bitMasks[i] = bitMask;
        }
        int result = 0;
        for (int i = 1; i < dict.length; i++) {
            for (int j = 0; j < i; j++) {
                // check if valid
                if((bitMasks[i] & bitMasks[j]) == 0) {
                    // if two words do not share any common characters
                    // the bit masks "and" result should be 0 since
                    // there is no position such that in the two bit masks are all 1
                    result = Math.max(dict[i].length() * dict[j].length(), result);
                }
            }
        }
        return result;
    }

    /**
     * if use this to validate, the Time = O(n^2 * m)
     */
    private boolean isValid2(int p1, int p2, String[] dict) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < dict[p1].length(); i++) { // NOTE dict[p1].toCharArray needs O(n) time
            set.add(dict[p1].charAt(i));
        }
        for (int i = 0; i < dict[p2].length(); i++) {
            if (set.contains(dict[p2].charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Kth Smallest With Only 3, 5, 7 As Factors
     * Find the Kth smallest number s such that s = 3 ^ x 5 ^ y 7 ^ z
     * x > 0 and y > 0 and z > 0, x, y, z are all integers.
     * Time = O(klogk)
     * Space = O(k)
     */
    public long kthSmallestWith357(int k) {
        // assuption: k >= 1
        // algorithm: using best first searcher
        // maintain a minHeap then pop k times
        PriorityQueue<Long> minHeap = new PriorityQueue<>();
        Set<Long> visited = new HashSet<>(); // used to dedup
        minHeap.offer(3 * 5 * 7L);
        visited.add(3 * 5 * 7L);
        long result = 0;
        for (int i = 0; i < k; i++) {
            result = minHeap.poll();
            // for the state <x+1, y, z>, the actual value is *3
            if (visited.add(3 * result)) {
                minHeap.offer(3 * result);
            }
            if (visited.add(5 * result)) {
                minHeap.offer(5 * result);
            }
            if (visited.add(7 * result)) {
                minHeap.offer(7 * result);
            }
        }
        return result;
    }

    /**
     * Kth Closest Point To <0,0,0>
     * Given three arrays sorted in ascending order. Pull one number from each array to form a coordinate <x,y,z>
     * in a 3D space. Find the coordinates of the points that is k-th closest to <0,0,0>.
     */
    public class Coordinate implements Comparable<Coordinate> {
        int xi;
        int yi;
        int zi;
        double dist;
        public Coordinate(int xi, int yi, int zi, int[] a, int[] b, int[] c) {
            this.xi = xi;
            this.yi = yi;
            this.zi = zi;
            this.dist = getDist(a[xi], b[yi], c[zi]);
        }

        private double getDist(int x, int y, int z) {
            return Math.sqrt(x * x + y * y + z * z);
        }

        @Override
        public int compareTo(Coordinate c2) {
            if (Double.compare(dist, c2.dist) == 0) {
                return 0;
            }
            return Double.compare(dist, c2.dist) < 0 ? -1 : 1;
        }

        @Override
        public int hashCode() {
            return xi * 31 * 31 + yi * 31 + zi;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Coordinate)) {
                return false;
            }
            // o must be a coordinate, otherwise no o.x
            Coordinate c = (Coordinate) o;
            return xi == c.xi && yi == c.yi && zi == c.zi;
        }
    }

    public List<Integer> kthClosestToOrigin(int[] a, int[] b, int[] c, int k) {
        // Assumptions: 1 the three given arrays are not null or empty
        // 2 k >= 1 and k <= a.length * b.length * c.length
        PriorityQueue<Coordinate> minHeap = new PriorityQueue<>();
        Set<Coordinate> checked = new HashSet<>();
        Coordinate cur = new Coordinate(0, 0, 0, a, b, c);
        minHeap.offer(cur);
        checked.add(cur);
        while(k > 0) {
            cur = minHeap.poll();
            if (cur.xi + 1 < a.length) {
                Coordinate point1 = new Coordinate(cur.xi + 1, cur.yi, cur.zi, a, b, c);
                if (checked.add(point1)) {
                    minHeap.offer(point1);
                }
            }
            if (cur.yi + 1 < b.length) {
                Coordinate point2 = new Coordinate(cur.xi, cur.yi + 1, cur.zi, a, b, c);
                if (checked.add(point2)) {
                    minHeap.offer(point2);
                }
            }
            if (cur.zi + 1 < c.length) {
                Coordinate point3 = new Coordinate(cur.xi, cur.yi, cur.zi + 1, a, b, c);
                if (checked.add(point3)) {
                    minHeap.offer(point3);
                }
            }
            k--;
        }
        return Arrays.asList(a[cur.xi], b[cur.yi], c[cur.zi]);
    }

    /**
     * Given a non-negative integer array representing the heights of a list of adjacent bars.
     * Suppose each bar has a width of 1. Find the largest rectangular area that can be formed in the histogram.
     * eg. { 2, 1, 3, 3, 4 }, the largest rectangle area is 3 * 3 = 9(starting from index 2 and ending at index 4)
     * Time = O(n)
     * Space = O(n)
     */
    public int largestRectangle(int[] array) {
        // assumption: array is not null or empty, all value are non-negative
        int max = 0;
        Deque<Integer> stack = new ArrayDeque<>(); // stores an ascending order of indices
        for (int i = 0; i <= array.length; i++) {
            // add a column of 0 to the end to deal with the last column
            int cur = i == array.length ? 0 : array[i];
            while (!stack.isEmpty() && cur <= array[stack.peekFirst()]) {
                int height = array[stack.pollFirst()];
                // cur left border == the index of the element on top of the stack + 1
                int left = stack.isEmpty() ? 0 : stack.peekFirst() + 1;
                // if stack is empty means it is the lowest column till now
                // so we can use 0 as the left boundary
                int right = i - 1; // cur right border == the current index - 1
                max = Math.max(max, height * (right - left + 1));
            }
            // add column to the stack if it greater than the column of top element in the stack
            stack.offerFirst(i);
        }
        return max;
    }

    /***
     * Given a non-negative integer array representing the heights of a list of adjacent bars.
     * Suppose each bar has a width of 1. Find the largest amount of water that can be trapped in the histogram.
     * eg. { 2, 1, 3, 2, 4 }, the amount of water can be trapped is 1 + 1 = 2
     * (at index 1, 1 unit of water can be trapped and index 3, 1 unit of water can be trapped)
     * Time = O(n)
     * Space = O(1)
     */
    public int maxWaterTrapped(int[] array) {
        // assumption: array is not null
        int waterSum = 0;
        int i = 0; // left index
        int j = array.length - 1; // right index
        int leftMax = 0;
        int rightMax = 0;
        while (i <= j) {
            leftMax = Math.max(leftMax, array[i]);
            rightMax = Math.max(rightMax, array[j]);
            if (leftMax < rightMax) {
                waterSum += leftMax - array[i];
                i++;
            } else {
                waterSum += rightMax - array[j];
                j--;
            }
        }
        return waterSum;
    }
}