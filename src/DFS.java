import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class DFS {
    public static void search(State initialState) {
        if (isGoal(initialState)) {
            result(initialState);
            return;
        }
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        inFrontier.put(initialState.hash(), true);
        recursive(initialState, inFrontier, explored);
    }

    private static void recursive(State state, Hashtable<String, Boolean> inFrontier
            , Hashtable<String, Boolean> explored) {
        inFrontier.remove(state.hash());
        explored.put(state.hash(), true);
        ArrayList<State> children = state.successor();
        for (int i = 0; i < children.size(); i++) {
            if (!(inFrontier.containsKey(children.get(i).hash()))
                    && !(explored.containsKey(children.get(i).hash()))) {
                if (isGoal(children.get(i))) {
                    result(children.get(i));
                    return;
                }
                inFrontier.put(children.get(i).hash(), true);
                recursive(children.get(i), inFrontier, explored);
            }
        }
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
            FileWriter myWriter = new FileWriter("DFSResult.txt");
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
