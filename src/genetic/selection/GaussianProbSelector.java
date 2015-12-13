package genetic.selection;


public class GaussianProbSelector extends ProbSelector
{
	public GaussianProbSelector(double constantProb) 
	{
		super(constantProb);
	}

	@Override
	public double getRand() 
	{
		return (mGen.nextGaussian() + 1) / 2;
	}
	
	
	
}
