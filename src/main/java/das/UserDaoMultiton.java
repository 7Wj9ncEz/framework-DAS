package das;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class UserDaoMultiton<user_class> {
	private static Map<String, Dao<?, Long>> daoMap = new HashMap<String, Dao<?, Long>>();
	
	@SuppressWarnings("unchecked")
	public static <T> Dao<T, Long> getDao(Class<T> userClass) throws SQLException{
		if(daoMap.get(userClass.getName()) == null){
			Dao<T, Long> dao = 
					DaoManager.createDao(ConnectionSourceSingleton.getConnectionSource(), userClass);
			daoMap.put(userClass.getName(), dao);
		}
		
		return (Dao<T, Long>) daoMap.get(userClass.getName());
	}
}
