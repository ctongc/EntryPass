package com.ctong.entrypass.interviews;

import java.util.*;

public class Google {
    /**
     * Longest Line of Consecutive One in Matrix
     * Given a 01 matrix M, find the longest line of consecutive one in the matrix.
     * The line could be horizontal, vertical, diagonal or anti-diagonal.
     * e.g. Input: [[0,1,1,0],
     *              [0,1,1,0],
     *              [0,0,0,1]]
     * Output: 3
     * Time = O(n ^ 2)
     * Space = O(m * n * 4) // can optimized to O(1) by only stores the int[][] = [left[], ad[], up[], anti-ad[]]
     */
    public int longestLine(int[][] M) {
        // sanity check
        if (M == null || M.length == 0 || M[0] == null || M[0].length == 0) {
            return 0;
        }
        int row = M.length;
        int col = M[0].length;
        // lengthOfOnes stores the longest consecutive ones from left, upper diagonal, up, upper anti-diagonal
        int[][][] lengthOfOnes = new int[row][col][4]; // [left, diagonal, up, anti-diagonal]
        int max = 0;
        int[][] directions = new int[][]{{0, -1}, {-1, -1}, {-1, 0}, {-1, 1}}; // left, dia, up, anti-dia
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (M[i][j] == 1) {
                    // initialize lengthOfOnes[][][]
                    for (int k = 0; k < 4; k++) {
                        lengthOfOnes[i][j][k] = 1;
                    }
//                    // check left
//                    if(j - 1 >= 0) {
//                        lengthOfOnes[i][j][0] += lengthOfOnes[i][j - 1][0];
//                    }
//                    // check diagonal
//                    if(j - 1 >= 0 && i - 1 >= 0) {
//                        lengthOfOnes[i][j][1] += lengthOfOnes[i - 1][j - 1][1];
//                    }
//                    // check up
//                    if(i - 1 >= 0) {
//                        lengthOfOnes[i][j][2] += lengthOfOnes[i - 1][j][2];
//                    }
//                    // check anti-diagonal
//                    if(j + 1 < col && i - 1 >= 0) {
//                        lengthOfOnes[i][j][3] += lengthOfOnes[i - 1][j + 1][3];
//                    }
                    for (int d = 0; d < directions.length; d++) {
                        int newRow = i + directions[d][0];
                        int newCol = j + directions[d][1];
                        if(isValid(M, newRow, newCol)) {
                            lengthOfOnes[i][j][d] += lengthOfOnes[newRow][newCol][d];
                        }
                    }
                    // check if need update max
                    for (int k = 0; k < 4; k++) {
                        max = Math.max(lengthOfOnes[i][j][k], max);
                    }
                }
            }
        }
        return max;
    }

    private boolean isValid(int[][] M, int row, int col) {
        if (row < 0 || row >= M.length || col < 0 || col >= M[0].length) {
            return false;
        }
        return true;
    }
    /**
     * Image overlap
     * Two images A and B are given, represented as binary, square matrices of the same size.
     * We translate one image however we choose (sliding it left, right, up, or down any number of units),
     * and place it on top of the other image.  After, the overlap of this translation is the number of
     * positions that have a 1 in both images.
     * Input: A = [[1,1,0],
     *             [0,1,0],
     *             [0,1,0]]
     *        B = [[0,0,0],
     *             [0,1,1],
     *             [0,0,1]]
     * Output: 3
     * Time = O(n ^ 4)
     * Space = O(1)
     * */
    public int largestOverlap(int[][] A, int[][] B) {
        // assumption: 1 <= A.length = A[0].length = B.length = B[0].length <= 30
        // 2: A and B both contains only 0 and 1
        int max = 0;
        for (int i = - A.length; i < A.length; i++) {
            for (int j = - A[0].length; j < A[0].length; j++) {
                int overlap = findOverlap(i, j, A, B);
                max = Math.max(max, overlap);
            }
        }
        return max;
    }

    private int findOverlap(int rowOffset, int colOffset, int[][] A, int[][] B) {
        int overlap = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                if (i + rowOffset < 0 || i + rowOffset >= A.length
                        || j + colOffset < 0 || j + colOffset >= A[0].length) {
                    continue;
                }
                if (A[i][j] == 1 && B[i + rowOffset][j + colOffset] == 1) {
                    overlap++;
                }
            }
        }
        return overlap;
    }
    /**
     * delete node from doubly linked list, 要求从doubly linked list 里删除第一个match key的node
     * Time = O(n)
     * Space = O(1)
     */
    private static class ListNodeD {
        int val;
        ListNodeD prev;
        ListNodeD next;
        public ListNodeD(int val) {
            this.val = val;
        }
    }
    public ListNodeD deleteNode(ListNodeD head, int val) {
        if (head == null) {
            return head;
        } else if (head.val == val) {
            head = head.next;
            head.prev = null;
            return head;
        }
        ListNodeD current = head;
        while (current.next != null) {
            if (current.next.val == val) {
                current.next = current.next.next;
                if (current.next != null) { // if not tail
                    current.next.prev = current;
                }
                return head;
            }
            current = current.next;
        }
        return head;
    }

    /**
     * sort string based on order from second string
     * Time = O(S.length + T.length)
     * Space = O(T.length)
     */
    public String customSortString(String S, String T) {
        if (T == null || T.length() == 0 || S == null || S.length() == 0) {
            return T;
        }
        // posMap stores the position of a char occurs in S
        Map<Character, Integer> posMap = new HashMap<>();

        for (int i = 0; i < S.length(); i++) {
            posMap.put(S.charAt(i), i);
        }
        Character[] temp = new Character[T.length()];
        for (int i = 0; i < T.length(); i++) {
            temp[i] = T.charAt(i);
        }
        Arrays.sort(temp, Comparator.comparing((Character c) -> posMap.getOrDefault(c, posMap.size())));
        StringBuilder sb = new StringBuilder();
        for (Character c : temp) {
            sb.append(c);
        }
        return sb.toString();
    }

    // faster way if we don't care the order of char that not in S
    // count sort
    public String customSortString2(String S, String T) {
        int[] count = new int[26];
        for (char c : T.toCharArray()) { // count each char in T
            count[c - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        // append chars that in S with the order of S
        for (char c : S.toCharArray()) {
            while (count[c - 'a']-- > 0) {
                sb.append(c);
            }
        }
        // append chars that in T but not in S
        for (char c = 'a'; c <= 'z'; c++) {
            while (count[c - 'a']-- > 0) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 河里有石头 人可以踩石头跳过去 人可以选择跳一格 跳两个 或者不跳, check人能不能跳过去
     * 比如 o _ o _ _ o _, o是石头 _ 是河 然后check 这种不能跳过去
     * o _ o o _ 就可以
     */
    public boolean canCrossRiver(char[] river) {
        boolean[] canCross = new boolean[river.length + 1]; // padding the end river bank
        canCross[canCross.length - 1] = true;
        for (int i = river.length - 1; i >= 0; i--) {
            if (canCross[i + 1]) {
                if (river[i] == 'o') {
                    canCross[i] = true;
                } else {
                    if (i - 1 >= 0 && river[i - 1] == 'o') {
                        canCross[i] = true;
                    }
                }
            }
        }
        return canCross[0];
    }

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

    public boolean canJump(int[] nums) {
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
     * Frog jump
     * A frog is crossing a river. The river is divided into x units and at each unit there may or
     * may not exist a stone. The frog can jump on a stone, but it must not jump into the water.
     * Given a list of stones' positions (in units) in sorted ascending order, determine if
     * the frog is able to cross the river by landing on the last stone. Initially, the frog is on
     * the first stone and assume the first jump must be 1 unit.
     * If the frog's last jump was k units, then its next jump must be either k - 1, k, or k + 1 units.
     * Note that the frog can only jump in the forward direction.
     * Time = O(n ^ 2)
     * Space = O(n ^ 2)
     */

    public boolean canCross(int[] stones) {
        // assumptions:
        // stones.length >= 2 and < 1,100
        // each stone's position will be a non-negative integer < 2^31
        // first stone's position is always 0
        // the first jump must be 1 unit
        // Note that the frog doesn't need to jump to next stone and furthest might not be good also
        // 45  55  64  65  66  74
        //     10   9  --
        //     10      10       9
        //     10          11   --
        if(stones.length == 2 && stones[1] != 1) {
            return false;
        }
        HashMap<Integer, Set<Integer>> map = new HashMap<>();  // <stone, valid steps>
        for (int i = 0; i < stones.length; i++) {
            map.put(stones[i], new HashSet<>() );
        }
        map.get(0).add(1);
        for (int i = 0; i < stones.length - 1; i++) {
            int stone = stones[i];
            for (int step : map.get(stone)) { // for each valid step
                int reach = step + stone; // distance I could reach by take that step
                if (reach == stones[stones.length - 1]) { // reach the last
                    return true;
                }
                Set<Integer> set = map.get(reach);
                if (set != null) { // if I reach a valid stone, update the possible movements for that stone
                    if (step - 1 > 0) {
                        set.add(step - 1);
                    }
                    set.add(step);
                    set.add(step + 1);
                }
            }
        }
        return false;
    }

    /**
     * 给定一个树（不一定是binary tree), 树的节点的value为char类型，找出字母顺序最小的path（从一个叶子节点到root的路径）
     *
     * eg：  a
     *    / | \
     *   b  d  c
     *  / \    \
     * c  d    a
     *
     * 所有的path为cba, dba, da, aca，  字母顺序最小的为aca，返回aca。
     * 比如a < b， aa < b， 两个string谁的第一个字符小谁就小，如果相等再比较第二个，这样
     *
     * Time = O(m ^ height * height) // each node has average m children, compare needs O(height)
     * Space = O(height + n * height) // there are n nodes in the tree, each leaf nodes needs a O(height) for the list
     */
    private static class TreeNodeC {
        char key;
        List<TreeNodeC> children;
        public TreeNodeC(char c) {
            this.key = c;
            this.children = new ArrayList<>();
        }
    }

    public List<Character> findPath(TreeNodeC root) {
        // base case
        if (root.children == null || root.children.size() == 0) {
            List<Character> path = new ArrayList<>();
            path.add(root.key);
            return path;
        }
        List<Character> path = null;
        for (TreeNodeC n : root.children) {
            List<Character> prefix = findPath(n);
            prefix.add(root.key);
            if (path == null) {
                path = prefix;
            } else {
                path = getSmallerList(path, prefix);
            }
        }
        return path;
    }

    private List<Character> getSmallerList(List<Character> l1, List<Character> l2) {
        if (l1.size() > l2.size()) {
            List<Character> temp = l1;
            l1 = l2;
            l2 = temp;
        }
        for (int i = 0; i < l1.size(); i++) {
            if (l1.get(i) > l2.get(i)) {
                return l2;
            }
        }
        return l1;
    }

    /**
     * 给一个树，求从根节点到所有叶子节点的路径里最长路径的长度
     *     root
     *    /1 \2
     *   M1   M2
     *  /1 \2  \3
     * L1  L2  L3
     *
     * 一共有三条路径, 分别是
     * root -> M1 -> L1         1 + 1
     * root -> M1 -> L2         1 + 2
     * root -> M2 -> L3         2 + 3
     */
    private static class TreeNodeI {
        int key; // edge cost
        List<TreeNodeI> children;
        public TreeNodeI(int i) {
            this.key = i;
            this.children = new ArrayList<>();
        }
    }

    public int getMaxPathSum(TreeNodeI root) {
        if (root.children == null || root.children.size() == 0) {
            return root.key;
        }
        int longestPath = 0;
        for (TreeNodeI n : root.children) {
            int pathSum = getMaxPathSum(n);
            longestPath = Math.max(longestPath, pathSum);
        }
        return longestPath + root.key;
    }

    /**
     * 题目很简单，说有一堆forecast，每个forecast都可能依赖于另一些forecast。如果要update某个forecast, 需要将dependency也update
     * 给定一个forecast id，需要返回它所有需要update的Forecast id
     * 其实就是一个有向图，问你指向某个节点的所有节点。
     * 如果是某个节点指向的所有节点，那就可以直接用dfs了。最后用的笨方法，reconstruct这个graph，让子节点指向父节点，然后再做dfs。
     * 照例讲思路，说复杂度，然后就开写，边写边讲。写完之后跑两个测试用例。
     * follow up是问如果需要保证遍历的层级顺序怎么办，回答用bfs
     * 又问如果需要做并行优化怎么办，其实我也不知道，就说了个每次在某个节点遍历子节点的时候用新的线程，每个层级设置线程锁。
     * 最后让我问问题，我说好像可以不用reconstruct这个图，有没有什么别的做法，她说拓扑排序。
     */
    static class Forecast {
        int id;
        List<Forecast> children;
        Forecast(int i) {
            this.id = i;
            children = new ArrayList<>();
        }
    }
    public List<Integer> getDependency(List<Forecast> list, Forecast forecast) {
        Map<Integer, List<Integer>> map = new HashMap<>(); // <id, dependency>
        Set<Integer> visited = new HashSet<>();
        // for each forecast, get all his children, add him to each of his child's dependent list
        for (Forecast f : list) {
            if (visited.add(f.id)) {
                getChildren(f, map, visited);
            }
        }
        return map.get(forecast.id);
    }

    private void getChildren(Forecast f, Map<Integer, List<Integer>> map, Set<Integer> visited) {
        Queue<Forecast> queue = new ArrayDeque<>();
        queue.offer(f);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Forecast nextForecast = queue.poll();
                for (Forecast child : nextForecast.children) {
                    map.putIfAbsent(child.id, new ArrayList<>());
                    List<Integer> dependentList = map.get(child.id);
                    dependentList.add(nextForecast.id);
                    if (visited.add(child.id)) {
                        queue.offer(child);
                    }
                }
            }
        }
    }

    /**
     * 给一个string, 返回string里连续重复三遍及以上的character的起始和结束位置, 比如heeellooo返回[[1,3],[6,8]]
     * Time = O(n)
     * Space = O(n)
     */
    public List<List<Integer>> getCharactersPosition(String s) {
        List<List<Integer>> result = new ArrayList<>();
        if (s == null || s.length() < 3) {
            return null;
        }
        for (int i = 0; i < s.length() - 2; i++) {
            if (s.charAt(i) == s.charAt(i + 1) && s.charAt(i) == s.charAt(i + 2)) {
                System.out.println("["+ i + ", " + (i + 2) + "]");
                result.add(Arrays.asList(i, i + 2));
                i += 2;
            }
        }
        return result;
    }
    /**
     * 给一个string判断在不在dictionary里。string里连续重复三遍及以上的character可以删减。
     * 因为只有连续3个或以上才会算成是extended，所以找所有的extended substring然后去试只有1个或者2个的情况.
     * 比如helloooooo就只用试hello和helloo. 2^N这个N表示的是extended substring的个数
     * 是它自己内部有一个隐藏的dictionary，但是你只能通过他给的API来确定某个string在不在那个dictionary里。
     * 比如如果hello在那个dictionary里，那么对于你写的function来说，
     * heeelloooo和hellooo和helllo都应该返回true因为他们都是hello extend来的
     */

    /**
     * Range sum Query - using Binary index tree
     * Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.
     * The update(i, val) function modifies nums by updating the element at index i to val.
     *
     * e.g. Given nums = [1, 3, 5]
     * sumRange(0, 2) -> 9
     * update(1, 2)
     * sumRange(0, 2) -> 8
     * Time = O(n log n) for construct the BIT, O(log n) for update, O(log n) forgetSum
     * Space = O(n)
     */
    class NumArray {
        private int[] numsArray;
        private int[] biTree;
        private int size;
        public NumArray(int[] nums) {
            this.size = nums.length;
            this.numsArray = new int[size]; // need an new array from scratch
            this.biTree = new int[size + 1];
            // construct binary index tree
            for (int i = 0; i < size; i++) {
                update(i, nums[i]);
            }
        }

        public void update(int i, int val) {
            int diff = val - numsArray[i];
            numsArray[i] = val;
            int index = i + 1;
            while (index <= size) {
                biTree[index] += diff;
                index += - index & index;
            }
        }

        public int sumRange(int i, int j) {
            return getSum(j) - getSum(i - 1);
        }

        private int getSum(int i) { // get sum nums[0...i]
            int sum = 0;
            int index = i + 1;
            while (index > 0) {
                sum += biTree[index];
                index -= - index & index;
            }
            return sum;
        }
    }

    public List<List<Integer>> palindromePairs(String[] words) {
        if (words == null || words.length < 2) {
            return null;
        }
        List<List<Integer>> result = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i ++) {
            map.put(words[i], i);
        }
        for (int i = 0; i < words.length - 1; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                String s1 = words[i].substring(0, j);
                String s2 = words[i].substring(j);
                if (isPalindrome(s1)) {
                    System.out.println(s1);
                    String rvss2 = new StringBuilder(s2).reverse().toString();
                    Integer index = map.get(rvss2);
                    if (index != null && index != i) {
                        result.add(Arrays.asList(index, i));
                    }
                }
                if (isPalindrome(s2)) {
                    System.out.println(s2);
                    String rvss1 = new StringBuilder(s1).reverse().toString();
                    Integer index = map.get(rvss1);
                    if (index != null && index != i) {
                        result.add(Arrays.asList(i, index));
                    }
                }
            }
        }
        return result;
    }

    private boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i++) != s.charAt(j--)) {
                return false;
            }
        }
        return true;
    }

    static class TreeNodeS {
        String key;
        TreeNodeS left;
        TreeNodeS right;
        TreeNodeS(String val) {
            this.key = val;
        }
    }

    public String printTree(TreeNodeS root) {
         // base case
         if (root == null) {
             return "";
         }
         StringBuilder sb = new StringBuilder();
         inOrderTraverse(root, sb);
         return new String(sb);
    }

    private void inOrderTraverse(TreeNodeS root, StringBuilder sb) {
        if (root.right == null && root.left == null) {
            sb.append(root.key);
            return;
        }
        // decide add "("
        sb.append("(");
        inOrderTraverse(root.left, sb);
        sb.append(root.key);
        // decide add ")"
        inOrderTraverse(root.right, sb);
        sb.append(")");
    }

    public static void main(String[] args) {
        Google google = new Google();
        int[][] M = {{1}};
        System.out.println(M == null);
        System.out.println(M.length);
        System.out.println(M[0] == null);
        System.out.println(M[0].length);
        String s1 = google.customSortString("zxcvb","zyxwvutsrqponmlkjihgfedcba");
        String s2 = google.customSortString2("zxcvb","zyxwvutsrqponmlkjihgfedcba");
        System.out.println(s1 + ", " + s2);
        System.out.println(google.canCrossRiver(new char[]{'o', 'o', '_', 'o', '_','o'}));
        System.out.println(google.canCross(new int[]{0,1,3,4,5,7,9,10,12}));

        TreeNodeC n1 = new TreeNodeC('a');
        TreeNodeC n2 = new TreeNodeC('b');
        TreeNodeC n3 = new TreeNodeC('d');
        TreeNodeC n4 = new TreeNodeC('c');
        TreeNodeC n5 = new TreeNodeC('c');
        TreeNodeC n6 = new TreeNodeC('d');
        TreeNodeC n7 = new TreeNodeC('a');
        n1.children.add(n2);
        n1.children.add(n3);
        n1.children.add(n4);
        n2.children.add(n5);
        n2.children.add(n6);
        n4.children.add(n7);
        List<Character> list = google.findPath(n1);
        for (char c : list) {
            System.out.println(c);
        }
        ListNodeD m1 = new ListNodeD(1);
        ListNodeD m2 = new ListNodeD(2);
        ListNodeD m3 = new ListNodeD(3);
        ListNodeD m4 = new ListNodeD(4);
        m1.next = m2;
        m2.prev = m1;
        m2.next = m3;
        m3.prev = m2;
        m3.next = m4;
        m4.prev = m3;
        ListNodeD cur = m1;
        while(cur != null) {
            System.out.print(cur.val);
            cur = cur.next;
        }
        System.out.println();
        cur = m4;
        while(cur != null) {
            System.out.print(cur.val);
            cur = cur.prev;
        }
        System.out.println();
        m1 = google.deleteNode(m1, 1);
        cur = m1;
        while(cur != null) {
            System.out.print(cur.val);
            cur = cur.next;
        }
        System.out.println();
        cur = m1;
        while(cur.next != null) {
            cur = cur.next;
        }
        while(cur != null) {
            System.out.print(cur.val);
            cur = cur.prev;
        }
        System.out.println();

        /**
         *     root
         *    /1 \2
         *   M1   M2
         *  /1 \2  \3
         * L1  L2  L3
         */
        TreeNodeI tn1 = new TreeNodeI(0);
        TreeNodeI tn2 = new TreeNodeI(1);
        TreeNodeI tn3 = new TreeNodeI(2);
        TreeNodeI tn4 = new TreeNodeI(1);
        TreeNodeI tn5 = new TreeNodeI(2);
        TreeNodeI tn6 = new TreeNodeI(3);
        tn1.children.add(tn2);
        tn1.children.add(tn3);
        tn2.children.add(tn4);
        tn2.children.add(tn5);
        tn3.children.add(tn6);
        System.out.println(google.getMaxPathSum(tn1));

        Forecast f1 = new Forecast(1);
        Forecast f2 = new Forecast(2);
        Forecast f3 = new Forecast(3);
        Forecast f4 = new Forecast(4);
        Forecast f5 = new Forecast(5);
        Forecast f6 = new Forecast(6);
        f1.children.add(f2);
        f1.children.add(f4);
        f2.children.add(f5);
        f3.children.add(f1);
        f3.children.add(f6);
        f4.children.add(f6);
        f6.children.add(f5);
        List<Forecast> fList = Arrays.asList(f1,f2,f3,f4,f5,f6);
        List<Integer> fresult = google.getDependency(fList, f6);
        System.out.println("??");
        for (int i : fresult) {
            System.out.println(i);
        }

        google.getCharactersPosition("heeellooo");
        String[] words = new String[]{"abcd","dcba","lls","s","sssll"};
        google.palindromePairs(words);

        TreeNodeS sn1 = new TreeNodeS("*");
        TreeNodeS sn2 = new TreeNodeS("+");
        TreeNodeS sn3 = new TreeNodeS("2");
        TreeNodeS sn4 = new TreeNodeS("15");
        TreeNodeS sn5 = new TreeNodeS("3");
        sn1.left = sn2;
        sn1.right = sn3;
        sn2.left = sn4;
        sn2.right = sn5;
        System.out.println(google.printTree(sn1));
        google.compareVersion("1.01", "1.1");
    }

    public int compareVersion(String version1, String version2) {
        if (version1.length() == 0 && version2.length() == 0) {
            return 0;
        } else if (version1.length() == 0) {
            return -1;
        } else if (version2.length() == 0) {
            return 1;
        }
        int i = 0;
        int j = 0;
        while (i < version1.length() && j < version2.length()) {
            int s1 = 0;
            int s2 = 0;
            while(i < version1.length() && version1.charAt(i) != '.') {
                s1 += (version1.charAt(i++) - '0');
            }
            while(j < version2.length() && version2.charAt(j) != '.') {
                s2 += (version2.charAt(j++) - '0');
            }
            if (s1 < s2) {
                return -1;
            } else if (s1 > s2) {
                return 1;
            }
            i++;
            j++;
        }
        if (i == version1.length() + 1 && j == version2.length() + 1) {
            return 0;
        } else if (i == version1.length() + 1) {
            return -1;
        } else {
            return 1;
        }
    }
}
