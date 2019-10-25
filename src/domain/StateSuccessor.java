package domain;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

public class StateSuccessor implements SuccessorFunction {

    public List getSuccessors(Object state) {
        ArrayList retval= new ArrayList();
        State board = (State) state;


        ArrayList<Van> fleet = board.getFleet();
        Estaciones stations = board.getStations();
        int nStations = stations.size();

        for (int i = 0; i < fleet.size(); i++) {
            Van actV = fleet.get(i);
            int nOrigin = actV.getIdOrigin();
            Estacion origin = stations.get(nOrigin);
            // TODO Actualitzar VAN
            if (!board.isVisited(nOrigin)) { // Si ja s'ha recollit a l'estació no podem fer res

                // TODO Optimizació capar generació amb limit recepcio
                // generateSingle
                for (int j = 0; j < nStations; ++j) {
                    if (nOrigin != j) {
                        Estacion destination = stations.get(j);

                        // Evitem portar més bicicletes de les que es necessiten
                        Integer demand = destination.getDemanda();
                        if (demand > 30) demand = 30;

                        for (Integer k = 1; k <= demand; ++k) {
                            State newBoard = new State(board);
                            newBoard.single_move(origin, destination, k);
                            newBoard.setVanVisited(i);
                            String S = "Single" + k;
                            retval.add(new Successor(S, newBoard));
                        }
                    }
                }
/*
                // TODO Optimització capar generació
                // generateDouble
                for (int j = 0; j < nStations; ++j) {
                    if (nOrigin != j) {
                        Estacion first_destination = stations.get(j);

                        for (Integer s = 0; s < nStations; ++s) {
                            if (!s.equals(j)) {
                                Estacion second_destination = stations.get(s);

                                // Evitem portar més bicicletes de les que es necessiten
                                Integer demand = first_destination.getDemanda() + second_destination.getDemanda();
                                if (demand > 30) demand = 30;

                                for (Integer k = 1; k <= demand; ++k) {
                                    State newBoard = new State(board);
                                    newBoard.double_move(origin, first_destination, second_destination, k);
                                    newBoard.setVanVisited(i);
                                    String S = "Double" + k;
                                    retval.add(new Successor(S, newBoard));
                                }
                            }
                        }
                    }
                }
                */
            }
        }
        return retval;
    }
}

