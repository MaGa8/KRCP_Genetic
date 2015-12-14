package genetic.selection;

import genetic.main.Individual;

import java.util.List;

public class DynamicProbSelector extends ProbSelector {
	
	public DynamicProbSelector(double minValue, double coefficient) {
		super(0.0);
		this.mMinValue = minValue;
		this.mCoefficient = coefficient;
	}

	@Override
	public double getRand() {
		
		return mGen.nextDouble();
	}
	
	@Override
	public void select(List<Individual> l) 
	{
		//set const prob value
		if (l.size() > mMinValue)
			mConstantProb = Math.pow (Math.E, -mCoefficient * (l.size() - mMinValue));
		else
			mConstantProb = 1;
		super.select (l);
	}
	
	private int mTargetPopSize;
	private double mMinValue;
	private double mCoefficient;
}
