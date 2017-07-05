package framework;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class SolicitationDaoSingleton {
	private static Dao<Solicitation, Long> solicitationDao;
	
	public static Dao<Solicitation, Long> getDao() throws SQLException{
		if(solicitationDao == null){
			solicitationDao = DaoManager.createDao(ConnectionSourceSingleton.getConnectionSource(), Solicitation.class);
		}
		return solicitationDao;
	}
}
