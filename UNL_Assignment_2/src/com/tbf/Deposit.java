/*
 * Subclass for Assets because its risk, total balance of return and value varies upon different assets 
 */
package com.tbf;

public class Deposit extends Asset
{
	private double apr;
	private double totalBalance;

	public Deposit(String code, String type, String label, Double apr)
	{
		super(code, type, label);
		this.apr = apr;
	}

	public Deposit(Deposit d, double totalBalance)
	{
		super(d.getCode(), d.getType(), d.getLabel());
		this.apr = d.getApr();
		this.totalBalance = totalBalance;
	}

	public double getApr()
	{
		return apr;
	}

	public double risk()
	{
		return 0;
	}

	public double annualReturn()
	{
		return (((Math.exp(apr) - 1) * totalBalance));
	}

	public double getTotalBalance()
	{
		return totalBalance;
	}

	public void setTotalBalance(double totalBalance)
	{
		this.totalBalance = totalBalance;
	}

	public double getTotalValue()
	{
		return this.totalBalance;
	}

}
