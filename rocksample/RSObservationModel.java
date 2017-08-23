package rocksample;

import java.util.List;

import burlap.debugtools.RandomFactory;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.pomdp.observations.DiscreteObservationFunction;
import burlap.mdp.singleagent.pomdp.observations.ObservationProbability;
import burlap.mdp.singleagent.pomdp.observations.ObservationUtilities;
import rocksample.state.RockSampleState;

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
		List<State> result = new ArrayList<State>();
		
		//result.add(State e);
		
		throw new RuntimeException("all observations not implemented");
		/*if(includeDoNothing){
			result.add(this.observationNothing());
		}*/
	}
	
	@Override
	public double probability(State s, State s_prime, Action action) {
		
		throw new RuntimeException("probability(State, State, Action) not implemented");
		
		
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
		return new RockSampleObserv(feedback);
	}

	@Override
	public List<ObservationProbability> probabilities(State arg0, Action arg1) {
		throw new RuntimeException("probabilities(State, Ation) not implemented");
	}

}
