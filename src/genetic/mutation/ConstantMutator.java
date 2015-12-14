package genetic.mutation;
import genetic.main.*;

public class ConstantMutator extends Mutator
{
	/**
	 * @param i individual to modify
	 * Mutates random genes in accordance with
	 * constant mutation rate
	 */

	public ConstantMutator(double mutationRate) {
		this.mutationRate = mutationRate;
	}
	
	public String toString()
	{
		return new String ("constant mutator at rate = " + mutationRate);
	}

	public void mutate(Individual i)
	{
		char[] chrom = i.getChromosome();
		for (int curGene = 0; curGene < i.getChromosome().length; curGene++) {
			if (Math.random() <= mutationRate) {
				int letter = (int) (Math.random() * GeneticAlgorithm.alphabet.length);
				chrom[curGene] = GeneticAlgorithm.alphabet[letter];
			}
		}
	}

	private double mutationRate;

}
