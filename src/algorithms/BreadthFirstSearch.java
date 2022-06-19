package algorithms;

import java.util.*;

class GraphNode {

    public int key;
    public List<GraphNode> neighbors;

    public GraphNode(int key) {
        this.key = key;
        this.neighbors = new ArrayList<>();
    }
}

class KthSmallestCell {

    int row;
    int column;
    int value;

    KthSmallestCell(int row, int col, int val) {
        this.row = row;
        this.column = col;
        this.value = val;
    }
}

class ProductStringPair implements Comparable<ProductStringPair> {

    final String s1;
    final String s2;
    final int product;

    public ProductStringPair(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
        this.product = s1.length() * s2.length();
    }

    @Override
    public int compareTo(ProductStringPair ps) {
        if (this.product == ps.product) {
            return 0;
        }
        return this.product > ps.product? -1 : 1;
    }
}

public class BreadthFirstSearch {
    /**
     * Get Keys In Binary Tree Layer by Layer
     * Get the list of keys in a given binary tree layer by layer. Each layer is
     * represented by a list of keys and the keys are traversed from left to right.
     *
     * Time = O(n)
     * Space = O(n)
     */
    public List<List<Integer>> printBinaryTreeLevelByLevel(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> curLevel = new ArrayList<>(); // stores the nodes in current level
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                // expanded
                TreeNode cur = queue.poll();
                curLevel.add(cur.key);
                // generate
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
            result.add(curLevel);
        }

