package genetic.termination;

import genetic.main.Individual;

import java.util.ArrayList;


public class StableSolutionTerminator implements Terminator 
{
	/**
	 * constructor
	 * @param stableGens number of stable generations required
	 */
	public StableSolutionTerminator (int stableGens)
	{
		mBest = null;
		mStableGens = stableGens;
		mcStableGens = 0;
	}
	
	/**
	 * @param pop population of individuals based on which terminate should be evaluated
	 * @return true if best solution persists throughout specified number of generations
	 */
	public boolean terminate (ArrayList<Individual> pop) 
	{
		if (!pop.isEmpty())
		{
			if (mBest == null)
				mBest = pop.get (0);
			else if (pop.get (0).getFitness() == mBest.getFitness())
			{
				++mcStableGens;
				if (mcStableGens >= mStableGens)
					return true;
			}
			else
			{
				mcStableGens = 0;
				mBest = pop.get (0);
			}
		}
		else
			return true;
		return false;
	}
	
	private Individual mBest;
	private int mStableGens, mcStableGens;
}
