package framework;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/**
 * Class that is a singleton for solicitation Dao
 */
public class SolicitationDaoSingleton {
	private static Dao<Solicitation, Long> solicitationDao;

	/**
	 * Method that return the instance of solicitation Dao
	 * @return Dao<Solicitation, Long>
	 * @throws SQLException
	 */
	public static Dao<Solicitation, Long> getDao() throws SQLException{
		if(solicitationDao == null){
			solicitationDao = DaoManager.createDao(ConnectionSourceSingleton.getConnectionSource(), Solicitation.class);
		}
		return solicitationDao;
	}
}
