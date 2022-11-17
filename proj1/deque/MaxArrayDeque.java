package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    /** Creates a MaxArrayDeque with the give Constructor.*/
    public MaxArrayDeque(Comparator<T> cmp) {
        super();
        comparator = cmp;
    }

    /**
     * returns the maximum element in the deque as governed by
     * the previously given Comparator.
     * If the MaxArrayDeque is empty, simply return null.
     */
    public T max() {
        return max(comparator);
    }

    /**
     * returns the maximum element in the deque as governed by the parameter
     * Comparator newComparator. If the MaxArrayDeque is empty, simply return null.
     */
    public T max(Comparator<T> newComparator) {
        if (this.size() == 0) {
            return null;
        }

        int maxIndex = 0;
        for (int i = 0; i < this.size(); i++) {
            if (newComparator.compare(get(maxIndex), get(i)) < 0) {
                maxIndex = i;
            }
        }

        return get(maxIndex);
    }
}
