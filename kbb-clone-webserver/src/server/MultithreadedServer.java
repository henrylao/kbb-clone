package server;

import adapter.*;
import java.io.*;
import java.net.*;
//import exception.AutoException;
public class MultithreadedServer implements Runnable, Debuggable {
	
	//////////PROPERTIES //////////

	private BuildCarModelOptions createDB;
	private int port;
	private static ServerSocket server;
	private boolean wasConnected;
	////////// CONSTRUCTORS //////////

	public MultithreadedServer(int port) {
		wasConnected = false;
		String dataPath = "src/data/";
		this.port = port;
		createDB = new BuildCarModelOptions();
		
		// define loading of data here
		// init with models in server
//		createDB.buildAutoFromPropertiesFile("src/data/1.prop");
		createDB.buildAutoFromPropertiesFile("src/data/2.prop");
//		createDB.buildAuto(dataPath + "FordZTW.prop");
		createDB.buildAutoFromPropertiesFile(dataPath + "FordZTW.prop");
	}
	
	////////// INSTANCE METHODS //////////

	@Override
	public void run() {
		Socket clientSocket = null;
		try {
			server = new ServerSocket(port);
			this.wasConnected = true;
		} catch (IOException e) {
			System.out.println("Could not listen on port " + port + " ... ");
			this.wasConnected = false;
//			System.exit(1);
			return;
		}
//		this.wasConnected = true;
		while (DEBUG) {
			synchronized(this){
	            Thread.currentThread();
	        }
	 			// Accept client
			try {
				clientSocket = server.accept();
			} catch (IOException e) {
				System.out.println("Error establishing client connection ... ");
				System.exit(1);
			}

			// Handle client-server interaction
			if (DEBUG)
				System.out.println(clientSocket.getLocalAddress());
			new Thread(new ServerToClientSocketThread(clientSocket)).start();
		}
	}
	
	public boolean wasConnected() {
		return this.wasConnected;
	}

	
}
