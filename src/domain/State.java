package domain;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.max;

public class State {

  // ============================== ATTRIBUTES ============================== //

  private Estaciones stations;
  private ArrayList<Boolean> isVisited;

  private ArrayList<Van> fleet;

  private double totalCost = 0;

  // =============================== METHODS ================================ //

  // ----------------------------- Constructors ----------------------------- //

  // Initial constructor
  public State(int nest, int nbic, int dem, int seed, int nvan) {
    this.stations = new Estaciones(nest, nbic, dem, seed);
    initIsVisited(nest);
    initFleet(nvan);
  }

  // Copy constructor
  public State(final State state) {
    this.stations = (Estaciones) state.getStations().clone();
    this.setIsVisited(state.getIsVisited());
    this.setFleet(state.getFleet());
    this.setTotalCost(state.getTotalCost());
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
      this.fleet.add(new Van(-1));
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


    private ArrayList<Integer> initDemand(int size) {
    ArrayList<Integer> demand = new ArrayList<>(size);
    for (Estacion e : this.stations) {
      demand.add(e.getDemanda());
    }
    return demand;
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
    // TODO we shouldn't use the demand
    ArrayList<Integer> demand = initDemand(this.stations.size());
    ArrayList<Integer> maxDemand = getMaxDemand(demand, idStations, nvan);
    for (Van v: this.fleet) {
      if (maxDemand.size() == 0)
        break;
      v.setOriginStationID(maxDemand.get(0));
      maxDemand.remove(0);
    }
  }

  //  ------------------------------ Modifiers ------------------------------- //

  private void setIsVisited(final ArrayList<Boolean> isVisited) {
    initIsVisited(isVisited.size());
    for (int i = 0; i < isVisited.size(); ++i) {
      this.isVisited.set(i, isVisited.get(i));
    }
  }
  private void setTotalCost(final double tc) {
    this.totalCost = tc;
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

  public Estacion getStation(final int i) {
    return this.stations.get(i);
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

  public Van getVan(final int i) {
    return this.fleet.get(i);
  }

  public double getTotalCost() { return this.totalCost; }

  public double heuristic() {
        return  -this.totalCost;
  }

  // TODO Demandes
  // ------------------------------ Operators ------------------------------- //

  public void single_move(Estacion origin, Estacion destination, Integer taken) {

    Integer nonUsed = origin.getNumBicicletasNoUsadas();
    origin.setNumBicicletasNoUsadas(nonUsed-taken);

    Integer nextO = origin.getNumBicicletasNext();
    origin.setNumBicicletasNext(nextO-taken);

    Integer nextD = destination.getNumBicicletasNext();
    destination.setNumBicicletasNext(nextD+taken);

    calculateCost(origin, destination, taken);
  }

  public void double_move(Estacion origin, Estacion first_destination, Estacion second_destination, Integer taken) {

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
    double distance = Math.abs(origin.getCoordX() - destination.getCoordX() + Math.abs(origin.getCoordY() - destination.getCoordY()));
    double cost = distance * kilometer_cost;
    totalCost += cost;
  }

}
