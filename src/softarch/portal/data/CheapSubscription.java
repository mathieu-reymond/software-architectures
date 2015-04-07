package softarch.portal.data;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Represents a <i>cheap subscription</i> user account.
 * @author Niels Joncheere
 */
public class CheapSubscription extends RegularUser {
	/**
	 * Creates a new <i>cheap subscription</i> account from a
	 * <code>javax.servlet.http.HttpServletRequest</code> object.
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public CheapSubscription(HttpServletRequest request) {
		this(	request.getParameter("Username"),
			request.getParameter("Password"),
			request.getParameter("FirstName"),
			request.getParameter("LastName"),
			request.getParameter("EmailAddress"),
			new Date());
	}

	/**
	 * Creates a new <i>cheap subscription</i> account from a
	 * <code>java.sql.ResultSet</code> object.
	 * @see java.sql.ResultSet
	 */
	public CheapSubscription(ResultSet rs)
		throws SQLException, ParseException {
	
		this.username		= rs.getString("Username");
		this.password		= rs.getString("Password");
		this.firstName		= rs.getString("FirstName");
		this.lastName		= rs.getString("LastName");
		this.emailAddress	= rs.getString("EmailAddress");
		this.lastLogin		= df.parse(rs.getString("LastLogin"));
	}

	/**
	 * Creates a new <i>cheap subscription</i> account.
	 */
	public CheapSubscription(	String	username,
					String	password,
					String	firstName,
					String	lastName,
					String	emailAddress,
					Date	lastLogin) {

		this.username		= username;
		this.password		= password;
		this.firstName		= firstName;
		this.lastName		= lastName;
		this.emailAddress	= emailAddress;
		this.lastLogin		= lastLogin;
	}
	
	public CheapSubscription(JSONObject json) throws ParseException {
		this.username = (String) json.get("Username");
		this.password = (String) json.get("Password");
		this.firstName = (String) json.get("FirstName");
		this.lastName = (String) json.get("LastName");
		this.emailAddress = (String) json.get("EmailAddress");
		this.lastLogin = df.parse((String) json.get("LastLogin"));
	}

	/**
	 * Returns an XML representation of the object.
	 */
	public String asXml() {
		return	"<CheapSubscription>" +
			"<username>" + normalizeXml(username) + "</username>" +
			// password is not returned,
			// as it should only be used internally
			"<firstName>" +
			normalizeXml(firstName) +
			"</firstName>" +
			"<lastName>" + normalizeXml(lastName) + "</lastName>" +
			"<emailAddress>" +
			normalizeXml(emailAddress) +
			"</emailAddress>" +
			"<lastLogin>" + df.format(lastLogin) + "</lastLogin>" +
			"</CheapSubscription>";
	}

	/**
	 * Returns an SQL INSERT string that allows the system to add
	 * the account to a relational database.
	 */
	public String asSql() {
		return	"INSERT INTO CheapSubscription (Username, Password, " +
			"FirstName, LastName, EmailAddress, LastLogin) " +
			"VALUES (\'" + normalizeSql(username) + "\', \'" +
			normalizeSql(password) + "\', \'" +
			normalizeSql(firstName) + "\', \'" +
			normalizeSql(lastName) + "\', \'" +
			normalizeSql(emailAddress) + "\', \'" +
			df.format(lastLogin) + "\');";
	}

	/**
	 * Returns an SQL UPDATE string that allows the system to update
	 * the account in a relational database.
	 */
	public String asSqlUpdate() {
		return  "UPDATE CheapSubscription SET Password = \'" +
			normalizeSql(password) + "\', FirstName = \'" +
	                normalizeSql(firstName) + "\', LastName = \'" +
			normalizeSql(lastName) + "\', EmailAddress = \'" +
	                normalizeSql(emailAddress) + "\', LastLogin = \'" +
			df.format(lastLogin) + "\' " + "WHERE Username = \'" +
			normalizeSql(username) + "\';";
	}
	
	public JSONObject asJSON() {
		JSONObject obj = super.asJSON();
		obj.put("type", "cheapSubscription");
		return obj;
	}
}
