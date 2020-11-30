/*
 * Class created to for person which takes parameters for person.dat which is passed through filereader
 */
package com.tbf;

import java.util.ArrayList;

public class Person implements Comparable
{
	private String code;
	private String lastName;
	private String firstName;
	private Address add;
	ArrayList<String> email = new ArrayList<String>();

	public Person(String code, String lastName, String firstName, Address add, ArrayList<String> email)
	{
		super();
		this.code = code;
		this.firstName = firstName;
		this.lastName = lastName;
		this.add = add;
		this.email = email;

	}

	public String getCode()
	{
		return code;
	}

	public String getfirstName()
	{
		return firstName;
	}

	public String getlastName()
	{
		return lastName;
	}

	public Address getAdd()
	{
		return add;
	}

	public ArrayList<String> getEmail()
	{
		return email;
	}

	@Override
	public int compareTo(Object arg0)
	{
		Person person1 = (Person) arg0;

		if (this.lastName.compareTo(person1.getlastName()) == 0)
		{
			return firstName.compareTo(person1.getfirstName());
		}

		else
		{
			return lastName.compareTo(person1.getlastName());
		}

	}

}
