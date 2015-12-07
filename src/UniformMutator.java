
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
			char modify = chrom[mGen.nextInt (chrom.length)];
			modify = Practical2.alphabet[mGen.nextInt (Practical2.alphabet.length)];
		}
	}
	
	
	private double mThreshold;
}
