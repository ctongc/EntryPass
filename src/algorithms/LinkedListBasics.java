package algorithms;

class ListNode {
    public int value;
    public ListNode next;
    // public ListNode prev;

    public ListNode(int value) {
        this.value = value;
    }

    /* 1 以下皆为工具方法，可以使用Static
     * 2 可以直接使用head而不用复制一个current进行操作是因为head已经是个copy！不会改变原来的head
     * 3 设计要从用户的角度出发: 有哪些接口, 参数是什么, 返回是什么 */

    /**
     * 实际上java LinkedList里的size()是eager computation
     * by keeping a field. 所以 Time = O(1)
     */
    public static int size(ListNode head) {
        int size = 0;
        while (head != null) {
            size++;
            head = head.next;
        }
        return size;
    }

    /**
     * 1 -> 2 -> 3 -> null
     * index = 0 -> return 1
     * index = 4 -> return null
     * Integer可以返回null，告诉你返回出错了，跟返回0和-1不一样
     * 而且封装类的时候，可以不让别人知道我用的ListNode这个结构
     */
    public static Integer get(ListNode head, int index) {
        // index > 0先判定，因为head == null时跳出循环而index还没到，不会跳npe
        while (index > 0 && head != null) {
            head = head.next;
            index--;
        }
        // now index <= 0 || head == null
        if (index < 0 || head == null) {
            return null;  // 否则跳npe
        }
        return head.value;
    }

    public static ListNode appendHead(ListNode head, int value) {
        ListNode newHead = new ListNode(value);
        newHead.next = head;
        return newHead;
    }

    /**
     * 当链表为空时，就改变了头
     * 只要有可能改变头，就要把头返回给用户
     * 所以最后必须return head
     */
    public static ListNode appendTail(ListNode head, int value) {
        // case 1: head == null
        if (head == null) {
            return new ListNode(value);
        }

        // case 2: head != null. need a index here since we need to return head
        ListNode current = head;
        while(current.next != null) {
            current = current.next;
        }
        // current.next == null
        current.next = new ListNode(value);

        /* 如果用while(head != null) {
         *      head = new ListNode(value); }
         * 原链表根本没变
         * 因为一直在stack里 没有触发heap effect
         * 而head.next存在heap里 */
        return head;
    }

    /**
     * Assuming no duplicate values or remove first occurrence only
     * 1 -> 2 -> 3 -> null
     * remove(2)
     * 1 -> 3 -> null
     */
    public static ListNode remove(ListNode head, int value) {
        if (head == null) {
            return null;
        } else if (head.value == value) {
            return head.next; // remove head
        }
        ListNode current = head;
        while(current.next != null) {
            if(current.next.value == value) {
                current.next = current.next.next;
                return head;
            }
            current = current.next;
        }
        return head;
    }
}

