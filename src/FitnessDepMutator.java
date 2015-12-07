
public class FitnessDepMutator extends Mutator
{
	/**
	 * @param i individual to modify
	 * Mutates random gene (assigning random value of alphabet) if 
	 * random value in [0; 1] exceeds fitness of i
	 */
	public void mutate(Individual i) 
	{
		if (mGen.nextDouble() > i.getFitness())
		{
			char[] chrom = i.getChromosome();
			char modify = chrom[mGen.nextInt (chrom.length)];
			modify = Practical2.alphabet[mGen.nextInt (Practical2.alphabet.length)];
		}
	}

}
