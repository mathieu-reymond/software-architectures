package softarch.portal.db;

import java.util.Date;
import java.util.List;

public interface RecordFinderInterface {


	/**
	 * Returns a list containing all records of the given information type
	 * that match the given query string.
	 */
	public List findRecords(String informationType, String queryString) throws DatabaseException;

	/**
	 * Returns a list containing all records of the given information type
	 * that were added after the given date.
	 */
	public List findRecordsFrom(String informationType, Date date) throws DatabaseException;

}
