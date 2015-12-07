import java.util.Random;


public abstract class Mutator 
{
	private static int counter;
	private static final int mod = 10000;
	
	public Mutator ()
	{
		++counter;
		mGen = new Random (System.currentTimeMillis() / counter % mod);
		if (counter == Integer.MAX_VALUE)
			counter = 0;
	}
	
	public abstract void mutate (Individual i);
	
	protected Random mGen;
}
