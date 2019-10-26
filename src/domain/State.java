package domain;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.System.out;


public class State {

    // ============================== ATTRIBUTES ============================== //

    private Estaciones stations;
    private ArrayList<Boolean> isVisited;

    private ArrayList<Van> fleet;

    private Integer cost;

    // =============================== METHODS ================================ //

    // ----------------------------- Constructors ----------------------------- //

    // Initial constructor
    public State(int nest, int nbic, int dem, int seed, int nvan) {
        this.stations = new Estaciones(nest, nbic, dem, seed);
        initIsVisited(nest);
        initFleet(nvan);
        this.cost = 0;
    }

    // Copy constructor
    public State(final State state) {
        this.stations = (Estaciones) state.getStations().clone();
        this.setIsVisited(state.getIsVisited());
        this.setFleet(state.getFleet());
        this.setCost(state.getCost());
    }

    // ----------------------------- Initializers ----------------------------- //

    private void initIsVisited(final int nest) {
        this.isVisited = new ArrayList<>(nest);
        for (int i = 0; i < nest; i++) {
            this.isVisited.add(false);
        }
    }

    private void initFleet(final int nvan) {
        this.fleet = new ArrayList<>(nvan);
        for (int i = 0; i < nvan; i++) {
            this.fleet.add(new Van(i));
        }
    }

    public void initRandom(final int seed) {
        Random rand = new Random(seed);
        for (Van v: this.fleet) {
            int est = rand.nextInt(this.stations.size()-1);
            v.setOriginStationID(est);
        }
    }

    private int maxIndex(ArrayList<Integer> arrayList) {
        int max = arrayList.get(0);
        int maxIndex = 0;
        for (int i = 1; i < arrayList.size(); i++){
            if(arrayList.get(i) > max ){
                max = arrayList.get(i);
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private ArrayList<Integer> initExcess(int size) {
        ArrayList<Integer> excess = new ArrayList<>(size);
        for (Estacion e : this.stations) {
            excess.add(e.getNumBicicletasNext() - e.getDemanda());
        }
        return excess;
    }

    private ArrayList<Integer> initIdStations(int size) {
        ArrayList<Integer> idStation = new ArrayList<>(this.stations.size());
        for (int i = 0; i < size; i++) {
            idStation.add(i);
        }
        return idStation;
    }

    private ArrayList<Integer> getMaxDemand(ArrayList<Integer> d, ArrayList<Integer> id, int n) {
        ArrayList<Integer> maxDemand = new ArrayList<>();
        int k = 0;
        while (k < n) {
            int i = maxIndex(d);
            if (d.get(i) <= 0)
                break;
            maxDemand.add(i+k);
            d.remove(i);
            id.remove(i);
            k++;
        }
        return maxDemand;
    }

    public void initFixed1() {
        int nvan = this.fleet.size();
        ArrayList<Integer> idStations = initIdStations(this.stations.size());
        ArrayList<Integer> excess = initExcess(this.stations.size());
        ArrayList<Integer> maxExcess = getMaxDemand(excess, idStations, nvan);
        for (Van v: this.fleet) {
            if (maxExcess.size() == 0)
                break;
            v.setOriginStationID(maxExcess.get(0));
            maxExcess.remove(0);
        }
    }

    //  ------------------------------ Modifiers ------------------------------- //

    private void setIsVisited(final ArrayList<Boolean> isVisited) {
        initIsVisited(isVisited.size());
        for (int i = 0; i < isVisited.size(); ++i) {
            this.isVisited.set(i, isVisited.get(i));
        }
    }

    public void setCost(final int cost) {
        this.cost = cost;
    }

    public void setVanVisited(final int van) {
        this.isVisited.set(van, true);
    }

    private void setFleet(final ArrayList<Van> fleet) {
        initFleet(fleet.size());
        for (int i = 0; i < fleet.size(); ++i) {
            this.fleet.set(i, fleet.get(i));
        }
    }

    // ----------------------------- Consultants ------------------------------ //

    public Estaciones getStations() {
        return this.stations;
    }

    public ArrayList<Boolean> getIsVisited() {
        return this.isVisited;
    }

    public Boolean isVisited(final int i) {
        return this.isVisited.get(i);
    }

    public ArrayList<Van> getFleet() {
        return this.fleet;
    }

    public int getCost() {
        return this.cost;
    }

    // ------------------------------ Operators ------------------------------- //

    public void singleMove(Estacion origin, Estacion destination, Integer taken) {
        Integer nonUsed = origin.getNumBicicletasNoUsadas();
        origin.setNumBicicletasNoUsadas(nonUsed-taken);

        Integer nextO = origin.getNumBicicletasNext();
        origin.setNumBicicletasNext(nextO-taken);

        Integer nextD = destination.getNumBicicletasNext();
        destination.setNumBicicletasNext(nextD+taken);

        calculateCost(origin, destination, taken);
    }

    public void doubleMove(Estacion origin, Estacion first_destination, Estacion second_destination, Integer taken) {
        // First move
        Integer excedent = origin.getNumBicicletasNoUsadas();
        origin.setNumBicicletasNoUsadas(excedent-taken);

        Integer demand = first_destination.getDemanda();
        // first_destination.setDemanda(0);

        // Update Van
        taken -= demand;

        // Second move
        second_destination.setDemanda(second_destination.getDemanda()- taken);
        calculateCost(origin, first_destination, taken+demand);
        calculateCost(first_destination, second_destination, taken);
    }

    private void calculateCost(Estacion origin, Estacion destination, Integer taken) {
        int kilometer_cost = (taken + 9)/10;
        int distance = Math.abs(origin.getCoordX() - destination.getCoordX() + Math.abs(origin.getCoordY() - destination.getCoordY()));
        int cost = distance * kilometer_cost;
        this.cost += cost;
    }

    // -------------------------------- Driver -------------------------------- //

    private void printStations() {
        out.print(this.stations.get(0));
        for (int i = 1; i < this.stations.size(); ++i) {
            out.print(" | " + this.stations.get(i));
        }
        out.println();
    }

    private void printVisited() {
        out.print(this.isVisited.get(0));
        for (int i = 1; i < this.isVisited.size(); ++i) {
            out.print(" | " + this.isVisited.get(i));
        }
        out.println();
    }

    private void printFleet() {
        out.print("(" + this.fleet.get(0).getOriginStationID() + "," + this.fleet.get(0).getNumBikes() + ")");
        for (int i = 1; i < this.fleet.size(); ++i) {
            out.print(" | (" + this.fleet.get(i).getOriginStationID() + "," + this.fleet.get(i).getNumBikes() + ")");
        }
        out.println();
    }

    private void printCost() {
        out.println(this.cost);
    }

    public void print() {
        out.println("[State] INFO: printing attributes...");
        out.print("  ⤷ stations(" + this.stations.size() + "):\t"); this.printStations();
        out.print("  ⤷ visited(" + this.isVisited.size() + "): \t"); this.printVisited();
        out.print("  ⤷ fleet(" + this.fleet.size() + "):\t\t"); this.printFleet();
        out.print("  ⤷ cost:\t\t\t"); this.printCost();
    }

    // ------------------------------------------------------------------------ //

    public static void main(String[] args) {

        State s = new State(5, 100, 0, 1, 10);
        s.print();

    }

}
