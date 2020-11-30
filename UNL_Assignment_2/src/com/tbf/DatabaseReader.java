/*
 * This class reads through person, assets, and portfolio file and uses queres to create a database. 
 * Making tables with respective properties such as name, address and etc.
 */
package com.tbf;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;

public class DatabaseReader
{
	private ArrayList<Person> p = new ArrayList<Person>();
	private ArrayList<Asset> a = new ArrayList<Asset>();
	private LinkedList<Portfolio> port = new LinkedList<Portfolio>();

	// this method reads person
	public ArrayList<Person> readPerson()
	{
		// create connection and prepare statements and result sets
		Connection conn = DatabaseInfo.getConnection();

		// queries
		String query = "SELECT * FROM Person;";
		String query2 = "select email from Email where personID = ?;";

		PreparedStatement ps, ps2;
		ResultSet rs, rs2;

		try
		{
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			// retrieve data from database
			while (rs.next())
			{
				String id = rs.getString("id");
				String code = rs.getString("personCode");
				String brokerType = rs.getString("brokerType");
				String secCode = rs.getString("secCode");
				String lastName = rs.getString("lastName");
				String firstName = rs.getString("firstName");
				int addressID = rs.getInt("addressID");

				// using getAddress method
				Address add = DatabaseReader.getAddress(addressID);

				// implementing query for getting email
				ps2 = conn.prepareStatement(query2);
				ps2.setString(1, id);
				rs2 = ps2.executeQuery();
				ArrayList<String> emailarr = new ArrayList<String>();
				String email = null;

				while (rs2.next())
				{
					email = rs2.getString("email");
					emailarr.add(email);
				}

				// using if else statements as we are using different constructors to store
				// person

				if (brokerType == null || brokerType.isEmpty())
				{
					Person person = new Person(code, lastName, firstName, add, emailarr);
					p.add(person);
				}
				else if (brokerType.equals("E"))
				{
					Expert person = new Expert(code, brokerType, secCode, lastName, firstName, add, emailarr);
					p.add(person);
				} 
				else if (brokerType.equals("J"))
				{
					Junior person = new Junior(code, brokerType, secCode, lastName, firstName, add, emailarr);
					p.add(person);
				} 
			}

		} catch (SQLException sqle)
		{
			throw new RuntimeException(sqle);
		}
		// close connections
		try
		{
			if (rs != null && !rs.isClosed())
			{
				rs.close();
			}
			if (ps != null && !ps.isClosed())
			{
				ps.close();
			}
			if (conn != null && !conn.isClosed())
			{
				conn.close();
			}
		} catch (SQLException sqle)
		{
			throw new RuntimeException(sqle);
		}

		return p;
	}

	// method to get Address
	public static Address getAddress(int id)
	{
		// queries
		final String AddressQuery = "SELECT * FROM Address WHERE id=?;";
		final String StateQuery = "SELECT name,countryID FROM StatesCountries WHERE id=?;";
		final String CountryQuery = "SELECT name FROM Countries WHERE id=?;";

		// Create connection and prepare statement and result sets
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps, ps2, ps3;
		ResultSet rs, rs2, rs3;

		Address add = null;
		String street = null, city = null, state = null, country = null, zipcode = null;

		try
		{
			ps = conn.prepareStatement(AddressQuery);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			// retrieve data from database
			while (rs.next())
			{
				street = rs.getString("street");
				city = rs.getString("city");
				zipcode = rs.getString("zipcode");
				int stateCountriesID = rs.getInt("stateCountriesID");

				// query to get state country
				ps2 = conn.prepareStatement(StateQuery);
				ps2.setInt(1, stateCountriesID);
				rs2 = ps2.executeQuery();
				while (rs2.next())
				{
					state = rs2.getString("name");
					int countryID = rs2.getInt("countryID");

					// query to get country
					ps3 = conn.prepareStatement(CountryQuery);
					ps3.setInt(1, countryID);
					rs3 = ps3.executeQuery();
					while (rs3.next())
					{
						country = rs3.getString("name");
					}

				}
				add = new Address(street, city, state, country, zipcode);

			}
			conn.close();
		} catch (SQLException sqle)
		{
			throw new RuntimeException(sqle);
		}
		return add;
	}

