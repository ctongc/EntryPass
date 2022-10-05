package com.ctong.entrypass.crosstraining;

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

class Pair {
    int leftIndex;
    int rightIndex;

    public Pair(int left, int right) {
        this.leftIndex = left;
        this.rightIndex = right;
    }
}

public class C2LinkedListBst234Sum {

    public C2LinkedListBst234Sum() {}

    /**
     * Deep Copy Linked List With Random Pointer
     * Each of the nodes in the linked list has another pointer pointing to a random node
     * in the list or null. Make a deep copy of the original list.
     *
     * Time = O(n)
     * Space = O(n)
     */
    public RandomListNode deepCopyLinkedList(RandomListNode head) {
        if (head == null) {
            return null;
        }
        HashMap<RandomListNode, RandomListNode> lookup = new HashMap<>();
        RandomListNode copyHead = new RandomListNode(head.value);
        lookup.put(head, copyHead);

        while (head != null) {
            RandomListNode deepNode = lookup.get(head);
            if (deepNode == null) {
                deepNode = new RandomListNode(head.value);
                lookup.put(head, deepNode);
            }

            if (head.next != null) {
                RandomListNode deepNodeNext = lookup.get(head.next);
                if (deepNodeNext == null) {
                    deepNodeNext = new RandomListNode(head.next.value);
                    lookup.put(head.next, deepNodeNext);
                }
                deepNode.next = deepNodeNext;
            }

            if (head.random != null) {
                RandomListNode deepNodeRandom = lookup.get(head.random);
                if (deepNodeRandom == null) {
                    deepNodeRandom = new RandomListNode(head.random.value);
                    lookup.put(head.random, deepNodeRandom);
                }
                deepNode.random = deepNodeRandom;
            }

            head = head.next;
        }
        return copyHead;
    }

    /**
     * Deep Copy Undirected Graph
     * Make a deep copy of an undirected graph, there could be cycles in the original graph.
     *
     * Time = O(E + V)
     * Space = O(n) // O(height) for the call stack
     */
    public List<GraphNode> deepCopyGraph(List<GraphNode> graph) {
        List<GraphNode> result = new ArrayList<>();
        if (graph == null || graph.isEmpty()) {
            return result;
        }
        HashMap<GraphNode, GraphNode> lookup = new HashMap<>();
        for (GraphNode node : graph) {
            result.add(cloneGraphNode(node, lookup));
        }
        return result;
    }

    private GraphNode cloneGraphNode(GraphNode node, HashMap<GraphNode, GraphNode> lookup) {

        /* For every single recursion function call, we make a copy of the input node
         * and leave all other copies of the successors to the recursion functions. */

        if (node == null) { // base case 1
            return null;
        }
        if (lookup.containsKey(node)) { // base case 2
            return lookup.get(node);
        }

        GraphNode copyNode = new GraphNode(node.value);
        lookup.put(node, copyNode);

        for (GraphNode neighbor : node.neighbors) {
            copyNode.neighbors.add(cloneGraphNode(neighbor, lookup));
        }

        return copyNode;
    }

    /**
     * Closest Number In Binary Search Tree
     * In a binary search tree, find the node containing the closest number to the given target number
     *
     * Time = O(height)
     * Space = O(1)
     */
    public TreeNode closestNodeInBst(TreeNode root, int target) {
        if (root == null || root.key == target) {
            return root;
        }
        TreeNode result = root;
        while (root != null) {
            if (root.key == target) {
                return root;
            } else {
                if (Math.abs(root.key - target) < Math.abs(result.key - target)) {
                    result = root;
                }
                root = root.key > target ? root.left : root.right;
            }
        }
        return result;
    }

    /**
     * Largest Number Smaller Than Target In Binary Search Tree
     * In a binary search tree, find the node containing the largest number smaller
     * than the given target number. If there is no such number, return INT_MIN
     *
     * Time = O(height)
     * Space = O(1)
     */
    public TreeNode largestSmallerNodeInBst(TreeNode root, int target) {
        if (root == null) {
            return null;
        }
        TreeNode result = null;
        while (root != null) {
            if (target <= root.key) {
                root = root.left;
            } else {
                if (result == null
                        || Math.abs(root.key - target) < Math.abs(result.key - target)) {
                    result = root;
                    root = root.right;
                }
            }
        }
        return result;
    }

    /**
     * 2 sum
     * Determine if there exist two elements in a given array
     * the sum of which is the given target number
     *
     * if sorted, using two pointers for a space O(1) solution
     *
     * Time = O(n)
     * Space = O(n)
     */
    public boolean twoSum(int[] array, int target) {
        if (array == null || array.length < 2) {
            return false;
        }
        /* for each element in the array, check if (target - input[i]) is in the set
         * 1. if yes => return true
         * 2. if no => add input[i] to the set */
        Set<Integer> lookup = new HashSet<>();
        for (int i : array) {
            if (lookup.contains(target - array[i])) {
                return true;
            } else {
                lookup.add(array[i]);
            }
        }
        return false;
    }

