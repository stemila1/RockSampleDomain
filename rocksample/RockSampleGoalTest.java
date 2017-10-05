package rocksample;

import burlap.mdp.core.state.State;
import burlap.mdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.common.GoalBasedRF;
import rocksample.RockSample;
//import rocksample.state.KSGridWorldGoal;
import rocksample.state.RockSampleState;
import rocksample.state.RockSampleAgent;

public class RockSampleGoalTest implements StateConditionTest{

	@Override
	public boolean satisfies(State s) {
		
		//need to check if the agent has collected all the good rocks -- success
		RockSampleAgent agent = (RockSampleAgent) s.get(RockSample.CLASS_AGENT);
		
		int x = agent.getX();
		int y = agent.getY();
		
		if(x == 4 && y == 0){
			return true;
		}
		
		return false;
		//need to check if the agent has collected at least one bad rock -- fail
		
	}

}
