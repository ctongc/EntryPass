package algorithms;

import java.util.*;

public class DynamicProgramming {
    DynamicProgramming() {
    }

    /**
     * Get the Kth number in the Fibonacci Sequence
     *
     * index 0 1 2 3 4 5 ...   1000
     * F(n)  0 1 1 2 3 5 ... F(1000)
     *
     * Time = O(n) vs recursion O(2 ^ n)
     * Space = O(n)
     */
    public long fibonacci(int K) {
        long[] fibsFound = new long[K + 1]; // K + 1 since the base case is K = 0;
        fibsFound[0] = 0;
        fibsFound[1] = 1;
        for (int i = 2; i <= K; i++) {
            fibsFound[i] = fibsFound[i - 1] + fibsFound[i - 2];
        }
        return fibsFound[K];
    }

    /**
     * 0th 1th 2th 3th 4th
     * 0   1   1   2   3
     * dp solution with O(1) space
     */
    public long fibonacci2(int K) {
        if (K <= 0) {
            return 0;
        }
        long a = 0;
        long b = 1;
        int index = 2;
        while (index <= K) {
            long temp = a + b;
            a = b;
            b = temp;
            index++;
        }
        return b;
    }

    /**
     * Given an unsorted array, find the length of the longest subarray
     * in which the numbers are in ascending order.
     *
     * {7, 2, 3, 1, 5, 8, 9, 6}, longest ascending subarray is {1, 5, 8, 9}
     * length is 4
     *
     * Time = O(n)
     * Space = O(n)
     */
    public int longestAscendingSubarray(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        // M[i] represents from the 0-th element to the i-th element, include the i-th element
        // the max length of the ascending subarray
        int[] M = new int[array.length];
        M[0] = 1; // base case
        int globalMax = Integer.MIN_VALUE;

        /* M[i] = M[i - 1] + 1, if input[i] > input [i - 1]
         *      = 1           , otherwise */
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[i - 1]) {
                M[i] = M[i - 1] + 1;
                globalMax = Math.max(globalMax, M[i]);
            } else {
                M[i] = 1;
            }
        }
        return globalMax;
    }

    /**
     * Given an unsorted array, find the length of the longest subarray
     * in which the numbers are in ascending order.
     *
     * Time = O(n)
     * Space = O(1)
     */
    public int longestAscendingSubArray2(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int longestSubLength = 1;
        int currentLongest = 1;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[i - 1]) {
                longestSubLength = Math.max(longestSubLength, ++currentLongest);
            } else {
                currentLongest = 1;
            }
        }
        return longestSubLength;
    }

    /**
     * Given a rope with positive integer-length n, how to cut the rope
     * into m integer-length parts with length p[0], p[1], ...,p[m-1],
     * in order to get the maximal product of p[0]p[1] ... p[m-1]?
     * m must be greater than 2 (at least one cut must be made).
     *
     * 左大段 + 右小段(不可再分)
     * M[5] = case 1 : max(4, M[4]) * 1 (meter)
     * case 2 : max(3, M[3]) * 2 (meter)  - 2 meter需要的分法已包含在case1里
     * case 3 : max(2, M[2]) * 3 (meter)
     * case 4 : max(1, M[1]) * 4 (meter)
     * M[5] = max(case1, case2, case3, case4)
     *
     * 每个case需要 max(i - j, M[i - j]) 是因为每个subcut可以选择不切
     *
     * Time = O(n^2)
     * Space = O(n)
     */
    public int maxProductOfCuttingRope(int length) {
        if (length < 2) {
            return 0;
        }
        // M[i] represent the maxProduct of length i's rope
        // a.k.a when the rope could be cut i-1 times
        int[] M = new int[length + 1];

        // base case, which doesn't make any sense tho
        M[0] = 0;
        M[1] = 0;

        // 左大段 + 右小段
        for (int i = 2; i <= length; i++) {
            int curMax = 0;
            for (int j = 1; j < i; j++) {
                curMax = Math.max(curMax, Math.max(i - j, M[i - j]) * j); // Math.max(j, M[j]) * (i - j))
            }
            M[i] = curMax;
        }
        return M[length];
    }

    /**
     * Given a word and a dictionary, determine if it can be composed by concatenating words from the given dictionary.
     *
     * Dictionary: {“bob”, “cat”, “rob”}
     * Word: “robob” return false
     * Word: “robcatbob” return true since it can be composed by "rob", "cat", "bob"
     *
     * Time = O(n^3) // O(n^2) * substring(j,i) API takes anther O(n).
     * Space = O(n)
     */
    public boolean canBreakIntoDict(String input, String[] dict) {
        if (dict == null || dict.length == 0
                || input == null || input.length() == 0) {
            return false;
        }

        /* M[i] means if the string built by the 1-th (input[0]) to the i-th (input[i - 1]) character
         * could be composed from the given dictionary, which is substring(0, i)
         *
         * index   0 1 2 3 4 5 input[i - 1]
         * S  =    r o b o b
         * M[i]  0 1 2 3 4 5   i-th letter */

        boolean[] M = new boolean[input.length() + 1]; // since include index 0, and 1 to length
        // all default values are false so M[0] == false; // base case
        Set<String> dictionary = new HashSet<>(Arrays.asList(dict)); // O(1)

        /* M[全段] = no cut || (M[左大段] && dict.contains(右小段))
         * M[i] = no cut OR {M[j] AND check if substring(j…i) is in the dict} */

        for (int i = 1; i <= input.length(); i++) {
            // if current word is in the dictionary, done for current
            if (dictionary.contains(input.substring(0, i))) {
                // substring(0, 1) = first letter
                M[i] = true;
                continue; // skip, 继续看下一个字母
            }
            // otherwise, check the possible single splits
            for (int j = 1; j < i; j++) { // check sub-problems and check the rest of the word
                // M[i - j] && substring(i - j, i)
                // M[5]: M[4] && 5-th letter input[4]
                if (M[i - j] && dictionary.contains(input.substring(i - j, i))) {
                    // same as M[j] && dictionary.contains(input.substring(j, i))
                    M[i] = true;
                    break; // terminate the loop, 继续看下一个字母
                }
            }
        }
        return M[input.length()];
    }

    /**
     * Given an array of non-negative integers, you are initially positioned at index 0 of the array.
     * array[i] means the maximum jump distance from that position (you can only jump towards the end of the array).
     * Determine if you are able to reach the last index.
     *
     * Index    0  1  2  3  4
     * Input = {2, 3, 1, 1, 4}
     * M[]   =  T  T  T  T  T
     * <- DP可以从左往右run，也可以从右往左run
     * Time = O(n ^ 2)
     * Space = O(n)
     */
    public boolean canJumpToLast(int[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        // M[i] represents if could finally jump to last index from position i
        boolean[] M = new boolean[array.length]; // don't need + 1 every time
        M[array.length - 1] = true; // base case, since already at last index
        /* run DP from right to left
         * M[i] = true   if i + array[i] >= array.length - 1
                            or there exists an element j, where i < j <= i + array[i] AND array[j] = true
         * M[i] = false  otherwise */
        for (int i = array.length - 2; i >= 0; i--) {
            if (i + array[i] >= array.length - 1) {
                M[i] = true;
            } else {
                for (int j = i + 1; j <= i + array[i]; j++) { // 等号不能少
                    if (M[j]) { // array[j] can jump out
                        M[i] = true;
                        break;
                    }
                }
            }
        }
        return M[0];
    }

    public boolean canJumpToLast2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int furthestPosCanReach = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (i > furthestPosCanReach) {
                return false;
            }
            furthestPosCanReach = Math.max(furthestPosCanReach, i + nums[i]);
        }
        return true;
    }

    /**
     * Given an array A of non-negative integers, A[i] means the maximum jump distance from index i
     * (you can only jump towards the end of the array). Determine the minimum number of jumps you need
     * to reach the end of array. If you can not reach the end of the array, return -1.
     */
    public int minJumpsToLast(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }

        // M[i] represents the min steps needed to jump
        // from the i-th position to the array.length - 1 index
        int[] M = new int[array.length];


        M[array.length - 1] = 0; // base case

        // run DP from right to left
        for (int i = array.length - 2; i >= 0; i--) {
            M[i] = -1; // set it to unreachable
            // get min(minStep[i+1], ..., minStep[j]), where i < j <= (i + array[i] && array.length - 1)
            for (int j = i; j <= i + array[i] && j <= array.length - 1; j++) {
                // when reach a position j that could reach the last index from it
                // if smallest hasn't been set or it is the smallest
                // set minStep[i] = minStep[j] + 1
                if (M[j] != -1 && (M[i] == -1 || M[j] < M[i])) {
                    M[i] = M[j] + 1;
                }
            }
        }
        return M[0];
    }

    /**
     * Given an unsorted integer array, find the subarray that has the greatest sum. Return the sum.
     *
     * Input = {1, 2, 4, -1, -2, -100, -100}
     *  M[i] =  1  3  7   6   4   -96  -100
     *
     * Time = O(n)
     * Space = O(n)
     */
    public int largestSubarraySum(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        // M[i] represents the value of the largest sum [from 0-th element to the i-th element]
        // including the i-th element
        int[] M = new int[array.length];
        M[0] = array[0]; // base case
        int globalMax = array[0];

        /* M[i] = M[i-1] + input[i]        if M[i-1] >= 0
         *        input[i]                 otherwise */
        for (int i = 1; i < array.length; i++) {
            if (M[i - 1] >= 0) {
                M[i] = M[i - 1] + array[i];
            } else {
                M[i] = array[i];
            }
            globalMax = Math.max(globalMax, M[i]);
        }

        return globalMax;
    }

    /**
     * DP solution but reduce Space to O(1)
     */
    public int largestSubarraySum2(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int curMax = array[0];
        int globalMax = array[0];

        for (int i = 1; i < array.length; i++) {
            curMax = Math.max(curMax + array[i], array[i]);
            globalMax = Math.max(globalMax, curMax);
        }

        return globalMax;
    }

    /**
     * Given an unsorted integer array, find the subarray that has the greatest sum.
     * Return the sum and the index of left & right border.
     * If there are multiple solutions, return the leftmost subarray.
     */
    public int[] largestSumWithBorder(int[] array) {
        if (array == null || array.length == 0) {
            return new int[]{0, 0, 0};
        }
        // M represents the largest sum from index 0 to index i
        // including the element at index i
        int[] M = new int[array.length];
        int globalMax = array[0];
        M[0] = array[0]; // base case

        int curLeft = 0; // cur right is i
        int solLeft = 0;
        int solRight = 0;

        for (int i = 1; i < array.length; i++) {

            /* When to change the current left border?
             * If lastMax < 0 (i.e we reset M[i] = input[i]), reset curLeft = i */
            if (M[i - 1] >= 0) {
                M[i] = M[i - 1] + array[i];
            } else {
                M[i] = array[i];
                curLeft = i;
            }

            /* When to update solLeft, solRight?
             * Whenever we update the globalMax, set
             *   - solLeft = curLeft
             *   - solRight = i */
            if (globalMax < M[i]) {
                globalMax = M[i];
                solLeft = curLeft;
                solRight = i;
            }
        }

        return new int[]{globalMax, solLeft, solRight};
    }

    /**
     * Reduce space to O(1)
     */
    public int[] largestSumWithBorder2(int[] array) {
        if (array == null || array.length == 0) {
            return new int[]{0, 0, 0};
        }

        int globalMax = Integer.MIN_VALUE;
        int curMax = array[0];

        int curLeft = 0;
        int solLeft = 0;
        int solRight = 0;

        for (int i = 1; i < array.length; i++) {
            if (curMax >= 0) {
                curMax = curMax + array[i];
            } else {
                curMax = array[i];
                curLeft = i;
            }

            if (globalMax < curMax) {
                globalMax = curMax;
                solLeft = curLeft;
                solRight = i;
            }
        }

        return new int[]{globalMax, solLeft, solRight};
    }


    /**
     * Given two strings of alphanumeric characters, determine the minimum number of
     * Replace, Delete, and Insert operations needed to transform one string into the other.
     */
    public int editDistance(String one, String two) {
        if (one == null || two == null) {
            return -1;
        }
        // M[i][j] represents the minimum operation needed to transform from
        // String one[0..i-1] to String two[0..j-1]
        int[][] M = new int[one.length() + 1][two.length() + 1];

        /* if (one[i - 1] = two[j - 1]
         * 	case 0: M[i][j] = M[i - 1][j - 1] // do nothing
         * otherwise
         *  case 1 replace: 1 + M[i - 1][j - 1] // one operation
         *	case 2 delete: 1 + M[i - 1][j]
         *  case 3 insert: 1 + M[i][j - 1]
         *  M[i][j] = min(case 1, case 2, case 3) */

        for (int i = 0; i <= one.length(); i++) {
            for (int j = 0; j <= two.length(); j++) {
                if (i == 0) {
                    // base case
                    // "" to two.substring(0, j)
                    M[i][j] = j;
                } else if (j == 0) {
                    // base case
                    // one.substring(0, i) to ""
                    M[i][j] = i;
                } else if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    M[i][j] = M[i - 1][j - 1];
                } else {
                    M[i][j] = 1 + Math.min(Math.min(M[i][j - 1], M[i - 1][j]), M[i - 1][j - 1]);
                }
            }
        }
        return M[one.length()][two.length()];
    }

    /**
     * Determine the largest square of 1s in a binary matrix (a binary matrix only contains 0 and 1)
     * return the length of the largest square.
     *
     * Assumptions
     * The given matrix is not null and guaranteed to be of size N * N, N >= 0
     * Examples
     * { {0, 0, 0, 0},
     *   {1, 1, 1, 1},
     *   {0, 1, 1, 1},
     *   {1, 0, 1, 1} }
     *
     * the largest square of 1s has length of 2
     *
     * Time = O(n^2)
     * Space = O(n^2) -> optimized to O(n)
     */
    public int largestSquareOfOnes(int[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int globalMax = 0;
        // M[i][j] represents the largest square of 1's
        // with right bottom corner as matrix[i][j]
        int[][] M = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i == 0 || j == 0) { // base case
                    if (matrix[i][j] == 1) {
                        // on the edge, 1 square (itself) at most if matrix[i][j] = 1
                        M[i][j] = 1;
                    }
                } else if (matrix[i][j] == 1) { // if == 0, matrix[i][j] == 0 already assigned
                    /* M[i][j] = 0                                             if A[i][j] == 0
                               = min(M[i-1][j-1], M[i-1][j], M[i][j-1]) + 1    if A[i][j] == 1 */
                    M[i][j] = 1 + Math.min(M[i - 1][j - 1], Math.min(M[i - 1][j], M[i][j - 1]));
                }
                globalMax = Math.max(globalMax, M[i][j]);
            }
        }
        return globalMax;
    }

    /**
     * Determine the largest square surrounds by 1s in a binary matrix (a binary matrix only contains 0 and 1)
     * return the length of the largest square.
     *
     * Assumptions
     * The given matrix is not null and guaranteed to be of size N * N, N >= 0
     * Examples
     * { {1, 1, 1, 0},
     *   {1, 0, 1, 1},
     *   {1, 1, 1, 1},
     *   {1, 0, 1, 1} }
     *
     * the largest square surrounds by 1s is 3
     *
     * Time = O(n^3)
     * Space = O(n^2)
     */
    public int largestSquareSurroundsByOnes(int[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int globalMax = 0;
        /* leftToRight[i][j] represents how many consecutive 1s
         * at matrix[i][j] from the matrix[i][0]
         * and leftToRight[i][j] is mapped to matrix[i - 1][j - 1]
         * to reduce corner cases */
        int[][] leftToRight = new int[matrix.length + 1][matrix[0].length + 1];
        int[][] topToBottom = new int[matrix.length + 1][matrix[0].length + 1];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    leftToRight[i + 1][j + 1] = leftToRight[i + 1][j] + 1;
                    topToBottom[i + 1][j + 1] = topToBottom[i][j + 1] + 1;
                    /* since we do the consecutive 1s from left to right and top to bottom
                     * we only need to check the longest left arm length from top right
                     * and the longest up arm length from bottom left
                     * ------->
                     *  1  [2]   |
                     * [3] (i,j) |
                     *           v
                     * for (i,j), check 2 from left to right
                     * check 3 from top to bottom */
                    for (int maxLength = Math.min(leftToRight[i + 1][j + 1], topToBottom[i + 1][j + 1]);
                         maxLength >= 1; maxLength--) {
                        if (leftToRight[i + 2 - maxLength][j + 1] >= maxLength
                                && topToBottom[i + 1][j + 2 - maxLength] >= maxLength) {
                            globalMax = Math.max(globalMax, maxLength);
                            break;
                        }
                    }
                }
            }
        }
        return globalMax;
    }

    /**
     * Given an array containing only 0s and 1s, find the length of the longest subarray of consecutive 1s
     *
     * Time = O(n)
     * Space = O(n)
     */

    public int longestConsecutiveOnes(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int globalMax = array[0];
        // M[i] represents from the 0th element to the
        // i-th element (including i) the longest contiguous 1s
        int[] M = new int[array.length];
        M[0] = array[0]; // Base case:

        for (int i = 1; i < array.length; i++) {
            /* M[i] = 0           if A[i] = 0
             *      = M[i-1] + 1, if A[i] = 1 */
            if (array[i] == 0) {
                M[i] = 0;
            } else {
                M[i] = M[i - 1] + 1;
            }
            globalMax = Math.max(globalMax, M[i]);
        }
        return globalMax;
    }

    /**
     * Reduce Space to O(1)
     */
    public int longestConsecutiveOnes2(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }

        int curMax = array[0];
        int globalMax = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] == 0) {
                curMax = 0;
            } else {
                curMax += array[i];
            }
            globalMax = Math.max(globalMax, curMax);
        }
        return globalMax;
    }

    /**
     * Given a matrix that contains only 1s and 0s, find the largest cross which contains only 1s
     * with the same arm lengths and the four arms joining at the central point.
     * Return the arm length of the largest cross.
     *
     *
     * { {0, 0, 0, 0},
     *   {1, 1, 1, 1},
     *   {0, 1, 1, 1},
     *   {1, 0, 1, 1} }
     *
     * the largest cross of 1s has arm length 2.
     */
    public int largestCrossOfOnes(int[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;

        // M[i][j] represents the longest consecutive 1s at [i][j] of that direction
        int[][] leftToRight = new int[row][col];
        int[][] rightToLeft = new int[row][col];
        int[][] topToBottom = new int[row][col];
        int[][] bottomToTop = new int[row][col];

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                // fill the left to right matrix M1
                if (c == 0) {
                    // base case: left most column
                    leftToRight[r][c] = matrix[r][c];
                } else {
                    if (matrix[r][c] == 0) {
                        leftToRight[r][c] = 0;
                    } else {
                        leftToRight[r][c] = leftToRight[r][c - 1] + 1;
                    }
                }

                // fill the top to bottom matrix M2
                if (r == 0) {
                    // base case: top most row
                    topToBottom[r][c] = matrix[r][c];
                } else {
                    if (matrix[r][c] == 0) {
                        topToBottom[r][c] = 0;
                    } else {
                        topToBottom[r][c] = topToBottom[r - 1][c] + 1;
                    }
                }
            }
        }

        for (int r = row - 1; r >= 0; r--) {
            for (int c = col - 1; c >= 0; c--) {
                // fill the right to left matrix M3
                if (c == col - 1) {
                    // base case: right most column
                    rightToLeft[r][c] = matrix[r][c];
                } else {
                    if (matrix[r][c] == 0) {
                        rightToLeft[r][c] = 0;
                    } else {
                        rightToLeft[r][c] = rightToLeft[r][c + 1] + 1;
                    }
                }

                // fill the bottom to top matrix M4
                if (r == row - 1) {
                    // base case: bottom most row
                    bottomToTop[r][c] = matrix[r][c];
                } else {
                    if (matrix[r][c] == 0) {
                        bottomToTop[r][c] = 0;
                    } else {
                        bottomToTop[r][c] = bottomToTop[r + 1][c] + 1;
                    }
                }
            }
        }

        int largestArm = 0;
        int[][] curLargestArm = new int[row][col];
        // merge: M[r][c] = min(M1[r][c], M2[r][c], M3[r][c], M4[r][c])
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                curLargestArm[r][c] = Math.min(leftToRight[r][c], rightToLeft[r][c]);
                curLargestArm[r][c] = Math.min(topToBottom[r][c], curLargestArm[r][c]);
                curLargestArm[r][c] = Math.min(bottomToTop[r][c], curLargestArm[r][c]);
                // after merging, get the current max arm length from [0][0] to [r][c]
                largestArm = Math.max(curLargestArm[r][c], largestArm);
            }
        }
        return largestArm;
    }

    /**
     * Given a matrix that contains only 1s and 0s, find the largest X shape which contains only 1s
     * with the same arm lengths and the four arms joining at the central point.
     * Return the arm length of the largest X shape.
     *
     *
     * { {0, 0, 0, 0},
     *   {1, 1, 1, 1},
     *   {0, 1, 1, 1},
     *   {1, 0, 1, 1} }
     *
     * the largest X shape of 1s has arm length 2.
     */
    public int largestXOfOnes(int[][] matrix) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;

        // M[i][j] represents the longest consecutive 1s at [i][j] of that direction
        int[][] topLToBotR = new int[row][col];
        int[][] botRToTopL = new int[row][col];
        int[][] topRToBotL = new int[row][col];
        int[][] botLToTopR = new int[row][col];

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                // fill the topLeft to bottomRight matrix M1
                if (r == 0 || c == 0) {
                    // base case: top most row && left most column
                    topLToBotR[r][c] = matrix[r][c];
                } else {
                    if (matrix[r][c] == 0) {
                        topLToBotR[r][c] = 0;
                    } else {
                        topLToBotR[r][c] = topLToBotR[r - 1][c - 1] + 1;
                    }
                }

                // fill the topRight to bottomLeft matrix M2
                if (r == 0 || c == col - 1) {
                    // base case: top most row && right most column
                    topRToBotL[r][c] = matrix[r][c];
                } else {
                    if (matrix[r][c] == 0) {
                        topRToBotL[r][c] = 0;
                    } else {
                        topRToBotL[r][c] = topRToBotL[r - 1][c + 1] + 1;
                    }
                }
            }
        }

        for (int r = row - 1; r >= 0; r--) {
            for (int c = col - 1; c >= 0; c--) {
                // fill the bottomLeft to topRight matrix M3
                if (r == row - 1 || c == 0) {
                    // base case: bottom most row && left most column
                    botLToTopR[r][c] = matrix[r][c];
                } else {
                    if (matrix[r][c] == 0) {
                        botLToTopR[r][c] = 0;
                    } else {
                        botLToTopR[r][c] = botLToTopR[r + 1][c - 1] + 1;
                    }
                }

                // fill the bottomRight to topLeft matrix M4
                if (r == row - 1 || c == col - 1) {
                    // base case: bottom most row && right most column
                    botRToTopL[r][c] = matrix[r][c];
                } else {
                    if (matrix[r][c] == 0) {
                        botRToTopL[r][c] = 0;
                    } else {
                        botRToTopL[r][c] = botRToTopL[r + 1][c + 1] + 1;
                    }
                }
            }
        }

        int largestArm = 0;
        int[][] curLargestArm = new int[row][col];
        // merge: M[r][c] = min(M1[r][c], M2[r][c], M3[r][c], M4[r][c])
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                curLargestArm[r][c] = Math.min(topLToBotR[r][c], topRToBotL[r][c]);
                curLargestArm[r][c] = Math.min(botLToTopR[r][c], curLargestArm[r][c]);
                curLargestArm[r][c] = Math.min(botRToTopL[r][c], curLargestArm[r][c]);
                // after merging, get the current max arm length from [0][0] to [r][c]
                largestArm = Math.max(curLargestArm[r][c], largestArm);
            }
        }
        return largestArm;
    }

    /**
     * Given a matrix that contains integers, find the sub-matrix with the largest sum.
     *
     * Return the sum of the sub-matrix.
     *
     * Time = O(n^3)
     */
    public int largestSubmatrixSum(int[][] matrix) {
        int globalMax = Integer.MIN_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            int[] cur = new int[matrix[0].length];
            for (int j = i; j < matrix.length; j++) {
                /* for each row i, we add the rows one by one for row j*/
                for (int k = 0; k < cur.length; k++) {
                    cur[k] += matrix[j][k];
                }
                globalMax = Math.max(globalMax, largestSubarraySum2(cur));
            }
        }
        return globalMax;
    }


    public boolean containsPairSumToZero(int[] array, int i, int j) {
        // assumption: array has at least two elements and i,j both within [0, array.length - 1]
        int[] minIndex = getLargestSmallerValidIndex(array);
        for(int a : minIndex) {
            System.out.print(a+"， ");
        }
        return minIndex[j + 1] >= i;
    }

    private int[] getLargestSmallerValidIndex(int[] array) {
        Map<Integer, Integer> lookup = new HashMap<>(); // stores viewed elements and their indices
        // M[i] stores the largest smaller index which from 0 to i - i that there is a pair could sum to zero
        // if M[i] = -1 then there is no pair from 0 to i - 1 could sum to 0
        int[] M = new int[array.length + 1]; // + 1 to deal with corner case
        M[0] = -1; // base case
        for (int i = 1; i <= array.length; i++) {
            M[i] = -1;
            Integer index = lookup.get(0 - array[i - 1]);
            M[i] = index == null ? M[i] = Math.max(M[i - 1], M[i]) : Math.max(M[i - 1], index);
            lookup.put(array[i - 1], i - 1); // update an element with the most recent(largest) index
        }
        return M;
    }

    public static void main(String[] args) {
        DynamicProgramming dp = new DynamicProgramming();
        long fibonacciResult = dp.fibonacci(5);
        System.out.println(fibonacciResult);

        int[] array = {7, 2, 3, 1, 5, 8, 9, 6};
        int longestAscendingSubArrayResult = dp.longestAscendingSubarray(array);
        System.out.println(longestAscendingSubArrayResult);

        int maxProductOfCuttingRopeResult = dp.maxProductOfCuttingRope(100);
        System.out.println(maxProductOfCuttingRopeResult);

        int[] array2 = {3, 3, 1, 0, 4};
        dp.minJumpsToLast(array2);

        int[] array3 = {4, 2, -3, -2, 3, -1, -2, 6};
        System.out.println(Arrays.toString(dp.largestSumWithBorder2(array3)));

        String s = "abc";
        System.out.println(s.substring(1, 2));

        int[][] array4 = {{1, 1, 1, 0}, {1, 0, 1, 1}, {1, 1, 1, 1}, {1, 0, 1, 1}};
        System.out.println(dp.largestSquareSurroundsByOnes(array4));


        int[] array5 = {0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1};
        System.out.println("1: " + dp.longestConsecutiveOnes(array5));
        System.out.println("2: " + dp.longestConsecutiveOnes2(array5));

        int[][] array6 = {{0, 0, 0, 0}, {1, 1, 1, 1}, {0, 1, 1, 1}, {1, 0, 1, 1}};
        System.out.println(dp.largestCrossOfOnes(array6));

        System.out.println(dp.editDistance("aaa", "bbb"));
        System.out.println(dp.canJumpToLast(new int[]{0}));
    }
}
