package adapter;
import exception.*;
public interface ReadAuto {
	public void printAuto(String autoID);
	public void printAllAuto() throws AutoException;
}
