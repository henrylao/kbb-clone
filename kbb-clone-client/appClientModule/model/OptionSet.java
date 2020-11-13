package model;

import java.util.ArrayList;
import exception.*;
//import model.Option;

/*************************************
 * Henry Lao CIS35B Lab 3
 * 
 * Description: The OptionSet class functions as a package-private class in
 * order to allow the automobile, the higher level class, to behave as an
 * interface between outside classes and packages. It contains both protected
 * and private access levels in order to limit access from outside classes thus
 * implementing in a sense a black box sort of object.
 *
 */

class OptionSet implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// Instance Variables
	private Option choice;
	private String name;
	private ArrayList<Option> options;

	/****************************** CONSTRUCTORS ******************************/
	protected OptionSet() {
		this("", 0);
	}

	protected OptionSet(String name) {
		this(name, 0);
	}

	protected OptionSet(int size) {
		this("", size);
	}

	// top of the class constructor chain
	protected OptionSet(String name, int size) {
		this.name = name;
		options = new ArrayList<Option>();

		for (int i = 0; i < size; i++) {
			options.add(new Option());
		}
		this.choice = new Option();
	}

	/****************************** GETTERS ***********************************/
	protected Option getOptionChoice() {
		return choice;
	}

	protected String getOptionChoiceName() throws AutoException {
		if (choice != null)
			return choice.getName();
		else
			throw new AutoException(22);
	}

	protected float getOptionChoicePrice() throws AutoException {
		if (choice != null)
			return choice.getPrice();
		else
			throw new AutoException(22);
	}

	protected String getName() {
		return name;
	}

	protected ArrayList<Option> getOptions() {
		return options;
	}

	protected Option getOption(int optionIndex) {
		if (optionIndex < 0 || optionIndex > options.size()) {
			return null;
		} else
			return options.get(optionIndex);
	}

	/****************************** SETTERS ***********************************/
	protected void setOptionChoice(String optionName) {
		int pos = findOption(optionName);
		if (pos != -1) {
			choice = options.get(pos);
		}
	}

	protected void setOptionChoice(int option) {
		choice = options.get(option);
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected void setOptions(ArrayList<Option> options) {
		this.options = (ArrayList<Option>) options; // shallow copy, is a deep copy necessary?
	}

	protected void setOptionName(int optionIndex, String optionName) {
		options.get(optionIndex).setName(optionName);

	}

	protected void setOptionPrice(int optionIndex, float price) {
		options.get(optionIndex).setPrice(price);
	}

	protected void setOption(int optionIndex, String optionName, float price) {
		options.get(optionIndex).setName(optionName);
		options.get(optionIndex).setPrice(price);
	}

	/****************************** DELETE ***********************************/
	protected boolean deleteOption(int optionIndex) {
		if (optionIndex < 0 || optionIndex >= this.options.size()) {
			return false;
		} else {
			this.options.remove(optionIndex);
			return true;
		}
	}

	/****************************** UPDATE ***********************************/
	protected boolean updateOptionPrice(String optionName, float newPrice) {
		for (Option option : options) {
			if (option.getName().equals(optionName)) {
				option.setPrice(newPrice);
				return true;
			}
		}
		return false;
	}

	protected boolean updateOptionName(String optionName, String newOptionName) {
		for (Option option : options) {
			if (option.getName().equals(optionName)) {
				option.setName(newOptionName);
				return true;
			}
		}
		return false;
	}

	/****************************** ADD ***********************************/
	protected boolean addOption(String optionName, float price) {
		return this.options.add(new Option(optionName, price));
	}

	/****************************** FIND ***********************************/
	protected int findOption(String optionName) {
		for (int i = 0; i < this.options.size(); i++) {
			if (this.options.get(i).getName().equals(optionName))
				return i;
		}
		return -1;
	}

	/***************************** STRING OUTPUT *****************************/
//	public String toString() {
//		StringBuilder out = new StringBuilder(name);
//		String newline = "\n", tab = "\t";
//		out.append(": ");
//		for ( int i = 0; i < options.size(); i ++) 
//			out.append(newline).append(tab).append(tab).append(options.get(i).toString());
//		return out.toString();
//	}
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(String.format("%s\n-------------------------------------------------", getOptionSetName()))
				.append(String.format("\n%-35s%11s\n", "Options:", "Cost"));
		// s.append(String.format("<tr><td>%</td><select>",getOptionSetName()));
		for (int i = 0; i < options.size(); i++) {
			s.append(String.format("%2s. %-35s%8.2f\n", i + 1, options.get(i).getName(), options.get(i).getPrice()));
			// s.append(String.format("<option>%</option>", opt.get(i).getOptionName()));
		}
		// s.append("</select></tr>");
		return s.toString();
	}

	protected String getOptionSetName() {
		return this.name;
	}

	public String[] getOptionList() {
		String[] opList = new String[this.options.size()];
		for (int i = 0; i < options.size(); i++) {
			opList[i] = options.get(i).getName();
		}
		return opList;
	}

	public void print() {
		System.out.println(this.toString());
	}

	public int size() {
		return this.options.size();
	}

	public String getOptionNames() {
		String optionNames = "";
		int SIZE = this.options.size();
		for (int i = 0; i < SIZE - 1; i++) {
			optionNames += this.options.get(i).getName() + ",";
		}
		optionNames += this.options.get(SIZE - 1).getName();
		return optionNames;
	}

}
