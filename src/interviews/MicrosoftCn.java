package interviews;

import java.util.*;

class ListNode {

    int value;
    ListNode next;

    public ListNode(int value) {
        this.value = value;
    }

    public ListNode(int value, ListNode next) {
        this.value = value;
        this.next = next;
    }
}

class TreeNode {

    public int key;
    public TreeNode left;
    public TreeNode right;
    // public TreeNode parent;

    public TreeNode(int key) {
        this.key = key;
    }
}

public class MicrosoftCn {

    /**
     * Return the maximum possible value obtained by deleting one '5' digit from
     * the decimal representation. It's guaranteed that num has at least one '5'.
     *
     * Time = O(n)
     * Space = O(n)
     */
    public int removeFive(int num) {
        String numString = String.valueOf(num); // this still keeps the sign
        int deletePos = -1;
        if (numString.charAt(0) != '-') { // positive number
            for (int i = 0; i < numString.length(); i++) {
                if (numString.charAt(i) == '5') {
                    deletePos = i;
                    if (i + 1 < numString.length() && numString.charAt(i + 1) > '5') { // charAt() returns char!!!
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i < numString.length(); i++) {
                if (numString.charAt(i) == '5') {
                    deletePos = i;
                    if (i + 1 < numString.length() && numString.charAt(i + 1) < '5') {
                        break;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numString.length(); i++) {
            if (i == deletePos) {
                continue;
            }
            sb.append(numString.charAt(i));
        }

        return Integer.parseInt(sb.toString());
    }

    /**
     * Min Deletions To Obtain String in Right Format
     * Given a string with only characters X and Y. Find the minimum number of characters
     * to remove from the string such that there is no interleaving of character X and Y
     * and all the Xs appear before any Y.
     *
     * e.g. Input: BAAABAB
     * Output: 2
     * We can obtain AAABB by:
     * Delete first B -> AAABAB
     * Delete last occurrence of A -> AAABB
     *
     * e.g. Input:BBABAA
     * Output: 3
     * We can remove all occurrence of A or B
     */
    public int minStep(String str) {
        int numB = 0;
        int minDel = 0;

        //      BAAABAB
        //            i
        // numB 1111223
        // minD 0111122
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'A') {
                minDel = Math.min(numB, minDel + 1);
            } else {
                numB++;
            }
        }
        return minDel;
    }

    /**
     * Given an array A of N integers, returns the smallest positive integer (greater
     * than 0) that does not occur in A.
     *
     * e.g. A = [1, 3, 6, 4, 1, 2], return 5
     * e.g. A = [1, 2, 3], return 4
     * e.g. A = [−1, −3], return 1.
     *
     * Assumptions:
     * 1. N is an integer within the range [1..100,000]
     * 2. each element of array A is an integer within the range [−1,000,000..1,000,000]
     */
    public int solution(int[] A) {
        if (A == null || A.length == 0) {
            return 1;
        }

        boolean negCheck = false;
        boolean[] lookup = new boolean[A.length + 1];
        for (int i : A) {
            if (i > 0) {
                negCheck = true;
            }
            if (i > 0 && i <= A.length) {
                lookup[i] = true;
            }
        }

        if (!negCheck) {
            return 1;
        }

        for (int i = 1; i < lookup.length; i++) {
            if (!lookup[i]) {
                return i;
            }
        }

        return A.length + 1;
    }

    /**
     * Bing团队: 先增后减的数组，求最大的index
     */
    public int maxInPeekLikeArray(int[] array) {
        if (array == null || array.length == 0) {
            return -1;
        }

        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] < array[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    /**
     * Azure: 查找数组中第一个大于target的数字返回下标
     */
    public int smallestElementLargerThanTarget(int[] array, int target) {
        if (array == null || array.length == 0) {
            return -1;
        }

        // {1, 2, 2, 2, 3}, T = 2
        //  l     M     r
        //  case 1: a[mid] = T -> left = mid
        //  case 2: a[mid] < T -> left = mid
        //  case 3: a[mid] > T -> right = mid
        int left = 0;
        int right = array.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (array[mid] <= target) {
                left = mid;
            } else {
                right = mid;
            }
        }

        // post-processing
        if (array[left] > target) {
            return left;
        }
        if (array[right] > target) {
            return right;
        }

        return - 1;
    }

    /**
     * Ads: 657 模拟机器人上下左右走动最后判断是否能返回原点
     */
    public boolean judgeCircle(String moves) {
        int ver = 0;
        int hor = 0;

        for (char move : moves.toCharArray()) {
            switch (move) {
                case 'U' -> ver++;
                case 'D' -> ver--;
                case 'R' -> hor++;
                case 'L' -> hor--;
            }
        }

        return ver == 0 && hor == 0;
    }

    /**
     * Ads: meeting schedule
     */
    public int maxMeetings(int[][] intervals) {
        /* perform greedy
         * 1. sort by ending time
         * 2. pick by ending time without conflicts */
        Arrays.sort(intervals, Comparator.comparingInt(m -> m[1]));

        int count = 1;
        int lastMeetingEndTime = intervals[0][1];
        for (int[] meetings : intervals) {
            if (meetings[0] > lastMeetingEndTime) {
                count++;
                lastMeetingEndTime = meetings[1];
            }
        }

        return count;
    }

    /**
     * Meeting rooms
     */
    public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(m -> m[0]));

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int meetingRoom = 1;
        minHeap.offer(intervals[0][1]);
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < minHeap.peek()) {
                minHeap.offer(intervals[i][1]);
                meetingRoom = Math.max(meetingRoom, minHeap.size());
            } else {
                minHeap.poll();
                minHeap.offer(intervals[i][1]);
            }
        }

