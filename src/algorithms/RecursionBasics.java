package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecursionBasics {
    private RecursionBasics(){}

    /**
     * Get the Kth number in the Fibonacci Sequence.
     * K is 0-indexed, the 0th Fibonacci number is 0 and the 1st Fibonacci number is 1.
     * int K = n ( == 6)
     *    level1  level2  level3  level4          leveln
     * n    6       5       4       3               1
     *      1       2       4       8      ...    2 ^ (n - 1)  we run fibonacci() such times at such levels
     *    2 ^ 0 + 2 ^ 1 + 2 ^ 2 + 2 ^ 3 +  ...  + 2 ^ (n - 1) = O(2 ^ n)
     * Time = O(2 ^ n) // we total run fibonacci(n) O(2 ^ n) times, approximately O(branches^depth)
     * Space = O(n) // it's n levels
     */
    public int fibonacci(int K) {
        // 进入function之后首先check是否要停下
        if (K <= 0) {
            return 0;
        } else if (K == 1) {
            return 1;
        }
                                   // break point
        return fibonacci(K - 1) + fibonacci(K - 2);
    }

    /**
     * Evaluate a to the power of b, assuming both a and b are integers.
     * Time = O(log b) // notice it's 一叉树
     * Space = O(log b)
     */
    public long power(int a, int b) {
        if (a == 0) {
            return 0;
        } else if (b < 0) {
            return (long) (1.0 / (double) simplePower(a, -b)); // NOTICE using -b here
        } else {
            return simplePower(a, b);
        }
    }

    private long simplePower(int a, int b) {
        // need a long so that it won't be overflow
        if (b == 0) {
            return 1L; // long
        }

        /* a ^ b = a ^ (b / 2) * a ^ (b / 2)
         * 1 why not return power(a, b/2) * power(a, b/2)?
         *   runtime O(n) vs O(logn)
         * 2 remember int / int 是向下取整 */
        long half = simplePower(a, b / 2);
        return b % 2 == 0 ? half * half :  half * half * a;
    }

    /**
     * Spiral Order Traverse I
     * Traverse an N * N 2D array in spiral order clock-wise starting from the top left corner.
     * Return the list of traversal sequence.
     *
     * input = { {1,  2,  3},      size = 3
     *           {4,  5,  6},      size = 1
     *           {7,  8,  9} }
     *
     * the traversal sequence is [1, 2, 3, 6, 9, 8, 7, 4, 5]
     * Time = O(n * n) // n * n's element
     * Space = O(n) // n/2
     */
    public List<Integer> spiralOrderTraverse(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return result;
        }
        // offset = 0, size = N
        spiralTraverse(matrix, 0, matrix.length, result);
        return result;
    }

    private void spiralTraverse(int[][] matrix, int offset, int size, List<Integer> result) {
        /* base case
         * size = 1: 1 element left on the last spiral
         * size = 0: nothing left */
        if(size <= 1) {
            if (size == 1) {
                result.add(matrix[offset][offset]);
            }
            // print
            return;
        }

        /* offset is the [x] and [y] coordinates of the upper left corner of current box */

        // top row
        for(int i = 0; i < size - 1; i++) {
            result.add(matrix[offset][offset + i]);
        }
        // right column
        for(int i = 0; i < size - 1; i++) {
            result.add(matrix[offset + i][offset + size - 1]);
        }
        // bottom row
        for(int i = 0; i < size - 1; i++) {
            result.add(matrix[offset + size - 1][offset + size - 1 - i]);
        }
        // left column
        for(int i = 0; i < size - 1; i++) {
            result.add(matrix[offset + size - 1 - i][offset]);
        }

        // recursive rule
        spiralTraverse(matrix, offset + 1, size - 2, result);
    }

    /**
     * Spiral Order Traverse II
     * Traverse an M*N 2D array in spiral order clock-wise starting from the top left corner.
     * Return the list of traversal sequence.
     *
     * input = { {1,  2,  3,  4},      width = 4, height = 3
     *           {5,  6,  7,  8},      width = 2, height = 1
     *           {9, 10, 11, 12} }
     *
     * the traversal sequence is [1, 2, 3, 4, 8, 12, 11, 10 ,9, 5, 6 ,7, 8]
     * Time = O(m * n) // m * n's element
     * Space = O(n) // only for result list
     */
    public List<Integer> spiralOrderTraverse2(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return result;
        }
        int height = matrix.length;
        int width = matrix[0].length;

        int left = 0;
        int right = width - 1;
        int top = 0;
        int bottom = height - 1;

        while (left < right && top < bottom) {
            // top row
            for(int i = left; i < right; i++) {
                result.add(matrix[top][i]);
            }
            // right column
            for(int i = top; i < bottom; i++) {
                result.add(matrix[i][right]);
            }
            // bottom row
            for(int i = right; i > left; i--) {
                result.add(matrix[bottom][i]);
            }
            // left column
            for(int i = bottom; i > top; i--) {
                result.add(matrix[i][left]);
            }
            left++;
            right--;
            top++;
            bottom--;
        }
        // left >= right || top >= bottom

        /* case 1: left > right || top > bottom: nothing left
         * case 2: left == right && top == bottom: one element left
         * case 3: left == right && top < bottom: one column left
         * case 4: left < right && top == bottom: one row left */

        if (left == right && top == bottom) {
            result.add(matrix[left][top]);
        } else if (left == right && top < bottom) {
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][left]);
            }
        } else if (left < right && top == bottom) {
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
        }
        return result;
    }

    /**
     * Generate an M*N 2D array in spiral order clock-wise starting from the top left corner,
     * using the numbers of 1, 2, 3, …, M * N in increasing order.
     * input: M = 3, N = 4,
     *
     * the generated matrix is
     * { {1,  2,  3,  4}
     *   {10, 11, 12, 5},
     *   {9,  8,  7,  6} }
     *
     * Time = O(n * n) // n * n's element
     * Space = O(m) // if m > n
     */

    public int[][] spiralOrderGenerate(int m, int n) {
        int matrix[][] = new int[m][n];
        if (m == 0 || n == 0) {
            return matrix;
        }

        int counter = 1;
        // offset = 0, height = m, width = n
        spiralTraverse(0, m, n, counter, matrix);
        return matrix;
    }

    private void spiralTraverse(int offset, int m, int n, int counter, int[][] matrix) {
        /* base case
         * m == 0 || m == 0: nothing left
         * m == 1 && m == 1: one element left
         * m == 1 && n != 1: one column left
         * m != 1 && n == 1: one row left */
        if(m <= 1 || n <= 1) {
            if (m == 1 && n == 1) {
                matrix[offset][offset] = counter;
            } else if (n == 1 && m != 1) {
                for (int i = 0; i < m; i++) {
                    matrix[offset + i][offset] = counter++;
                }
            } else if (n != 1 && m == 1){
                for (int i = 0; i < n; i++) {
                    matrix[offset][offset + i] = counter++;
                }
            }
            // m == 0 && n == 0
            return;
        }

        /* offset is the [x] and [y] coordinates of the upper left corner */

        // top row
        for(int i = 0; i < n - 1; i++) {
            matrix[offset][offset + i] = counter++;
        }
        // right column
        for(int i = 0; i < m - 1; i++) {
             matrix[offset + i][offset + n - 1] = counter++;
        }
        // bottom row
        for(int i = 0; i < n - 1; i++) {
            matrix[offset + m - 1][offset + n - 1 - i] = counter++;
        }
        // left column
        for(int i = 0; i < m - 1; i++) {
            matrix[offset + m - 1 - i][offset] = counter++;
        }
        // recursive rule
        spiralTraverse(offset + 1, m - 2, n - 2, counter, matrix);
    }

    /**
     * Reverse pairs of elements in a singly-linked list.
     *
     * Input: 1 -> 2 -> 3 -> 4 -> 5 -> NULL
     * Output: 2 -> 1 -> 4 -> 3 -> 5 -> NULL
     */
    public ListNode reverseInPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = head.next;
        head.next = reverseInPairs(head.next.next);
        newHead.next = head;

        return newHead;
    }

    /**
     * Given a string and an abbreviation, return if the string matches the given abbreviation.
     * Word “book” can be abbreviated to 4, b3, b2k, etc.
     *
     * Time = O(n) // m + n
     * Space = O(n) // n > n
     */
    public boolean abbreviationMatching(String input, String pattern) {
        return isMatch(input, pattern, 0, 0);
    }

    private boolean isMatch(String input, String pattern, int i, int p) {
        // base case
        // only when we run out of input and pattern, there is a match
        if (i == input.length() && p == pattern.length()) {
            return true;
        }
        // if one reach the end but the other doesn't
        if (i >= input.length() || p >= pattern.length()) {
            return false;
        }

        /* recursive rule
         * case 1: pattern is not a digit
         * case 2: pattern is a digit */

        if (!Character.isDigit(pattern.charAt(p))) {
            if (input.charAt(i) != pattern.charAt(p)) {
                return false;
            } else {
                return isMatch(input, pattern, i + 1, p + 1);
            }
        } else {
            int num = 0;
            while (p < pattern.length() && Character.isDigit(pattern.charAt(p))) {
                num = num * 10 + (pattern.charAt(p) - '0');
                p++;
            }
            if (num + i > input.length()) {
                return false;
            } else {
                return isMatch(input, pattern, i + num, p);
            }
        }
    }

    /**
     * Given two nodes in a binary tree, find their lowest common ancestor.
     *
     * Time = O(n) // traverse n nodes, might less than n when find one earlier
     * Space = O(height)
     *
     * 1 If return other node, then we know:
     * 	- one and two are both in the tree
     * 	- one and two 不直接隶属
     * 2 If return one, then we know:  (same with return two)
     * 	- Either two 隶属于 one
     * 	- OR two is not in the tree  -- How do we know?
     * 		- we can run findNode(root = one, two)
     * OR just run LCA(root = one, two, two)
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode one, TreeNode two) {
        // base case: find nothing, find one, find two
        if (root == null || root == one || root == two) {
            return root;
        }
        /* Step 1: what do you expect from your leftChild/rightChild?
	     * Step 2: What do you want to do in the current layer?
	     * Step 3: What do you want to report to your parent? */

        // step 1
        TreeNode leftNode = lowestCommonAncestor(root.left, one, two);
        TreeNode rightNode = lowestCommonAncestor(root.right, one, two);

        /* Case 1 if left == null && right == null: return null to my parent
         * Case 2 if either left or right return Non-null: return the NON-null side to my parent
         * Case 3 if both left and right return NON-null (b and c are both found): return a */

        // step 2 & step 3
        if (leftNode == null && rightNode == null) {
            // case 1
            return null;
        } else if (leftNode != null && rightNode != null) {
            // case 3
            return root;
        } else {
            // case 2
            if (leftNode != null) {
                return leftNode;
            } else {
                return rightNode;
            }
        }
    }

    public static void main(String[] args) {
        RecursionBasics solution = new RecursionBasics();
        System.out.println(solution.fibonacci(5));
        int array[][] = solution.spiralOrderGenerate(4,2);
        for (int i = 0; i < 4; i++) {
            System.out.println(Arrays.toString(array[i]));
        }
        System.out.println(solution.abbreviationMatching("apple","a2l1"));
    }
}
