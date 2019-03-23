package crosstraining;

import java.util.*;

class RandomListNode {
    public final int value;
    public RandomListNode next;
    public RandomListNode random;
    public RandomListNode(int value) {
        this.value = value;
        this.next = null;
        this.random = null;
    }
}
class GraphNode {
    int value;
    List<GraphNode> neighbors;
    public GraphNode(int value) {
        this.value = value;
        this.neighbors = new ArrayList<>();
    }
}

public class C2LinkedListBstDp {
    public C2LinkedListBstDp() {}

    /**
     * Deep Copy Linked List With Random Pointer
     * Each of the nodes in the linked list has another pointer pointing to a random node
     * in the list or null. Make a deep copy of the original list.
     *
     * Time = O(n)
     * Space = O(n)
     */
    public RandomListNode deepCopyLinkedList(RandomListNode head) {
        if (head == null || (head.next == null && head.random == null)) {
            return new RandomListNode(head.value);
        }
        Map<RandomListNode, RandomListNode> lookup = new HashMap<>();
        RandomListNode cur = head;
        while (cur != null) {
            RandomListNode copiedNode = lookup.get(cur);
            if (copiedNode == null) {
                copiedNode = new RandomListNode(cur.value);
                lookup.put(cur, copiedNode);
            }

            RandomListNode copiedNodeNext = lookup.get(cur.next);
            if (cur.next != null && copiedNodeNext == null) {
                copiedNodeNext = new RandomListNode(cur.next.value);
                lookup.put(cur.next, copiedNodeNext);
            }
            copiedNode.next = copiedNodeNext;

            RandomListNode copiedNodeRandom = lookup.get(cur.random);
            if (cur.random != null && copiedNodeRandom == null) {
                copiedNodeRandom = new RandomListNode(cur.random.value);
                lookup.put(cur.random, copiedNodeRandom);
            }
            copiedNode.random = copiedNodeRandom;

            cur = cur.next;
        }
        return lookup.get(head);
    }

    /**
     * Deep Copy Undirected Graph
     * Make a deep copy of an undirected graph, there could be cycles in the original graph.
     *
     * Time = O(E + V)
     * Space = O(n) // O(height) for the call stack
     */
    public List<GraphNode> copyGraph(List<GraphNode> graph) {
        if (graph == null) {
            return null;
        }
        Map<GraphNode, GraphNode> lookup = new HashMap<>();
        cloneGraphNode(graph.get(0), lookup);
        List<GraphNode> result = new ArrayList<>();
        for (GraphNode node : graph) {
            result.add(lookup.get(node));
        }
        return result;
    }

    private GraphNode cloneGraphNode(GraphNode input, Map<GraphNode, GraphNode> lookup) {
        if (input == null) {
            return null;                     // base case 1
        }
        if (lookup.containsKey(input)) {
            return lookup.get(input);        // base case 2
        }
        // recursive rule
        GraphNode copyNode = new GraphNode(input.value);
        lookup.put(input, copyNode);
        for (GraphNode neighbor : input.neighbors) {
            copyNode.neighbors.add(cloneGraphNode(neighbor, lookup));
        }
        return copyNode;
    }

    /**
     * Merge K Sorted Array
     * Merge K sorted array into one big sorted array in ascending order.
     *
     * Assumptions
     * The input arrayOfArrays is not null, none of the arrays is null either.
     * Time = O(nk * logk) // nk elements, each offered once and polled once
     * Space = O(k) // heap大小, k个element(value, arrayIndex, indexInArray)
     */
    public int[] mergeKSortedArray(int[][] arrayOfArrays) {
        PriorityQueue<Element> minHeap = new PriorityQueue<>(new MyComparator());
        int resultLength = 0;
        /* stored the first element to be merged in each array */
        for (int i = 0; i < arrayOfArrays.length; i++) {
            if (arrayOfArrays[i].length != 0) {
                minHeap.offer(new Element(arrayOfArrays[i][0], i, 0));
            }
            resultLength += arrayOfArrays[i].length;
        }

        int[] result = new int[resultLength];
        int cur = 0; // index of result[]

        /* k-way merge */
        while (!minHeap.isEmpty()) {
            Element temp = minHeap.poll();
            result[cur++] = temp.value;

            if (temp.indexInArray + 1 < arrayOfArrays[temp.arrayIndex].length) {
                // reuse the element but advance the index by 1
                temp.value = arrayOfArrays[temp.arrayIndex][temp.indexInArray + 1];
                temp.indexInArray++;
                minHeap.offer(temp);
            }
        }
        return result;
    }

