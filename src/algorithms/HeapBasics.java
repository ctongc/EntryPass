package algorithms;

import java.util.*;

class MyMinHeap {
    private int[] array;
    private int size;

    public MyMinHeap(int cap) {
        if (cap <= 0) {
            throw new IllegalArgumentException("Capacity can't be less or equal to 0!");
        }
        this.array = new int[cap];
        this.size = 0;
    }

    public MyMinHeap(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Input array can't be null or empty!");
        }
        this.array = array;
        size = array.length;
        heapify();
    }

    private void heapify() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            percolateDown(i);
        }
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isFull() {
        return this.size == array.length;
    }

    public Integer peek() {
        return isEmpty() ? null : array[0];
    }

    public void offer(int value) {
        if (isFull()) {
            int[] newArray = new int[(int) (this.array.length * 1.5)];
            copy(array, newArray);
            this.array = newArray;
        }
        array[size] = value;
        size++;
        percolateUp(size - 1);
    }

    public int poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        int result = array[0];
        array[0] = array[this.size - 1];
        size--;
        percolateDown(0);
        return result;
    }

    // update when you know the position of the element
    // return the original value
    public int update(int index, int value) {
        if (index < 0 || index > size - 1) {
            throw new ArrayIndexOutOfBoundsException("Invalid index range!");
        }
        int result = array[index];
        array[index] = value;
        if (result > value) {
            percolateUp(index);
        } else {
            percolateDown(index);
        }
        return result;
    }

    // update when don't know the position of the element
    public int update2(int oldValue, int newValue) {
        int index = findValue(this.array, oldValue);
        if (index == -1) {
            throw new NoSuchElementException("No such element in the Heap!");
        }
        return update(index, newValue);
    }

    private int findValue(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private void percolateUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (array[parentIndex] > array[index]) {
                swap(array, parentIndex, index);
            } else {
                break; // needed!
            }
            index = parentIndex;
        }
    }

    private void percolateDown(int index) {
        // check if index is legal?
        while (index <= size / 2 - 1) {
            int leftChildIndex = index * 2 + 1;
            int rightChildIndex = index * 2 + 2;
            // swapCandidate is the smallest one among leftChild and rightChild
            // update swapCandidate if rightChild exists and it is smaller than leftChild
            int swapCandidate =
                    rightChildIndex <= size - 1 && array[leftChildIndex] >= array[rightChildIndex] ?
                            rightChildIndex : leftChildIndex;
            // swap if necessary
            if (array[index] > array[swapCandidate]) {
                swap(array, index, swapCandidate);
            } else {
                break; // needed!
            }
            index = swapCandidate;
        }
    }

    private void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    private void copy(int[] oldArray, int[] newArray) {
        for (int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[i];
        }
    }
}

class ReverseComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer c1, Integer c2) {
        if (c1 == c2) {
            return 0;
        }
        return c1 > c2 ? -1 : 1;
    }
}

public class HeapBasics {
    /**
     * K Smallest In Unsorted Array
     * Find the K smallest numbers in an unsorted integer array A.
     * The returned numbers should be in ascending order.
     * Input: A = {3, 4, 1, 2, 5}, k = 3
     * Output: {1, 2, 3}
     *
     * Using max heap - Online algorithm - first choice
     * Time = O(nlogk) // O(k + (n - k)logk)
     * Space = (n)
     */
    public int[] kSmallest(int[] array, int k) {
        // assume k is >= 0 and smaller than or equal to size of array
        if (array == null || array.length == 0) {
            return new int[0];
        }
        /* Put k elements into the minHeap O(k)
         * Iterate over the rest (n - k) elements one by one:
         * - Compare with the LARGEST element of the previous smallest k candidates
         *   - Case 1: New element >= Top, ignore
         *   - Case 2: New element < top, poll(), insert(new element)
         * All elements remaining in the maxHeap are the final solution */
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new ReverseComparator()); // Collections.reverseOrder()

