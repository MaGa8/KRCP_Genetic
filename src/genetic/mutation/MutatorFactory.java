package genetic.mutation;

import genetic.main.Individual;

public class MutatorFactory 
{
	public enum Type {UNIFORM, CONSTANT, FITNESS}
	
	/**
	 * @param mutationRate mutation rate used by mutators using specific rate
	 */
	public MutatorFactory (double mutationRate)
	{
		mMutationRate = mutationRate;
	}
	
	/**
	 * @param t type of mutator to construct
	 * @return newly constructed mutator
	 */
	public Mutator getMutator (Type t)
	{
		switch (t)
		{
		case UNIFORM: return new UniformMutator(mMutationRate);
		case CONSTANT: return new ConstantMutator(mMutationRate);
		case FITNESS: return new FitnessDepMutator();
		}
		throw new IllegalArgumentException ("Unknown type provided to mutator factory");
	}
	
	private double mMutationRate;
}
