package rocksample;

import java.util.ArrayList;
import java.util.List;

import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.core.state.State;

public class RockSampleTF implements TerminalFunction{

	public RockSampleTF(){
		
	}
	
	//once task has been completed or agent has gotten to a state
	//that cannot be returned from, this is terminal
	@Override 
	public boolean isTerminal(State s){
		
		boolean isTS = false;
		
		//collected all good rocks or collected at least one bad rock
		if (RockSample.scannedGoodRock(s) || RockSample.scannedBadRock(s)){ // TODO change to scanned all good rocks
			isTS = true;
		}
		
		return isTS;
	}
	
}
