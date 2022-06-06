package interviews;

import java.util.*;

public class Cider {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    static class ListNode {
        int val;
        ListNode next;
        public ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 53. Maximum Subarray
     * https://leetcode.com/problems/maximum-subarray/
     * Given an integer array nums, find the contiguous subarray (containing at
     * least one number) which has the largest sum and return its sum.
     *
     * A subarray is a contiguous part of an array.
     *
     * e.g. nums = [-2,1,-3,4,-1,2,1,-5,4]
     * Output: 6 -> [4,-1,2,1] has the largest sum = 6.
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int max = nums[0];
        int temp = nums[0];
        for (int i = 1; i < nums.length; i++) {
            temp = Math.max(nums[i], temp + nums[i]);
            max = Math.max(max, temp);
        }

        return max;
    }

    /**
     * 695. Max Area of Island
     * https://leetcode.com/problems/max-area-of-island/
     * 给定一个二维数组, 0表示海, 1表示岛屿, 岛屿的上下左右任意一个方向有1就表示岛屿连接, 否则不连接, 求最大岛屿面积
     * Time = O(row * col)
     * Space = O(row * col)
     */
    public int maxAreaOfIsland(int[][] grid) {
        int max = 0;
        int numOfRow = grid.length;
        int numOfCol = grid[0].length;

        boolean[][] visited = new boolean[numOfRow][numOfCol]; // mark grid[i][j] to 0 could save this

        for (int i = 0; i < numOfRow; i++) {
            for (int j = 0; j < numOfCol; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    int cur = getArea(i, j, grid, visited);
                    max = Math.max(cur, max);
                }
            }
        }
        return max;
    }

    private int getArea(int row, int col, int[][] grid, boolean[][] visited) {
        if (row < 0 || row >= grid.length
                || col < 0 || col >= grid[0].length
                || grid[row][col] != 1 || visited[row][col]) {
            return 0;
        }

        visited[row][col] = true;
        int up = getArea(row - 1, col, grid, visited);
        int down = getArea(row + 1, col, grid, visited);
        int left = getArea(row, col - 1, grid, visited);
        int right = getArea(row, col + 1, grid, visited);

        return 1 + up + down + left + right;
    }

    /**
     * 给定分类原始数据，实现构建分类树逻辑，并实现打印分类树逻辑
     * 女装
     *     上装
     *         衬衫
     *             短袖衬衫
     *             长袖衬衫
     *         T恤
     *             长袖T
     *             短袖T
     *         毛衣
     *         外套
     *     下装
     *         裙子
     *             短裙
     *             长裙
     *         裤子
     *             长裤 */

    /**
     * 1. Two Sum
     * https://leetcode.com/problems/two-sum/
     * 给定一个整数数组nums和一个目标值target, 在该数组中找出和为目标值的那两个整数, 并返回他们的数组下标
     * Time = O(n)
     * Space = O(n)
     */
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[]{-1, -1};
        }

        Map<Integer, Integer> lookup = new HashMap<>(); // <num, index>

        for (int i = 0; i < nums.length; i++) {
            int complement = lookup.getOrDefault(target - nums[i], -1);
            if (complement != -1) {
                return new int[]{complement, i};
            }
            lookup.put(nums[i], i);
        }

        return new int[]{-1, -1};
    }

    /**
     * 103. Binary Tree Zigzag Level Order Traversal
     * 蛇形遍历一棵二叉树
     * https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/submissions/
     * Given the root of a binary tree, return the zigzag level order traversal of its nodes' values.
     * (i.e., from left to right, then right to left for the next level and alternate between).
     *
     * Time = O(N)
     * Space = O(N)
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }

        List<List<Integer>> result = new ArrayList<>();
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.offerLast(root);
        boolean isOddLevel = true;

        while (!deque.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                if (isOddLevel) {
                    TreeNode cur = deque.pollFirst();
                    level.add(cur.val);
                    if (cur.left != null) {
                        deque.offerLast(cur.left);
                    }
                    if (cur.right != null) {
                        deque.offerLast(cur.right);
                    }
                } else {
                    TreeNode cur = deque.pollLast();
                    level.add(cur.val);
                    if (cur.right != null) {
                        deque.offerFirst(cur.right);
                    }
                    if (cur.left != null) {
                        deque.offerFirst(cur.left);
                    }
                }
            }
            result.add(level);
            isOddLevel = !isOddLevel;
        }

        return result;
    }

    /**
     * 中国象棋中的马是走“日”字，给定一个 n*m 的棋盘，问最少多少步可以走到给定的一个点，走不到时输出提示，要求算法尽可能的快速
     */

    /**
     * 有 n 堆石头，需要合并成一堆，每次合并要消耗两堆石头的重量总和的体力，问合并成一堆最少消费多少体力
     * 例如：1 2 3，这三堆石头最优解是先合并 1、2，花费 3，剩余两堆 3 3 合并花费 6，最后总花费 9*/
    public static int process(int[] arr) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(); // 构建小根堆
        for (int j : arr) {
            heap.add(j);
        }
        int sum = 0;
        while (heap.size() > 1) {
            int cur = heap.poll() + heap.poll(); // 每次取出两个最小的值合并
            sum += cur;
            heap.add(cur); // 合并完之后在丢到小根堆
        }

        return sum;
    }

    /**
     * 转驼峰命名
     */
    public String underscoreNamingToCamel(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        String[] nameSeg = name.split("_");
        StringBuilder sb = new StringBuilder();
        nameSeg[0] = nameSeg[0].toLowerCase();
        for (String seg : nameSeg) {
            if (seg.isEmpty()) {
                continue;
            }

            if (sb.length() == 0) { // first seg
                sb.append(seg.toLowerCase());
            } else {
                sb.append(seg.substring(0, 1).toUpperCase())
                        .append(seg.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

     /**
      * 线性结构转树
      */
     public String serialize(TreeNode root) {
         if (root == null) {
             return "null,";
         }
         return root.val + "," + serialize(root.left) + serialize(root.right);
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

    /**
     * 605. Can Place Flowers
     * 种花问题
     * You have a long flowerbed in which some plots are planted, and some are not.
     * However, flowers cannot be planted in adjacent plots.
     *
     * Given an integer array flowerbed containing 0's and 1's, where 0 means empty
     * and 1 means not empty, and an integer n, return if n new flowers can be planted
     * in the flowerbed without violating the no-adjacent-flowers rule.
     *
     * Time = O(n)
     */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int count = 0;
        for (int i = 0; i < flowerbed.length; i++) {
            if (isValid(i, flowerbed)) {
                flowerbed[i] = 1; // place the flower
                count++;
            }
            if (count >= n) {
                return true;
            }
        }

        return false;
    }

    private boolean isValid(int i, int[] flowerbed) {
        boolean isLeftValid = i == 0 || flowerbed[i - 1] == 0;
        boolean isRightValid = i == flowerbed.length - 1 || flowerbed[i + 1] == 0;
        return isLeftValid && isRightValid && flowerbed[i] == 0;
    }

    /**
     * 拓扑排序
     * Time = O(n + e)
     * Space = O(n)
     */
    public int[] topologicalSort(List<List<Integer>> graph) {
        int numCourses = graph.size();
        int[] topologicalOrder = new int[numCourses];
        int[] incomingEdges = new int[numCourses];
        for (int x = 0; x < numCourses; x++) {
            for (int y : graph.get(x)) {
                incomingEdges[y]++;
            }
        }

        // nodes with no incoming edges
        Queue<Integer> queue = new ArrayDeque<>();
        for (int x = 0; x < numCourses; x++) {
            if (incomingEdges[x] == 0) {
                queue.offer(x);
            }
        }

        int numExpanded = 0;
        while (!queue.isEmpty()) {
            int x = queue.poll();
            topologicalOrder[numExpanded++] = x;
            for (int y : graph.get(x)) {
                if (--incomingEdges[y] == 0) {
                    queue.offer(y);
                }
            }
        }

        return numExpanded == numCourses ? topologicalOrder : new int[]{};
    }

    /**
     * Reverse LinkedList between first and last node
     */
    public ListNode reverseBetweenFirstAndLast(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }

        ListNode newHead = head;
        ListNode reversedTail = head.next;

        ListNode prev = null;
        head = head.next;
        while (head != null) {
            ListNode nextNode = head.next;
            head.next = prev;
            prev = head;
            head = nextNode;
        }

        ListNode newTail = prev;
        newHead.next = prev.next;
        newTail.next = null;
        reversedTail.next = newTail;

        return newHead;
    }
}

