package rocksample.state;

import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;

public class RockSampleRock extends RSLocalObject{

	public static final String CLASS_NAME = "RSRock";
	
	public RockSampleRock(String name, int x, int y){
		super(name, x, y);
	}
	
	@Override
	public String className() {
		return CLASS_NAME;
	}

	@Override
	public ObjectInstance copyWithName(String s) {
		return new RockSampleRock(s, super.getX(), super.getY());
	}

	@Override
	public State copy() {
		return copyWithName(super.name);
	}

	
}
