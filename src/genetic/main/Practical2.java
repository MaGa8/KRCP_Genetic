package genetic.main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import genetic.evaluation.*;
import genetic.termination.*;
import genetic.selection.*;
import genetic.recombination.*;
import genetic.mutation.*;
import genetic.processing.*;

/**
 * Some very basic stuff to get you started. It shows basically how each
 * chromosome is built.
 * 
 * @author Jo Stevens
 * @version 1.0, 14 Nov 2008
 * 
 * @author Alard Roebroeck
 * @version 1.1, 12 Dec 2012
 * 
 */

public class Practical2 {

	static public final String TARGET = "HELLO WORLD";


	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		//testMutationRate (50);
		//testPopSize (50);
		//testRecombinator(50);
		//testMutator(50);
		testProcessor(50);
		
		
		/*
		ParameterTest paramTest = new ParameterTest(TARGET, 10, 5);

		MutatorFactory mutatorFactory = new MutatorFactory(0.1);
		ArrayList<Mutator> mutators = new ArrayList<Mutator>();
		mutators.add(mutatorFactory.getMutator(MutatorFactory.Type.UNIFORM));
		mutators.add(mutatorFactory.getMutator(MutatorFactory.Type.CONSTANT));
		mutators.add(mutatorFactory.getMutator(MutatorFactory.Type.FITNESS));

		paramTest.setMutators(mutators);

		ArrayList<Recombinator> recombinators = new ArrayList<Recombinator>();
		recombinators.add(new AlternatingRecombinator());
		recombinators.add(new HalfRecombinator());
		//recombinators.add(new SingletonRecombinator());

		paramTest.setRecombinators(recombinators);


		Individual target = new Individual(TARGET.toCharArray());
		EvaluatorFactory evaluatorFactory = new EvaluatorFactory(target);
		ArrayList<FitnessEvaluator> evaluators = new ArrayList<FitnessEvaluator>();
		evaluators.add(evaluatorFactory.getEvaluator(EvaluatorFactory.Type.LIN_EDIT_DIST));
		//evaluators.add(evaluatorFactory.getEvaluator(EvaluatorFactory.Type.NONLIN_EDIT_DIST));

		paramTest.setEvaluators(evaluators);


		ArrayList<PopProcessor> processes = new ArrayList<PopProcessor>();
		processes.add(new RandPopProcessor(mutatorFactory.getMutator(MutatorFactory.Type.CONSTANT),
				new HalfRecombinator()));
		processes.add(new ChunkPopProcessor(mutatorFactory.getMutator(MutatorFactory.Type.UNIFORM),
				new AlternatingRecombinator(), 20));
		processes.add(new ChunkPopProcessor(mutatorFactory.getMutator(MutatorFactory.Type.FITNESS),
				new AlternatingRecombinator(), 20));
		processes.add(new ChunkPopProcessor(mutatorFactory.getMutator(MutatorFactory.Type.CONSTANT),
				new AlternatingRecombinator(), 20));
		processes.add(new ChunkPopProcessor(mutatorFactory.getMutator(MutatorFactory.Type.UNIFORM),
				new HalfRecombinator(), 20));
		processes.add(new ChunkPopProcessor(mutatorFactory.getMutator(MutatorFactory.Type.FITNESS),
				new HalfRecombinator(), 20));
		processes.add(new ChunkPopProcessor(mutatorFactory.getMutator(MutatorFactory.Type.CONSTANT),
				new HalfRecombinator(), 20));
		processes.add(new ChunkPopProcessor(mutatorFactory.getMutator(MutatorFactory.Type.UNIFORM),
				new SingletonRecombinator(), 20));
		processes.add(new ChunkPopProcessor(mutatorFactory.getMutator(MutatorFactory.Type.FITNESS),
				new SingletonRecombinator(), 20));
		processes.add(new ChunkPopProcessor(mutatorFactory.getMutator(MutatorFactory.Type.CONSTANT),
				new SingletonRecombinator(), 20));

		paramTest.setProcessors(processes);

		ArrayList<Selector> selectors = new ArrayList<Selector>();

		selectors.add(new DynamicProbSelector(500, 1.5));
		selectors.add(new ElitistSelector(500));
		//selectors.add(new GaussianProbSelector(1.5));
		//selectors.add(new UniformProbSelector(1.5));

		paramTest.setSelectors(selectors);

		ArrayList<Integer> popSizes = new ArrayList<Integer>();

		popSizes.add(100);
		//popSizes.add(500);
		//popSizes.add(1000);
		paramTest.setPopSizes(popSizes);



		ArrayList<Integer> genSizes = new ArrayList<Integer>();

		genSizes.add(50);
		//genSizes.add(100);
		//genSizes.add(500);
		//genSizes.add(1000);

		paramTest.setNumGenerations(genSizes);


		try {
			paramTest.runTest();
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
*/


