package scale;

import exception.AutoException;
import model.Automobile;

/*******
 * 
 * @author hlao1
 * 
 *         Lab 4: Initial implementations synchronization were done at the
 *         collections level however were later reconsidered. Locking at the
 *         object level works, however would like to implement a lock at the
 *         object's field level.
 * 
 *         The EditOptions class allows is allowed access to the protected
 *         static LHM proxyAutoMap. Furthermore, by implementing the Runnable
 *         interface, one could cast an EditOptions instance as a thread.
 *         Furthermore, the EditOptions class has its own implementation of a
 *         thread therefore, one could also run the thread without casting the
 *         instance as a new thread.
 */
//by implementing runnable allows an instance of this class to be casted as a thread and ran
public class EditRunnable implements Runnable {
	private int opnum;
	private String threadName;
	private Automobile auto;
	private Thread t; // internal thread
	private String args[]; // key, optionSet, optionName, newName == max length of arguments

	// constructor
	public EditRunnable(String threadName, int opnum, Automobile auto, String... args) {
		if (args.length > 3) {
			throw new IllegalArgumentException();
		}
		this.args = args;
		this.auto = auto;
		this.opnum = opnum;
		this.threadName = threadName;
		// instantiate a new thread and place itself within the thread b/c implementing
		// runnable
		this.t = new Thread(this, threadName);

	}

	public void run() { // when the instance of this class is started up it runs the operations handler
		try {
			this.opHandler();
		} catch (AutoException ae) {
			// TODO Auto-generated catch block
			System.out.println("Error code: " + ae.getErrorID());
		}
	}

	// by providing an internal thread, we are also able to simply run an
	// instantiation of this class
	public void start() {
		t.start();
	}

	public void opHandler() throws AutoException { // provided with an opnum, the ophandler knows to behave
																// in defined
		// cases
		// auto passed by proxyAuto class from the LHM
		boolean success;
		// multithread handling section
//		print("Inside operation handler for thread: " + this.threadName); // DEBUG
			switch (this.opnum) {

			case 0: // edit option set name case
				success = this.auto.updateOptionSetName(args[0], args[1]);
				if (success) {
					print("Editted by thread " + this.threadName);
				}
				else
					print("Thread " + this.threadName + " couldn't edit");
				break;
			case 1: // edit option name case
				success = this.auto.updateOptionName(args[0], args[1], args[2]);
				if (success)
					print("Editted by thread " + this.threadName);
				else
					print("Thread " + this.threadName + " couldn't edit");
				break;
			case 2:
				success = this.auto.updateBasePrice(Double.parseDouble(args[0]));
				if (success) {
					print("Thread " + this.threadName + " -- BasePrice: $" + Double.toString(this.auto.getBasePrice()));
				}
				else
					print("Thread " + this.threadName + " couldn't edit");
				break;
			case 3:
				success = this.auto.updateOptionPrice(args[0], args[1], Float.parseFloat(args[2]));
				if (success)
					print("Editted by thread " + this.threadName);
				else
					print("Thread " + this.threadName + " couldn't edit");
				break;
			default:
				print("Couldn't update " + this.auto.getAutoID());
			}


	}

	public static void print(String msg) {
		System.out.println(msg);
	}

}
