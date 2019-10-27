package domain;

import aima.search.framework.HeuristicFunction;

public class HeuristicMaxBenefits implements HeuristicFunction {

    public double getHeuristicValue(Object obj) {
        State state = (State) obj;
        return state.getBenefits();
    }
}