    /**
     * 2 Sum All Pairs I
     * Find all pairs of elements in a given array that sum to the given target number,
     * return all the pairs of indices.
     * e.g. A = {1, 2, 2, 4}, target = 6, return [[1, 3], [2, 3]]
     *
     * Time = O(n)
     * Space = O(n)
     */
    public List<List<Integer>> twoSumAllPairs(int[] array, int target) {
        if (array == null || array.length < 2) {
            return Collections.emptyList();
        }

        List<List<Integer>> result = new ArrayList<>();
        HashMap<Integer, List<Integer>> lookup = new HashMap<>(); // <value, List<index>>
        for (int i = 0; i < array.length; i++) {
            List<Integer> compList = lookup.get(target - array[i]);
            if (compList != null) { // get all pairs<j,i> with i as the larger index
                for (int j : compList) {
                    result.add(Arrays.asList(j, i));
                }
            }

            List<Integer> indexList = lookup.get(array[i]);
            if (indexList == null) {
                lookup.put(array[i], new ArrayList<>(Arrays.asList(i)));
            } else {
                indexList.add(i);
                lookup.put(array[i], indexList);
            }
        }
        return result;
    }

    /**
     * 2 Sum All Pairs II
     * Find all pairs of elements in a given array that sum to the given target number,
     * return all the distinct pairs of values.
     * e.g. A = {2, 1, 3, 2, 4, 3, 4, 2}, target = 6, return [[2, 4], [3, 3]]
     *
     * Time = O(n)
     * Space = O(n)
     */
    public List<List<Integer>> twoSumAllPairsII(int[] array, int target) {
        if (array == null || array.length < 2) {
            return Collections.emptyList();
        }

        List<List<Integer>> result = new ArrayList<>();
        Set<Integer> set = new HashSet<>(); // store elements we have met
        boolean twoSameElements = false;
        for (int element : array) {
            if (set.contains(target - element)) {
                if (set.add(element)) {
                    result.add(Arrays.asList(target - element, element));
                }
                if (element * 2 == target) { // deal with the case [3, 3]
                    twoSameElements = true;
                }
            } else {
                set.add(element);
            }
        }
        if (twoSameElements) {
            result.add(Arrays.asList(target / 2, target / 2));
        }
        return result;
    }

    /**
     * Time = O(nlogn)
     * Space = O(1)
     */
    public List<List<Integer>> twoSumAllPairsII2(int[] array, int target) {
        if (array == null || array.length < 2) {
            return Collections.emptyList();
        }

        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(array);
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            // don't compare left with left + 1
            // compare left with left - 1 to ignore all consecutive duplicates
            // so if we have multiple values, only the first one will be count
            if (left > 0 && array[left] == array[left - 1]) {
                left++;
                continue;
            }
            int sum = array[left] + array[right];
            if (sum == target) {
                result.add(Arrays.asList(array[left++], array[right--]));
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return result;
    }

    /**
     * 3 Sum
     * Determine if there exists three elements in a given array that sum to the given target number.
     * Return all the triple of values that sums to target.
     * A = {1, 2, 2, 3, 2, 4}, target = 8, return [[1, 3, 4], [2, 2, 4]]
     *
     * Time = O(n^2)
     * Space = O(1)
     */
    public List<List<Integer>> threeSum(int[] array, int target) {
        // assumptions: array != null && array.length >= 3
        Arrays.sort(array); // O(nlogn) < O(n^2)
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < array.length - 2; i++) {
            // find i < j < k that array[i] + array[j] + array[k] == target
            // to avoid duplicate tuple, we ignore all following duplicate number
            // e.g. if we have 2, 2, 2, only the first 2 will be selected as array[i]
            if (i > 0 && array[i] == array[i - 1]) {
                continue;
            }
            int j = i + 1;
            int k = array.length - 1;
            while (j < k) {
                int curSum = array[i] + array[j] + array[k];
                if (curSum == target) {
                    result.add(Arrays.asList(array[i], array[j], array[k]));
                    j++;
                    // ignore all following duplicate j
                    while (j < k && array[j] == array[j - 1]) {
                        j++;
                    }
                } else if (curSum < target) {
                    j++;
                } else {
                    k--;
                }
            }
        }
        return result;
    }

    /**
     * 4 Sum
     * Determine if there exists a set of four elements in a given array that sum to the given target number.
     * e.g. A = {1, 2, 2, 3, 4}, target = 9, return true // 1 + 2 + 2 + 4 = 9
     *      A = {1, 2, 2, 3, 4}, target = 12, return false
     *
     * Time = O(n^2)
     * Space = O(n)
     */
    public boolean fourSum(int[] array, int target) {
        // assumptions: given array is not null and has length of at least 4
        // no need to sort first
        Map<Integer, Pair> lookup = new HashMap<>(); // stores 2Sum and both index
        // make sure i1 < j1 < i2 < j2 to avoid duplicate
        // store a pair of sum with right index j as small as possible
        for (int j = 1; j < array.length; j++) { // right index
            for (int i = 0; i < j; i++) { // left index
                int sum = array[i] + array[j];
                Pair compPair = lookup.get(target - sum);
                if (compPair != null && compPair.rightIndex < i) {
                    return true;
                }
                // only stores the pair with smallest right index
                if (!lookup.containsKey(sum)) {
                    lookup.put(sum, new Pair(i, j));
                }
            }
        }
        return false;
    }
}


