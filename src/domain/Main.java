package domain;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.framework.SuccessorFunction;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class Main {

    private static State initializeState(String[] args) {
        int nest = Integer.parseInt(args[2]);
        int nbic = Integer.parseInt(args[3]);
        int dem  = Integer.parseInt(args[4]);
        int seed = Integer.parseInt(args[5]);
        int nVans = Integer.parseInt(args[6]);
        return new State(nest, nbic, dem, seed, nVans);
    }
    public static void main(String[] args) throws Exception {
        // Initialize initialState, Random or Fixed
        State initialState = initializeState(args);
        (args[1].equals("r"))
            ? initialState.initalizeRandom()
            : initialState.initalizeFixed();

        // Only for Simulated Annealing Search
        int maxIt  = Integer.parseInt(args[7]);
        int numIt  = Integer.parseInt(args[8]);
        int k      = Integer.parseInt(args[9]);
        int lambda = Integer.parseInt(args[10]);

        // Initialize Search. (AIMA) and initialize Successor
        Search search;
        SuccessorFunction successor;
        if (args[0].equals("h")) {
            search = new HillClimbingSearch();
            successor = new Successor();
        }
        else {
            search = new SimulatedAnnealingSearch(maxIt, numIt, k, lambda);
            successor = new SuccessorRandom();
        }

        // Initialize Problem. (AIMA)
        Problem problem = new Problem(initialState, successor, new Goal());

        SearchAgent agent = new SearchAgent(problem, search);

        //print action i printIntrumentation
    }
}
