package rocksample;

import java.util.List;

import burlap.debugtools.RandomFactory;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.pomdp.observations.DiscreteObservationFunction;
import burlap.mdp.singleagent.pomdp.observations.ObservationProbability;
import burlap.mdp.singleagent.pomdp.observations.ObservationUtilities;
import rocksample.state.RockSampleAgent;
import rocksample.state.RockSampleState;
import rocksample.RockSampleObserv;

import java.util.ArrayList;
import java.util.List;

public class RSObservationModel implements DiscreteObservationFunction{

	protected double scanAccuracy;
	//protected boolean includeDoNothing;
	
	public RSObservationModel(double scanAccuracy){
		this.scanAccuracy = scanAccuracy;
	}
	
	@Override
	public List<State> allObservations() {
		//TODO
		List</*State*/RockSampleObserv> result = new ArrayList</*State*/RockSampleObserv>();
		
		result.add(); //need to add observations
		
		return result;
		
		throw new RuntimeException("all observations not implemented");
	}
	
	@Override
	public double probability(State observation, State s_prime, Action action) {
		
		//throw new RuntimeException("probability(State, State, Action) not implemented");
		String obsVal = (String)observation.get(RockSample.ACTION_SCAN);
		
		if(action.actionName().equals(RockSample.ACTION_SCAN)){
			throw new RuntimeException("if action equals scan");
		}
		
		/*else if(action.actionName().equals(RockSample.ACTION_CLEAR)){
			
		}*/
		
		//return scanAccuracy;
	}
	
	//computation for distance feedback
	//RETURN GOOD OR BAD DEPENDING ON DISTANCE
	//need to return some form of noise
	public void feedback(State s, RockSampleAgent agent, int mapWidth, int mapHeight){
		
		/*1st Option (scan entire map)
		 * feedback accuracy is determined by how far away the
		 * rock or object is from the agent
		 */
		//TODO
		//to get the distance, use the Pythagorean theorem
		int distX_AgentToRock  = Math.abs(agent.getX()/* - x coordinate of a rock*/);
		int distY_AgentToRock =  Math.abs(agent.getY()/* - y coordinate of a rock*/); 
		double hypDist =  Math.sqrt(((distX_AgentToRock*distX_AgentToRock) + (distY_AgentToRock*distY_AgentToRock)));
		
		double mapDist = Math.sqrt((mapWidth*mapWidth)+(mapHeight*mapHeight));
		
		/*
		 * if the distance from the agent to the rock is within 25% if the 
		 * map Distance
		 */
		double feedback_Accuracy;
		
		if (hypDist < (0.25*mapDist)){
			feedback_Accuracy = 20.0;
		}
		
		/*
		 * if the distance from the agent to the rock is between 25% 
		 * and 50% of the map Distance
		 */
		else if((hypDist > (0.25*mapDist)) && (hypDist <= (0.5*mapDist))){
			feedback_Accuracy = 10.0;
			
			
		}
		
		else if(hypDist > (0.5*mapDist)){
			feedback_Accuracy = 1.0;
		}
		
		//if feedback accuracy within certain range, quality is good or bad	
		else{
			throw new RuntimeException("Feedback accuracy is not within proper accuracy range");
		}
		
		
		
		//------------------------------------------------------------------------------
		/*2nd Option (scan radius)
			 * for scan radius, check if the object's x or y coordinate
			 * is less than or equal to the agent's x or y plus whatever				 * range----therefore the object is within the particular
				 * scan radius
				 */
				//TODO
		
	}

	@Override
	public State sample(State state, Action action) {
		//throw new RuntimeException("sample(State, Action) not implemented");
		
		/*
		 * must return an observation state depending on your action and your noise
		 */
		/*if(action.actionName().equals(RockSample.ACTION_SCAN)){
			return new this.observationReset();
		}*/
		
		//cast the state to a rocksample state
		RockSampleState s = (RockSampleState) state;
		
		List<String> feedback = new ArrayList<String>();
		int numRocks = s.objectsOfClass(RockSample.CLASS_ROCK).size();
		for(int i = 1; i < numRocks; i++){
			feedback.add(RockSample.QUALITY_UNKNOWN);
		}
		
		if(action.actionName().equals(RockSample.ACTION_NORTH) || action.actionName().equals(RockSample.ACTION_SOUTH)
				|| action.actionName().equals(RockSample.ACTION_EAST) || action.actionName().equals(RockSample.ACTION_WEST)){

			return new RockSampleObserv(feedback);
		}
		
		else if(action.actionName().equals(RockSample.ACTION_SCAN)){
			return new RockSampleObserv(feedback);
		}
		
		else{
			throw new RuntimeException("Unknown action type in sample");
		}
	
	}

	@Override
	public List<ObservationProbability> probabilities(State arg0, Action arg1) {
		throw new RuntimeException("probabilities(State, Action) not implemented");
	}
	
	protected State observationRockQuality(){
		throw new RuntimeException("observation for rock's quality not implemented");
		//return new RockSampleObserv(null));
	}

	
	
	//TODO - will need to cast to a State but return a string?
	
	//need to return the observation of scanning a good rock
	protected State observationGood(){
		return RockSampleObserv(RockSample.QUALITY_GOOD);
	}
	
	//TODO
	//need to return the observation of scanning a bad rock
	protected State observationBad(){
		return RockSampleObserv(RockSample.QUALITY_BAD);
	}
}
