package client;

import java.net.*;

import adapter.Debuggable;
import exception.AutoException;
import model.Automobile;

import java.io.*;

/* 
 * 
 * 
 */
public class ClientToServerSocketThread implements Runnable, Debuggable {

	//////////PROPERTIES //////////

	private ObjectOutputStream out;
	private ObjectInputStream in;
	private BufferedReader stdIn;
	private Socket sock;
	private String strHost;
	private int iPort;
	private CarModelOptionsIO clientFTP;
	private SelectCarOption selectAutoProtocol;
	private Object data = null;
	private int protocolNum;
	private String key;
	private boolean isDead;
	
	
	
	////////// CONSTRUCTORS //////////

	public ClientToServerSocketThread(String strHost, int iPort) {
		// default protocol is to not do anything for usage as a
		// persistent client between client-user and server
		protocolNum = -1;
		this.strHost = strHost;
		this.iPort = iPort;
		data = null;
	}
	public ClientToServerSocketThread(String strHost, int iPort, int protocol)
	{
		this.key = null;
		protocolNum = protocol;
		this.strHost = strHost;
		this.iPort = iPort;	}
	
	public ClientToServerSocketThread(String strHost, int iPort, int protocol, String key)
	{
		this.key = key;
		protocolNum = protocol;
		this.strHost = strHost;
		this.iPort = iPort;	}
	
	
	////////// INSTANCE METHODS //////////

	/* Method for client-side initialization of a connection to a server */
	public void openConnection() {
		try {
			if (DEBUG)
				System.out.println("Connecting to host ... ");
			this.sock = new Socket(strHost, iPort);

			if (DEBUG)
				System.out.println("Connected to host, creating object streams ... ");
			out = new ObjectOutputStream(sock.getOutputStream());
			in = new ObjectInputStream(sock.getInputStream());
			stdIn = new BufferedReader(new InputStreamReader(System.in));

			clientFTP = new CarModelOptionsIO();
			selectAutoProtocol = new SelectCarOption();
		} catch (IOException e) {
			System.err.println("Error obtaining I/O for connection to host ... ");
			System.exit(1);
		}
	}

	public ObjectOutputStream getOutputStream() {
		return out;
	}
	public  ObjectInputStream getInputStream() {
		return in;
	}
	public Socket getSocket() {
		 return sock;
	}
	@SuppressWarnings("unused")
	public void handleConnection() throws AutoException {
		Object fromServer, toServer = null;

		try {
			if (DEBUG)
				System.out.println("Communicating with host ... ");

			while ((fromServer = in.readObject()) != null) {
				// verify connected
				if (DEBUG)
					System.out.println("Received server response ... ");
				System.out.println(fromServer.toString());
				if (DEBUG)
					System.out.println("Response to server: ");
				
				// handle client-console-input

				// PROTOCOL HANDLING
				
				// fetching an automobile for client configuration
				if (protocolNum == 1 && fromServer != null) {
					if (key == null)
					{
						throw new AutoException(301);
					}
					// fetch command for a specific car 
					toServer = 2; 
					sendOutput(toServer);
					out.flush();
					fromServer = in.readObject();
					
					toServer = key;
					sendOutput(toServer);
					out.flush();
					fromServer = in.readObject();
					Automobile selected = null;

					if (selectAutoProtocol.isAutomobile(fromServer))
					{
						selected = (Automobile) fromServer;
						data = fromServer;
						break;
					}

					data = in.readObject();
					// 
					System.out.println("Model request: " + in.readObject().toString());	// DEBUG
					fromServer = in.readObject();
					if (selectAutoProtocol.isAutomobile(fromServer) && fromServer != null) {
//						selectAutoProtocol.configureAuto(fromServer, protocolNum);
						System.out.println("Successfully received requested Automobile by: "+ key);
					}
					else {
						System.out.println("Failed to retrieve Automobile by key: " + key);
					}
					System.out.println("Press any key to return to main menu");
					// disconnect from server after data has been fetched
					break;
				}
				
				// fetch string of all the available models 
				if (protocolNum == 2 ) {
					if (key != null)
					{
						throw new AutoException(302);
					}
					toServer = 3;
					sendOutput(toServer);
					
					out.flush();

					fromServer = in.readObject();
					data = fromServer;
					System.out.println(fromServer.toString());	// DEBUG

					if ( fromServer != null) {
						System.out.println("Successfully received requested Automobile keys...");
						break;
					}
					else {
						System.out.println("Failed to retrieve Automobile by key: " + key);
					}
					System.out.println("Press any key to return to main menu");
				}
				// end PROTOCOL HANDLING
				toServer = stdIn.readLine();

				// fetching an automobile for client configuration
				if (selectAutoProtocol.isAutomobile(fromServer) && protocolNum == -1) {
					selectAutoProtocol.configureAuto(fromServer, protocolNum);
					System.out.println("Press any key to return to main menu");
				}
				// client to server upload for a .prop file
				if (toServer.toString().contains(".prop")) {
					toServer = clientFTP.loadPropsFile(toServer.toString());
					System.out.println("Press any key to return to main menu");

				}
				// client upload from a deprecated file format
				if (toServer.toString().contains(".txt")) {
					toServer = clientFTP.loadTextFile(toServer.toString());
					System.out.println("Press any key to return to main menu");

				}
				// client receives auto model keys
				if (selectAutoProtocol.isKeys(fromServer)) {
					System.out.println("Press any key to return to main menu");
				}

				// client send to server
				if (DEBUG)
					System.out.println("Sending " + toServer + " to server ... ");
				sendOutput(toServer);
				System.out.println("");
				
				// exit client connection immediately - no need to contact server
				if (toServer.equals("0")) {
					break;
				}
			}

			if (DEBUG)
				System.out.println("Closing client input stream ... ");
			in.close();

		} catch (ClassNotFoundException e) {
			System.err.println("Error in downloaded object, object may be corrupted ... ");
//			System.exit(1);
		} catch (IOException e) {
			System.err.println("Error in I/O stream ... ");
			System.exit(1);
		}
	}
	public boolean isAlive() {
		return (isDead? false: true);
	}
	public String getHost() { return this.strHost; }
	
	public int getPort() { return this.iPort; }
	
	
	public void sendOutput(Object obj) {
		try {
			out.writeObject(obj);
		} catch (IOException e) {
			System.err.println("Error in I/O stream while sending object to host ... ");
			System.exit(1);
		}
	}

	@Override
	public void run() {
		isDead = false;
		openConnection();
		try {
			handleConnection();
		} catch (AutoException e1) {
			// TODO Auto-generated catch block
			e1.fix(e1.getErrorID());
		}
		try {
			if (DEBUG)
				System.out.println("Closing client output stream ... ");
			out.close();
			if (DEBUG)
				System.out.println("Closing client console input stream ... ");
			stdIn.close();
			if (DEBUG)
				System.out.println("Closing client socket ... ");
			sock.close();
			isDead = true;
			
		} catch (IOException e) {
			System.err.println("Error ending client connection ... ");
			System.exit(1);
		}
	}
	/* Method for getting the data retrieved by the client.
	 * Client must be done running for retrieval of data */
	public Object getData() {
		return data;
	}
	/* Method for automating the client thread */
	public void setProtocol(int protocolNum)
	{
		this.protocolNum = protocolNum;
	}
	

}
