package domain;

import aima.search.framework.HeuristicFunction;


public class Heuristic implements HeuristicFunction {

  public double getHeuristicValue(Object state){
    return ((State) state).heuristic();
  }

}
