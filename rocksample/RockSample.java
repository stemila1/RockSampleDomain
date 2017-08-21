package rocksample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.domain.singleagent.pomdp.tiger.TigerModel;
/*import burlap.behavior.policy.Policy;
import burlap.behavior.policy.PolicyUtils;
import burlap.behavior.singleagent.Episode;
import burlap.behavior.singleagent.auxiliary.EpisodeSequenceVisualizer;
import burlap.behavior.singleagent.auxiliary.performance.LearningAlgorithmExperimenter;
import burlap.behavior.singleagent.auxiliary.performance.PerformanceMetric;
import burlap.behavior.singleagent.auxiliary.performance.TrialMode;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.LearningAgentFactory;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.learning.tdmethods.SarsaLam;
import burlap.behavior.singleagent.planning.stochastic.rtdp.BoundedRTDP;
import burlap.behavior.valuefunction.ConstantValueFunction;
import burlap.debugtools.RandomFactory;
*/
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.auxiliary.common.NullTermination;
/*
import burlap.mdp.auxiliary.common.GoalConditionTF;
import burlap.mdp.auxiliary.common.NullTermination;
import burlap.mdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.mdp.core.Domain;
import burlap.mdp.core.StateTransitionProb;
*/
import burlap.mdp.core.TerminalFunction;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.common.UniformCostRF;
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.model.FactoredModel;
/*
import burlap.mdp.core.action.Action;
import burlap.mdp.core.action.ActionType;
import burlap.mdp.core.action.UniversalActionType;
import burlap.mdp.core.oo.OODomain;
import burlap.mdp.core.oo.propositional.GroundedProp;
import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.core.oo.state.OOState;
import burlap.mdp.core.oo.state.ObjectInstance;
import burlap.mdp.core.state.MutableState;
import burlap.mdp.core.state.State;
import burlap.mdp.singleagent.common.GoalBasedRF;
import burlap.mdp.singleagent.common.UniformCostRF;
import burlap.mdp.singleagent.environment.Environment;
import burlap.mdp.singleagent.environment.EnvironmentOutcome;
import burlap.mdp.singleagent.model.FactoredModel;
*/
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.oo.OOSADomain;
/*
import burlap.mdp.singleagent.model.statemodel.FullStateModel;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.shell.visual.VisualExplorer;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import burlap.visualizer.Visualizer;
import burlap.mdp.singleagent.SADomain;
*/
import burlap.mdp.singleagent.pomdp.PODomain;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
//import ksgridworld.KSGoalConditionTest;
import rocksample.RockSample;
import rocksample.RockSampleRF;
import rocksample.RockSampleTF;
import rocksample.state.RockSampleAgent;
import rocksample.state.RockSampleBlock;
import rocksample.state.RockSampleDebris;
import rocksample.state.RockSampleRock;
import rocksample.state.RockSampleState;

public class RockSample implements DomainGenerator {

	//attributes
	public static final String POS_X = "x";
	public static final String POS_Y = "y";
	public static final String COLOR = "color";
	public static final String SHAPE = "shape";
	public static final String SIZE_LARGE = "large";
	public static final String SIZE_SMALL = "small";
	
	//object classes
	public static final String CLASS_AGENT = "agent";
	public static final String CLASS_BLOCK = "block";
	public static final String CLASS_ROCK =  "rock";
	public static final String CLASS_DEBRIS = "debris";
	
	//actions
	public static final String ACTION_NORTH = "north";
	public static final String ACTION_SOUTH = "south";
	public static final String ACTION_EAST = "east";
	public static final String ACTION_WEST = "west";
	//public static final String ACTION_NORTHEAST = "northeast";
	//public static final String ACTION_NORTHWEST = "northwest";
	//public static final String ACTION_SOUTHEAST = "southeast";
	//public static final String ACTION_SOUTHWEST = "southwest";
	public static final String ACTION_SCAN = "scan";
	public static final String ACTION_CLEAR = "clear";
	//public static final String ACTION_PICKUP = "pickup";
	public static final String ACTION_DO_NOTHING = "doNothing";
	public static final String ACTION_SCAN_NOTHING = "scanNothing";
	
