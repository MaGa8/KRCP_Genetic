package genetic.evaluation;
import genetic.main.Individual;


//serves as abstract class for all classes evaluating fitness of individuals
public abstract class FitnessEvaluator
{
	/**
	 * sets fitness value of individual to desired value
	 * controls that this value is in [0; 1]
	 * @param i
	 */
	public final void evaluateFitness (Individual i)
	{
		double newFitVal = computeFitness (i);
		assert (newFitVal >= 0 && newFitVal <= 1);
		if (newFitVal >= 0 && newFitVal <= 1)
			i.setFitness (newFitVal);
	}
	
	
	public abstract double computeFitness (Individual i);
	
}
