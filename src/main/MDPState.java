package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

//@author Michael Haertling
public class MDPState {

    double reward;
    HashMap<String, ArrayList<ArrayList>> actions;
    ArrayList<String> actionNames;

    public MDPState(double reward) {
        this.reward = reward;
        actions = new HashMap<>();
        actionNames = new ArrayList<>();
    }

    public void addTransition(String name, String nextState, double probability) {
        if (!actionNames.contains(name)) {
            actionNames.add(name);
        }
        ArrayList<ArrayList> transitions = actions.get(name);
        if (transitions == null) {
            transitions = new ArrayList<>();
            //destinations
            transitions.add(new ArrayList<String>());
            //probabilities
            transitions.add(new ArrayList<Double>());
            //add to the map
            actions.put(name, transitions);
        }

        //Add the transition
        transitions.get(0).add(nextState);
        transitions.get(1).add(probability);

    }

    public ArrayList<ArrayList> getActionMatrix(String actionName) {
        return actions.get(actionName);
    }

    public Collection<String> getActions() {
        return actionNames;
    }

    public double getReward() {
        return reward;
    }

    public String getFirstAction() {
        return actionNames.get(0);
    }

    @Override
    public String toString() {
        String out = "";
        out += "------------------------\n";
        out += "|Reward: " + reward + "\n";
        for (String aname : actions.keySet()) {
            out += "|Action: " + aname + "\n";
            for (ArrayList list : actions.get(aname)) {
                out += "|" + list.toString() + "\n";
            }
        }
        out += "------------------------";
        return out;
    }

}