	// method to read Assets
	public ArrayList<Asset> readAssets()
	{
		// Create connection and prepare statement and result sets
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;
		ResultSet rs;

		a = new ArrayList<Asset>();

		// queries
		String query1 = "select label, assetCode, apr, assetType  from Deposit JOIN Asset on Deposit.assetID = Asset.id;";
		String query2 = "select assetCode, assetType, label, quarterlyDividend, baseRateOfReturn, betaMeasure, stockSymbol, sharePrice from Stocks JOIN Asset on Stocks.assetID = Asset.id;";
		String query3 = "select assetCode, assetType, label, quarterlyDividend, baseRateOfReturn, baseOmegaMeasure, totalValue from PrivateInvestments JOIN Asset on PrivateInvestments.assetID = Asset.id;";

		try
		{
			ps = conn.prepareStatement(query1);
			rs = ps.executeQuery();

			// retrieve data from database for Deposit
			while (rs.next())
			{

				String code = rs.getString("assetCode");
				String type = rs.getString("assetType");
				String label = rs.getString("label");
				double apr = rs.getDouble("apr");

				Asset asset = new Deposit(code, type, label, apr);

				a.add(asset);
			}

			ps = conn.prepareStatement(query2);
			rs = ps.executeQuery();
			// retrieve data from database for sTOCKS

			while (rs.next())
			{
				String code = rs.getString("assetCode");
				String type = rs.getString("assetType");
				String label = rs.getString("label");
				double quarterlyDividend = rs.getDouble("quarterlyDividend");
				double baseRateOfReturn = rs.getDouble("baseRateOfReturn");
				double betaMeasure = rs.getDouble("betaMeasure");
				String stockSymbol = rs.getString("stockSymbol");
				double sharePrice = rs.getDouble("sharePrice");

				Asset asset = new Stock(code, type, label, quarterlyDividend, baseRateOfReturn, betaMeasure,
						stockSymbol, sharePrice);

				a.add(asset);
			}
			ps = conn.prepareStatement(query3);
			rs = ps.executeQuery();
			// retrieve data from database for PrivateInvestment

			while (rs.next())
			{

				
				String code = rs.getString("assetCode");
				String type = rs.getString("assetType");
				String label = rs.getString("label");
				double quarterlyDividend = rs.getDouble("quarterlyDividend");
				double baseRateOfReturn = rs.getDouble("baseRateOfReturn");
				double baseOmegaMeasure = rs.getDouble("baseOmegaMeasure");
				double totalValue = rs.getDouble("totalValue");

				Asset asset = new PrivateInvestment(code, type, label, quarterlyDividend, baseRateOfReturn,
						baseOmegaMeasure, totalValue);
				a.add(asset);
			}
		} catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		// code connections ps and rs
		try
		{
			if (rs != null && !rs.isClosed())
			{
				rs.close();
			}
			if (ps != null && !ps.isClosed())
			{
				ps.close();
			}
			if (conn != null && !conn.isClosed())
			{
				conn.close();
			}
		} catch (SQLException sqle)
		{
			throw new RuntimeException(sqle);
		}

		return a;
	}

