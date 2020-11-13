package adapter;

import model.*;
import scale.EditRunnable;
import server.BuildCarModelOptions;
import server.MultithreadedServer;
import util.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Formatter;
import java.util.Properties;

//import org.apache.tomcat.jni.Time;

import client.ClientToServerSocketThread;
import exception.AutoException;

public abstract class ProxyAutomobile {

//	protected static ArrayList<Thread> clientThreads = new ArrayList<Thread>();
	protected static AutoDatabase<Automobile> proxyAutoDB = new AutoDatabase<Automobile>();
	
	public String getAllModels(ObjectInputStream i) {
		return null;
		
	}
	public Automobile getAnAuto(ObjectInputStream i) {
		return null;
		
	}
	
	////////////////////////////
	// GETTERS
	////////////////////////////
	public String getAllModels() {
		String strOut = "";
		String[] models = proxyAutoDB.getAutoDatabaseKeys();
		for (int i = 0; i < models.length - 1; i++) {
			strOut +=  models[i] + ",";
		}
		strOut += models[models.length - 1];
		return strOut;
	}

	public Automobile getAuto(String key) {
		try {
			return proxyAutoDB.getAuto(key);
		} catch (AutoException ae) {
			fix(ae.getErrorID());
		}
		return null;
	}
	
	////////////////////////////
	// DELETE AUTO 
	////////////////////////////
	public void deleteOptionSet(String key, String optionSetName) {
		try {
			proxyAutoDB.getAuto(key).deleteOptionSet(optionSetName);
		} catch (AutoException ae) {
//			System.out.println("Error code: " + ae.getErrorID());
			fix(ae.getErrorID());

		}
		return;
	}

	public void deleteOption(String key, String optionSetName, String optionName) {
		try {
			proxyAutoDB.getAuto(key).deleteOption(optionSetName, optionName);
		} catch (AutoException ae) {
//			System.out.println("Error code: " + ae.getErrorID());
			fix(ae.getErrorID());

		}
		return;
	}

	public void deleteAuto(String key) {
		proxyAutoDB.deleteAuto(key);
		return;
	}
	
	////////////////////////////
	// CONFIGURE AUTO
	////////////////////////////
	public void printAutoConfiguration(String key) {
		try {
			proxyAutoDB.getAuto(key).printChoices();
		} catch (AutoException ae) {
//			System.out.println("Error code: " + ae.getErrorID());
			fix(ae.getErrorID());

		}
	}

	public void printTotalPrice(String key) {
		String msg = "Total: ";
		try {
			java.text.DecimalFormat two = new java.text.DecimalFormat("$#.00");
			System.out.println(msg + two.format(proxyAutoDB.getAuto(key).getTotalPrice()));
		} catch (AutoException ae) {
//			System.out.println("Error code: " + ae.getErrorID());
			fix(ae.getErrorID());


			System.out.println(msg + "$0.00");
		}
	}

	public void setChoice(String key, String setName, String optionName) {
		try {
			proxyAutoDB.getAuto(key).setOptionChoice(setName, optionName);
		} catch (AutoException ae) {
//			System.out.println("Error code: " + ae.getErrorID());
			fix(ae.getErrorID());

		}
	}
	
	////////////////////////////
	// CREATE AUTO
	////////////////////////////
	public boolean buildAutoFromPropertiesFile(Properties prop) {
		BuildCarModelOptions serverSideBuildAuto = new BuildCarModelOptions();
		return serverSideBuildAuto.buildAutoFromPropertiesFile(prop);
	}

	// deprecated building of auto from .txt
	public void buildAuto(String filename) {
		print(filename);
		FileIO io = new FileIO();
		try {
			proxyAutoDB.addAuto(io.buildAutomobile(filename));

		} catch (AutoException ae) {
//			System.out.println("Error code: " + ae.getErrorID());
			fix(ae.getErrorID());

		}
	}

	// updated version of auto construction via .prop
	public void buildAuto(Properties prop) {
		try {
			Automobile newAuto = null;
			FileIO io = new FileIO();
			newAuto = io.buildAutoFromPropFile(prop);
			proxyAutoDB.addAuto(newAuto);

		} catch (AutoException ae) {
//			System.out.println("Error code: " + ae.getErrorID());
			fix(ae.getErrorID());

		}
	}

	public void buildAutoFromPropertiesFile(String filename) {
		try {
			FileIO io = new FileIO();
			Automobile newAuto = null;
			newAuto = io.buildAutoFromPropFile(filename);
			proxyAutoDB.addAuto(newAuto);
		} catch (AutoException ae) {
//			System.out.println("Error code: " + ae.getErrorID());
			fix(ae.getErrorID());

		}

	}
	
	////////////////////////////
	// READ AUTO
	////////////////////////////

	public void printAllAuto() {
		try {
			String[] keys = proxyAutoDB.getAutoDatabase();
			for (int i = 0; i < keys.length; i++) {
				proxyAutoDB.getAuto(keys[i]).print();
			}
		} catch (AutoException ae) {
			System.out.println("Error code: " + ae.getErrorID());

		}

	}
	
