package rocksample;

import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static rocksample.RockSample.*;

public class RockSampleObserv implements MutableState{

	//public String scan;
	private List<String> rockFeedback =  new ArrayList<String>();
	
	//return the string type quality of the rock
	public RockSampleObserv(String quality){
		
	}
	
	
	//list to store the returned feedback
	public RockSampleObserv(List<String> rockFeedback){
		
		//scan = ACTION_SCAN_NOTHING;
		this.rockFeedback = rockFeedback;
	}

	
	@Override
	public RockSampleObserv copy() {
		throw new RuntimeException("not implemented");
		//return new RockSampleObserv(rockFeedback);
	}

	@Override
	public Object get(Object variableKey) {
		//throw new RuntimeException("not implemented");
		if(variableKey.equals(VAR_FEEDBACK)){
			return rockFeedback;
		}
		
		else{
			throw new RuntimeException("other variable key");
		}
	}

	@Override
	public List<Object> variableKeys() {
		//throw new RuntimeException("not implemented");
		return Arrays.<Object>asList(VAR_FEEDBACK);
	}

	@Override
	public MutableState set(Object variableKey, Object value) {
		throw new RuntimeException("not implemented");
		
		/*if(!(value instanceof String)){
			throw new RuntimeException("Value must be a string");
		}
		
		List<String> feedback = (List<String>)value;
		if(!rockFeedback.equals(value)){//TODO
			throw new RuntimeException("");
		}
		this.rockFeedback = rockFeedback;
		
		return this;*/
	}

	public List<String> getRockFeedback() {
		return rockFeedback;
	}

	public void setRockFeedback(List<String> rockFeedback) {
		this.rockFeedback = rockFeedback;
	}
	
}