    public static class Element {
        int value;
        int arrayIndex;
        int indexInArray;
        public Element(int value, int arrayIndex, int indexInArray) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.indexInArray = indexInArray;
        }
    }

    public static class MyComparator implements Comparator<Element> {
        @Override
        public int compare(Element c1, Element c2) {
            if (c1.value == c2.value) {
                return 0;
            }
            return c1.value < c2.value ? -1 : 1;
        }
    }

    /**
     * Merge K Sorted Linked List
     * Merge K sorted lists into one big sorted list in ascending order.
     *
     * Assumptions
     * The input listOfLists is not null, none of the arrays is null either.
     * Time = O(nk * logk) // nk elements, each offered once and polled once
     * Space = O(k) // heap大小, k个ListNode
     */
    public ListNode merge(List<ListNode> listOfLists) {
        // assumption listOfLists is not null, and non of the lists is null
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(new MyComparator2());
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (ListNode n : listOfLists) {
            if (n != null) {
                minHeap.offer(n);
            }
        }
        while (!minHeap.isEmpty()) {
            cur.next = minHeap.poll();
            if (cur.next.next != null) {
                minHeap.offer(cur.next.next);
            }
            cur = cur.next;
        }
        return dummy.next;
    }

    private class MyComparator2 implements Comparator<ListNode> {
        @Override
        public int compare(ListNode n1, ListNode n2) {
            if (n1.value == n2.value) {
                return 0;
            }
            return n1.value < n2.value ? -1 : 1;
        }
    }

    /**
     * Closest Number In Binary Search Tree
     * In a binary search tree, find the node containing the closest number to the given target number.
     * Time = O(height)
     * Space = O(1)
     */
    public int closestInBst(TreeNode root, int target) {
        if (root == null || root.key == target) {
            return root.key;
        }
        int result = root.key; // stores the current result
        while (root != null) {
            if (root.key == target) {
                return root.key;
            } else {
                if (Math.abs(root.key - target) <  Math.abs(result - target)) {
                    result = root.key;
                }
                root = root.key < target ? root.right : root.left;
            }
        }
        return result;
    }

    /**
     * Largest Number Smaller In Binary Search Tree
     * In a binary search tree, find the node containing the largest number smaller than the given target number.
     * If there is no such number, return INT_MIN
     * Time = O(height)
     * Space = O(1)
     */
    public int largestSmaller(TreeNode root, int target) {
        if (root == null) {
            return Integer.MIN_VALUE;
        }
        int result = Integer.MIN_VALUE; // current result
        while (root != null) {
            if (root.key < target) {
                if (target - root.key < Math.abs(target - result)) {
                    result = root.key;
                }
                root = root.right;
            } else {
                root = root.left;
            }
        }
        return result;
    }

    /**
     * Delete In Binary Search Tree
     * Delete the target key K in the given binary search tree if the
     * binary search tree contains K. Return the root of the binary search tree.
     * Time = O(height)
     * Space = O(height)
     */
    public TreeNode deleteInBst(TreeNode root, int key) {
        // base case
        if (root == null) {
            return root;
        }
        // what do you want from your child
        if (root.key < key) {
            root.right = deleteInBst(root.right, key);
        } else if (root.key > key){
            root.left = deleteInBst(root.left, key);
        } else {
            if (root.left == null && root.right == null) {
                // case 1
                return null;
            } else if (root.left == null || root.right == null) {
                // case 2 3
                return root.left == null ? root.right : root.left;
            } else {
                // case 4
                TreeNode smallest = findSmallestLarger(root.right);
                root.key = smallest.key;
                root.right = deleteInBst(root.right, smallest.key);
            }
        }
        return root;
    }

    private TreeNode findSmallestLarger(TreeNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    /**
     * Insert In Binary Search Tree
     * Insert a key in a binary search tree if the binary search tree does not already contain the key.
     * Return the root of the binary search tree.
     * Time = O(height)
     * Space = O(1)
     */
    public TreeNode insertInBst(TreeNode root, int key) {
        if (root == null || root.key == key) {
            return root;
        }
        TreeNode cur = root;
        TreeNode parent = null;
        while (cur != null) {
            if (key > cur.key) {
                parent = cur;
                cur = cur.right;
            } else if (key < cur.key) {
                parent = cur;
                cur = cur.left;
            } else {
                return root;
            }
        }
        // connect newNode to its parent
        TreeNode newNode = new TreeNode(key);
        if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        return root;
    }

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

    public static void main(String[] args) {
        C2LinkedListBstDp ins = new C2LinkedListBstDp();
        System.out.println("haha: "+ins.minCostCuttingWood(new int[]{2,4,7}, 10));
    }
}


