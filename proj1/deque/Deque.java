package deque;

public interface Deque<T> {
    /** Add an item to the front of the Deque. */
    void addFirst(T item);

    /** Add an item to the last of the Deque. */
    void addLast(T item);

    /** Returns true if the size of Deque is zero. */
    default boolean isEmpty() {
        return size() == 0;
    }

    /** Returns the size of the Deque. */
    int size();

    /** Print out the whole deque. */
    void printDeque();

    /** Removes and returns the first item of deque. */
    T removeFirst();

    /** Removes and returns the last item of deque. */
    T removeLast();

    /** Returns the item at index. */
    T get(int index);
}
