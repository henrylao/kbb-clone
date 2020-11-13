package scale;

/*******
 * 
 * @author hlao1
 * This new interface is another declaration of
 * methods that allow for polymorphism as well as 
 * implementing self containment within the proxyauto
 * class. The proxyauto class simply has to provide
 * implementation of this interface and will be able 
 * to create and run a thread for editing an option name
 * or option set name. 
 */
public interface EditAuto  {
	public void editOptionName(
			String threadName, 
			int opNum,
			String key,
			String optionSetName,
			String optionName,
			String newName);
	public void editOptionSetName(
			String threadName,
			int opNum,
			String key,
			String oldOptionSetName,
			String newOptionSetName
			);
	public void editOptionPrice(
			String threadName,
			int opNum,
			String autoID,
			String optionSetName,
			String optionName,
			float newPrice);
	public void editBasePrice(
			String threadName,
			int opNum,
			String autoID,
			double newBasePrice);
	

}
