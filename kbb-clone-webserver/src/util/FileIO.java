package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

import exception.AutoException;
import model.Automobile;


/*  */ 
public class FileIO {
	/* method for that takes a string input for the filename
	 an instance of the Automobile class 
	 @pre Automobile has yet to be constructed from a string filename
	 @post a Automobile object has been created from a parsed .txt file
	 @return Autmobile object created from .txt file
	 @param filename is the string name of the input file 	 */
	public Automobile buildAutomobile(String filename) throws AutoException{
		try {
			File infile = new File(filename);
			
			// check if file is empty
			if (infile.length() == 0) {
				throw new AutoException(101);
			}
			
			BufferedReader br = new BufferedReader(new FileReader(filename));
			boolean eof = false;
			String line = br.readLine();	// 
			
			// check data file formatting
			if (line == null) {		// first line is whitespace
				br.close();
				throw new AutoException(120);
			} 
			if (line.split(": ").length != 3) {	// incorrect data file formatting
				br.close();
				throw new AutoException(121);	
			}
			if (line.split(": ")[0].isEmpty()) {	// missing modelName
				br.close();
				throw new AutoException(122);	
			}
			if (line.split(": ")[1].isEmpty()) {	// missing basePrice
				br.close();
				throw new AutoException(123);
			
			}
			
			String make = line.split(": ")[0];
			String model = line.split(": ")[1];
			Double basePrice = Double.parseDouble(line.split(": ")[2]);
			model.Automobile automodel = new model.Automobile(make, model, basePrice);
			
			while (!eof) {
				line = br.readLine(); // first time is optionSetName

				if (line == null)
					eof = true;
				else {
					String opsName = line; // save for adding Options to this OptionSet name
					automodel.addOptionSet(opsName);

					line = br.readLine(); // next line after OptionSet name must be a "{"
					float readCounter = Float.parseFloat(br.readLine()); // size of optionSet

					for (int i = 1; i <= readCounter; i++) {
						line = br.readLine();
						String option_info[] = line.substring(1).split(": ");
						automodel.addOption(opsName, option_info[0], Float.parseFloat(option_info[1]));
					}

					line = br.readLine();
				}
			}
			br.close();
			return automodel;
		} catch (IOException ioe) {
			System.out.println("Error: " + ioe.toString());
			throw new AutoException(102);
		} 
	}
	
	/* Method to attempt construction of an Automobile object from a string filename 
	 * of a properties file utilizing the buildAutoFromPropFile that accepts a Properties
	 * object as an input arg
	 * 
	 * @param filename is the name of the properties file
	 */
	public Automobile buildAutoFromPropFile(String filename ) throws AutoException
	{
		Properties propFile = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filename);
			propFile.load(fis);
			return buildAutoFromPropFile(propFile);
			
		} catch (Exception e)
		{
			throw new AutoException(103);
		}
		
	}
	
	public Automobile buildAutoFromPropFile(Properties propfile) throws AutoException 
	{
		Automobile auto = null;
		String make, model, year, baseprice;
		make = propfile.getProperty("Make");
		model = propfile.getProperty("Model");
		baseprice = propfile.getProperty("BasePrice");
		
		// populate core fields 
		if (make != null && model != null && baseprice != null)
		{
			auto = new Automobile();
			auto.setMake(make);
			auto.setBasePrice(Double.parseDouble(baseprice));
			auto.setModel(model);
		}
		// missing make field exception
		else if (make == null)
		{
			throw new AutoException(104);
		}
		// missing model field exception
		else if (model == null)
		{
			throw new AutoException(105);
		}
		// missing baseprice field exception
		else if (baseprice == null)
		{
			throw new AutoException(106);
		}
		
		// populate option sets with appropriate names
		ArrayList<String> optionSetNames = new ArrayList<String>();
		int optionSetCount = 0;
		while ( (propfile.getProperty("OptionSet" + (optionSetCount + 1) ) != null))
		{
			optionSetCount += 1;
			String optionSetName = "";
			optionSetName = propfile.getProperty("OptionSet" + (optionSetCount));
			optionSetNames.add(optionSetName);
			
			
		}
		// populate appropriate options of the option set
		for (int i =0; i < optionSetNames.size(); i++)
		{
			int optionCount = 0;
			String optionSetName, optionName, optionPrice;
			// add option set name into the auto in construction while looping 
			optionSetName = optionSetNames.get(i);
			auto.addOptionSet(optionSetNames.get(i));
			while ((optionName = propfile.getProperty("Option" + ( i + 1 ) + "Name" + (optionCount + 1))) !=  null
					&& (optionPrice = propfile.getProperty("Option" + ( i + 1 ) + "Price" + (optionCount + 1))) != null)
			{
				optionCount++;
				float optionPriceToFloat;
				try
				{
					optionPriceToFloat = Float.parseFloat(optionPrice);
				} 
				catch (Exception e) 
				{
					throw new AutoException(107);
				}
				auto.addOption(optionSetName, optionName, optionPriceToFloat);
				
			}
		} // end populate option loop
		return auto;
	}

	public static void print(String line) {
		System.out.println(line);
	}

	public void serializeAutomobile(String fileName, model.Automobile autoObj) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(autoObj);
			oos.close();
		} catch (FileNotFoundException fnfException) {
			System.out.println("Error: " + fnfException.toString());
			System.exit(2);
			// TODO Auto-generated catch block
			fnfException.printStackTrace();
		} catch (IOException ioException) {
			System.out.println("Error: " + ioException.toString());
			System.exit(3);
		}

	}
	

	public model.Automobile deserializeAutombile(String fileName) {
		model.Automobile autoObj = null;

		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			autoObj = (model.Automobile) ois.readObject();
			ois.close();
		} catch (FileNotFoundException fnfException) {
			System.out.println("Error: " + fnfException.toString());
			System.exit(4);
			// TODO Auto-generated catch block
			fnfException.printStackTrace();
		} catch (IOException ioException) {
			System.out.println("Error: " + ioException.toString());
			System.exit(5);
		} catch (ClassNotFoundException cnfException) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + cnfException.toString());
			System.exit(6);
		}
		return autoObj;
	}

}
