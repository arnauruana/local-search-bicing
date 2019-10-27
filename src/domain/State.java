package domain;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.min;
import static java.lang.System.out;


public class State {

    // ============================== ATTRIBUTES ============================== //

    private Estaciones stations;
    private static int nest, nbic, dem, seed;
    private ArrayList<Boolean> isVisited;

    private ArrayList<Van> fleet;

    private double cost;
    private double benefits;
    private Integer demandSupplied;

    // =============================== METHODS ================================ //

    // ----------------------------- Constructors ----------------------------- //

    // Initial constructor
    public State(int nest, int nbic, int dem, int seed, int nvan) {
        State.nest = nest;
        State.nbic = nbic;
        State.dem  = dem;
        State.seed = seed;
        this.stations = new Estaciones(State.nest, State.nbic, State.dem, State.seed);
        initIsVisited(nest);
        initFleet(nvan);
        this.cost = 0;
        this.benefits = 0;
        this.demandSupplied = 0;
    }

    // Copy constructor
    public State(final State state) {
        this.setStations(state.getStations());
        this.setIsVisited(state.getIsVisited());
        this.setFleet(state.getFleet());
        this.setCost(state.getCost());
        this.setBenefits(state.getBenefits());
        this.setDemandSupplied(state.getDemandSupplied());
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
            int est = rand.nextInt(this.stations.size());
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
            int ex = e.getNumBicicletasNext() - e.getDemanda();
            if (ex > 0) ex = min(ex, e.getNumBicicletasNoUsadas());
            excess.add(ex);
        }
        return excess;
    }

    private ArrayList<Integer> initIsExcess(final ArrayList<Integer> excess) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < excess.size(); ++i) {
            if (excess.get(i) > 0) {
                result.add(i);
            }
        }
        return result;
    }

    private ArrayList<Integer> initIdStations(int size) {
        ArrayList<Integer> idStation = new ArrayList<>(this.stations.size());
        for (int i = 0; i < size; i++) {
            idStation.add(i);
        }
        return idStation;
    }

    private ArrayList<Integer> getMaxExcess(ArrayList<Integer> e, ArrayList<Integer> id, int n) {
        ArrayList<Integer> maxExcess = new ArrayList<>();
        for (int k = 0; k < n; k++) {
            int i = maxIndex(e);
            if (e.get(i) <= 0)
                break;
            maxExcess.add(i);
            e.set(i, -1);
        }
        return maxExcess;
    }

    public void initFixed() {
        int nvan = this.fleet.size();
        ArrayList<Integer> idStations = initIdStations(this.stations.size());
        ArrayList<Integer> excess = initExcess(this.stations.size());
        ArrayList<Integer> maxExcess = getMaxExcess(excess, idStations, nvan);
        for (Van v: this.fleet) {
            if (maxExcess.size() == 0)
                break;
            v.setOriginStationID(maxExcess.get(0));
            maxExcess.remove(0);
        }
    }

    public void initCombined(final int seed) {
        Random rand = new Random(seed);
        ArrayList<Integer> excess = initExcess(this.stations.size());
        ArrayList<Integer> isExcess = initIsExcess(excess);
        for (Van v : this.fleet) {
            if (!isExcess.isEmpty()) {
                int est = rand.nextInt(isExcess.size());
                v.setOriginStationID(isExcess.get(est));
                isExcess.remove(est);
            }
            else {
                v.setOriginStationID(0);
            }
        }

    }

    //  ------------------------------ Modifiers ------------------------------- //

    private void setStations(Estaciones stations) {
        this.stations = new Estaciones(State.nest, State.nbic, State.dem, State.seed);
        for (int i = 0; i < this.stations.size(); i++) {
            Estacion eAux = stations.get(i);
            Estacion e = new Estacion(eAux.getCoordX()/100, eAux.getCoordY()/100);
            e.setNumBicicletasNext(eAux.getNumBicicletasNext());
            e.setNumBicicletasNoUsadas(eAux.getNumBicicletasNoUsadas());
            e.setDemanda(eAux.getDemanda());
            this.stations.set(i, e);
        }
    }

    private void setIsVisited(final ArrayList<Boolean> isVisited) {
        initIsVisited(isVisited.size());
        for (int i = 0; i < isVisited.size(); ++i) {
            this.isVisited.set(i, isVisited.get(i));
        }
    }

    public void setStationVisited(final int station) {
        this.isVisited.set(station, true);
    }

    private void setFleet(final ArrayList<Van> fleet) {
        initFleet(fleet.size());
        for (int i = 0; i < fleet.size(); ++i) {
            this.fleet.set(i, fleet.get(i));
        }
    }

    public void setCost(final double cost) {
        this.cost = cost;
    }

    public void setBenefits(final double benefits) {
        this.benefits = benefits;
    }

    public void setDemandSupplied(final int demandSupplied) {
        this.demandSupplied = demandSupplied;
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

    public double getCost() {
        return this.cost;
    }

    public double getBenefits() {
        return this.benefits;
    }

    public int getDemandSupplied() { return this.demandSupplied;
    }

    // ------------------------------ Operators ------------------------------- //

    public void singleMove(Integer origin, Integer destination, Integer taken) {
        Integer nonUsed = this.stations.get(origin).getNumBicicletasNoUsadas();
        this.stations.get(origin).setNumBicicletasNoUsadas(nonUsed-taken);

        Integer nextO = this.stations.get(origin).getNumBicicletasNext();
        this.stations.get(origin).setNumBicicletasNext(nextO-taken);

        Integer nextD = this.stations.get(destination).getNumBicicletasNext();
        this.stations.get(destination).setNumBicicletasNext(nextD+taken);

        calculateCost(this.stations.get(origin), this.stations.get(destination), taken);
        this.demandSupplied += taken;
        this.benefits = (this.demandSupplied - this.cost);
    }

    public void doubleMove(Integer origin, Integer firstDestination, Integer secondDestination, Integer taken) {
        // Update origin
        Integer nonUsed = this.stations.get(origin).getNumBicicletasNoUsadas();
        this.stations.get(origin).setNumBicicletasNoUsadas(nonUsed-taken);
        Integer nextO = this.stations.get(origin).getNumBicicletasNext();
        this.stations.get(origin).setNumBicicletasNext(nextO-taken);

        // Update dest1
        Integer nextD1 = this.stations.get(firstDestination).getNumBicicletasNext();
        Integer demand1 = (this.stations.get(firstDestination).getDemanda() - nextD1);
        this.stations.get(firstDestination).setNumBicicletasNext(nextD1 - demand1);

        // Update Van
        Integer demand2 = taken - demand1;

        // Update dest2
        Integer nextD2 = this.stations.get(secondDestination).getNumBicicletasNext();
        this.stations.get(secondDestination).setNumBicicletasNext(nextD2 - demand2);

        calculateCost(this.stations.get(origin), this.stations.get(firstDestination), demand1);
        calculateCost(this.stations.get(firstDestination), this.stations.get(secondDestination), demand2);
        this.demandSupplied += taken;
        this.benefits = (this.demandSupplied - this.cost);
    }

    private void calculateCost(Estacion origin, Estacion destination, Integer taken) {
        int kilometer_cost = (taken + 9)/10;
        double distance = (Math.abs(origin.getCoordX() - destination.getCoordX()) + Math.abs(origin.getCoordY() - destination.getCoordY()))*1.0/1000;
        double cost = distance * kilometer_cost;
        this.cost += cost;

    }

}
