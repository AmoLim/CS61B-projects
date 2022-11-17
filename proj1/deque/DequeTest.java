package deque;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;

public class DequeTest {

    @Test
    public void randomizedDequeTest() {
        Deque<Integer> arrayDeque = new ArrayDeque<>();
        Deque<Integer> linkedListDeque = new LinkedListDeque<>();

        java.util.Deque<Integer> rightDeque = new java.util.ArrayDeque<>();

        int N = 400;
        for (int i = 0; i < 400; i++) {
            int operationNumber = StdRandom.uniform(0 , 5);

            switch (operationNumber) {
                case 0 -> {
                    int randVal = StdRandom.uniform(-100, 100);
                    rightDeque.addFirst(randVal);
                    arrayDeque.addFirst(randVal);
                    linkedListDeque.addFirst(randVal);
                }

                case 1 -> {
                    int randVal = StdRandom.uniform(-100, 100);
                    rightDeque.addLast(randVal);
                    arrayDeque.addLast(randVal);
                    linkedListDeque.addLast(randVal);
                }

                case 2 -> {
                    // check isEmpty.
                    assertEquals(rightDeque.isEmpty(), arrayDeque.isEmpty());
                    assertEquals(rightDeque.isEmpty(), linkedListDeque.isEmpty());
                }

                case 3 -> {
                    if (rightDeque.isEmpty()) {
                        break;
                    }
                    // call removeFirst.
                    Integer firstItem = rightDeque.removeFirst();
                    assertEquals(firstItem, arrayDeque.removeFirst());
                    assertEquals(firstItem, linkedListDeque.removeFirst());
                }

                case 4 -> {
                    if (rightDeque.isEmpty()) {
                        break;
                    }
                    // call removeLast.
                    Integer lastItem = rightDeque.removeLast();
                    assertEquals(lastItem, arrayDeque.removeLast());
                    assertEquals(lastItem, linkedListDeque.removeLast());
                }
            }
        }

    }
}
