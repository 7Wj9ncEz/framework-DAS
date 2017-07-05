package framework;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class PermissionDaoSingleton {
	private static Dao<Permission, Long> permissionDao;

	public static Dao<Permission, Long> getDao() throws SQLException{
		if(permissionDao == null){
			permissionDao = DaoManager.createDao(ConnectionSourceSingleton.getConnectionSource(), Permission.class);
		}
		return permissionDao;
	}
}
