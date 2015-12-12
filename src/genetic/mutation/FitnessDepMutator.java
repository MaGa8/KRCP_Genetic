package genetic.mutation;
import genetic.main.Individual;
import genetic.main.Practical2;


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
			char newGene = Practical2.alphabet[mGen.nextInt (Practical2.alphabet.length)];
			chrom[mGen.nextInt (chrom.length)] = newGene;
		}
	}

}
