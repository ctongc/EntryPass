package crosstraining;

import java.util.*;

class ListNode {
    public final int value;
    public ListNode next;
    public ListNode(int value) {
        this.value = value;
        this.next = null;
    }
}

public class Leftover {

    // below are not in Cross Training I

    /**
     * Sort in specified order
     * Given two integer arrays A1 and A2, sort A1 in such a way that the relative order
     * among the elements will be same as those are in A2. For the elements that are not in A2
     * append them in the right end of the A1 in ascending order.
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
     * using statement lambda
     */
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


    // DPs

    /**
     * There is a wooden stick with length >= 1, we need to cut it into pieces,
     * where the cutting positions are defined in an int array A. The positions
     * are guaranteed to be in ascending order in the range of [1, length - 1].
     * The cost of each cut is the length of the stick segment being cut.
     * Determine the minimum total cost to cut the stick into the defined pieces.
     * Time = O(n^3) // O(n^2)填表，填每个格子的时候都要扫一遍(i+j)个元素*O(n)
     * Space = O(n^2)
     */
    public int minCostCuttingWood(int[] cuts, int length) {
        // assumption: length >= 1
        // sanity checks

        int[] mycuts = new int[cuts.length + 2]; // pad with leftmost and rightmost pos.
        // mycuts[0] = 0;
        for (int i = 0; i < cuts.length; i++) {
            mycuts[i + 1] = cuts[i];
        }
        mycuts[mycuts.length - 1] = length;

        // M[i][j] represents the minimum cost of cutting the wood
        // between index i and index j in the input array cuts.
        // The final solution to return is the value of M[0][length - 1].
        int[][] M = new int[mycuts.length][mycuts.length];

        // induction rule
        // for each M[i][j], try out all possible k that (i < k < j)
        // M[i][j] = MIN(A[j] - A[i] + M[i][k] + M[k][j]) for all possible k
        for (int i = mycuts.length - 1; i >= 0; i--) { // bottom up
            for (int j = 0; j < mycuts.length; j++) { // left to right
                if (i + 1 == j) { // Base case, size = 1
                    M[i][j] = 0;
                } else {
                    M[i][j] = Integer.MAX_VALUE; // since it was initialized with 0
                    for (int k = i + 1; k < j; k++) {
                        M[i][j] = Math.min(M[i][j], mycuts[j] - mycuts[i] + M[i][k] + M[k][j]);
                    }
                }
            }
        }
        return M[0][mycuts.length - 1];
    }

    // Old C3 recursion and construct binary trees

