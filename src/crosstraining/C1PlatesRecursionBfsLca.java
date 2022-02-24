package crosstraining;

import java.util.*;

class TreeNode {
    public int key;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int key) {
        this.key = key;
    }
}

class TreeNodeP {
    public int key;
    public TreeNodeP left;
    public TreeNodeP right;
    public TreeNodeP parent;

    public TreeNodeP(int key, TreeNodeP parent) {
        this.key = key;
        this.parent = parent;
    }
}

public class C1PlatesRecursionBfsLca {

    /**
     * Given a sorted integer array, remove duplicate elements.
     * For each group of elements with the same value KEEP AT MOST 1 of them
     * Do it in-place
     * Time = O(n)
     * Space = O(n)  // O(slow + 1)
     */
    public int[] arrayDedup1(int[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }
        /* Slow: all elements to the left of the slow (excluding slow) pointer
         * are the results for the elements that have been processed.
         * Fast: The current index that's being processed.
         * (all elements to the right side of the fast pointer have not been processed) */
        int slow = 1;
        for (int fast = 1; fast < array.length; fast++) {
            if (array[fast] != array[slow - 1]) {
                array[slow++] = array[fast];
            }
        }
        return Arrays.copyOfRange(array, 0, slow);
    }

    /**
     * Given a sorted integer array, remove duplicate elements.
     * For each group of elements with the same value KEEP AT MOST 2 of them
     * Do it in-place
     * Time = O(n)
     * Space = O(n)  // O(slow)
     */
    public int[] arrayDedup2(int[] array) {
        if (array == null || array.length <= 2) {
            return array;
        }
        /* Slow: all elements to the left of the slow (excluding slow) pointer
         * are the results for the elements that have been processed.
         * Fast: The current index that's being processed.
         * (all elements to the right side of the fast pointer have not been processed).
         * Initialize: slow = 2, fast = 2 */
        int slow = 2;
        for (int fast = 2; fast < array.length; fast++) {
            /* Case 1: a[f] == a[s - 2], we are sure a[f] == a[s - 1], not copy
             * Case 2: a[f] != a[s - 2], a[s] = a[f]; s++;*/
            if (array[slow - 2] != array[fast]) {
                array[slow++] = array[fast];
            }
        }
        return Arrays.copyOfRange(array, 0, slow);
    }

    /**
     * Given a sorted integer array, remove duplicate elements.
     * For each group of elements with the same value DO NOT KEEP ANY of them
     * Do it in-place
     * Time = O(n)
     * Space = O(n)  // O(slow)
     */
    public int[] arrayDedup3(int[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }
        /* Slow: all elements to the left of the slow (excluding slow) pointer
         * are the results for the elements that have been processed.
         * Fast: The current index that's being processed.
         * (all elements to the right side of the fast pointer have not been processed). */
        int slow = 0;
        int fast = 0; // fast start from 0 since @param begin follows it
        while (fast < array.length) {
            int begin = fast;
            while (fast < array.length && array[fast] == array[begin]) {
                // System.out.println(fast);
                fast++;
            }
            /* after the inner while loop, "fast" points to the first character after input[begin]
             * that doesn't equal input[begin]
             * case 1: fast - begin > 1, which means there are duplicates input[begin], continue;
             * case 2: fast - begin == 1, which means there is no duplicate for input[begin], copy;
             * So we are actually checking for input[begin] */
            if (fast - begin == 1) {
                array[slow++] = array[begin];
            }
        }
        return Arrays.copyOfRange(array, 0, slow);
    }

    /**
     * Given a sorted integer array, remove adjacent duplicate elements repeatedly, from left to right
     * For each group of elements with the same value DO NOT KEEP ANY of them
     * Do it In place
     *
     * 1 2 3 3 3 2 2 -> 1
     * 1 1 2 1 -> 1, since left to right, no 1 left when process the last 1
     *
     * Time = O(n)
     * Space = O(n)  // result array
     */
    public int[] arrayDedup4(int[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }
        /* Slow: all elements to the left of the slow (including slow) pointer
         * are the results for the elements that have been processed.
         * Fast: The current index that's being processed.
         * (all elements to the right side of the fast pointer have not been processed). */
        int slow = -1;
        Integer lastPoll = null; // record the last element been polled
        for (int fast = 0; fast < array.length; fast++) {
            // case 1: no element being added yet or new element is not same as the last been added
            if (slow == -1 || array[fast] != array[slow]) {
                if (lastPoll == null || array[fast] != lastPoll) {
                    array[++slow] = array[fast];
                    lastPoll = null; // since new element is added, think of 1,1,2,1
                }
            } else { // case 2: has duplicate
                lastPoll = array[slow--];
            }
        }
        return Arrays.copyOfRange(array, 0, slow + 1);
    }