	public static final String QUALITY_GOOD = "good";
	public static final String QUALITY_BAD =  "bad";
	//public static final String QUALITY_TOXIC =  "toxic";
	//public static final String QUALITY_NONTOXIC =  "non-toxic";
	
	public int numGoodRocksVisited = 0;
	public int numBadRocksVisited = 0;
	public int numGoodRocksScanned = 0;
	public int numBadRocksScanned = 0;
	public int numRocksScanned = numGoodRocksScanned + numBadRocksScanned;
	
	
	public double scanReward = -1.0; //or 0 or 1
	public double goodRockCollectReward = 10.0;
	public double badRockReward = -100.0; //or badRockCollect
	public double nothingReward = 0.0;
	
	//probability for good scan feedback
	protected double scanAccuracy = 0.0;
	
	private int width;
	private int height;
	
	private RewardFunction rf;
	private TerminalFunction tf;
	
	public RockSample(RewardFunction rf, TerminalFunction tf, int width, int height){
	
		this.width = width;
		this.height = height;
		
		this.rf = rf;
		this.tf = tf;
	}
	
	public RewardFunction getRf(){
		
		return rf;
	}
	
	public void setRf(RewardFunction rf){
		
		this.rf = rf;
	}
	
	public TerminalFunction getTf(){

		return tf;
	}
	
	public void setTf(TerminalFunction tf){
		
		this.tf = tf;
	}
	
	public double getScanAccuracy(){
		return scanAccuracy;
	}
	
	public void setScanAccuracy(double scanAccuracy){
		this.scanAccuracy = scanAccuracy;
	}
	
	public PODomain generateDomain(){
		
		PODomain rsdomain = new PODomain();
		
		//add state classes?
		
		//add the action types
		rsdomain.addActionType(new UniversalActionType(ACTION_NORTH))
			.addActionType(new UniversalActionType(ACTION_SOUTH))
			.addActionType(new UniversalActionType(ACTION_EAST))
			.addActionType(new UniversalActionType(ACTION_WEST))
			.addActionType(new UniversalActionType(ACTION_SCAN))
			//.addActionType(new UniversalActionType(ACTION_CLEAR))
			//.addActionType(new UniversalActionType(ACTION_PICKUP))
			;
		
		
		if (rf == null){
			
			rf = new UniformCostRF();
		}
		
		if (tf == null){
			
			tf = new NullTermination();
		}
		
		
		/*create a model = new MYmodel()
		 * 
		 * 
		 */
		RockSampleModel model = new RockSampleModel(goodRockCollectReward, badRockReward, scanReward, nothingReward);
		//want to create a factored model so pass in the sample state model
		FactoredModel fmodel = new FactoredModel(model, rf, tf);
		rsdomain.setModel(fmodel);
		
		return rsdomain;
	}
	
	public static boolean completedGoal(State s){
		RockSampleGoalTest goal = new RockSampleGoalTest();
		return goal.satisfies(s);
	}
	
	public static boolean failedGoal(State s){
		return false;
	}

	public static boolean scannedGoodRock(State s) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean scannedBadRock(State s) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public /*OOState*/RockSampleState getInitialState(int x, int y) {
		
		//set up an initial state
		RockSampleAgent agent = new RockSampleAgent(CLASS_AGENT, x, y);
        List<RockSampleBlock> blocks = new ArrayList<>(Arrays.asList());
		RockSampleRock rock = new RockSampleRock(CLASS_ROCK, x, y);
		RockSampleDebris debris = new RockSampleDebris(CLASS_DEBRIS, x, y);
		
		RockSampleState s = new RockSampleState(agent, blocks, rock, debris, width, height);
		
		//need to add in the objects as necessary
		s.addObject(new RockSampleBlock("block0", 0, 2));
		s.addObject(new RockSampleBlock("block1", 1, 2));
		s.addObject(new RockSampleBlock("block2", 2, 2));
		s.addObject(new RockSampleBlock("block3", 3, 0));
		s.addObject(new RockSampleBlock("block4", 2, 4));
		s.addObject(new RockSampleBlock("block5", 1, 3));
		//s.addObject(new KSGridWorldAgent("overallAgent", 1, 0));
		//s.addObject(new KSGridWorldGoal ("overallGoal", 2, 3, "red"));
		s.addObject(new RockSampleRock("rock1", 3, 4));
		s.addObject(new RockSampleDebris("debris1", 2, 4));
		
		
		return s;
	}
	
	
	
