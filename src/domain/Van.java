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

  Van(final Integer estationID) {
    this.stationID = estationID;
  }

  // ------------------------------ Modifiers ------------------------------- //

  void setOriginStationID(final Integer stationID) {
    this.stationID = stationID;
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

}
