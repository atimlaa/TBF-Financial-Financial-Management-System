/*
 * This class extends broker and as commision and fee varies we created this class 
 */
package com.tbf;

import java.util.ArrayList;

public class Junior extends Broker
{

	public Junior(String code, String brokerType, String secCode, String lastName, String firstName, Address add,
			ArrayList<String> email)
	{
		super(code, brokerType, secCode, lastName, firstName, add, email);
	}

	
	public double getFee()
	{
		return 75.00;
	}

	
	public double getCommision()
	{
		return 1.25;
	}

}
