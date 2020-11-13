package adapter;
import exception.*;

public interface UpdateAuto {
	public void updateOptionSetName(
			String autoID,
			String optionSetName,
			String newName) throws AutoException;
	public void updateOptionName(
			String autoID,
			String optionSetName,
			String optionName,
			String newName) throws AutoException;
	public void updateOptionPrice(
			String autoID,
			String optionSetName,
			String optionName,
			float newPrice) throws AutoException;
	public void updateBasePrice(
			String autoID,
			double newBasePrice) throws AutoException;
	
}