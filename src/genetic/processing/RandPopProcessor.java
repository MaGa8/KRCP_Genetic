package genetic.processing;
import genetic.main.Individual;
import genetic.mutation.Mutator;
import genetic.recombination.Recombinator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class RandPopProcessor extends PopProcessor 
{
	public static final int mMod = 10000;
	
	
	
	public RandPopProcessor (Mutator mut, Recombinator recomb)
	{
		super (mut, recomb);
		++mObjectCount;
		mGen = new Random (System.currentTimeMillis() / mObjectCount % mMod);
	}
	
	/**
	 * @param pop population
	 * Matches random pairs in pop
	 * pairs produce new offspring stored in pop
	 */
	public void process (ArrayList<Individual> pop) 
	{
		LinkedList <Integer> indices = getIndexList (pop);
		shuffleList (indices);
		mate (indices, pop);
	}
	
	public LinkedList <Integer> getIndexList (ArrayList <Individual> pop)
	{
		LinkedList <Integer> pairs = new LinkedList<Integer>();
		for (int cPop = 0; cPop< pop.size(); ++cPop)
			pairs.add (new Integer (cPop));
		return pairs;
	}
	
	public void shuffleList (LinkedList <Integer> indices)
	{
		for (int cIndex = 0; cIndex < indices.size() - 1; ++cIndex)
		{
			//swap current index with one of the following indices
			int swap = mGen.nextInt (indices.size() - cIndex - 1) + cIndex + 1;
			Integer tmp = indices.get (cIndex);
	 		indices.set (cIndex, indices.get (swap));
	 		indices.set (swap, tmp);
		}
	}
	
	public void mate (LinkedList <Integer> pairs, ArrayList <Individual> pop)
	{
		for (int cPair = 0; cPair < pairs.size() - 1; cPair += 2)
		{
			//Individual newBorn = recombine (pop.get (cPair), pop.get (cPair + 1));
			int iparent1 = pairs.get(cPair), iparent2 = pairs.get(cPair + 1);
			Individual newBorn = recombine (pop.get (iparent1), pop.get(iparent2));
			mutate (newBorn);
			pop.add (newBorn);
		}
	}
	
	protected Random mGen;
	
	private static int mObjectCount = 0;
}
