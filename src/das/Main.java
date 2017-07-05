package das;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {

		ConnectionSource connectionSource = ConnectionSourceSingleton
				.getConnectionSource();

		TableUtils.createTable(connectionSource, User1.class);
		TableUtils.createTable(connectionSource, User2.class);
		TableUtils.createTable(connectionSource, Resource1.class);
		TableUtils.createTable(connectionSource, Solicitation.class);
		TableUtils.createTable(connectionSource, Permission.class);

		Dao<User1, Long> user1Dao = UserDaoMultiton.getDao(User1.class);

		Dao<User2, Long> dao2 = UserDaoMultiton.getDao(User2.class);

		Dao<Resource1, Long> resource1Dao = ResourceDaoMultiton.getDao(Resource1.class);

		User1 user1 = new User1("Renata", 140065415, "Santa Maria", 20);
		user1Dao.create(user1);

		User2 user2 = new User2("Vitor", 140033149, "Barbosa");
		dao2.create(user2);

		Resource1 projetor1 = new Resource1();
		projetor1.setPatrimonyId((long) 1);
		projetor1.setResolucao("700x400");
		projetor1.setName("Projetor1");
		projetor1.setDescription("e preto");

		resource1Dao.create(projetor1);

		Dao<Solicitation, Long> solicitationDao = SolicitationDaoSingleton.getDao();

		Dao<Permission, Long> permissionDao = PermissionDaoSingleton.getDao();

		Permission p = new Permission();
		p.setUserType("User1");
		p.setResourceType("Resource1");

		permissionDao.create(p);

		Permission p2 = new Permission();
		p2.setUserType("User2");
		p2.setResourceType("Resource1");

		permissionDao.create(p2);

		Solicitation s = new Solicitation();
		s.MakeSolicitation(user1, Resource1.class);
		solicitationDao.create(s);

		Solicitation s2 = new Solicitation();
		s2.MakeSolicitation(user2, Resource1.class);
		solicitationDao.create(s2);

		s.returnResource();

		s2.MakeSolicitation(user2, Resource1.class);
	}
}
