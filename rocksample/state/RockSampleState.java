package rocksample.state;

import burlap.mdp.core.oo.state.MutableOOState;
import burlap.mdp.core.oo.state.OOStateUtilities;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.oo.state.exceptions.UnknownClassException;
import burlap.mdp.core.state.MutableState;
import rocksample.state.RockSampleAgent;
import rocksample.state.RockSampleBlock;
//import rocksample.state.KSGridWorldGoal;
import rocksample.state.ObjectNotFoundException;
import rocksample.state.RockSampleAgent;
import rocksample.state.RockSampleRock;
import rocksample.state.RockSampleDebris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static rocksample.RockSample.CLASS_AGENT;
import static rocksample.RockSample.CLASS_BLOCK;
//import static rocksample.RockSample.CLASS_GOAL;
import static rocksample.RockSample.*;

public class RockSampleState implements MutableOOState {

	private RockSampleAgent agent;
	private List<RockSampleBlock> blocks; 
	private RockSampleRock rock;
	private RockSampleDebris debris;
	
	private int width;
	private int height;
	
	private static final List<Object> keys = 
			Arrays.asList(CLASS_AGENT, CLASS_BLOCK, CLASS_ROCK, CLASS_DEBRIS);
	
	
	public RockSampleState(RockSampleAgent agent, 
			List<RockSampleBlock> blocks, RockSampleRock rock, RockSampleDebris debris, int width, int height){
		
		this.agent =  agent;
		this.blocks = blocks;
		this.rock = rock;
		this.debris = debris;
		
		this.width = width;
		this.height = height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	@Override
	public RockSampleState addObject(ObjectInstance o){
		if( o instanceof RockSampleBlock) 
			this.blocks.add((RockSampleBlock)o);
		else if( o instanceof RockSampleAgent) 
			this.agent = (RockSampleAgent)o;
		//add any other instances here
		else if (o instanceof RockSampleRock)
			this.rock = (RockSampleRock)o;
		else if (o instanceof RockSampleDebris)
			this.debris = (RockSampleDebris)o;
		else
			throw new UnknownClassException(o.className());
		
		return this;
	}
	
	@Override
	public int numObjects() {
		int count = 0;
		if(agent != null)count++;
		
		return count+blocks.size();
	}
	
	@Override
	public ObjectInstance object(String s) {
	    if(agent!=null&&s.equals(agent.name()))
	        return agent;
	    //else if(goal!=null&&s.equals(goal.name()))
	        //return goal;
	    else
	        for(RockSampleBlock b : blocks)
	            if(s.equals(b.name()))
	                return b;
	    
	    //rock
	    
	    //debris

        throw new ObjectNotFoundException("No object of name: "+s+" found");
	}

	@Override
	public List<ObjectInstance> objects() {
        List<ObjectInstance> objects = new ArrayList<>();
        objects.add(agent);
        //objects.add(goal);
        objects.addAll(blocks);
		return objects;
	}

	@Override
	public List<ObjectInstance> objectsOfClass(String s) {
	    List<ObjectInstance> objectsOfClass = new ArrayList<>();
	    switch(s){
            case(CLASS_AGENT):
                objectsOfClass.add(agent);    break;
            //case (CLASS_GOAL):
                //objectsOfClass.add(goal);     break;
	        case(CLASS_BLOCK):
                objectsOfClass.addAll(blocks);break;

        }
        return objectsOfClass;
	}

	@Override
    //I'd rather suppress just the one List<> cast, not sure how
    @SuppressWarnings("unchecked")
    public RockSampleState set(Object key, Object value) {
	    //so are keys always strings? I've never seen one that wasn't
		String immutableKey = (String)key;
		switch(immutableKey) {
			case(CLASS_AGENT):
				agent  = (RockSampleAgent)value;break;
			//case (CLASS_GOAL):
				//goal   = (KSGridWorldGoal) value;break;
			case(CLASS_BLOCK):
				blocks = (List<RockSampleBlock>)value;break;
		}
        return this;
    }

	@Override
	public List<Object> variableKeys() {
		return OOStateUtilities.flatStateKeys(this);
	}

	@Override
    //I'm letting this return null if goal is null, just because
    //likely burlap will pick up on the goal key regardless of state
	public Object get(Object key) {
        String immutableKey = (String)key;
        switch(immutableKey) {
            case(CLASS_AGENT):
                return agent;
            //case (CLASS_GOAL):
                //return goal;
            case(CLASS_BLOCK):
                return blocks;
        }
		throw new ObjectNotFoundException("key: "+(immutableKey)+" given not in state keys");
	}
	
	@Override
	public RockSampleState copy() {
		List<RockSampleBlock> newblocks = new ArrayList<>();
		for(RockSampleBlock b : blocks)
			newblocks.add(b.copy());
		return new RockSampleState(agent.copy(), newblocks, rock, debris, width, height);
	}
	
	
	public RockSampleAgent getAgent() {
		return agent;
	}
	
	public RockSampleAgent touchAgent() {
		if( agent == null) return null;
		this.agent = agent.copy();
		return agent;
	}

	@Override
	public MutableOOState removeObject(String s) {
		
		if(s.equals(agent.name()))
			agent = null;
		//else if(s.equals(goal.name()));
			//goal = null;
		else if(s.equals(rock.name()))
			rock = null;
		else if(s.equals(debris.name()))
			debris =  null;
		else{
			for(Iterator<RockSampleBlock> i = blocks.iterator(); i.hasNext();){
				if(s.equals(i.next().name()))
					i.remove();
			}
		}
		
		return this;
	}

	@Override
	public MutableOOState renameObject(String s0, String s1) {
		
		ObjectInstance o = this.object(s0);
		o = o.copyWithName(s1);
		this.removeObject(s1);
		
		return this.addObject(o);
	}
}
