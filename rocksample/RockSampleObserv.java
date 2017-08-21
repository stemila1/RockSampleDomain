package rocksample;

import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static rocksample.RockSample.*;

public class RockSampleObserv implements MutableState{

	List<String> rockFeedback =  new ArrayList<String>();
	
	//list to store the returned feedback
	public RockSampleObserv(List<String> rockFeedback){
		
		this.rockFeedback = rockFeedback;
	}

	
	@Override
	public State copy() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Object get(Object variableKey) {
		
		throw new RuntimeException("not implemented");
	}

	@Override
	public List<Object> variableKeys() {
		//throw new RuntimeException("not implemented");
		return Arrays.<Object>asList(ACTION_SCAN);
	}

	@Override
	public MutableState set(Object variableKey, Object value) {
		throw new RuntimeException("not implemented");
	}

	
	
}
