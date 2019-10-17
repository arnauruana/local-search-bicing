package test;

import IA.Bicing.Estacion;
import IA.Bicing.Estaciones;
import domain.State;
import domain.Van;

import java.util.ArrayList;

import static java.lang.System.out;


public class DriverState {

    // ============================== ATTRIBUTES ============================== //

    private static State state;

    // ---------------------------- Input / Output ---------------------------- //

    public static void printState() {
        out.println("[State] INFO: printing attributes...");
        out.print("  ⤷ stations: "); printStations();
        out.print("  ⤷ isVisited: "); printIsVisited();
        out.print("  ⤷ fleet: "); printFleet();
    }

    public static void printStations() {
        out.println("[Stations] ");
        for (int i = 0; i < state.getStations().size(); i++)
            printStation(state.getStation(i));
    }

    public static void printStation(Estacion e) {
        out.print("X: "); out.println(e.getCoordX());
        out.print("Y: "); out.println(e.getCoordY());
        out.print("Demanda: "); out.println(e.getDemanda());
        out.print("NumBicisNext "); out.println(e.getNumBicicletasNext());
        out.print("NumBicisNoUsades "); out.println(e.getNumBicicletasNoUsadas());
        out.println();
    }

    public static void printIsVisited() {
        out.println("[IsVisited] ");
        for (Boolean b: state.getIsVisited())
            out.print(b);
    }

    public static void printFleet() {
        out.println("[IsVisited] ");
        for (Van v : state.getFleet()) {
            DriverVan dVan = new DriverVan(v.getPosition(), v.getNumBikes());
            dVan.print();
        }
    }


    // =============================== METHODS ================================ //

    private static void printHeader() {
        out.println("");
        out.println("=========================== VAN ============================");
        out.println("");
    }

    private static void printFooter() {
        out.println("============================================================");
        out.println("");
    }

    // ------------------------------------------------------------------------ //

    private static void testConstructors() {
        out.println("----------------------- Constructors -----------------------");
        out.println("");
        DriverState.testState0();
    }

    private static void testState0() {
        out.println("***** DriverState() *****"); out.println("");
        Estaciones estaciones = new Estaciones(10, 300, Estaciones.EQUILIBRIUM, 4);
        ArrayList<Van> fleet = new ArrayList<>(10);
        state = new State(estaciones, fleet);
        printState(); out.println("");
    }

    // ------------------------------------------------------------------------ //



    public static void main(String[] args) {
        DriverState.printHeader();
        DriverState.testConstructors();
        DriverState.printFooter();
    }
}