        return meetingRoom;
    }

    /**
     * LeetCode 739 每日温度
     */
    public int[] dailyTemperatures(int[] temperatures) {
        // updating warmerDay[i] while i has been popped out from the stack
        // 1. if curTemp > stack.peek(), which is j,
        //    stack.pop(), update warmerDay[j], until curTemp < stack.peek(), stack.push(curTemp)
        // 2. if no date to update or curTemp < stack.peek(), no day can be updated, stack.push(curTemp)
        int[] warmerDay = new int[temperatures.length];
        Deque<Integer> stack = new ArrayDeque<>(); // warmerDays that hasn't been updated

        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int date = stack.pollFirst();
                warmerDay[date] = i - date;
            }
            stack.offerFirst(i);
        }

        return warmerDay;
    }

    /**
     * LeetCode 261 判断图是否是二叉树
     * Given n nodes labeled from 0 to n-1 and a list of undirected edges (each edge is a pair of nodes),
     * write a function to check whether these edges make up a valid tree
     */
    public boolean validTree(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            // 双向连接
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        Set<Integer> visited = new HashSet<>();
        // A tree is a special undirected graph. It satisfies two properties
        // - It is connected
        // - It has no cycle
        return dfs(graph, 0, -1, visited) && visited.size() == n;
    }

    private boolean dfs(List<List<Integer>> graph, int node, int pre, Set<Integer> visited) {
        if (!visited.add(node)) {
            return false;
        }

        for (int neighbor : graph.get(node)) {
            if (neighbor != pre) { // 查重时排除父节点
                if(!dfs(graph, neighbor, node, visited)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 反转链表、每隔k个反转链表
     */
    public ListNode reverseLinkedList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode prev = null;
        while (head != null) { // if using while (head.next != null) then it won't reverse the last element
            ListNode nextNode = head.next; // always store the new head first
            head.next = prev; // reserve happens here, 记住是在reverse head->prev而不是head.next->head
            prev = head; // move prev by one
            head = nextNode; // move current by one
        }
        // now head == null

        return prev;
    }

    public ListNode reverseLinkedListRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = reverseLinkedListRecursive(head.next); // newHead is the last node of current linked list

        head.next.next = head; // head.next is the next node
        head.next = null; // reset

        return newHead;
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        // post-processing
        ListNode prev = null;
        ListNode cur = head;
        for (int i = 0; i < left - 1; i++) {
            prev = cur;
            cur = cur.next;
        }
        ListNode frontTail = prev;
        ListNode newLinkTail = cur;

        // start reversing
        for (int i = 0; i < right - left + 1; i++) {
            ListNode nextNode = cur.next;
            cur.next = prev;
            prev = cur;
            cur = nextNode;
        }

        // post-processing
        if (frontTail != null) {
            frontTail.next = prev;
        } else { // frontTail == null
            head = prev; // head == newLinkTail, prev points to the newLinkHead
        }
        newLinkTail.next = cur;

        return head;
    }

    public ListNode reverseInPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummyHead = new ListNode(0);
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

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null || k == 1) {
            return head;
        }

        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode lastKTail = dummyHead;
        ListNode cur = head;

        int index = 0;
        while (cur != null) {
            cur = cur.next;
            index++;
            if (index % k == 0) {
                lastKTail = reverseBetweenTwoNodes(lastKTail, cur); // points to the lastNode in last group
                cur = lastKTail.next;
            }
        }

        return dummyHead.next;
    }

    private ListNode reverseBetweenTwoNodes(ListNode start, ListNode end) {
        // t(start)            c(end)
        // A -> 1 -> 2 -> 3 -> B
        //      l    c
        // A    1 <- 2    3 -> B
        // |_________|    n
        //                c
        // A    1 <- 2 <- 3 -> B
        // |______________|    n
        //                     c
        // A -> 3 -> 2 -> 1 -> B

        ListNode newLastNode = start.next;
        ListNode cur = newLastNode.next; // reverse from the second node in link

        while(cur != end) {
            ListNode next = cur.next;
            cur.next = start.next;
            start.next = cur;
            cur = next;
        }
        newLastNode.next = end;

        return newLastNode;
    }

     /**
      * n个球内选k个，所有的排列组合
      */
     public int nChooseK(int n, int k) {
         if (n < k) {
             return 0;
         }
         if (n == k) {
             return 1;
         }
         if (n == 0) {
             return 0;
         }
         return nChooseK(n - 1, k - 1) + nChooseK(n - 1, k);
     }

    public List<List<String>> nChooseK(String[] n, int k) {
         if (n == null || n.length == 0 || n.length < k) {
             return Collections.emptyList();
         }

         List<List<String>> result = new ArrayList<>();
         nChooseKAllPerm(n, k, 0, new ArrayList<>(), result);

         return result;
    }

    private void nChooseKAllPerm(String[] n, int k, int index, List<String> cur, List<List<String>> result) {
         if (cur.size() == k) {
             result.add(new ArrayList<>(cur));
             return;
         }

         if (cur.size() + n.length - index < k) {
             return;
         }

         cur.add(n[index]);
         nChooseKAllPerm(n, k, index + 1, cur, result);
         cur.remove(cur.size() - 1);

         nChooseKAllPerm(n, k, index + 1, cur, result);
    }

    /**
     * 树的反序列化、二叉树查找父节点
     */
    public String serialize(TreeNode root) {
        if (root == null) {
            return "null,";
        }

        return root.key + "," + serialize(root.left) + serialize(root.right);
    }

    public TreeNode deserialize(String data) {
        String[] dataArray = data.split(",");
        List<String> dataList = new ArrayList<>(Arrays.asList(dataArray));

        return deserializer(dataList);
    }

    private TreeNode deserializer(List<String> dataList) {
        if ("null".equals(dataList.get(0))) {
            dataList.remove(0);
            return null;
        }

        TreeNode root = new TreeNode(Integer.parseInt(dataList.get(0)));
        dataList.remove(0);
        root.left = deserializer(dataList);
        root.right = deserializer(dataList);

        return root;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode leftLCA = lowestCommonAncestor(root.left, p, q);
        TreeNode rightLCA = lowestCommonAncestor(root.right, p, q);

        if (leftLCA != null && rightLCA != null) {
            return root;
        }

        return leftLCA == null ? rightLCA : leftLCA;
    }

    public TreeNode lcaNotGuaranteeInTree(TreeNode root, TreeNode one, TreeNode two) {
        if (root == null) {
            return null;
        }

        TreeNode result = lowestCommonAncestor(root, one, two);

        if (result != one && result != two && result != null) {
            return result;
        }

        if (result == one) {
            if (two == null || lowestCommonAncestor(one, two, two) != null) {
                return result;
            }
        } else if (result == two) {
            if (one == null || lowestCommonAncestor(two, one, one) != null) {
                return result;
            }
        }

        return null;
    }

    /**
     * 下一个排列，给一个数字，找出下一个排列
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
        if (left >= 0) {
            int right = nums.length - 1;
            while(nums[right] <= nums[left]) {
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
     * String to Double实现
     * 系统设计题：京东优惠券，大致思路，关键数据结构设计，数据存储等等
     * 做一个Throttling downloader. 类似于实现一个可以限制速度的下载器。用的token bucket算法完成的。
     * 做一个支持20000000user的排名系统，User登录评论可以加分，要返回user的排名
     * 问了一些java的内容，GC, 多线程, 然后系统设计tiny url
     */

    /**
     * 快排
     */
    public void quickSort(int[] a) {
        if (a == null || a.length <= 1) {
            return;
        }

        quickSort(a, 0, a.length - 1);
    }

    private void quickSort(int[] a, int left, int right) {
        if (left >= right) {
            return;
        }

        // now the pivot is sorted
        int pivot = partition(a, left, right);
        // sort the left side of the pivot
        quickSort(a, left, pivot - 1);
        // sort the right side of the pivot
        quickSort(a, pivot + 1, right);
    }

    private int partition(int[] a, int left, int right) {
        int pivot = (int) (left + Math.random() * (right - left + 1));
        int pivotValue = a[pivot];
        int last = right;
        // move pivot to the last, and keep it there for comparison
        swap(a, pivot, right--);

        while (left <= right) {
            if (a[left] < pivotValue) { // find elements on left is greater than pivot
                left++;
            } else if (a[right] > pivotValue) { // find elements on right is less than pivot
                right--;
            } else {
                swap(a, left++, right--);
            }
        }

        // left on the first element that greater than pivot
        swap(a, left, last);

        return left;
    }

    /**
     * 给定一个含有重复元素的有序数组nums，和一个数A，在有序数组中寻找数A的起止位置
     * 如果没找到返回[-1, -1]
     *
     * e.g. Nums = [1,3,5,5,5,6,9], A = 5
     * 输出结果为[2,4]
     */
    public int[] getRangeOfSameTargetValue(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                // should be optimized O(n) => O(logn)
                // int left = firstOccurrence();
                // int right = lastOccurrence();
                int i = mid; // go left
                int j = mid; // go right
                while (i >= 0 && nums[i] == target) {
                    i--;
                }
                while (j <= nums.length - 1 && nums[j] == target) {
                    j++;
                }

                return new int[]{i + 1, j - 1};
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return new int[]{-1, -1};
    }

    /**
     * 英文句子换行
     * 把英文句子打印在纸上，每行有最大字符数限制，不足时换行。
     * 输入句子和每行字符数，输出一个数组，其中每个元素为本行内容。
     *
     * 句子只由“a-z.,”空格组成。其中“.,”后面有空格。有下列额外要求：
     * 一个单词只能出现在单行上，不能断开。此时需要从上一个空格提前换行。
     * 如果一行的最后是“.,”，那么这行可以超出字符数限制1个字符。
     *
     * "The quick brown fox jumps over the lazy dog", 9
     * ["The quick", "brown fox", "jumps", "over the", "lazy dog"]
     *
     * "Hello, world. Hello Microsoft.", 12
     * ["Hello, world.", "Hello", "Microsoft."]
     *
     * "Hello, world. Hello Microsoft.", 11
     * ["Hello,", "world.", "Hello", "Microsoft."]
     */
    public List<String> printWords(String s, int limit) {
        List<String> result = new ArrayList<>();
        String[] words = s.split(" ");
        int count = 0;
        int index = 0;

        StringBuilder sb = new StringBuilder();

        while (index < words.length) {
            while (index < words.length && (words[index].length() + count < limit)) {
                sb.append(words[index]);
                count += words[index++].length();
                if (count < limit) {
                    sb.append(" ");
                }
            }

            if (sb.charAt(sb.length() - 1) == ' ') {
                sb.deleteCharAt(sb.length() - 1); // remove tailing space
            }

            // case 1: index > words
            if (index == words.length && !sb.isEmpty()) {
                result.add(sb.toString());
                break;
            }

            // case 2: index < words && sb + word > limit
            String curWord = words[index];
            int curWordLength = curWord.length();
            if ((count + curWordLength == limit + 1)
                    && ((curWord.charAt(curWordLength - 1) == '.'
                    || curWord.charAt(curWordLength - 1) == ','))) {
                sb.append(curWord);
            }

            count = 0;
            result.add(sb.toString());
            sb.setLength(0);
        }

        return result;
    }

    static class TreeNodeP {

        int val;
        TreeNodeP left;
        TreeNodeP right;
        TreeNodeP parent;

        TreeNodeP(int value) {
            this.val = value;
        }

        TreeNodeP(int value, TreeNodeP parent) {
            this.val = value;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return val + "";
        }
    }

    /**
     * Next TreeNode in in-Order traversal, with Parent pointer
     */
    public TreeNodeP nextNodeInOrderTraversal(TreeNodeP node) {
        if (node == null) {
            return null;
        }

        TreeNodeP cur = node;
        /* case 1 如果右边有, 往右走一步, 再往左走到底 打印
         * case 2 右边没有
         *   - case 2.1: cur == cur.parent.left, 左边和自己已经全遍历完了
         *               打印parent
         *   - case 2.2: cur == cur.parent.right, parent的左边和parent已经全遍历完了, 自己也遍历完了
         *               往上找到parent是别的node的left的情况，打印该parent */
        if (cur.right != null) {
            cur = cur.right;
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur;
        } else {
            if (cur == cur.parent.left) {
                return cur.parent;
            } else {
                while (cur.parent != null) {
                    cur = cur.parent;
                    if (cur.parent != null && cur == cur.parent.left) {
                        return cur.parent;
                    }
                }
            }
        }
        
        return null;
    }

    /**
     * in order iterator of binary tree
     */
    static class BinaryTreeIterator {
        Deque<TreeNode> stack;
        TreeNode node;

        public BinaryTreeIterator(TreeNode root) {
            this.stack = new ArrayDeque<>();
            this.node = root;
        }

        public boolean hasNext() {
            return node != null || !stack.isEmpty();
        }

        public int next() {
            while (node != null) {
                stack.offerFirst(node);
                node = node.left;
            }
            if (hasNext()) {
                node = stack.pollFirst();
                int curVal = node.key;
                node = node.right;
                return curVal;
            } else {
                throw new NoSuchElementException("no next value!");
            }
        }
    }

    /**
     * [0, 0,  1,  2,  0,  1,  1]  candidate list
     * [0, 3, 10, 15, 20, 25, 30]  timestamp list
     * input: 18, output: 0
     * input: 30, output: 1
     */
    // int[] elected = new int[TimeStamp.length] 下标对应timestamp
    // binary search - log(n) 找timestamp里最接近input的两个数
    // O(n)
    // O(1)

    /**
     * Tag Validator + form an n-nary tree
     * https://leetcode.com/problems/tag-validator/
     */

    /**
     * Multiply Strings
     * https://leetcode.com/problems/multiply-strings/
     * Given two non-negative integers num1 and num2 represented as strings,
     * return the product of num1 and num2, also represented as a string.
     * In order to simply the problem, there is no string which contains
     * leading “0” except for the num is 0.
     */
    public String multiplyStrings(String num1, String num2) {
        int[] num = new int[num1.length() + num2.length()];
        for (int i = num1.length() - 1; i >= 0; i--) {
            for (int j = num2.length() - 1; j >= 0; j--) {
                int product = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int digit = num[i + j + 1];
                int sum = product + digit;

                num[i + j] += sum / 10;
                num[i + j + 1] = sum % 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i : num) {
            if (!(sb.length() == 0 && i == 0)) {
                sb.append(i);
            }
        }

        return sb.length() == 0 ? "0" : sb.toString();
    }

    /**
     * 870. Advantage Shuffle
     * You are given two integer arrays nums1 and nums2 both of the same length.
     * The advantage of nums1 with respect to nums2 is the number of indices i for
     * which nums1[i] > nums2[i].
     *
     * Return any permutation of nums1 that maximizes its advantage with respect to nums2.
     *
     * e.g. num1 = {3, 9, 11, 5, 1}, num2 = {6, 4, 10, 8, 2}
     * output = {9, 5, 1, 11, 3} or {9, 5, 11, 1, 3}
     */
    public int[] advantageCount(int[] nums1, int[] nums2) {
        // sanity check
        if (nums1 == null || nums2 == null
                || nums1.length == 0 || nums2.length == 0) {
            throw new IllegalArgumentException("nums1 or nums2 can't be empty!");
        }

        // pre-processing
        TreeMap<Integer, Integer> counts = new TreeMap<>();
        for (int num : nums1) {
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }

        int[] result = new int[nums1.length];
        for (int i = 0; i < nums2.length; i++) {
            Integer cur = counts.higherKey(nums2[i]);
            if (cur == null) {
                cur = counts.firstKey();
            }

            result[i] = cur;

            if (counts.get(cur) == 1) {
                counts.remove(cur);
            } else {
                counts.put(cur, counts.get(cur) - 1);
            }
        }

        return result;
    }

    public int[] advantageCount2(int[] nums1, int[] nums2) {
        // sanity check
        if (nums1 == null || nums2 == null
                || nums1.length == 0 || nums2.length == 0) {
            throw new IllegalArgumentException("nums1 or nums2 can't be empty!");
        }

        int[] result = new int[nums1.length];
        Integer[] order = new Integer[nums2.length]; // ascending nums2 order
        for (int i = 0; i < nums2.length; i++) {
            order[i] = i;
        }

        Arrays.sort(nums1);
        Arrays.sort(order, Comparator.comparingInt(a -> nums2[a]));

        int left = 0;
        int right = nums2.length - 1;
        for (int i = order.length - 1; i >= 0; i--) {
            // if largest of nums1 < largest nums2
            // assign smallest nums1 to largest nums2
            if (nums1[right] > nums2[order[i]]) {
                result[order[i]] = nums1[right--];
            } else {
                result[order[i]] = nums1[left++];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        MicrosoftCn ins = new MicrosoftCn();
        System.out.println(ins.removeFive(15759));
        System.out.println(ins.removeFive(-15759));
        System.out.println(ins.removeFive(5759));
        System.out.println(ins.removeFive(-5759));
        System.out.println(ins.removeFive(153515));
        System.out.println(ins.removeFive(-153515));
        System.out.println(ins.removeFive(6535153));
        System.out.println(ins.removeFive(-6535153));
        System.out.println(ins.removeFive(6535156));
        System.out.println(ins.removeFive(-6535156));
        System.out.println(ins.removeFive(-5000));

        System.out.println(ins.minStep("AAABBB"));
        System.out.println(ins.minStep("BBAABBBB"));
        System.out.println(ins.minStep("BAAABAB"));

        ins.solution(new int[]{1,3,6,4,1,2});
        System.out.println(ins.validTree(3, new int[][]{{1,0},{0,2},{2,1}}));

        List<List<String>> nChooseK = ins.nChooseK(new String[]{"a","b","c","d","e","f","g"}, 3);
        System.out.println(nChooseK.size());
        System.out.println(ins.nChooseK(7, 3));

        // [1,3,5,5,5,6,9] A = 5,
        int[] a = ins.getRangeOfSameTargetValue(new int[]{1,3,5,5,5,6,9}, 5);
        System.out.println(a[0] + " " + a[1]);

        String s = "The quick brown fox jumps over the lazy dog";
        List<String> list = ins.printWords(s, 9);
        System.out.println(list);

        //         1
        //      2      3
        //   4       6   7
        // 8   9
        //  10
        // 8 10 4 9 2 1 6 3 7
        TreeNodeP t1 = new TreeNodeP(1);
        TreeNodeP t2 = new TreeNodeP(2, t1);
        TreeNodeP t3 = new TreeNodeP(3, t1);
        TreeNodeP t4 = new TreeNodeP(4, t2);
        TreeNodeP t6 = new TreeNodeP(6, t3);
        TreeNodeP t7 = new TreeNodeP(7, t3);
        TreeNodeP t8 = new TreeNodeP(8, t4);
        TreeNodeP t9 = new TreeNodeP(9, t4);
        TreeNodeP t10 = new TreeNodeP(10, t8);
        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t3.left = t6;
        t3.right = t7;
        t4.left = t8;
        t4.right = t9;
        t8.right = t10;
        // System.out.println(ins.nextNodeInOrderTraversal(t7).val);

        // case 1 null node
        TreeNode nullNode = null;
        BinaryTreeIterator it1 = new BinaryTreeIterator(nullNode);
        // it1.next(); will throw exception

        // case 2 has one node
        TreeNode n1 = new TreeNode(0);
        BinaryTreeIterator it2 = new BinaryTreeIterator(n1);
        System.out.println(it2.hasNext());
        it2.next();
        System.out.println(it2.hasNext());

        // case 3 tree height > 3
        //        1
        //     2     3
        //   4  5  6
        // 7
        // inorder -> 7 4 2 5 1 6 3
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        BinaryTreeIterator it3 = new BinaryTreeIterator(node1);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node4.left = node7;
        for (int i = 0; i < 7; i++) {
            System.out.print(it3.next() + " ");
        }

        System.out.println(ins.multiplyStrings("0", "1"));
        System.out.println(ins.multiplyStrings("9", "9"));
        System.out.println(ins.multiplyStrings("99", "99"));
        System.out.println(ins.multiplyStrings("11322324234523532532453453453245345235235235253",
                "11322324234523532532453453453245345235235235253"));
    }
}
