package exception;

/******************
 * This Exception handles any exceptional cases related to 
 * accessing attributes of an Automobile object
 *
 */


class Fix1to100 extends AutoException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6887759836514877668L;

	public void fix(int errID) {
		String msg = "Error: " + errID;
		switch(errID) {
		case 1:
			msg += " | Auto basePrice missing!";
			System.out.println(msg);
			break;
		case 2:
			msg += " | Auto name missing!";
			System.out.println(msg);
			break;
		case 3:
			msg += " | Auto.optionSet.option data missing!";
			System.out.println(msg);
			break;
		case 4:
			msg += " | Auto.optonSet name missing!";
			System.out.println(msg);
			break;
		case 5:
			msg += " | Auto.optionSet.option name missing!";
			System.out.println(msg);
			break;
		case 6: 
			msg += " | Auto object is null!";
			System.out.println(msg);
			break;
			
			
		case 22:
			msg += " | Mising option selection!";
			System.out.println(msg);
			
			
			break;
		case 50: 
			msg += " | Failed option delete: Unable to locate OptionSet and Option index!";
			System.out.println(msg);
			break;
		case 51:
			msg += " | Failed option delete: Unable to locate OptionSet index!";
			System.out.println(msg);
			break;
		case 52:
			msg += " | Failed option delete: Unable to locate Option index!";
			System.out.println(msg);
			break;
			
		case 60: 
			msg += " | Unable to set option choice";
			System.out.println(msg);
			break;
			
			
		case 79:
			msg += " | Unable to locate user selected OptionSet and Option";
			System.out.println(msg);
			break;
		case 80:
			msg += " | Unable to locate user selected OptionSet";
			System.out.println(msg);
			break;
		case 81:
			msg += " | Unable to locate user selected Option";
			System.out.println(msg);
			break;
		case 82:
			msg += " | No OptionSet name match";
			System.out.println(msg);
			break;
		

		}
		this.log(msg);
	}
	

}
