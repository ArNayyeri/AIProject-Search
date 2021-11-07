import java.util.Comparator;

public class GBFSComparator implements Comparator<State> {
    @Override
    public int compare(State o1, State o2) {
        return o1.h_n() > o2.h_n() ? 1 : -1;
    }
}