    /**
     * Check if a given binary tree is balanced.
     * A balanced binary tree is one in which the depths of every node’s
     * left and right subtree differ by at most 1.
     *
     * Time = O(n)
     * Space = O(height)
     */
    public boolean isBalancedBst(TreeNode root) {
        if (root == null) return true;
        return getHeight(root) > 0;
    }

    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        if (leftHeight == -1 || rightHeight == -1 ||
                Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Maximum Path Sum Binary Tree I
     * Given a binary tree in which each node contains an integer number.
     * Find the maximum possible sum from one leaf node to another leaf node.
     * If there is no such path available, return Integer.MIN_VALUE
     * Time = O(n)
     * Space = O(height)
     */
    public int maxPathSumLeafToLeaf(TreeNode root) {
        if (root == null) {
            return Integer.MIN_VALUE;
        }
        int[] maxSum = new int[]{Integer.MIN_VALUE}; // remember it's 0 for new int[0]
        getMaxPathSum1(root, maxSum);
        return maxSum[0];
    }

    private int getMaxPathSum1(TreeNode root, int[] maxSum) {
        if (root == null) { // base case
            return 0;
        }
        int leftPathSum = getMaxPathSum1(root.left, maxSum);
        int rightPathSum = getMaxPathSum1(root.right, maxSum);

        // 因为是leaf to leaf 所以必须左右都不为空才更新maxSum
        int cur = leftPathSum + rightPathSum + root.key;
        if (maxSum[0] < cur &&
                root.left != null && root.right != null) {
            maxSum[0] = cur;
        }
        if (root.left == null) {
            return rightPathSum + root.key;
        } else if (root.right == null) {
            return leftPathSum + root.key;
        }
        return Math.max(leftPathSum, rightPathSum) + root.key;
    }

    /**
     * Maximum Path Sum Binary Tree II
     * Given a binary tree in which each node contains an integer number.
     * Find the maximum possible sum from any node to any node (the start node and the end node can be the same).
     * Time = O(n)
     * Space = O(height)
     */
    public int maxPathSumNodeToNode(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int[] maxSum = new int[]{Integer.MIN_VALUE}; // carry the maxSum
        getMaxPathSum2(root, maxSum);
        return maxSum[0];
    }

    private int getMaxPathSum2(TreeNode root, int[] maxSum) {
        if (root == null) {            return 0;
        }
        int leftPathSum = getMaxPathSum2(root.left, maxSum);
        int rightPathSum = getMaxPathSum2(root.right, maxSum);
        // 左右path小于0就discard
        if (leftPathSum < 0) {
            leftPathSum = 0;
        }
        if (rightPathSum < 0) {
            rightPathSum = 0;
        }
        // update max sum
        maxSum[0] = Math.max(leftPathSum + rightPathSum + root.key, maxSum[0]);
        return Math.max(leftPathSum, rightPathSum) + root.key;
    }

    /**
     * Given a binary tree in which each node contains an integer number.
     * Find the maximum possible sum from leaf to the root.
     * Time = O(n)
     * Space = O(height)
     */
    public int maxPathSumLeafToRoot(TreeNode root) {
        // assumption: root is not null
        int[] maxSum = new int[]{Integer.MIN_VALUE};
        getMaxPathSum4(root, 0, maxSum);
        return maxSum[0];
    }

    private void getMaxPathSum4(TreeNode root, int prefixSum, int[] maxSum) {
        if (root == null) { // base case
            return;
        }

        prefixSum += root.key; // update new prefix

        // root/one child == null doesn't mean it on the leaf!!
        if (root.left == null && root.right == null) {
            // update max path sum
            maxSum[0] = Math.max(prefixSum, maxSum[0]);
            return;
        }
        // prefixSum += root.key;
        getMaxPathSum4(root.left, prefixSum, maxSum);
        getMaxPathSum4(root.right, prefixSum, maxSum);
    }

    /**
     * Binary Tree Path Sum To Target III
     * Given a binary tree in which each node contains an integer number. Determine if there exists a path
     * (the path can only be from one node to itself or to any of its descendants), the sum of the numbers
     * on the path is the given target number.
     * Time = O(n)
     * Space = O(n) // O(n) for call stack + O(n) for the set
     */
    public boolean existPathSumToTarget(TreeNode root, int target) {
        if (root == null) {
            return false;
        }
        // contains all path prefix sum from root node to the current node
        HashSet<Integer> prefixSumSet = new HashSet<>(); // use a HashMap with count to deal with duplicate sums
        prefixSumSet.add(0);
        return findTargetSum(root, target, 0, prefixSumSet);
    }

    private boolean findTargetSum(TreeNode root, int target, int prefixSum, HashSet<Integer> prefixSumSet) {
        if (root == null) {
            return false;
        }
        prefixSum += root.key;
        if (prefixSumSet.contains(prefixSum - target)) {
            return true;
        }
        boolean needRemove = prefixSumSet.add(prefixSum);
        if (findTargetSum(root.left, target, prefixSum, prefixSumSet)) {
            return true;
        }
        if (findTargetSum(root.right, target, prefixSum, prefixSumSet)) {
            return true;
        }

        // don't forget to recovery!
        if (needRemove) {
            prefixSumSet.remove(prefixSum);
        }
        return false;
    }

    /**
     * Maximum Path Sum Binary Tree III
     * Given a binary tree in which each node contains an integer number. Find the maximum possible subpath sum
     * (both the starting and ending node of the subpath should be on the same path from root to one of the leaf nodes,
     * and the subpath is allowed to contain only one node).
     * Time = O(n)
     * Space = O(height)
     */
    public int maxPathSumSamePath(TreeNode root) {
        // assumption: root is not null
        int[] maxSum = new int[]{Integer.MIN_VALUE};
        getMaxPathSum3(root, Integer.MIN_VALUE, maxSum);
        return maxSum[0];
    }

    private void getMaxPathSum3(TreeNode root, int largestPrefix, int[] maxSum) {
        if (root == null) { // base case
            return;
        }
        if(largestPrefix < 0) {
            // discard prefix sum
            largestPrefix = root.key;
        } else {
            largestPrefix += root.key;
        }
        // int largestPrefix = root.key + (largestPrefix < 0 ? 0 : largest)
        // update max path sum every time going to next node, actually a pre-order traversal
        maxSum[0] = Math.max(largestPrefix, maxSum[0]);
        getMaxPathSum3(root.left, largestPrefix, maxSum);
        getMaxPathSum3(root.right, largestPrefix, maxSum);
    }

    /**
     * Reconstruct Binary Tree With Preorder And Inorder
     * Given the preorder and inorder traversal sequence of a binary tree, reconstruct the original tree.
     * Time = O(n)
     * Space = O(n)
     */
    public TreeNode reconstructPreIn(int[] inOrder, int[] preOrder) {
        // Assumptions:
        // 1 the two given sequences are not null and have the same length
        // 2 there is NO duplicate keys in the binary tree

        // index of a Node in inorder sequence
        HashMap<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < inOrder.length; i++) {
            indexMap.put(inOrder[i], i);
        }
        TreeNode root = construct(inOrder, 0, inOrder.length - 1,
                preOrder, 0, preOrder.length - 1,
                indexMap);
        return root;
    }

