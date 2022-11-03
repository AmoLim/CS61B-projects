package deque;

public class LinkedListDeque<Item> {
    /** Basic structure of LinkedListDeque. */
    private class Node {
        public Item item;
        public Node prev;
        public Node next;

        public Node(Node p, Node n) {
            prev = p;
            next = n;
        }

        public Node(Item i, Node p, Node n) {
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

    public LinkedListDeque(Item itm) {
        sentinel = new Node(null, null);
        sentinel.next = new Node(itm, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

}
