package genetic.processing;

import genetic.mutation.Mutator;
import genetic.recombination.Recombinator;

public class ProcessorFactory 
{
	
	public enum Type {RANDOM, CHUNK}
	
	/**
	 * constructor
	 * @param mutator mutator to use
	 * @param recombinator recombinator to use
	 * @param chunks number of chunks for chunk processor
	 */
	public ProcessorFactory (Mutator mutator, Recombinator recombinator, int chunks)
	{
		mMutator = mutator;
		mRecombinator = recombinator;
		mChunks = chunks;
	}
	
	
	public PopProcessor getProcessor (Type t)
	{
		switch(t)
		{
		case RANDOM: return new RandPopProcessor(mMutator, mRecombinator);
		case CHUNK: return new ChunkPopProcessor(mMutator, mRecombinator, mChunks);
		}
		throw new IllegalArgumentException ("pop processor factory was provided illegal type");
	}
	
	
	private Mutator mMutator;
	private Recombinator mRecombinator;
	private int mChunks;
}
