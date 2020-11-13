package adapter;

public interface DeleteAuto {
	public void deleteOptionSet(
			String key,
			String optionSetName);
	public void deleteOption(
			String key,
			String optionSetName,
			String optionName);
	public void deleteAuto(
			String key);
}
