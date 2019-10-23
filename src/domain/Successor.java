package domain;

import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

public class Successor implements SuccessorFunction {

    public List getSuccessors(Object state) {
        ArrayList<State> retval = new ArrayList<>();
        State board = (State) state;


        ArrayList<Van> fleet = board.getFleet();
        Estaciones stations = board.getStations();
        int nStations = stations.size();

        for (Van actV : fleet) {
            int nOrigin = actV.getIdStation();
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
                            retval.add(newBoard);
                        }
                    }
                }

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
                                    retval.add(newBoard);
                                }
                            }
                        }
                    }
                }
            }
        }
        return retval;
    }
}

