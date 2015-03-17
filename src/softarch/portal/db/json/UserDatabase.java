package softarch.portal.db.json;

import softarch.portal.data.UserProfile;
import softarch.portal.db.DatabaseException;

public class UserDatabase extends Database{
	
	
	public UserDatabase(String path) {
		super(path);
	}

	public void insert(UserProfile profile) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	public void update(UserProfile profile) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	public UserProfile findUser(String username) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean userExists(String username) throws DatabaseException {
		// TODO Auto-generated method stub
		return false;
	}

}
