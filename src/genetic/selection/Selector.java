package genetic.selection;


import genetic.main.Individual;

import java.util.List;

public interface Selector 
{
	public void select (List <Individual> l);
}
