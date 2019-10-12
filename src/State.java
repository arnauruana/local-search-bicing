import java.lang.Integer;
import java.util.ArrayList;

import IA.Bicing.Estaciones;


public class State {

  // ============================== ATTRIBUTES ============================== //

  // TODO (pensar atributs) TODO //
  private ArrayList<Integer> board;
  private static ArrayList<Integer> goal;

  // =============================== METHODS ================================ //

  // ----------------------------- Constructors ----------------------------- //

  public State() {}

  public State(final ArrayList<Integer> board, final ArrayList<Integer> goal) {
    this.setBoard(board);
    this.setGoal(goal);
  }

  // ------------------------------ Modifiers ------------------------------- //

  public void setBoard(final ArrayList<Integer> board) {
    this.board = new ArrayList<> (board.size());
    for (int i = 0; i < board.size(); ++i)
      this.board.set(i, board.get(i));
  }

  public void setGoal(final ArrayList<Integer> goal) {
    this.goal = new ArrayList<> (goal.size());
    for (int i = 0; i < goal.size(); ++i)
      this.goal.set(i, goal.get(i));
  }

  // ----------------------------- Consultants ------------------------------ //

  public ArrayList<Integer> getBoard() {
    return this.board;
  }

  public ArrayList<Integer> getGoal() {
    return this.goal;
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
