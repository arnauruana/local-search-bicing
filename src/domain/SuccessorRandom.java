package domain;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

import static java.lang.Math.min;

public class SuccessorRandom implements SuccessorFunction {

    public List getSuccessors(Object state) {
        ArrayList retval = new ArrayList();
        State board = (State) state;


        ArrayList<Van> fleet = board.getFleet();
        int nVans = fleet.size();
        Estaciones stations = board.getStations();
        int nStations = stations.size();

        int randomVan  = ThreadLocalRandom.current().nextInt(0, nVans + 1);
        Van actV = fleet.get(randomVan);
        int nOrigin = actV.getOriginStationID();
        Estacion origin = stations.get(nOrigin);

        int randomOp  = ThreadLocalRandom.current().nextInt(0, 2);

        // TODO Si una estació no té demanda es accesible?
        int randomDest  = ThreadLocalRandom.current().nextInt(0, nStations + 1);
        while (randomDest == nOrigin) randomDest = ThreadLocalRandom.current().nextInt(0, nStations + 1);


        if (randomOp == 1) {

            Estacion destination = stations.get(randomDest);

            int numBikes = calculateNumBikes(origin, destination);
            int randomBikes  = ThreadLocalRandom.current().nextInt(0, numBikes + 1); // TODO why +1

            State newBoard = new State(board);
            newBoard.singleMove(origin, destination, randomBikes);
            String S = "RandomSingle";
            retval.add(new Successor(S, newBoard));

        }
        else {

            int randomSecondDest  = ThreadLocalRandom.current().nextInt(0, nStations + 1);
            while (randomDest == randomSecondDest) randomSecondDest = ThreadLocalRandom.current().nextInt(0, nStations + 1);

            Estacion firstDestination = stations.get(randomDest);
            Estacion secondDestination = stations.get(randomSecondDest);

            int numBikes = calculateNumBikesDouble(origin, firstDestination, secondDestination);
            int randomBikes  = ThreadLocalRandom.current().nextInt(0, numBikes + 1); // TODO why +1

            State newBoard = new State(board);
            newBoard.doubleMove(origin, firstDestination, secondDestination, randomBikes);
            String S = "RandomDouble";
            retval.add(new Successor(S, newBoard));
        }
        return retval;
    }

    private int calculateNumBikes(Estacion act, Estacion dest) {
        int numBikes = 0;
        int excess = act.getNumBicicletasNext() - dest.getDemanda();
        if (excess > 0) {
            numBikes = min(excess, act.getNumBicicletasNoUsadas());
            int deficit = dest.getDemanda() - dest.getNumBicicletasNext();
            numBikes = min(numBikes, deficit);
            numBikes = min (numBikes, Van.CAPACITY);
        }
        return numBikes;
    }

    private int calculateNumBikesDouble(Estacion act, Estacion dest1, Estacion dest2) {
        int numBikes = 0;
        int excess = act.getNumBicicletasNext() - dest1.getDemanda();
        if (excess > 0) {
            numBikes = min (excess, act.getNumBicicletasNoUsadas());
            int deficit1 = dest1.getDemanda() - dest1.getNumBicicletasNext();
            int deficit2 = dest2.getDemanda() - dest2.getNumBicicletasNext();
            if (deficit1 > 0 && deficit2 > 0) {
                numBikes = min(numBikes, deficit1+deficit2);
                numBikes = min(numBikes, Van.CAPACITY);
            }
        }
        return numBikes;
    }

}