		// do your own cool GA here
		/**
		 * Some general programming remarks and hints:
		 * - A crucial point is to set each individual's fitness (by the setFitness() method) before sorting. When is an individual fit? 
		 * 	How do you encode that into a double (between 0 and 1)?
		 * - Decide when to stop, that is: when the algorithm has converged. And make sure you  terminate your loop when it does.
		 * - print the whole population after convergence and print the number of generations it took to converge.
		 * - print lots of output (especially if things go wrong).
		 * - work in an orderly and structured fashion (use tabs, use methods,..)
		 * - DONT'T make everything private. This will only complicate things. Keep variables local if possible
		 * - A common error are mistakes against pass-by-reference (this means that you pass the 
		 * 	address of an object, not a copy of the object to the method). There is a deepclone method included in the
		 *  Individual class.Use it!
		 * - You can compare your chromosome and your target string, using for eg. TARGET.charAt(i) == ...
		 * - Check your integers and doubles (eg. don't use ints for double divisions).
		 */
		
		/**IDEAS
		*create INTERFACES for fitness evaluation, mutation, termination
		*evaluation edit distance
		*selection elitist, probabilistic (likelyhood decreasing for lower fitness value: linearly, exponentially, logarithmically?)
		*mutation: uniform (every individual same probability), increasing (less fit individuals should try to adapt faster), constant, decreasing
		*recombination: recombine random individuals, recombine individuals with similar fitness
		*processing random pairing + mutation
		*termination: fixed number of generations, no change in fittest individual, condition is met
		*/
	}
	
	public static void testMutationRate (int repeat)
	{
		int popSize = 250;
		String targetString = new String ("HELLO WORLD");
		FitnessEvaluator eval = new EditDistance(new Individual (targetString.toCharArray()));
		Selector select = new DynamicProbSelector(100, 2);
		Terminator terminate = new StableSolutionTerminator(250);
		Recombinator recombine = new HalfRecombinator();
		
		for (double cMutRate = 0.05; cMutRate <= 0.75; cMutRate += 0.025)
		{
			Mutator mut = new ConstantMutator(cMutRate);
			
			PopProcessor proc = new RandPopProcessor(mut, recombine);
			
			GeneticAlgorithm ga = new GeneticAlgorithm(	popSize, 
														targetString, 
														eval, 
														proc, 
														select, 
														terminate);
			
			double avGens = 0, avFit = 0;
			for (int cRepeat = 0; cRepeat < repeat; ++cRepeat)
			{
				ga.Initialize();
				ga.evolve();
				avGens += ga.getGenerationsNeeded();
				avFit += ga.getFinalFitness();
			}
			avGens /= repeat;
			avFit /= repeat;
			System.out.println ("mutation rate " + cMutRate + " average gens/fit = " + avGens + " " + avFit);
		}
	}
	
	public static void testPopSize (int repeat)
	{
		double mutationRate = 0.18;
		String targetString = new String ("HELLO WORLD");
		FitnessEvaluator eval = new EditDistance(new Individual (targetString.toCharArray()));
		Selector select = new DynamicProbSelector(100, 2);
		Terminator terminate = new StableSolutionTerminator(250);
		Recombinator recombine = new HalfRecombinator();
		
		for (int cPop = 50; cPop <= 500; cPop += 25)
		{
			Mutator mut = new ConstantMutator(mutationRate);
			
			PopProcessor proc = new RandPopProcessor(mut, recombine);
			
			GeneticAlgorithm ga = new GeneticAlgorithm(	cPop, 
														targetString, 
														eval, 
														proc, 
														select, 
														terminate);
			
			double avGens = 0, avFit = 0;
			for (int cRepeat = 0; cRepeat < repeat; ++cRepeat)
			{
				ga.Initialize();
				ga.evolve();
				avGens += ga.getGenerationsNeeded();
				avFit += ga.getFinalFitness();
			}
			avGens /= repeat;
			avFit /= repeat;
			System.out.println ("population size " + cPop + " average gens/fit = " + avGens + " " + avFit);
		}
	}
	
	public static void testRecombinator (int repeat)
	{
		double mutationRate = 0.18;
		int popSize = 250;
		String targetString = new String ("HELLO WORLD");
		FitnessEvaluator eval = new EditDistance(new Individual (targetString.toCharArray()));
		Selector select = new DynamicProbSelector(100, 2);
		Terminator terminate = new StableSolutionTerminator(250);
		
		String des = null;
		for (int cRecombine = 0; cRecombine < 2; ++cRecombine)
		{
			Recombinator recombine = null;
			
			switch (cRecombine)
			{
			case 0: recombine = new HalfRecombinator();
					des = new String ("crossover");
			break;
			case 1: recombine = new AlternatingRecombinator();
					des = new String ("alternating");
			}
			
			Mutator mut = new ConstantMutator(mutationRate);
			
			PopProcessor proc = new RandPopProcessor(mut, recombine);
			
			GeneticAlgorithm ga = new GeneticAlgorithm(	popSize, 
														targetString, 
														eval, 
														proc, 
														select, 
														terminate);
			
			double avGens = 0, avFit = 0;
			for (int cRepeat = 0; cRepeat < repeat; ++cRepeat)
			{
				ga.Initialize();
				ga.evolve();
				avGens += ga.getGenerationsNeeded();
				avFit += ga.getFinalFitness();
			}
			avGens /= repeat;
			avFit /= repeat;
			System.out.println ("recombinator " + des + " average gens/fit = " + avGens + " " + avFit);
		}
	}
	
	public static void testMutator (int repeat)
	{
		double mutationRate = 0.18;
		int popSize = 250;
		String targetString = new String ("HELLO WORLD");
		FitnessEvaluator eval = new EditDistance(new Individual (targetString.toCharArray()));
		Selector select = new DynamicProbSelector(100, 2);
		Recombinator recombine = new HalfRecombinator();
		Terminator terminate = new StableSolutionTerminator(250);
		
		String des = null;
		for (int cMutate = 0; cMutate < 3; ++cMutate)
		{
			Mutator mut = null;
			
			switch (cMutate)
			{
			case 0: mut = new ConstantMutator(0.18);
					des = "constant mutator";
					break;
			case 1: mut = new UniformMutator(0.18);
					des = "uniform mutator";
					break;
			case 2: mut = new FitnessDepMutator();
					des = "fitness dependent mutator";
					break;
			}
			
			
			
			PopProcessor proc = new RandPopProcessor(mut, recombine);
			
			GeneticAlgorithm ga = new GeneticAlgorithm(	popSize, 
														targetString, 
														eval, 
														proc, 
														select, 
														terminate);
			
			double avGens = 0, avFit = 0;
			for (int cRepeat = 0; cRepeat < repeat; ++cRepeat)
			{
				ga.Initialize();
				ga.evolve();
				avGens += ga.getGenerationsNeeded();
				avFit += ga.getFinalFitness();
			}
			avGens /= repeat;
			avFit /= repeat;
			System.out.println ("mutator " + des + " average gens/fit = " + avGens + " " + avFit);
		}
	}
	
	public static void testProcessor (int repeat)
	{
		double mutationRate = 0.18;
		int popSize = 250;
		String targetString = new String ("HELLO WORLD");
		FitnessEvaluator eval = new EditDistance(new Individual (targetString.toCharArray()));
		Selector select = new DynamicProbSelector(100, 2);
		Recombinator recombine = new HalfRecombinator();
		Terminator terminate = new StableSolutionTerminator(250);
		
		String des = null;
		for (int cMutate = 0; cMutate < 2; ++cMutate)
		{
			Mutator mut = new FitnessDepMutator();
			PopProcessor proc = null;
			
			switch (cMutate)
			{
			case 0: proc = new RandPopProcessor(mut, recombine);
							des = "Random";
			break;
			case 1: proc = new ChunkPopProcessor(mut, recombine, 10);
							des = "chunk";
			break;
			}
			
			
			
			
			
			GeneticAlgorithm ga = new GeneticAlgorithm(	popSize, 
														targetString, 
														eval, 
														proc, 
														select, 
														terminate);
			
			double avGens = 0, avFit = 0;
			for (int cRepeat = 0; cRepeat < repeat; ++cRepeat)
			{
				ga.Initialize();
				ga.evolve();
				avGens += ga.getGenerationsNeeded();
				avFit += ga.getFinalFitness();
			}
			avGens /= repeat;
			avFit /= repeat;
			System.out.println ("recombinator " + des + " average gens/fit = " + avGens + " " + avFit);
		}
	}
}
