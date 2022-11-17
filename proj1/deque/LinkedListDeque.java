package deque;

import org.junit.Test;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
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
    /*
     * Invariants about sentinel:
     *      1. The first item of the deque(if it exists) is always at sentinel.next.
     *      2. The last item of the deque(if it exists) is always at sentinel.prev.
     */
    private Node sentinel;

    /*
     * Invariants about size:
     * The size always equals to the number of items in Deque.
     */
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

    /** Return the number of items in Deque. */
    @Override
    public int size() {
        return size;
    }

    /** Add an item at the front of the Deque. */
    @Override
    public void addFirst(T itm) {
        sentinel.next.prev = new Node(itm, sentinel, sentinel.next);
        sentinel.next = sentinel.next.prev;
        size++;
    }

    /** Add an item at the end of the Deque. */
    @Override
    public void addLast(T itm) {
        sentinel.prev.next = new Node(itm, sentinel.prev, sentinel);;
        sentinel.prev = sentinel.prev.next;
        size++;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.*/
    @Override
    public void printDeque() {
        Node iterator = sentinel.next;
        while (iterator != sentinel) {
            System.out.print(iterator.item + " ");
            iterator = iterator.next;
        }
        System.out.println();
    }

    /** Remove and return the item at the front of the Deque. */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T return_item = sentinel.next.item;
        sentinel.next.item = null;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return return_item;
    }

    /** Remove and return the item at the end of the Deque. */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T return_item = sentinel.prev.item;
        sentinel.prev.item = null;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return return_item;
    }

    /** Returns the item at the given index,
     * where 0 is the front, 1 is the next item, and so forth. */
    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }

        Node itr = sentinel.next;
        for (int i = 0; i < index; i++) {
            itr = itr.next;
        }
        return itr.item;
    }

    /** Recursive version of get.
     * Returns the item at the given index. */
    public T getRecursive(int index) {
        return getRecursive(index, sentinel.next).item;
    }

    /** Help private method for getRecursive. */
    private Node getRecursive(int index, Node current) {
        if (index == 0)
            return current;
        return getRecursive(index - 1, current);
    }

    /** Returns an iterator of linked list deque. */
    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    public class LinkedListDequeIterator implements Iterator<T> {
        private Node currentNode;

        public LinkedListDequeIterator() {
            currentNode = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return  currentNode != sentinel;
        }

        @Override
        public T next() {
            T returnItem = currentNode.item;
            currentNode = currentNode.next;
            return returnItem;
        }
    }


    /** Override toString method for LinkedListDeque.
     *  Returns a string of all the items in the form of { item1, item2, ...}. */
    @Override
    public String toString() {
        StringBuilder lldStringBuilder = new StringBuilder("{ ");
        for (T item : this) {
            lldStringBuilder.append(item.toString());
            lldStringBuilder.append(", ");
        }
        lldStringBuilder.delete(lldStringBuilder.length() - 2, lldStringBuilder.length() - 1);
        lldStringBuilder.append("}");
        return lldStringBuilder.toString();
    }



    /**
     * Equals method for LinkedListDeque.
     * @param obj another compare object.
     * @return true if two list are equal to each other.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof LinkedListDeque otherDeque) {
            // check dequeues are of the same size.
            if (otherDeque.size != this.size) {
                return false;
            }

            // check that all the items are equal to each other.
            Iterator itrOfOtherDeque = otherDeque.iterator();
            for (T item : this) {
                if (!item.equals(itrOfOtherDeque.next())) {
                    return false;
                }
            }

            // pass
            return true;
        }
        // obj is not a LinkedListDeque return false.
        return false;
    }


}
