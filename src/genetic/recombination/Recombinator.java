package genetic.recombination;
import genetic.main.Individual;


/**
 * @author martin
 * Implements recombination process
 */
public interface Recombinator 
{
	public abstract Individual recombine (Individual p1, Individual p2);
}
