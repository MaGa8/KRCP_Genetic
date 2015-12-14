package genetic.main;

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
			ParameterTest.this.notify();
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
		
		private int mGenerationsNeeded;
		private double mFinalFitness;
		private String mParamDescription;
	}
	
	
	public ParameterTest (String target, int stableGens, int repetitions)
	{
		mTarget = target;
		mStableGens = stableGens;
		mRepetitions = repetitions;
	}
	
	public boolean canAdvance (ArrayList <Integer> counters, ArrayList <Integer> boundaries)
	{
		for (int cElem = 0; cElem < counters.size() && cElem < boundaries.size(); ++cElem)
		{
			if (!counters.get(cElem).equals (boundaries.get (cElem)))
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
	
	public void initializeCounters()
	{
		cEvaluator = new Integer (0);
		cMutator = new Integer (0);
		cProcessor = new Integer (0);
		cRecombinator = new Integer (0);
		cSelector = new Integer (0);
		cSizes = new Integer (0);
		cNumGenerations = new Integer (0);
	}
	
	public void runTest() throws InterruptedException
	{
		checkForExceptions();
		initializeCounters();
		
		ArrayList <Integer> counters, boundaries;
		
		counters = new ArrayList <Integer>();
		counters.add (cEvaluator);
		counters.add (cMutator);
		counters.add (cProcessor);
		counters.add (cRecombinator);
		counters.add (cSelector);
		counters.add (cSizes);
		counters.add (cNumGenerations);
		
		boundaries = new ArrayList <Integer>();
		boundaries.add (new Integer (mEvaluators.size()));
		boundaries.add (new Integer (mMutators.size()));
		boundaries.add (new Integer (mProcessors.size()));
		boundaries.add (new Integer (mRecombinators.size()));
		boundaries.add (new Integer (mSelectors.size()));
		boundaries.add (new Integer (mPopSizes.size()));
		boundaries.add (new Integer (mNumGenerations.size()));
		
		boolean allPermutations = false;
		
		ArrayList <ArrayList <DataSet>> data = new ArrayList <ArrayList <DataSet>>();
		LinkedList <Runner> threads = new LinkedList <Runner>();
		while (!allPermutations)
		{
			ArrayList <DataSet> temp = new ArrayList <DataSet>();
			for (int cRepetitions = 0; cRepetitions < mRepetitions; ++cRepetitions)
			{
				launchThreads (threads, mRepetitions - cRepetitions);
				wait();
				cleanUpThreads(threads, temp);
			}
			
			data.add (temp);
			
			if (canAdvance (counters, boundaries))
				advance (0, counters, boundaries);
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
			threads.get(threads.size()).startThread();
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
	
	public void advance (int position, ArrayList <Integer> counters, ArrayList <Integer> boundaries)
	{
		if (position < counters.size() && position < boundaries.size())
		{
			Integer counter = counters.get(position);
			if (counter.compareTo (boundaries.get (position)) < 0)
				counters.set(position, counter + 1);
			else
			{
				counters.set (position, 0);
				advance (position + 1, counters, boundaries);
			}
		}
	}
	
	public void writeToCSV (File csvFile)
	{
		
	}
	
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
