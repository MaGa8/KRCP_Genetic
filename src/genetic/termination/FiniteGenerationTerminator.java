package genetic.termination;

import genetic.main.Individual;

import java.util.ArrayList;


public class FiniteGenerationTerminator implements Terminator {

	public FiniteGenerationTerminator (int generations)
	{
		mGenerations = generations;
	}
	
	public boolean terminate (ArrayList<Individual> pop) 
	{
		--mGenerations;
		if (mGenerations == 0)
			return false;
		return true;
	}
	
	private int mGenerations;
}
