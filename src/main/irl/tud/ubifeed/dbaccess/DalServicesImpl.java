package irl.tud.ubifeed.dbaccess;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DalServicesImpl implements DalBackendServices, DalServices {
	
	private Connection conn;

	private static final String URL = "";
	private static final String PASSWORD = "";
	private static final String USER = "";

	/**
	 * Class Constructor.
	 */
	public DalServicesImpl() {
	 
		try {
			Class.forName( "com.mysql.jdbc.Driver" ) ;


			// Get a connection to the database
			this.conn = DriverManager.getConnection(URL, PASSWORD, USER) ;

		}catch( SQLException se ){
			System.out.println( "SQL Exception:" ) ;

			// Loop through the SQL Exceptions
			while( se != null ){
				System.out.println( "State  : " + se.getSQLState()  ) ;
				System.out.println( "Message: " + se.getMessage()   ) ;
				System.out.println( "Error  : " + se.getErrorCode() ) ;

				se = se.getNextException() ;
			}
		}
		catch( Exception e ){
			System.out.println( e ) ;
		}
	}
}
