import java.util.Comparator;

public class AComparator implements Comparator<State> {
    @Override
    public int compare(State o1, State o2) {
        return o1.h_n() + o1.g_n() > o2.h_n() + o1.g_n() ? 1 : -1;
    }
}
