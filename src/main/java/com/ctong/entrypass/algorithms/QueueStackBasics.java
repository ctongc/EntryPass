package com.ctong.entrypass.algorithms;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/* 设计要从用户的角度出发: 有哪些接口, 参数是什么, 返回是什么 */

/**
 * Implement a stack using ListNode
 */
class NodeStack {
    // int size;
    private ListNode head;

    public NodeStack() {
        // size = 0;
        // head = null;
    }

    public Integer pop() {  // need return Integer since might return null
        if (head == null) {
            return null;
        }
        ListNode cur = head;
        head = head.next;
        cur.next = null; // clean up
        return cur.value;
    }

    public void push(int val) {
        ListNode newNode = new ListNode(val);
        newNode.next = head;
        head = newNode;
    }

    public Integer peek() {
        if (head == null) {
            return null;
        }
        return head.value;
    }
}

/**
 * Implement a queue using ListNode
 */
class NodeQueue {
    // int size;
    ListNode head;
    ListNode tail;

    public NodeQueue() {
        // size = 0;
        // head = null;
        // tail = null;
    }

    // tail
    public void offer(Integer value) {
        ListNode newNode = new ListNode(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = tail.next;
        }
    }

    // head
    public Integer poll() {
        if (head == null) {
            return null;
        }
        ListNode node = head;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        node.next = null; // clean up
        return node.value;
    }

    public Integer peek() {
        if (head == null) {
            return null;
        }
        return head.value;
    }
}

/**
 * Implement a queue using Array (circular array)
 */
class BoundedQueue {
    int head;
    int tail;
    int size;
    Integer[] array;
    public BoundedQueue(int cap) {
        array = new Integer[cap];
        // head = 0;
        // tail = 0;
        // size = 0;
    }

    public boolean offer(Integer value) {
        // solution 1
        if (size == array.length) {
            return false;
        }
        // solution 2
        array[tail] = value;
        tail = (tail + 1 == array.length) ? 0 : tail + 1;
        size++;
        return true;
    }

    public Integer peek() {
        if (size == 0) {
            return null;
        }
        return array[head];
    }

    public Integer poll() {
        if (size == 0) {
            return null;
        }
        Integer result = array[head];
        head = (head + 1 == array.length) ? 0: head + 1; // (head + 1) % array.length;
        size--;
        return result;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == array.length;
    }
}

class BoundedStack {
    int[] array;
    int head;

    public BoundedStack(int cap) {
        // check cap
        this.array = new int[cap];
        head = -1;
    }

    public boolean push(int value) {
        if (head == array.length - 1) {
            return false;
        }
        array[++head] = value;
        return true;
    }

    public Integer pop() {
        return head == -1 ? null : array[head--];
    }

    public Integer peek() {
        return head == -1 ? null : array[head];
    }
}

/**
 * Queue by two stack
 * inStack is only used for storing new elements that been added
 * outStack is used for output(pop) elements out of the queue
 * only if outStack is empty, move all elements from inStack, if any, into outStack
 * Time = Amortized O(1)
 * Space = O(n)
 */
class QueueByTwoStacks {
    private Deque<Integer> inStack;
    private Deque<Integer> outStack;
    public QueueByTwoStacks() {
        inStack = new ArrayDeque<>();
        outStack = new ArrayDeque<>();
    }

    public Integer poll() {
        moveToOutStack();
        return outStack.pollFirst();
    }

    public void offer(int element) {
        inStack.offerFirst(element);
    }

    public Integer peek() {
        moveToOutStack();
        return outStack.peekFirst();
    }

    public int size() {
        return inStack.size() + outStack.size();
    }

    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    private void moveToOutStack() {
        // we need to make sure outStack is Empty before we move
        // otherwise the order will be messed up !!
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.offerFirst(inStack.pollFirst());
            }
        }
    }
}

/**
 * Stack With min()
 * Enhance the stack implementation to support min() operation.
 * min() - return the current minimum value in the stack. If the stack is empty, min() should return -1.
 * pop() - remove and return the top element, if the stack is empty, return -1
 * push(int element) - push the element to top
 * top() - return the top element without remove it, if the stack is empty, return -1
 * Time = O(1);
 * Space = O(n);
 *
 * 如果加的元素里重复的很多，那么minStack里只加一次minElement的tuple <minElement, current stack.size()> // 不是Min的个数
 * 当stack.peekFirst() == minStack.peekFirst() && stack.size() == minStack.peekLast.size()的时候才minStack.pop()
 * 用another stack记录global min加入stack2时的stack1.size()也行
 */
