package example;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import framework.*;

import javax.naming.NoPermissionException;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		
		System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");

		ConnectionSource connectionSource = ConnectionSourceSingleton
				.getConnectionSource();

		TableUtils.createTable(connectionSource, User1.class);
		TableUtils.createTable(connectionSource, User2.class);
		TableUtils.createTable(connectionSource, Resource1.class);
		TableUtils.createTable(connectionSource, Resource2.class);
		TableUtils.createTable(connectionSource, Solicitation.class);
		TableUtils.createTable(connectionSource, Permission.class);

		Dao<User1, Long> user1Dao = UserDaoMultiton.getDao(User1.class);

		Dao<User2, Long> dao2 = UserDaoMultiton.getDao(User2.class);

		Dao<Resource1, Long> resource1Dao = ResourceDaoMultiton.getDao(Resource1.class);
		Dao<Resource2, Long> resource2Dao = ResourceDaoMultiton.getDao(Resource2.class);

		User1 user1 = new User1("Renata", 140065415, "Santa Maria", 20);
		User1 user2 = new User1("Sabryna", 1239812, "Gama", 20);
		User1 user3 = new User1("Mateus", 1289812, "Santa Maria", 20);
		User1 user4 = new User1("João", 1281671, "Candanga", 20);
		User1 user5 = new User1("Rosângela", 12672561, "Santa Maria", 20);

		
		user1Dao.create(user1);
		user1Dao.create(user2);
		user1Dao.create(user3);
		user1Dao.create(user4);
		user1Dao.create(user5);


		User2 user6 = new User2("Vitor", 140033149, "Barbosa");
		dao2.create(user6);

		Resource1 projetor1 = new Resource1((long)1, "Projetor 1", "700x800", "é preto");
		Resource1 projetor2 = new Resource1((long)1, "Projetor 2", "800x800", "é branco");

		resource1Dao.create(projetor1);
		resource1Dao.create(projetor2);

		Resource2 notebook1 = new Resource2((long) 2, "Notebook1", "Prata", "Dell");
		
		resource2Dao.create(notebook1);
		
		Dao<Solicitation, Long> solicitationDao = SolicitationDaoSingleton.getDao();

		Dao<Permission, Long> permissionDao = PermissionDaoSingleton.getDao();

		Permission p = new Permission(User1.class, Resource1.class);
		permissionDao.create(p);

		Permission p2 = new Permission(User2.class, Resource1.class);
		permissionDao.create(p2);

		try {
			Solicitation s = new Solicitation();
			s.MakeSolicitation(user1, Resource1.class);
			solicitationDao.create(s);

			Solicitation s2 = new Solicitation();
			s2.MakeSolicitation(user2, Resource1.class);
			solicitationDao.create(s2);

			s.returnResource();
			s.returnResource();

			s2.MakeSolicitation(user6, Resource1.class);

			Solicitation s4 = new Solicitation();
			s4.MakeSolicitation(user3, Resource1.class);
			solicitationDao.create(s4);

			Solicitation s5 = new Solicitation();
			s5.MakeSolicitation(user2, Resource1.class);
			solicitationDao.create(s5);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
