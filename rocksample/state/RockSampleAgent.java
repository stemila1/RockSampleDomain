package rocksample.state;

import rocksample.RockSample;

public class RockSampleAgent extends RSLocalObject{

	
	public RockSampleAgent(String name, int x, int y){
		super(name, x, y);
	}
	
	@Override
	public String className(){
		return RockSample.CLASS_AGENT;
	}
	
	@Override
	public RockSampleAgent copyWithName(String name){
		return new RockSampleAgent(name, super.getX(), super.getY());
	}
	
	@Override
	public RockSampleAgent copy(){
		return copyWithName(this.name);
	}
	
	@Override 
	public boolean equals(Object other){
		return false;
	}
	
}

