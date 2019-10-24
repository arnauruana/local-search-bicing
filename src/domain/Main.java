package domain;

import IA.Bicing.Estaciones;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.ArrayList;

public class Main {

    private static ArrayList initalizeRandomFleet(int nVans) {
        ArrayList<Van> fleet = new ArrayList<>(nVans);
        for (Van v: fleet) {

        }
        return fleet;
    }

    private static ArrayList initalizeFixedFleet(int nVans) {
        ArrayList<Van> fleet = new ArrayList<>(nVans);
        for (Van v: fleet) {

        }
        return fleet;
    }


    private static State initializeState(String[] args) {
        int nest = Integer.parseInt(args[2]);
        int nbic = Integer.parseInt(args[3]);
        int dem  = Integer.parseInt(args[4]);
        int seed = Integer.parseInt(args[5]);
        Estaciones estaciones = new Estaciones(nest, nbic, dem, seed);
        ArrayList fleet = (args[1].equals("r"))
                ? initalizeRandomFleet(Integer.parseInt(args[6]))
                : initalizeFixedFleet(Integer.parseInt(args[6]));
        return new State (estaciones, fleet);
    }
    public static void main(String[] args) throws Exception {
        State initialState = initializeState(args);
        Problem problem = new Problem(initialState, new Successor(), new Goal());

        Search search = (args[0].equals("h")) ? new HillClimbingSearch() : new SimulatedAnnealingSearch();

        SearchAgent agent = new SearchAgent(problem, search);

        //print action i printIntrumentation
    }
}
