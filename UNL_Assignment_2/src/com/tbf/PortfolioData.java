/*
 * This class is for creating queries. Queries such as add, remove and getting is to be implemented 
 */
package com.tbf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class PortfolioData
{

	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons()
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;

// delete personID from Invoices, Emails, and Customers
		String query1 = "delete from Email";
		String query2 = "delete from Portfolio";
		String query3 = "delete from Person";
		try
		{
			ps = conn.prepareStatement(query1);
			ps.executeUpdate();
			ps = conn.prepareStatement(query2);
			ps.executeUpdate();
			ps = conn.prepareStatement(query3);
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Removes the person record from the database corresponding to the provided
	 * <code>personCode</code>
	 * 
	 * @param personCode
	 */
	public static void removePerson(String personCode)
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;
		String query = "delete from Person where personCode = ?";
		try
		{
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>brokerType</code> will either be "E" or "J" (Expert or Junior) or
	 * <code>null</code> if the person is not a broker.
	 * 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param brokerType
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city,
			String state, String zip, String country, String brokerType, String secBrokerId)
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;
		int addressID = 0;
		int stateCountryID = 0;
		int countryID = 0;

		String query = "insert into Person (personCode, brokerType, secCode, lastName, firstName, addressID)"
				+ "values (?, ?, ?, ?, ?, ?)";
		
		countryID=PortfolioData.getCountryID(country);
		stateCountryID = PortfolioData.getStateCountryID(state,state,countryID);
		addressID = PortfolioData.getAddressID(street, city, stateCountryID, zip);

		// check if the address is already existing in the table, if it is not then add.
		int existingAddress = PortfolioData.getAddressID(street, city, stateCountryID, zip);

		// add new Address if it is not existing already
		if (existingAddress != 0)
		{
			addressID = existingAddress;
		} else
		{
			PortfolioData.addAddress(street, city, stateCountryID, zip);
			addressID = PortfolioData.getAddressID(street, city, stateCountryID, zip);
		}

		try
		{
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, brokerType);
			ps.setString(3, secBrokerId);
			ps.setString(4, lastName);
			ps.setString(5, firstName);
			ps.setInt(6, addressID);
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email)
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps = null;

		String getPersonID = "(select id from Person where personCode = ?)";
		String addEmail = "insert into Email (personID, email) values (" + getPersonID + ", ?)";
		try
		{
			ps = conn.prepareStatement(addEmail);
			ps.setString(1, personCode);
			ps.setString(2, email);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Removes all asset records from the database
	 */
	public static void removeAllAssets()
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;

		String query1 = "delete from Deposit";
		String query2 = "delete from Stocks";
		String query3 = "delete from PrivateInvestments";
		String query4 = "delete from PortfolioAsset";
		String query5 = "delete from Asset";

		try
		{
			ps = conn.prepareStatement(query1);
			ps.executeUpdate();
			ps = conn.prepareStatement(query2);
			ps.executeUpdate();
			ps = conn.prepareStatement(query3);
			ps.executeUpdate();
			ps = conn.prepareStatement(query4);
			ps.executeUpdate();
			ps = conn.prepareStatement(query5);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Removes the asset record from the database corresponding to the provided
	 * <code>assetCode</code>
	 * 
	 * @param assetCode
	 */
	public static void removeAsset(String assetCode)
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;
		String query = "delete from Asset where assetCode = ?";
		try
		{
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Adds a deposit account asset record to the database with the provided data.
	 * 
	 * @param assetCode
	 * @param label
	 * @param apr
	 */
	public static void addDepositAccount(String assetCode, String label, double apr)
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;
		PortfolioData.addAssets(assetCode, "D");
		int assetID = PortfolioData.getAssetID(assetCode);

		String insertDeposit = "insert into Deposit (label, apr, assetID)" + "values (?, ?, ?)";
		try
		{
			ps = conn.prepareStatement(insertDeposit);
			ps.setString(1, label);
			ps.setDouble(2, apr);
			ps.setInt(3, assetID);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Adds a private investment asset record to the database with the provided
	 * data.
	 * 
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param baseOmega
	 * @param totalValue
	 */
	public static void addPrivateInvestment(String assetCode, String label, Double quarterlyDividend,
			Double baseRateOfReturn, Double baseOmega, Double totalValue)
	{

		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;
		PortfolioData.addAssets(assetCode, "P");
		int assetID = PortfolioData.getAssetID(assetCode);

		String insertPrivateInvestment = "insert into PrivateInvestments (label, quarterlyDividend, baseRateOfReturn, baseOmegaMeasure, totalValue, assetID)"
				+ "values (?, ?, ?, ?, ?, ?)";
		try
		{
			ps = conn.prepareStatement(insertPrivateInvestment);
			ps.setString(1, label);
			ps.setDouble(2, quarterlyDividend);
			ps.setDouble(3, baseRateOfReturn);
			ps.setDouble(4, baseOmega);
			ps.setDouble(5, totalValue);
			ps.setInt(6, assetID);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Adds a stock asset record to the database with the provided data.
	 * 
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn
	 * @param beta
	 * @param stockSymbol
	 * @param sharePrice
	 */
	public static void addStock(String assetCode, String label, Double quarterlyDividend, Double baseRateOfReturn,
			Double beta, String stockSymbol, Double sharePrice)
	{

		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;
		PortfolioData.addAssets(assetCode, "S");
		int assetID = PortfolioData.getAssetID(assetCode);

		String insertStocks = "insert into Stocks (label, quarterlyDividend, baseRateOfReturn, betaMeasure, stockSymbol, sharePrice, assetID)"
				+ "values (?, ?, ?, ?, ?, ?, ?)";
		try
		{
			ps = conn.prepareStatement(insertStocks);
			ps.setString(1, label);
			ps.setDouble(2, quarterlyDividend);
			ps.setDouble(3, baseRateOfReturn);
			ps.setDouble(4, beta);
			ps.setString(5, stockSymbol);
			ps.setDouble(6, sharePrice);
			ps.setInt(7, assetID);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Removes all portfolio records from the database
	 */
	public static void removeAllPortfolios()
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;

		String query1 = "delete from `PortfolioAsset`";
		String query2 = "delete from `Portfolio`;";

		try
		{
			ps = conn.prepareStatement(query1);
			ps.executeUpdate();

			ps = conn.prepareStatement(query2);
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Removes the portfolio record from the database corresponding to the provided
	 * <code>portfolioCode</code>
	 * 
	 * @param portfolioCode
	 */
	public static void removePortfolio(String portfolioCode)
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;

		String query1 = "delete from Portfolio where portCode = ?";
		String getPortfolioID = "select id from Portfolio where portCode = ?";
		String query3 = "delete from PortfolioAsset where portfolioID = " + getPortfolioID + "";

		try
		{
			ps = conn.prepareStatement(query3);
			ps.setString(1, portfolioCode);
			ps.executeUpdate();

			ps = conn.prepareStatement(query1);
			ps.setString(1, portfolioCode);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Adds a portfolio records to the database with the given data. If the
	 * portfolio has no beneficiary, the <code>beneficiaryCode</code> will be
	 * <code>null</code>
	 * 
	 * @param portfolioCode
	 * @param ownerCode
	 * @param managerCode
	 * @param beneficiaryCode
	 */
	public static void addPortfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode)
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;
		String getOwnerID = "select id from Person where personCode = ?";
		String getManagerID = "select id from Person where personCode = ?";
		String getBeneficiaryID = "select id from Person where personCode = ?";
		String insertPortfolio = "insert into Portfolio(portCode,ownerID,managerID,beneficialID) " + "values(?,("
				+ getOwnerID + "), (" + getManagerID + "), (" + getBeneficiaryID + "))";
		try
		{
			ps = conn.prepareStatement(insertPortfolio);
			ps.setString(1, portfolioCode);
			ps.setString(2, ownerCode);
			ps.setString(3, managerCode);
			ps.setString(4, beneficiaryCode);
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Associates the asset record corresponding to <code>assetCode</code> with the
	 * portfolio corresponding to the provided <code>portfolioCode</code>. The third
	 * parameter, <code>value</code> is interpreted as a <i>balance</i>, <i>number
	 * of shares</i> or <i>stake percentage</i> depending on the type of asset the
	 * <code>assetCode</code> is associated with.
	 * 
	 * @param portfolioCode
	 * @param assetCode
	 * @param value
	 */
	public static void addAsset(String portfolioCode, String assetCode, double value)
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;

		String PortfolioID = "(select id from Portfolio where portCode = ?)";
		String AssetID = "(select id from Asset where assetCode = ?)";
		String insertPortfolioAssets = "insert into PortfolioAsset(portfolioID, assetID, totalno) " + "values("
				+ PortfolioID + ", " + AssetID + ", ?)";
		try
		{
			ps = conn.prepareStatement(insertPortfolioAssets);
			ps.setString(1, portfolioCode);
			ps.setString(2, assetCode);
			ps.setDouble(3, value);
			ps.executeUpdate();

			ps.close();
			conn.close();

		} catch (SQLException e)
		{
			throw new RuntimeException("Cannot add the same asset to protfolio twice");
		}
	}

	/**
	 * ============================Additional Functions===============================
	 */

	/**
	 * Additional Method that adds an address
	 * 
	 * @param street
	 * @param city
	 * @param stateCountriesID
	 * @param zipcode
	 *
	 */
	public static void addAddress(String street, String city, int stateCountriesID, String zipcode)
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;

		String query = "insert into Address (street, city, stateCountriesID, zipcode) values (?, ?, ?, ?)";
		try
		{
			ps = conn.prepareStatement(query);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setInt(3, stateCountriesID);
			ps.setString(4, zipcode);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Additional Method that gets the id from the Countries table
	 * 
	 * @param country (the country to be searched for)
	 *
	 */
	public static int getCountryID(String name)
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;
		ResultSet rs;

		int countryID = 0;
		String query = "select id from Countries where name = ?";
		try
		{
			ps = conn.prepareStatement(query);
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next())
			{
				countryID = rs.getInt("id");
			}
			if ( countryID == 0)
			{
				String query2 = "insert into Countries(name) values(?)";
				try
				{
					ps = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, name);
					
					ps.executeUpdate();
					rs = ps.getGeneratedKeys();
					if (rs.next())
					{
						countryID = rs.getInt("GENERATED_KEY");
					}
				}catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return countryID;
	}

	/**
	 * Additional Method that gets the id from StatesCountries table
	 * 
	 * @param stateCountry (the stateCountry to be searched for)
	 *
	 */
	public static int getStateCountryID(String name, String fullname, int countryID)
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;
		ResultSet rs;

		int stateCountriesID = 0;
		String query = "select id from StatesCountries s where name = ?";
		try
		{
			ps = conn.prepareStatement(query);
			ps.setString(1, name);
			rs = ps.executeQuery();
			if (rs.next())
			{
				stateCountriesID = rs.getInt("id");
			}
			
			if ( stateCountriesID == 0)
			{
				String query2 = "insert into StatesCountries(name,fullname,countryID)" + "values(?,?,?)";
				try
				{
					ps = conn.prepareStatement(query2,Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, name);
					ps.setString(2, fullname);
					ps.setInt(3, countryID);
					
					ps.executeUpdate();
					rs = ps.getGeneratedKeys();
					if (rs.next())
					{
						stateCountriesID = rs.getInt("GENERATED_KEY");
					}

				}catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return stateCountriesID;
	}

	/**
	 * Additional Method that gets the id from Address table *
	 * 
	 * @param street
	 * @param city
	 * @param stateCountriesID
	 * @param zipcode
	 *
	 */
	public static int getAddressID(String street, String city, int stateCountriesID, String zipcode)
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;
		ResultSet rs;

		int addressID = 0;
		String query = "select id from Address where street = ? AND city = ? AND stateCountriesID = ? AND zipcode = ?";
		try
		{
			ps = conn.prepareStatement(query);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setInt(3, stateCountriesID);
			ps.setString(4, zipcode);
			rs = ps.executeQuery();
			if (rs.next())
			{
				addressID = rs.getInt("id");
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return addressID;
	}

	/**
	 * Additional Method that helps to add the separate asset tables into the main
	 * Asset table
	 * 
	 * @param assetCode
	 * @param assetType
	 */
	public static void addAssets(String assetCode, String assetType)
	{

		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;

		String insertAsset = "insert into Asset (assetCode, assetType) " + "values (?, ?)";

		try
		{
			ps = conn.prepareStatement(insertAsset);
			ps.setString(1, assetCode);
			ps.setString(2, assetType);
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Additional Method that gets the id from the Asset table
	 * 
	 * @param assetCode
	 */
	public static int getAssetID(String assetCode)
	{
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;
		ResultSet rs;

		int assetID = 0;
		String query = "SELECT id FROM Asset WHERE assetCode = ?";
		try
		{
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			rs = ps.executeQuery();
			if (rs.next())
			{
				assetID = rs.getInt("id");
			}
			ps.close();
			rs.close();
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return assetID;
	}

}
