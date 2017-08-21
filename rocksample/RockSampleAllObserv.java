package rocksample;

import java.util.List;

import burlap.debugtools.RandomFactory;
import burlap.mdp.core.action.Action;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.pomdp.observations.DiscreteObservationFunction;
import burlap.mdp.singleagent.pomdp.observations.ObservationProbability;
import burlap.mdp.singleagent.pomdp.observations.ObservationUtilities;

import java.util.ArrayList;
import java.util.List;

public class RockSampleAllObserv implements DiscreteObservationFunction{

	protected double scanAccuracy;
	protected boolean includeDoNothing;
	
	@Override
	public List<State> allObservations() {
		
		List<State> result = new ArrayList<State>(3);
		
		result.add(State e);
		
		if(includeDoNothing){
			result.add(this.observationNothing());
		}
	}
	
	@Override
	public double probability(State s, State s_prime, Action action) {
		throw new RuntimeException("probability(State, State, Action) not implemented");
	}

	@Override
	public State sample(State s0, Action a1) {
		throw new RuntimeException("sample(State, Action) not implemented");
	}

	@Override
	public List<State> allObservations() {
		throw new RuntimeException("allObservations() not implemented");
	}

	@Override
	public List<ObservationProbability> probabilities(State arg0, Action arg1) {
		throw new RuntimeException("probabilities(State, Ation) not implemented");
	}

}
