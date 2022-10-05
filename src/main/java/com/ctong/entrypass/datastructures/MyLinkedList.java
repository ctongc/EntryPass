package com.ctong.entrypass.datastructures;

import java.util.NoSuchElementException;

/**
 * 设计要从用户的角度出发: 有哪些接口, 参数是什么, 返回是什么
 */
public class MyLinkedList<E> {

    private static class ListNode<E> {
        E item;
        ListNode<E> next;
        ListNode<E> prev;

        public ListNode(E item) {
            this.item = item;
        }

        public ListNode(E item, ListNode<E> next) {
            this.item = item;
            this.next = next;
        }

        public ListNode(ListNode<E> prev, E item, ListNode<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private ListNode<E> head;
    private ListNode<E> tail; // doubly linked list
    private int size;

    /**
     * eager computation by keeping a field
     *
     * Time = O(1)
     */
    public int size() {
        return this.size;
    }

    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }

        for (ListNode<E> cur = head; cur != null; cur = cur.next) {
            if (o.equals(cur.item)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Links e as last element
     */
    public void add(E e) {
        final ListNode<E> newNode = new ListNode<>(tail, e, null);
        final ListNode<E> lastTail = tail;
        tail = newNode;
        if (lastTail == null) {
            head = newNode;
        } else {
            lastTail.next = newNode;
        }

        size++;
    }

    public E get(int index) {
        checkElementIndex(index);

        ListNode<E> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }

        return cur.item;
    }

    public E set(int index, E newElement) {
        checkElementIndex(index);

        ListNode<E> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        E oldElement = cur.item;
        cur.item = newElement;

        return oldElement;
    }

    private boolean checkElementIndex(int index) {
        if (index >= 0 && index < size) {
            return true;
        }
        throw new IndexOutOfBoundsException();
    }

    private E unlink(ListNode<E> node) {
        E element = node.item;
        final ListNode<E> prevNode = node.prev;
        final ListNode<E> nextNode = node.next;

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;

        return element;
    }

    public E remove(int index) {
        checkElementIndex(index);

        ListNode<E> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }

        return unlink(cur);
    }

    public E remove(Object o) {
        if (o == null) {
            throw new NoSuchElementException();
        }

        for (ListNode<E> cur = head; head != null; head = head.next) {
            if (o.equals(cur.item)) {
                return unlink(cur);
            }
        }

        return null;
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println(list.contains(3));
        list.remove(2);
        System.out.println(list.contains(3));
        System.out.println(list.contains(4));
        list.remove(Integer.valueOf(4));
        System.out.println(list.contains(4));
    }
}