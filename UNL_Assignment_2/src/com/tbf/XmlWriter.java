/*
 * Xml converter
 */
package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

public class XmlWriter
{

	public void writePersonXML(ArrayList<Person> p)
	{

		PrintWriter xmlprint1 = null;
		XStream xstream = new XStream();

		File xmlPersonout = new File("data/Persons.xml");

		// Reads Persons File
		try
		{
			xmlprint1 = new PrintWriter(xmlPersonout);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		xmlprint1.write("<?xml version=\"1.0\"?>\n");

		for (Person i : p)
		{
			String personOutput = xstream.toXML(i);
			xmlprint1.write(personOutput);
		}
		xmlprint1.close();
	}

	public void writeAssetsXML(ArrayList<Asset> a)
	{
		PrintWriter xmlprint2 = null;
		XStream xstream = new XStream();

		File xmlAssetsout = new File("data/Assets.xml");

		// Reads Persons File
		try
		{
			xmlprint2 = new PrintWriter(xmlAssetsout);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		xmlprint2.write("<?xml version=\"1.0\"?>\n");

		for (Asset i : a)
		{
			String personOutput = xstream.toXML(i);
			xmlprint2.write(personOutput);
		}
		xmlprint2.close();
	}
}
