package domain;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.min;


public class SuccessorRandomAcotat implements SuccessorFunction {

    public List getSuccessors(Object state) {
        ArrayList retval = new ArrayList();
        State board = (State) state;

        // For print
        HeuristicFunction hf = new HeuristicMinCost();

        ArrayList<Van> fleet = board.getFleet();
        int nVans = fleet.size();
        Estaciones stations = board.getStations();
        int nStations = stations.size();

        // Random Van
        int randomVan  = ThreadLocalRandom.current().nextInt(0, nVans);
        Van actV = fleet.get(randomVan);
        while(board.isVisited(actV.getOriginStationID()) == true) {
            randomVan  = ThreadLocalRandom.current().nextInt(0, nVans);
            actV = fleet.get(randomVan);
        }
        int nOrigin = actV.getOriginStationID();

        // Random Operator
        int randomOp  = ThreadLocalRandom.current().nextInt(0, 2);

        if (randomOp == 1) {

            // Random Dest
            int randomDest  = ThreadLocalRandom.current().nextInt(0, nStations);
            int numBikes = calculateNumBikes(stations.get(nOrigin), stations.get(randomDest));
            while (randomDest == nOrigin || numBikes == 0) {
                randomDest = ThreadLocalRandom.current().nextInt(0, nStations);
            }
            int randomBikes = ThreadLocalRandom.current().nextInt(1, numBikes + 1);
            State newBoard = new State(board);
            newBoard.singleMove(nOrigin, randomDest, randomBikes);
            newBoard.setStationVisited(nOrigin);
            String S = "Operator: single " + "\n" +
                    "Heuristic value: " + -hf.getHeuristicValue(newBoard) + "\n" +
                    "Origin: " + nOrigin + "\n" +
                    "Destination: " + randomDest + "\n" +
                    "Bikes moved: " + numBikes + "\n";
            retval.add(new Successor(S, newBoard));
        }
        else {
            int randomDest  = ThreadLocalRandom.current().nextInt(0, nStations);
            int randomSecondDest  = ThreadLocalRandom.current().nextInt(0, nStations);
            int numBikes = calculateNumBikesDouble(stations.get(nOrigin), stations.get(randomDest), stations.get(randomSecondDest));
            while (randomDest == nOrigin || randomSecondDest == nOrigin || randomDest == randomSecondDest|| numBikes == 0) {
                randomDest = ThreadLocalRandom.current().nextInt(0, nStations);
            }
            int randomBikes = ThreadLocalRandom.current().nextInt(0, numBikes + 1);
            State newBoard = new State(board);
            newBoard.doubleMove(nOrigin, randomDest, randomSecondDest, randomBikes);
            newBoard.setStationVisited(nOrigin);
            Integer demand1 = (stations.get(randomDest).getDemanda() - stations.get(randomDest).getNumBicicletasNext());
            String S = "Operator: double " + "\n" +
                    "Heuristic value: " + -hf.getHeuristicValue(newBoard) + "\n" +
                    "Origin: " + nOrigin + "\n" +
                    "First destination: " + randomDest + "\n" +
                    "Bikes moved: " + demand1 + "\n" +
                    "Second destination: " + randomSecondDest + "\n" +
                    "Bikes moved: " + (randomBikes - demand1) + "\n" +
                    "Total bikes moved: " + numBikes + "\n";
            retval.add(new Successor(S, newBoard));
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

