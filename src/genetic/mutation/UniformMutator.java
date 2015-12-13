package genetic.mutation;
import genetic.main.*;


public class UniformMutator extends Mutator 
{
	/**
	 * constructor
	 * @param threshold threshold a random number needs to exceed so individual gets mutated
	 */
	public UniformMutator (double threshold)
	{
		assert (threshold >= 0 && threshold <= 1);
		mThreshold = threshold;
	}
	
	/**
	 * If threshold is exceeded random gene is picked and mutated (assigned another random value)
	 * @param i individual to mutate
	 */
	public void mutate(Individual i) 
	{
		if (mGen.nextDouble() > mThreshold)
		{
			char[] chrom = i.getChromosome();
			char newGene = GeneticAlgorithm.alphabet[mGen.nextInt (GeneticAlgorithm.alphabet.length)];
			chrom[mGen.nextInt (chrom.length)] = newGene;
		}
	}
	
	
	private double mThreshold;
}
