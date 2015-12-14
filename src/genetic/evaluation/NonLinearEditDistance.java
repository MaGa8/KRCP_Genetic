package genetic.evaluation;

import genetic.main.Individual;

public class NonLinearEditDistance extends FitnessEvaluator {

	public NonLinearEditDistance (Individual target)
	{
		mTarget = new Individual (target.getChromosome());
	}
	
	@Override
	public double computeFitness(Individual i) 
	{
		char[] iChrom = i.getChromosome();
		char[] tChrom = mTarget.getChromosome();
		if (iChrom.length != tChrom.length)
			return 0.0;
		
		
		int cCorrect = 0;
		double score = 0;
		for (int cChrom = 0; cChrom < tChrom.length; ++cChrom)
		{
			if (iChrom[cChrom] == tChrom[cChrom])
				++cCorrect;
			else
			{
				score += calcNewScore(score, cCorrect);
				cCorrect = 0;
			}
		}
		score += calcNewScore(score, cCorrect);
		
		return (score / calcNewScore (0, iChrom.length));
	}
	
	public double calcNewScore (double oldScore, int matches)
	{
		return (oldScore + matches * matches);
	}
	
	private Individual mTarget;
}
