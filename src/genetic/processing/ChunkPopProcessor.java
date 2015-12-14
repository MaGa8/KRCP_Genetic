package genetic.processing;

import genetic.main.HeapSort;
import genetic.main.Individual;
import genetic.mutation.Mutator;
import genetic.recombination.Recombinator;

import java.util.ArrayList;
import java.util.LinkedList;

public class ChunkPopProcessor extends RandPopProcessor {

	public ChunkPopProcessor(Mutator mut, Recombinator recomb, int numChunks) 
	{
		super(mut, recomb);
		mNumChunks = numChunks;
	}
	
	public String toString()
	{
		return new String("Chunk processor chunks = " + mNumChunks);
	}

	/**
	 * @param pop population
	 * Sorts pop, divides sorted pop into specified number of equally sized chunks
	 * Individuals within chunks are paired randomly to produce offspring
	 * offspring is added to pop
	 */
	public void process(ArrayList<Individual> pop) 
	{
		sort (pop);
		ArrayList <ArrayList <Individual>> chunkedPop = new ArrayList<ArrayList<Individual>>();
		
		int remainingPop = pop.size();
		for (int cChunk = 0; cChunk < mNumChunks; ++cChunk)
		{
			chunkedPop.add (new ArrayList<Individual>());
			int chunkStart = pop.size() - remainingPop;
			int chunkEnd = chunkStart + remainingPop / (mNumChunks - cChunk);
			for (int cAdd = chunkStart; cAdd < chunkEnd; ++cAdd)
				chunkedPop.get (cChunk).add (pop.get(cAdd));
			remainingPop -= chunkEnd - chunkStart;
		}
		
		pop.clear();
		for (ArrayList <Individual> chunk : chunkedPop)
		{
			LinkedList<Integer> indices = getIndexList (chunk);
			shuffleList(indices);
			mate (indices, chunk);
			pop.addAll(chunk);
		}
	}
	
	public void sort (ArrayList <Individual> pop)
	{
		Individual[] arrPop = new Individual[pop.size()];
		pop.toArray(arrPop);
		pop.clear();
		HeapSort.sort (arrPop);
		for (Individual i : arrPop)
			pop.add(i);
	}
	
	private int mNumChunks;
}
