package softarch.portal.db.json;

import java.util.Date;
import java.util.List;

import softarch.portal.data.RawData;
import softarch.portal.data.RegularData;
import softarch.portal.data.UserProfile;
import softarch.portal.db.DatabaseException;
import softarch.portal.db.DbFacadeInterface;

public class DatabaseFacade implements DbFacadeInterface {
	UserDatabase userDb;
	
	public DatabaseFacade(String path) {
		userDb = new UserDatabase(path);
	}

	public void insert(UserProfile profile) throws DatabaseException {
		userDb.insert(profile);

	}

	public void update(UserProfile profile) throws DatabaseException {
		userDb.update(profile);

	}

	public UserProfile findUser(String username) throws DatabaseException {
		return userDb.findUser(username);
	}

	public boolean userExists(String username) throws DatabaseException {
		return userDb.userExists(username);
	}

	public List findRecords(String informationType, String queryString)
			throws DatabaseException {
		throw new DatabaseException("Not supported by JSON database");
	}

	public List findRecordsFrom(String informationType, Date date)
			throws DatabaseException {
		throw new DatabaseException("Not supported by JSON database");
	}

	public void add(RegularData rd) throws DatabaseException {
		throw new DatabaseException("Not supported by JSON database");

	}

	public int getNumberOfRegularRecords(String informationType)
			throws DatabaseException {
		throw new DatabaseException("Not supported by JSON database");
	}

	public List getRawData() throws DatabaseException {
		throw new DatabaseException("Not supported by JSON database");
	}

	public RawData getRawData(int id) throws DatabaseException {
		throw new DatabaseException("Not supported by JSON database");
	}

	public void addRawData(RegularData rd) throws DatabaseException {
		throw new DatabaseException("Not supported by JSON database");

	}

	public void deleteRawData(RawData rd) throws DatabaseException {
		throw new DatabaseException("Not supported by JSON database");

	}

	public void updateRawData(RawData rd) throws DatabaseException {
		throw new DatabaseException("Not supported by JSON database");

	}

	public int getNumberOfRawRecords() throws DatabaseException {
		throw new DatabaseException("Not supported by JSON database");
	}

}
