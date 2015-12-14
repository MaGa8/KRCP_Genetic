package genetic.mutation;
import genetic.main.*;

import java.lang.reflect.GenericArrayType;


public class FitnessDepMutator extends Mutator
{
	
	public String toString()
	{
		return new String("fitness dependent mutator");
	}
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
			char newGene = GeneticAlgorithm.alphabet[mGen.nextInt (GeneticAlgorithm.alphabet.length)];
			chrom[mGen.nextInt (chrom.length)] = newGene;
		}
	}

}
