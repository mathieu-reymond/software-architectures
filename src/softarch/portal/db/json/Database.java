package softarch.portal.db.json;

public class Database {
	protected String userDB;
	
	public Database(String path) {
		this.userDB = path;
	}
	
	public JSONFile getConnection() {
		return new JSONFile(userDB);
	}
	
	public void executeJSON(String query) {
		
	}

}
