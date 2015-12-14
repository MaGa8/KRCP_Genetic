package genetic.evaluation;
import genetic.main.Individual;


/**
 * @author Martin
 * computes edit distance between target and parameter
 */
public class EditDistance extends FitnessEvaluator 
{
	/**
	 * @param target target individual
	 */
	public EditDistance (Individual target)
	{
		mTarget = target;
	}
	
	/**
	 * @param i individual whose fitness is to be computed
	 * Computes fitness of i
	 */
	public double computeFitness(Individual i) {
		char[] iChrom = i.getChromosome();
		char[] tChrom = mTarget.getChromosome();
		if (iChrom.length != tChrom.length)
			return 0.0;
		
		
		int cEdits = 0;
		for (int cChrom = 0; cChrom < tChrom.length; ++cChrom)
		{
			if (iChrom[cChrom] != tChrom[cChrom])
				++cEdits;
		}
		return (1 - (double)cEdits / (double)iChrom.length);
	}
	
	private Individual mTarget;
}
