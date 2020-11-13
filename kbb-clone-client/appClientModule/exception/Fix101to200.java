package exception;

/*****
 * This Exception handles any exceptional cases related to
 * FileIO with an Automobile object 
 *
 */
class Fix101to200 extends AutoException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 732491107917620493L;

	public void fix(int errID) {
		String msg = "Fixed error code: " + errID;
		switch(errID) {
		case 101:
			msg += " | Empty file!";
			System.out.println(msg);
			break;
		case 102:
			msg += " | Error reading .txt file!";
			System.out.println(msg);
			break;
		/* */
		case 103:
			msg += " | Error reading .prop file!";
			System.out.println(msg);
			break;
		case 104:
			msg += " | Missing Make field in .prop file";
			System.out.println(msg);
			break;
		case 105:
			msg += " | Missing Model field in .prop file";
			System.out.println(msg);
			break;
		case 106:
			msg += " | Missing BasePrice field in .prop file";
			System.out.println(msg);
			break;
		case 107:
			msg += " | Error parsing Option Price to Float";
			System.out.println(msg);
			break;
		case 108:
			msg += " | Error creating Automobile from properties object";
			System.out.println(msg);
			break;
		/* */
			
			
		case 120:
			msg += " | First line is whitespace!";
			System.out.println(msg);
			break;
		case 121:
			msg += " | Incorrect data file formatting!";
			System.out.println(msg);
			break;
		case 122:
			msg += " | Missing modelName!";
			System.out.println(msg);
			break;
		case 123:
			msg += " | Missing basePrice!";
			System.out.println(msg);
			break;
		case 124:
			msg += " | Missing makeName!";
			System.out.println(msg);
			break;
			
		case 150:
			msg += " | Can't display null object in AutoMap!";
			print(msg);

			break;

		}
		this.log(msg);

		
	}
	
	private void print(String output) {
		System.out.println(output);
	}
}


