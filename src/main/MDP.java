package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

//@author Michael Haertling
public class MDP {

    HashMap<String, MDPState> states;
    ArrayList<String> stateNames;

    public MDP(String filepath) throws FileNotFoundException {
        states = new HashMap<>();
        stateNames = new ArrayList<>();
        
        File file = new File(filepath);
        Scanner in = new Scanner(file);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] values = line.replaceAll("[()]", "").split(" ");
            String stateName = values[0];
            double reward = Double.parseDouble(values[1]);
            
            MDPState state = new MDPState(reward);
            states.put(stateName, state);
            stateNames.add(stateName);
            
            //Gather all the transitions
            for(int i=2;i<values.length;i+=3){
                state.addTransition(values[i], values[i+1], Double.parseDouble(values[i+2]));
            }
        }
    }
    
    public double getReward(String stateName){
        return getState(stateName).getReward();
    }
    
    public MDPState getState(String name){
        return states.get(name);
    }
    
    public ArrayList<String> getStateNames(){
        return stateNames;
    }

    public int getNumStates(){
        return stateNames.size();
    }
    
    public Collection<MDPState> getStates(){
        return states.values();
    }
    
    @Override
    public String toString(){
        String out = "";
        for(String sname:states.keySet()){
            out += "**"+sname+"**\n";
            out += states.get(sname).toString()+"\n\n";
        }
        return out;
    }

}
