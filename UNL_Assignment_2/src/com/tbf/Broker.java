/*
 * This is subclass for Person not all person are broker but all broker are person
 */
package com.tbf;

import java.util.ArrayList;

public abstract class Broker extends Person
{
	String secCode;
	String brokerType;

	public Broker(String code, String brokerType, String secCode, String lastName, String firstName, Address add,
			ArrayList<String> email)
	{
		super(code, lastName, firstName, add, email);
		this.secCode = secCode;
		this.brokerType = brokerType;
	}

	public String getBrokerCode()
	{
		return secCode;
	}

	public String getBrokerType()
	{
		return brokerType;
	}

	public abstract double getFee();

	public abstract double getCommision();
}
