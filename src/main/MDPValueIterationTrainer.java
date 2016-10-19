package main;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//@author Michael Haertling
public class MDPValueIterationTrainer {

    MDP mdp;
    ArrayList<ArrayList<Double>> jMatrix;
    double gamma;

    public MDPValueIterationTrainer(double g) {
        gamma = g;
    }

    public MDPValueIterationTrainer(String filename, double g) throws FileNotFoundException {
        this(g);
        mdp = new MDP(filename);
        jMatrix = new ArrayList<>();
    }

    public MDPValueIterationTrainer(MDP mdp, double g) {
        this(g);
        this.mdp = mdp;
    }

//    public HashMap<String, String> findOptimalPolicy() {
//        boolean go = true;
//        int numIterations = 0;
//
//        //Create the default policy
//        HashMap<String, String> policy = new HashMap<>();
//        String defaultState = "a1";
//        for (String name : mdp.getStateNames()) {
//            policy.put(name, defaultState);
//        }
//
//        //Begin the master loop
//        while (go) {
//            numIterations++;
//
//            //Find optimal J values for MS defined by policy
//            ArrayList<double[]> js = valueIteration(policy);
//            double[] jstar = js.get(js.size() - 1);
//
//            //Print the J values
////            for (double[] j : js) {
////                System.out.println(Arrays.toString(j));
////            }
//
//            //Find the optimal policy for the MS (Bellman's EQ)
//            HashMap<String, String> newPolicy = new HashMap<>();
//            ArrayList<String> outs = new ArrayList<>();
//
//            //For each state find the action to take
//            for (String stateName : mdp.getStateNames()) {
//                double valueMax = 0;
//                String argMax = null;
//
//                //For each action find the value
//                MDPState state = mdp.getState(stateName);
//                for (String action : state.getActions()) {
//                    ArrayList<ArrayList> actionMatrix = state.getActionMatrix(action); //0 = destination 1 = probability 
//                    double value = 0;
//                    //Iterate over possible states from the action
//                    for (int i = 0; i < actionMatrix.get(0).size(); i++) {
//                        String nextState = (String) actionMatrix.get(0).get(i);
//                        double probability = (double) actionMatrix.get(1).get(i);
//                        int nextStateIndex = mdp.getStateNames().indexOf(nextState);
//                        double jNextState = jstar[nextStateIndex];
//                        value += probability * jNextState;
//                    }
//                    if (value > valueMax || argMax == null) {
//                        valueMax = value;
//                        argMax = action;
//                    }
//                }
//
//                //Add the print content
//                outs.add(" (" + stateName + " " + argMax + " " + jstar[mdp.getStateNames().indexOf(stateName)] + ")");
//
//                //Add to the policy
//                newPolicy.put(stateName, argMax);
//            }
//
//            //Print what has been done
//            String out = "Afer iteration " + numIterations + ":";
//            for (String o : outs) {
//                out += o;
//            }
//            System.out.println(out);
//
//            policy = newPolicy;
//
//            //Only iterate 20 times
//            if (numIterations == 20) {
//                go = false;
//            }
//        }
//
//        return policy;
//
//    }
//
//    public ArrayList<double[]> valueIteration(HashMap<String, String> policy) {
//
////        System.out.println("Beginning value iteration.");
//        ArrayList<double[]> js = new ArrayList<>();
//
//        //Place the first row, which is just the rewards
//        double[] j = new double[mdp.getNumStates()];
//        int index = 0;
//        for (String sname : mdp.getStateNames()) {
//            MDPState state = mdp.getState(sname);
//            j[index] = state.getReward();
//            index++;
//        }
//        js.add(j);
//
////        System.out.println("Generated first row.");
//        //Begin iteration
//        boolean go = true;
//        int iterations = 0;
//        while (go) {
////            System.out.println("Iteration: "+iterations);
//            j = new double[mdp.getNumStates()];
//            index = 0;
//            //Iterate over the states
//            for (String sname : mdp.getStateNames()) {
//
////                System.out.println("State: "+sname);
//                //Ji+1 = r + g sum(Pi*Ji)
//                MDPState state = mdp.getState(sname);
//                double value = 0;
//
////                System.out.println(policy.get(sname));
//                ArrayList<ArrayList> am = state.getActionMatrix(policy.get(sname));// 0 = destination 1 = probability
//                double[] previousJ = js.get(js.size() - 1);
//                for (int i = 0; i < am.get(0).size(); i++) {
//                    String nextState = (String) am.get(0).get(i);
//                    int nextStateIndex = mdp.getStateNames().indexOf(nextState);
//                    double probability = (double) am.get(1).get(i);
//                    value += probability * previousJ[nextStateIndex];
//                }
//                value *= gamma;
//
//                value += mdp.getReward(sname);
//
//                j[index] = value;
//                index++;
//            }
//            js.add(j);
//            iterations++;
//            if (iterations == 20) {
//                go = false;
//            }
//        }
//
//        return js;
//
//    }

