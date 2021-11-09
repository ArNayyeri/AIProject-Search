import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Stack;

public class IDA {
    public static void search(State initialState) {
        if (isGoal(initialState)) {
            result(initialState);
            return;
        }
        Hashtable<String, Boolean> explored = new Hashtable<>();
        for (int i = initialState.h_n(); !recursive(initialState, explored, i); i++) {
            explored = new Hashtable<>();
        }
    }

    private static boolean recursive(State state, Hashtable<String, Boolean> explored, int cutoff) {
        explored.put(state.hash(), true);
        PriorityQueue<State> children = new PriorityQueue<>(new AComparator());
        children.addAll(state.successor());
        while (!children.isEmpty()) {
            State child = children.poll();
            if (!(explored.containsKey(child.hash())) && (child.f_n() <= cutoff)) {
                if (isGoal(child)) {
                    result(child);
                    return true;
                }
                boolean isResult = recursive(child, explored, cutoff);
                if (isResult)
                    return true;
            }
        }
        return false;
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
            FileWriter myWriter = new FileWriter("IDAResult.txt");
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
