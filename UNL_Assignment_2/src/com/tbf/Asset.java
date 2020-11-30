/*
 * This is an abstract class because no people will have only asset either they will have 
 * stock account, deposit account or private account. 
 */
package com.tbf;

public abstract class Asset
{
	protected String code;
	protected String type;
	protected String label;

	// constructor
	public Asset(String code, String type, String label)
	{
		super();
		this.code = code;
		this.type = type;
		this.label = label;
	}

	public String getCode()
	{
		return code;
	}

	public String getType()
	{
		return type;
	}

	public String getLabel()
	{
		return label;
	}

	// Abstract functions
	public abstract double risk();

	public abstract double annualReturn();

	public abstract double getTotalValue();

}
