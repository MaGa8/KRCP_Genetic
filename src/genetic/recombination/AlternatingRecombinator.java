package genetic.recombination;
import genetic.main.Individual;


public class AlternatingRecombinator implements Recombinator {

	public Individual recombine(Individual p1, Individual p2) 
	{
		assert (p1.getChromosome().length == p2.getChromosome().length);
		char[] newChrom = new char [p1.getChromosome().length];
		for (int cChrom = 0; cChrom < newChrom.length; ++cChrom)
		{
			if (cChrom % 2 == 0)
				newChrom[cChrom] = p1.getChromosome()[cChrom];
			else
				newChrom[cChrom] = p2.getChromosome()[cChrom];
		}
		return new Individual (newChrom);
	}

}
