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
	static public char[] alphabet = new char[27];

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int popSize = 50000;
		for (char c = 'A'; c <= 'Z'; c++) {
			alphabet[c - 'A'] = c;
		}
		alphabet[26] = ' ';
		Random generator = new Random(System.currentTimeMillis());
		Individual[] population = new Individual[popSize];
		// we initialize the population with random characters
		for (int i = 0; i < popSize; i++) 
		{
			char[] tempChromosome = new char[TARGET.length()];
			for (int j = 0; j < TARGET.length(); j++) {
				tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)]; //choose a random letter in the alphabet
			}
			population[i] = new Individual(tempChromosome);
		}
		// What does your population look like?
		for (int i = 0; i < population.length; i++) {
			System.out.println(population[i].genoToPhenotype());
		}

		int numberOfGenerations = 100;
		Terminator terminator = new FiniteGenerationTerminator(numberOfGenerations);
		//Terminator terminator = new StableSolutionTerminator(10);

		ArrayList<Individual> pop =  new ArrayList<Individual>(Arrays.asList(population));
		FitnessEvaluator fitnessEval = new EditDistance (new Individual(TARGET.toCharArray()));

		while (!terminator.terminate(pop)) {
			for (int i = 0; i < pop.size(); i++)
				fitnessEval.evaluateFitness(pop.get(i));
			
			//System.out.println ("new generation size " + pop.size());
			//Selector selector = new GaussianProbSelector();
			System.out.println ("Highest fitness " + pop.get(0).fitness);
			
			if (pop.get(0).getChromosome().equals ("HELLO WORLD"))
				System.out.println ("Success");
			
			Recombinator recombinator = new HalfRecombinator();
			//Recombinator recombinator = new AlternatingRecombinator();
			//Recombinator recombinator = new SingletonRecombinator();
			Mutator mutator = new FitnessDepMutator();
			//Mutator mutator = new UniformMutator(0.2);
			PopProcessor popProc = new RandPopProcessor(mutator, recombinator); 
			popProc.process(pop);
			
			double max = 0;
			for (Individual i : pop)
			{
				if (i.getFitness() > max)
					max = i.getFitness();
			}
			System.out.println ("Highest fitness after recombination " + max);
			
			Selector selector = new ElitistSelector (100);
			//Selector selector = new ProbSelector();
			selector.select(pop);
			
			//printPop (pop);
			System.out.println();
		}

		System.out.println();
		System.out.println ("After");
		// What does your population look like?
		printPop (pop);


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
	
	public static void printPop (ArrayList <Individual> pop)
	{
		for (Individual i : pop)
			System.out.println (i.genoToPhenotype());
	}
}
