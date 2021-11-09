import java.util.Comparator;

public class AComparator implements Comparator<State> {
    @Override
    public int compare(State o1, State o2) {
        return o1.f_n() > o2.f_n() ? 1 : -1;
    }
}
