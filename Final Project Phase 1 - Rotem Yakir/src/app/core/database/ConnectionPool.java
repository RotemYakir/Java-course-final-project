package app.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import app.core.exceptions.CouponSystemException;

/**
 * implements a pool of connections to the database
 * 
 * @author RotemYakir
 *
 */
public class ConnectionPool {

	private Set<Connection> connections = new HashSet<>();
	public static final int MAX_CONNECTIONS = 25;
	private boolean isActive;
	private final String url = "jdbc:mysql://localhost:3306/coupon_system?createDatabaseIfNotExist=true";
	private final String user = "root";
	private final String password = "1234";
	private static ConnectionPool instance;

	/**
	 * constructs a connection pool with a limited amount of connections.
	 * 
	 * @throws SQLException
	 */
	private ConnectionPool() throws SQLException {
		for (int i = 0; i < MAX_CONNECTIONS; i++) {
			this.connections.add(DriverManager.getConnection(url, user, password));
		}
		isActive = true;
	}

	/**
	 * a static method to initialize the connection pool (if needed).
	 * 
	 * @return the (single) instance of the ConnectionPool
	 */
	public static ConnectionPool getInstance() {
		if (instance == null) {
			try {
				instance = new ConnectionPool();
			} catch (SQLException e) {
				throw new CouponSystemException("connection pool initialize failed", e);
			}
		}
		return instance;
	}

	/**
	 * provides a connection from the connection pool. in case the connection pool
	 * is not active - throws an exception. in case the connection pool is empty -
	 * waits until a connection is restored.
	 * 
	 * @return
	 */
	public synchronized Connection getConnection() {
		if (!isActive) {
			throw new CouponSystemException("getConnection failed - connection pool is not active");
		}
		while (this.connections.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		Iterator<Connection> it = this.connections.iterator();
		Connection con = it.next();
		it.remove();
		return con;
	}

	/**
	 * restores a connection (that has been used) to the connection pool.
	 * 
	 * @param con connection to restore
	 */
	public synchronized void restoreConnection(Connection con) {
		this.connections.add(con);
		notify();
	}

	/**
	 * closes all the open connections to the database and deactivates the
	 * connection pool.
	 * 
	 * @throws CouponSystemException
	 */
	public synchronized void closeAllConnections() throws CouponSystemException {
		this.isActive = false;
		while (connections.size() < MAX_CONNECTIONS) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		for (Connection connection : connections) {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponSystemException("closeAllConnections failed", e);
			}
		}
	}

}
