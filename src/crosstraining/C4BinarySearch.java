package crosstraining;

public class C4BinarySearch {
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
}
