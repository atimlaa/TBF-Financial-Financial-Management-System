/*
 * Json converter 
 */
package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonWriter
{
	public void writePersonJason(ArrayList<Person> p)
	{
		// Json File Converter

		PrintWriter print = null;

		Gson g = new GsonBuilder().setPrettyPrinting().create();

		File openFilePerson = new File("data/Persons.json");

		// Reads Persons File
		try
		{
			print = new PrintWriter(openFilePerson);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		print.write("{ persons: [");

		for (Person i : p)
		{
			String pjson = g.toJson(i);
			print.write(pjson);
			print.write(",");
			print.write('\n');
		}
		
		print.write("]}");

		print.close();

	}

	public void writeAssetsJason(ArrayList<Asset> a)
	{
		PrintWriter print2 = null;

		Gson g = new GsonBuilder().setPrettyPrinting().create();

		File openFileAssets = new File("data/Assets.json");

		// Reads Persons File
		try
		{
			print2 = new PrintWriter(openFileAssets);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		print2.write("{ assets: [");

		for (Asset i : a)
		{
			String pjson = g.toJson(i);
			print2.write(pjson);
			print2.write(",");
			print2.write('\n');
		}
		
		print2.write("]}");

		print2.close();

	}
}
