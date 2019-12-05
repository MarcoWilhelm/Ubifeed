package irl.tud.ubifeed.dbaccess;

public interface DalServices {

	/**
	 * Start transaction, open connection.
	 */
	void startTransaction();
	/**
	 * Commit transaction, close the connection and put it back in DataSource.
	 */
	void commitTransaction();
	/**
	 * RollBack transaction in case of error, close the connection and put it back in DataSource.
	 */
	void rollbackTransaction();

}
