package rocksample.state;

import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.State;

public class RockSampleDebris extends RSLocalObject{

public static final String CLASS_NAME = "RSDebris";
	
	public RockSampleDebris(String name, int x, int y){
		super(name, x, y);
	}
	
	@Override
	public String className() {
		return CLASS_NAME;
	}

	@Override
	public ObjectInstance copyWithName(String s) {
		return new RockSampleDebris(s, super.getX(), super.getY());
	}

	@Override
	public State copy() {
		return copyWithName(super.name);
	}
}
