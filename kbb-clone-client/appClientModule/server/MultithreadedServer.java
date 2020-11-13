package server;

import adapter.*;
import java.io.*;
import java.net.*;

public class MultithreadedServer implements Runnable, Debuggable {
	
	//////////PROPERTIES //////////

	private BuildCarModelOptions createDB;
	private int port;
	private static ServerSocket server;
	
	////////// CONSTRUCTORS //////////

	public MultithreadedServer(int port) {
		this.port = port;
		createDB = new BuildCarModelOptions();
		// init with models in server
		createDB.buildAutoFromPropertiesFile("src/data/1.prop");
		createDB.buildAutoFromPropertiesFile("src/data/2.prop");
	}
	
	////////// INSTANCE METHODS //////////

	@Override
	public void run() {
		Socket clientSocket = null;
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Could not listen on port " + port + " ... ");
			System.exit(1);
		}
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

	
}
