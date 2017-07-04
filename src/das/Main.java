package das;

import java.sql.SQLException;
import java.util.Set;

import org.reflections.Reflections;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		Reflections reflections = new Reflections("das");
		Set<Class<? extends User>> allClasses = reflections.getSubTypesOf(User.class);
		System.out.println(allClasses.size());
		for(Object obj : allClasses){
			System.out.println(obj);
		}
		
		ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:h2:mem:UserTest");
		
		TableUtils.createTable(connectionSource, User1.class);
		Dao<User1, Long> dao = DaoManager.createDao(connectionSource, User1.class);
		
		TableUtils.createTable(connectionSource, User2.class);
		Dao<User2, Long> dao2 = DaoManager.createDao(connectionSource, User2.class);
		
		TableUtils.createTable(connectionSource, Resource1.class);
		Dao<Resource1, Long> daoR = DaoManager.createDao(connectionSource, Resource1.class);
		
		User1 user1 = new User1();
		user1.setName("Renata");
		user1.setFunctionalRegistration(140065415);
		user1.setIdade(20);
		user1.setEndereco("Santa Maria");
				
		dao.create(user1);
		
		User2 user2 = new User2();
		user2.setName("Pedro");
		user2.setFunctionalRegistration(110135725);
		user2.setBlabla("Blabla");
		
		dao2.create(user2);
		
		Resource1 projetor1 = new Resource1();
		projetor1.setPatrimonyId((long) 1);
		projetor1.setResolucao("700x400");
		projetor1.setName("Projetor1");
		projetor1.setBorrowed(false);
		projetor1.setDescription("e preto");
		
		daoR.create(projetor1);
		
		Solicitation s = new Solicitation();
		s.MakeSolicitation(user1, projetor1);
		s.MakeSolicitation(user2, projetor1);
		s.returnResource(user1, projetor1);
		s.MakeSolicitation(user2, projetor1);
		
	}
}
