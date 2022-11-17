package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList<Integer> Ns = new AList<>(), opCounts = new AList<>();
        AList<Double> times = new AList<>();

        int kOpCounts = 10000;
        for (int i = 1000; i < 140000; i *= 2) {
            Ns.addLast(i);
            opCounts.addLast(kOpCounts);
            times.addLast(timeSLListTest(i, kOpCounts));
        }

        printTimingTable(Ns, times, opCounts);
    }

    /** Return the running time of the test with n elements, m accesses. */
    private static double timeSLListTest(int n, int m) {
        SLList<Integer> slList = new SLList<>();

        for (int i = 0; i < n; i++) {
            slList.addLast(i);
        }

        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < m; i++) {
            slList.getLast();
        }
        return sw.elapsedTime();
    }

}
