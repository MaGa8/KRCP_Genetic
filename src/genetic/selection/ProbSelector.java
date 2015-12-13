package genetic.selection;

import genetic.main.Individual;

import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public abstract class ProbSelector implements Selector 
{
	static int mod;
	static int counter;
	
	static
	{
		mod = 10000;
		counter = 0;
	}
	
	/**
	 * constructor
	 * @param constantProb least probability for every individual to survive
	 */
	public ProbSelector (double constantProb)
	{
		++counter;
		mGen = new Random (System.currentTimeMillis() / counter % mod);
		mConstantProb = constantProb;
	}
	
	/**
	 * Probabilistically selects fittest individuals
	 * @param l list of individuals
	 */
	public void select (List<Individual> l) 
	{
		//find minimum and maximum fitness of a population
		double minFit = 1, maxFit = 0;
		for (Individual i : l)
		{
			if (i.getFitness() > maxFit)
				maxFit = i.getFitness();
			if (i.getFitness() < minFit)
				minFit = i.getFitness();
		}
		
		for (ListIterator<Individual> il = l.listIterator(); il.hasNext(); )
		{
			Individual i = il.next();
			//random fitness value relative to min/max fitness of population
			double randFitness = (maxFit - minFit) * getRand() + minFit;
			double relativeConst = (maxFit - minFit) * mConstantProb;
			//integrated const prob
			if (randFitness > i.getFitness() + relativeConst)
				il.remove();
		}
	}
	
	/**
	 * @return random double in [0, 1)
	 */
	public abstract double getRand();
	
	protected Random mGen;
	private double mConstantProb;
}
