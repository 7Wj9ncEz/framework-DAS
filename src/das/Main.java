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
		/*UserFinder agoravai = new UserFinder("User");
		Vector<String> children = agoravai.getChildren();
		for(String af : children){
			System.out.println(af);
		}*/
		Reflections reflections = new Reflections("das");
		Set<Class<? extends User>> allClasses = reflections.getSubTypesOf(User.class);
		System.out.println(allClasses.size());
		for(Object obj : allClasses){
			System.out.println(obj);
		}
		
		ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:h2:mem:UserTest");
		
		TableUtils.createTable(connectionSource, User1.class);
		Dao<User1, Long> dao = DaoManager.createDao(connectionSource, User1.class);
		System.out.println(dao.queryForAll());
	}
}