    /**
     * Given an array of random numbers, push all the "0s" to the right end of the array.
     * The order of all design patterns elements should be the same
     * 1 9 8 4 0 0 2 7 0 6 0 -> 1 9 8 4 2 7 6 0 0 0 0
     * Time = O(n)
     * Space = O(1)
     */
    public int[] moveZerosToTheEnd(int[] array) {
        if (array == null || array.length <= 1) return array;
        /* Slow: all elements to the left side of slow (excluding slow) are non-zeros.
         * Fast: the current element being processed */
        int slow = 0;
        for (int fast = 0; fast < array.length; fast++) {
            if (array[fast] != 0) {
                array[slow++] = array[fast];
            }
        }
        // replace all elements to the right side of slow (including slow) with 0
        while (slow < array.length) {
            array[slow++] = 0;
        }
        return array;
    }

    /**
     * Use the least number of comparisons to get the largest and smallest number
     * in the given integer array. Return the largest number and the smallest number.
     *
     * Total number of comparisons = O(1.5n) < O(2n), which compare each element with min and max
     */
    public int[] largestAndSmallest(int[] array) {
        if (array == null || array.length == 0) {
            return new int[2];
        }
        List<Integer> large = new ArrayList<>(); // store the larger elements in the divide process
        List<Integer> small = new ArrayList<>();

        /* pre processing, divide all elements into two groups
         * this step takes O(n/2) comparisons */
        divide(array, large, small);
        int max = array[0];
        int min = array[0];
        /* find the largest in the large group and smallest in the small group
         * this step takes O(n/2) + O(n/2) comparisons
         * 这步不需要binary reduction, 次数是一样的 */
        for (int i : large) {
            max = Math.max(i, max);
        }
        for (int j : small) {
            min = Math.min(j, min);
        }
        return new int[]{max, min};
    }

    private void divide(int[] array, List<Integer> large, List<Integer> small) {
        int i = 0;
        while (i < array.length) {
            if (i + 1 == array.length) {
                if (array[i] > array[0]) {
                    large.add(array[i]);
                } else {
                    small.add(array[i]);
                }
                break;
            }
            if (array[i] < array[i + 1]) {
                large.add(array[i + 1]);
                small.add(array[i]);
            } else {
                small.add(array[i + 1]);
                large.add(array[i]);
            }
            i = i + 2;
        }
    }

    /**
     * Use the least number of comparisons to get the largest and 2nd largest number
     * in the given integer array. Return the largest number and 2nd largest number.
     *
     * Total number of comparisons = O(n + logn) < O(2n) which compare each element with max and 2nd max
     */
    public int[] largestAndSecondLargest(int[] array) {
        if (array == null || array.length < 2) {
            return new int[2];
        }
        List<Integer> elements = new ArrayList<>();
        for (int i : array) {
            elements.add(i);
        }
        Map<Integer, List<Integer>> lookup = new HashMap<>();
        /* Binary reduction
         * n/2 + n/4 + n/8 + … = n comparisons find the max */
        while (elements.size() > 1) {
            int a = elements.remove(0);
            int b = elements.remove(0);
            if (a > b) {
                process(a, b, lookup);
                elements.add(a);
            } else {
                process(b, a, lookup);
                elements.add(b);
            }
        }
        int max = elements.get(0);
        int secMax = Integer.MIN_VALUE;
        /* O(log n) find the second max */
        for (int i : lookup.get(max)) {
            secMax = Math.max(i, secMax);
        }
        return new int[]{max, secMax};
    }

    private void process(int large, int small, Map<Integer, List<Integer>> lookup) {
        List<Integer> temp = lookup.getOrDefault(large, new ArrayList<>()); // store the smaller elements
        temp.add(small);
        lookup.put(large, temp);
        if(large != small) { // int[]{1,1}
            lookup.remove(small);
        }
    }

