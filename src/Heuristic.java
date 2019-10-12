import aima.search.framework.HeuristicFunction;

public class Heuristic implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        return ((State) n).heuristic();
    }
}