class StackWithMin {
    // 两个stack同加同减
    final private Deque<Integer> stack;
    final private Deque<Integer> minStack;

    public StackWithMin() {
        stack = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
    }

    public int pop() {
        if (stack.peekFirst() == null) {
            return -1;
        }
        Integer top = stack.pollFirst();
        if (minStack.peekFirst().equals(top)) {
            minStack.pollFirst();
        }
        return top;
    }

    public void push(int element) {
        stack.offerFirst(element);
        // when value <= current min value in stack, push it to minStack
        if (minStack.isEmpty() || element <= minStack.peekFirst()) {
            minStack.offerFirst(element);
        }
    }

    public int top() {
        return stack.isEmpty() ? -1 : stack.peekFirst();
    }

    public int min() {
        return minStack.isEmpty() ? -1 : minStack.peekFirst();
    }
}

public class QueueStackBasics {
    /**
     * Sort with three stacks
     * Given one stack with integers, sort it with two additional stacks (total 3 stacks).
     * After sorting the original stack should contain the sorted integers and from top to
     * bottom the integers are sorted in ascending order.
     * Time = O(n ^ 2)
     * Space = O(n)
     */
    public void sortWithThreeStacks(LinkedList<Integer> s1) {
        Deque<Integer> s2 = new LinkedList<>(); // store the rest
        Deque<Integer> s3 = new LinkedList<>(); // store the min

        while (!s1.isEmpty()) {
            Integer globalMin = null;
            while (!s1.isEmpty()) {
                Integer cur = s1.pollFirst();
                globalMin = globalMin == null ? cur : Math.min(globalMin, cur);
                s2.offerFirst(cur);
            }
            while (!s2.isEmpty()) {
                Integer temp = s2.pollFirst();
                if (temp.equals(globalMin)) {
                    s3.offerFirst(temp);
                } else {
                    s1.offerFirst(temp);
                }
            }
        }
        while (!s3.isEmpty()) {
            s1.offerFirst(s3.pollFirst());
        }
    }

    /**
     * Sort with three stacks
     * using the thought of merge sort
     * Time = O(nlogn)
     * Space = O(nlogn)
     */
    public void sortWithThreeStacks2(Deque<Integer> s1) {
        Deque<Integer> s2 = new LinkedList<>(); // store the rest
        Deque<Integer> s3 = new LinkedList<>(); // store the min
        sortWithThreeStacks(s1, s2, s3, s1.size());
    }

    private void sortWithThreeStacks(Deque<Integer> s1,Deque<Integer> s3,Deque<Integer> s2, int size) {
        if (size <= 1) { // base case
            return;
        }
        int mid1 = size / 2;
        int mid2 = size - size / 2;
        for (int i = 0; i < mid1; i++) {
            s2.offerFirst(s1.pollFirst());
        }
        // use the other stacks to sort s2/s1
        // after sorting the numbers in s2/s1 are in ascending order from top to
        // bottom in the two stacks
        sortWithThreeStacks(s2, s3, s1, mid1);
        sortWithThreeStacks(s1, s3, s2, mid2);
        int i = 0;
        int j = 0;
        while (i < mid1 && j < mid2) {
            if (s2.peekFirst() < s1.peekFirst()) {
                s3.offerFirst(s2.pollFirst());
                i++;
            } else {
                s3.offerFirst(s1.pollFirst());
                j++;
            }
        }
        while (i < mid1) {
            s3.offerFirst(s2.pollFirst());
            i++;
        }
        while (j < mid2) {
            s3.offerFirst(s1.pollFirst());
            j++;
        }
        // after merging, the numbers are in descending order from top to bottom in s3,
        // we need to push them back into s1 so that they are in ascending order
        for (int index = 0; index < size; index++) {
            s1.offerFirst(s3.pollFirst());
        }
    }

