package model;
import java.util.*;
import exception.AutoException;

public class AutoDatabase<T extends Automobile> {
	private LinkedHashMap<String, T> autoDB;
	/****************************** CONSTRUCTORS ******************************/
	public AutoDatabase() {
		autoDB = new LinkedHashMap<String, T>(10);
	}

	public AutoDatabase(int size) {
		autoDB = new LinkedHashMap<String,T>(size);
	}
	/****************************** GETTERS ***********************************/
	public Automobile getAuto(String key) throws AutoException{
		if (autoDB.get(key) != null) {
			return autoDB.get(key);
		}
		else throw new AutoException(150);	// throw error when no auto is found in database
	}

	public int size() {
		return autoDB.size();
	}

	public String[] getAutoDatabase() {
		Set<String> strAutoDB = autoDB.keySet();
		return strAutoDB.toArray(new String[autoDB.size()]);
	}

	public boolean containsKey(String autoID) {
		return autoDB.containsKey(autoID);
	}

	/****************************** MUTATORS **********************************/
	public String[] getAutoDatabaseKeys() {
		Set<String> autoList = autoDB.keySet();
		return autoList.toArray(new String[autoDB.size()]);
	}

	public void addAuto(T autoObj) throws AutoException {
		try {
			autoDB.put(autoObj.getAutoID(), autoObj );
		} catch (Exception e) {
			throw new AutoException(204);
		}
	}

	public void deleteAuto(String key) {
		autoDB.remove(key);
	}
	public void updateAuto(String key, T autoObj) {
		autoDB.replace(key, autoObj);
	}
}
