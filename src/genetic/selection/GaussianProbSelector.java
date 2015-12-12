package genetic.selection;

import genetic.main.Individual;

import java.util.List;
import java.util.ListIterator;


public class GaussianProbSelector extends ProbSelector
{
		
	@Override
	public void select(List<Individual> l) 
	{
		for (ListIterator<Individual> il = l.listIterator(); il.hasNext(); )
		{
			Individual i = il.next();
			double r = mGen.nextGaussian();
			if (r > (i.getFitness() - 0.5) * 2)
				il.remove();
		}
	}
	
	
}