public class LinkedListBasics {
    /**
     * Reverse LinkedList
     * Reverse a singly-linked list.
     * Time = O(n)
     * Space = O(1)
     * iteratively
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

    /**
     * Reverse LinkedList recursively
     *                 head    ||      SUBPROBLEM
     * before:         Node1 ->  Node2 -> Node3 -> ... -> NodeN -> null
     *                current    next
     *                 head    ||      SUBPROBLEM
     * after:  null <- Node1 <-  Node2 <- Node3 <- ... <- NodeN
     *                current    next
     * 除了subproblem外只有2处不同
     * 1 next.next = current; subproblem head 指向current
     * 2 current.next = null; current node's next is set to null;
     * Time = O(n)
     * Space = O(n)
     */
    public ListNode reverseLinkedList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseLinkedList2(head.next); // newHead is the last node of current linked list
        head.next.next = head; // head.next is the next node
        head.next = null; // reset, now pev -> head <- originalNext
        return newHead;
    }

    /**
     * Middle Node Of Linked List
     * Find the middle node of a given linked list.
     * When fast HIT the end of the linked list, slow is the mid point
     * if even list nodes, mid is the front one since we can get mid.next by mid and not vice versa
     * REMEMBER to check fast.next.next as well so we don't need to check fast == null in while loop
     * 双指针 vs 跑两遍 = online algorithm vs offline algorithm
     * 双指针任意时刻slow pointer都是fast pointer的1/2, 特别对于data stream数据流很关键
     * Time = O(n)
     * Space = O(1)
     */
    public ListNode getMiddleNode(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode slow = head;
        ListNode fast = head;
        // 如果fast已经移到null了，slow就过中点了，所以不能 fast != null && fast.next != null
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // now fast.next == null || fast.next.next == null
        return slow;
    }

    /**
     * Check If Linked List Has A Cycle
     * Check if a given linked list has a cycle. Return true if it does, otherwise return false.
     * Time = O(n)
     * Space = O(1)
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断环所在的位置
     * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
     * we know that 2 * velocity(slow) = velocity(fast)
     * so 2 * distance(slow) = distance(fast)
     * distance(slow) = headToCycleEntrance + entranceToIntersection
     *                         F                         a                       b                        a
     * distance(fast) = headToCycleEntrance + entranceToIntersection + IntersectionToEntrance + entranceToIntersection
     * => 2 * (F + a) = F + a + b + a
     * => F = b
     * Hence when two pointers (one from the front of the list, and the other from the intersection)
     * with same speed meet again, the intersection will be the start of the cycle
     * Time = O(n)
     * Space = O(1)
     */
    public ListNode detectCycle(ListNode head) {
        ListNode intersection = getIntersection(head);
        if (intersection == null) {
            return null;
        }
        ListNode n1 = head;
        ListNode n2 = intersection;
        while (n1 != n2) {
            n1 = n1.next;
            n2 = n2.next;
        }
        return n1;
    }

    private ListNode getIntersection(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return slow;
            }
        }
        return null;
    }

    /**
     * Insert In Sorted Linked List
     * Insert a value in a sorted linked list
     * Time = O(n)
     * Space= O(1)
     */
    public ListNode insertToSortedLinkedList(ListNode head, int value) {
        if (head == null) return new ListNode(value);
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = head;
        ListNode newNode = new ListNode(value);

        ListNode cur = dummy;
        while (cur.next != null && cur.next.value < value) {
            cur = cur.next;
        }
        // cur.next == null || cur.next.value >= value
        ListNode oldNext = cur.next;
        cur.next = newNode;
        newNode.next = oldNext;
        return dummy.next;
    }

    /**
     * Merge Two Sorted Linked Lists
     * Merge two sorted lists into one large sorted list.
     * Time = O(n)
     * Space = O(1)
     */
    public ListNode mergeTwoSortedLinkedList(ListNode one, ListNode two) {
        if (one == null) {
            return two;
        } else if (two == null) {
            return one;
        }
        ListNode dummyHead = new ListNode(0);
        ListNode cur = dummyHead; // if current = dummy.next, then current = one || two => NO HEAP EFFECT!!
        while (one != null && two != null) {
            if (one.value < two.value) {
                cur.next = one;
                one = one.next;
            } else {
                cur.next = two;
                two = two.next;
            }
            cur = cur.next; // move cur pointer to the newly added node
        }
        // now one == null || two == null
        // linked the remaining nodes from one || two to the large sorted list
        // no while loop needed here since the remaining nodes are linked already
        cur.next = one == null ? two : one;
        return dummyHead.next;
    }

    /**
     * ReOrder Linked List
     * Reorder the given singly-linked list N1 -> N2 -> N3 -> N4 -> … -> Nn -> null
     * to be N1- > Nn -> N2 -> Nn-1 -> N3 -> Nn-2 -> … -> null
     *
     * 1 find the mid node
     * 2 reverse the 2nd half
     * 3 merge 1st half and 2nd half's elements one by one
     *
     * Time = O(n)
     * Space = O(1)
     */
    public ListNode reorderLinkedList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 1
        ListNode mid = getMiddleNode(head);
        // 2 3
        ListNode n2 = mid.next;
        mid.next = null; // cut the original linked list
        return mergeTwoLinkedListOneByOne(head, reverseLinkedList(n2));
    }

    private ListNode mergeTwoLinkedListOneByOne(ListNode n1, ListNode n2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while (n1 != null && n2 != null) {
            // merge one element from n1
            current.next = n1;
            n1 = n1.next;
            // merge one element from n2
            current.next.next = n2;
            n2 = n2.next;
            // move current, Don't forgot this!
            current = current.next.next;
        }
        // n1 == null || n2 == null
        current.next = n1 == null ? n2 : n1;
        return dummy.next;
    }

    /**
     * Partition Linked List
     * 根据target值, 将链表分成两部分: 前面的元素为小于x的, 后面部分为大于等于x的, 并且各个节点之间次序不变
     * 1 new两个dummy head, 一个拉比target小的元素的linkedList, 另一个拉比target大的元素的linkedList
     *   list 1: all elements.value < target, list 2: otherwise
     * 2 iterate over every element in the given linked list
     *   case 1: if current.value < target.value, 加到list1中
     *   case 2: otherwise 加到list2中
     * 3 把list2 append到list1后面
     * 4 记得给list 2最后要手动设置指向null
     * Time = O(n)
     * Space = O(1)
     */
    public ListNode partitionLinkedList(ListNode head, int target) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummySmall = new ListNode(0);
        ListNode dummyLarge = new ListNode(0);
        ListNode smallCurrent = dummySmall;
        ListNode largeCurrent = dummyLarge;
        while (head != null) {
            if (head.value < target) {
                smallCurrent.next = head;
                smallCurrent = smallCurrent.next;
            } else{
                largeCurrent.next = head;
                largeCurrent = largeCurrent.next;
            }
            head = head.next;
        }
        // head == null
        // append largeList to smallList
        smallCurrent.next = dummyLarge.next;
        // cut the tail by let last element.next = null, don't forgot!!
        largeCurrent.next = null;
        return dummySmall.next;
    }

    /**
     * Merge Sort Linked List
     * Given a singly-linked list, where each node contains an integer value
     * sort it in ascending order. The merge sort algorithm should be used to solve this problem.
     * Time = O(nlogn)
     * Space = O(nlogn)
     */
    public ListNode mergeSort(ListNode head) {
        // sanity check
        if (head == null || head.next == null) {
            return head;
        }
        return partition(head);
    }

    private ListNode partition(ListNode head) {
        // if only one list node left, return
        if (head.next == null) {
            return head;
        }
        // first half's head is head
        ListNode midNode = getMiddleNode(head);
        ListNode head2 = midNode.next;
        midNode.next = null; // cut
        head = partition(head);
        head2 = partition(head2);
        return merge(head, head2); // merge them
    }

    private ListNode merge(ListNode n1, ListNode n2) {
        ListNode dummyHead = new ListNode(0);
        ListNode cur = dummyHead;
        // 谁小移谁
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
        // link the remaining nodes
        cur.next = n1 == null ? n2 : n1;
        return dummyHead.next;
    }

    /**
     * Add Two Numbers
     * You are given two linked lists representing two non-negative numbers.
     * The digits are stored in reverse order and each of their nodes contain a single digit.
     * Add the two numbers and return it as a linked list.
     * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4) // 342 + 456
     * Output: 7 -> 0 -> 8 // = 807
     * Time = O(n)
     * Space = O(n)
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }
        ListNode dummyHead = new ListNode(0);
        ListNode cur = dummyHead;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int v1 = 0;
            int v2 = 0;
            if (l1 != null) {
                v1 = l1.value;
                l1 = l1.next;
            }
            if (l2 != null) {
                v2 = l2.value;
                l2 = l2.next;
            }
            int posValue = (v1 + v2 + carry) % 10;
            carry = (v1 + v2 + carry) / 10;
            ListNode newNode = new ListNode(posValue);
            cur.next = newNode;
            cur = cur.next;
        }
        // check if there is any carry left
        if (carry > 0) {
            ListNode newNode = new ListNode(carry);
            cur.next = newNode;
        }
        return dummyHead.next;
    }

    /**
     * Check If Linked List Is Palindrome
     * Given a linked list, check whether it is a palindrome.
     * Input:  1 -> 2 -> 3 -> 2 -> 1 -> null
     * Output: true
     * Time = O(n)
     * Space = O(1)
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode midNode = getMiddleNode(head);
        ListNode oldLast = reverseLinkedList(midNode.next); // 注意midNode位置 1 M 3 4
        midNode.next = null; // cut the original LinkedList
        // note now the length of the second half <= length of first half
        // by the definition of palindrome, the middle 3 doesn't have an impact
        // on if a "word" is palindrome, so we can use the length of second half to iterate
        return checkPalindrome(head, oldLast);
    }

    private boolean checkPalindrome(ListNode n1, ListNode n2) {
        while (n2 != null) {
            if (n1.value != n2.value) {
                return false;
            }
            n1 = n1.next;
            n2 = n2.next;
        }
        return true;
    }
}
