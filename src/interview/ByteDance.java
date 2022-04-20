package interview;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

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
     * Q1 两个Thread交替打印
     */
    static int counter = 0;
    static int N = 100;

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
     * Q2 Deep clone n-nary tree
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

    public Node deepCopy(Node n) {
        if (n == null) {
            return null;
        }
        Queue<Node> queue = new ArrayDeque<>();
        Node newNode = new Node(n.val, new ArrayList(n.children)); // <new Val, old.children>
        queue.offer(newNode);
        while (!queue.isEmpty()) {
            Node curNode = queue.poll();
            List<Node> children = curNode.children;
            int size = children.size();
            for (int i = 0; i < size; i++) {
                Node beenCopied = children.remove(0);
                Node childCopy = new Node(beenCopied.val, new ArrayList(beenCopied.children));
                children.add(childCopy);
                queue.offer(childCopy);
            }
        }
        return newNode;
    }

    public static void main(String[] args) {
        ByteDance ins = new ByteDance();
        int[] nums = {1,4,2,8,5,7,9,10,15,3,1};
        ins.heapSort(nums);
        for (int n : nums) {
            System.out.print(n + " ");
        }
        System.out.println();
        ins.printOneByOne();
    }
}
