package deque;

import org.junit.Test;

public class LinkedListDeque<T> {
    /** Basic structure of LinkedListDeque. */
    private class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node(Node p, Node n) {
            prev = p;
            next = n;
        }

        public Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    // Private data members.
    /**
     * Invariants about sentinel:
     *      1. The first item of the deque(if it exists) is always at sentinel.next.
     *      2. The last item of the deque(if it exists) is always at sentinel.prev.
     */
    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public LinkedListDeque(T itm) {
        sentinel = new Node(null, null);
        sentinel.next = new Node(itm, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T itm) {
        sentinel.next.prev = new Node(itm, sentinel, sentinel.next);
        sentinel.next = sentinel.next.prev;
        size++;
    }

    public void addLast(T itm) {
        sentinel.prev.next = new Node(itm, sentinel.prev, sentinel);;
        sentinel.prev = sentinel.prev.next;
        size++;
    }

    public void printDeque() {
        Node iterator = sentinel.next;
        while (iterator != sentinel) {
            System.out.print(iterator.item + " ");
            iterator = iterator.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T return_item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return return_item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T return_item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return return_item;
    }

    public T get(int index) {
        Node itr = sentinel.next;
        for (int i = 0; i < index; i++) {
            itr = itr.next;
        }
        return itr.item;
    }
    
}
