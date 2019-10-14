import java.awt.Point;
import java.lang.Integer;
import java.lang.String;
import static java.lang.System.err;
import static java.lang.System.out;


public class Van {

  // ============================== ATTRIBUTES ============================== //

  private Point position;
  private Integer numBikes;

  // ------------------------------------------------------------------------ //

  public static final Integer CAPACITY = 30;

  // =============================== METHODS ================================ //

  // ----------------------------- Constructors ----------------------------- //

  public Van() {}

  public Van(final Point position) {
    this.position = new Point(position);
  }

  public Van(final Point position, final Integer numBikes) {
    this(position);
    this.setNumBikes(numBikes);
  }

  public Van(final Integer x, final Integer y) {
    this(new Point(x, y));
  }

  public Van(final Integer x, final Integer y, final Integer numBikes) {
    this(new Point(x, y), numBikes);
  }

  // ------------------------------ Modifiers ------------------------------- //

  public void setPosition(final Point position) {
    this.position.setLocation(position);
  }

  public void setPosition(final Integer x, final Integer y) {
    this.position.setLocation(x, y);
  }

  public void setNumBikes(final Integer numBikes) {
    this.checkCapacity(numBikes, "setNumBikes");
    this.numBikes = numBikes;
  }

  // ----------------------------- Consultants ------------------------------ //

  public Point getPosition() {
    return this.position;
  }

  public Integer getCoordX() {
    return (int)this.position.getX();
  }

  public Integer getCoordY() {
    return (int)this.position.getY();
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
    if (numBikes.compareTo(0) < 0) { // numBikes < 0
      error =
        "[Van(" + function + ")] ERROR: number of bikes ("
        + numBikes.toString() + ") cannot be negative"
      ;
      printError(error);
      System.exit(1);
    }
    if (numBikes.compareTo(this.CAPACITY) > 0) { // numBikes > CAPACITY(30)
      error =
        "[Van(" + function + ")] ERROR: number of bikes ("
        + numBikes.toString() + ") cannot be greater than capacity ("
        + this.CAPACITY.toString() + ")"
      ;
      printError(error);
      System.exit(1);
    }
  }

  public void moveTo(final Point position) {
    this.moveTo((int)position.getX(), (int)position.getY());
  }

  public void moveTo(final Integer x, final Integer y) {
    this.position.move(x, y);
  }

  public void takeBikes(final Integer numBikes) {
    Integer sum = this.numBikes + numBikes;
    checkCapacity(sum, "takeBikes");
    this.numBikes = sum;
  }

  public void dropBikes(final Integer numBikes) {
    Integer sub = this.numBikes - numBikes;
    checkCapacity(sub, "dropBikes");
    this.numBikes = sub.intValue();
  }

  // ---------------------------- Input / Output ---------------------------- //

  public void print() {
    out.println("[Van(print)]");
      out.print("  ⤷ position: "); this.printPosition();
      out.print("  ⤷ numBikes: "); this.printNumBikes();
      out.print("  ⤷ CAPACITY: "); this.printCapacity();
  }

  public void printPosition() {
    out.println(this.position);
  }

  public void printNumBikes() {
    out.println(this.numBikes);
  }

  public void printCapacity() {
    out.println(this.CAPACITY);
  }

}
