/*
 * This program prints out the report both as summary and individually.
 */
package com.tbf;

import java.util.ArrayList;
import java.util.LinkedList;

public class PortfolioReport
{
	public static void main(String[] args)
	{
		DatabaseReader db = new DatabaseReader();
		ArrayList<Person> person = db.readPerson();
		ArrayList<Asset> asset = db.readAssets();
		LinkedList<Portfolio> port = db.readPortfolio();
		MyLinkedList<Portfolio> listValue = new MyLinkedList<Portfolio>(Portfolio.PortfolioValueComparator);
		MyLinkedList<Portfolio> listName = new MyLinkedList<Portfolio>(Portfolio.PortfolioNameComparator);
		MyLinkedList<Portfolio> listBroker = new MyLinkedList<Portfolio>(Portfolio.PortfolioBrokerComparator);

		for (int i = 0; i < port.size(); i++)
		{
			listValue.insert(port.get(i));
		}

		for (int i = 0; i < port.size(); i++)
		{
			listName.insert(port.get(i));
		}

		for (int i = 0; i < port.size(); i++)
		{
			listBroker.insert(port.get(i));
		}

		// Summary Report
		System.out.println("Portfolio Summary Report");
		System.out.println(
				"======================================================================================================================================================");
		// Table Header
		System.out.printf("%-8s %-26s %-26s %-16s %-16s %-16s %10s %15s", "Portfolio", "Owner", "Manager", "Fees",
				"Commisions", "Weighted Risk", "Return ", "Total");
		double fee = 0.0;
		String portCode = null;
		double commisions = 0.0;
		double weighthRisk = 0.0;
		double total = 0.0;
		double aReturn = 0.0;
		double totalFee = 0.0;
		double totalCommsions = 0.0;
		double totalReturn = 0.0;
		double totalTotal = 0.0;

		// looping through the Portfolio ArrayList to get the respective needed
		// calculated values via pre-created functions
		for (int i = 0; i < listValue.getSize(); i++)
		{
			portCode = listValue.getNodeAtIndex(i).getPortCode();

			fee = listValue.getNodeAtIndex(i).getFees();
			commisions = listValue.getNodeAtIndex(i).getCommissions();
			weighthRisk = listValue.getNodeAtIndex(i).getRiskMeasures();
			aReturn = listValue.getNodeAtIndex(i).getAnnualReturns();
			total = listValue.getNodeAtIndex(i).getValues();

			totalFee += fee;
			totalCommsions += commisions;
			totalReturn += aReturn;
			totalTotal += total;

			System.out.printf("\n%-8s %-26s %-26s $%-16.2f $%-16.2f $%-16.4f $%-16.2f $%-16.2f \n", portCode,
					listValue.getNodeAtIndex(i).getOwner().getlastName() + ","
							+ listValue.getNodeAtIndex(i).getOwner().getfirstName(),
					listValue.getNodeAtIndex(i).getPortManager().getlastName() + ","
							+ listValue.getNodeAtIndex(i).getPortManager().getfirstName(),
					fee, commisions, weighthRisk, aReturn, total);

		}
		// Footer for calculating Totals
		System.out.printf(
				"-------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		System.out.printf("%-8s %-26s %-26s %-16.2f $%-16.2f $  %-16s $  %-18.2f $  %-18.2f \n\n\n", " ", " ",
				" Totals", totalFee, totalCommsions, " ", totalReturn, totalTotal);

		// Summary Report
		System.out.println("Portfolio Summary Report");
		System.out.println(
				"======================================================================================================================================================");
		// Table Header
		System.out.printf("%-8s %-26s %-26s %-16s %-16s %-16s %10s %15s", "Portfolio", "Owner", "Manager", "Fees",
				"Commisions", "Weighted Risk", "Return ", "Total");

		fee = 0.0;
		portCode = null;
		commisions = 0.0;
		weighthRisk = 0.0;
		total = 0.0;
		aReturn = 0.0;
		totalFee = 0.0;
		totalCommsions = 0.0;
		totalReturn = 0.0;
		totalTotal = 0.0;

		// looping through the Portfolio ArrayList to get the respective needed
		// calculated values via pre-created functions
		for (int i = 0; i < listName.getSize(); i++)
		{
			portCode = listName.getNodeAtIndex(i).getPortCode();

			fee = listName.getNodeAtIndex(i).getFees();
			commisions = listName.getNodeAtIndex(i).getCommissions();
			weighthRisk = listName.getNodeAtIndex(i).getRiskMeasures();
			aReturn = listName.getNodeAtIndex(i).getAnnualReturns();
			total = listName.getNodeAtIndex(i).getValues();

			totalFee += fee;
			totalCommsions += commisions;
			totalReturn += aReturn;
			totalTotal += total;

			System.out.printf("\n%-8s %-26s %-26s $%-16.2f $%-16.2f $%-16.4f $%-16.2f $%-16.2f \n", portCode,
					listName.getNodeAtIndex(i).getOwner().getlastName() + ","
							+ listName.getNodeAtIndex(i).getOwner().getfirstName(),
					listName.getNodeAtIndex(i).getPortManager().getlastName() + ","
							+ listName.getNodeAtIndex(i).getPortManager().getfirstName(),
					fee, commisions, weighthRisk, aReturn, total);

		}
		// Footer for calculating Totals
		System.out.printf(
				"-------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		System.out.printf("%-8s %-26s %-26s %-16.2f $%-16.2f $  %-16s $  %-18.2f $  %-18.2f \n\n\n", " ", " ",
				" Totals", totalFee, totalCommsions, " ", totalReturn, totalTotal);

		// Footer for calculating Totals
		System.out.printf(
				"-------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		System.out.printf("%-8s %-26s %-26s %-16.2f $%-16.2f $  %-16s $  %-18.2f $  %-18.2f \n\n\n", " ", " ",
				" Totals", totalFee, totalCommsions, " ", totalReturn, totalTotal);

		// Summary Report
		System.out.println("Portfolio Summary Report");
		System.out.println(
				"======================================================================================================================================================");
		// Table Header
		System.out.printf("%-8s %-26s %-26s %-16s %-16s %-16s %10s %15s", "Portfolio", "Owner", "Manager", "Fees",
				"Commisions", "Weighted Risk", "Return ", "Total");

		fee = 0.0;
		portCode = null;
		commisions = 0.0;
		weighthRisk = 0.0;
		total = 0.0;
		aReturn = 0.0;
		totalFee = 0.0;
		totalCommsions = 0.0;
		totalReturn = 0.0;
		totalTotal = 0.0;
		// looping through the Portfolio ArrayList to get the respective needed
		// calculated values via pre-created functions
		for (int i = 0; i < listBroker.getSize(); i++)
		{
			portCode = listBroker.getNodeAtIndex(i).getPortCode();

			fee = listBroker.getNodeAtIndex(i).getFees();
			commisions = listBroker.getNodeAtIndex(i).getCommissions();
			weighthRisk = listBroker.getNodeAtIndex(i).getRiskMeasures();
			aReturn = listBroker.getNodeAtIndex(i).getAnnualReturns();
			total = listBroker.getNodeAtIndex(i).getValues();

			totalFee += fee;
			totalCommsions += commisions;
			totalReturn += aReturn;
			totalTotal += total;

			System.out.printf("\n%-8s %-26s %-26s $%-16.2f $%-16.2f $%-16.4f $%-16.2f $%-16.2f \n", portCode,
					listBroker.getNodeAtIndex(i).getOwner().getlastName() + ","
							+ listBroker.getNodeAtIndex(i).getOwner().getfirstName(),
					listBroker.getNodeAtIndex(i).getPortManager().getlastName() + ","
							+ listBroker.getNodeAtIndex(i).getPortManager().getfirstName(),
					fee, commisions, weighthRisk, aReturn, total);

		}
		// Footer for calculating Totals
		System.out.printf(
				"-------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		System.out.printf("%-8s %-26s %-26s %-16.2f $%-16.2f $  %-16s $  %-18.2f $  %-18.2f \n\n\n", " ", " ",
				" Totals", totalFee, totalCommsions, " ", totalReturn, totalTotal);

	}

}
