package genetic.processing;
import genetic.main.Individual;
import genetic.mutation.Mutator;
import genetic.recombination.Recombinator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class RandPopProcessor extends PopProcessor 
{
	public RandPopProcessor (Mutator mut, Recombinator recomb)
	{
		super (mut, recomb);
	}
	
	@Override
	public void process (ArrayList<Individual> pop) 
	{
		LinkedList <Integer> pairs = new LinkedList<Integer>();
		for (int cPop = 0; cPop< pop.size(); ++cPop)
			pairs.add (new Integer (cPop));
		
		Random gen = new Random ();
		for (int cPop = 0; cPop < pop.size() - 1; ++cPop)
		{
			int swap = gen.nextInt (pop.size() - cPop - 1) + cPop + 1;
			Integer tmp = pairs.get (cPop);
	 		pairs.set (cPop, pairs.get (swap));
	 		pairs.set (swap, tmp);
		}
		
		for (int cPair = 0; cPair < pairs.size() - 1; cPair += 2)
		{
			//Individual newBorn = recombine (pop.get (cPair), pop.get (cPair + 1));
			int iparent1 = pairs.get(cPair), iparent2 = pairs.get(cPair + 1);
			Individual newBorn = recombine (pop.get (iparent1), pop.get(iparent2));
			mutate (newBorn);
			pop.add (newBorn);
		}
		
	}

}
