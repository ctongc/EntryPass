package interviews;

import java.util.*;

public class ByteDance {
    public void heapSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int n = nums.length;

        // 对array的前一半，从后往前建堆
        // 从底向上来完成，因为要保证heapify()每一个节点的时候，它的左右子树都已经是个堆了
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            // call max heapify on the reduced heap
            heapify(nums, i, 0);
        }
    }

    /** To heapify a subtree rooted with node i which is
     * an index in arr[]. n is size of heap
     */
    private void heapify(int[] arr, int n, int i) {
        int largest = i;  // Initialize largest as root
        int l = 2 * i + 1;  // left = 2 * i + 1
        int r = 2 * i + 2;  // right = 2 * i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    /**
     * 两个Thread交替打印
     */
    static int counter = 1;
    static int N = 10;

    private synchronized void printEvenNumber() throws InterruptedException {
        while (counter < N) {
            while (counter % 2 == 1) {
                wait();
            }
            System.out.print(counter++ + " ");
            notifyAll();
        }
    }

    private synchronized void printOddNumber() throws InterruptedException {
        while (counter < N) {
            while (counter % 2 == 0) {
                wait();
            }
            System.out.print(counter++ + " ");
            notifyAll();
        }
    }

    public void printOneByOne() {
        Thread t1 = new Thread(() -> {
            try {
                printOddNumber();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                printEvenNumber();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }

    /**
     * Deep clone n-nary tree
     */
    static class Node {
        public int val;
        public List<Node> children;


        public Node() {
            children = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            children = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * Time = O(n)
     * Space = O(n)
     */
    public Node cloneTreeBfs(Node root) {
        if (root == null) {
            return null;
        }

        Queue<Node[]> queue = new ArrayDeque<>();
        Node copyHead = new Node(root.val);
        Node[] nodePair = new Node[]{root, copyHead}; // <originalNode, copyNode>
        queue.offer(nodePair);
        while (!queue.isEmpty()) {
            Node[] cur = queue.poll();
            Node original = cur[0];
            Node copy = cur[1];

            List<Node> children = original.children;
            for (Node child : children) {
                Node copyChild = new Node(child.val);
                copy.children.add(copyChild);
                queue.offer(new Node[]{child, copyChild});
            }
        }

        return copyHead;
    }

    /**
     * Time = O(n)
     * Space = O(n)
     */
    public Node cloneTreeDfs(Node root) {
        // Base case: empty node.
        if (root == null) {
            return root;
        }

        // First, copy the node itself.
        Node nodeCopy = new Node(root.val);

        // Then, recursively clone the sub-trees.
        for (Node child : root.children) {
            nodeCopy.children.add(cloneTreeDfs(child));
        }

        return nodeCopy;
    }

    /**
     * Deep clone undirectedGraph
     */
    static class GraphNode {
        public int val;
        public List<GraphNode> neighbors;
        public GraphNode() {
            val = 0;
            neighbors = new ArrayList<GraphNode>();
        }
        public GraphNode(int _val) {
            val = _val;
            neighbors = new ArrayList<GraphNode>();
        }
        public GraphNode(int _val, ArrayList<GraphNode> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    public GraphNode cloneGraphBfs(GraphNode node) {
        if (node == null) {
            return node;
        }

        Map<GraphNode, GraphNode> lookup = new HashMap<>(); // <originalNode, copyNode>
        Queue<GraphNode> queue = new ArrayDeque<>(); // stores original nodes
        GraphNode copyHead = new GraphNode(node.val);
        lookup.put(node, copyHead);
        queue.offer(node);
        while (!queue.isEmpty()) {
            GraphNode cur = queue.poll();
            GraphNode copy = lookup.get(cur);
            for (GraphNode nei : cur.neighbors) {
                GraphNode neiCopy = lookup.get(nei);
                if (neiCopy == null) {
                    neiCopy = new GraphNode(nei.val);
                    lookup.put(nei, neiCopy);
                    queue.offer(nei);
                }
                copy.neighbors.add(neiCopy);
            }
        }

        return copyHead;
    }

    public GraphNode cloneGraphDfs(GraphNode node) {
        if (node == null) {
            return null;
        }

        Map<GraphNode, GraphNode> lookup = new HashMap<>();

        return dfs(node, lookup);
    }

    private GraphNode dfs(GraphNode node, Map<GraphNode, GraphNode> lookup) {
        if (node == null) {
            return null;
        }
        GraphNode nodeCopy = lookup.get(node);
        if (nodeCopy != null) {
            return nodeCopy;
        }

        nodeCopy = new GraphNode(node.val);
        lookup.put(node, nodeCopy);
        for (GraphNode nei : node.neighbors) {
            nodeCopy.neighbors.add(dfs(nei, lookup));
        }

        return nodeCopy;
    }

    /**
     * Factorial of Big String
     */
    public String factorialOfString(String num) {
        if (num == null) {
            return "";
        }
        if ("0".equals(num)) {
            return "1";
        }

        String result = "1";
        String factor = "1";
        while (!factor.equals(num)) {
            factor = addOne(factor);
            result = multiplyStrings(result, factor);
        }

        return result;
    }

    private String multiplyStrings(String num1, String num2) {
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

    private String addOne(String num) {
        int carry = 1;
        StringBuilder sb = new StringBuilder();
        for (int i = num.length() - 1; i >= 0; i--) {
            int sum = (num.charAt(i) - '0') + carry;
            carry = sum / 10;
            int digit = sum % 10;
            sb.append(digit);
        }

        if (carry > 0) {
            sb.append(carry);
        }

        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        ByteDance ins = new ByteDance();
        int[] nums = {1,4,2,8,5,7,9,10,15,3,1};
        ins.heapSort(nums);
        for (int n : nums) {
            System.out.print(n + " ");
        }
        System.out.println();
        // ins.printOneByOne();

        String num = ins.factorialOfString("100");
        System.out.println(num);
    }
}
