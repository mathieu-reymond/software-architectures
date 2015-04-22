package softarch.portal.db;

import java.util.Date;
import java.util.List;

import softarch.portal.data.RawData;
import softarch.portal.data.RegularData;
import softarch.portal.data.UserProfile;

public interface DbFacadeInterface extends RecordFinderInterface {

	
	/**
	 * Inserts a new user profile into the user database.
	 */
	public void insert(UserProfile profile) throws DatabaseException;

	/**
	 * Updates an existing user profile in the user database.
	 */
	public void update(UserProfile profile) throws DatabaseException;

	/**
	 * Returns the user with the specified username.
	 */
	public UserProfile findUser(String username) throws DatabaseException;

	/**
	 * Checks whether a user with the specified username exists.
	 */
	public boolean userExists(String username) throws DatabaseException;

	/**
	 * Adds a new regular data object to the regular database.
	 */
	public void add(RegularData rd) throws DatabaseException;

	/**
	 * Returns the number of records of the given information type in the
	 * regular database.
	 */
	public int getNumberOfRegularRecords(String informationType) throws DatabaseException;

	/**
	 * Returns a list of all raw data.
	 */
	public List getRawData() throws DatabaseException;

	/**
	 * Returns a specific raw data object.
	 */
	public RawData getRawData(int id) throws DatabaseException;

	public void addRawData(RegularData rd) throws DatabaseException;

	/**
	 * Deletes a raw data object.
	 */
	public void deleteRawData(RawData rd) throws DatabaseException;

	/**
	 * Updates a raw data object.
	 */
	public void updateRawData(RawData rd) throws DatabaseException;

	/**
	 * Returns the number of records in the raw database.
	 */
	public int getNumberOfRawRecords() throws DatabaseException;
}