	public static void main(String [] args){

        //KSGridWorldDomain dgen = new KSGridWorldDomain();
        //OOSADomain domain = dgen.generateDomain();

		PODomain domain;
    	RewardFunction rf;
		TerminalFunction tf;
		//KSGridWorldGoal goalCondition;
		//OOState initialState;
		RockSampleState initialState;
		HashableStateFactory hashingFactory;
		Environment env;

		RockSampleGoalTest atGoal = new RockSampleGoalTest();
		double rewardGoal = 1000;
		double rewardDefault = 0;
		double rewardNoop = 0;
		double rewardMove = 0;
		
		String name;
		int x;
		int y;
		String color;
		
		//goalCondition = new KSGridWorldGoal();
		rf = new RockSampleRF(atGoal, rewardGoal, rewardDefault, rewardNoop, rewardMove);
		tf = new RockSampleTF();
		RockSample gen = new RockSample(rf, tf, 5, 5);
		
		domain = gen.generateDomain();

//		goalCondition.setGoals(goals);
		initialState = (RockSampleState) gen.getInitialState(1, 0);
		hashingFactory = new SimpleHashableStateFactory();
		env = new SimulatedEnvironment(domain, initialState);
//
//		Visualizer v = CleanupVisualizer.getVisualizer(width, height);
//		VisualExplorer exp = new VisualExplorer(domain, v, initialState);
//		exp.addKeyAction("w", ACTION_NORTH, "");
//		exp.addKeyAction("s", ACTION_SOUTH, "");
//		exp.addKeyAction("d", ACTION_EAST, "");
//		exp.addKeyAction("a", ACTION_WEST, "");
//		exp.addKeyAction("r", ACTION_SCAN, "");
//		exp.initGUI();
//		exp.requestFocus();
//		exp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		String outputPath = "./output/";
		double gamma = 0.9;
		double qInit = 0;
		double learningRate = 0.01;
		int nEpisodes = 100;
		int maxEpisodeSize = 100;
		int writeEvery = 1;
		
		LearningAgent agent = new QLearning(domain, gamma, hashingFactory, qInit, learningRate, maxEpisodeSize);
		for(int i = 0; i < nEpisodes; i++){
			Episode e = agent.runLearningEpisode(env, maxEpisodeSize);
			if (i % writeEvery == 0) {
				e.write(outputPath + "ql_" + i);
			}
			System.out.println(i + ": " + e.maxTimeStep());
			System.out.println(i + ": " + e.actionSequence);
			System.out.println(i + ": " + e.rewardSequence);
			//System.out.println(i + ": " + e.stateSequence);
			env.resetEnvironment();
		}
		/*
		LearningAgent agent = new BFS(domain, gamma, hashingFactory, qInit, learningRate, maxEpisodeSize);
		for(int i = 0; i < nEpisodes; i++){
			Episode e = agent.runLearningEpisode(env, maxEpisodeSize);
			if (i % writeEvery == 0) {
				e.write(outputPath + "ql_" + i);
			}
			System.out.println(i + ": " + e.maxTimeStep());
			System.out.println(i + ": " + e.actionSequence);
			env.resetEnvironment();
			
		}*/
//		Visualizer v = CleanupVisualizer.getVisualizer(width, height);
//		EpisodeSequenceVisualizer esv = new EpisodeSequenceVisualizer(v, domain, outputPath);
//		esv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    }
	
	
	
}