    /**
     * Sort with two stacks
     * Given an array that is initially stored in one stack, sort it with one
     * additional stacks (total 2 stacks). After sorting the original stack
     * should contain the sorted integers and from top to bottom the integers
     * are sorted in ascending order.
     * Time = O(n ^ 2)
     * Space = O(n)
     */
    public void sortWithTwoStacks(LinkedList<Integer> s1) {
        Deque<Integer> s2 = new ArrayDeque<>();
        while (!s1.isEmpty()) {
            Integer globalMin = null;
            int count = 0;
            // move all the elements from s1 to s2
            // meanwhile, record the globalMin and how many of it
            while (!s1.isEmpty()) {
                Integer cur = s1.pollFirst();
                if (globalMin == null || cur < globalMin) {
                    globalMin = cur;
                    count = 1;
                } else if (cur.equals(globalMin)) {
                    count++;
                }
                s2.offerFirst(cur);
            }
            // now we have global min and need to push
            // every element less than global min back from s2 to s1
            while (!s2.isEmpty() && s2.peekFirst() >= globalMin) {
                Integer temp = s2.pollFirst();
                if (temp > globalMin) {
                    s1.offerFirst(temp);
                }
            }
            for (int i = 0; i < count; i++) {
                s2.offerFirst(globalMin);
            }
        }
        while (!s2.isEmpty()) {
            s1.offerFirst(s2.pollFirst());
        }
    }


    /**
     * Use multiple Stacks to implement a de-queue
     *
     * Method 1
     * Use two stacks end to end 1234 || 5678
     * addLeft() = stack1.add();
     * addRight() = stack2.add();
     * removeLeft()   O(2n + 1)
     *  case 1: if stack1 is not empty, then call stack1.pop()
     *  case 2: if stack1 is empty, move all elements from stack 2 to stack 1 (if any)
     *          and then call stack1.pop()
     * removeRight() same as removeLeft()
     * Time = O(n)
     *
     * Method 2
     * Use three stacks to improve the time complexity of remove()
     * We use stack3 as a buffer stack when moving all elements between right and left,
     * To make sure the number of elements in Left and Right part
     * are roughly 50% of all elements
     * In detail, say stack3 stores the top 1/2 elements of stack1
     * so bottom 1/2 elements of stack1 could be moved to stack2
     * then move elements in stack3 back to stack 1
     * 12345678 ||
     * stack3: 1234
     * 5678 || 1234
     * this could optimize the time complexity of remove() to amortized O(1)
     */
    public class DequeByThreeStacks {
        final private Deque<Integer> left;
        final private Deque<Integer> right;
        final private Deque<Integer> buffer;

        public DequeByThreeStacks() {
            this.left = new ArrayDeque<>();
            this.right = new ArrayDeque<>();
            this.buffer = new ArrayDeque<>();
        }

        public void offerFirst(int element) {
            left.offerFirst(element);
        }

        public void offerLast(int element) {
            right.offerLast(element);
        }

        public Integer pollFirst() {
            move(right, left);
            return left.isEmpty() ? null : left.pollFirst();
        }

        public Integer peekFirst() {
            move(right, left);
            return left.isEmpty() ? null : left.peekFirst();
        }

        public Integer pollLast() {
            move(left,right);
            return right.isEmpty() ? null : right.pollFirst();
        }

        public Integer peekLast() {
            move(left,right);
            return right.isEmpty() ? null : right.peekFirst();
        }

        public int size() {
            return left.size() + right.size();
        }

        public boolean isEmpty() {
            return left.isEmpty() && right.isEmpty();
        }

        // when the destination stack is empty, move half of the elements from
        // the source stack to the destination stack
        private void move(Deque<Integer> src, Deque<Integer> dest) {
            if (!dest.isEmpty()) {
                return;
            }
            int halfSize = src.size() / 2;
            for (int i = 0; i < halfSize; i++) {
                buffer.offerFirst(src.pollFirst());
            }
            while (!src.isEmpty()) {
                dest.offerFirst(src.pollFirst());
            }
            while (!buffer.isEmpty()) {
                src.offerFirst(buffer.pollFirst());
            }
        }
    }

    public static void main(String[] args) {
        QueueStackBasics ins = new QueueStackBasics();
    }
}
