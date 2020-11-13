package server;
import java.util.Properties;

public interface AutoServer {
	public boolean buildAutoFromPropertiesFile(
			Properties prop);
	public void initServer(int port);

}
