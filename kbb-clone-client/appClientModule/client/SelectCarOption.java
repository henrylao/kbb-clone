
package client;

import model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

public class SelectCarOption {

	////////// PROPERTIES //////////
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static ObjectOutputStream out;

	////////// CONSTRUCTORS //////////
	public SelectCarOption() {
		
	}
	public SelectCarOption(ObjectOutputStream o) {
		out = o;
	}

	////////// INSTANCE METHODS //////////

	/* Client Side Handling of Automobile for user-configuration.
	 * Once user is done configuring their selected Automobile,
	 * their configuration is displayed onto the console 
	 * @param protocol is an integer signifiying how configureAuto handles an input obj
	 * 
	 * 1 -> return the Automobile received from the data server
	 * -1 -> default state of a clientThread
	 * */
	public Automobile configureAuto(Object obj, int protocol) {
  		Automobile a = (Automobile) obj;
		if (protocol == -1 )
		{
			boolean next = false;
			for(int i = 0; i < a.getLengthOfOptionSets(); i++) {
				do {
					System.out.println(("Choose from the following:\n"+a.getAnOptionSetStr(i)));
					try {
						int temp = Integer.parseInt(in.readLine()) - 1;
						if(a.setOptionChoice(i, temp)) {
							next = true;
						}else {
							System.out.println("Invalid input!\nTry again!");
						}
					}catch(Exception e) {
						System.out.println("Invalid input!");
					}
					System.out.println();
				}while(next == false);
				next = false;
			}
			// Output onto client-side console
			System.out.println(a.getOptionChoicesStr());
			System.out.println(a.getTotalPrice());
			return null;
		}
		else {
			return a;
		}
	}
	
	public void sendOut(Object obj) {
		try {
			out.writeObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean isAutomobile(Object obj) {
		boolean isAutomobile = false;

		try {
			@SuppressWarnings("unused")
			Automobile a1 = (Automobile) obj;
			isAutomobile = true;
		}
		catch (ClassCastException e) {
			isAutomobile = false;
		}

		return isAutomobile;
	}

	public boolean isKeys(Object obj) {
		String[] keys = null;
		boolean isKeys = false;
		try {
//			@SuppressWarnings("unused")
			if (( (String) obj).contains("*NOTE*"))
				return false;
//			String[] keys = ((String) obj).replace(oldChar, newChar);
			isKeys = true;
		} 
		catch (ClassCastException e) {
			
		}
		return isKeys;
	}

}