    public void doWeirdValueIterationThing() {

        //Create the default policy
        HashMap<String, String> policy = null;
//        for (String name : mdp.getStateNames()) {
//            policy.put(name, mdp.getState(name).getFirstAction());
//        }

        double[] jp = null; //previous j
        double[] jc; //current j


//        System.out.println("Generated first row.");
        //Begin iteration
        boolean go = true;
        int iterations = 1;
        while (go) {
//            System.out.println("Iteration: "+iterations);
            jc = new double[mdp.getNumStates()];
            int index = 0;
            //Iterate over the states
            if (jp == null) {
                //First iteration
                for (String sname : mdp.getStateNames()) {
                    MDPState state = mdp.getState(sname);
                    jc[index] = state.getReward();
                    index++;
                }
            } else {
                for (String sname : mdp.getStateNames()) {
//                System.out.println("State: "+sname);
                    //Ji+1 = r + g sum(Pi*Ji)
                    MDPState state = mdp.getState(sname);
                    double value = 0;

//                System.out.println(policy.get(sname));
                    ArrayList<ArrayList> am = state.getActionMatrix(policy.get(sname));// 0 = destination 1 = probability
                    for (int i = 0; i < am.get(0).size(); i++) {
                        String nextState = (String) am.get(0).get(i);
                        int nextStateIndex = mdp.getStateNames().indexOf(nextState);
                        double probability = (double) am.get(1).get(i);
                        value += probability * jp[nextStateIndex];
                    }
                    value *= gamma;

                    value += mdp.getReward(sname);

                    jc[index] = value;
                    index++;
                }
            }

//            System.out.println(Arrays.toString(jc));
            jp = jc;

            //Re-evaluate the policy
            //Find the optimal policy for the MS (Bellman's EQ)
            HashMap<String, String> newPolicy = new HashMap<>();
            ArrayList<String> outs = new ArrayList<>();

            //For each state find the action to take
            for (String stateName : mdp.getStateNames()) {
                double valueMax = 0;
                String argMax = null;
                
//                System.out.print(stateName+" ");
                
                //For each action find the value
                MDPState state = mdp.getState(stateName);
                for (String action : state.getActions()) {
                    ArrayList<ArrayList> actionMatrix = state.getActionMatrix(action); //0 = destination 1 = probability 
                    double value = 0;
                    //Iterate over possible states from the action
                    for (int i = 0; i < actionMatrix.get(0).size(); i++) {
                        String nextState = (String) actionMatrix.get(0).get(i);
                        double probability = (double) actionMatrix.get(1).get(i);
                        int nextStateIndex = mdp.getStateNames().indexOf(nextState);
                        double jNextState = jc[nextStateIndex];
                        value += probability * jNextState;
                    }
                    
//                    System.out.print(action+":"+new DecimalFormat("0.0000").format(value)+" ");
                    
                    if (value > valueMax || argMax == null) {
                        valueMax = value;
                        argMax = action;
                    }
                }

//                System.out.println("");
                
                //Add the print content
                DecimalFormat format = new DecimalFormat("0.0000");
//                format.setRoundingMode(RoundingMode.HALF_DOWN);
                outs.add("(" + stateName + " " + argMax + " " + format.format(jc[mdp.getStateNames().indexOf(stateName)]) + ") ");

                //Add to the policy
                newPolicy.put(stateName, argMax);
            }

            policy = newPolicy;

            //Print what has been done
            String out = "After iteration " + iterations + ":\n";
            for (String o : outs) {
                out += o;
            }

            System.out.println(out);

            iterations++;
            if (iterations > 20) {
                go = false;
            }
        }

    }

}
