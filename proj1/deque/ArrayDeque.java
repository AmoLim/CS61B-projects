package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T> , Deque<T> {
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
    @Override
    public int size() {
        return size;
    }

    /** Returns the item at the given index, where 0 is the front, 1 is the next item,
     *  and so forth. If no such item exists, returns null. */
    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }

        return array[getPosAfter(nextFirst, index + 1)];
    }

    /** Add item to the front of the list. */
    @Override
    public void addFirst(T item) {
        if (array.length <= size) {
            resize(2 * array.length + 1);
        }
        array[nextFirst] = item;

        // Update invariants.
        size++;
        nextFirst = getPosBefore(nextFirst, 1);
    }

    /** Add item to the back of the list. */
    @Override
    public void addLast(T item) {
        if (array.length <= size) {
            resize(2 * array.length + 1);
        }
        array[nextLast] = item;

        // Update invariants.
        size++;
        nextLast = getPosAfter(nextLast, 1);
    }


    /** Remove the item at the front of the Deque. */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        int firstIndex = getPosAfter(nextFirst, 1);
        // update invariants.
        nextFirst = firstIndex;
        size--;

        T firstItem = array[firstIndex];
        remove(firstIndex);
        return firstItem;
    }

    /** Remove the item at the back of the Deque. */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        int lastIndex = getPosBefore(nextLast, 1);
        // update invariants.
        nextLast = lastIndex;
        size--;

        T lastItem = array[lastIndex];
        remove(lastIndex);
        return lastItem;
    }

    /** Internal help method to remove the item at index, and check if deque needs to resie.*/
    private void remove(int index) {
        array[index] = null;

        if (array.length >= 16 && size <= array.length / 4) {
            resize(array.length / 2);
        }
    }

    /** Resizing the underlying array to target capability. */
    private void resize(int capability) {
        T[] new_array = (T[]) new Object[capability];

        int itr = getPosAfter(nextFirst, 1);

        for (int i = 0; i < size; i++) {
            new_array[i] = array[itr];
            itr = getPosAfter(itr, 1);
        }

        array = new_array;
        nextLast = size;
        nextFirst = array.length - 1;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    @Override
    public void printDeque() {
        int itr = nextFirst + 1 >= array.length? 0: nextFirst + 1;
        for (int i = 0; i < size; i++) {
            System.out.print(array[itr] + " ");
            itr = getPosAfter(itr, 1);
        }
        System.out.println();
    }

    /** Internal method to return the position after the current position with index. */
    private int getPosAfter(int pos, int index) {
        return pos + index >= array.length? pos + index - array.length: pos + index;
    }

    /** Internal method to return the position before the current position with index. */
    private int getPosBefore(int pos, int index) {
        return pos - index < 0? array.length - index: pos - index;
    }

    /** Get an iterator of Array deque. */
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    public class ArrayDequeIterator implements Iterator<T> {
        private int currentPos;

        public ArrayDequeIterator() {
            currentPos = getPosAfter(nextFirst, 1);
        }

        @Override
        public boolean hasNext() {
            int nextPos = getPosAfter(currentPos, 1);
            return nextPos != nextLast;
        }

        @Override
        public T next() {
            T returnItem = array[currentPos];
            currentPos = getPosAfter(currentPos, 1);
            return returnItem;
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof ArrayDeque otherDeque) {
            // check dequeues are of the same size.
            if (this.size != otherDeque.size) {
                return false;
            }

            // check if items are equals to each other.
            Iterator itrOfOtherDeque = otherDeque.iterator();
            for (T item: this) {
                if (!item.equals(itrOfOtherDeque.next())) {
                    return false;
                }
            }

            // pass.
            return true;
        }
        // obj is not an ArrayDeque, returns false.
        return false;
    }

}
