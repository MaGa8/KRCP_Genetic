package genetic.selection;

import genetic.main.HeapSort;
import genetic.main.Individual;

import java.util.List;


public class ElitistSelector implements Selector
{
	public ElitistSelector (int popSize) 
	{
		mPopSize = popSize;
	}
	
	
	public void select(List <Individual> l) 
	{
		if (!l.isEmpty())
		{
			//Individual[] pop = (Individual[]) l.toArray();
			Individual[] pop = new Individual[l.size()];
			l.toArray(pop);
			l.clear();
			HeapSort.sort (pop);
			for (int cSize = 0; cSize < mPopSize && cSize < l.size(); ++cSize)
				l.add (pop[cSize]);
		}
	}
	

	private int mPopSize;
}
