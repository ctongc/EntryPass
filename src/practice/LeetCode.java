package practice;

import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class LeetCode {

    /**
     * 5. Longest Palindromic Substring
     * https://leetcode.com/problems/longest-palindromic-substring/
     * Given a string s, return the longest palindromic substring in s.
     *
     * Time = O(n^2)
     * Space = O(1)
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        int left = 0;
        int maxLength = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            int length1 = getPalindromeLength(s, i, i); // center is i
            int length2 = getPalindromeLength(s, i, i + 1); // center is between i and i + 1
            int cur = Math.max(length1, length2);

            if (maxLength < cur) {
                left = (length1 > length2) ? i - length1 / 2 : i - length2 / 2 + 1;
                maxLength = cur;
            }
        }

        return s.substring(left, left + maxLength);
    }

    private int getPalindromeLength(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }

        return right - left - 1;
    }

    /**
     * 31. Next Permutation
     * https://leetcode.com/problems/next-permutation/
     * A permutation of an array of integers is an arrangement of its members into
     * a sequence or linear order. For example, for arr = [1,2,3], the following are
     * considered permutations of arr: [1,2,3], [1,3,2], [3,1,2], [2,3,1].
     * The next permutation of an array of integers is the next lexicographically
     * greater permutation of its integer. More formally, if all the permutations of
     * the array are sorted in one container according to their lexicographical order,
     * then the next permutation of that array is the permutation that follows it in
     * the sorted container. If such arrangement is not possible, the array must be
     * rearranged as the lowest possible order (i.e., sorted in ascending order).
     *
     * For example, the next permutation of arr = [1,2,3] is [1,3,2].
     * Similarly, the next permutation of arr = [2,3,1] is [3,1,2].
     * While the next permutation of arr = [3,2,1] is [1,2,3] because [3,2,1] does
     * not have a lexicographical larger rearrangement.
     *
     * Given an array of integers nums, find the next permutation of nums.
     * The replacement must be in place and use only constant extra memory.
     *
     * Time = O(n)
     * Space = O(1)
     */
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return;
        }

        int left = nums.length - 2;
        while (left >= 0 && nums[left] >= nums[left + 1]) {
            left--;
        }

        // if left < 0, means nums is in descending order: [9, 5, 4, 3, 1]
        // then no next larger permutation is possible and reverse the whole
        // if left >= 0, we find the first num that larger than nums[left]
        // swap them, and reverse all nums after left
        if (left >= 0) {
            int right = nums.length - 1;
            while (nums[right] <= nums[left]) {
                right--;
            }
            swap(nums, left, right);
        }

        reverse(nums, left + 1, nums.length - 1);
    }

    private void reverse(int[] nums, int i, int j) {
        while (i < j) {
            swap(nums, i++, j--);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 33. Search in Rotated Sorted Array
     * https://leetcode.com/problems/search-in-rotated-sorted-array/
     * There is an integer array nums sorted in ascending order (with distinct values).
     * Prior to being passed to your function, nums is possibly rotated at an unknown
     * pivot index k (1 <= k < nums.length) such that the resulting array is
     * [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed).
     * For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].
     *
     * Given the array nums after the possible rotation and an integer target, return
     * the index of target if it is in nums, or -1 if it is not in nums.
     *
     * Time = O(logn)
     * Space = O(1)
     */
    public int searchInRotatedArray(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[left] <= nums[mid]) { // left to mid is ascending
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else { // mid to right is ascending
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 40. Combination Sum II
     * https://leetcode.com/problems/combination-sum-ii/
     * Given a collection of candidate numbers (candidates) and a target number (target),
     * find all unique combinations in candidates where the candidate numbers sum to target.
     * Each number in candidates may only be used once in the combination.
     *
     * Note: The solution set must not contain duplicate combinations.
     *
     * Time = O(n^k) // k is the number of candidates
     * Space = O(k)
     */
    public List<List<Integer>> combinationSumWithDup(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void dfs(int[] candidates, int remain, int index, List<Integer> sol, List<List<Integer>> result) {
        if (remain == 0) {
            result.add(new ArrayList<>(sol));
            return;
        }

        if (index == candidates.length || remain < 0) {
            return;
        }

        sol.add(candidates[index]);
        dfs(candidates, remain - candidates[index], index + 1, sol, result);
        sol.remove(sol.size() - 1);

        // skip all subsequent duplicate elements once decided to not add current element
        while (index < candidates.length - 1 && candidates[index] == candidates[index + 1]) {
            index++;
        }
        dfs(candidates, remain, index + 1, sol, result);
    }

    /**
     * 47. Permutations II
     * https://leetcode.com/problems/permutations-ii/
     * Given a collection of numbers, nums, that might contain duplicates, return
     * all possible unique permutations in any order.
     *
     * Time = O(n!)
     * Space = O(n * n) // * n for set
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        if (nums == null || nums.length == 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> numsList = new ArrayList<>();
        Arrays.sort(nums);
        for (int n : nums) {
            numsList.add(n);
        }
        permutation(numsList, 0, result);
        return result;
    }

    private void permutation(List<Integer> nums, int index, List<List<Integer>> result) {
        if (index == nums.size()) {
            result.add(new ArrayList(nums));
            return;
        }

        Set<Integer> seen = new HashSet<>();
        for (int i = index; i < nums.size(); i++) {
            if (seen.add(nums.get(i))) {
                swap(nums, index, i);
                permutation(nums, index + 1, result);
                swap(nums, index, i);
            }
        }
    }

    private void swap(List<Integer> nums, int i, int j) {
        int temp = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, temp);
    }

    /**
     * 437. Binary Tree Path Sum To Target III
     * https://leetcode.com/problems/path-sum-iii/
     * Given the root of a binary tree and an integer targetSum, return the number
     * of paths where the sum of the values along the path equals targetSum.
     *
     * The path does not need to start or end at the root or a leaf, but it must
     * go downwards (i.e., traveling only from parent nodes to child nodes).
     *
     * Time = O(n) // n is number of nodes
     * Space = O(n) // hashmap
     */
    public int pathSumToTarget(TreeNode root, int targetSum) {
        if (root == null) {
            return 0;
        }

        int[] result = new int[1];
        Map<Integer, Integer> pathSumCount = new HashMap<>(); // <pathSum, count>

        pathSum(root, targetSum, 0, pathSumCount, result);

        return result[0];
    }

    private void pathSum(TreeNode root, int target, int preSum, Map<Integer, Integer> pathSumCount, int[] result) {
        preSum += root.val;
        if (preSum == target) {
            result[0]++;
        }

        int pathCount = pathSumCount.getOrDefault(preSum - target, 0);
        if (pathCount > 0) {
            result[0] += pathCount; // that's why need a map
        }

        pathSumCount.put(preSum, pathSumCount.getOrDefault(preSum, 0) + 1);

        if (root.left != null) {
            pathSum(root.left, target, preSum, pathSumCount, result);
        }
        if (root.right != null) {
            pathSum(root.right, target, preSum, pathSumCount, result);
        }

        pathSumCount.put(preSum, pathSumCount.get(preSum) - 1);
    }

    /**
     * 554. Brick Wall
     * https://leetcode.com/problems/brick-wall/
     * There is a rectangular brick wall in front of you with n rows of bricks.
     * The ith row has some number of bricks each of the same height (i.e., one unit)
     * but they can be of different widths. The total width of each row is the same.
     *
     * Draw a vertical line from the top to the bottom and cross the least bricks.
     * If your line goes through the edge of a brick, then the brick is not considered
     * as crossed. You cannot draw a line just along one of the two vertical edges of
     * the wall, in which case the line will obviously cross no bricks.
     *
     * Given the 2D array wall that contains the information about the wall, return
     * the minimum number of crossed bricks after drawing such a vertical line.
     *
     * Time = O(n ^ 2) // total number of bricks
     * Space = O(m) // m is the width of the wall
     */
    public int leastBricks(List<List<Integer>> wall) {
        Map<Integer, Integer> skipBrickCounter = new HashMap<>(); // <xCoordinate, skip count>
        int maxSkip = 0;
        for (List<Integer> row : wall) {
            // we are recording on which xCoordinate the vertical line is not
            // crossing a brick, which means the brick ends on that xCoordinate
            int xCoordinate = 0;
            // as required the line can't be edges of the wall
            for (int i = 0; i < row.size() - 1; i++) {
                xCoordinate += row.get(i);
                int newSkip = skipBrickCounter.getOrDefault(xCoordinate, 0) + 1;
                skipBrickCounter.put(xCoordinate, newSkip);
                maxSkip = Math.max(maxSkip, newSkip);
            }
        }
        return wall.size() - maxSkip;
    }

    /**
     * 581. Shortest Unsorted Continuous Subarray
     * https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
     * Given an integer array nums, you need to find one continuous subarray that if you
     * only sort this subarray in ascending order, then the whole array will be sorted in
     * ascending order.
     *
     * Return the shortest such subarray and output its length.
     *
     * Time = O(n)
     * Space = O(1) // if using stack then space is O(n)
     */
    public int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }

        int right = -1;
        int max = Integer.MIN_VALUE;
        // from left to right, find the right most element that smaller than the
        // largest element on it's left to set the right border
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < max) {
                right = i;
            }
            max = Math.max(max, nums[i]);
        }

        // from right to left, find the left most element that bigger than the
        // smallest element on it's right to set the left border
        int left = nums.length;
        int min = Integer.MAX_VALUE;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] > min) {
                left = i;
            }
            min = Math.min(min, nums[i]);
        }

        return Math.max(0, right - left + 1);
    }

    /**
     * 621. Task Scheduler
     * https://leetcode.com/problems/task-scheduler/
     * Given a characters array tasks, representing the tasks a CPU needs to do,
     * where each letter represents a different task. Tasks could be done in any
     * order. Each task is done in one unit of time. For each unit of time, the
     * CPU could complete either one task or just be idle.
     *
     * However, there is a non-negative integer n that represents the cool down
     * period between two same tasks (the same letter in the array), that is that
     * there must be at least n units of time between any two same tasks.
     *
     * Return the least number of units of times that the CPU will take to finish
     * all the given tasks.
     *
     * Time = O(nlogn) // for the sorting, if sorted then O(n)
     * Space = O(1)
     */
    public int leastInterval(char[] tasks, int n) {
        if (tasks == null || tasks.length == 0) {
            return 0;
        }
        if (tasks.length == 1 || n == 0) {
            return tasks.length;
        }

        int[] frequencies = new int[26];
        for (char c : tasks) {
            frequencies[c - 'A']++;
        }

        Arrays.sort(frequencies); // Comparator.reverseOrder() does not apply to primitive time

        int maxFrequency = frequencies[frequencies.length - 1];
        int idleTime = (maxFrequency - 1) * n;

        /* the processing time of all tasks is tasks.length
         * we only need to know how many idle slots are empty */
        for (int i = frequencies.length - 2; i >= 0; i--) {
            /* maxFrequency - 1 is the number of intervals
             * - if frequencies[i] <= maxFrequency - 1
             *   means it could fit in the slots between two same tasks,
             *   so we put all task i in the empty slots
             *   and idleTime subtract the empty slots it takes
             * - if frequencies == maxFrequency
             *   means it needs space after the last maxFrequency task
             *   after put all task i in the empty slots
             *   so idleTime only subtract the slot it takes */
            idleTime -= Math.min(maxFrequency - 1, frequencies[i]);
            if (idleTime < 0) {
                idleTime = 0;
                break;
            }
        }

        return tasks.length + idleTime;
    }

    /**
     * 669. Trim a Binary Search Tree
     * https://leetcode.com/problems/trim-a-binary-search-tree/
     * Given the root of a binary search tree and the lowest and highest boundaries
     * as low and high, trim the tree so that all its elements lies in [low, high].
     * Trimming the tree should not change the relative structure of the elements
     * that will remain in the tree (i.e., any node's descendant should remain a
     * descendant). It can be proven that there is a unique answer.
     *
     * Return the root of the trimmed binary search tree. Note that the root may
     * change depending on the given bounds.
     *
     * Time = O(n)
     * Space = O(n)
     */
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) {
            return null;
        }

        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);

        if (root.val > high) {
            return root.left;
        }
        if (root.val < low ) {
            return root.right;
        }

        return root;
    }

    /**
     * 1874. Minimize Product Sum of Two Arrays
     * https://leetcode.com/problems/minimize-product-sum-of-two-arrays/
     * The product sum of two equal-length arrays a and b is equal to the sum of a[i] * b[i]
     * for all 0 <= i < a.length (0-indexed). For example, if a = [1,2,3,4] and b = [5,2,3,1],
     * the product sum would be 1*5 + 2*2 + 3*3 + 4*1 = 22.
     *
     * Given two arrays nums1 and nums2 of length n, return the minimum product sum if you are
     * allowed to rearrange the order of the elements in nums1.
     *
     * Time = O(n + k) // k is the range of numbers, in this case is 100
     * Space = O(k)
     */
    public int minProductSum(int[] nums1, int[] nums2) {
        // Counting Sort
        int[] counter1 = new int[101];
        int[] counter2 = new int[101];

        for (int i = 0; i < nums1.length; i++) {
            counter1[nums1[i]]++;
            counter2[nums2[i]]++;
        }

        int i = 1;
        int j = counter2.length - 1;
        int sum = 0;

        while (true) {
            while (i < 101 && counter1[i] == 0) {
                i++;
            }
            while (j > 0 && counter2[j] == 0) {
                j--;
            }

            if (i == 101 || j == 0) {
                break;
            }

            int occ = Math.min(counter1[i], counter2[j]);

            sum += occ * i * j;
            counter1[i] -= occ;
            counter2[j] -= occ;
        }
        return sum;
    }
}
