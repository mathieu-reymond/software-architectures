package softarch.portal.db.sql;

import softarch.portal.data.RawData;
import softarch.portal.data.RegularData;
import softarch.portal.data.UserProfile;
import softarch.portal.db.DatabaseException;
import softarch.portal.db.DbFacadeInterface;
import softarch.portal.db.webservice.WebService;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Date;

import javax.xml.rpc.ServiceException;

/**
 * This class implements a facade for all of the database layer's functionality.
 * @author Niels Joncheere
 */
//we will create a database facade interface
public class DatabaseFacade implements DbFacadeInterface{
	private UserDatabase	userDb;
	private RegularDatabase	regularDb;
	private RawDatabase	rawDb;

	/**
	 * Creates a new database facade.
	 */
	public DatabaseFacade(String dbUser, String dbPassword, String dbUrl) {
		userDb		= new UserDatabase(	dbUser,
							dbPassword,
							dbUrl);
		regularDb	= new RegularDatabase(	dbUser,
							dbPassword,
							dbUrl);
		rawDb		= new RawDatabase(	dbUser,
							dbPassword,
							dbUrl);
	}

	/**
	 * Inserts a new user profile into the user database.
	 */
	public void insert(UserProfile profile)
		throws DatabaseException {
	
		userDb.insert(profile);
	}

	/**
	 * Updates an existing user profile in the user database.
	 */
	public void update(UserProfile profile)
		throws DatabaseException {

		userDb.update(profile);
	}

	/**
	 * Returns the user with the specified username.
	 */
	public UserProfile findUser(String username)
		throws DatabaseException {

		return userDb.findUser(username);
	}

	/**
	 * Checks whether a user with the specified username exists.
	 */
	public boolean userExists(String username)
		throws DatabaseException {

		return userDb.userExists(username);
	}

	/**
	 * Returns a list containing all records of the given information type
	 * that match the given query string.
	 */
	public List findRecords(String informationType, String queryString)
		throws DatabaseException {
		List result = regularDb.findRecords(informationType, queryString);	
		try {
			WebService service = new WebService();
			result.addAll(service.findRecords(informationType, queryString));
		} catch (ServiceException e) {

		} catch (MalformedURLException e) {

		} catch (DatabaseException e) {
			
		}
		return result;
	}

	/**
	 * Returns a list containing all records of the given information type
	 * that were added after the given date.
	 */
	public List findRecordsFrom(String informationType, Date date)
		throws DatabaseException {
		List result = regularDb.findRecordsFrom(informationType, date);
		try {
			WebService service = new WebService();
			result.addAll(service.findRecordsFrom(informationType, date));
		} catch (ServiceException e) {

		} catch (MalformedURLException e) {

		} catch (DatabaseException e) {
			
		}
		return result;
	}

	/**
	 * Adds a new regular data object to the regular database.
	 */
	public void add(RegularData rd)
		throws DatabaseException {
	
		regularDb.add(rd);
	}

	/**
	 * Returns the number of records of the given information type in the
	 * regular database.
	 */
	public int getNumberOfRegularRecords(String informationType)
		throws DatabaseException {

		return regularDb.getNumberOfRegularRecords(informationType);
	}

	/**
	 * Returns a list of all raw data.
	 */
	public List getRawData()
		throws DatabaseException {

		return rawDb.getRawData();
	}

	/**
	 * Returns a specific raw data object.
	 */
	public RawData getRawData(int id)
		throws DatabaseException {

		return rawDb.getRawData(id);
	}

	public void addRawData(RegularData rd)
		throws DatabaseException {

		rawDb.addRawData(rd);
	}

	/**
	 * Deletes a raw data object.
	 */
	public void deleteRawData(RawData rd)
		throws DatabaseException {

		rawDb.deleteRawData(rd);
	}

	/**
	 * Updates a raw data object.
	 */
	public void updateRawData(RawData rd)
		throws DatabaseException {

		rawDb.updateRawData(rd);
	}

	/**
	 * Returns the number of records in the raw database.
	 */
	public int getNumberOfRawRecords()
		throws DatabaseException {

		return rawDb.getNumberOfRawRecords();
	}
}
