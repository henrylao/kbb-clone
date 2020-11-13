package adapter;


public interface ConfigureAuto {
	public void printAutoConfiguration(
			String key);
	public void printTotalPrice(
			String key);
	public void setChoice(
			String key, 
			String optionSetChoiceName, 
			String optionChoiceName);
}
