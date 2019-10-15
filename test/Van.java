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
    this.numBikes = sub;
  }

  // ---------------------------- Input / Output ---------------------------- //

  public void print() {
    out.println("[Van] INFO: printing attributes...");
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

  // -------------------------------- Driver -------------------------------- //

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
    Van.testVan0();
    Van.testVan1();
    Van.testVan2();
    Van.testVan3();
    Van.testVan4();
  }

  private static void testVan0() {
    out.println("***** Van() *****"); out.println("");
    Van van = new Van();
    van.print(); out.println("");
  }

  private static void testVan1() {
    out.println("***** Van(Point(1,1)) *****"); out.println("");
    Van van = new Van(new Point(1, 1));
    van.print(); out.println("");
  }

  private static void testVan2() {
    out.println("***** Van(Point(2,2),Integer(2)) *****"); out.println("");
    Van van = new Van(new Point(2, 2), new Integer(2));
    van.print(); out.println("");
  }

  private static void testVan3() {
    out.println("***** Van(Integer(3),Integer(3)) *****"); out.println("");
    Van van = new Van(new Integer(3), new Integer(3));
    van.print(); out.println("");
  }

  private static void testVan4() {
    out.println("***** Van(Integer(4),Integer(4),Intger(4)) *****");
    out.println("");
    Van van = new Van(new Integer(4), new Integer(4), new Integer(4));
    van.print(); out.println("");
  }

  // ------------------------------------------------------------------------ //

  private static void testModifiers() {
    out.println("------------------------ Modifiers -------------------------");
    out.println("");
    Van.testSetPosition0();
    Van.testSetPosition1();
    Van.testSetNumBikes();
  }

  private static void testSetPosition0() {
    out.println("***** void setPosition(Point(0,0)) *****"); out.println("");
    Van van = new Van(new Point(-1, -1));
    van.print(); out.println("");
    van.setPosition(new Point(0, 0));
    van.print(); out.println("");
  }

  private static void testSetPosition1() {
    out.println("***** void setPosition(Integer(1),Integer(1)) *****\n");
    Van van = new Van(new Point(-1, -1));
    van.print(); out.println("");
    van.setPosition(new Integer(1), new Integer(1));
    van.print(); out.println("");
  }

  private static void testSetNumBikes() {
    out.println("***** void setNumBikes(Integer(5)) *****\n");
    Van van = new Van(new Point(-1, -1), new Integer(2));
    van.print(); out.println("");
    van.setNumBikes(new Integer(5));
    van.print(); out.println("");
  }

  // ------------------------------------------------------------------------ //

  private static void testConsultants() {
    out.println("----------------------- Consultants ------------------------");
    out.println("");
    Van.testGetPosition();
    Van.testGetCoordX();
    Van.testGetCoordY();
    Van.testGetNumBikes();
  }

  private static void testGetPosition() {
    out.println("***** Point getPosition() *****\n");
    Van van = new Van(new Point(1, 1));
    out.println("Position: " + van.getPosition()); out.println("");
    van.print(); out.println("");
  }

  private static void testGetCoordX() {
    out.println("***** Integer getCoordX() *****\n");
    Van van = new Van(new Point(2, 0));
    out.println("CoordX: " + van.getCoordX()); out.println("");
    van.print(); out.println("");
  }

  private static void testGetCoordY() {
    out.println("***** Integer getCoordY() *****\n");
    Van van = new Van(new Point(0, 3));
    out.println("CoordY: " + van.getCoordY()); out.println("");
    van.print(); out.println("");
  }

  private static void testGetNumBikes() {
    out.println("***** Integer getNumBikes() *****\n");
    Van van = new Van(new Point(0, 0), new Integer(10));
    out.println("NumBikes: " + van.getNumBikes()); out.println("");
    van.print(); out.println("");
  }

  // ------------------------------------------------------------------------ //

  private static void testAuxiliary() {
    out.println("------------------------ Auxiliary -------------------------");
    out.println("");
    Van.testMoveTo0();
    Van.testMoveTo1();
    Van.testTakeBikes();
    Van.testDropBikes();
  }

  private static void testMoveTo0() {
    out.println("***** void moveTo(Point(5,2)) *****\n");
    Van van = new Van(new Point(-1, -1), new Integer(0));
    van.print(); out.println("");
    van.moveTo(new Point(5,2));
    van.print(); out.println("");
  }

  private static void testMoveTo1() {
    out.println("***** void moveTo(Integer(2),Integer(3)) *****\n");
    Van van = new Van(new Point(-1, -1), new Integer(0));
    van.print(); out.println("");
    van.moveTo(new Integer(2), new Integer(3));
    van.print(); out.println("");
  }

  private static void testTakeBikes() {
    out.println("***** void takeBikes(Integer(10)) *****\n");
    Van van = new Van(new Point(-1, -1), new Integer(5));
    van.print(); out.println("");
    van.takeBikes(new Integer(20));
    van.print(); out.println("");
  }

  private static void testDropBikes() {
    out.println("***** void dropBikes(Integer(10)) *****\n");
    Van van = new Van(new Point(-1, -1), new Integer(25));
    van.print(); out.println("");
    van.dropBikes(new Integer(20));
    van.print(); out.println("");
  }

  // ------------------------------------------------------------------------ //

  private static void testIO() {
    out.println("--------------------------- I/O ----------------------------");
    out.println("");
    Van.testPrintPosition();
    Van.testPrintNumBikes();
    Van.testPrintCapacity();
    Van.testPrint();
  }

  private static void testPrintPosition() {
    out.println("***** void printPosition() *****\n");
    Van van = new Van(new Point(10, 10), new Integer(10));
    out.print("Position: "); van.printPosition(); out.println("");
    van.print(); out.println("");
  }

  private static void testPrintNumBikes() {
    out.println("***** void printNumBikes() *****\n");
    Van van = new Van(new Point(10, 10), new Integer(10));
    out.print("NumBikes: "); van.printNumBikes(); out.println("");
    van.print(); out.println("");
  }

  private static void testPrintCapacity() {
    out.println("***** void printCapacity() *****\n");
    Van van = new Van(new Point(10, 10), new Integer(10));
    out.print("Capacity: "); van.printCapacity(); out.println("");
    van.print(); out.println("");
  }

  private static void testPrint() {
    out.println("***** void print() *****\n");
    Van van = new Van(new Point(10, 10), new Integer(10));
    van.print(); out.println("");
  }

  // ------------------------------------------------------------------------ //

  public static void main(String[] args) {
    Van.printHeader();
    Van.testConstructors();
    Van.testModifiers();
    Van.testConsultants();
    Van.testAuxiliary();
    Van.testIO();
    Van.printFooter();
  }

}
