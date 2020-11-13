package server;

import java.util.Properties;
import adapter.*;
import model.Automobile;

public class BuildCarModelOptions extends ProxyAutomobile implements AutoServer{

	////////// PROPERTIES //////////

	private static final int WAITING = 0;
	private static final int REQUEST_BUILD_AUTO = 1;
	private static final int REQUEST_CONFIGURE_AUTO = 2;
	private static final int REQUEST_AUTO_KEYS = 3;
	private int state = WAITING;
	////////// CONSTRUCTORS //////////

	public BuildCarModelOptions() {

	}

	////////// INSTANCE METHODS //////////

	/* Method for dynamically processing a request dependent upon the 
	 * state of the instance. Auto resets itself to 0 == WAITING
	 * after fetching data for a client. 
	 * 
	 * @return type depends on instance state:
	 * STATE	|		TYPE				|
	 * _________|___________________________|
	 * 		0	|	null					|
	 * 		1	|	String success message	|
	 * 		2	|	Automobile				|
	 * 		3	|	String[] of keys		|
	 * 
	 */
	public Object processRequest(Object obj) {
		Object toClient = null;
		// STATE 1 @return String
		if (state == REQUEST_BUILD_AUTO) {
			try {
	    		@SuppressWarnings("unused")
				Properties props = (Properties) obj;
				this.buildAuto(props);
	    	}
			catch (ClassCastException e) {
			}
			toClient = "Automobile object successfully added to database\n"
					+ "Press any key to return to main menu";
			this.state = WAITING;
		}
		// STATE 2 @return Automobile
		else if (state == REQUEST_CONFIGURE_AUTO) {
			Automobile a = new BuildAuto().getAuto( (String) (obj));
			toClient = a;
		}
		// STATE 3	@return String of database keys
		else if (state == REQUEST_AUTO_KEYS)
		{ 
			String[] models = new BuildAuto().getAllModels().split("\n");
			toClient = models;
		}
		
		this.state = WAITING;

		return toClient;
	}

	/* Method for setting the instance request state handling state
	 * and handling various String messages to send back to cliet
	 * */
	public String setRequest(int i) {
		String output = null;
		if (i == 1) {
			this.state = REQUEST_BUILD_AUTO;
			output = "Enter the path to .prop file to upload and create an Automobile";
		}
		else if (i == 2) {
			this.state = REQUEST_CONFIGURE_AUTO;
			output = "Select from the following: \n";
			String[] arrModels = super.getAllModels().split(",");
			for (int j = 0; j < arrModels.length; j++)
			{
				output+= (j + 1) + ". " + arrModels[j] + "\n";
			}
			output += "*NOTE* Enter the name of the Automobile as it appears.\n";
		}
		else if (i == 3) {
			this.state = REQUEST_AUTO_KEYS;
			output = super.getAllModels();
		}
		else {
			output = "Invalid request";
		}
		return output;
	}

	



}