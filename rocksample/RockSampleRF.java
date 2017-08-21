package rocksample;


import burlap.behavior.policy.Policy;
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
import burlap.mdp.auxiliary.DomainGenerator;
import burlap.mdp.auxiliary.common.GoalConditionTF;
import burlap.mdp.auxiliary.common.NullTermination;
import burlap.mdp.auxiliary.stateconditiontest.StateConditionTest;
import burlap.mdp.core.Domain;
import burlap.mdp.core.StateTransitionProb;
import burlap.mdp.core.TerminalFunction;
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
import burlap.mdp.singleagent.model.RewardFunction;
import burlap.mdp.singleagent.model.statemodel.FullStateModel;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.shell.visual.VisualExplorer;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import burlap.visualizer.Visualizer;


//what reward function class to extend from?
public class RockSampleRF extends GoalBasedRF{

	private double goalReward;
	private double moveReward;
	private double no_opReward;
	private double scanReward;
	private double clearReward;
	
	public RockSampleRF(StateConditionTest goalReached, double rewardGoal, double rewardDefault, double rewardNoop, double rewardMove/*, 
			double scanR, double clearR*/){
		
		super(goalReached, rewardGoal, rewardDefault);
		
		this.goalReward = rewardGoal;
		this.no_opReward = rewardNoop;
		this.moveReward = rewardMove;
		//this.scanReward = scanR;
		//this.clearReward = clearR;
		
	}
	
	public double getScanReward(){
		return scanReward;
	}
	
	public void setScanReward(double scan){
		this.scanReward = scan;
	}
	
	public double getClearReward(){
		return clearReward;
	}
	
	public void setClearReward(double clear){
		this.clearReward = clear;
	}
	
	
	@Override
	public double reward(State s, Action a, State s_prime){
		double r = super.reward(s, a, s_prime);
		
		//moves to a valid next state
		if (s.equals(s_prime)){
			r += no_opReward;
		}
		//s is not in the next state yet, must move
		else{
			r += moveReward;
		}
		return r;
	}
	
}



