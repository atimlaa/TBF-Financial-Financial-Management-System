/*
 * This is an subclass of broker whose commisons and fee depends 
 */
package com.tbf;

import java.util.ArrayList;

public class Expert extends Broker
{

	public Expert(String code, String brokerType, String secCode, String lastName, String firstName, Address add,
			ArrayList<String> email)
	{
		super(code, brokerType, secCode, lastName, firstName, add, email);
	}

	
	public double getFee() 
	{
		return 0.0;
	}

	
	public double getCommision()
	{
		return 3.75;
	}

}
