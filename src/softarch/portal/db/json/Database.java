package softarch.portal.db.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import softarch.portal.db.DatabaseException;

public class Database {
	
	private String path;
	
	/**
	 * Simple JSON Database. All tables are separate JSON files, located in the same folder.
	 * @param path The path to the folder containing all tables
	 */
	public Database(String path) {
		this.path = path;
	}
	/**
	 * Get the complete path of a specific JSON table
	 * @param tableName the name of the Table
	 * @return complete path of tableName
	 */
	private String getTablePath(String tableName) {
		return path + File.separator + tableName + ".json";
	}
	
	/**
	 * Read a table, convert it to a JSONArray (each element of the array is one table element)
	 * @param tableName name of table we want to get
	 * @return the table in JSON form
	 * @throws DatabaseException no such table, problem reading table, not a valid json file
	 */
	private JSONArray readTable(String tableName) throws DatabaseException{
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(getTablePath(tableName)));
			JSONArray array = (JSONArray) obj;
			
			return array;
			
		} catch (FileNotFoundException e) {
			throw new DatabaseException("No table named " + tableName);
		} catch (IOException e) {
			throw new DatabaseException(e.getMessage());
		} catch (ParseException e) {
			throw new DatabaseException("Table " + tableName + " is not a valid JSON table");
		}	
	}
	/**
	 * Write a JSONArray to table (replace previous table with this name)
	 * @param tableName name of table we want to write to
	 * @param json JSONArray of table elements 
	 * @throws DatabaseException something went wrong while writing
	 */
	private void writeTable(String tableName, JSONArray json) throws DatabaseException{
		try {
			FileWriter file = new FileWriter(getTablePath(tableName));
			file.write(json.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	/**
	 * Add a new element to table
	 * @param tableName table of new element
	 * @param keyName primary key of table
	 * @param key key of element
	 * @param json element to add
	 * @throws DatabaseException there is already an element with this key
	 */
	public void insert(String tableName, String keyName, String key, JSONObject json) throws DatabaseException {
		JSONArray array = readTable(tableName);
		Iterator<JSONObject> iter = array.iterator();
		while(iter.hasNext()) {
			JSONObject current = iter.next();
			if(current.get(keyName).equals(key)) {
				throw new DatabaseException("Cannot insert : key '" + keyName + "' already exists.");
			}
		}
		array.add(json);
		writeTable(tableName, array);
	}
	
	/**
	 * update an element
	 * @param tableName table of element
	 * @param keyName primary key of table
	 * @param key key of element
	 * @param json element to update
	 * @throws DatabaseException there is no element with this key
	 */
	public void update(String tableName, String keyName, String key, JSONObject json) throws DatabaseException {
		JSONArray array = readTable(tableName);
		ListIterator<JSONObject> iter = array.listIterator();
		boolean found = false;
		while(iter.hasNext()) {
			JSONObject current = iter.next();
			if(current.get(keyName).equals(key)) {
				//replace existing json with given json
				iter.set(json);
				found = true;
				break;
			}
		}
		if (found) {
			writeTable(tableName, array);
		}
		else {
			throw new DatabaseException("There is no element with key '" + keyName + "' to update");
		}
	}

}
