package algorithms;

import java.util.*;

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
        // base case. 进入function之后首先check是否要停下
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
        // assume b >= 0, and if a = 0, b > 0
        if (a == 0) {
            // b <= 0 -> error
            return 0;
        } else if (b < 0) {
            return (long) (1.0 / (double)powerHelper(a, -b)); // NOTICE using -b here
        } else {
            return powerHelper(a, b);
        }
    }

    private long powerHelper(int a, int b) {
        // need a long so that it won't be overflow
        if (b == 0) {
            return 1L; // long
        }
        /* a ^ b = a ^ (b / 2) * a ^ (b / 2)
         * 1 why not return power(a, b/2) * power(a, b/2)?
         *   runtime O(n) vs O(logn) 后一半无需重复计算
         * 2 remember int / int 是向下取整 */
        long halfResult = powerHelper(a, b / 2);
        return b % 2 == 0 ? halfResult * halfResult : halfResult * halfResult * a;
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
        int[][] matrix = new int[m][n];
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
        if (m <= 1 || n <= 1) {
            if (m == 1 && n == 1) {
                matrix[offset][offset] = counter;
            } else if (n == 1) {
                for (int i = 0; i < m; i++) {
                    matrix[offset + i][offset] = counter++;
                }
            } else if (m == 1){
                for (int i = 0; i < n; i++) {
                    matrix[offset][offset + i] = counter++;
                }
            }
            // m == 0 && n == 0
            return;
        }

        /* offset is the [x] and [y] coordinates of the upper left corner */

        // top row
        for (int i = 0; i < n - 1; i++) {
            matrix[offset][offset + i] = counter++;
        }
        // right column
        for (int i = 0; i < m - 1; i++) {
             matrix[offset + i][offset + n - 1] = counter++;
        }
        // bottom row
        for (int i = 0; i < n - 1; i++) {
            matrix[offset + m - 1][offset + n - 1 - i] = counter++;
        }
        // left column
        for (int i = 0; i < m - 1; i++) {
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
     * Given a binary tree where all the right nodes are leaf nodes,
     * flip it upside down and turn it into a tree with left leaf nodes
     */
    public TreeNode reverseTree(TreeNode root) {
        if (root == null || root.left == null) {
            return root;
        }

        TreeNode newRoot = reverseTree(root.left);
        root.left.left = root.right;
        root.left.right = root;
        root.left = null;
        root.right = null;

        return newRoot;
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

    private boolean isMatch(String input, String pattern, int textStart, int patternStart) {
        // base case
        // only when we run out of input and pattern, there is a match
        if (textStart == input.length() && patternStart == pattern.length()) {
            return true;
        }
        // if one reach the end but the other doesn't
        if (textStart >= input.length() || patternStart >= pattern.length()) {
            return false;
        }

        /* recursive rule
         * case 1: pattern is not a digit
         * case 2: pattern is a digit */
        if (!Character.isDigit(pattern.charAt(patternStart))) {
            if (input.charAt(textStart) != pattern.charAt(patternStart)) {
                return false;
            } else {
                return isMatch(input, pattern, textStart + 1, patternStart + 1);
            }
        } else {
            int num = 0;
            while (patternStart < pattern.length() && Character.isDigit(pattern.charAt(patternStart))) {
                num = num * 10 + (pattern.charAt(patternStart) - '0');
                patternStart++;
            }
            if (num + textStart > input.length()) {
                return false;
            } else {
                return isMatch(input, pattern, textStart + num, patternStart);
            }
        }
    }

    /**
     * Given two nodes in a binary tree, find their lowest common ancestor
     *
     * Time = O(n) -> traverse n nodes, might less than n when find one earlier
     * Space = O(height)
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode one, TreeNode two) {
        // base case: find nothing, find one, find two
        if (root == null || root == one || root == two) {
            return root;
        }

        TreeNode leftNode = lowestCommonAncestor(root.left, one, two);
        TreeNode rightNode = lowestCommonAncestor(root.right, one, two);

        /* Case 1 if left == null && right == null -> return null to my parent
         * Case 2 if either left or right returns NON-null -> return the NON-null side to my parent
         * Case 3 if both left and right return NON-null (both found) -> return root */

        // case 1
        if (leftNode != null && rightNode != null) {
            return root;
        }

        // case 2 & case 3
        return leftNode != null ? leftNode : rightNode;
    }

    /**
     * Find distance between two given keys of a Binary Tree
     * Find the distance between two nodes in a binary tree, no parent pointers are given
     * Distance between two nodes is the minimum number of edges
     * to be traversed to reach one node from another
     *
     * Time = O(n)
     * Space = O(height)
     */
    public int distanceFromTwoGivenKeys(TreeNode root, int k1, int k2) {
        int[] nodeLevels = {-1, -1, -1};
        lcaWithHeight(root, k1, k2, 0, nodeLevels);
        int k1Level = nodeLevels[1];
        int k2Level = nodeLevels[2];
        int lcaLevel = nodeLevels[0] == -1 ? Math.min(k1Level, k2Level) : nodeLevels[0];
        return k1Level + k2Level - 2 * lcaLevel;

    }

    private TreeNode lcaWithHeight(TreeNode root, int k1, int k2, int level, int[] nodeLevels) {
        if (root == null) {
            return root;
        }

        TreeNode leftNode = lcaWithHeight(root.left, k1, k2, level + 1, nodeLevels);
        TreeNode rightNode = lcaWithHeight(root.right, k1, k2, level + 1, nodeLevels);

        if (leftNode != null && rightNode != null) {
            nodeLevels[0] = level;
            return root;
        } else if (root.key == k1) {
            nodeLevels[1] = level;
            return root;
        } else if (root.key == k2) {
            nodeLevels[2] = level;
            return root;
        }

        return leftNode != null ? leftNode : rightNode;
    }

    /**
     * Determine whether a binary tree is a balanced binary tree
     * O(nlogn) solution
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        int diff = Math.abs(getHeight(root.left) - getHeight(root.right));
        if (diff > 1) {
            return false;
        }
        return isBalanced(root.left) && isBalanced(root.right);
    }

    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    /**
     * Determine whether a binary tree is a balanced binary tree
     * O(n) solution
     */
    public boolean isBalanced2 (TreeNode root) {
        return checkHeight(root) != -1;
    }

    private int checkHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        // step 1
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);

        // step 2
        if (leftHeight == -1 || rightHeight == -1
                || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        // step 3
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Max path sum from leaf to leaf
     * Given a binary tree in which each node element contains a number
     * Find the maximum possible sum from one leaf node to another
     * The maximum sum path may or may not go through root
     *
     * Time = O(n)
     * Space = O(height)
     */
    public int maxPathSumFromLeafToLeaf(TreeNode root) {
        int[] result = new int[]{ Integer.MIN_VALUE };
        getMaxPathSum(root, result);
        return result[0];
    }

    private int getMaxPathSum(TreeNode root, int[] result) {
        if (root == null) {
            return 0;
        }

        // step 1
        int leftPathSum = getMaxPathSum(root.left, result);
        int rightPathSum = getMaxPathSum(root.right, result);

        // step 2 - both child need to be non-null to update the globalMax
        if (root.left != null && root.right != null) {
            result[0] = Math.max(result[0], leftPathSum + rightPathSum + root.key);
        }

        // step 3
        if (root.left == null) {
            return rightPathSum + root.key;
        } else if (root.right == null) {
            return leftPathSum + root.key;
        }
        return Math.max(leftPathSum, rightPathSum) + root.key;
    }

    /**
     * Max path sum from any node to any node
     * Get maximum sum of the path cost from **any node to any node
     *
     * Time = O(n)
     * Space = O(height)
     */
    public int maxPathSumFromAnyToAny(TreeNode root) {
        int[] result = new int[]{ Integer.MIN_VALUE };
        getMaxPathSum2(root, result);
        return result[0];
    }

    private int getMaxPathSum2(TreeNode root, int[] result) {
        if (root == null) {
            return 0;
        }

        // step 1
        int leftPathSum = getMaxPathSum2(root.left, result);
        int rightPathSum = getMaxPathSum2(root.right, result);

        // step 2
        result[0] = Math.max(result[0], Math.max(leftPathSum, 0) + Math.max(rightPathSum, 0) + root.key);

        // step 3, care if one child is null the other is negative
        return Math.max(leftPathSum, rightPathSum) + root.key;
    }

    /**
     * Find maximum path cost for all paths from leaf to root in a Binary Tree
     *
     * Time = O(n^2)
     * Space = O(height)
     */
    public int maxPathCost(TreeNode root) {
        int[] result = new int[]{ Integer.MIN_VALUE };
        getMaxPathCostWithPrefix(root, new ArrayList<>(), result);
        return result[0];
    }

    private void getMaxPathCostWithPrefix(TreeNode root, List<Integer> pathPrefix, int[] globalMax) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            int pathSum = 0;
            for (int i : pathPrefix) {
                pathSum += i;
            }
            globalMax[0] = Math.max(globalMax[0], pathSum + root.key);
            return;
        }

        // recursive rule
        pathPrefix.add(root.key);
        getMaxPathCostWithPrefix(root.left, pathPrefix, globalMax);
        getMaxPathCostWithPrefix(root.right, pathPrefix, globalMax);
        pathPrefix.remove(pathPrefix.size() - 1);
    }

    /**
     * Find maximum path cost for all paths from leaf to root in a Binary Tree
     *
     * Time = O(n)
     * Space = O(height)
     */
    public int maxPathCost2(TreeNode root) {
        int[] result = new int[]{ Integer.MIN_VALUE };
        getMaxPathCostWithPrefix2(root, 0, result);
        return result[0];
    }

    private void getMaxPathCostWithPrefix2(TreeNode root, int prefixSum, int[] globalMax) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            globalMax[0] = Math.max(globalMax[0], prefixSum + root.key);
            return;
        }

        // recursive rule
        getMaxPathCostWithPrefix2(root.left, prefixSum + root.key, globalMax); // go left
        getMaxPathCostWithPrefix2(root.right, prefixSum + root.key, globalMax); // go right
    }

    /**
     * Given a binary tree, flatten it to a linked list in place
     */
    public void flattenBinaryTree(TreeNode root) {
        if (root == null) {
            return;
        }
        rightToLeftPostOrderTraversal(root, new TreeNode[1]);
    }

    private void rightToLeftPostOrderTraversal(TreeNode root, TreeNode[] previous) {
        if (root == null) {
            return;
        }
        rightToLeftPostOrderTraversal(root.right, previous);
        rightToLeftPostOrderTraversal(root.left, previous);
        // 注意方向, 是从下到上
        root.right = previous[0];
        root.left = null;
        previous[0] = root;
    }

    /***
     * Reconstruct a binary tree (with no duplicate values) with pre-order and in-order sequences
     *
     * Time = O(n)
     * Space = O(n)
     */
    public TreeNode constructTreeFromPreOrderAndInOrder(int[] inOrder, int[] preOrder) {
        if (inOrder == null || inOrder.length == 0
                || preOrder == null || preOrder.length == 0) {
            return null;
        }
        // indexMap maps each node in the in-order sequence to its index in the in-order sequences
        // e.g <k = 10, value = 3>
        Map<Integer, Integer> indexMap = new HashMap<>(inOrder.length);
        for (int i = 0; i < inOrder.length; i++) {
            indexMap.put(i, inOrder[i]);
        }
        // recursion function returns the sub-tree root
        return construct(inOrder, 0, inOrder.length - 1,
                         preOrder, 0, preOrder.length - 1,
                         indexMap);
    }

    private TreeNode construct(int[] in, int inLeft, int inRight,
                               int[] pre, int preLeft, int preRight,
                               Map<Integer, Integer> indexMap) {
        if (inLeft > inRight) {
            return null;
        }
        TreeNode root = new TreeNode(preLeft);
        int leftSize = indexMap.get(root.key) - inLeft; // calculate left-subtree size
        root.left = construct(in, inLeft, inLeft + leftSize - 1,
                              pre, preLeft + 1, preLeft + leftSize,
                              indexMap);
        root.right = construct(in, inLeft + leftSize + 1, inRight,
                               pre, preLeft + leftSize + 1, preRight,
                               indexMap);
        return root;
    }

    /**
     * Construct a binary tree from in-order and level-order sequences
     * Given the in-order and level-order sequences of a binary tree
     * (you can assume all unique numbers in the tree), reconstruct the binary tree
     *
     * Time = O(n^2)
     * Space = O(n^2)
     */
    public TreeNode constructTreeFromInOrderAndLevelOrder(List<Integer> levelOrder, List<Integer> inOrder) {
        if (levelOrder == null || levelOrder.isEmpty()
                || inOrder == null || inOrder.isEmpty()) {
            return null;
        }

        Map<Integer, Integer> indexMap = new HashMap<>();
        int index = 0;
        for (int e : inOrder) {
            indexMap.put(index++, e);
        }

        return construct(levelOrder, indexMap);
    }

    private TreeNode construct(List<Integer> level, Map<Integer, Integer> indexMap) {
        if (level.isEmpty()) {
            return null;
        }

        TreeNode root = new TreeNode(level.remove(0));
        List<Integer> left = new LinkedList<>();
        List<Integer> right = new LinkedList<>();
        for (int num : level) {
            if (indexMap.get(num) < indexMap.get(root.key)) {
                left.add(num);
            } else {
                right.add(num);
            }
        }
        root.left = construct(left, indexMap);
        root.right = construct(right, indexMap);

        return root;
    }

    /**
     * Construct a Binary Search Tree from Preorder Traversal
     * Given an array of integers pre-order, which represents the preorder traversal of
     * a BST (i.e., binary search tree), construct the tree and return its root.
     *
     * It is guaranteed that there is always possible to find a binary search tree with
     * the given requirements for the given test cases.
     *
     * Time = O(n)
     * Space = O(n)
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        return bstFromPreorder(preorder, 0, preorder.length - 1);
    }

    private TreeNode bstFromPreorder(int[] preorder, int left, int right) {
        if (left > right) {
            return null;
        }

        TreeNode node = new TreeNode(preorder[left]);
        int index = left;
        while (index <= right && preorder[index] <= node.key) {
            index++;
        }

        node.left = bstFromPreorder(preorder, left + 1, index - 1);
        node.right = bstFromPreorder(preorder, index, right);

        return node;
    }

    public static void main(String[] args) {
        RecursionBasics solution = new RecursionBasics();
        System.out.println(solution.fibonacci(5));
        int[][] array = solution.spiralOrderGenerate(5,5);
        for (int i = 0; i < array.length; i++) {
            System.out.println(Arrays.toString(array[i]));
        }
        System.out.println(solution.abbreviationMatching("apple","a2l1"));
    }
}
