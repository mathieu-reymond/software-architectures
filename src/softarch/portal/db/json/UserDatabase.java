package softarch.portal.db.json;

import java.sql.SQLException;
import java.text.ParseException;

import org.json.simple.JSONObject;

import softarch.portal.data.CheapSubscription;
import softarch.portal.data.ExpensiveSubscription;
import softarch.portal.data.FreeSubscription;
import softarch.portal.data.UserProfile;
import softarch.portal.db.DatabaseException;

public class UserDatabase extends Database{
	
	
	public UserDatabase(String path) {
		super(path);
	}

	public void insert(UserProfile profile) throws DatabaseException {
		insert("user", "Username", profile.getUsername(), profile.asJSON());

	}

	public void update(UserProfile profile) throws DatabaseException {
		update("user", "Username", profile.getUsername(), profile.asJSON());
	}

	public UserProfile findUser(String username) throws DatabaseException {
		JSONObject user = find("user", "Username", username);
		if (user == null) throw new DatabaseException("Invalid username!");
		String type = (String) user.get("type");
		//add case to make correct user
		UserProfile up;
		try {
		if(type.equals("freeSubscription")) {
			up = new FreeSubscription(user);
		}
		else if(type.equals("cheapSubscription")) {
			up = new CheapSubscription(user);
		}
		else if(type.equals("expensiveSubscription")) {
			up = new ExpensiveSubscription(user);
		}
		else throw new DatabaseException("No userType " + type);
		} catch (ParseException e) {
			throw new DatabaseException("problem parsing " + e.getMessage());
		}
		return up;
	}

	public boolean userExists(String username) throws DatabaseException {
		JSONObject user = find("user", "Username", username);
		if(user != null) return true;
		else return false;
	}

}
