package irl.tud.ubifeed.dbaccess;

import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.dbcp2.BasicDataSource;

import com.mysql.jdbc.Driver;


public class DalServicesImpl implements DalBackendServices, DalServices {

	private BasicDataSource pool;
	private ThreadLocal<Connection> connections;

	private static final String URL = "localhost:3306";
	private static final String PASSWORD = "WEB&business96";
	private static final String USER = "root";

	/**
	 * Class Constructor.
	 */
	public DalServicesImpl() {

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
	}

	@Override
	public PreparedStatement getPreparedStatement(String query){
		try {
			return connections.get().prepareStatement(query);
		} catch (SQLException sqlExcept) {
			throw new RuntimeException();
		}
	}

	@Override
	public void startTransaction(){
		if (connections.get() != null) {
			throw new RuntimeException();
		}
		try {
			connections.set(pool.getConnection());
			connections.get().setAutoCommit(false);
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
	}

	@Override
	public void commitTransaction() {
		try {
			connections.get().commit();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		closeConnection();
	}

	@Override
	public void rollbackTransaction() {
		try {
			connections.get().rollback();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		closeConnection();
	}

	/**
	 * close the connection and remove it from the ThreadLocal.
	 */
	private void closeConnection() {
		try {
			connections.get().close();
		} catch (SQLException sqlExcept) {
			sqlExcept.printStackTrace();
		}
		connections.remove();
	}
}
