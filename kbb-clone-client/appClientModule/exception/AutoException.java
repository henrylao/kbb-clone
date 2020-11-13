package exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


/* Class for handling known exceptions along the way during 
 * development of project. Various exceptions are organized in 
 * accordance to exception id numbers by their grouping
 * amongst various packages.
 * 
 * As of 12/13/19:
 * ------------------------------------------------------
 * ERROR CODE		|		EXCEPTION TYPE			
 * ------------------------------------------------------
 * 		1 - 100		|	Automobile Attribute Access
 * 		101 - 200	|	Automobile FileIO
 * 		201 - 300	|	Automobile Attribute Updates
 * 		301 - 400	|	Server and Client Issues
 * 
 */
public class AutoException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4002301870670620621L;
	private int errID;
	private String errMsg;
	protected FileWriter fw = null;
	
	/*****************
	 * constructors
	 */
	public AutoException() {
		super();
	}
	
	public AutoException(int errID) {
		this.errID = errID;
	}
	
	public AutoException(String errMsg) {
		super(errMsg);
	}
	
	public AutoException(String errMsg, int errID) {
		this.errMsg = errMsg;
		this.errID = errID;
	}
	
	// universal fix method
	public void fix(int errID) {
		switch((int)((errID + 99)/ 100)) {
		// fix errors in automobile data eg missing prices or missing options/optionSets
		case 1:
			Fix1to100 f1 = new Fix1to100();
			f1.fix(errID);
			break;
		case 2:
			Fix101to200 f2 = new Fix101to200();
			f2.fix(errID);
			break;
		case 3:
			Fix201to300 f3 = new Fix201to300();
			f3.fix(errID);
			break;
		case 4:
			Fix301to400 f4 = new Fix301to400();
			f4.fix(errID);
			break;
		default:
			System.out.println("Ran into undocumented error, exiting program");
			System.out.println();
			this.log("Undocumented error, exiting program");
			System.exit(0);
		}
	}
	
	// log exception method
	public void log(String err) {
		try {

			fw = new FileWriter("logs/debug_log", true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(new Date() + " || " + err);
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			System.out.println("Error: " + e.toString());
		}
	}
	
	
	/**********
	 * getters
	 * @return
	 */
	public String getErrorMsg() {
		return errMsg;
	}
	public int getErrorID() {
		return errID;
	}
	/************
	 * setters
	 * @param errMsg
	 */
	public void seterrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public void seterrID(int errID) {
		this.errID = errID;
	}

	
}
