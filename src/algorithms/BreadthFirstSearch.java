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

public class BreadthFirstSearch {
    /**
     * Get Keys In Binary Tree Layer by Layer
     * Get the list of list of keys in a given binary tree layer by layer.
     * Each layer is represented by a list of keys and the keys are traversed from left to right.
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
            List<Integer> curLevel = new ArrayList<>(); // stores the nodes in curren level
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
     * Determine if an undirected graph is bipartite.
     * A bipartite graph is one in which the nodes can be divided into two groups
     * such that no nodes have direct edges to nodes in the same group.
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
         * since its a graph and could have separate nodes */
        for (GraphNode node : graph) {
            if (!checkGroup(node, nodeGroup)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkGroup(GraphNode node, HashMap<GraphNode, Integer> nodeGroup) {
        /* this if block is really important
         * eg. n1 -> n2 -> n3
         * after run BFS on n1 you will mark n1 as group0, n2 as group1, n3 as group0
         * but when you run BFS on n2 (since you need to run DFS for EVERY NODE)
         * n2's start group mark will be 0, so no deed to do BFS again */
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
     * Check if a given binary tree is completed.
     * A complete binary tree is one in which every level of the binary tree is completely filled
     * except possibly the last level. Furthermore, all nodes are as far left as possible.
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
            // apply to left child
            if (cur.left == null) {
                flag = true; // see a null, don't need to add to queue anymore
            } else if (flag) {
                return false;
            } else {
                queue.offer(cur.left);
            }
            // apply to right child
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
     * Given a matrix of size N x M. For each row the elements are sorted in ascending order,
     * and for each column the elements are also sorted in ascending order. Find the Kth smallest number in it.
     *
     * Time = O(k log k) // for k element we doing log 3k
     * Space = O(m*n) // for the visited[][]
     */
    public int kthSmallest(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0
                || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int rows = matrix.length;
        int columns = matrix[0].length;

        PriorityQueue<Cell> minHeap = new PriorityQueue<>(k, Comparator.comparingInt(Cell -> Cell.value));
        // all the generated cells will be marked true,
        // to avoid being generated more than once
        boolean[][] visited = new boolean[rows][columns];
        minHeap.offer(new Cell(0, 0, matrix[0][0])); // first element
        visited[0][0] = true;
        // do bfs2 for the smallest k - 1 cells
        for (int i = 0; i < k - 1; i++) {
            // expand node[i][j]         -poll O(log n)
            Cell cur = minHeap.poll();
            // 	1 generate [i + 1][j]    -offer O(log n)
            // 	2 generate [i][j + 1]    -offer O(log n)
            if (cur.row + 1 < rows && !visited[cur.row + 1][cur.column]) {
                minHeap.offer(new Cell(cur.row + 1, cur.column, matrix[cur.row + 1][cur.column]));
                visited[cur.row + 1][cur.column] = true;
            }
            if (cur.column + 1 < columns && !visited[cur.row][cur.column + 1]) {
                minHeap.offer(new Cell(cur.row, cur.column + 1, matrix[cur.row][cur.column + 1]));
                visited[cur.row][cur.column + 1] = true;
            }
        }
        return minHeap.peek().value;
    }

    static class Cell {
        int row;
        int column;
        int value;

        Cell(int row, int col, int val) {
            this.row = row;
            this.column = col;
            this.value = val;
        }
    }

    public static void main(String[] args) {
        BreadthFirstSearch bfs = new BreadthFirstSearch();

        //[1, 2, 3, #, #, 4]
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
    }
}
