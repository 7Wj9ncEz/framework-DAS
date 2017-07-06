package framework;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/**
 * Class that is a singleton for resource Dao
 */
public class ResourceDaoMultiton {
	private static Map<String, Dao<?, Long>> daoMap = new HashMap<String, Dao<?, Long>>();

	/**
	 * Method that return the instance of resource Dao
	 * @return <T> Dao<T, Long>
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public static <T> Dao<T, Long> getDao(Class<T> resourceClass) throws SQLException{
		if(daoMap.get(resourceClass.getName()) == null){
			Dao<T, Long> dao = 
					DaoManager.createDao(ConnectionSourceSingleton.getConnectionSource(), resourceClass);
			daoMap.put(resourceClass.getName(), dao);
		}
		
		return (Dao<T, Long>) daoMap.get(resourceClass.getName());
	}
}
