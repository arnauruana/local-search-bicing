package domain;

import java.lang.Integer;
import java.lang.String;

import static java.lang.System.err;
import static java.lang.System.out;


public class Van {

  // ============================== ATTRIBUTES ============================== //

  private Integer stationID;     // origin station identifier
  private Integer numBikes = 0;  // number of bikes being transported

  // ------------------------------------------------------------------------ //

  static final Integer CAPACITY = 30;  // maximum number of bikes

  // =============================== METHODS ================================ //

  // ----------------------------- Constructors ----------------------------- //

  Van() {}

  Van(final Integer estationID) {
    this.stationID = estationID;
  }

  // ------------------------------ Modifiers ------------------------------- //

  void setOriginStationID(final Integer stationID) {
    this.stationID = stationID;
  }

  void setNumBikes(final Integer numBikes) {
    this.checkCapacity(numBikes);
    this.numBikes = numBikes;
  }

  // ----------------------------- Consultants ------------------------------ //

  Integer getOriginStationID() {
    return this.stationID;
  }

  Integer getNumBikes() {
    return this.numBikes;
  }

  // ------------------------------ Auxiliary ------------------------------- //

  private static void printError(final String errorMsg) {
    err.println(errorMsg);
  }

  private void checkCapacity(final Integer numBikes) {
    String error = "[Van(setNumBikes)] ERROR: number of bikes ("
            + numBikes.toString();
    if (numBikes < 0) {
      error = error + ") cannot be negative";
      Van.printError(error);
      System.exit(1);
    }
    else if (numBikes > 30) {
      error = error + ") cannot be greater than capacity ("
              + Van.CAPACITY.toString() + ")";
      Van.printError(error);
      System.exit(1);
    }
  }

  // ---------------------------- Input / Output ---------------------------- //

  private void printStationID() {
    out.println(this.stationID);
  }

  private void printNumBikes() {
    out.println(this.numBikes);
  }

  private void printCapacity() {
    out.println(Van.CAPACITY);
  }

  // ------------------------------------------------------------------------ //

  void print() {
    out.println("[Van] INFO: printing attributes...");
    out.print("  ⤷ stationID:\t"); this.printStationID();
    out.print("  ⤷ numBikes:\t");  this.printNumBikes();
    out.print("  ⤷ CAPACITY:\t");  this.printCapacity();
  }

  // -------------------------------- Driver -------------------------------- //

  public static void main(String[] args) {

    out.println();
    Van v0 = new Van();
    v0.print();

    out.println();
    Van v1 = new Van(1);
    v1.print();

    out.println();
    Integer origin2 = 2;
    Van v2 = new Van(origin2);
    origin2 = 200;
    v2.print();

    out.println();
    Van v3 = new Van();
    v3.setOriginStationID(3);
    v3.print();

    out.println();
    Van v4 = new Van(400);
    v4.setOriginStationID(4);
    v4.print();

    out.println();
    Van v5 = new Van(500);
    Integer origin5 = 5;
    v5.setOriginStationID(origin5);
    origin5 = 500;
    v5.print();

    out.println();
    Van v6 = new Van();
    v6.setNumBikes(6);
    v6.print();

    out.println();
    Van v7 = new Van();
    Integer numBikes7 = 7;
    v7.setNumBikes(numBikes7);
    numBikes7 = 700;
    v7.print();

    out.println();
    Van v8 = new Van();
    out.println(v8.getOriginStationID());

    out.println();
    Van v9 = new Van(9);
    out.println(v9.getOriginStationID());

    out.println();
    Integer origin10 = 10;
    Van v10 = new Van(origin10);
    origin10 = 1000;
    out.println(v10.getOriginStationID());

    out.println();
    Van v11 = new Van();
    out.println(v11.getNumBikes());

    out.println();
    Van v12 = new Van();
    v12.setNumBikes(12);
    out.println(v12.getNumBikes());

    out.println();
    Van v13 = new Van();
    Integer numBikes13 = 13;
    v13.setNumBikes(numBikes13);
    numBikes13 = 1300;
    out.println(v13.getNumBikes());

    out.println();
    Van.printError("This is an error message.");

    out.println();
    Van v15 = new Van();
    v15.setNumBikes(0);
    v15.setNumBikes(10);
    v15.setNumBikes(30);
    v15.setNumBikes(-1);
    v15.setNumBikes(31);

  }

}
