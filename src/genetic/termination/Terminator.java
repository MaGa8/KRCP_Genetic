package genetic.termination;


import genetic.main.Individual;

import java.util.ArrayList;

public interface Terminator 
{
	/**
	 * @param pop population
	 * @return true if algorithm should terminate
	 */
	public boolean terminate (ArrayList <Individual> pop);
}
