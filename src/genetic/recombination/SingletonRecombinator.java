package genetic.recombination;

import genetic.main.Individual;

public class SingletonRecombinator implements Recombinator {

	@Override
	public Individual recombine(Individual p1, Individual p2) 
	{
		return new Individual (p1.getChromosome());
	}

}
