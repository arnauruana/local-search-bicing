package domain;

import aima.search.framework.*;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.lang.System.err;
import static java.lang.System.out;


public class Main {

    private static State initState(String[] args) {
        int nest = Integer.parseInt(args[4]);
        int nbic = Integer.parseInt(args[5]);
        int dem  = Integer.parseInt(args[6]);
        int seed = Integer.parseInt(args[7]);
        int nVans = Integer.parseInt(args[8]);
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
    }

    private static void printErrorMessage(String s) {
        err.println(s);
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {

        // Local Search Algorithm
        Search search = null;
        switch (args[0]) {
            case "hc":
                search = new HillClimbingSearch();
                break;
            case "sa":
                int maxIt  = Integer.parseInt(args[9]);
                int numIt  = Integer.parseInt(args[10]);
                int k      = Integer.parseInt(args[11]);
                int lambda = Integer.parseInt(args[12]);
                search = new SimulatedAnnealingSearch(maxIt, numIt, k, lambda);
                break;
            default:
                printErrorMessage("Introduce a valid Local Search Algorithm");
        }

        // Initial State
        State initialState = initState(args);
        switch (args[1]) {
            case "r":
                initialState.initRandom(Integer.parseInt(args[7]));
                break;
            case "f":
                initialState.initFixed();
                break;
            case "rf":
                initialState.initCombined(Integer.parseInt(args[7]));
                break;
            default:
                printErrorMessage("Introduce a valid Initial State");
        }

        // Heuristic
        HeuristicFunction heuristic = null;
        switch (args[2]) {
            case "max":
                heuristic = new HeuristicMaxObtained();
                break;
            case "min":
                heuristic = new HeuristicMinCost();
                break;
            default:
                printErrorMessage("Introduce a valid Heuristic");
        }

        // Set of Operators
        SuccessorFunction successor = null;
        if (args[0].equals("sa") && !args[3].equals("r"))
            printErrorMessage("Introduce a valid set of Operators");
        switch (args[3]) {
            case "s":
                successor = new SuccessorSingle();
                break;
            case "d":
                successor = new SuccessorDouble();
                break;
            case "sd":
                successor = new SuccessorBoth();
                break;
            case "r":
                successor = new SuccessorRandom();
                break;
            default:
                printErrorMessage("Introduce a valid set of Operators");
        }

        // Initialize Problem. (AIMA)
        Problem problem = new Problem(initialState, successor, new StateGoal(), heuristic);

        long startTime = System.nanoTime();
        SearchAgent agent = new SearchAgent(problem, search);
        long endTime = System.nanoTime();

        Main.printInstrumentation(agent.getInstrumentation());
        Main.printActions(agent.getActions());
        Main.printElapsedTime(startTime, endTime);

    }

}
