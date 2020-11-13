package drivers;

import adapter.BuildAuto;
import client.AutoClient;
//import client.ClientToServerSocketThread;
//import model.Automobile;

public class ClientDriver {
	public static void main(String[] args) {
		
		// GET COMMAND to be called by servlet
		int port = 1234;
		String host = "127.0.0.1";
		AutoClient client = new BuildAuto();
		client.connectToServer(host, port);
//		ClientToServerSocketThread clientThread = new ClientToServerSocketThread(host, port, 1, "BMW M3");
//		new Thread(clientThread).start();
//		while(clientThread.isAlive())
//		{
//			try {
//				new Thread().sleep(250);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("Thread is still running");
//		}
////		String models = (String) clientThread.getData();
////		System.out.println(models);
//
//		Automobile a1 = (Automobile) clientThread.getData();
//
//		System.out.println(a1.toString());
	}
}
