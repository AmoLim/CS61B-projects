package deque;

public class ArrayDeque<T> {
    /**
     * nextFirst always points at the position where the next item will be added to the front of Deque.
     * nextLast always points at the position where the next item will be added to the back of Deque.
     */
    private int nextFirst;
    private int nextLast;

    /** size always equals to the number of items in Deque. */
    private int size;

    private T[] array;

    public ArrayDeque() {
        array = (T[]) new Object[20];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }


    private void resize(int capability) {
        T[] new_array = (T[]) new Object[capability];

    }
}
