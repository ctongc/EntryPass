package com.ctong.entrypass.algorithms;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class SortingAlgorithms {

    /**
     * Selection Sort an array a[] with size n.
     * 找到current index的最小值，然后再看下一个index
     * Time = O(n^2)
     * Space = O(1)
     */
    public void selectionSort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }

        // iterate除了最后一个元素外的所有元素, 所以要length - 1
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            // find the min element in unsorted sub-array of (i, array.length - 1]
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            swap(array, i, minIndex);
        }
    }

    /**
     * Merge Sort
     * 每一层拿到左半和右半sort好的结果, 然后merge
     * Merge sort is mostly used to sort objects
     * Time = O(nlogn)
     * Space = O(n) // due to the auxiliary space used to merge parts of the array
     */
    public void mergeSort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        // create auxiliary array here to avoid using extra memory
        int[] aux = new int[array.length];
        mergeSort(array, aux, 0, array.length - 1);
    }

    private void mergeSort(int[] array, int[] aux, int left, int right) {
        // left == right then return
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(array, aux, left, mid); // sort the left half
        mergeSort(array, aux, mid + 1, right); // sort the right half
        // if largest element in the left half < smallest element in the second half
        // then array is already in order
        if (array[mid] < array[mid + 1]) {
            return;
        }
        merge(array, aux, left, mid, right); // merge them
    }

    private void merge(int[] array, int[] aux, int left, int mid, int right) {
        // copy elements into an auxiliary array
        // !!! i <= right !!! because right is already array.length - 1
        for (int i = left; i <= right; i++) {
            aux[i] = array[i];
        }

        int auxLeft = left;
        int auxRight = mid + 1;
        int current = left;

        // compare left half and right half
        // move the smaller element back into the original array 谁小移谁
        while (auxLeft <= mid && auxRight <= right) {
            if (aux[auxLeft] < aux[auxRight]) {
                array[current++] = aux[auxLeft++];
            } else {
                array[current++] = aux[auxRight++];
            }
        }
        // auxLeft > mid || auxRight > right

        // if we still have some elements remaining on the left side
        // we need to copy them to the original array
        while (auxLeft <= mid) {
            array[current++] = aux[auxLeft++];
        }
        // no need to copy remaining elements on the right side
        // since they were already in the position
    }

    /**
     * Quick Sort
     * 找到pivot真正的位置，左边都比他小，右边都比他大。然后递归
     * 找到pivot左边应该在右边的数, 找到pivot右边应该在左边的数, 交换
     * 两个挡板left right 三个区域a b c思想
     * a [0,left) 左挡板左侧(不包括自己) 都比pivot小
     * b [left, right] 左右挡板之间为未探索区域
     * c (right, array.length - 1] 右挡板右侧(不包括自己) 都大于等于pivot
     *
     * Time = O(nlogn), worse case O(n^2)
     * Space = O(logn), worse case O(n)
     */
    public void quickSort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }
        int pivot = partition(array, left, right); // now my pivot is sorted and at the right place
        // sort the right of the pivot
        quickSort(array, left, pivot - 1);
        // sort the left of the pivot
        quickSort(array, pivot + 1, right);
    }

    private int partition(int[] array, int left, int right) {
        // pick an arbitrary element as the pivot
        int pivot = getPivotIndex(left, right);
        int pivotValue = array[pivot];
        int last = right;
        // move pivot to the rightmost position first
        swap(array, pivot, right--);
        while (left <= right) {
            if (array[left] < pivotValue) { // find elements on left that should be on right
                left++;
            } else if (array[right] > pivotValue) { // find elements on right that should be on left
                right--;
            } else { // if no index change, swap elements and move both indices
                swap(array, left++, right--);
            }
        }
        // move pivot to the right and final position
        swap(array, left, last);
        return left;
    }

    private int getPivotIndex(int left, int right) {
        return left + (int) (Math.random() * (right - left + 1));  // +1 is a must-have
    }

    /**
     * Move 0s To The End
     * Given an array of integers, move all the 0s to the right end of the array.
     * The relative order of the elements in the original array does not need to be maintained.
     * Time = O(n)
     * Space = O(1)
     */
    public int[] moveZero(int[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int j = 0;
        // iterate the array, move every nonzero elements to the left of j [0, j)
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                array[j++] = array[i];
            }
        }
        // replace all remaining elements that to the right [j, array.length - 1] with 0
        while (j < array.length) {
            array[j++] = 0;
        }
        return array;
    }

    /**
     * Rainbow Sort
     * 3个挡板 neg zero one，4个区域a b c d，同向+相向而行
     * a [0, neg) neg左边不包括red 全是-1
     * b [neg, zero) neg到zero左侧不包括zero 全是0.
     *   zero是这道题的current index
     * c [zero, one] 为未探索区域
     * d (one, length - 1] one右侧不包括zero 全是1
     * Time = O(n)
     * Space = O(1)
     */
    public void rainbowSort(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        int neg = 0; // all number to the left of neg [0, neg) are all -1
        int zero = 0; // all number between neg and one [neg, one] are all 0
        int one = array.length - 1; // all number to the right of one (one, array.length - 1] are all 1

        // zero is actually the current index, elements in [zero, one] are unsorted
        while (zero <= one) {
            if (array[zero] == -1) {
                // swap the first zero with incoming neg
                // current points to the next element
                swap(array, neg++, zero++);
            } else if (array[zero] == 0) {
                // no swap is needed
                zero++;
            } else {
                // swap the last unsorted element with incoming one
                // keep the position of current index !!!!
                // since the new array[zero] is unsorted after the swap
                swap(array, one--, zero);
            }
        }
    }

    /**
     * Topological Sort
     * Given n courses and the prerequisites of each course
     * find a valid order to take all the courses (aka topological order)
     *
     * e.g. course 0 -> course 1 -> course 3
     *               -> course 2 ->
     * output: 0 1 2 3 or 0 2 1 3
     *
     * Time = O(n + e)
     * Space = O(n)
     */
    public int[] topologicalSort(List<List<Integer>> graph) {
        int numCourses = graph.size();
        int[] topologicalOrder = new int[numCourses];
        int[] incomingEdges = new int[numCourses];
        for (int x = 0; x < numCourses; x++) {
            for (int y : graph.get(x)) {
                incomingEdges[y]++;
            }
        }

        // nodes with no incoming edges
        Queue<Integer> queue = new ArrayDeque<>();
        for (int x = 0; x < numCourses; x++) {
            if (incomingEdges[x] == 0) {
                queue.offer(x);
            }
        }

        int numExpanded = 0;
        while (!queue.isEmpty()) {
            int x = queue.poll();
            topologicalOrder[numExpanded++] = x;
            for (int y : graph.get(x)) {
                if (--incomingEdges[y] == 0) {
                    queue.offer(y);
                }
            }
        }

        return numExpanded == numCourses ? topologicalOrder : new int[]{};
    }

    /**
     * Bubble Sort
     * 比较相邻的元素, 把大的换到后面, 每轮都能把倒数第i个最大值放到倒数第i个位置上
     * Time = O(n^2)
     * Space = O(1)
     */
    public static void bubbleSort(int[] array) {
        boolean swapped = true;

        for (int i = 0; i < array.length - 1 && swapped; i++) {
            // if no swapping happen in one iteration, means it's already sorted (ascending)
            swapped = false;
            for (int j = 0; j < array.length - 1 - i; j++) {
                // if current element is greater than the next element, it is swapped
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    swapped = true;
                }
            }
        }
    }

    /**
     * Swap Two Elements
     * note that i and j are INDEX, not the element itself
     */
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        SortingAlgorithms solution = new SortingAlgorithms();
        int[] array = new int[] {2, 4, 1, 5, 3};
        int[] array2 = new int[] {0, 0, 1, -1, 1, 0, 1, 0, -1, 1, 0};

        solution.selectionSort(array);
        //solution.mergeSort(array);
        //solution.quickSort(array);

        solution.rainbowSort(array2);

        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(array2));
    }
}
