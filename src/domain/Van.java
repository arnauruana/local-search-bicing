package domain;

import java.lang.Integer;
import java.lang.String;
import static java.lang.System.err;
import static java.lang.System.out;


public class Van {

  // ============================== ATTRIBUTES ============================== //

  private Integer idOrigin;
  private Integer numBikes = 0;

  // ------------------------------------------------------------------------ //

  public static final Integer CAPACITY = 30;

  // =============================== METHODS ================================ //

  // ----------------------------- Constructors ----------------------------- //

  public Van() {}

  public Van(final Integer idOrigin) {
    this.idOrigin = idOrigin;
  }

  // ------------------------------ Modifiers ------------------------------- //

  public void setNumBikes(final Integer numBikes) {
    this.checkCapacity(numBikes, "setNumBikes");
    this.numBikes = numBikes;
  }

  public void setOriginStation(final Integer id) {
    this.idOrigin = id;
  }

  // ----------------------------- Consultants ------------------------------ //

  public Integer getIdOrigin() {
    return this.idOrigin;
  }

  public Integer getNumBikes() {
    return this.numBikes;
  }

  // ------------------------------ Auxiliary ------------------------------- //

  private void printError(final String errorMsg) {
    err.println(errorMsg);
  }

  private void checkCapacity(final Integer numBikes, final String function) {
    String error;
    if (numBikes < 0) {
      error =
        "[Van(" + function + ")] ERROR: number of bikes ("
        + numBikes.toString() + ") cannot be negative"
      ;
      printError(error);
      System.exit(1);
    }
    if (numBikes > 0) {
      error =
        "[Van(" + function + ")] ERROR: number of bikes ("
        + numBikes.toString() + ") cannot be greater than capacity ("
        + CAPACITY.toString() + ")"
      ;
      printError(error);
      System.exit(1);
    }
  }

  // ---------------------------- Input / Output ---------------------------- //

  public void print() {
    out.println("[Van] INFO: printing attributes...");
      out.print("  ⤷ idStation: "); this.printIdStation();
      out.print("  ⤷ numBikes: "); this.printNumBikes();
      out.print("  ⤷ CAPACITY: "); this.printCapacity();
  }

  public void printIdStation() {
    out.println(this.idOrigin);
  }

  public void printNumBikes() {
    out.println(this.numBikes);
  }

  public void printCapacity() {
    out.println(this.CAPACITY);
  }

}
