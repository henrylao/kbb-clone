package exception;

import java.io.FileWriter;
import java.io.IOException;

/* This Exception class handles any exceptional cases related 
 * to the application server
 * 
 */
class Fix301to400 extends AutoException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7438466637713503666L;
	public void fix(int errID) {
		try {
			this.fw = new FileWriter("logs/applicationServer_log", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String msg = "Error: " + errID;
		switch(errID) {
		case 301:
			msg += " | ClientServerSocketThread Error: Fetch request to server is missing model name (key)";
			print(msg);
			break;
		case 302: 
			msg += " | ClientServerSocketThread Error: Fetch request for all models should not have a model name (key)";
			print(msg);
			break;
//		case 303: 
//			msg += " | Could not update option set name!";
//			print(msg);
//			break;
//		case 304:
//			msg += " | Attempting to add a null object to LHM!";
//			print(msg);
//			break;
//		case 350:
//			msg += " | Attempting to edit a locked Automobile";
		
		case 370:
			msg += " | Could not start up server";
			print(msg);
			break;
		}

		this.log(msg);

		
	}
	private void print(String output) {
		System.out.println(output);
	}
}