	////////////////////////////
	// AUTO EXCEPTION HANDLING
	////////////////////////////
	public void fix(int errID) {
		System.out.println(errID);
		AutoException ae = new AutoException(errID);
		ae.fix(errID);
		System.out.println(ae.getErrorMsg());
	}

	public void printAuto(String key) {
		try {
			proxyAutoDB.getAuto(key).print();
		} catch (AutoException ae) {
			System.out.println("Error code: " + ae.getErrorID());

		}
	}
	
	////////////////////////////
	// UPDATE AUTO
	// Update functions are not multithread safe
	////////////////////////////
	public void updateOptionSetName(String key, String oldOptionSetName, String newOptionSetName) {
		this.editOptionSetName("Update Option Set Name Thread", 0, key, oldOptionSetName, newOptionSetName);
	}

	public void updateOptionName(String key, String optionSetName, String optionName, String newOptionName) {
		this.editOptionName("Update Option Name Thread", 1, key, optionSetName, optionName, newOptionName);
	}

	public void updateOptionPrice(String key, String optionSetName, String optionName, float newOptionPrice) {
		this.editOptionPrice("Update Option Price", 2, key, optionSetName, optionName, newOptionPrice);
	}

	public void updateBasePrice(String key, double newBasePrice) {
		this.editBasePrice("Update " + key + " Base Price Thread", 3, key, newBasePrice);

	}
	
	////////////////////////////
	// EDIT AUTO 
	// Multithread safe methods
	////////////////////////////
	public void editOptionName(String threadName, int opNum, String key, String optionSetName, String optionName,
			String newName) {
		try {
			EditRunnable edit = new scale.EditRunnable(threadName, opNum, proxyAutoDB.getAuto(key), optionSetName,
					optionName, newName);
			Thread t = new Thread(edit);
			t.start();
			t.join();

		} catch (AutoException ae) {
			System.out.println("Error code: " + ae.getErrorID());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void editOptionSetName(String threadName, int opNum, String key, String oldOptionSetName,
			String newOptionSetName) {
		try {
			EditRunnable edit = new scale.EditRunnable(threadName, opNum, proxyAutoDB.getAuto(key), oldOptionSetName,
					newOptionSetName);
			Thread t = new Thread(edit);
			t.start();
			t.join();
		} catch (AutoException ae) {
			System.out.println("Error code: " + ae.getErrorID());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void editOptionPrice(String threadName, int opNum, String key, String optionSetName, String optionName,
			float newOptionPrice) {
		try {
			EditRunnable edit = new scale.EditRunnable(threadName, opNum, proxyAutoDB.getAuto(key), optionSetName,
					optionName, Float.toString(newOptionPrice));
			Thread t = new Thread(edit);
			t.start();
			t.join();
		} catch (AutoException ae) {
			System.out.println("Error code: " + ae.getErrorID());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void editBasePrice(String threadName, int opNum, String key, double newBasePrice) {

		try {
			synchronized (proxyAutoDB.getAuto(key)) {
				EditRunnable edit = new scale.EditRunnable(threadName, opNum, proxyAutoDB.getAuto(key),
						Double.toString(newBasePrice));
				Thread t = new Thread(edit);
				t.start();
				t.join();
			}
		} catch (AutoException ae) {

			System.out.println("Error code: " + ae.getErrorID());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void print(String output) {
		System.out.println(output);
	}
	
	////////////////////////////
	// Client/Server
	////////////////////////////
	public void connectToServer(String host, int port) {
		new Thread (new ClientToServerSocketThread(host, port)).start();
	}
	public void sendToServer(Object toServer) {
	}
	
	public Object getFromServer()
	{
		return "";

	}
	
	
	public void startServer(int port) {
		Formatter fmtMsg = new Formatter();
		fmtMsg.format("Starting server on port: %d", port);
		System.out.println(fmtMsg);
		new Thread (new MultithreadedServer(port)).start();
	}
	
	
	/*
	 * Broken code, an attempt at probing for open ports to start up
	 * a server thread; however, could not resolve the issue of 
	 * after a successful server connection could not stop
	 * loop
	 */
	private void startServer() {
		int port = 1234;
		Formatter fmtMsg = new Formatter();
		String startMsg = "Starting server on port: %d";
		fmtMsg.format("Starting server on port: %d", port);
		System.out.println(fmtMsg);
		MultithreadedServer ms = new MultithreadedServer(port);
		Thread t = new Thread (ms);
		t.start();
		// loop try to start up to find a port to connect to 
		while (!ms.wasConnected()) {
			try {
				t.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			port += 1;
//			this.startServer(port);
			fmtMsg.format("Starting server on port: %d", port);
			System.out.println(fmtMsg);
			ms = new MultithreadedServer(port);
			t = new Thread(ms);
			t.start();
			if (ms.wasConnected())
				break;
		}
		
	}
	
		
	////////////////////////////
	// Web Section
	////////////////////////////
	public Automobile getAutoModel(ObjectInputStream i)
	{
		Object fromServer = null;
		try {
			fromServer = i.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Automobile a = (Automobile) fromServer;
		return a;
	}

	
}
