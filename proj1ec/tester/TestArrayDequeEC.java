package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {

    @Test
    public void testArrayDeque() {
        ArrayDequeSolution<Integer> arrayDequeSolution = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> studentArrayDeque = new StudentArrayDeque<>();

        StringBuilder assertMessage = new StringBuilder();

        int N = 1000;
        for (int i = 0; i < N; i++) {
            // randomly call addFirst, addLast, removeFirst, removeLast.
            int operationNumber = StdRandom.uniform(0, 4);

            switch (operationNumber) {
                case 0 -> {
                    // do addFirst
                    int randVal = StdRandom.uniform(0, 100);
                    arrayDequeSolution.addFirst(randVal);
                    studentArrayDeque.addFirst(randVal);

                    assertMessage.append("addFirst(").append(randVal).append(")\n");
                }
                case 1 -> {
                    // do addLast.
                    int randVal = StdRandom.uniform(0, 100);
                    arrayDequeSolution.addLast(randVal);
                    studentArrayDeque.addLast(randVal);

                    assertMessage.append("addLast(").append(randVal).append(")\n");
                }
                case 2 -> {
                    if (arrayDequeSolution.size() == 0)
                        break;
                    // do removeLast, check the result.

                    assertMessage.append("removeLast()\n");
                    assertEquals(assertMessage.toString() ,arrayDequeSolution.removeLast(), studentArrayDeque.removeLast());
                }
                case 3 -> {
                    if (arrayDequeSolution.size() == 0)
                        break;
                    // do removeFirst, check the result.

                    assertMessage.append("removeFirst()\n");
                    assertEquals(assertMessage.toString(), arrayDequeSolution.removeFirst(), studentArrayDeque.removeFirst());
                }
            }
        }
    }

}
