/*
 * Class created to for Portfolio which takes parameters for portfolio.
 */
package com.tbf;

import java.util.ArrayList;
import java.util.Comparator;

public class Portfolio
{
	private String portCode;
	private Person owner;
	private Broker manager;
	private Person beneficial;
	private ArrayList<Asset> portfolioAssets;
	double fees;
	double commissions;
	double riskMeasures;
	double annualReturns;
	double values;

	public Portfolio(String portCode, Person ownerCode, Broker managerCode, Person beneficialCode,
			ArrayList<Asset> portfolioAssets)
	{
		super();
		this.portCode = portCode;
		this.owner = ownerCode;
		this.manager = managerCode;
		this.beneficial = beneficialCode;
		this.portfolioAssets = portfolioAssets;
	}

	public String getPortCode()
	{
		return portCode;
	}

	public Person getOwner()
	{
		return owner;
	}

	public Person getPortManager()
	{
		return manager;
	}

	public Person getBeneficiary()
	{
		return beneficial;
	}

	public ArrayList<Asset> getPortfolioAssets()
	{
		return portfolioAssets;
	}

	public double getFees()
	{
		// Fee depends upon Expert and Junior broker so filter is needed
		if (manager.getBrokerType().equals("E"))
		{
			fees = manager.getFee();
		} else if (manager.getBrokerType().equals("J"))
		{
			fees = manager.getFee() * portfolioAssets.size();
		}
		return fees;
	}

	public double getCommissions()
	{
		// Commision depends upon Expert and Junior broker so filter is needed
		if (manager.getBrokerType().equals("E"))
		{
			commissions = (manager.getCommision() / 100.0) * getAnnualReturns();
		}

		else if (manager.getBrokerType().equals("J"))
		{
			commissions = (manager.getCommision() / 100) * getAnnualReturns();
		}
		return commissions;
	}

	public double getRiskMeasures()
	{
		riskMeasures = 0;
		for (int i = 0; i < portfolioAssets.size(); i++)
		{
			riskMeasures += portfolioAssets.get(i).risk() * (portfolioAssets.get(i).getTotalValue() / getValues());

		}
		return riskMeasures;
	}

	public double getAnnualReturns()
	{
		annualReturns = 0;
		for (int i = 0; i < portfolioAssets.size(); i++)
		{
			annualReturns += portfolioAssets.get(i).annualReturn();
		}
		return annualReturns;
	}

	public double getValues()
	{
		values = 0;
		for (int i = 0; i < portfolioAssets.size(); i++)
		{
			values += portfolioAssets.get(i).getTotalValue();
		}
		return values;
	}

	public static Comparator<Portfolio> PortfolioValueComparator = new Comparator<Portfolio>()
	{

		public int compare(Portfolio portfolio1, Portfolio portfolio2)
		{

			Double value1 = portfolio1.getValues();
			Double value2 = portfolio2.getValues();

			// highest to lowest order
			return value1.compareTo(value2);

		}

	};

	public static Comparator<Portfolio> PortfolioNameComparator = new Comparator<Portfolio>()
	{

		public int compare(Portfolio portfolio1, Portfolio portfolio2)
		{

			Person person1 = portfolio1.getOwner();
			Person person2 = portfolio2.getOwner();

			return person1.compareTo(person2);

		}

	};

	public static Comparator<Portfolio> PortfolioBrokerComparator = new Comparator<Portfolio>()
	{

		public int compare(Portfolio portfolio1, Portfolio portfolio2)
		{

			Broker person1 = (Broker) portfolio1.getPortManager();
			Broker person2 = (Broker) portfolio2.getPortManager();

			if (person1.getBrokerType().compareTo(person2.getBrokerType()) == 0)
			{
				return person1.compareTo(person2);
			}

			else
			{
				return person1.getBrokerType().compareTo(person2.getBrokerType());
			}

		}

	};

}
