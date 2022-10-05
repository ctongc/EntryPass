package com.ctong.entrypass.interviews;

import java.util.ArrayDeque;
import java.util.Queue;

public class Nio {
    /**
     * 53. Maximum Subarray
     * https://leetcode.com/problems/maximum-subarray/
     * Given an integer array nums, find the contiguous subarray (containing at
     * least one number) which has the largest sum and return its sum.
     *
     * A subarray is a contiguous part of an array.
     *
     * e.g. nums = [-2,1,-3,4,-1,2,1,-5,4]
     * Output: 6 -> [4,-1,2,1] has the largest sum = 6.
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int max = nums[0];
        int temp = nums[0];
        for (int i = 1; i < nums.length; i++) {
            temp = Math.max(nums[i], temp + nums[i]);
            max = Math.max(max, temp);
        }

        return max;
    }

    /**
     * Producer consumer problem*/
    static class MyBlockingQueue {
        Queue<Integer> queue; // work queue
        int limit;

        public MyBlockingQueue(int limit) {
            this.queue = new ArrayDeque<>();
            this.limit = limit;
        }

        public synchronized void put(Integer element) {
            while (queue.size() == limit) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (queue.size() == 0) {
                notifyAll();
            }

            queue.offer(element);
        }

        public synchronized Integer take() {
            while (queue.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (queue.size() == limit) {
                    notifyAll();
                }
            }

            return queue.poll();
        }
    }

    static class Producer implements Runnable {
        MyBlockingQueue workQueue;

        public Producer(MyBlockingQueue workQueue) {
            this.workQueue = workQueue;
        }

        @Override
        public void run() {
            workQueue.put(0);
        }
    }

    static class Consumer implements Runnable {
        MyBlockingQueue workQueue;

        public Consumer(MyBlockingQueue workQueue) {
            this.workQueue = workQueue;
        }

        @Override
        public void run() {
            workQueue.take();
        }
    }

    // 2^n

    // 1 3 5 7 9 - 8 4 6 2
    // int[] , target
    // index, -1
    public int search(int[] array, int target) {
        if (array == null || array.length == 0) {
            return -1;
        }

        int peakIndex = getLargestIndex(array);

        int findFirst = binarySearchSmallToBig(array, peakIndex, target);
        int findSecond = binarySearchBigToSmall(array, peakIndex + 1, target);

        return findFirst == -1 ? findSecond : findFirst;
    }

    private int getLargestIndex(int[] array) {
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (array[mid] <= array[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private int binarySearchSmallToBig(int[] array, int right, int target) {
        int left = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    private int binarySearchBigToSmall(int[] array, int left, int target) {
        int right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] > target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
