package domain;

import aima.search.framework.HeuristicFunction;


public class Heuristic1 implements HeuristicFunction {

    public double getHeuristicValue(Object obj) {
        return -((State) obj).getCost();
    }

}
