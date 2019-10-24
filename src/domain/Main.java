package domain;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.framework.SuccessorFunction;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main {

    private static State initState(String[] args) {
        int nest = Integer.parseInt(args[2]);
        int nbic = Integer.parseInt(args[3]);
        int dem  = Integer.parseInt(args[4]);
        int seed = Integer.parseInt(args[5]);
        int nVans = Integer.parseInt(args[6]);
        return new State(nest, nbic, dem, seed, nVans);
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
    }

    private static void printActions(List actions) {
        for (Object action1 : actions) {
            String action = action1.toString();
            System.out.println(action);
        }
    }

    public static void main(String[] args) throws Exception {
        // Initialize initialState, Random or Fixed
        State initialState = initState(args);
        /*(args[1].equals("r"))
            ? initialState.initRandom(Integer.parseInt(args[5]))
            : initialState.initFixed(Integer.parseInt(args[5]));*/
        initialState.initRandom(Integer.parseInt(args[5]));
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
            successor = new StateSuccessor();
        }
        else {
            search = new SimulatedAnnealingSearch(maxIt, numIt, k, lambda);
            successor = new SuccessorRandom();
        }

        // Initialize Problem. (AIMA)
        Problem problem = new Problem(initialState, successor, new Goal(), new Heuristic1());

        SearchAgent agent = new SearchAgent(problem, search);

        printInstrumentation(agent.getInstrumentation());
        printActions(agent.getActions());
    }
}
