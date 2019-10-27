package domain;

import aima.search.framework.HeuristicFunction;

public class HeuristicMinCost implements HeuristicFunction {

    public double getHeuristicValue(Object obj) {
        State state = (State) obj;
        return state.getCost();
    }
}
