package softarch.portal.db.json;

import org.json.simple.JSONObject;

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
		JSONObject user = find("user", "username", username);
		String type = (String) user.get("type");
		//add case to make correct user
		return null;
	}

	public boolean userExists(String username) throws DatabaseException {
		JSONObject user = find("user", "username", username);
		if(user != null) return true;
		else return false;
	}

}
