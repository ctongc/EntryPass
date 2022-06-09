package sprint;

import java.util.HashMap;
import java.util.Map;

class ListNode {
    public final int value;
    public ListNode next;
    public ListNode(int value) {
        this.value = value;
        this.next = null;
    }
}

class RandomListNode {
    public final int value;
    public RandomListNode next;
    public RandomListNode random;
    public RandomListNode(int value) {
        this.value = value;
        this.next = null;
        this.random = null;
    }
}

public class Class2LinkedList {

    public ListNode reverseLinkedList(ListNode head) {
        if (head == null || head.next == null) {
            return head; // not return null;
        }

        ListNode newHead = reverseLinkedList(head.next);
        // 注意recursion结束之后, newHead已经指向head了, 所以只用处理head就可以了
        head.next.next = head; // not newHead.next = head;
        head.next = null;

        return newHead;
    }

    public ListNode reverseLinkedList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prevNode = null;

        while (head != null) {
            ListNode nextNode = head.next;
            // 翻转当前node, 不要管head.next.next指向哪儿
            head.next = prevNode;
            prevNode = head;
            head = nextNode;
        }
        return prevNode;
    }

    public ListNode reverseInPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = head.next;
        head.next = reverseInPairs(head.next.next);
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

    // TODO
    public ListNode reverseInKNodes(ListNode head, int k) {
        if (k < 2) {
            return head;
        }
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode p = dummyHead;
        while (p.next != null && p.next.next != null) {
            ListNode prevNode = p.next;
            ListNode cur = prevNode.next;
            int i = 0;
            while (cur != null && i < k - 1) {
                ListNode nextNode = cur.next;
                cur.next = prevNode;
                prevNode = cur;
                cur = nextNode;
                i++;
            }
            if (i == k - 1) { // k nodes reversed
                ListNode nextNode = p.next;
                p.next.next = cur;
                p.next = prevNode;
                p = nextNode;
            } else { // less than k nodes reversed
                cur = prevNode.next;
                prevNode.next = null;
                while (cur != p.next) {
                    ListNode nextNode = cur.next;
                    cur.next = prevNode;
                    prevNode = cur;
                    cur = nextNode;
                }
                break;
            }
        }
        return dummyHead.next;
    }

    public ListNode removeLastNthNode(ListNode head, int n) {
        if (head == null || n < 1) {
            return head;
        }
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode slow = dummyHead;
        ListNode fast = dummyHead;
        for (int i = 0; i <= n; i++) {
            if (fast != null) { // Assume last n <= number of nodes, otherwise remove head
                fast = fast.next;
            }
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return dummyHead.next;
    }

    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(fast == slow) {
                return true;
            }
        }
        return false;
    }

    public ListNode findCycleStart(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(fast == slow) {
                slow = head;
                while (fast != slow) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }

    public boolean isPalindromeLinkedList(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode midNode = findMidNode(head); // 中点或前一半的最后一个元素！
        ListNode reversedLast = reverseLinkedList(midNode.next); // reverse后一半, 不包括midNode
        midNode.next = null; // reverse后，前一半仍然指向midNode.next，需要手动切掉
        while(reversedLast != null) { // 因为前一半包括midNode
            if (reversedLast.value != head.value) {
                return false;
            }
            reversedLast = reversedLast.next;
            head = head.next;
        }
        return true;
    }

    private ListNode findMidNode(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            // 不能是fast != null && fast.next != null, 否则slow就过中点了
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow; // 中点或前一半的最后一个元素！
    }

    public ListNode reorderLinkedList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode midNode = findMidNode(head);
        ListNode reversedLast = reverseLinkedList(midNode.next);
        midNode.next = null; // reverse后，前一半仍然指向midNode.next，需要手动切掉
        return mergeTwoLinkedList(head, reversedLast);

    }

    private ListNode mergeTwoLinkedList(ListNode n1, ListNode n2) {
        ListNode dummyHead = new ListNode(0);
        ListNode cur = dummyHead;
        while (n1 != null && n2 != null) {
            cur.next = n1;
            n1 = n1.next;
            cur.next.next = n2;
            n2 = n2.next;
            cur = cur.next.next;
        }
        while (n1 != null) {
            cur.next = n1;
            n1 = n1.next;
            cur = cur.next;
        }
        while (n2 != null) {
            cur.next = n2;
            n2 = n2.next;
            cur = cur.next;
        }
        return dummyHead.next;
    }

    private ListNode mergeTwoSortedLinkedList(ListNode n1, ListNode n2) {
        ListNode dummyHead = new ListNode(0);
        ListNode cur = dummyHead;
        while (n1 != null && n2 != null) {
            if (n1.value < n2.value) {
                cur.next = n1;
                n1 = n1.next;
            } else {
                cur.next = n2;
                n2 = n2.next;
            }
            cur = cur.next;
        }
        while (n1 != null) {
            cur.next = n1;
            n1 = n1.next;
            cur = cur.next;
        }
        while (n2 != null) {
            cur.next = n2;
            n2 = n2.next;
            cur = cur.next;
        }
        return dummyHead.next;
    }

    public ListNode addMathValueOfTwoLinkedList(ListNode n1, ListNode n2) {
        if (n1 == null || n1.value == 0) {
            if(n2 == null) {
                return new ListNode(0);
            }
            return n2;
        }
        if (n2 == null || n2.value == 0) {
            if(n1 == null) {
                return new ListNode(0);
            }
            return n1;
        }
        ListNode dummyHead = new ListNode(0);
        ListNode cur = dummyHead;
        n1 = reverseLinkedList(n1);
        n2 = reverseLinkedList(n2);
        int carry = 0;
        while (n1 != null || n2 != null) {
            int digit1 = 0;
            int digit2 = 0;
            if (n1 != null) {
                digit1 = n1.value;
                n1 = n1.next;
            }
            if (n2 != null) {
                digit2 = n2.value;
                n2 = n2.next;
            }
            int digitSum = digit1 + digit2 + carry;
            if (digitSum == 0) {
                break;
            }
            carry = digitSum / 10;
            cur.next = new ListNode(digitSum % 10);
            cur = cur.next;
        }
        return reverseLinkedList(dummyHead.next);
    }

    public boolean isIntersect(ListNode n1, ListNode n2) {
        if (n1 == null || n2 == null) {
            return false;
        }
        while(n1.next != null) {
            n1 = n1.next;
        }
        while(n2.next != null) {
            n2 = n2.next;
        }
        return n1 == n2;
    }

    public ListNode findIntersection(ListNode n1, ListNode n2) {
        if (n1 == null || n2 == null) {
            return null;
        }
        int n1Length = 0;
        int n2Length = 0;
        ListNode index1 = n1;
        ListNode index2 = n2;
        /* L1:           2 -> 3               Length = 4
         * L2: 1 -> 4 -> 7 -> 3 -> 5 -> 9     Length = 6 */
        while(index1.next != null) {
            n1Length++;
            index1 = index1.next;
        }
        while(index2.next != null) {
            n2Length++;
            index2 = index2.next;
        }
        if (n1Length < n2Length) {
            for (int i = 0; i < n2Length - n1Length; i++) {
                n2 = n2.next;
            }
        } else {
            for (int i = 0; i < n2Length - n1Length; i++) {
                n1 = n1.next;
            }
        }
        while (n1 != null && n2 != null) {
            if (n1 == n2) {
                return n1;
            }
            n1 = n1.next;
            n2 = n2.next;
        }
        return null;
    }

    public RandomListNode deepCopyLinkedList(RandomListNode head) {
        if (head == null) {
            return head;
        }

        Map<RandomListNode, RandomListNode> lookup = new HashMap<>();
        RandomListNode cur = head;
        while (cur != null) {
            RandomListNode copiedNode = lookup.get(cur);
            if (copiedNode == null) {
                copiedNode = new RandomListNode(cur.value);
                lookup.put(cur, copiedNode);
            }

            RandomListNode copiedNodeNext = lookup.get(cur.next);
            if (cur.next != null && copiedNodeNext == null) {
                copiedNodeNext = new RandomListNode(cur.next.value);
                lookup.put(cur.next, copiedNodeNext);
            }
            copiedNode.next = copiedNodeNext;

            RandomListNode copiedNodeRandom = lookup.get(cur.random);
            if (cur.random != null && copiedNodeRandom == null) {
                copiedNodeRandom = new RandomListNode(cur.random.value);
                lookup.put(cur.random, copiedNodeRandom);
            }
            copiedNode.random = copiedNodeRandom;

            cur = cur.next;
        }
        return lookup.get(head);
    }

    public RandomListNode cloneLinkedListWithRandom(RandomListNode head) {
        if (head == null) {
            return head;
        }
        Map<RandomListNode, RandomListNode> lookup = new HashMap<>();
        return cloneLinkedListWithRandom(head, lookup);
    }

    private RandomListNode cloneLinkedListWithRandom(RandomListNode node, Map<RandomListNode, RandomListNode> lookup) {
        if (node == null) {
            return null;
        }

        RandomListNode nodeCopy = lookup.get(node);
        if (nodeCopy != null) {
            return nodeCopy;
        }

        nodeCopy = new RandomListNode(node.value);
        lookup.put(node, nodeCopy);

        nodeCopy.next = cloneLinkedListWithRandom(node.next, lookup);
        nodeCopy.random = cloneLinkedListWithRandom(node.random, lookup);

        return nodeCopy;
    }

    public static void main(String[] args) {
        Class2LinkedList ins = new Class2LinkedList();
        int[] values = new int[]{1,2,3,4,5,6,7,8,9};
        ListNode head = ins.createLinkedList(values);
        ins.printLinkedList(head);
        // ListNode n = ins.reverseLinkedList(head);
        // ListNode n = ins.reverseLinkedList2(head);
        // ListNode n = ins.reverseInPairs(head);
        // ListNode n = ins.reverseInPairs2(head);
        // ListNode n = ins.reverseInKNodes(head, 4);
        // ListNode n = ins.removeLastNthNode(head, 9);
        // ListNode n = ins.reorderLinkedList(head);
        // ListNode n = ins.mergeTwoSortedLinkedList(
        //        ins.createLinkedList(new int[]{1, 3, 5}),
        //        ins.createLinkedList(new int[]{2, 4, 6, 7}));
        // ListNode n = ins.addMathValueOfTwoLinkedList(
        //        ins.createLinkedList(new int[]{1, 3, 5, 7}),
        //        ins.createLinkedList(new int[]{2, 4, 6}));

        RandomListNode n1 = new RandomListNode(1);
        RandomListNode n2 = new RandomListNode(2);
        RandomListNode n3 = new RandomListNode(3);
        RandomListNode n4 = new RandomListNode(4);
        RandomListNode n5 = new RandomListNode(5);
        RandomListNode n6 = new RandomListNode(6);

        n1.next = n2;
        n1.random = n4;
        n2.next = n3;
        n2.random = n2;
        n3.next = n4;
        n4.next = n5;
        n4.random = n1;
        n5.next = n6;
        n5.random = n6;
        n6.random = n3;

        ins.printLinkedListWithRandom(n1);
        RandomListNode rn1 = ins.deepCopyLinkedList(n1);
        ins.printLinkedListWithRandom(rn1);
        RandomListNode rn2 = ins.cloneLinkedListWithRandom(n1);
        ins.printLinkedListWithRandom(rn2);
    }

    public ListNode createLinkedList(int[] values) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int i : values) {
            cur.next = new ListNode(i);
            cur = cur.next;
        }
        return dummy.next;
    }

    public void printLinkedList(ListNode head) {
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println();
    }

    public void printLinkedListWithRandom(RandomListNode head) {
        while (head != null) {
            System.out.print("[" + head.value + ", ");
            if (head.random != null) {
                System.out.print(head.random.value);
            } else {
                System.out.print("null");
            }
            System.out.print("]");
            head = head.next;
        }
        System.out.println();
    }
}
