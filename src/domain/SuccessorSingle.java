package domain;

import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;


public class SuccessorSingle implements SuccessorFunction {

    public List getSuccessors(Object state) {

        ArrayList retval= new ArrayList();
        State board = (State) state;

        // For print
        HeuristicFunction hf = new HeuristicMinCost();

        ArrayList<Van> fleet = board.getFleet();
        Estaciones stations = board.getStations();
        int nStations = stations.size();

        for (int i = 0; i < fleet.size(); i++) {
            Van actV = fleet.get(i);
            int nOrigin = actV.getOriginStationID();
            if (!board.isVisited(nOrigin)) { // Si ja s'ha recollit a l'estació no podem fer res

                // generateSingle
                for (int j = 0; j < nStations; ++j) {
                    if (nOrigin != j) {
                        int numBikes = calculateNumBikes(stations.get(nOrigin), stations.get(j));
                        if (numBikes > 0) {
                            State newBoard = new State(board);
                            newBoard.singleMove(nOrigin, j, numBikes);
                            newBoard.setStationVisited(nOrigin);
                            String S = "Single: " + numBikes + " Acc: " + -hf.getHeuristicValue(newBoard);
                            retval.add(new Successor(S, newBoard));
                        }
                    }
                }
            }
        }
        return retval;
    }

    private int calculateNumBikes(Estacion act, Estacion dest) {
        int numBikes = 0;
        int excess = act.getNumBicicletasNext() - act.getDemanda();
        if (excess > 0) {
            numBikes = min(excess, act.getNumBicicletasNoUsadas());
            int deficit = dest.getDemanda() - dest.getNumBicicletasNext();
            numBikes = min(numBikes, deficit);
            numBikes = min(numBikes, Van.CAPACITY);
        }
        return numBikes;
    }

}