    private TreeNode construct(int[] inOrder, int inLeft, int inRight,
                               int[] preOrder, int preLeft, int preRight,
                               HashMap<Integer, Integer> indexMap) {
        if (inLeft > inRight) {
            return null; // base case
        }
        TreeNode root = new TreeNode(preOrder[preLeft]); // the first element is always the root
        int rootIndex = indexMap.get(root.key) - inLeft; // calculate size of left-subTree
        root.left = construct(inOrder, inLeft, inLeft + rootIndex - 1,
                preOrder, preLeft + 1, preLeft + rootIndex,
                indexMap);
        root.right = construct(inOrder, inLeft + rootIndex + 1, inRight,
                preOrder, preLeft + rootIndex + 1, preRight,
                indexMap);
        return root;
    }

    /**
     * Reconstruct Binary Tree With Postorder And Inorder
     * Given the postorder and inorder traversal sequence of a binary tree, reconstruct the original tree.
     * Time = O(n)
     * Space = O(n)
     */
    public TreeNode reconstructPostIn(int[] inOrder, int[] postOrder) {
        // Assumptions: 1 The given sequences are not null and they have the same length
        // 2 There are no duplicate keys in the binary tree
        HashMap<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < inOrder.length; i++) {
            indexMap.put(inOrder[i], i);
        }
        return construct(postOrder, 0, postOrder.length - 1,
                indexMap, 0, inOrder.length - 1);
    }

    private TreeNode construct(int[] postOrder, int postLeft, int postRight,
                               HashMap<Integer, Integer> indexMap, int inLeft, int inRight) {
        if (postLeft > postRight) {
            return null;
        }
        TreeNode root = new TreeNode(postOrder[postRight]);
        int rootIndex = indexMap.get(root.key) - inLeft; // calculate the left subtree.
        root.left = construct(postOrder, postLeft, postLeft + rootIndex - 1,
                indexMap, inLeft, inLeft + rootIndex - 1);
        root.right = construct(postOrder, postLeft + rootIndex, postRight - 1,
                indexMap, inLeft + rootIndex + 1, inRight);
        return root;
    }
    /**
     * Reconstruct Binary Tree With Levelorder And Inorder
     * Given the levelorder and inorder traversal sequence of a binary tree, reconstruct the original tree.
     * Time = O(n^2) // worst case n levels, and for each level, time = n + (n-1) + (n-2) + … = O(n^2)
     * Space = O(n^2) // for each node, we stored the left and right tree
     */
    public TreeNode reconstructLevelIn(int[] inOrder, int[] levelOrder) {
        // assumption: 1 inOrder and levelOrder have the same length and neither is null
        // 2 there is no duplicate keys in the binary tree
        // indexMap is used as lookup for the index of a node in in-order sequence
        HashMap<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < inOrder.length; i++) {
            indexMap.put(inOrder[i], i);
        }
        List<Integer> level = new ArrayList<>(); // record the current level
        for (int node : levelOrder) {
            level.add(node);
        }
        TreeNode root = construct(level, indexMap);
        return root;
    }

    private TreeNode construct(List<Integer> level, HashMap<Integer, Integer> indexMap) {
        // base case
        if (level.isEmpty()) {
            return null;
        }
        TreeNode root = new TreeNode(level.remove(0));
        // store the node in the corresponding list
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        // Case 1: if indexMap[x] < indexMap[20]: x goes to the left sub tree.
        // Case 2: if indexMap[x] > indexMap[20]: x goes to the right sub tree.
        for (int node : level) {
            if (indexMap.get(node) < indexMap.get(root.key)) {
                left.add(node);
            } else {
                right.add(node);
            }
        }
        root.left = construct(left, indexMap);
        root.right = construct(right, indexMap);
        return root;
    }

    /**
     * Reconstruct Binary Search Tree With Postorder Traversal
     * Given the postorder traversal sequence of a binary search tree, reconstruct the original tree.
     * Time = O(n)
     * Space = O(n)
     */
    public TreeNode reconstructBstPost(int[] post) {
        // Assumptions: 1 the given sequence is not null
        // 2 there are no duplicate keys in the binary search tree
        // we traverse the post order sequence from right to left
        int[] index = new int[]{post.length - 1}; // use to iterate the the postorder sequence from last to first
        return construct(post, index, Integer.MIN_VALUE);
    }

    private TreeNode construct(int[] post, int[] index, int min) {
        // reverse后 走到没法再往右，跳到上一层往左走一个再往右
        if (index[0] < 0 || post[index[0]] < min) {
            return null;
        }
        // like a reversed preorder from right to left
        TreeNode root = new TreeNode(post[index[0]--]);
        root.right = construct(post, index, root.key);
        root.left = construct(post, index, min);
        return root;
    }

    /**
     * Reconstruct Binary Search Tree With Preorder Traversal
     * Given the preorder traversal sequence of a binary search tree, reconstruct the original tree.
     * Time = O(n)
     * Space = O(n)
     */
    public TreeNode reconstructBstPre(int[] pre) {
        // Assumptions: 1 The given sequence is not null
        // 2 There are no duplicate keys in the binary search tree
        int[] index = new int[1]; // iterate the pre sequence from the top
        return construct2(pre, index, Integer.MAX_VALUE);
    }

    private TreeNode construct2(int[] pre, int[] index, int max) {

        if (index[0] > pre.length - 1 || pre[index[0]] > max) {
            return null;
        }
        TreeNode root = new TreeNode(pre[index[0]++]);

        root.left = construct(pre, index, root.key);
        root.right = construct(pre, index, max);
        return root;
    }

    /**
     * Reconstruct Binary Search Tree With Levelorder Traversal
     * Given the levelorder traversal sequence of a binary search tree, reconstruct the original tree.
     * Time = O(n^2)
     * Space = O(n^2)
     */
    public TreeNode reconstructBstLevel(int[] level) {
        // Assumptions: 1 the given sequence is not null
        // 2 there are no duplicate keys in the binary search tree
        // we traverse the post order sequence from right to left
        if (level == null || level.length == 0) {
            return null;
        }
        List<Integer> curLevel = new ArrayList<>();
        for (int i : level) {
            curLevel.add(i);
        }
        return construct(curLevel);
    }

    private TreeNode construct(List<Integer> level) {
        if (level.isEmpty()) {
            return null;
        }
        int rootKey = level.remove(0);
        TreeNode root = new TreeNode(rootKey);
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (int i : level) {
            if (i < rootKey) {
                left.add(i);
            } else {
                right.add(i);
            }
        }
        root.left = construct(left);
        root.right = construct(right);
        return root;
    }
    // Old C4





        public ListNode reverseLinkedList(ListNode head) {
            if (head == null || head.next == null) {
                return head; // RETURN HEAD NOT NULL!!!
            }
            ListNode newHead = reverseLinkedList(head.next); // newHead is the last node not the next node
            head.next.next = head; // leave the newHead pointer but operate head.next.next
            head.next = null; // we have newHead points to head.nextNode so original head.next could point to null
            return newHead;
        }

        public ListNode reverseLinkedList2(ListNode head) {
            if (head == null || head.next == null) {
                return null;
            }
            ListNode pre = null;
            while(head != null) {
                ListNode nextNode = head.next;
                head.next = pre;
                pre = head;
                head = nextNode;
            }
            return pre;
        }

        public ListNode reverseInPairs(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }

            ListNode newHead = head.next;
            head.next = reverseInPairs(newHead.next);
            newHead.next = head;
            return newHead;
        }

        public ListNode reverseInPairs2(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }

            ListNode dummyHead = new ListNode(0);
            dummyHead.next = head;
            ListNode prev = dummyHead;
            while (head != null && head.next != null) {
                prev.next = head.next;
                head.next = head.next.next;
                prev.next.next = head;
                prev = head;
                head = head.next;
            }
            return dummyHead.next;
        }

        /**
         * Reverse Binary Tree Upside Down
         * Given a binary tree where all the right nodes are leaf nodes, flip it upside down
         * and turn it into a tree with left leaf nodes as the root.
         * Time = O(n)
         * Space = O(height)
         */
        public TreeNode reverseBtUpsideDown1(TreeNode root) {
            if (root == null || root.left == null) {
                return root;
            }
            TreeNode newRoot = reverseBtUpsideDown1(root.left);
            root.left.right = root.right;
            root.left.left = root;
            // don't forget to clean up
            root.left = null;
            root.right = null;
            return newRoot;
        }

        public TreeNode reverseBtUpsideDown2(TreeNode root) {
            if (root == null) {
                return root;
            }
            // root is root of input tree
            // newLeft is the root of new tree
            // preRoot is the oldRoot of last level's input tree
            // we deal with each level after store the information of the next level
            TreeNode prevRoot = null;
            TreeNode prevRight = null;
            while (root != null) {
                TreeNode newLeft = root.left; //will be the newRoot
                TreeNode newRight = root.right;
                root.right = prevRight;
                root.left = prevRoot;
                prevRoot = root;
                prevRight = newRight;
                root = newLeft;
            }
            return prevRoot;
        }

        public static ListNode createLinkedList(int n) {
            List<ListNode> nodeList = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                ListNode newNode = new ListNode(i);
                nodeList.add(newNode);
            }
            ListNode head = nodeList.remove(0);
            ListNode cur = head;
            for (ListNode node : nodeList) {
                cur.next = node;
                cur = node;
            }
            return head;
        }

        public static void printLinkedList(ListNode head) {
            while (head != null) {
                System.out.println(head.value);
                head = head.next;
            }
        }

    // Old C5

    /**
     * Largest Product Of Length
     * Given a dictionary containing many words, find the largest product of two words’ lengths,
     * such that the two words do not share any common characters.
     * e.g.
     * dictionary = [“abcde”, “abcd”, “ade”, “xy”], the largest product is 5 * 2 = 10 (by choosing “abcde” and “xy”)
     * Time = O(n * (n + m)) // m is the average length of string
     */
    public int largestLengthProduct(String[] dict) {
        // assumptions:
        // 1 The words only contains characters of 'a' to 'z'
        // 2 The dictionary is not null and does not contain null string, and has at least two strings
        // 3 If there is no such pair of words, just return 0

        // algorithm:
        // iterate from the largest product pairs, check each pair if valid using bit mask
        Arrays.sort(dict, Comparator.reverseOrder());
        int[] bitMasks = new int[dict.length];
        for (int i = 0; i < dict.length; i++) {
            int bitMask = 0;
            String s = dict[i];
            for (int j = 0; j < s.length(); j++) {
                // the 26 characters 'a' to 'z' are mapped to 0 - 25th bit
                // use (char - 'a') since their values are in a consecutive range
                // if char exists in the word, we set the bit at corresponding index to 1
                bitMask |= 1 << (s.charAt(j) - 'a');
            }
            bitMasks[i] = bitMask;
        }
        int result = 0;
        for (int i = 1; i < dict.length; i++) {
            for (int j = 0; j < i; j++) {
                // check if valid
                if((bitMasks[i] & bitMasks[j]) == 0) {
                    // if two words do not share any common characters
                    // the bit masks "and" result should be 0 since
                    // there is no position such that in the two bit masks are all 1
                    result = Math.max(dict[i].length() * dict[j].length(), result);
                }
            }
        }
        return result;
    }

    /**
     * if use this to validate, the Time = O(n^2 * m)
     */
    private boolean isValid2(int p1, int p2, String[] dict) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < dict[p1].length(); i++) { // NOTE dict[p1].toCharArray needs O(n) time
            set.add(dict[p1].charAt(i));
        }
        for (int i = 0; i < dict[p2].length(); i++) {
            if (set.contains(dict[p2].charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Kth Smallest With Only 3, 5, 7 As Factors
     * Find the Kth smallest number s such that s = 3 ^ x 5 ^ y 7 ^ z
     * x > 0 and y > 0 and z > 0, x, y, z are all integers.
     * Time = O(klogk)
     * Space = O(k)
     */
    public long kthSmallestWith357(int k) {
        // assumption: k >= 1
        // algorithm: best first searcher
        // maintain a minHeap then pop k times
        PriorityQueue<Long> minHeap = new PriorityQueue<>();
        Set<Long> visited = new HashSet<>(); // used to dedup
        minHeap.offer(3 * 5 * 7L);
        visited.add(3 * 5 * 7L);
        long result = 0;
        for (int i = 0; i < k; i++) {
            result = minHeap.poll();
            // for the state <x+1, y, z>, the actual value is *3
            if (visited.add(3 * result)) {
                minHeap.offer(3 * result);
            }
            if (visited.add(5 * result)) {
                minHeap.offer(5 * result);
            }
            if (visited.add(7 * result)) {
                minHeap.offer(7 * result);
            }
        }
        return result;
    }

    /**
     * Kth-closest Point To <0,0,0>
     * Given three arrays sorted in ascending order. Pull one number from each array to form a coordinate <x,y,z>
     * in a 3D space. Find the coordinates of the points that is k-th closest to <0,0,0>.
     */
    public class Coordinate implements Comparable<Coordinate> {
        int xi;
        int yi;
        int zi;
        double dist;
        public Coordinate(int xi, int yi, int zi, int[] a, int[] b, int[] c) {
            this.xi = xi;
            this.yi = yi;
            this.zi = zi;
            this.dist = getDist(a[xi], b[yi], c[zi]);
        }

        private double getDist(int x, int y, int z) {
            return Math.sqrt(x * x + y * y + z * z);
        }

        @Override
        public int compareTo(Coordinate c2) {
            if (Double.compare(dist, c2.dist) == 0) {
                return 0;
            }
            return Double.compare(dist, c2.dist) < 0 ? -1 : 1;
        }

        @Override
        public int hashCode() {
            return xi * 31 * 31 + yi * 31 + zi;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Coordinate)) {
                return false;
            }
            // o must be a coordinate, otherwise no o.x
            Coordinate c = (Coordinate) o;
            return xi == c.xi && yi == c.yi && zi == c.zi;
        }
    }

    public List<Integer> kthClosestToOrigin(int[] a, int[] b, int[] c, int k) {
        // Assumptions: 1 the three given arrays are not null or empty
        // 2 k >= 1 and k <= a.length * b.length * c.length
        PriorityQueue<Coordinate> minHeap = new PriorityQueue<>();
        Set<Coordinate> checked = new HashSet<>();
        Coordinate cur = new Coordinate(0, 0, 0, a, b, c);
        minHeap.offer(cur);
        checked.add(cur);
        while(k > 0) {
            cur = minHeap.poll();
            if (cur.xi + 1 < a.length) {
                Coordinate point1 = new Coordinate(cur.xi + 1, cur.yi, cur.zi, a, b, c);
                if (checked.add(point1)) {
                    minHeap.offer(point1);
                }
            }
            if (cur.yi + 1 < b.length) {
                Coordinate point2 = new Coordinate(cur.xi, cur.yi + 1, cur.zi, a, b, c);
                if (checked.add(point2)) {
                    minHeap.offer(point2);
                }
            }
            if (cur.zi + 1 < c.length) {
                Coordinate point3 = new Coordinate(cur.xi, cur.yi, cur.zi + 1, a, b, c);
                if (checked.add(point3)) {
                    minHeap.offer(point3);
                }
            }
            k--;
        }
        return Arrays.asList(a[cur.xi], b[cur.yi], c[cur.zi]);
    }

    public static void main(String[] args) {
        Leftover ins = new Leftover();
        ins.sortInSpecialOrder(new int[]{4,4,3,3,3}, new int[]{4});
        System.out.println("haha: "+ins.minCostCuttingWood(new int[]{2,4,7}, 10));

        ListNode head = createLinkedList(8);
        ListNode newHead = ins.reverseLinkedList(head);
        printLinkedList(newHead);

        System.out.println("------");
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        ListNode dm = new ListNode(0);
        ListNode h = n1;
        dm.next = h;
        h = h.next.next;
        printLinkedList(dm);

        String s = "a";
    }
}
