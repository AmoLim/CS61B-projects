package deque;

public class ArrayDeque<T> {
    /** nextFirst always points at the position where the next item will be added to the front of Deque. */
    private int nextFirst;

    /** nextLast always points at the position where the next item will be added to the back of Deque. */
    private int nextLast;

    /** size always equals to the number of items in Deque. */
    private int size;

    private T[] array;

    public ArrayDeque() {
        array = (T[]) new Object[8];
        nextFirst = array.length - 1;
        nextLast = 0;
        size = 0;
    }

    /** Return the number of items in Deque. */
    public int size() {
        return size;
    }

    /** Return true if size == 0. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Add item to the front of the list. */
    public void addFirst(T item) {
        if (array.length <= size) {
            resize(2 * array.length + 1);
        }
        array[nextFirst] = item;

        // Update invariants.
        size++;
        nextFirst--;
        if (nextFirst < 0) {
            nextFirst = array.length - 1;
        }
    }

    /** Add item to the back of the list. */
    public void addLast(T item) {
        if (array.length <= size) {
            resize(2 * array.length + 1);
        }
        array[nextLast] = item;

        // Update invariants.
        size++;
        nextLast++;
        if (nextLast >= array.length) {
            nextLast = 0;
        }
    }


    /** Remove the item at the front of the Deque. */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        int firstIndex = nextFirst + 1 >= array.length? 0: nextFirst + 1;
        // update invariants.
        nextFirst = firstIndex;
        size--;

        T firstItem = array[firstIndex];
        remove(firstIndex);
        return firstItem;
    }

    /** Remove the item at the back of the Deque. */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        int lastIndex = nextLast - 1 < 0? array.length - 1: nextLast - 1;
        // update invariants.
        nextLast = lastIndex;
        size--;

        T lastItem = array[lastIndex];
        remove(lastIndex);
        return lastItem;
    }

    /** Help method to remove the item at index, and check if deque needs to resie.*/
    private void remove(int index) {
        array[index] = null;

        if (array.length >= 16 && size <= array.length / 4) {
            resize(array.length / 2);
        }
    }

    /** Resizing the underlying array to target capability. */
    private void resize(int capability) {
        T[] new_array = (T[]) new Object[capability];

        int itr = nextFirst + 1 >= array.length? 0: nextFirst + 1;

        for (int i = 0; i < size; i++) {
            new_array[i] = array[itr];
            itr = itr + 1 >= array.length? 0: itr + 1;
        }

        array = new_array;
        nextLast = size;
        nextFirst = array.length - 1;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        int itr = nextFirst + 1 >= array.length? 0: nextFirst + 1;
        for (int i = 0; i < size; i++) {
            System.out.print(array[itr] + " ");
            itr = itr + 1 >= array.length? 0: itr + 1;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        deque.addFirst(0);
        deque.addFirst(1);
        deque.removeLast();

        deque.printDeque();
    }
}
