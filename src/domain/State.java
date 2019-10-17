package domain;

import java.lang.Boolean;
import java.lang.Integer;
import java.util.ArrayList;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;


public class State {

  // ============================== ATTRIBUTES ============================== //

  private Estaciones stations;
  private ArrayList<Boolean> isVisited;

  private ArrayList<Van> fleet;
  private ArrayList<Integer> visitCount;

  // =============================== METHODS ================================ //

  // ----------------------------- Initializers ----------------------------- //

  private void initIsVisited() {
    this.isVisited = new ArrayList<> (this.stations.size());
    for (int i = 0; i < this.isVisited.size(); ++i) {
      this.isVisited.set(i, false);
    }
  }

  private void initVisitCount() {
    this.visitCount = new ArrayList<> (this.fleet.size());
    for (int i = 0; i < this.visitCount.size(); ++i) {
      this.visitCount.set(i, 0);
    }
  }

  // ----------------------------- Constructors ----------------------------- //

  // TODO -> comprovar final
  public State(final Estaciones stations, final ArrayList<Van> fleet) {
    this.setStations(stations);
    this.initIsVisited();
    this.setFleet(fleet);
    this.initVisitCount();
  }

  public State(final State state) {
    this.setStations(state.stations);
    this.setIsVisited(state.isVisited);
    this.setFleet(state.fleet);
    this.setVisitCount(state.visitCount);
  }

  // ------------------------------ Modifiers ------------------------------- //

  private void setStations(final Estaciones stations) {
    this.stations = new Estaciones (stations.size()); // FIXME
    for (int i = 0; i < stations.size(); ++i) {
      this.stations.set(i, stations.get(i));
    }
  }

  private void setIsVisited(final ArrayList<Boolean> isVisited) {
    this.isVisited = new ArrayList<> (isVisited.size());
    for (int i = 0; i < isVisited.size(); ++i) {
      this.isVisited.set(i, isVisited.get(i));
    }
  }

  private void setFleet(final ArrayList<Van> fleet) {
    this.fleet = new ArrayList<> (fleet.size());
    for (int i = 0; i < fleet.size(); ++i) {
      this.fleet.set(i, fleet.get(i));
    }
  }

  private void setVisitCount(final ArrayList<Integer> visitCount) {
    this.visitCount = new ArrayList<Integer> (visitCount.size());
    for (int i = 0; i < visitCount.size(); ++i) {
      this.visitCount.set(i, visitCount.get(i));
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

  public ArrayList<Integer> getVisitCount() {
    return this.visitCount;
  }

  public Integer visitCount(final int i) {
    return this.visitCount.get(i);
  }

  // vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv TODO vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv //

  public void flip_it(int i){
      // flip the coins i and i + 1
  }

  /* Heuristic function */
  public double heuristic(){
      // compute the number of coins out of place respect to solution
      return 0;
  }

   /* Goal test */
   public boolean is_goal(){
       // compute if board = solution
       return false;
   }

   // Some functions will be needed for creating a copy of the state

   // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ TODO ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ //

}
