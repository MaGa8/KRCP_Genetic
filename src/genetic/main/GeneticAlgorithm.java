package genetic.main;

import genetic.evaluation.EditDistance;
import genetic.evaluation.FitnessEvaluator;
import genetic.mutation.ConstantMutator;
import genetic.mutation.FitnessDepMutator;
import genetic.mutation.Mutator;
import genetic.processing.PopProcessor;
import genetic.processing.RandPopProcessor;
import genetic.recombination.HalfRecombinator;
import genetic.recombination.Recombinator;
import genetic.selection.ElitistSelector;
import genetic.selection.Selector;
import genetic.termination.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {

    public static String getParamDescriptionKeys()
    {
    	return new String ("Population size|number generations|evaluator|processor|selector|terminator");
    }
	
	public GeneticAlgorithm(int populationSize,
                            String target,
                            FitnessEvaluator fitness,
                            PopProcessor processor,
                            Selector selector,
                            Terminator terminator) {
        this.populationSize = populationSize;
        this.numberOfGenerations = 0;
        this.target = target;
        this.fitness = fitness;
        this.processor = processor;
        this.selector = selector;
        this.terminator = terminator;
    }

    public void Initialize() {
        for (char c = 'A'; c <= 'Z'; c++) {
            alphabet[c - 'A'] = c;
        }
        alphabet[26] = ' ';

        Random generator = new Random(System.currentTimeMillis());
        pop = new ArrayList<Individual>();
        // we initialize the population with random characters
        for (int i = 0; i < populationSize; i++) {
            char[] tempChromosome = new char[target.length()];
            for (int j = 0; j < target.length(); j++)
                tempChromosome[j] = alphabet[generator.nextInt(alphabet.length)]; 
            pop.add (new Individual(tempChromosome));
        }
        //reset
        numberOfGenerations = 0;
        mFinalFitness = 0.0;
        
    }

    public void evolve() {
        while (!terminator.terminate(pop)) {
            for (int i = 0; i < pop.size(); i++)
                fitness.evaluateFitness(pop.get(i));

            //System.out.println("Highest fitness " + pop.get(0).fitness);

            if (!pop.isEmpty() && pop.get(0).getChromosome().equals("HELLO WORLD"))
                System.out.println("Success");

            processor.process(pop);

            double max = 0;
            for (Individual i : pop) {
                if (i.getFitness() > max)
                    max = i.getFitness();
            }
            //System.out.println("Highest fitness after recombination " + max);

            selector.select(pop);
            ++numberOfGenerations;
        }
        if (!pop.isEmpty())
        	mFinalFitness = pop.get(0).getFitness();
    }

    public void printPop() {
        for (Individual i : pop)
            System.out.println(i.genoToPhenotype());
    }
    
    public String getParamDescription()
    {
    	return new String (	populationSize + "|" + 
    						numberOfGenerations + "|" +
    						fitness.toString() + "|" + 
    						processor.toString() + "|" + 
    						selector.toString() + "|" + 
    						terminator.toString());
    }
    
    public int getGenerationsNeeded()
    {
    	return numberOfGenerations;
    }
    
    public double getFinalFitness()
    {
    	return mFinalFitness;
    }

    private int populationSize;
    private int numberOfGenerations;
    private double mFinalFitness;
    private final String target;
    private ArrayList<Individual> pop;
    private FitnessEvaluator fitness;
    private PopProcessor processor;
    private Selector selector;
    private Terminator terminator;
    public static char[] alphabet = new char[27];
}