import java.util.Comparator;

public class UCSComparator implements Comparator<State> {
    @Override
    public int compare(State o1, State o2) {
        int n1 = 0, n2 = 0;
        switch (o1.getGraph().getNode(o1.getSelectedNodeId()).getColor()) {
            case Red -> n1 = 1;
            case Black -> n1 = 2;
            case Green -> n1 = 3;
        }
        switch (o2.getGraph().getNode(o2.getSelectedNodeId()).getColor()) {
            case Red -> n2 = 1;
            case Black -> n2 = 2;
            case Green -> n2 = 3;
        }
        if (o1.getParentState() != null)
            n1 += o1.getParentState().getCost();
        if (o2.getParentState() != null)
            n2 += o2.getParentState().getCost();
        o1.setCost(n1);
        o2.setCost(n2);
        return n1 < n2 ? 1 : -1;
    }
}
