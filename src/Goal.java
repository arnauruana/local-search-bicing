import aima.search.framework.GoalTest;


public class Goal implements GoalTest {

    public boolean isGoalState(Object state){

        return((State) state).is_goal();
    }
}
