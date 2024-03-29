package domain;

import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;


public class SuccessorDouble implements SuccessorFunction {

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
                // generateDouble
                for (int j = 0; j < nStations; ++j) {
                    if (nOrigin != j) {
                        for (Integer s = 0; s < nStations; ++s) {
                            if (!s.equals(j)) {
                                int numBikes =  calculateNumBikesDouble(stations.get(nOrigin), stations.get(j), stations.get(s));
                                if (numBikes > 0) {
                                    State newBoard = new State(board);
                                    newBoard.doubleMove(nOrigin, j, s, numBikes);
                                    newBoard.setStationVisited(nOrigin);

                                    Integer demand1 = (stations.get(j).getDemanda() -  stations.get(j).getNumBicicletasNext());
                                    String S = "Operator: double " + "\n" +
                                            "Heuristic value: " + -hf.getHeuristicValue(newBoard) + "\n" +
                                            "Origin: " + nOrigin + "\n" +
                                            "First destination: " + j + "\n" +
                                            "Bikes moved: " + demand1 + "\n" +
                                            "Second destination: " + s + "\n" +
                                            "Bikes moved: " + (numBikes - demand1) + "\n" +
                                            "Total bikes moved: " + numBikes + "\n";
                                    retval.add(new Successor(S, newBoard));
                                }
                            }
                        }
                    }
                }
            }
        }
        return retval;
    }

    private int calculateNumBikesDouble(Estacion act, Estacion dest1, Estacion dest2) {
        int numBikes = 0;
        int excess = act.getNumBicicletasNext() - act.getDemanda();
        if (excess > 0) {
            numBikes = min(excess, act.getNumBicicletasNoUsadas());
            numBikes = min(numBikes, Van.CAPACITY);
            int deficit1 = dest1.getDemanda() - dest1.getNumBicicletasNext();
            int deficit2 = dest2.getDemanda() - dest2.getNumBicicletasNext();

            if (deficit1 < numBikes && deficit1 > 0 && deficit2 > 0) {
                numBikes = min(numBikes, deficit1 + deficit2);

            }
            else numBikes = 0;
        }
        return numBikes;
    }
}