        return result;
    }

    /**
     * Determine if an undirected graph is bipartite
     * A bipartite graph is one in which the nodes can be divided into two groups
     * such that no nodes have direct edges to nodes in the same group.
     *
     * Time = O(n + e) // since run DFS for each node + check each edge once
     * Space = O(n)
     */
    public boolean isBipartite(List<GraphNode> graph) {
        if (graph == null || graph.size() <= 2) {
            return true;
        }

        /* use 0 and 1 to denote two groups
         * the map contains the information of which group a node belongs to */
        HashMap<GraphNode, Integer> nodeGroup = new HashMap<>();
        /* Notice that you need to run DFS for EVERY NODE
         * since it's a graph and could have separate nodes */
        for (GraphNode node : graph) {
            if (!checkGroup(node, nodeGroup)) {
                return false;
            }
        }

        return true;
    }

    private boolean checkGroup(GraphNode node, HashMap<GraphNode, Integer> nodeGroup) {
        /* This if block is really important
         * e.g. n1 -> n2 -> n3
         * After run BFS on n1 you will mark n1 as group0, n2 as group1, n3 as group0
         * but when you run BFS on n2 (since you need to run DFS for EVERY NODE)
         * n2's start group mark will be 0, so no need to do BFS again */
        if (nodeGroup.containsKey(node)) {
            return true;
        }

        // BFS
        Queue<GraphNode> queue = new LinkedList<>();
        queue.offer(node);
        int groupNumber = 0;
        while (!queue.isEmpty()) {
            int size = queue.size(); // size = # number of nodes in the cur layer
            for (int i = 0; i < size; i++) {
                // expand
                GraphNode cur = queue.poll();
                if (!nodeGroup.containsKey(cur)) {
                    nodeGroup.put(cur, groupNumber);
                    // generate
                    for (GraphNode n : cur.neighbors) {
                        queue.offer(n);
                    }
                } else {
                    if (nodeGroup.get(cur) != groupNumber) {
                        return false;
                    }
                }
            }
            groupNumber = groupNumber == 0 ? 1 : 0;
        }

        return true;
    }

    /**
     * Check if a given binary tree is completed
     * A complete binary tree is one in which every level of the binary tree is
     * completely filled except possibly the last level. Furthermore, all nodes
     * are as far left as possible.
     *
     * Time = O(n)
     * Space = O(n)
     */
    public boolean isCompletedBinaryTree(TreeNode root) {
        if (root == null) {
            return true;
        }

        boolean flag = false; // records if any null has been generated
        // BFS
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            // generated, cur can't be null
            TreeNode cur = queue.poll();
            // if any of the child is not present, set the flag to true
            // apply to left child
            if (cur.left == null) {
                flag = true; // see a null, don't need to add to queue anymore
            } else if (flag) {
                // if flag is set but still see cur has a left child
                // the binary tree is not a completed one
                return false;
            } else {
                // if flag is not set and left child is present
                queue.offer(cur.left);
            }
            // apply to right child with same logic
            if (cur.right == null) {
                flag = true;
            } else if (flag) {
                return false;
            } else {
                queue.offer(cur.right);
            }
        }

        return true;
    }

    /**
     * Given a matrix of size N x M. For each row the elements are sorted in ascending
     * order, and for each column the elements are also sorted in ascending order.
     * Find the kth-smallest number in it.
     * Assume: k << n^2
     *
     * Time = O(klogk) // for k elements we are doing log2k
     * Space = O(k + m*n) // for the visited[][], can be optimized to O(k) if using hash table
     */
    public int kthSmallest(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }

        int rows = matrix.length;
        int columns = matrix[0].length;
        PriorityQueue<KthSmallestCell> minHeap = new PriorityQueue<>(k, Comparator.comparingInt(KthSmallestCell -> KthSmallestCell.value));
        /* All the generated cells will be marked true to avoid being generated more than once. */
        boolean[][] visited = new boolean[rows][columns];
        minHeap.offer(new KthSmallestCell(0, 0, matrix[0][0])); // first element
        visited[0][0] = true;
        // do bfs2 for the smallest k - 1 cells
        for (int i = 0; i < k - 1; i++) {
            // expand node[i][j]         -poll O(log n)
            KthSmallestCell cur = minHeap.poll();
            // 	1. generate [i + 1][j]    -offer O(log n)
            // 	2. generate [i][j + 1]    -offer O(log n)
            if (cur.row + 1 < rows && !visited[cur.row + 1][cur.column]) {
                minHeap.offer(new KthSmallestCell(cur.row + 1, cur.column, matrix[cur.row + 1][cur.column]));
                visited[cur.row + 1][cur.column] = true;
            }
            if (cur.column + 1 < columns && !visited[cur.row][cur.column + 1]) {
                minHeap.offer(new KthSmallestCell(cur.row, cur.column + 1, matrix[cur.row][cur.column + 1]));
                visited[cur.row][cur.column + 1] = true;
            }
        }

        return minHeap.peek().value;
    }

    /**
     * Word Ladder I
     * Given a beginWord, an endWord and a dictionary, find the least number transformations
     * from beginWord to endWord, and return the length of the transformation sequence
     * Return 0 if there is no such transformations.
     *
     * In each transformation, you can only change one letter, and the word should still
     * in the dictionary after each transformation.
     *
     * Assumptions
     * 1. All words have the same length.
     * 2. All words contain only lowercase alphabetic characters.
     * 3. There is no duplicates in the word list.
     * 4. The beginWord and endWord are non-empty and are not the same.
     *
     * e.g. beginWord = "git", endWord = "hot", dictionary = {"git","hit","hog","hot"}
     * Output: 3, git -> hit -> hot
     *
     * Time = O(26 * n * k) // k is the length of each word
     * Space = O(26 * k * n)
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (wordList == null || wordList.isEmpty()) {
            return 0;
        }

        Set<String> set = new HashSet<>(wordList); // dedup: remove from set once seen a word
        if (!set.contains(beginWord) || !set.contains(endWord)) {
            return 0;
        }

        // BFS-1
        Queue<String> qualifiers = new ArrayDeque<>();
        qualifiers.offer(beginWord);
        set.remove(beginWord);
        int stepCount = 1;
        while (!qualifiers.isEmpty()) {
            int size = qualifiers.size();
            stepCount++;
            for (int j = 0; j < size; j++) {
                String word = qualifiers.poll();
                StringBuilder sb = new StringBuilder(word);
                for (int i = 0; i < word.length(); i++) {
                    char cur = word.charAt(i);
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == cur) {
                            continue;
                        }
                        sb.setCharAt(i, c);
                        String temp = sb.toString();
                        if (temp.equals(endWord)) {
                            return stepCount;
                        }
                        if (set.contains(temp)) {
                            qualifiers.offer(temp);
                            set.remove(temp);
                        }
                    }
                    sb.setCharAt(i, cur);
                }
            }
        }
        return 0;
    }

    /**
     * Largest product of two Strings with unique characters
     * Given a dictionary containing many words, find the largest product of two words’ lengths
     * such that the two words do not share any common characters
     *
     * e.g. dictionary = [“abcde”, “abcd”, “ade”, “xy”]
     * the largest product is 5 * 2 = 10 (by choosing “abcde” and “xy”)
     *
     * Time = O((1+n)*n/2*(logn) + n^2 * (log(n) + m))
     *      = O(n^2 * (log(n) + m)) // m is average length of a word
     * Space = O(n^2) // worst case for heap
     */
    public int largestProductOfTwoStrings(String[] dict) {
        // assumptions:
        // 1. the words only contains characters of 'a' to 'z'
        // 2. the dictionary is not null and does not contain null string, and has at least two strings
        // 3. if there is no such pair of words, just return 0

        // pre-processing
        Arrays.sort(dict, Collections.reverseOrder());
        PriorityQueue<ProductStringPair> pq = new PriorityQueue<>();

        for (int i = 0; i < dict.length - 1; i++) {
            for (int j = i + 1; j < dict.length; j++) {
                pq.offer(new ProductStringPair(dict[i], dict[j]));
            }
        }

        while (!pq.isEmpty()) {
            ProductStringPair ps = pq.poll();
            if(!containsDupChar(ps)) {
                return ps.product;
            }
        }

        return 0;
    }

    private boolean containsDupChar(ProductStringPair ps) {
        Set<Character> set = new HashSet<>();
        for (char c : ps.s1.toCharArray()) {
            set.add(c);
        }
        for (char c : ps.s2.toCharArray()) {
            if(set.contains(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * optimizing via bit mask to dedup
     * iterate from the largest product pairs, check each pair if valid using bit mask
     */
    public int largestProduct2(String[] dict) {
        HashMap<String, Integer> bitMasks = getBitMasks(dict);
        // sort the dict by length of the words in descending order
        Arrays.sort(dict, Comparator.reverseOrder());
        int largest = 0;
        // note the order of constructing all the pairs
        // we make our best to try the largest product
        for (int i = 1; i < dict.length; i++) {
            for (int j = 0; j < i; j++) {
                // early break if the product is already smaller than
                // the current largest one
                int prod = dict[i].length() * dict[j].length();
                if (prod <= largest) {
                    break;
                }
                int iMask = bitMasks.get(dict[i]);
                int jMask = bitMasks.get(dict[i]);
                // if two words do not share any common characters,
                // the bit masks "and" result should be 0 since
                // there is not any position such that in the two bit masks
                // they are all 1
                if ((iMask & jMask) == 0) {
                    largest = prod;
                }
            }
        }
        return largest;
    }

    private HashMap<String, Integer> getBitMasks(String[] dict) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String s : dict) {
            int bitMast = 0;
            for (int i = 0; i < s.length(); i++) {
                // the 26 characters 'a' - 'z' are mapped to 0 - 25th bit
                // to determine which bit it is for a character x
                // use (x - 'a') since their values are in a consecutive range
                // if character x exists in the word, we set the bit at
                // corresponding index to 1
                bitMast |= 1 << (s.charAt(i) - 'a');
            }
            map.put(s, bitMast);
        }
        return map;
    }

    /**
     * Kth Smallest With Only 3, 5, 7 As Factors
     * Find the Kth-smallest number s such that s = 3 ^ x * 5 ^ y * 7 ^ z
     * x > 0 and y > 0 and z > 0, x, y, z are all integers
     *
     * e.g.
     * the smallest is 3 * 5 * 7 = 105
     * the 2nd smallest is 3 ^ 2 * 5 * 7 = 315
     * the 3rd smallest is 3 * 5 ^ 2 * 7 = 525
     * the 5th smallest is 3 ^ 3 * 5 * 7 = 945
     *
     * Time = O(klogk)
     * space = O(k)
     */
    public long kthSmallest357(int k) {
        // assume k >= 1, and x, y, z > 0
        // use minHeap with poll not maxHeap with peek since there are 3 elements added each time
        PriorityQueue<Long> minHeap = new PriorityQueue<>(k);
        Set<Long> visited = new HashSet<>();
        long firstEle = 3 * 5 * 7L;
        minHeap.offer(firstEle);
        visited.add(firstEle);
        long cur = 0;
        for (int i = 0; i < k; i++) {
            cur = minHeap.poll();
            // for state<x+1, y, z>, the actual value is cur * 3
            if (visited.add(cur * 3)) {
                minHeap.offer(cur * 3);
            }
            // for state<x, y+1, z>, the actual value is cur * 5
            if (visited.add(cur * 5)) {
                minHeap.offer(cur * 5);
            }
            // for state<x, y, z+1>, the actual value is cur * 7
            if (visited.add(cur * 7)) {
                minHeap.offer(cur * 7);
            }
        }
        // cur is the k-th element been polled from pq
        return cur;
    }

    /**
     * Kth-Closest Point To <0,0,0>
     * Given three arrays sorted in ascending order. Pull one number from each array
     * to form a coordinate <x,y,z> in a 3D space. Find the coordinates of the points
     * that is k-th closest to <0,0,0>. We are using euclidean distance here.
     *
     * Assumptions
     * The three given arrays are not null or empty, containing only non-negative numbers
     * K >= 1 and K <= a.length * b.length * c.length
     *
     * Return
     * a size 3 integer list, the first element should be from the first array, the second
     * element should be from the second array and the third should be from the third array
     *
     * e.g. A = {1, 3, 5}, B = {2, 4}, C = {3, 6}
     * The closest is <1, 2, 3>, distance is sqrt(1 + 4 + 9)
     * The 2nd closest is <3, 2, 3>, distance is sqrt(9 + 4 + 9)
     *
     * Time = O(klogk)
     * Space = (k)
     */
    public List<Integer> kthClosestPointToOrigin(int[] a, int[] b, int[] c, int k) {
        PriorityQueue<List<Integer>> closestPoint = new PriorityQueue<>(
                Comparator.comparing(list -> Math.sqrt(a[list.get(0)] * a[list.get(0)]
                                                        + b[list.get(1)] * b[list.get(1)]
                                                        + c[list.get(2)] * c[list.get(2)])));
        // use index not actual element to dedup, since {1,2} and {2,1} are same list but different point
        Set<List<Integer>> visited = new HashSet<>();
        List<Integer> cur = Arrays.asList(0, 0, 0);
        closestPoint.offer(cur);
        visited.add(cur);
        for (int i = 0; i < k; i++) {
            cur = closestPoint.poll();
            List<Integer> pointNewI = Arrays.asList(cur.get(0) + 1, cur.get(1), cur.get(2));
            if (pointNewI.get(0) < a.length && visited.add(pointNewI)) {
                closestPoint.offer(pointNewI);
            }
            List<Integer> pointNewJ = Arrays.asList(cur.get(0), cur.get(1) + 1, cur.get(2));
            if (pointNewJ.get(1) < b.length && visited.add(pointNewJ)) {
                closestPoint.offer(pointNewJ);
            }
            List<Integer> pointNewP = Arrays.asList(cur.get(0), cur.get(1), cur.get(2) + 1);
            if (pointNewP.get(2) < c.length && visited.add(pointNewP)) {
                closestPoint.offer(pointNewP);
            }
        }
        // cur is the k-th element been polled from pq
        return Arrays.asList(a[cur.get(0)], b[cur.get(1)], c[cur.get(2)]);
    }

    /**
     * Course Schedule
     * There are a total of n courses you have to take, labeled from 0 to n - 1. Some
     * courses may have prerequisites, for example to take course 0 you have to first
     * take course 1, which is expressed as a pair: [0,1]. Given the total number of
     * courses and a list of prerequisite pairs, return the ordering of courses you
     * should take to finish all courses.
     * There may be multiple correct orders, you just need to return one of them.
     * If it is impossible to finish all courses, return an empty array.
     *
     * Time = O(v + e)
     * Space = (v)
     */
    public int[] findCourseSchedulingOrder(int numCourses, int[][] prerequisites) {
        // modeling the graph from Edge lists to Adjacency lists
        List<List<Integer>> courseGraph = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            courseGraph.add(new ArrayList<>());
        }
        for (int[] preReq : prerequisites) {
            /* 构建正向图: the i-th course is prerequisite to courses preReq
             *                          course  0     1    2    3
             * [[1,0],[2,0],[3,1],[3,2]] ->  [[1,2], [3], [3], [ ]] */
            courseGraph.get(preReq[1]).add(preReq[0]);
        }

        return topologicalSort(courseGraph);
    }

    private int[] topologicalSort(List<List<Integer>> courseGraph) {
        int numCourses = courseGraph.size();
        int[] numPreReq = new int[numCourses]; // course i's number of prerequisites = numPostReq[i]
        for (List<Integer> postCourses : courseGraph) {
            for (int course : postCourses) {
                numPreReq[course]++; // course 0 -> {1, 2}, numPostReq[1] = 1, numPostReq[2] = 1
            }
        }

        // starting from courses with no incoming edges
        Queue<Integer> queue = new ArrayDeque<>();
        for (int course = 0; course < numCourses; course++) {
            if (numPreReq[course] == 0) {
                queue.offer(course);
            }
        }

        int[] topologicalOrder = new int[numCourses];
        int topoStep = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            topologicalOrder[topoStep++] = course;
            for (int postCourse : courseGraph.get(course)) {
                if (--numPreReq[postCourse] == 0) {
                    queue.offer(postCourse);
                }
            }
        }

        return topoStep == numCourses ? topologicalOrder : new int[]{};
    }

    public static void main(String[] args) {
        final BreadthFirstSearch bfs = new BreadthFirstSearch();

        // [1, 2, 3, #, #, 4]
        TreeNode t1 = new TreeNode(1);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(3);
        TreeNode t4 = new TreeNode(4);
        t1.left = t2;
        t1.right = t3;
        t3.left = t4;
        bfs.printBinaryTreeLevelByLevel(t1);

        // 0->1; 1->0,2; 2->1,3; 3->2
        GraphNode n0 = new GraphNode(0);
        GraphNode n1 = new GraphNode(1);
        GraphNode n2 = new GraphNode(2);
        GraphNode n3 = new GraphNode(3);
        n0.neighbors.add(n1);
        n1.neighbors.add(n0);
        n1.neighbors.add(n2);
        n2.neighbors.add(n1);
        n2.neighbors.add(n3);
        n3.neighbors.add(n2);

        List<GraphNode> graph = new LinkedList<>();
        graph.add(n0);
        graph.add(n1);
        graph.add(n2);
        graph.add(n3);

        if (bfs.isBipartite(graph)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        System.out.println(bfs.ladderLength("ymain", "oecij",
                List.of("ymain", "ymann", "ymanj", "ymcnj", "yzcnj", "yycrj", "oecij", "yzcrj", "yycij", "xecij", "yecij")));

        System.out.println(bfs.largestProductOfTwoStrings(new String[]{"abcdefhi","ix","hj","x"}));

        System.out.println(bfs.kthSmallest357(40));

        System.out.println(bfs.kthClosestPointToOrigin(new int[]{1,2,3},new int[]{2,4},new int[]{1,2},10));

        int[][] prerequisites = new int[][]{{1,0}, {2,0}, {3,1}, {3,2}};
        System.out.println(Arrays.toString(bfs.findCourseSchedulingOrder(4, prerequisites)));
    }
}
