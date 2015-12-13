package genetic.mutation;
import genetic.main.Individual;
import genetic.main.Practical2;


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

	public void mutate(Individual i)
	{
		char[] chrom = i.getChromosome();
		for (int curGene = 0; curGene < i.getChromosome().length; curGene++) {
	           if (Math.random() <= mutationRate) {
        	        int letter = (int)(Math.random() * Practical2.alphabet.length);
                	chrom[curGene] = Practical2.alphabet[letter];
		   		}
		}
	}

	private double mutationRate;

}
