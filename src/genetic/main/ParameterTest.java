package genetic.main;

import java.rmi.server.ExportException;
import java.util.*;
import java.io.*;

import genetic.evaluation.*;
import genetic.mutation.*;
import genetic.processing.*;
import genetic.recombination.*;
import genetic.selection.*;
import genetic.termination.*;

public class ParameterTest
{
	
	class Runner implements Runnable 
	{	
		public Runner (GeneticAlgorithm ga)
		{
			mGa = ga;
		}
		
		public void startThread()
		{
			mT = new Thread (this);
			mT.start();
		}
		
		public void run()
		{
			mGa.Initialize();
			mGa.evolve();
			synchronized (ParameterTest.this) {
				ParameterTest.this.notify();;
			}
		}
		
		public DataSet getData()
		{
			return new DataSet (mGa);
		}
		
		public boolean isDone()
		{
			return !mT.isAlive();
		}
		
		private Thread mT;
		private GeneticAlgorithm mGa;
	}
	
	public class DataSet
	{
		public DataSet (GeneticAlgorithm ga)
		{
			mGenerationsNeeded = ga.getGenerationsNeeded();
			mFinalFitness = ga.getFinalFitness();
			mParamDescription = ga.getParamDescription();
		}
		
		public final int mGenerationsNeeded;
		public final double mFinalFitness;
		public final String mParamDescription;
	}
	
	
	public ParameterTest (String target, int stableGens, int repetitions)
	{
		mTarget = target;
		mStableGens = stableGens;
		mRepetitions = repetitions;
		mData = new ArrayList <ArrayList <DataSet>>();
	}
	
	public boolean canAdvance (Integer[] counters, Integer[] boundaries)
	{
		for (int cElem = 0; cElem < counters.length && cElem < boundaries.length; ++cElem)
		{
			if (!counters[cElem].equals (boundaries[cElem]))
				return true;
		}
		return false;
	}
	
	public GeneticAlgorithm constructGA()
	{
		GeneticAlgorithm ga = new GeneticAlgorithm (	mPopSizes.get (cSizes), 
														mNumGenerations.get (cNumGenerations), 
														mTarget,
														mEvaluators.get (cEvaluator),
														mProcessors.get (cProcessor),
														mSelectors.get (cSelector), 
														new StableSolutionTerminator(mStableGens));
		return ga;
	}
	
	public void setEvaluators (ArrayList <FitnessEvaluator> evaluators)
	{
		mEvaluators = evaluators;
	}
	
	public void setMutators (ArrayList <Mutator> mutators)
	{
		mMutators = mutators;
	}
	
	public void setProcessors (ArrayList <PopProcessor> processors)
	{
		mProcessors = processors;
	}
	
	public void setRecombinators (ArrayList <Recombinator> recombinators)
	{
		mRecombinators = recombinators;
	}
	
	public void setSelectors (ArrayList <Selector> selectors)
	{
		mSelectors = selectors;
	}
	
	public void setPopSizes (ArrayList <Integer> popSizes)
	{
		mPopSizes = popSizes;
	}
	
	public void setNumGenerations (ArrayList <Integer> genNumbers)
	{
		mNumGenerations = genNumbers;
	}
	
	public void checkForExceptions()
	{
		if (mEvaluators == null)
			throw new IllegalStateException ("not ready for test: evaluators missing");
		else if (mMutators == null)
			throw new IllegalStateException ("not ready for test: mutators missing");
		else if (mRecombinators == null)
			throw new IllegalStateException ("not ready for test: recombinators mising");
		else if (mProcessors == null)
			throw new IllegalStateException ("not ready for test: processors missing");
		else if (mSelectors == null)
			throw new IllegalStateException ("not ready for test: selectors missing");
		else if (mPopSizes == null)
			throw new IllegalStateException ("not ready for test: population sizes missing");
		else if (mNumGenerations == null)
			throw new IllegalStateException ("not ready for test: generation numbers missing");
	}
	
	public void initializeCounters (Integer[] counters, Integer[] boundaries)
	{
		counters[0] = new Integer (0);
		counters[1] = new Integer (0);
		counters[2] = new Integer (0);
		counters[3] = new Integer (0);
		counters[4] = new Integer (0);
		counters[5] = new Integer (0);
		counters[6] = new Integer (0);
		
		boundaries[0] = new Integer (mEvaluators.size());
		boundaries[1] = new Integer (mMutators.size());
		boundaries[2] = new Integer (mProcessors.size());
		boundaries[3] = new Integer (mRecombinators.size());
		boundaries[4] = new Integer (mSelectors.size());
		boundaries[5] = new Integer (mPopSizes.size());
		boundaries[6] = new Integer (mNumGenerations.size());
	}
	
