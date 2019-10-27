package domain;

import aima.search.framework.GoalTest;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

import java.util.ArrayList;
import java.util.List;

public class StateGoal implements GoalTest {

    public boolean isGoalState(Object state) {
        State board = (State) state;
        ArrayList<Van> fleet = board.getFleet();
        for (Van actV : fleet) {
            if (actV.getIsVisited() == false) return false;

        }
        return true;
    }

}
