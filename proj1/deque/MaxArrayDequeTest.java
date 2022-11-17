package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayDeque;
import java.util.Comparator;

public class MaxArrayDequeTest {

    /** Randomized test for max() method. */
    @Test
    public void maxArrayDequeTest() {
        Comparator<Integer> integerComparator = Comparator.comparingInt(o -> o);

        Comparator<Integer> secondComparator = (o1, o2) -> o2 - o1;

        MaxArrayDeque<Integer> maxArrayDeque = new MaxArrayDeque<>(integerComparator);
        ArrayDeque<Integer> correctDeque = new ArrayDeque<>();

        int N = 400;
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0, 7);

            switch (operationNumber) {
                case 0 -> {
                    // add an integer to the back of ArrayDeque.
                    int randVal = StdRandom.uniform(0, 100);
                    maxArrayDeque.addLast(randVal);
                    correctDeque.addLast(randVal);
                }
                case 1 -> {
                    // add an integer to the front of ArrayDeque.
                    int randVal = StdRandom.uniform(0, 100);
                    maxArrayDeque.addFirst(randVal);
                    correctDeque.addFirst(randVal);
                }

                case 2 -> {
                    // check size
                    assertEquals(correctDeque.size(), maxArrayDeque.size());
                }

                case 3 -> {
                    if (correctDeque.isEmpty()) {
                        break;
                    }
                    // call removeFirst.
                    assertEquals(correctDeque.removeFirst(), maxArrayDeque.removeFirst());
                }

                case 4 -> {
                    if (correctDeque.isEmpty()) {
                        break;
                    }
                    // call removeLast.
                    assertEquals(correctDeque.removeLast(), maxArrayDeque.removeLast());
                }

                case 5 -> {
                    // check maximum by ascending order.
                    assertEquals(findMaxInDeque(correctDeque, integerComparator), maxArrayDeque.max());
                }

                case 6 -> {
                    // check maximum by descending order.
                    assertEquals(findMaxInDeque(correctDeque, secondComparator), maxArrayDeque.max(secondComparator));
                }
            }
        }
    }

    /** Help method to find the maximum in a correct ArrayDeque. */
    private Integer findMaxInDeque(ArrayDeque<Integer> deque, Comparator<Integer> cmp) {
        if (deque.isEmpty()) {
            return null;
        }

        Integer maxVal = deque.getFirst();

        for (Integer item : deque) {
            if (cmp.compare(maxVal, item) < 0) {
                maxVal = item;
            }
        }

        return maxVal;
    }
}
