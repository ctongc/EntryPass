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

public class C4ReverseDfs234Sum {
    public C4ReverseDfs234Sum() {
    }

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

    /**
     * 2 sum
     * Determine if there exist two elements in a given array, the sum of which is the given target number.
     *
     * Time = O(n)
     * Space = O(n)
     * if sorted, using two pointers for a space O(1) solution
     */
    public boolean twoSum(int[] array, int target) {
        // Assumptions: array.length >= 2
        // since it only ask to return true or false
        // a HashSet is actually enough for this case

        // iterate over the whole array, for current index i:
        // check whether (target - input[i]) is in the HashMap or not
        //    if yes => solution (HashMap.get(target - input[i], i)
        //    if no => put(input[i] -> i) into the HashMap
        Map<Integer, Integer> lookup = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (lookup.containsKey(target - array[i])) {
                return true;
            } else {
                lookup.put(array[i], i);
            }
        }
        return false;
    }

    /**
     * 2 sum all pair I
     * Find all pairs of elements in a given array that sum to the given target number. Return all the pairs of indices.
     * eg. A = {1, 2, 2, 4}, target = 6, return [[1, 3], [2, 3]]
     * Time = O(n)
     * Space = O(n)
     */
    public List<List<Integer>> twoSumallPairs(int[] array, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Map<Integer, List<Integer>> lookup = new HashMap<>(); // key: element, value: list of indices of the element
        for (int i = 0; i < array.length; i++) {
            List<Integer> indices = lookup.get(target - array[i]); // same one operation than .containsValue then .get
            if (indices != null) {
                for(int j : indices) {
                    result.add(Arrays.asList(i, j)); // quick way to initialize a fix size arrayList
                }
            }
            lookup.computeIfAbsent(array[i], k -> new ArrayList<>());
            lookup.get(array[i]).add(i);
        }
        return result;
    }

    /**
     * 2 sum all pair II
     * Find all pairs of elements in a given array that sum to the pair the given target number.
     * Return all the distinct pairs of values.
     * eg. A = {2, 1, 3, 2, 4, 3, 4, 2}, target = 6, return [[2, 4], [3, 3]]
     * Time = O(n)
     * Space = O(n)
     */
    public List<List<Integer>> twoSumallPairsII(int[] array, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Set<Integer> set = new HashSet<>(); // sorts elements we have met
        int halfCount = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] + array[i] == target) { // deal with the case 3, 3
                halfCount++;
                continue;
            }
            if (set.add(array[i])) {
                if (set.contains(target - array[i])) {
                    result.add(Arrays.asList(target - array[i], array[i]));
                }
            }
        }
        if (halfCount > 1) {
            result.add(Arrays.asList(target / 2, target / 2));
        }
        return result;
    }

    /**
     * Time = O(nlogn)
     * Space = O(1)
     */
    public List<List<Integer>> twoSumallPairsII2(int[] array, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(array);
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            // don't compare left with left + 1
            // compare left with left - 1 to ignore all consecutive dups
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
     * 3 sum
     * Determine if there exists three elements in a given array that sum to the given target number.
     * Return all the triple of values that sums to target.
     * A = {1, 2, 2, 3, 2, 4}, target = 8, return [[1, 3, 4], [2, 2, 4]]
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
            // eg. if we have 2, 2, 2, only the first 2 will be selected as array[i]
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
     * eg. A = {1, 2, 2, 3, 4}, target = 9, return true // 1 + 2 + 2 + 4 = 9
     *     A = {1, 2, 2, 3, 4}, target = 12, return false
     */
    class Pair {
        int leftIndex;
        int rightIndex;
        public Pair(int left, int right) {
            this.leftIndex = left;
            this.rightIndex = right;
        }
    }

    public boolean fourSum(int[] array, int target) {
        // assumptions: given array is not null and has length of at least 4
        // no need to sort first
        Map<Integer, Pair> lookup = new HashMap<>(); // stores 2Sum and both index
        // make sure i1 < j1 < i2 < j2 to avoid duplicate
        // store a pair sum with right index j as small as possible
        for (int j = 1; j < array.length; j++) {
            for (int i = 0; i < j; i++) {
                int sum = array[i] + array[j];
                if (lookup.containsKey(target - sum) && lookup.get(target - sum).rightIndex < i) {
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

    public static void main(String[] args) {
        C4ReverseDfs234Sum ins = new C4ReverseDfs234Sum();
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
}
