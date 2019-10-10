import java.util.ArrayList;

public class BicingBoard {

    private ArrayList<int> board;
    private static ArrayList<int> goal;

    // ====================================================================== //

    // Constructor by defualt
    public BicingBoard() {}

    // Constructor with parameters
    public BicingBoard(final ArrayList<int> board, final ArrayList<int> goal) {
        this.board = new ArrayList<int> (board.size());
        this.board = board.clone();

        this.goal  = new ArrayList<int> (goal.size());

        this.goal  = goal.clone();
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
