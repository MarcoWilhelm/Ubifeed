package irl.tud.ubifeed.dbaccess;

import java.sql.PreparedStatement;

public interface DalBackendServices {
	
	/**
	   * Provide a preparedStatement.
	   * 
	   * @param query The SQL query of the request as a string.
	   * @return preparedStatement if no problem, else return null.
	   */
	PreparedStatement getPreparedStatement(String query);
	
}
