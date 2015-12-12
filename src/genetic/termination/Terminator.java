package genetic.termination;


import genetic.main.Individual;

import java.util.ArrayList;

public interface Terminator 
{
	public boolean terminate (ArrayList <Individual> pop);
}
