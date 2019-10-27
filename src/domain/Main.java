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
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;


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
        out.println();
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + ": " + property);
        }
    }

    private static void printActions(List actions) {
        out.println();
        for (Object action1 : actions) {
            String action = action1.toString();
            System.out.println(action);
        }
    }

    private static void printElapsedTime(final long start, final long end) {
        long elapsedTime = end - start; // in nanoseconds
        out.println();
        out.print("Elapsed time (ms):\t"); out.println(TimeUnit.NANOSECONDS.toMillis(elapsedTime));
        out.print("Elapsed time (s): \t"); out.println(TimeUnit.NANOSECONDS.toSeconds(elapsedTime));
        out.print("Elapsed time (m): \t"); out.println(TimeUnit.NANOSECONDS.toMinutes(elapsedTime));
        out.print("Elapsed time (h): \t"); out.println(TimeUnit.NANOSECONDS.toHours(elapsedTime));
    }

    public static void main(String[] args) throws Exception {
        // Initialize initialState, Random or Fixed
        State initialState = initState(args);
        if ((args[1].equals("r"))) {
            initialState.initRandom(Integer.parseInt(args[5]));
        } else if ((args[1].equals("f"))) {
            initialState.initFixed();
        } else {
            initialState.initRandomFixed(Integer.parseInt(args[5]));
        }

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
        Problem problem = new Problem(initialState, successor, new StateGoal(), new HeuristicMaxDemandSupplied());

        long startTime = System.nanoTime();
        SearchAgent agent = new SearchAgent(problem, search);
        long endTime = System.nanoTime();

        Main.printInstrumentation(agent.getInstrumentation());
        Main.printActions(agent.getActions());
        Main.printElapsedTime(startTime, endTime);

    }

}
