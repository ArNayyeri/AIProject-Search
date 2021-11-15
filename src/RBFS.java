import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class RBFS {
    public static void search(State initialState) {
        if (isGoal(initialState)) {
            result(initialState);
            return;
        }
        recursive(initialState, Integer.MAX_VALUE);
    }

    private static byte recursive(State state, int minValue) {
        ArrayList<State> children = state.successor();
        while (!children.isEmpty()) {
            children.sort(new AComparator());
            State child = children.get(0);
            if (child.f_n() > minValue) {
                state.setCost(child.f_n());
                return 0;
            } else {
                if (isGoal(child)) {
                    result(child);
                    return 1;
                }
                byte isResult = recursive(child, Math.min(children.get(1).f_n(), minValue));
                if (isResult == 1)
                    return 1;
                else if (isResult == -1)
                    children.remove(0);
            }
        }
        return -1;
    }

    private static boolean isGoal(State state) {
        for (int i = 0; i < state.getGraph().size(); i++) {
            if (state.getGraph().getNode(i).getColor() == Color.Red
                    || state.getGraph().getNode(i).getColor() == Color.Black) {
                return false;
            }
        }
        return true;
    }

    private static void result(State state) {
        Stack<State> states = new Stack<State>();
        while (true) {
            states.push(state);
            if (state.getParentState() == null) {
                break;
            } else {
                state = state.getParentState();
            }
        }
        try {
            FileWriter myWriter = new FileWriter("RBFSResult.txt");
            System.out.println("initial state : ");
            while (!states.empty()) {
                State tempState = states.pop();
                if (tempState.getSelectedNodeId() != -1) {
                    System.out.println("selected id : " + tempState.getSelectedNodeId());
                }
                tempState.getGraph().print();

                myWriter.write(tempState.getSelectedNodeId() + " ,");
                myWriter.write(tempState.outputGenerator() + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
