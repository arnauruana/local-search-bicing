package IA.ProbIA5;


import aima.search.framework.HeuristicFunction;

public class ProbIA5HeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        return ((ProbIA5Board) n).heuristic();
    }
}
