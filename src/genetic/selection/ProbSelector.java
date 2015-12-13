package genetic.selection;

import genetic.main.Individual;

import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class ProbSelector implements Selector 
{
	static int mod;
	static int counter;
	
	static
	{
		mod = 10000;
		counter = 0;
	}
	
	public ProbSelector()
	{
		++counter;
		mGen = new Random (System.currentTimeMillis() / counter % mod);
	}
	
	public void select(List<Individual> l) 
	{
		
		
		for (ListIterator<Individual> il = l.listIterator(); il.hasNext(); )
		{
			Individual i = il.next();
			double r = mGen.nextDouble();
			if (r > i.getFitness())
				il.remove();
		}
	}
	
	
	protected Random mGen;
}
