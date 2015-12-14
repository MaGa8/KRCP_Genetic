package genetic.evaluation;

import genetic.main.Individual;

public class EvaluatorFactory 
{
	public enum Type {LIN_EDIT_DIST, NONLIN_EDIT_DIST}
	
	/**
	 * constructor
	 * @param target target value used for norming evaluation
	 */
	public EvaluatorFactory (Individual target)
	{
		mTarget = target;
	}
	
	/**
	 * @param t type of evaluator
	 * @return newly constructed evaluator
	 */
	public FitnessEvaluator getEvaluator (Type t)
	{
		switch (t)
		{
		case LIN_EDIT_DIST: return new EditDistance(mTarget);
		case NONLIN_EDIT_DIST: return new NonLinearEditDistance(mTarget);
		}
		throw new IllegalArgumentException ("non matching type provided to evaluation factory");
	}
	
	private Individual mTarget;
}