	public void transferCounterRefs (Integer[] counters)
	{
		cEvaluator = counters[0];
		cMutator = counters[1];
		cProcessor = counters[2];
		cRecombinator = counters[3];
		cSelector = counters[4];
		cSizes = counters[5];
		cNumGenerations = counters[6];
	}
	
	public void runTest() throws InterruptedException
	{
		checkForExceptions();
		
		Integer[] counters = new Integer[7];
		Integer[] boundaries = new Integer[7];
		initializeCounters (counters, boundaries);
		transferCounterRefs (counters);
		
		boolean allPermutations = false;
		
		LinkedList <Runner> threads = new LinkedList <Runner>();
		while (!allPermutations)
		{
			ArrayList <DataSet> temp = new ArrayList <DataSet>();
			for (int cRepetitions = 0; cRepetitions < mRepetitions; ++cRepetitions)
			{
				launchThreads (threads, mRepetitions - cRepetitions);
				synchronized (this) 
				{
					wait();
				}
				cleanUpThreads(threads, temp);
			}
			
			mData.add (temp);
			
			if (canAdvance (counters, boundaries))
			{
				advance (0, counters, boundaries);
				transferCounterRefs(counters);
			}
			else
				allPermutations = true;
		}
	}
	
	public void launchThreads (LinkedList <Runner> threads, int remainingRepetitions)
	{
		int coresAvailable = Runtime.getRuntime().availableProcessors();
		
		while (threads.size() < coresAvailable && remainingRepetitions > 0)
		{
			threads.add (new Runner (constructGA()));
			--remainingRepetitions;
			threads.get(threads.size() - 1).startThread();
		}
	}
	
	public void cleanUpThreads (LinkedList <Runner> threads, ArrayList <DataSet> writeResult)
	{
		for (ListIterator li = threads.listIterator(); li.hasNext(); )
		{
			Runner r = (Runner) li.next();
			if (r.isDone())
			{
				writeResult.add (r.getData());
				li.remove();
			}
		}
	}
	
	public void advance (int position, Integer[] counters, Integer[] boundaries)
	{
		if (position < counters.length && position < boundaries.length)
		{
			Integer counter = counters[position];
			counters[position] =  counter + 1;
			if (counters[position].equals (boundaries[position]))
			{
				counters[position] = 0;
				advance (position + 1, counters, boundaries);
			}
		}
	}
	
	public void writeToCSV (File csvFile) throws IOException
	{
		if (!csvFile.exists())
			csvFile.createNewFile();
		
		BufferedWriter bw = new BufferedWriter (new FileWriter (csvFile));
		bw.write("");
		bw.append ("Setup description,");
		for (int cRepetition = 0; cRepetition < mRepetitions; ++cRepetition)
			bw.append ("run " + cRepetition + ",,");
		bw.append ("average generations,average final fitness\n");
		
		double totalAverageGenerations = 0, totalAverageFinalFitness = 0;
		for (ArrayList <DataSet> setup : mData)
		{
			double averageGenerations = 0, averageFinalFitness = 0;
			for (DataSet dataSet : setup)
			{
				bw.append (dataSet.mParamDescription + "," + dataSet.mGenerationsNeeded + "," + dataSet.mFinalFitness + ",");
				averageGenerations += dataSet.mGenerationsNeeded;
				averageFinalFitness += dataSet.mFinalFitness;
			}
			averageGenerations /= mRepetitions;
			averageFinalFitness /= mRepetitions;
			totalAverageGenerations += averageGenerations;
			totalAverageFinalFitness += averageFinalFitness;
			
			bw.append (averageGenerations + "," + averageFinalFitness + "\n");
		}
		
		for (int cSkip = 0; cSkip < 1 + 2 * mRepetitions; ++cSkip)
			bw.append (",");
		bw.append (totalAverageGenerations + "," + totalAverageFinalFitness + "\n");
	}
	
	private ArrayList <ArrayList <DataSet>> mData;
	
	private ArrayList <FitnessEvaluator> mEvaluators;
	private ArrayList <Mutator> mMutators;
	private ArrayList <PopProcessor> mProcessors;
	private ArrayList <Recombinator> mRecombinators;
	private ArrayList <Selector> mSelectors;
	private ArrayList <Integer> mPopSizes, mNumGenerations;
	
	private String mTarget;
	
	private Integer cEvaluator, cMutator, cProcessor, cRecombinator, cSelector, cSizes, cNumGenerations;
	
	private int mStableGens, mRepetitions;
}
