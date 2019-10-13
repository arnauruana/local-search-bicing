import java.awt.Point;
import java.lang.Integer;
import java.lang.String;
import static java.lang.System.out;


public class Van {

  // ============================== ATTRIBUTES ============================== //

  private Point position;
  private Integer numBikes;

  // ------------------------------------------------------------------------ //

  public static final Integer CAPACITY = 30;

  // =============================== METHODS ================================ //

  private void printError(final String errorMsg) {
    out.println(errorMsg);
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

  // ----------------------------- Constructors ----------------------------- //

  public Van() {}

  public Van(final Point position) {
    this.setPosition(position);
  }

  public Van(final Point position, final Integer numBikes) {
    this(position);
    this.setBikes(numBikes);
  }

  public Van(final Integer x, final Integer y) {
    this.setPosition(x, y);
  }

  public Van(final Integer x, final Integer y, final Integer numBikes) {
    this(x, y);
    this.setBikes(numBikes);
  }

  // ------------------------------ Modifiers ------------------------------- //

  public void setPosition(final Point position) {
    this.position.setLocation(position);
  }

  public void setPosition(final Integer x, final Integer y) {
    this.position.setLocation(x, y);
  }

  public void setBikes(final Integer numBikes) {
    this.checkCapacity(numBikes, "setBikes");
    this.numBikes = numBikes.intValue();
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

  public void moveTo(final Point position) {
    this.moveTo((int)position.getX(), (int)position.getY());
  }

  public void moveTo(final Integer x, final Integer y) {
    this.position.move(x, y);
  }

  public void takeBikes(final Integer numBikes) {
    Integer sum = this.numBikes.intValue() + numBikes.intValue();
    checkCapacity(sum, "takeBikes");
    this.numBikes = sum.intValue();
  }

  public void dropBikes(final Integer numBikes) {
    Integer sub = this.numBikes.intValue() - numBikes.intValue();
    checkCapacity(sub, "dropBikes");
    this.numBikes = sub.intValue();
  }

}
