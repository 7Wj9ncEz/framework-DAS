package framework;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/**
 * Class that is a singleton for permission Dao
 */
public class PermissionDaoSingleton {
	private static Dao<Permission, Long> permissionDao;

	/**
	 * Method that return the instance of permission Dao
	 * @return Dao<Permission, Long>
	 * @throws SQLException
	 */
	public static Dao<Permission, Long> getDao() throws SQLException{
		if(permissionDao == null){
			permissionDao = DaoManager.createDao(ConnectionSourceSingleton.getConnectionSource(), Permission.class);
		}
		return permissionDao;
	}
}