    /**
     * Rotate an N * N matrix clockwise 90 degrees.
     * Time = O(n^2)
     * Space = O(n/2)
     */
    public void rotateMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0
                ||matrix[0] == null || matrix[0].length == 0) {
            return;
        }
        rotateMatrix(matrix, 0, matrix.length);
    }

    private void rotateMatrix(int[][] matrix, int offset, int size) {
        // base case
        if (size <= 1) {
            return;
        }
        for (int i = 0; i < size - 1; i++) {
            int temp = matrix[offset][offset + i];
            matrix[offset][offset + i] = matrix[offset + size - 1 - i][offset];
            matrix[offset + size - 1 - i][offset] = matrix[offset + size - 1][offset + size - 1 - i];
            matrix[offset + size - 1][offset + size - 1 - i] = matrix[offset + i][offset + size - 1];
            matrix[offset + i][offset + size - 1] = temp;
        }
        rotateMatrix(matrix, offset + 1, size - 2);
    }

    /**
     * Get the list of keys in a given binary tree layer by layer in zig-zag order.
     *    1
     *  2   3
     * 4 5 6 7
     * Output = 1 2 3 7 6 5 4
     * Time = O(n)
     * Space = O(n)
     */
    public List<Integer> zigZagPrintBinaryTree(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Deque<TreeNode> dq = new ArrayDeque<>(); // nodes always from left to right
        dq.offerFirst(root);
        /* Odd level
         * - expand left to right
         * - generate left child then right child and offerLast to dq
         * Even level
         * - expand right to left
         * - generate right child then left child and offerFirst to dq */
        boolean isOddLevel = true;
        while (!dq.isEmpty()) {
            int size = dq.size();
            for (int i = 0; i < size; i++) {
                if (isOddLevel) {
                    TreeNode cur = dq.pollFirst();
                    result.add(cur.key);
                    if (cur.left != null) {
                        dq.offerLast(cur.left);
                    }
                    if (cur.right != null) {
                        dq.offerLast(cur.right);
                    }
                } else {
                    TreeNode cur = dq.pollLast();
                    result.add(cur.key);
                    if (cur.right != null) {
                        dq.offerFirst(cur.right);
                    }
                    if (cur.left != null) {
                        dq.offerFirst(cur.left);
                    }
                }
            }
            isOddLevel = !isOddLevel;
        }
        return result;
    }

    /**
     * Classic LCA
     */
    public TreeNode lca(TreeNode root, TreeNode a, TreeNode b) {
        if (root == null || root == a || root == b) {
            return root;
        }

        TreeNode left = lca(root.left, a, b);
        TreeNode right = lca(root.right, a, b);

        if (left != null && right != null) {
            return root;
        }
        return left == null ? right : left;
    }

    /**
     * LCA with parent node
     */
    public TreeNodeP lcaWithParent(TreeNodeP one, TreeNodeP two) {
        // one == null return two is WRONG!
        // since two could be not in the tree

        int oneHeight = getHeightOfNode(one);
        int twoHeight = getHeightOfNode(two);

        if (oneHeight > twoHeight) {
            for (int i = 0; i < oneHeight - twoHeight; i++) {
                one = one.parent;
            }
        } else {
            for (int i = 0; i < twoHeight - oneHeight; i++) {
                two = two.parent;
            }
        }

        while (one != two && one != null) {
            one = one.parent;
            two = two.parent;
        }
        return one;
    }

    private int getHeightOfNode(TreeNodeP node) {
        int height = 0;
        while (node != null) {
            node = node.parent;
            height++;
        }
        return height;
    }

    /**
     * Given K nodes in a binary tree, find their lowest common ancestor.
     *
     * Time = O(n)
     * Space = O(height)
     */
    public TreeNode lcaOfKNodes(TreeNode root, List<TreeNode> nodes) {
        if (nodes == null || nodes.size() == 0) {
            return null;
        }
        Set<TreeNode> set = new HashSet<>(nodes); // use a set to see if find a node
        return getLca(root, set);
    }

    private TreeNode getLca(TreeNode root, Set<TreeNode> set) {
        // base case
        if (root == null || set.contains(root)) {
            return root;
        }
        /* Case 1: both return not null, return root
         * Case 2: one of the child returns not null, return the result of that child
		 * Case 3: both return null, return null */
        TreeNode leftChild = getLca(root.left, set);
        TreeNode rightChild = getLca(root.right, set);

        if (leftChild != null && rightChild != null) {
            return root;
        }

        return leftChild == null? rightChild : leftChild;
    }


    /**
     * Given two nodes in a binary tree, find their lowest common ancestor
     * (the given two nodes are not guaranteed to be in the binary tree).
     * Return null If any of the nodes is not in the tree.
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode one, TreeNode two) {
        if (root == null) {
            return null;
        }

        TreeNode result = getLca(root, one, two);

        if (result != one && result != two && result != null) {
            return result;
        }

        if (result == one) {
            if (two == null || getLca(one, two, two) != null) {
                return result;
            }
        } else if (result == two) {
            if (one == null || getLca(two, one, one) != null) {
                return result;
            }
        }
        return null;
    }

    private TreeNode getLca(TreeNode root, TreeNode one, TreeNode two) {
        if (root == null || root == one || root == two) {
            return root;
        }

        TreeNode leftChild = getLca(root.left, one, two);
        TreeNode rightChild = getLca(root.right, one, two);

        if (leftChild != null && rightChild != null) {
            return root;
        }

        return leftChild == null? rightChild : leftChild;
    }

    /**
     * LCA in Binary Search Tree
     *
     * Time = O(n) // O(height) 千万记住bst不一定balanced所以不是O(logn)
     */
    public TreeNode lcaInBinarySearchTree(TreeNode root, TreeNode a, TreeNode b) {
        if (root == null) {
            return null;
        }
        if (root.key < a.key && root.key < b.key) {
            return lcaInBinarySearchTree(root.right, a, b);
        } else if (root.key > a.key && root.key > b.key) {
            return lcaInBinarySearchTree(root.left, a, b);
        } else {
            return root;
        }
    }

    /**
     * Sort in specified order
     * Given two integer arrays A1 and A2, sort A1 in such a way that the relative order among the elements
     * will be same as those are in A2. For the elements that are not in A2, append them in the right end
     * of the A1 in an ascending order.
     *
     * eg. A1 = {2, 1, 2, 5, 7, 1, 9, 3}, A2 = {2, 1, 3}, A1 is sorted to {2, 2, 1, 1, 3, 5, 7, 9}
     *
     * Time = O(n)
     * Space = O(n)
     */
    public int[] sortInSpecialOrder(int[] A1, int[] A2) {
        // assumptions: A1 != null && A2 != null, not duplicate elements in A2
        // pre-processing - for comparator
        Integer[] A1I = new Integer[A1.length];
        for (int i = 0; i < A1.length; i++) {
            A1I[i] = A1[i];
        }
        Arrays.sort(A1I, new MyComparator(A2)); // Arrays.sort(T[], new MyComparator(..)) so need a transfer
        for (int i = 0; i < A1.length; i++) {
            A1[i] = A1I[i];
        }
        return A1;
    }

    private static class MyComparator implements Comparator<Integer> {
        private Map<Integer, Integer> map;
        public MyComparator(int[] order) {
            map = new HashMap<>();
            for (int i = 0; i < order.length; i++) {
                map.put(order[i], i);
            }
        }

        @Override
        public int compare(Integer a, Integer b) {
            if (map.containsKey(a) && map.containsKey(b)) {
                return map.get(a) < map.get(b) ? -1 : 1;
            } else if (!map.containsKey(a) && !map.containsKey(b)) {
                return a < b ? -1 : 1;
            }
            return map.containsKey(a) ? -1 : 1;
        }
    }

    /**
     * using statement lambda */
    public int[] sortInSpecialOrder2(int[] A1, int[] A2) {
        // assumptions: A1 != null && A2 != null, not duplicate elements in A2
        // preprocessing - for comparator
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < A2.length; i++) {
            map.put(A2[i], i);
        }
        Integer[] A1I = new Integer[A1.length];
        for (int i = 0; i < A1.length; i++) {
            A1I[i] = A1[i];
        }
        Arrays.sort(A1I, (a, b) -> {
            if (map.containsKey(a) && map.containsKey(b)) {
                return map.get(a) < map.get(b) ? -1 : 1;
            } else if (!map.containsKey(a) && !map.containsKey(b)) {
                return a < b ? -1 : 1;
            }
            return map.containsKey(a) ? -1 : 1;
        });
        for (int i = 0; i < A1.length; i++) {
            A1[i] = A1I[i];
        }
        return A1;
    }

    public static void main(String[] args) {
        C1PlatesRecursionBfsLca ins = new C1PlatesRecursionBfsLca();
        int[] array = {1,1,2,2,3,3,4};
        System.out.println(Arrays.toString(ins.arrayDedup3(array)));

        //int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        ins.rotateMatrix(matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }

        TreeNode n1 = new TreeNode(5);
        TreeNode n2 = new TreeNode(3);
        TreeNode n3 = new TreeNode(8);
        TreeNode n4 = new TreeNode(1);
        TreeNode n5 = new TreeNode(4);
        TreeNode n6 = new TreeNode(11);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n3.right = n6;
        ins.zigZagPrintBinaryTree(n1);

        TreeNodeP k1 = new TreeNodeP(5,null);
        TreeNodeP k2 = new TreeNodeP(9, k1);
        TreeNodeP k3 = new TreeNodeP(12, k1);
        TreeNodeP k4 = new TreeNodeP(2,k2);
        TreeNodeP k5 = new TreeNodeP(3,k2);
        TreeNodeP k6 = new TreeNodeP(14,k3);
        k1.left = k2;
        k1.right = k3;
        k2.left = k4;
        k2.right = k5;
        k3.right = k6;
        TreeNodeP result = ins.lcaWithParent(null, k5);

        System.out.println(ins.lowestCommonAncestor(n1,n5,n5).key);

        ins.sortInSpecialOrder(new int[]{4,4,3,3,3}, new int[]{4});
    }
}
