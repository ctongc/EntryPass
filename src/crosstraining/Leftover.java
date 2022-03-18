package crosstraining;

import java.util.*;

public class Leftover {

    // below are not in Cross Training I

    /**
     * Sort in specified order
     * Given two integer arrays A1 and A2, sort A1 in such a way that the relative order
     * among the elements will be same as those are in A2. For the elements that are not in A2
     * append them in the right end of the A1 in ascending order.
     *
     * eg. A1 = {2, 1, 2, 5, 7, 1, 9, 3}, A2 = {2, 1, 3}, A1 is sorted to {2, 2, 1, 1, 3, 5, 7, 9}
     *
     * Time = O(n)
     * Space = O(n)
     */
    public int[] sortInSpecialOrder(int[] A1, int[] A2) {
        // assumptions: A1 != null && A2 != null, not duplicate elements in A2
        // pre-processing - for comparator
        Integer[] A1I = new Integer[A1.length];
        for (int i = 0; i < A1.length; i++) {
            A1I[i] = A1[i];
        }
        Arrays.sort(A1I, new MyComparator(A2)); // Arrays.sort(T[], new MyComparator(..)) so need a transfer
        for (int i = 0; i < A1.length; i++) {
            A1[i] = A1I[i];
        }
        return A1;
    }

    private static class MyComparator implements Comparator<Integer> {
        private Map<Integer, Integer> map;
        public MyComparator(int[] order) {
            map = new HashMap<>();
            for (int i = 0; i < order.length; i++) {
                map.put(order[i], i);
            }
        }

        @Override
        public int compare(Integer a, Integer b) {
            if (map.containsKey(a) && map.containsKey(b)) {
                return map.get(a) < map.get(b) ? -1 : 1;
            } else if (!map.containsKey(a) && !map.containsKey(b)) {
                return a < b ? -1 : 1;
            }
            return map.containsKey(a) ? -1 : 1;
        }
    }

