package exams;

import java.util.*;

class ListNode {
    int value;
    ListNode next;

    public ListNode(final int value) {
        this.value = value;
    }
}

class TreeNode {
    int key;
    TreeNode left;
    TreeNode right;

    public TreeNode(final int key) {
        this.key = key;
    }
}

class Cell {
    int value;
    int row;
    int col;

    public Cell(int value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;

    }
}

public class Midterm1 {
    /**
     * Time = O(n)
     * Space = O(n)
     */
    public ListNode reverseLinkedList(final ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseLinkedList(head.next); // passing real last node for return
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    /**
     * Time = O(n)
     * Space = O(1)
     */
    public ListNode reverseLinkedList2(final ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode cur = head;
        ListNode prev = null;
        while(cur != null) { // if (cur.next != null) then it won't reverse the last element
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        // now cur == null
        return prev;
    }

    /**
     * Time = O(# of nodes)
     * Space = O(height)
     */
    public boolean isBST(final TreeNode head) {
        // BST: 任何一个节点, 左子树的所有node都比它小, 右子树的所有node都比它大
        // 看的是该节点本身是否在区间内, 而不是他和左右子节点的关系
        return isBSTHelper(head, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBSTHelper(final TreeNode cur, final int lowerBound, final int upperBound) {
        if (cur == null) {
            return true;
        }
        if (cur.key < lowerBound || cur.key > upperBound) {
            return false;
        }
        return isBSTHelper(cur.left, lowerBound, cur.key) && isBSTHelper(cur.right, cur.key, upperBound);
    }

    /**
     * Time = O(n!)
     * Space = O(n*n)
     */
    public List<String> allPermutation(final String s) {
        final List<String> result = new ArrayList<>();
        // when all elements are used, no sb is needed
        allPermutationHelper(result, s.toCharArray(), 0);
        return result;
    }

    private void allPermutationHelper(final List<String> result, final char[] cArray, final int index) {
        if (index == cArray.length) {
            result.add(new String(cArray));
        }
        final HashSet<Character> used = new HashSet<>();
        for (int i = index; i < cArray.length; i++) {
            if (used.add(cArray[i])) {
                swap(cArray, i, index);
                allPermutationHelper(result, cArray, i + 1);
                swap(cArray, i, index);
            }
        }
    }

    private void swap(final char[] array, int a, int b) {
        char temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /**
     * Time = O(n)
     * Space = O(n)
     */
    public String removeRedundantSpace(final String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        final char[] cArray = s.toCharArray();
        int i = 0;
        for (int j = 0; j < cArray.length; j++) {
            if (cArray[j] != ' ' ||
                    i > 0 && cArray[i - 1] != ' ') {
                cArray[i++] = cArray[j];
            }
        }
        if (cArray[cArray.length - 1] == ' ') {
            i--;
        }
        return new String(cArray, 0, i);
    }

    /**
     * Time = O(m * n)
     * Space = O(k)
     */
    public int getKthSmallestSum(final int k, final int[] a, final int[] b) {
        // assume k << a.length * b.length
        if (a == null || a.length == 0) {
            return b[k];
        } else if (b == null || b.length == 0) {
            return a[k];
        }
        final PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, Comparator.reverseOrder());
        for (int i : a) {
            for (int j : b) {
                int sum = i + j;
                if (maxHeap.size() < k) {
                    maxHeap.offer(sum);
                } else if (sum < maxHeap.peek()) {
                    maxHeap.poll();
                    maxHeap.offer(sum);
                }
            }
        }
        return maxHeap.peek();
    }

    /**
     * Time = O(k)
     * Space = O(m * n)
     */
    public int getKthSmallestSum2(final int k, final int[] a, final int[] b) {
        // assume k << a.length * b.length
        if (a == null || a.length == 0) {
            return b[k];
        } else if (b == null || b.length == 0) {
            return a[k];
        }

        final PriorityQueue<Cell> minHeap = new PriorityQueue<>(k, (Cell c1, Cell c2) -> {
            if (c1.value == c2.value) {
                return 0;
            }
            return c1.value < c2.value ? -1 : 1;
        });
        boolean visited[][] = new boolean[a.length][b.length];
        minHeap.offer(new Cell(a[0] + b[0], 0, 0));
        visited[0][0] = true;

        for (int i = 0; i < k - 1; i++) {
            // 拿一个出来放两个进去, 所以k - 2次后heap里有k个元素而且k-1 smallest已经被拿出来了
            final Cell cur = minHeap.poll();
            if (cur.row + 1 < a.length && !visited[cur.row + 1][cur.col]) {
                minHeap.offer(new Cell(a[cur.row + 1] + b[cur.col], cur.row + 1, cur.col));
                visited[cur.row + 1][cur.col] = true;
            }
            if (cur.col + 1 < b.length && !visited[cur.row][cur.col + 1]) {
                minHeap.offer(new Cell(a[cur.row] + b[cur.col + 1], cur.row, cur.col + 1));
                visited[cur.row][cur.col + 1] = true;
            }
        }
        return minHeap.peek().value;
    }
}

