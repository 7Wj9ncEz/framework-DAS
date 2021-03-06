package framework;

import java.sql.SQLException;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Class responsible to manage the database connection
 */
public class ConnectionSourceSingleton {
	private static ConnectionSource connectionSource;

	/**
	 * Method that return the connection instance
	 * @return ConnectionSource
	 * @throws SQLException
	 */
	public static ConnectionSource getConnectionSource() throws SQLException{
		if(connectionSource == null){
			connectionSource = new JdbcConnectionSource("jdbc:h2:mem:UserTest");
		}
		return connectionSource;
	}
}
