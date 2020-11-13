package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import adapter.Debuggable;

public class ServerToClientSocketThread implements Runnable, Debuggable {

	////////// PROPERTIES //////////

	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	private Socket serverToClientSocket = null;
	private BuildCarModelOptions buildCarProtocol = null;

	////////// CONSTRUCTORS //////////

	public ServerToClientSocketThread(Socket clientToServerSocket) {
		this.serverToClientSocket = clientToServerSocket;
	}

	////////// INSTANCE METHODS //////////

	public void openConnection(Socket clientToServerSocket) {
		if (DEBUG)
			System.out.println("Creating server object streams ... ");
		try {
			out = new ObjectOutputStream(this.serverToClientSocket.getOutputStream());
			in = new ObjectInputStream(this.serverToClientSocket.getInputStream());
		} catch (IOException e) {
			System.err.println("Error creating server object streams ... ");
			System.exit(1);
		}
	}

	public void handleConnection() {

		buildCarProtocol = new BuildCarModelOptions();
		String menu = "\nEnter 1 to upload a new Automobile\n" 
				+ "Enter 2 to configure an Automobile\n"
				+ "Enter 3 to view all available models\n"
				+ "Enter 0 to terminate connection\n";

		try {
			do {
				if (DEBUG)
					System.out.println("Sending client interaction choices ... ");
				sendOutput(menu);

				if (DEBUG)
					System.out.println("Reading client request ... ");
				int request = 0;
				boolean is_digit = false;
				do {
					try {
						request = Integer.parseInt(in.readObject().toString());
						is_digit = true;
					} catch (Exception e) {
						sendOutput("Enter again!\nReading client request ...");
						sendOutput(menu);
					}
				} while (is_digit == false);

				if (DEBUG)
					System.out.println(request);
				// client requests to exit
				if (request == 0)
					break;
				if (DEBUG)
					System.out.println("Sending client request follow-up ... ");
//				buildCarProtocol.setRequest(request);
				// handle non-exit cases
				buildCarProtocol.setRequest(request);
				sendOutput(buildCarProtocol.setRequest(request));
				if (request > 1 && request <= 2) {
					// set the request in the protocol class and output the messages to the client
					System.out.println("SERVER-SIDE: fromClient:: " + request);
					handleInput(request);
				}



			} while (in.readObject() != null);

			in.close();
		} catch (IOException e) {
			System.err.println("Error handling client connection ... ");
			closeConnection();
		} catch (ClassNotFoundException e) {
			System.err.println("Error in uploaded object, object may be corrupted ... ");
			closeConnection();
		}
	}

	public void sendOutput(Object obj) {
		try {
			out.writeObject(obj);
		} catch (IOException e) {
			System.err.println("Error returning output to client ... ");
			System.exit(1);
		}
	}

	public void handleInput(int request) {
		Object fromClient = null;
		Object toClient = null;
//		buildCarProtocol.setRequest(request);
		try {
			switch (request) {
			case 1: // Client request to build Automobile
				if (DEBUG)
					System.out.println("Waiting for client to upload file ... ");
				fromClient = in.readObject();
				if (DEBUG) {
					System.out.println(fromClient);
					System.out.println("Adding new Automobile to database ... ");
				}
				toClient = this.buildCarProtocol.processRequest(fromClient);
				sendOutput(toClient);
				break;

			case 2: // Client request to configure Automobile
				if (DEBUG)
					System.out.println("Waiting for client to select Automobile ... ");
				fromClient = in.readObject().toString();
				if (DEBUG) {
					System.out.println("Sending Automobile object to client ... ");
					toClient = buildCarProtocol.processRequest(fromClient);
					sendOutput(toClient);
				}
					
				break;
			case 3: // Client request to get all available models (keys returned in string array)
				if (DEBUG)
					System.out.println("Fetching Automobile models ... ");
				fromClient = (String[]) in.readObject();
//				fromClient = fromClient += "GET_MODELS";
				if (DEBUG)
					System.out.println("Sending Automobile models to client ... ");
				toClient = buildCarProtocol.processRequest(fromClient);
				System.out.println(toClient); // DEBUG
				sendOutput(toClient);
				break;
//			case 4: // Client request to get a specific model (by key) in string format with a string signal
//				if (DEBUG)
//					System.out.println("Fetching Automobile model ... ");
//				fromClient = in.readObject().toString();
//				if (DEBUG)
//					System.out.println("Sending Automobile model to client ... ");
//				toClient = buildCarProtocol.processRequest(fromClient);
//				sendOutput(toClient);

			default: // Invalid requests
				;
			}
		} catch (ClassNotFoundException e) {
			System.err.println("Error in uploaded object, file may be corrupted ... ");
//			System.exit(1);
		} catch (IOException e) {
			System.err.println("Error in retrieving object from client ... ");
//			System.exit(1);
		}
	}

	@Override
	public void run() {
		openConnection(this.serverToClientSocket);
		handleConnection();
		closeConnection();

	}

	public void closeConnection() {
		if (DEBUG)
			System.out.println(
					"Closing server output stream to client " + serverToClientSocket.getInetAddress() + " ... ");
		try {
			out.close();
		} catch (IOException e) {
			System.err.println(
					"Error closing server output stream for client " + serverToClientSocket.getInetAddress() + " ... ");
		}

		// Close client socket
		if (DEBUG)
			System.out.println("Closing socket to client " + serverToClientSocket.getInetAddress() + " ... ");
		try {
			serverToClientSocket.close();
		} catch (IOException e) {
			System.err.println("Error closing client socket " + serverToClientSocket.getInetAddress() + " ... ");
		}
	}
}
