package genetic.selection;

public class UniformProbSelector extends ProbSelector {

	public UniformProbSelector(double constantProb) 
	{
		super(constantProb);
	}

	@Override
	public double getRand() 
	{
		return mGen.nextDouble();
	}

}
