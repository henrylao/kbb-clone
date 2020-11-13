package drivers;

import adapter.BuildAuto;
import server.AutoServer;

public class ServerDriver {
	public static void main(String[] args) {
		int port = 1234;
		AutoServer server = new BuildAuto();
		server.startServer(port);
//		server.startServer();
	}
}


