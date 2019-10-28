package domain;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

    public void singleMove(Integer o, Integer d, Integer taken) {

        Estacion origin = this.stations.get(o);
        Estacion destination = this.stations.get(d);

        Integer excess = origin.getNumBicicletasNext() - origin.getDemanda();
        excess = min(excess, origin.getNumBicicletasNoUsadas());
        excess = min(excess, Van.CAPACITY);

        int demand = destination.getDemanda() - destination.getNumBicicletasNext();
        if (excess < taken || taken > demand) {
            if (excess <= 0) this.demandSupplied += excess;
            else if (excess < taken) this.demandSupplied -= (taken - excess);
            // Update origin
            Integer nonUsed = this.stations.get(o).getNumBicicletasNoUsadas();
            this.stations.get(o).setNumBicicletasNoUsadas(nonUsed - taken);
            Integer nextO = this.stations.get(o).getNumBicicletasNext();
            this.stations.get(o).setNumBicicletasNext(nextO - taken);

            // Update first
            this.stations.get(d).setNumBicicletasNext(destination.getNumBicicletasNext() + taken);
            if (demand > 0) {
                if (demand >= taken) this.demandSupplied += taken;
                else this.demandSupplied += demand;
            }

        }
        else{
            Integer nonUsed = this.stations.get(o).getNumBicicletasNoUsadas();
            this.stations.get(o).setNumBicicletasNoUsadas(nonUsed - taken);

            Integer nextO = this.stations.get(o).getNumBicicletasNext();
            this.stations.get(o).setNumBicicletasNext(nextO - taken);

            Integer nextD = this.stations.get(d).getNumBicicletasNext();
            this.stations.get(d).setNumBicicletasNext(nextD + taken);

            this.demandSupplied += taken;
        }
        calculateCost(this.stations.get(o), this.stations.get(d), taken);
        this.benefits = (this.demandSupplied - this.cost);
    }

    public void doubleMove(Integer o, Integer fD, Integer sD, Integer taken) {

        Estacion origin = this.stations.get(o);
        Estacion firstDestination = this.stations.get(fD);
        Estacion secondDestination = this.stations.get(sD);

        Integer excess = origin.getNumBicicletasNext() - origin.getDemanda();
        excess = min(excess, origin.getNumBicicletasNoUsadas());
        excess = min(excess, Van.CAPACITY);

        int firstDemand = firstDestination.getDemanda() - firstDestination.getNumBicicletasNext();
        int secondDemand = secondDestination.getDemanda() - secondDestination.getNumBicicletasNext();
        if (firstDemand < 1 || secondDemand < 1 || taken > excess || firstDemand > excess) {
            // Working with Simulated Annealing

            // Apliquem penalització
            if (excess <= 0) this.demandSupplied += excess;
            else if (excess < taken) this.demandSupplied -= (taken - excess);
            // Update origin
            Integer nonUsed = this.stations.get(o).getNumBicicletasNoUsadas();
            this.stations.get(o).setNumBicicletasNoUsadas(nonUsed - taken);
            Integer nextO = this.stations.get(o).getNumBicicletasNext();
            this.stations.get(o).setNumBicicletasNext(nextO - taken);


            int firstDrop = ThreadLocalRandom.current().nextInt(0, taken+1);
            // Update first
            Integer nextD1 = this.stations.get(fD).getNumBicicletasNext();
            Integer demand1 = (this.stations.get(fD).getDemanda() - nextD1);
            this.stations.get(fD).setNumBicicletasNext(nextD1 + firstDrop);
            if (demand1 > 0) {
                if (demand1 >= firstDrop) this.demandSupplied += firstDrop;
                else this.demandSupplied += demand1;
            }

            // Update second
            int secondDrop  = taken-firstDrop;
            Integer nextD2 = this.stations.get(sD).getNumBicicletasNext();
            Integer demand2 = (this.stations.get(sD).getDemanda() - nextD2);
            this.stations.get(sD).setNumBicicletasNext(nextD2 + secondDrop);
            if (demand2 > 0) {
                if (demand2 >= secondDrop) this.demandSupplied += secondDrop;
                else this.demandSupplied += demand2;
            }

            calculateCost(this.stations.get(o), this.stations.get(fD), demand1);
            calculateCost(this.stations.get(fD), this.stations.get(sD), demand2);
            this.benefits = (this.demandSupplied - this.cost);
        }
        else {
            // Working with Hill Climbing
            // Update origin
            Integer nonUsed = this.stations.get(o).getNumBicicletasNoUsadas();
            this.stations.get(o).setNumBicicletasNoUsadas(nonUsed - taken);
            Integer nextO = this.stations.get(o).getNumBicicletasNext();
            this.stations.get(o).setNumBicicletasNext(nextO - taken);

            // Update dest1
            Integer nextD1 = this.stations.get(fD).getNumBicicletasNext();
            Integer demand1 = (this.stations.get(fD).getDemanda() - nextD1);
            this.stations.get(fD).setNumBicicletasNext(nextD1 - demand1);

            // Update Van
            Integer demand2 = taken - demand1;

            // Update dest2
            Integer nextD2 = this.stations.get(sD).getNumBicicletasNext();
            this.stations.get(sD).setNumBicicletasNext(nextD2 - demand2);

            calculateCost(this.stations.get(o), this.stations.get(fD), demand1);
            calculateCost(this.stations.get(fD), this.stations.get(sD), demand2);
            this.demandSupplied += taken;
            this.benefits = (this.demandSupplied - this.cost);
        }
    }

    private void calculateCost(Estacion origin, Estacion destination, Integer taken) {
        int kilometer_cost = (taken + 9)/10;
        double distance = (Math.abs(origin.getCoordX() - destination.getCoordX()) + Math.abs(origin.getCoordY() - destination.getCoordY()))*1.0/1000;
        double cost = distance * kilometer_cost;
        this.cost += cost;

    }

}
