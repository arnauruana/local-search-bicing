package domain;

import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

public class SuccessorRandom implements SuccessorFunction {

    public List getSuccessors(Object state) {
        ArrayList<State> retval = new ArrayList<>();
        State board = (State) state;


        ArrayList<Van> fleet = board.getFleet();
        int nVans = fleet.size();
        Estaciones stations = board.getStations();
        int nStations = stations.size();

        int randomVan  = ThreadLocalRandom.current().nextInt(0, nVans + 1);
        Van actV = fleet.get(randomVan);
        int nOrigin = actV.getIdOrigin();
        Estacion origin = stations.get(nOrigin);

        int randomOp  = ThreadLocalRandom.current().nextInt(0, 2);

        // TODO Si una estació no té demanda es accesible?
        int randomDest  = ThreadLocalRandom.current().nextInt(0, nStations + 1);
        while (randomDest == nOrigin) randomDest = ThreadLocalRandom.current().nextInt(0, nStations + 1);


        if (randomOp == 1) {

            Estacion destination = stations.get(randomDest);
            int demand = destination.getDemanda();
            if (demand > 30) demand = 30;

            int randomBikes  = ThreadLocalRandom.current().nextInt(0, demand + 1);

            State newBoard = new State(board);
            newBoard.single_move(origin, destination, randomBikes);
            retval.add(newBoard);

        }
        else {

            int randomSecondDest  = ThreadLocalRandom.current().nextInt(0, nStations + 1);
            while (randomDest == randomSecondDest) randomSecondDest = ThreadLocalRandom.current().nextInt(0, nStations + 1);

            Estacion first_destination = stations.get(randomDest);
            Estacion second_destination = stations.get(randomSecondDest);

            int demand = first_destination.getDemanda() + second_destination.getDemanda();
            if (demand > 30) demand = 30;

            int randomBikes  = ThreadLocalRandom.current().nextInt(0, demand + 1);

            State newBoard = new State(board);
            newBoard.double_move(origin, first_destination, second_destination, randomBikes);
            retval.add(newBoard);
        }
        return retval;
    }

}
