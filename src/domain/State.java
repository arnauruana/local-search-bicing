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

  // =============================== METHODS ================================ //

  // ----------------------------- Constructors ----------------------------- //

  // TODO -> comprovar final
  public State(final Estaciones stations, final ArrayList<Van> fleet) {
    // this.setStations(stations);
    this.stations = stations;
    this.initIsVisited();
  }

  public State(final State state) {
    // this.setStations(state.stations);
    this.stations = state.stations;
    this.setIsVisited(state.isVisited);
    this.setFleet(state.fleet);
  }

  // ----------------------------- Initializers ----------------------------- //

  private void initIsVisited() {
    this.isVisited = new ArrayList<> (this.stations.size());
    for (int i = 0; i < this.isVisited.size(); ++i) {
      this.isVisited.set(i, false);
    }
  }

  // ------------------------------ Modifiers ------------------------------- //

  // private void setStations(final Estaciones stations) {
  //   this.stations = new Estaciones (stations.size()); // FIXME
  //   for (int i = 0; i < stations.size(); ++i) {
  //     this.stations.set(i, stations.get(i));
  //   }
  // }

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

    // ------------------------------ Operators ------------------------------- //

    public double single_move (Estacion origin, Estacion destination, Integer taken) {

        Integer disponible = origin.getNumBicicletasNoUsadas();
        origin.setNumBicicletasNoUsadas(disponible-taken);

        Integer demand = destination.getDemanda();
        destination.setDemanda(demand-taken);

        return calculateCost(origin, destination, taken);
    }


    public double double_move (Estacion origin, Estacion first_destination, Estacion second_destination, Integer taken) {

        // First move
        Integer disponible = origin.getNumBicicletasNoUsadas();
        origin.setNumBicicletasNoUsadas(disponible-taken);
        Integer demand = first_destination.getDemanda();
        first_destination.setDemanda(0);
        // Update Van
        taken -= demand;

        // Second move
        second_destination.setDemanda(second_destination.getDemanda()- taken);

        return calculateCost(origin, first_destination, taken+demand) + calculateCost(first_destination, second_destination, taken);
    }


    private double calculateCost (Estacion origin, Estacion destination, Integer taken) {
        Integer kilometer_cost = (taken + 9)/10;
        double distance = Math.abs(origin.getCoordX() - destination.getCoordX() + Math.abs(origin.getCoordY() - destination.getCoordY()));
        return distance * kilometer_cost;
    }

    // ------------------------------ Check constraints  ------------------------------- //

    public boolean VisitedStation (Integer station) {
        return isVisited.get(station);
    }

}


