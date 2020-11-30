/*
 * Subclass for Assets beacause its risk, total balance , annual rate of return and value varries upon diifernt assets 
 */
package com.tbf;

public class Stock extends Asset
{

	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double betaMeasure;
	private String stockSymbol;
	private double sharePrice;
	private double sharesOwned;
	
	public Stock(String code, String type, String label, Double quarterlyDividend, Double baseRateOfReturn,
			Double betaMeasure, String stockSymbol, Double sharePrice)
	{
		super(code, type, label);
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.betaMeasure = betaMeasure;
		this.stockSymbol = stockSymbol;
		this.sharePrice = sharePrice;
	}
	
	public Stock (Stock s , double sharesOwned )
	{
		super(s.getCode(), s.getType(), s.getLabel());
		this.quarterlyDividend = s.getQuarterlyDividend();
		this.baseRateOfReturn = s.getBaseRateOfReturn();
		this.betaMeasure = s.getBetaMeasure();
		this.stockSymbol = s.getStockSymbol();
		this.sharePrice = s.getSharePrice();
		this.sharesOwned = sharesOwned;
	}

	public double getQuarterlyDividend()
	{
		return quarterlyDividend;
	}

	public double getBaseRateOfReturn()
	{
		return baseRateOfReturn;
	}

	public double getBetaMeasure()
	{
		return betaMeasure;
	}

	public String getStockSymbol()
	{
		return stockSymbol;
	}

	public double getSharePrice()
	{
		return sharePrice;
	}

	@Override
	public double risk()
	{

		return betaMeasure;
	}
	
	public double annualReturn()
	{
		return (((baseRateOfReturn * sharePrice ) + (4 * quarterlyDividend)) * sharesOwned);
	}

	public double getSharesOwned()
	{
		return sharesOwned;
	}

	public void setSharesOwned(double sharesOwned)
	{
		this.sharesOwned = sharesOwned;
	}
	
	public double getTotalValue()
	{
		return this.sharesOwned * this.sharePrice;
		
	}

}