        for (int i = 0; i < array.length; i++) {
            if (i < k) {
                // put first k elements into the heap
                maxHeap.offer(array[i]);
            } else if (array[i] < maxHeap.peek()) { // from the k+1's element
                maxHeap.poll();
                maxHeap.offer(array[i]);
            }
        }
        int[] kSmallestElements = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            kSmallestElements[i] = maxHeap.poll();
        }
        return kSmallestElements;
    }

    /**
     * Using min heap - offline algorithm
     * Time = O(n + klogn)
     * Space = (n)
     */
    public int[] kSmallest2(int[] array, int k) {
        // assume k is >= 0 and smaller than or equal to size of array
        if (array == null || array.length == 0) {
            return new int[0];
        }
        /* Heapify all elements into the minHeap O(n)
         * Call .poll() k times O(k * logn) */
        List<Integer> list = new ArrayList<>();
        for (int i : array) {
            list.add(i);
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(list);
        int[] kSmallestElements = new int[k];
        for (int i = 0; i < k; i++) {
            kSmallestElements[i] = minHeap.poll();
        }
        return kSmallestElements;
    }

    /**
     * Using quick select - offline algorithm
     * Time = O(n) // worst case O(n^2),  n + n/2 + n/4 + ...
     * Space = O(logn) // worst case O(n)
     */
    public int[] kSmallest3(int[] array, int k) {
        // assume k is >= 0 and smaller than or equal to size of array
        // sanity check
        if (array == null || array.length == 0) {
            return new int[0];
        }
        quickSelect(array, 0, array.length - 1, k - 1); // NOTICE HERE, k - 1
        return Arrays.copyOfRange(array, 0, k);
    }

    private void quickSelect(int[] array, int low, int high, int k) {
        int pivot = quickSelect(array, low, high);
        /* we only do quick select on one partition
         * case 1: if pivot is already the kth smallest, return
         * case 2: if the kth smallest one is on the left partition, only do quick select on left part.
         * case 3: if the kth smallest one is on the right partition, only do quick select on right part. */
        if (pivot == k) {
            return;
        } else if (pivot > k){ // on left part
            quickSelect(array, low, pivot - 1, k);
        } else { // pivot < k, on right part
            quickSelect(array, pivot + 1, high, k);
        }
    }

    private int quickSelect(int[] array, int low, int high) {
        // pick an arbitrary element as the pivot
        int pivotIndex = low + (int) (Math.random() * (high - low + 1));
        int pivot = array[pivotIndex];
        int last = high;
        // move pivot to the last
        SortingAlgorithms.swap(array, pivotIndex, high--);
        while (low <= high) {
            // find elements on left that should be on right
            if (array[low] < pivot) {
                low++;
            } else if (array[high] > pivot) {
                // find elements on right that should be on left
                high--;
            } else {
                // swap elements and move both indices
                SortingAlgorithms.swap(array, low++, high--);
            }
        }
        SortingAlgorithms.swap(array, low, last);
        // low is the in place pivot
        return low;
    }

    /**
     * Find the K smallest sum of two elements,
     * each from one of the two unsorted integer arrays A and B
     * Not the optimized solution, considering using BFS with priority queue
     * see BreadthFirstSearch.kthSmallest()
     *
     * Time = O(k^2)
     * Space = O(k^2)
     */
    public int kSmallestSum(int[] A, int[] B, int k) {
        // corner case

        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < A.length && i < k; i++) {
            for (int j = 0; j < B.length && j < k; j++) {
                set.add(A[i] + B[j]);
            }
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(set); // heapify
        int result = 0;
        for (int p = 0; p < k; p++) {
            result = minHeap.poll();
        }
        return result;
    }

    public static void main(String[] args) {
        HeapBasics hp = new HeapBasics();
        System.out.println(Arrays.toString(hp.kSmallest(new int[]{3, 4, 1, 2, 5}, 3)));
        System.out.println(Arrays.toString(hp.kSmallest2(new int[]{3, 4, 1, 2, 5}, 3)));
        System.out.println(Arrays.toString(hp.kSmallest3(new int[]{3, 4, 1, 2, 5}, 3)));
        int[] A = {1,2,5};
        int[] B = {1,2,3};
        System.out.println(hp.kSmallestSum(A, B,3));
    }

}
