package datastructures;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * An example implementation of capacity limited min heap containing only int values,
 * with the capacity to do update and poll at a specific index.
 *
 * This is for demonstration of percolateUp and percolateDown methods and
 * how to utilize these methods to do basic heap operations.
 *
 * The public methods provided are:
 * size()
 * isEmpty()
 * isFull()
 * peek()
 * offer(int ele)
 * poll()
 * update(int index, int value) - update the element at index to a given new value
 */
public class MinHeap {
    private int[] array;
    private int size;

    public MinHeap(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Input array can not be null or empty.");
        }
        this.array = array;
        size = array.length;
        heapify();
    }

    public MinHeap(int cap) {
        if (cap <= 0) {
            throw new IllegalArgumentException("Capacity can not be <= 0.");
        }
        array = new int[cap];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == array.length;
    }

    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return array[0];
    }

    public int poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        int result = array[0];
        array[0] = array[size - 1];
        size--;
        percolateDown(0);
        if (size < array.length / 4) {
            array = Arrays.copyOf(array, array.length / 2);
        }
        return result;
    }

    public void offer(int ele) {
        if (isFull()) {
            array = Arrays.copyOf(array, (int) (array.length * 1.5));
        }
        array[size] = ele;
        percolateUp(size);
        size++;
    }

    private void heapify() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            percolateDown(i);
        }
    }

    private void percolateUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (array[index] < array[parentIndex]) {
                swap(index, parentIndex);
            } else {
                break; // needed!
            }
            index = parentIndex;
        }
    }

    private void percolateDown(int index) {
        // check if index is legal
        while (index <= size / 2 - 1) {
            int leftChildIndex = index * 2 + 1;
            int rightChildIndex = index * 2 + 2;
            // use swapCandidate to indicate the smaller element between left and right child
            int swapCandidate = rightChildIndex <= size - 1 && array[leftChildIndex] > array[rightChildIndex] ?
                    rightChildIndex : leftChildIndex;

            if (array[index] > array[swapCandidate]) {
                swap(index, leftChildIndex);
            } else {
                break; // needed!
            }
            index = swapCandidate;
        }
    }

    public void update(int index, int ele) {
        if (index < 0 || index > size - 1) {
            throw new ArrayIndexOutOfBoundsException("Invalid index: " + index);
        }
        int oldValue = array[index];
        array[index] = ele;
        if (oldValue < ele) {
            percolateDown(index);
        } else {
            percolateUp(index);
        }
    }

    // update when don't know the position of the element
    public void update2(int oldValue, int newValue) {
        int index = findIndex(oldValue);
        if (index == -1) {
            throw new NoSuchElementException("No such element in the Heap!");
        }
        update(index, newValue);
    }

    private int findIndex(int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private void swap(int left, int right) {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }
}