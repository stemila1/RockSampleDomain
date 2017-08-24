package rocksample;

import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static rocksample.RockSample.*;

public class RockSampleObserv implements MutableState{

	public String scan;
	private List<String> rockFeedback =  new ArrayList<String>();
	
	//list to store the returned feedback
	public RockSampleObserv(List<String> rockFeedback){
		
		scan = ACTION_SCAN_NOTHING;
		this.rockFeedback = rockFeedback;
	}

	
	@Override
	public RockSampleObserv copy() {
		//throw new RuntimeException("not implemented");
		return new RockSampleObserv(rockFeedback);
	}

	@Override
	public List<String> get(Object variableKey) {
		//throw new RuntimeException("not implemented");
		return rockFeedback;
	}

	@Override
	public List<Object> variableKeys() {
		//throw new RuntimeException("not implemented");
		return Arrays.<Object>asList(ACTION_SCAN);
	}

	@Override
	public MutableState set(Object variableKey, Object value) {
		//throw new RuntimeException("not implemented");
		if(!(value instanceof String)){
			throw new RuntimeException("Value must be a string");
		}
		
		List<String> feedback = (List<String>)value;
		if(!rockFeedback.equals() ){
			throw new RuntimeException("");
		}
		this.rockFeedback = rockFeedback;
		
		return this;
	}

	
	
}
