package domain;

import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.min;
import static java.lang.Math.random;


public class SuccessorRandom implements SuccessorFunction {

    public List getSuccessors(Object state) {
        ArrayList retval = new ArrayList();
        State board = (State) state;

        // For print
        HeuristicFunction hf = new HeuristicMinCost();

        ArrayList<Van> fleet = board.getFleet();
        int nVans = fleet.size();
        Estaciones stations = board.getStations();
        int nStations = stations.size();
        boolean able = false;
        for (int f = 0; f < nStations; ++f) {
            if (board.isVisited(f))  {
                able = true;
                f = nStations;
            }
        }
        if (able) {
            // Random Van
            int randomVan = ThreadLocalRandom.current().nextInt(0, nVans);
            Van actV = fleet.get(randomVan);
            while (board.isVisited(actV.getOriginStationID()) == true) {
                randomVan = ThreadLocalRandom.current().nextInt(0, nVans);
                actV = fleet.get(randomVan);
            }
            int nOrigin = actV.getOriginStationID();

            // Random Operator
            int randomOp = ThreadLocalRandom.current().nextInt(0, 2);

            if (randomOp == 1) {
                // Random Dest
                int randomDest = ThreadLocalRandom.current().nextInt(0, nStations);
                while (randomDest == nOrigin) {
                    randomDest = ThreadLocalRandom.current().nextInt(0, nStations);
                }

                int randomBikes = ThreadLocalRandom.current().nextInt(0, Van.CAPACITY + 1);
                State newBoard = new State(board);
                newBoard.singleMove(nOrigin, randomDest, randomBikes);
                newBoard.setStationVisited(nOrigin);
                String S = "Operator: single " + "\n" +
                        "Heuristic value: " + -hf.getHeuristicValue(newBoard) + "\n" +
                        "Origin: " + nOrigin + "\n" +
                        "Destination: " + randomDest + "\n" +
                        "Bikes moved: " + randomBikes + "\n";
                retval.add(new Successor(S, newBoard));
            }
            else {
                int randomDest = ThreadLocalRandom.current().nextInt(0, nStations);
                int randomSecondDest = ThreadLocalRandom.current().nextInt(0, nStations);
                while (randomDest == nOrigin || randomSecondDest == nOrigin || randomDest == randomSecondDest) {
                    randomDest = ThreadLocalRandom.current().nextInt(0, nStations);
                }
                int randomBikes = ThreadLocalRandom.current().nextInt(0, Van.CAPACITY);
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
                        "Total bikes moved: " + randomBikes + "\n";
                retval.add(new Successor(S, newBoard));
            }
        }
        return retval;
    }
}
