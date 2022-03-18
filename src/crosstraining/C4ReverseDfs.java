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

public class C4ReverseDfs {
    public C4ReverseDfs() {
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

    public static void main(String[] args) {
        C4ReverseDfs ins = new C4ReverseDfs();
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
