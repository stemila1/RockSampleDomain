package rocksample.state;

import rocksample.RockSample;
import utils.MutableObject;

import java.util.Arrays;
import java.util.List;

public abstract class RSLocalObject extends MutableObject{

	protected String name;
    
    protected RSLocalObject(){
    	
    }
    
    protected RSLocalObject(String name, int x, int y) {
        this.name = name;
        super.set(RockSample.POS_X, x);
        super.set(RockSample.POS_Y, y);
    }

    protected final static List<Object> keys = Arrays.asList(
            RockSample.POS_X,
            RockSample.POS_Y);

    public List<Object> variableKeys() {
        return keys;
    }

    @Override
    public String name() {
        return this.name;
    }

    public int getX(){
        return (Integer)super.get(RockSample.POS_X);
    }

    public int getY(){
        return (Integer)super.get(RockSample.POS_Y);
    }
}
