package rocksample;

import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;

//if using a FullModel
//import burlap.mdp.singleagent.environment.EnvironmentOutcome;
//import burlap.mdp.singleagent.model.FullModel;

//import burlap.mdp.singleagent.model.FactoredModel;
import burlap.mdp.singleagent.model.TransitionProb;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;
import burlap.mdp.singleagent.model.statemodel.SampleStateModel;
import rocksample.RockSample.*;
import rocksample.state.RockSampleAgent;
import rocksample.state.RockSampleBlock;
import rocksample.state.RockSampleDebris;
import rocksample.state.RSLocalObject;
import rocksample.state.RockSampleState;

//import java.lang.Object;
import java.util.Collections;
import java.util.List;

public class RockSampleModel implements SampleStateModel{ //implements FullModel

	public double scan = 0.0;
	public double goodRockCollect = 10.0;
	public double badRock = -100.0; //or badRockCollectReward
	public double nothing = 0.0;
	
	public RockSampleModel(double good, double bad, double scan, double nothing){
		
		this.goodRockCollect = good;
		this.badRock = bad;
		this.scan = scan;
		this.nothing = nothing;
	}
	
	public List<StateTransitionProb> stateTransitions(State s, Action a){
		return FullStateModel.Helper.deterministicTransition(this, s, a);
	}
	
	
	@Override 
	public State sample(State s, Action a){
		s = s.copy();
		String actionName = a.actionName();
		
		//action is a navigation action
		if(actionName.equals(RockSample.ACTION_NORTH) || actionName.equals(RockSample.ACTION_SOUTH)
		|| actionName.equals(RockSample.ACTION_EAST) || actionName.equals(RockSample.ACTION_WEST)){
			
			return move(s, actionName);
		}
		
		//action is scan 
		else if(actionName.equals(RockSample.ACTION_SCAN)){
			//TODO
			//return tempScan(s); //temporary for testing purposes
			
			return scanForRocks(s);
		}
		
		else if(actionName.equals(RockSample.ACTION_SCAN_NOTHING)){
			return s;
		}
		
		/*
		else if (actionName.equals(RockSample.ACTION_CLEAR)){
			//TODO
			//throw new RuntimeException("Clear not implemented yet");
			return clearDebris(s, RockSample.CLASS_DEBRIS);
		}
		*/
		else if(actionName.equals(RockSample.ACTION_DO_NOTHING)){
			//TODO
			throw new RuntimeException("Do nothing not implemented yet");
		}
		
		else {
			throw new RuntimeException("Unknown action" + actionName);	
		}
		
	}
	
	
	//need move to return another State
	public State move(State s, String actionName){
		
		RockSampleState rstate = (RockSampleState)s;
		RockSampleAgent agent = rstate.getAgent();
		
		int direction = actionDir(actionName);
		//current x and y coordinates
		int x_pos = (Integer) agent.get(RockSample.POS_X);
		int y_pos = (Integer) agent.get(RockSample.POS_Y);
		//change in x and y
		int xdelta = 0;
		int ydelta = 0;
		
		//north
		if(direction == 0){
			ydelta = 1;
		}
		//south
		else if(direction == 1){
			ydelta = -1;
		}
		//east
		else if(direction == 2){
			xdelta = 1;
		}
		//west
		else if(direction == 3){
			xdelta = -1;
		}
		else
			throw new RuntimeException("Unknown direction taken");
		
		int nx = x_pos + xdelta;
		int ny = y_pos + ydelta; 
		
		
		boolean agentCanMove = false;
		
		//be sure that the width is less than the new x
		//and the height is less than the new y
		if(rstate.getWidth() > nx && rstate.getHeight() > ny && nx >= 0 && ny >= 0){
			//agent is able to move within the boundaries
			agentCanMove = true;
			//check if the agent's coordinates are the same as a block object
			//if so, agentCanMove is false
			List<ObjectInstance> blocks = rstate.objectsOfClass(RockSample.CLASS_BLOCK);
			for(ObjectInstance block: blocks){
				RockSampleBlock b = (RockSampleBlock) block;
				if(nx == b.getX()){
					if(ny == b.getY()){
						agentCanMove = false;
						break;
					}
				}
				
			}
			
		}	
		
		RockSampleAgent nAgent = rstate.touchAgent();
		if (agentCanMove){
			
			nAgent.set(RockSample.POS_X, nx);
			nAgent.set(RockSample.POS_Y, ny);
		}
		
		return s;
	}
	
	//
	protected static int actionDir(String actionName) {
		
		int direction = -1;
		
		if(actionName.equals(RockSample.ACTION_NORTH)){
			direction = 0;
		}
		else if(actionName.equals(RockSample.ACTION_SOUTH)){
			direction = 1;
		}
		
		else if(actionName.equals(RockSample.ACTION_EAST)){
			direction = 2;
		}
		else if(actionName.equals(RockSample.ACTION_WEST)){
			direction = 3;
		}
		
		else{
			throw new RuntimeException("ERROR: not a valid direction for" + actionName);
		}
		return direction;
	}
	
	protected int actionDir(Action a){
		return actionDir(a.actionName());
	}
	
	//need a scan action to check for rocks
	//when agent is in any state, knows how many rocks
	public State scanForRocks(State s){
		
		//simply returns the current state
		s = (RockSampleState)s;
		
		return s;		
	}
	
	public void collectRocks(State s){
		/*TODO once a scan is completed, if a rock 
		 * is in close proximity to the agent then the agent
		 * will go to it and collect it (depending on the feedback
		 */
		RockSampleState rs = (RockSampleState)s;
		
	}

	public State clearDebris(State s, String whatIsThis){
		
		throw new RuntimeException("clear not necessary yet");
		/*
		RockSampleState rs = (RockSampleState)s;
		RockSampleAgent agent = rs.getAgent();
		//need a boolean to tell if able to clear???
		//check if the object encountered is debris and not a rock or other object
		if(whatIsThis == RockSample.CLASS_DEBRIS) { //instanceof debris
			rs.removeObject(whatIsThis);
		}
		
		return rs;
		*/
	}
	
	/*
	@Override
	public EnvironmentOutcome sample(State s, Action a) {
		
		s = s.copy();
		String actionName = a.actionName();
		
		if(actionName.equals(RockSample.ACTION_NORTH) || actionName.equals(RockSample.ACTION_SOUTH)
		|| actionName.equals(RockSample.ACTION_EAST) || actionName.equals(RockSample.ACTION_WEST)){
			
		State s_prime = move(s, actionName);
		
		double temp_reward = 0;
		
		return new EnvironmentOutcome(s, a, s_prime, temp_reward, false);
		}
		
		else {
			throw new RuntimeException("Unknown action" + actionName);
			
		}
		
	}

	@Override
	public boolean terminal(State s) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Not implemented");
	}

	@Override
	public List<TransitionProb> transitions(State s, Action a) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Not implemented");
	}
	*/
	
}
