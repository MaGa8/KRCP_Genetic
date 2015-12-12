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
		Individual[] pop = (Individual[]) l.toArray();
		l.clear();
		HeapSort.sort (pop);
		for (int cSize = 0; cSize < mPopSize; ++cSize)
			l.add (pop[cSize]);
	}
	

	private int mPopSize;
}
