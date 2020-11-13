package model;

import java.util.ArrayList;
//import model.OptionSet;
import exception.AutoException;

public class Automobile implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private double basePrice;
	private String make;
	private String model;
	private ArrayList<Option> choices;
	private ArrayList<OptionSet> optionSets;
	private boolean locked;

	/****************************** CONSTRUCTORS ******************************/
	public Automobile(String make, String model, double basePrice) {
		this(make, model, basePrice, 0);
		choices = new ArrayList<Option>();

	}
	public Automobile()
	{
		this("", "", 0, 0);
		choices = new ArrayList<Option>();

	}

	// top of class chain constructor
	public Automobile(String make, String model, double basePrice, int size) {
		this.make = make;
		this.model = model;
		this.basePrice = basePrice;
		choices = new ArrayList<Option>();

		if (size > 0) {
			optionSets = new ArrayList<OptionSet>();
			for (int i = 0; i < size; i++)
				optionSets.add(new OptionSet());
		} else
			optionSets = new ArrayList<OptionSet>();
	}

	/****************************** GETTERS ***********************************/
	public String getOptionSetName(int index)
	{
		return (index >= 0 && index < optionSets.size()? optionSets.get(index).getName(): null);
	}
	
	// return the set of the user's choices verified by the OptionSets in Auto
	public ArrayList<Option> getOptionChoices() {
		ArrayList<Option> selected = new ArrayList<Option>();
		for (int i = 0; i < this.optionSets.size(); i++)
			selected.add(this.optionSets.get(i).getOptionChoice());
		return selected;
	}

	/* Get option selected by a user by an optionset name*/
	public String getOptionChoice(String optionSetName) {
		int index = 0;
		index = this.findOptionSet(optionSetName);
		String optionName = this.choices.get(index).getName();
		return ( optionName != null ? optionName : "");
	}

	public float getOptionChoicePrice(String setName) throws AutoException {
		return optionSets.get(findOptionSet(setName)).getOptionChoicePrice();
	}

	public double getTotalPrice() {
		double total = basePrice;
		for (int i = 0; i < choices.size(); i++)
		{
			total += choices.get(i).getPrice();
		}
		return total;
	}

	public String getMake() {
		return make; // company name
	}

	public String getModel() {
		return model; // car model
	}

	public double getBasePrice() {
		return basePrice;
	}

	public String getAutoID() {
		return make + " " + model;
	}

	public OptionSet getOptionSet(int index) {
		if (index >= 0 && index <= optionSets.size())
			return this.optionSets.get(index);
		else
			return null;
	}
	
	// Return the length of the optionSet at the specified index 
	public int getLengthOfOptionSet(int index) {
		return (index > 0 && index < this.optionSets.size() ? this.optionSets.get(index).size() : -1);
	}
	// Return the length of the optionSet at the specified index 
	public int getLengthOfOptionSets() {
		return this.optionSets.size();
	}

	// index optionSets and options to get an option's name
	public String getOptionName(int optionSetIndex, int optionIndex) {
		if (optionSetIndex < 0 || optionSetIndex >= optionSets.size() || optionIndex < 0
				|| optionIndex >= optionSets.get(optionSetIndex).getOptions().size()) {
			return null;
		} else
			return optionSets.get(optionSetIndex).getName();
	}

	// get an option's name using both the optionset and the target option's index
	public String getOptionName(String optionSetName, int optionIndex) {
		return getOptionName(findOptionSet(optionSetName), optionIndex);
	}

	// get an option's price using both an index of an optionSet and the index of an
	// option
	public float getOptionPrice(int optionSetIndex, int optionIndex) {
		if (optionSetIndex < 0 || optionSetIndex >= optionSets.size() || optionSetIndex < 0
				|| optionSetIndex >= optionSets.get(optionSetIndex).getOptions().size())
			return Float.NaN;
		return optionSets.get(optionSetIndex).getOptions().get(optionIndex).getPrice();

	}

	// get an option's price using both the optionSet's name and the target option's
	// name
	public float getOptionPrice(String optionSetName, String optionName) {
		return optionSets.get(findOptionSet(optionSetName)).getOptions().get(findOption(optionSetName, optionName))
				.getPrice();
	}

	// get an option's price using the optionSet's name and the index of the target
	// option
	public float getOptionPrice(String optionSetName, int optionIndex) {
		return getOptionPrice(findOptionSet(optionSetName), optionIndex);
	}

	/****************************** SEARCH ******************************/
	public int findOptionSet(String optionSetName) {
		for (int i = 0; i < optionSets.size(); i++) {
			if (optionSets.get(i).getName().equals(optionSetName))
				return i;
		}
		return -1;
	}

	public String findOptionSetName(String optionName) {
		for (int i = 0; i < optionSets.size(); i++) {
			if (optionSets.get(i).findOption(optionName) > 0)
				return optionSets.get(i).getName();
		}
		return "";
	}

	public int findOption(String optionSetName, String optionName) {
		int optionSetIndex = this.findOptionSet(optionSetName);
		if (optionSetIndex >= 0 && optionSetIndex < this.optionSets.size()) {
			for (int i = 0; i < optionSets.get(optionSetIndex).getOptions().size(); i++) {
				if (optionSets.get(optionSetIndex).getOption(i).getName().equals(optionName))
					return i;
			}
		}
		return -1;
	}

	/****************************** SETTERS ***********************************/

	public synchronized void setChoices(ArrayList<Option> choices) {
		this.choices = choices;
	}

	public synchronized void setMake(String make) {
		this.make = make;
	}

	public synchronized void setModel(String model) {
		this.model = model;
	}

	public synchronized void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	// replace a optionSet with an new optionSet
	public synchronized void setOptionSets(ArrayList<OptionSet> optionSets) {
		this.optionSets = optionSets;
	}

	public synchronized void setOptionSet(OptionSet optionSet, int optionSetIndex) {
		if (optionSetIndex >= 0 && optionSetIndex < this.optionSets.size()) {
			this.optionSets.set(optionSetIndex, optionSet);
		} else
			return;
	}

	public synchronized void setOptionSetName(int index, String name) {
		this.optionSets.get(index).setName(name);
	}

	public synchronized void setOptionSetName(String oldName, String newName) {
		this.optionSets.get(this.findOptionSet(oldName)).setName(newName);
	}

	// set option values
	public synchronized void setOptionName(int optionSetIndex, int optionIndex, String name) {
		this.optionSets.get(optionSetIndex).setOptionName(optionIndex, name);
	}

	public synchronized void setOptionName(String optionSetName, String oldOptionName, String newOptionName) {
		this.optionSets.get(this.findOptionSet(optionSetName))
				.setOptionName(this.findOption(optionSetName, oldOptionName), newOptionName);
	}

	public synchronized void setOptionPrice(int optionSetIndex, int optionIndex, float price) {
		this.optionSets.get(optionSetIndex).setOptionPrice(optionIndex, price);
	}

	public synchronized void setOption(int optionSetIndex, int optionIndex, String name, float price) {
		this.optionSets.get(optionSetIndex).setOption(optionIndex, name, price);
	}

	/****************************** ADD ***************************************/
	// add optionSet with variable size
	public synchronized void addOptionSet(String optionSetName, int size) {
		if (size < 0)
			return;
		else
			this.optionSets.add(new OptionSet(optionSetName, size));
	}

	// add 1 optionSet with name
	public synchronized void addOptionSet(String optionSetName) {
		this.addOptionSet(optionSetName, 0);
	}

	// add option by optionSetName
	public synchronized void addOption(String optionSetName, String optionName, float price) {
		int optionSetIndex = findOptionSet(optionSetName);
		if (optionSetIndex >= 0 && optionSetIndex < optionSets.size()) {
			optionSets.get(optionSetIndex).addOption(optionName, price);
		} else
			return;
		return;
	}

	// add option by optionSetIndex
	public synchronized void addOption(int optionSetIndex, String optionName, float price) {
		if (optionSetIndex >= 0 && optionSetIndex < optionSets.size()) {
			optionSets.get(optionSetIndex).addOption(optionName, price);
		} else
			return;
		return;
	}

	/***************************** DELETE *************************************/
	// OptionSet Deletion
	// delete by index
	public synchronized void deleteOptionSet(int optionSetIndex) {
		if (optionSetIndex >= 0 && optionSetIndex < optionSets.size()) {
			this.optionSets.remove(optionSetIndex);
		} else
			return;
		return;
	}

	// delete by name
	public synchronized void deleteOptionSet(String optionSetName) {
		int optionSetIndex = this.findOptionSet(optionSetName);
		if (optionSetIndex >= 0 && optionSetIndex < optionSets.size())
			this.deleteOptionSet(optionSetIndex);
		else
			return;
		return;
	}

	// Option Deletion
	// delete though indices
	public synchronized void deleteOption(int optionSetIndex, int optionIndex) throws AutoException {

		if (optionSetIndex >= 0 && optionSetIndex < optionSets.size()) { // optionSetIndex index must exist
			if (optionIndex >= 0 && optionIndex < this.optionSets.get(optionSetIndex).getOptions().size()) {
				this.optionSets.get(optionSetIndex).deleteOption(optionIndex);
			} else
				return;
		} else
			return;
		return;
	}

	// delete by name and index
	public synchronized void deleteOption(String optionSetName, int optionIndex) throws AutoException {
		if (this.findOptionSet(optionSetName) >= 0 && this.findOptionSet(optionSetName) < optionSets.size()) {
			int optionSetIndex = this.findOptionSet(optionSetName);
			if (optionIndex >= 0 && optionIndex < this.optionSets.get(optionSetIndex).getOptions().size()) {
				this.deleteOption(optionSetIndex, optionIndex);
			} else
				return;
		} else
			return;
		return;
	}

	// delete by names
	public synchronized void deleteOption(String optionSetName, String optionName) throws AutoException {
		int optionSetIndex, optionIndex;
		optionSetIndex = this.findOptionSet(optionSetName);
		optionIndex = this.findOption(optionSetName, optionName);
		if (optionSetIndex < 0 && optionIndex < 0)
			throw new AutoException(50);
		if (optionSetIndex < 0)
			throw new AutoException(51);
		if (optionIndex < 0)
			throw new AutoException(52);
		// System.out.println(optionIndex + " " + optionSetIndex); // DEBUG
		this.deleteOption(optionSetIndex, optionIndex);
		this.deleteOption(findOptionSet(optionSetName),
				optionSets.get(findOptionSet(optionSetName)).findOption(optionName));
	}

	// delete multiple options
	public synchronized void deleteOptions(String optionSetName, int start, int end) {

	}

	// delete from a starting index to the end of the ArrayList of Options
	public synchronized void deleteOptions(String optionSetName, int start) {

	}

	// delete multiple optionSets
	public synchronized void deleteOptionSets(int start, int end) {

	}

	// delete from a starting index to the end of the ArrayList of OptionSets
	public synchronized void deleteOptionSets(int start) {

	}

	/***************************** UPDATE ************************************/
	public boolean updateBasePrice(double newBasePrice) {
		this.setBasePrice(newBasePrice);
		return true;
	}

	// update optionSet name by index
	public synchronized boolean updateOptionSetName(int optionSetIndex, String optionSetName) {
		if (optionSetIndex >= 0 && optionSetIndex < this.optionSets.size()) {
			this.optionSets.get(optionSetIndex).setName(optionSetName);
			return true;
		}
		return false;
	}

	// update an OptionSet's name using find by name method
	public synchronized boolean updateOptionSetName(String oldOptionSetName, String newOptionSetName) {
		return this.updateOptionSetName(this.findOptionSet(oldOptionSetName), newOptionSetName);
	}

	// update entire array of optionSet
	public synchronized boolean updateOptionSets(ArrayList<OptionSet> newOptionSet) {
		this.setOptionSets(newOptionSet);
		return false;
	}

	// update a single optionSet
	public synchronized boolean updateOptionSet(OptionSet newOptionSet, String oldOptionSet) {
		int optionSetIndex = this.findOptionSet(oldOptionSet);
		if (optionSetIndex >= 0 && optionSetIndex < this.optionSets.size()) {
			this.setOptionSet(newOptionSet, optionSetIndex);
			return true;
		}
		return false;
	}

	public synchronized boolean updateOptionName(String optionSetName, String optionName, String newName) {
		if (this.optionSets != null) {
//			System.out.println(optionSetIndex + " " + optionIndex);	// DEBUG
			this.optionSets.get(this.findOptionSet(optionSetName))
					.setOptionName(this.findOption(optionSetName, optionName), newName);
			return true;
		}
		return false;
	}

	public synchronized boolean updateOptionPrice(String optionSetName, String optionName, float newPrice) {
		if (this.optionSets != null) {
			this.optionSets.get(this.findOptionSet(optionSetName))
					.setOptionPrice(this.findOption(optionSetName, optionName), newPrice);
			return true;
		}
		return false;
	}

	/***************************** THREAD SAFETY *****************************/

	public synchronized void lock() {
		this.locked = true;
	}

	public synchronized void unlock() {
		this.locked = false;
	}

	public boolean isLocked() {
		return this.locked;
	}

	/***************************** STRING OUTPUT *****************************/
	public String toString() {
		String newline = "\n", tab = "\t";
		String div = "----------------------------------------------------";
		StringBuilder out = new StringBuilder();
		out.append(div + newline);
		out.append("Manufacturer: " + this.make + "\nModel: " + this.model);
		java.text.DecimalFormat two = new java.text.DecimalFormat("$#.00");
		out.append(newline).append("Base Price: ").append(two.format(this.basePrice)).append(newline);
//		.append(newline);
//				.append("OptionSets:");
		for (int i = 0; i < this.optionSets.size(); i++)
			out.append(div + "\n" + this.optionSets.get(i).toString());
		out.append(div).append(newline);

		return out.toString();
	}

	@SuppressWarnings("unused")
	public void print() throws AutoException {
		if (this == null) {
			throw new AutoException(150);
		} else
			System.out.println(this.toString());
	}

	public void printChoices() {
//		for (int i =0; i < this.optionSets.size(); i++) {
//			String optionName = choices.get(i).getName();
//			int optionSetIndex = this.optionSets.get(i).findOption(optionName);
//			System.out.print(this.optionSets.get(optionSetIndex).getName() + "\n\t" + optionName);
//		}
		for (int i = 0; i < this.choices.size(); i++) {
			System.out.println("\t" + choices.get(i).getName());
		}
	}
	public boolean setOptionChoice(String setName, String optName) {
		this.initChoices();
		int pos = findOptionSetByName(setName);
		if(pos != -1){
			optionSets.get(pos).setOptionChoice(optName);
			choices.set(pos, optionSets.get(pos).getOptionChoice());
			return true;
		}else{
			return false;
		}
	}

	public boolean setOptionChoice(int i, String optName) {
		this.initChoices();

		int pos = findOptionByName(i, optName);
		if(pos!=-1) {
			optionSets.get(i).setOptionChoice(pos);
			choices.set(i, optionSets.get(i).getOptionChoice());
			return true;
		}
		return false;
	}
	
	private void initChoices() {
		if (this.choices.size() == 0)
		{
			for (int j = 0; j < this.optionSets.size(); j++)
			{
				choices.add(new Option());
			}
		}
	}
	public boolean setOptionChoice(int i, int index) {
		this.initChoices();
		if(isValidOption(i, index)) {
			optionSets.get(i).setOptionChoice(index);
			choices.set(i, optionSets.get(i).getOptionChoice());
			return true;
		}
		return false;
	}
	
	public boolean isValidOption(int optionSetIndex, int optionIndex) {
		return optionIndex>=0 && optionIndex <optionSets.get(optionSetIndex).size();
	}
	
	public int findOptionSetByName(String n){
		int pos = -1;
		for(int i=0; i<optionSets.size(); i++){
			if(optionSets.get(i).getOptionSetName().equals(n))
				pos = i;
		}
		return pos;
	}
	
	public int findOptionByName(int i, String n){
		if(isValidOption(i))
			return optionSets.get(i).findOption(n);
		return -1;
	}
	
	public boolean isValidOption(int i){
		return i >= 0 && i < optionSets.size();
	}
	public int findOptionByName(String setName, String opName){
		int pos = findOptionSetByName( setName );
		if(pos != -1)
			return optionSets.get(pos).findOption(opName);
		return -1;
	}

	public String getAnOptionSetStr(int i)
	{
		String div = "-------------------------------------------------";
		StringBuffer s = new StringBuffer();
		s.append(div + "\n" + this.optionSets.get(i).toString()).append(div);
		return s.toString();

	}
	public String getOptionChoicesStr() {
		String div = "-------------------------------------------------";
		StringBuffer s = new StringBuffer("Choices").append("\n-------------------------------------------------");
		for( int i = 0; i< this.choices.size(); i++ ) {
			try {
				s.append(String.format( "\nOptionset:%36s\n" ,this.optionSets.get(i).getOptionChoiceName())).append(choices.get(i)).append("\n-------------------------------------------------");
			} catch (AutoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return s.toString();
	}
	public String getOptionChoiceName(String optionSetName) {
		int index = this.findOptionSetByName(optionSetName);
		if (index == -1)
		{
			return "";
		}
		return this.choices.get(index).getName();
	}
	
	public String getOptionChoiceName(int index) {
		if (index < choices.size() && index >= 0)
		{
			return choices.get(index).getName();
		}
		else return "";
		
	}
	
	public String getAllOptionNamesOfAnOptionSet(int index) {
		if (index < optionSets.size() && index >= 0)
		{
			return this.optionSets.get(index).getOptionNames();
		}
		else return "";
		
	}
	
	public String getOptionSetNames() {
		String optionSetNames = "";
		for (int i = 0; i < optionSets.size() - 1; i++)
			optionSetNames += this.optionSets.get(i).getName() + ",";
		optionSetNames += this.optionSets.get(optionSets.size() - 1).getName();
		return optionSetNames;
	}
}
