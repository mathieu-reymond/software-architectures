package softarch.portal.data;

import java.util.Date;

import org.json.simple.JSONObject;

/**
 * This is an abstract superclass for all user profiles.
 * @author Niels Joncheere
 */
public abstract class UserProfile extends Data {
	protected	String	username;
	protected	String	password;
	protected	String	firstName;
	protected	String	lastName;
	protected	String	emailAddress;
	protected	Date	lastLogin;

	/**
	 * Returns an SQL string that allows the system to add the account
	 * to a relational database.
	 */
	public abstract String asSql();

	/**
	 * When a user has logged in successfully, he will be
	 * redirected to this page.
	 */
	public abstract String getDefaultPage();

	/**
	 * Returns an SQL UPDATE string that allows the system to update
	 * the account in a relational database.
	 */
	public abstract String asSqlUpdate();

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public UserProfile setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
		return this;
	}
	
	/**
	 * Return a JSON representation of this userProfile
	 * @return
	 */
	public JSONObject asJSON() {
		JSONObject obj = new JSONObject();
		obj.put("Username", username);
		obj.put("Password", password);
		obj.put("FirstName", firstName);
		obj.put("LastName", lastName);
		obj.put("EmailAddress", emailAddress);
		obj.put("LastLogin", df.format(lastLogin));
		
		return obj;
	}
	
}
