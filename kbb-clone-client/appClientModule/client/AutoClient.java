package client;

//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;

public interface AutoClient {
	public void connectToServer(String host, int port);
	public void sendToServer(Object toServer);

}