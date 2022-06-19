package crosstraining;

import org.jetbrains.annotations.NotNull;

import java.util.*;

class Point {
    public int x;
    public int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class C3KWayMergeCommonElements {
    /**
     * Find common elements in 3 sorted arrays.
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
     * Find common elements in k sorted arrays.
     * Iterative reduction
     *
     * Time = O(kn)
     * Space = O(n)
     */
    public List<Integer> commonElements(List<List<Integer>> input) {
        List<Integer> result = new ArrayList<>();
        for (List<Integer> list : input) {
            result = commonElements(result, list);
        }
        return result;
    }

    private List<Integer> commonElements(List<Integer> list1, List<Integer> list2) {
        List<Integer> result = new ArrayList<>();
        int p1 = 0;
        int p2 = 0;
        while (p1 < list1.size() && p2 < list2.size()) {
            int compare = list1.get(p1).compareTo(list2.get(p2));
            if (compare == 0) {
                result.add(list1.get(p1));
                p1++;
                p2++;
            } else if (compare < 0) {
                p1++;
            } else {
                p2++;
            }
        }
        return result;
    }

    /**
     * Merge K Sorted Array
     * Merge K sorted array into one big sorted array in ascending order.
     *
     * Time = O(nk * logk) // nk elements, each offered once and polled once
     * Space = O(k) // heap大小, k个element(value, arrayIndex, indexInArray)
     */
    public int[] mergeKSortedArray(int[][] arrayOfArrays) {
        PriorityQueue<Element> minHeap = new PriorityQueue<>(Comparator.comparingInt((Element e) -> e.value));
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

    /**
     * Merge K Sorted Linked List
     * Merge K sorted lists into one big sorted list in ascending order.
     *
     * Time = O(nk * logk) // nk elements, each offered once and polled once
     * Space = O(k) // heap大小, k个ListNode
     */
    public ListNode mergeKSortedLinkedList(List<ListNode> listOfLists) {
        // assumption listOfLists is not null, and none of the lists is null
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
                Comparator.comparingInt((ListNode node) -> node.value));
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

    /**
     * Largest Rectangle In Histogram
     * Given a non-negative integer array representing the heights of a list of adjacent bars.
     * Suppose each bar has a width of 1. Find the largest rectangular area that can be formed in the histogram.
     * e.g. { 2, 1, 3, 3, 4 }, the largest rectangle area is 3 * 3 = 9(starting from index 2 and ending at index 4)
     *
     * Time = O(n)
     * Space = O(n)
     */
    public int largestRectangle(int[] array) {
        // assumption: array is not null or empty, all value are non-negative
        int maxArea = 0;
        Deque<Integer> stack = new ArrayDeque<>(); // stores an ascending order of indices, not values
        for (int i = 0; i <= array.length; i++) {
            // add a column of 0 to the end to deal with the last column
            int cur = i == array.length ? 0 : array[i];
            while (!stack.isEmpty() && cur <= array[stack.peekFirst()]) {
                int height = array[stack.pollFirst()];
                // cur left border == the index of the element on top of the stack + 1
                int left = stack.isEmpty() ? 0 : stack.peekFirst() + 1;
                // if stack is empty means it is the lowest column till now,
                // so we can use 0 as the left boundary
                int right = i - 1; // cur right border == the current index - 1
                maxArea = Math.max(maxArea, height * (right - left + 1));
            }
            // add column to the stack if it's greater than the column of top element in the stack
            stack.offerFirst(i);
        }
        return maxArea;
    }

    /**
     * Max Watter Trapped I
     * Given a non-negative integer array representing the heights of a list
     * of adjacent bars. Suppose each bar has a width of 1. Find the largest
     * amount of water that can be trapped in the histogram.
     *
     * e.g. { 2, 1, 3, 2, 4 }, the amount of water can be trapped is 1 + 1 = 2
     * (at index 1, 1 unit of water can be trapped and index 3, 1 unit of water can be trapped)
     *
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
            if (leftMax <= rightMax) {
                waterSum += Math.max(0, leftMax - array[i]);
                leftMax = Math.max(leftMax, array[i]);
                i++;
            } else {
                waterSum += Math.max(0, rightMax - array[j]);
                rightMax = Math.max(rightMax, array[j]);
                j--;
            }
        }
        return waterSum;
    }

    public int most(Point[] points) {
        // assume the given array is not null, and it has at least 2 points
        int result = 0;
        // we use each pair of points to form a line
        for (int i = 0; i < points.length; i++) {
            // 使用点斜式 y - y0 = k(x - x0)
            // we take the point as seed and try to find all possible slopes
            Point seed = points[i];
            // record the points with same <x,y>
            int same = 1;
            // record the points with same x, for the special case of infinite slop
            int sameX = 0;
            // record the maximum number of points on the same line crossing the seed point
            int most = 0;
            // a map with all possible slopes
            HashMap<Double, Integer> count = new HashMap<>();
            for (int j = 0; j < points.length; j++) {
                if (i == j) {
                    continue;
                }
                Point temp = points[j];
                if (temp.x == seed.x && temp.y == seed.y) {
                    // handle the points with same <x,y>
                    same++;
                } else if (temp.x == seed.x) {
                    // handle the points with same x
                    sameX++;
                } else {
                    // otherwise, just calculate the slope
                    // and increment the counter for the calculated slop
                    double slope = ((temp.y - seed.y) + 0.0) / (temp.x - seed.x);
                    count.put(slope, count.getOrDefault(slope, 0) + 1);
                    most = Math.max(most, count.get(slope));
                }
            }
            most = Math.max(most, sameX) + same;
            result = Math.max(result, most);
        }

        return result;
    }

    public static void main(String[] args) {
        C3KWayMergeCommonElements ins = new C3KWayMergeCommonElements();
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 3);
        Point p3 = new Point(1, 1);
        Point[] points = {p1, p2, p3};
        System.out.println(ins.most(points));
    }
}
