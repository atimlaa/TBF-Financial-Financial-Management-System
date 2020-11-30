/*
 * Subclass for Assets beacause its risk, total balance , annual rate of return and value varries upon diifernt assets 
 */
package com.tbf;

public class PrivateInvestment extends Asset
{

	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double baseOmegaMeasure;
	private double totalValue;
	private double stakePercentage;

	// Constructor
	public PrivateInvestment(String code, String type, String label, Double quarterlyDividend, Double baseRateOfReturn,
			Double baseOmegaMeasure, Double totalValue)
	{
		super(code, type, label);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.baseOmegaMeasure = baseOmegaMeasure;
		this.totalValue = totalValue;
	}

	public PrivateInvestment(PrivateInvestment p, double stakePercentage)
	{
		super(p.getCode(), p.getType(), p.getLabel());
		this.quarterlyDividend = p.getQuarterlyDividend();
		this.baseRateOfReturn = p.getBaseRateOfReturn();
		this.baseOmegaMeasure = p.getBaseOmegaMeasure();
		this.totalValue = p.totalValue;
		this.stakePercentage = stakePercentage;
	}

	public double getQuarterlyDividend()
	{
		return quarterlyDividend;
	}

	public double getBaseRateOfReturn()
	{
		return baseRateOfReturn;
	}

	public double getBaseOmegaMeasure()
	{
		return baseOmegaMeasure;
	}

	public double getTotalValue()
	{
		return (stakePercentage / 100.0) * totalValue;
	}

	public double risk()
	{
		return baseOmegaMeasure + Math.pow(Math.E, (double)(-125500.00 / totalValue));
	}

	public double annualReturn()
	{
		return ((baseRateOfReturn * totalValue)+ (4 * (quarterlyDividend )))  * (getStakePercentage()/100.0);
	}

	public double getStakePercentage()
	{
		return stakePercentage;
	}

	public void setStakePercentage(double stakePercentage)
	{
		this.stakePercentage = stakePercentage;
	}

}
