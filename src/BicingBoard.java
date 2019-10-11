import java.util.ArrayList;

public class BicingBoard {

    private ArrayList<Integer> board;
    private static ArrayList<Integer> goal;

    // ====================================================================== //

    // Constructor by defualt
    public BicingBoard() {}

    // Constructor with parameters
    public BicingBoard(final ArrayList<Integer> board, final ArrayList<Integer> goal) {
        this.board = new ArrayList<> (board.size());
        this.goal  = new ArrayList<> (goal.size());

        for (int i = 0; i< board.size(); i++) {
            this.board.set(i, board.get(i));
            this.goal.set(i, goal.get(i));
        }
    }

    /* vvvvv TO COMPLETE vvvvv */
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

     /* auxiliary functions */

     // Some functions will be needed for creating a copy of the state

    /* ^^^^^ TO COMPLETE ^^^^^ */

}
