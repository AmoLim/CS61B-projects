package randomizedtest;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> alst = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();

        alst.addLast(4); buggyAList.addLast(4);
        alst.addLast(5); buggyAList.addLast(5);
        alst.addLast(6); buggyAList.addLast(6);

        assertEquals(alst.removeLast(), buggyAList.removeLast());
        assertEquals(alst.removeLast(), buggyAList.removeLast());
        assertEquals(alst.removeLast(), buggyAList.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggyAList.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();

                assertEquals(size, buggyAList.size());
                System.out.println("size: " + size);
            } else if (operationNumber == 2 && L.size() > 0) {
                int lastItem = L.getLast();

                assertEquals(L.getLast(), buggyAList.getLast());
                System.out.println("getLast(" + lastItem + ")");
            } else if (operationNumber == 3 && L.size() > 0) {

                assertEquals(L.removeLast(), buggyAList.removeLast());
                System.out.println("removeLast.");
            }
        }
    }
}
