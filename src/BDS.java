import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BDS {
    public static void search(State initialState) {
        Graph graph = initialState.getGraph().copy();
        for (int i = 0; i < initialState.getGraph().size(); i++)
            graph.getNode(i).changeColorTo(Color.Green);
        State goalState = new State(graph, -1, null);
        Queue<State> frontier = new LinkedList<State>();
        Queue<State> frontier2 = new LinkedList<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        Hashtable<String, Boolean> inFrontier2 = new Hashtable<>();
        Hashtable<String, Boolean> explored2 = new Hashtable<>();
        frontier.add(initialState);
        frontier2.add(goalState);
        inFrontier.put(initialState.hash(), true);
        inFrontier2.put(goalState.hash(), true);
        ArrayList<State> exp1 = new ArrayList<>();
        ArrayList<State> exp2 = new ArrayList<>();
        while (!frontier.isEmpty() && !frontier2.isEmpty()) {
            State tempState = frontier.poll();
            State tempState2 = frontier2.poll();
            inFrontier.remove(tempState.hash());
            inFrontier2.remove(tempState2.hash());
            exp1.add(tempState);
            exp2.add(tempState2);
            explored.put(tempState.hash(), true);
            explored2.put(tempState2.hash(), true);
            ArrayList<State> children = tempState.successor();
            ArrayList<State> children2 = tempState2.successor();
            for (int i = 0; i < children.size(); i++) {
                if (!(inFrontier.containsKey(children.get(i).hash()))
                        && !(explored.containsKey(children.get(i).hash()))) {
                    if (inFrontier2.containsKey(children.get(i).hash())) {
                        while (!frontier2.isEmpty()) {
                            State s = frontier2.poll();
                            if (s.equals(children.get(i))) {
                                result(children.get(i), s);
                                result(children.get(i), "BDSfrominit");
                                result(s, "BDSfromgoal");
                                return;
                            }
                        }
                    } else if (explored2.containsKey(children.get(i).hash())) {
                        result(children.get(i), exp2.get(exp2.indexOf(children.get(i))));
                        result(children.get(i), "BDSfrominit");
                        result(exp2.get(exp2.indexOf(children.get(i))), "BDSfromgoal");
                        return;
                    }
                    frontier.add(children.get(i));
                    inFrontier.put(children.get(i).hash(), true);
                }
            }
            for (int i = 0; i < children2.size(); i++) {
                if (!(inFrontier2.containsKey(children2.get(i).hash()))
                        && !(explored2.containsKey(children2.get(i).hash()))) {
                    if (inFrontier.containsKey(children2.get(i).hash())) {
                        while (!frontier.isEmpty()) {
                            State s = frontier.poll();
                            if (s.equals(children2.get(i))) {
                                result(s, "BDSfrominit");
                                result(children2.get(i), "BDSfromgoal");
                                result(s, children2.get(i));
                                return;
                            }
                        }
                    } else if (explored.containsKey(children2.get(i).hash())) {
                        result(exp1.get(exp1.indexOf(children2.get(i))), "BDSfrominit");
                        result(children2.get(i), "BDSfromgoal");
                        result(exp1.get(exp1.indexOf(children2.get(i))), children2.get(i));
                        return;
                    }
                    frontier2.add(children2.get(i));
                    inFrontier2.put(children2.get(i).hash(), true);
                }
            }
        }
    }

    private static void result(State state, String name) {
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
            FileWriter myWriter = new FileWriter(name + ".txt");
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

    private static void result(State state, State state2) {
        Stack<State> states = new Stack<State>();
        while (true) {
            states.push(state);
            if (state.getParentState() == null) {
                break;
            } else {
                state = state.getParentState();
            }
        }
        Queue<State> queue = new LinkedList<State>();
        while (!states.isEmpty())
            queue.add(states.pop());
        int ee = state2.getSelectedNodeId();
        state2 = state2.getParentState();
        while (state2 != null) {
            queue.add(new State(state2.getGraph(), ee, state2.getParentState()));
            ee = state2.getSelectedNodeId();
            state2 = state2.getParentState();
        }
        try {
            FileWriter myWriter = new FileWriter("BDSResult.txt");
            System.out.println("initial state : ");
            while (!queue.isEmpty()) {
                State tempState = queue.poll();
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
