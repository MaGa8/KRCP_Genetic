package genetic.processing;
import genetic.main.Individual;
import genetic.mutation.Mutator;
import genetic.recombination.Recombinator;

import java.util.ArrayList;

/**
 * @author martin
 * Implements how a population is processed to produce the next generation
 */

public abstract class PopProcessor 
{
	/**
	 * constructor
	 * @param mut mutator object to use to mutate new individuals
	 * @param recomb recombination object to use to generate new individuals
	 */
	public PopProcessor (Mutator mut, Recombinator recomb)
	{
		mMutator = mut;
		mRecombinor = recomb;
	}
	
	/**
	 * Accessor to mutator
	 * @param i individual to mutate
	 */
	public void mutate (Individual i)
	{
		mMutator.mutate(i);
	}
	
	/**
	 * Accessor to recombinator
	 * @param p1 parent 1
	 * @param p2 parent 2
	 * @return new individual
	 */
	public Individual recombine (Individual p1, Individual p2)
	{
		return mRecombinor.recombine (p1, p2);
	}
	
	/**
	 * Method generating new generation
	 * @param pop current population
	 */
	public abstract void process (ArrayList <Individual> pop);
	
	
	private Mutator mMutator;
	private Recombinator mRecombinor;
}
