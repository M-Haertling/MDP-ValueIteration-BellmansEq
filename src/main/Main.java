package main;

import java.io.FileNotFoundException;

/**
 * 
 * @author Michael
 */

public class Main {
    
    public static void main(String[] args) throws FileNotFoundException{
        
        String infile = args[2];
        double discountFactor = Double.parseDouble(args[3]);
//        String infile = "C:\\Users\\Michael\\Google Drive\\School\\UTD Year 4\\Intro To Machine Learning\\Markov Systems\\In-Out\\test2.in";
        
        MDP mdp = new MDP(infile);
//        System.out.println(mdp);
        MDPValueIterationTrainer train = new MDPValueIterationTrainer(mdp,discountFactor);
        train.doWeirdValueIterationThing();
    }
    
}
