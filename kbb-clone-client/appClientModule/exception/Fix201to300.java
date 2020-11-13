package exception;
/* This Exception class handles any exceptional cases related 
 * to updating attributes of an Automobile object
 * 
 */
class Fix201to300 extends AutoException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7438466637713503666L;
	public void fix(int errID) {
		String msg = "Error: " + errID;
		switch(errID) {
		case 201:
			msg += " | Could not print auto!";
			print(msg);
			break;
		case 202: 
			msg += " | Could not update option name!";
			print(msg);
			break;
		case 203: 
			msg += " | Could not update option set name!";
			print(msg);
			break;
		case 204:
			msg += " | Attempting to add a null object to LHM!";
			print(msg);
			break;
		case 250:
			msg += " | Attempting to edit a locked Automobile";
		}

		this.log(msg);

		
	}
	private void print(String output) {
		System.out.println(output);
	}
}
