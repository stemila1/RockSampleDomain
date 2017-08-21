package rocksample.state;

public class RockSampleBlock extends RSLocalObject {

	public static final String CLASS_NAME = "RSBlock";
	
	public RockSampleBlock(String name, int x, int y){
		super(name, x, y);
	}
	
	public static String staticName(){
		return CLASS_NAME;
	}
	
	@Override
	public RockSampleBlock copyWithName(String s) {
		return new RockSampleBlock(s, super.getX(), super.getY());
	}
	
	@Override
	public RockSampleBlock copy() {
		return copyWithName(super.name);
	}

	@Override
	public String className() {
		return CLASS_NAME;
	}
	
}