	// method to read Portfolio
	public LinkedList<Portfolio> readPortfolio()
	{
		// Create connection and prepare statement and result sets
		readAssets();
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps, ps2, ps3;
		ResultSet rs, rs2, rs3;

		// Queries
		String query1 = "select id, portCode, ownerID , managerID, beneficialID  from Portfolio;";
		String query2 = "select portfolioID, assetID, totalno  from PortfolioAsset where portfolioID = ?;";
		String query3 = "select assetCode from Asset a where a.id=?;";

		try
		{
			ps = conn.prepareStatement(query1);
			rs = ps.executeQuery();
			// retrieve data from Database for Portfolio
			while (rs.next())
			{
				int id = rs.getInt("id");
				String portCode = rs.getString("portCode");
				int ownerID = rs.getInt("ownerID");
				int managerID = rs.getInt("managerID");
				int beneficialID = rs.getInt("beneficialID");

				// using getPerson method to get code for respective persons
				Person owner = DatabaseReader.getPerson(ownerID);
				Person mgr = DatabaseReader.getPerson(managerID);
				// type casting Broker
				Broker manager = (Broker) mgr;
				Person beneficial = DatabaseReader.getPerson(beneficialID);

				ps2 = conn.prepareStatement(query2);
				ps2.setInt(1, id);
				rs2 = ps2.executeQuery();

				ArrayList<Asset> portfolioAssets = new ArrayList<Asset>();
				// retrieve data for PortfolioAssets
				while (rs2.next())
				{
					int portfolioID = rs2.getInt("portfolioID");
					int assetID = rs2.getInt("assetID");
					int totalno = rs2.getInt("totalno");

					ps3 = conn.prepareStatement(query3);
					ps3.setInt(1, assetID);
					rs3 = ps3.executeQuery();
					if (rs3.next())
					{
						String assetCode = rs3.getString("assetCode");
						// finding the asset associated with the code we got
						for (Asset x : a)
						{
							if (x.getCode().equals(assetCode))
							{

								Asset temp = x;

								if (temp.getType().equals("S"))
								{
									// copy constructor set up
									Stock stock = new Stock((Stock) temp, totalno);
									portfolioAssets.add(stock);

								}

								else if (temp.getType().equals("D"))
								{
									// copy constructor set up
									Deposit deposit = new Deposit((Deposit) temp, totalno);
									portfolioAssets.add(deposit);
								}

								else if (temp.getType().equals("P"))
								{
									// copy constructor set up
									PrivateInvestment pvtinv = new PrivateInvestment((PrivateInvestment) temp, totalno);
									portfolioAssets.add(pvtinv);

								}
							}

						}

					}
				}

				Portfolio portf = new Portfolio(portCode, owner, manager, beneficial, portfolioAssets);
				port.add(portf);

			}
		} catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		// close Connections
		try
		{
			if (rs != null && !rs.isClosed())
			{
				rs.close();
			}
			if (ps != null && !ps.isClosed())
			{
				ps.close();
			}
			if (conn != null && !conn.isClosed())
			{
				conn.close();
			}
		} catch (SQLException sqle)
		{
			throw new RuntimeException(sqle);
		}

		return port;
	}

	// method to get Person
	public static Person getPerson(int personID)
	{
		// Queries
		String Query = "SELECT * FROM Person WHERE id=?;";
		String query2 = "select email from Email where personID = ?;";

		// Create connection and prepare Sql statement
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps, ps2;
		ResultSet rs, rs2;

		Person person = null;
		String street = null, city = null, state = null, country = null, zipcode = null;

		try
		{
			ps = conn.prepareStatement(Query);
			ps.setInt(1, personID);
			rs = ps.executeQuery();

			// retrieve data from database
			while (rs.next())
			{
				String personCode = rs.getString("personCode");
				String brokerType = rs.getString("brokerType");
				String secCode = rs.getString("secCode");
				String lastName = rs.getString("lastName");
				String firstName = rs.getString("firstName");
				int addressID = rs.getInt("addressID");

				Address add = DatabaseReader.getAddress(addressID);

				add = new Address(street, city, state, country, zipcode);

				ps2 = conn.prepareStatement(query2);
				ps2.setInt(1, personID);
				rs2 = ps2.executeQuery();
				ArrayList<String> emailarr = new ArrayList<String>();
				String email = null;

				while (rs2.next())
				{
					email = rs2.getString("email");
					emailarr.add(email);
				}

				if (secCode == null)
				{
					person = new Person(personCode, lastName, firstName, add, emailarr);
				} else
				{
					if (brokerType.equals("J"))
					{
						person = new Junior(personCode, brokerType, secCode, lastName, firstName, add, emailarr);
					} else if (brokerType.equals("E"))
					{
						person = new Expert(personCode, brokerType, secCode, lastName, firstName, add, emailarr);
					}
				}

			}
			// close connections
			ps.close();
			conn.close();
		} catch (SQLException sqle)
		{
			throw new RuntimeException(sqle);
		}
		return person;
	}

}
