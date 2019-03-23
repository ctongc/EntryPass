package algorithms;

import java.util.Arrays;

public class SortingAlgorithms {

    private SortingAlgorithms() {
    }

    /**
     * Selection Sort
     * 找到current index的最小值，然后再看下一个index
     * Time = O(n^2)
     * Space = O(1)
     */
    public int[] selectionSort(int[] array) {
        // sanity check
        if (array == null || array.length == 0) {
            return array;
        }

        // iterate除了最后一个元素外的所有元素, 所以要length - 1
        for (int i = 0; i < array.length - 1; i++) {
            int min = i;
            // find the min element in unsorted subarray of (i, array.length - 1]
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }
            swap(array, i, min);
        }
        return array;
    }

    /**
     * Merge Sort
     * 每一层拿到左半和右半sort好的结果, 然后merge
     * Merge sort is mostly used to sort objects
     * Time = O(nlogn)
     * Space = O(n) // due to the auxiliary space used to merge parts of the array
     */
    public int[] mergeSort(int[] array) {
        // sanity check
        if (array == null || array.length == 0) {
            return array;
        }
        // create auxiliary array here to avoid using extra memory
        int[] aux = new int[array.length];
        mergeSort(array, aux, 0, array.length - 1);
        return array;
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

        // if we still have some elements remaining at the left side
        // we need to copy them to the original array
        while (auxLeft <= mid) {
            array[current++] = aux[auxLeft++];
        }
        // no need to copy remaining elements at the right side
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
    public int[] quickSort(int[] array) {
        // sanity check
        if (array == null || array.length == 0) {
            return array;
        }
        quickSort(array, 0, array.length - 1);
        return array;
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
        // move pivot to the last
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
        return left + (int) (Math.random() * (right - left + 1));  // +1 is a must have
    }

    /**
     * Move 0s To The End
     * Given an array of integers, move all the 0s to the right end of the array.
     * The relative order of the elements in the original array does not need to be maintained.
     * Time = O(n)
     * Space = O(1)
     */
    public int[] moveZero(int[] array) {
        // sanity check
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
     * 3个挡板 red green blue，4个区域a b c d，同向+相向而行
     * a [0, red) red左边不包括red 全是-1
     * b [red, green) red到green左侧不包括green 全是0.
     *   green是这道题的current index
     * c [green, blue] 为未探索区域
     * d (blue, length - 1] blue右侧不包括green 全是1
     */
    public int[] rainbowSort(int[] array) {
        // sanity check
        if (array == null || array.length == 0) {
            return array;
        }
        int red = 0; // all number to the left of red [0, red) are all -1
        int green = 0; // all number between red and blue [red, blue] are all 0
        int blue = array.length - 1; // all number to the right of blue (blue, array.length - 1] are all 1

        // green is the actually the current index, elements in [green, blue] are unsorted
        while (green <= blue) {
            if (array[green] == -1) {
                // swap the first green with incoming red
                // current points to the next element
                swap(array, red++, green++);
            } else if (array[green] == 0) {
                // no swap is needed
                green++;
            } else {
                // swap the last unsorted element with incoming blue
                // keep the position of current index !!!!
                // since the new array[green] is unsorted after the swap
                swap(array, blue--, green);
            }
        }
        return array;
    }

    /**
     * Swap Two Elements
     * note that i and j are INDEX, not the element itself
     */
    public static void swap(int[] array, int i, int j) {
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