    /**
     * using statement lambda
     */
    public int[] sortInSpecialOrder2(int[] A1, int[] A2) {
        // assumptions: A1 != null && A2 != null, not duplicate elements in A2
        // preprocessing - for comparator
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < A2.length; i++) {
            map.put(A2[i], i);
        }
        Integer[] A1I = new Integer[A1.length];
        for (int i = 0; i < A1.length; i++) {
            A1I[i] = A1[i];
        }
        Arrays.sort(A1I, (a, b) -> {
            if (map.containsKey(a) && map.containsKey(b)) {
                return map.get(a) < map.get(b) ? -1 : 1;
            } else if (!map.containsKey(a) && !map.containsKey(b)) {
                return a < b ? -1 : 1;
            }
            return map.containsKey(a) ? -1 : 1;
        });
        for (int i = 0; i < A1.length; i++) {
            A1[i] = A1I[i];
        }
        return A1;
    }

    // below are not in Cross Training II

    /**
     * Merge K Sorted Array
     * Merge K sorted array into one big sorted array in ascending order.
     *
     * Assumptions
     * The input arrayOfArrays is not null, none of the arrays is null either.
     * Time = O(nk * logk) // nk elements, each offered once and polled once
     * Space = O(k) // heap大小, k个element(value, arrayIndex, indexInArray)
     */
    public int[] mergeKSortedArray(int[][] arrayOfArrays) {
        PriorityQueue<Element> minHeap = new PriorityQueue<>(new MyComparator1());
        int resultLength = 0;
        /* stored the first element to be merged in each array */
        for (int i = 0; i < arrayOfArrays.length; i++) {
            if (arrayOfArrays[i].length != 0) {
                minHeap.offer(new Element(arrayOfArrays[i][0], i, 0));
            }
            resultLength += arrayOfArrays[i].length;
        }

        int[] result = new int[resultLength];
        int cur = 0; // index of result[]

        /* k-way merge */
        while (!minHeap.isEmpty()) {
            Element temp = minHeap.poll();
            result[cur++] = temp.value;

            if (temp.indexInArray + 1 < arrayOfArrays[temp.arrayIndex].length) {
                // reuse the element but advance the index by 1
                temp.value = arrayOfArrays[temp.arrayIndex][temp.indexInArray + 1];
                temp.indexInArray++;
                minHeap.offer(temp);
            }
        }
        return result;
    }

    public static class Element {
        int value;
        int arrayIndex;
        int indexInArray;

        public Element(int value, int arrayIndex, int indexInArray) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.indexInArray = indexInArray;
        }
    }

    public static class MyComparator1 implements Comparator<Element> {
        @Override
        public int compare(Element c1, Element c2) {
            if (c1.value == c2.value) {
                return 0;
            }
            return c1.value < c2.value ? -1 : 1;
        }
    }

    /**
     * Merge K Sorted Linked List
     * Merge K sorted lists into one big sorted list in ascending order.
     *
     * Assumptions
     * The input listOfLists is not null, none of the arrays is null either.
     * Time = O(nk * logk) // nk elements, each offered once and polled once
     * Space = O(k) // heap大小, k个ListNode
     */
    public ListNode merge(List<ListNode> listOfLists) {
        // assumption listOfLists is not null, and non of the lists is null
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(new MyComparator2());
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (ListNode n : listOfLists) {
            if (n != null) {
                minHeap.offer(n);
            }
        }
        while (!minHeap.isEmpty()) {
            cur.next = minHeap.poll();
            if (cur.next.next != null) {
                minHeap.offer(cur.next.next);
            }
            cur = cur.next;
        }
        return dummy.next;
    }

    private class MyComparator2 implements Comparator<ListNode> {
        @Override
        public int compare(ListNode n1, ListNode n2) {
            if (n1.value == n2.value) {
                return 0;
            }
            return n1.value < n2.value ? -1 : 1;
        }
    }

    /**
     * There is a wooden stick with length >= 1, we need to cut it into pieces,
     * where the cutting positions are defined in an int array A. The positions
     * are guaranteed to be in ascending order in the range of [1, length - 1].
     * The cost of each cut is the length of the stick segment being cut.
     * Determine the minimum total cost to cut the stick into the defined pieces.
     * Time = O(n^3) // O(n^2)填表，填每个格子的时候都要扫一遍(i+j)个元素*O(n)
     * Space = O(n^2)
     */
    public int minCostCuttingWood(int[] cuts, int length) {
        // assumption: length >= 1
        // sanity checks

        int[] mycuts = new int[cuts.length + 2]; // pad with leftmost and rightmost pos.
        // mycuts[0] = 0;
        for (int i = 0; i < cuts.length; i++) {
            mycuts[i + 1] = cuts[i];
        }
        mycuts[mycuts.length - 1] = length;

        // M[i][j] represents the minimum cost of cutting the wood
        // between index i and index j in the input array cuts.
        // The final solution to return is the value of M[0][length - 1].
        int[][] M = new int[mycuts.length][mycuts.length];

        // induction rule
        // for each M[i][j], try out all possible k that (i < k < j)
        // M[i][j] = MIN(A[j] - A[i] + M[i][k] + M[k][j]) for all possible k
        for (int i = mycuts.length - 1; i >= 0; i--) { // bottom up
            for (int j = 0; j < mycuts.length; j++) { // left to right
                if (i + 1 == j) { // Base case, size = 1
                    M[i][j] = 0;
                } else {
                    M[i][j] = Integer.MAX_VALUE; // since it was initialized with 0
                    for (int k = i + 1; k < j; k++) {
                        M[i][j] = Math.min(M[i][j], mycuts[j] - mycuts[i] + M[i][k] + M[k][j]);
                    }
                }
            }
        }
        return M[0][mycuts.length - 1];
    }

    public static void main(String[] args) {
        Leftover ins = new Leftover();
        ins.sortInSpecialOrder(new int[]{4,4,3,3,3}, new int[]{4});
        System.out.println("haha: "+ins.minCostCuttingWood(new int[]{2,4,7}, 10));
    }
}
