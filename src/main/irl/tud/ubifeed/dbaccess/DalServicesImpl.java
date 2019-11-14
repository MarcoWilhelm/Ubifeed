package irl.tud.ubifeed.dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

import com.mysql.jdbc.Driver;


public class DalServicesImpl implements DalBackendServices, DalServices {

	//private BasicDataSource pool;
	private Connection conn;
	
	
	//private ThreadLocal<Connection> connections;

	private static final String URL = "jdbc:mysql://127.0.0.1:3306/ubifeed";
	private static final String PASSWORD = "qhry9rdrg"; //"n08odYkantC";
	private static final String USER = "root";//"project_user";

	/*private static final String URL = "localhost:3306";
	private static final String PASSWORD = "WEB&business96";
	private static final String USER = "root";*/

	/**
	 * Class Constructor.
	 */
	public DalServicesImpl() {
		try {
    	    Class.forName("com.mysql.jdbc.Driver");
    	    System.out.println("Driver loaded!");
    	} catch (ClassNotFoundException e) {
    	    throw new IllegalStateException("Cannot find the driver in the classpath!", e);
    	}
        
       	try {
           	System.out.println("Connecting database...");
			this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       		 System.out.println("Database connected!");
/*
		// init BasicDataSource
		pool = new BasicDataSource();
		try {
			pool.setDriver(new Driver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pool.setUrl(URL);
		pool.setUsername(USER);
		pool.setPassword(PASSWORD);
		
		// init ThreadLocal
		connections = new ThreadLocal<Connection>();
*/
	}

	@Override
	public PreparedStatement getPreparedStatement(String query){
		try {
			return this.conn.prepareStatement(query);
			//return connections.get().prepareStatement(query);
		} catch (SQLException sqlExcept) {
			throw new RuntimeException();
		}
	}

	@Override
	public void startTransaction(){
		/*
		System.out.println("getConnection");
		if (connections.get() != null) {
			throw new RuntimeException("connection already used in this thread");
		}
		try {
			System.out.println(pool.getConnection());
			connections.set(pool.getConnection());
			connections.get().setAutoCommit(false);
			System.out.println("its ok");
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		*/
	}

	@Override
	public void commitTransaction() {
		/*
		try {
			connections.get().commit();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		closeConnection();
		*/
	}

	@Override
	public void rollbackTransaction() {
		/*
		try {
			connections.get().rollback();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		closeConnection();
		*/
	}

	/**
	 * close the connection and remove it from the ThreadLocal.
	 */
	private void closeConnection() {
		/*
		try {
			connections.get().close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		connections.remove();
		*/
	}
}
