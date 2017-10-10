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
import burlap.mdp.core.TerminalFunction;
import rocksample.state.RockSampleState;

//what reward function class to extend from?
public class RockSampleRF extends GoalBasedRF{
	/**
	 * the reward for taking an action
	 */
	private double stepReward;

	/**
	 * the reward for sampling a good rock
	 */
	private double goodRockReward;

	/**
	 * the reward for sampling a bad rock
	 */
	private double badRockReward;

	/**
	 * the reward for moving into exit area
	 */
	private double exitReward;

	/**
	 * the reward for checking a rock
	 */
	private double checkReward;

	/**
	 * reward for taking no action
	 */
	private double noopReward;

	/**
	 * the rocksample terminal function
	 */
	private TerminalFunction tf;

	/**
	 * use the default rewards
	 */
	public RockSampleRF()
	{
		stepReward = -1;
		goodRockReward = 10;
		badRockReward = -10;
		exitReward = 10;
		checkReward = 0;
		tf = new RockSampleTF();
	}

	/**
	 * use custom rewards
	 * @param stepR the reward for an action
	 * @param goodrockR the reward for sampling a good rock
	 * @param badrockR the reward for sampling a bad rock
	 * @param exitR the reward for moving into exit area
	 * @param checkR the reward for checking a rock
	 */
	public RockSampleRF(double stepR, double goodRockR, double badRockR, double exitR, double checkR)
	{
		stepReward = stepR;
		goodRockReward = goodRockR;
		badRockReward = badRockR;
		exitReward = exitR;
		checkReward = checkR;
		tf = new RockSampleTF();
	}

	@Override
	public double reward(State s, Action a, State sprime){
		RockSample state = (RockSampleState) s;

		double superR = super.reward(s,a,sprime);
		double r = superR;

		// when state is terminal
		// TODO: redefine terminal state -- when agent moves to the exit area
		if (tf.isTerminal(sprime))
			return exitReward + r;

		// movement reward
		if (a.actionName().equals(RockSample.ACTION_NORTH)
				|| a.actionName().equals(RockSample.ACTION_EAST)
				|| a.actionName().equals(RockSample.ACTION_WEST)
				|| a.actionName().equals(RockSample.ACTION_SOUTH))
		{
			r+= stepReward;
		}

		// check action reward
		if (a.actionName().equals(RockSample.ACTION_SCAN))
		{
			r += checkReward;
		}

		// sample action reward
		if (a.actionName().equals(RockSample.ACTION_SAMPLE_ROCK1))
		{
			//if rock is bad
			r += badRockReward;

			// if rock is good
			r += goodRockReward;
		}

		if (s.equals(sprime))
		{
			r += noopReward;
		}

		return r;
	}
	
}



