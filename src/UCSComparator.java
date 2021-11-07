import java.util.Comparator;

public class UCSComparator implements Comparator<State> {
    @Override
    public int compare(State o1, State o2) {
        int n1 = 0, n2 = 0;
        switch (o2.getGraph().getNode(o2.getSelectedNodeId()).getColor()) {
            case Red -> n1 = 1;
            case Black -> n1 = 2;
            case Green -> n1 = 3;
        }
        switch (o1.getGraph().getNode(o1.getSelectedNodeId()).getColor()) {
            case Red -> n2 = 1;
            case Black -> n2 = 2;
            case Green -> n2 = 3;
        }
        return n1 > n2 ? 1 : -1;
    }
}
