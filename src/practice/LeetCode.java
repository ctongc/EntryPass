package practice;

import java.util.Arrays;

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
}
