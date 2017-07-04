package das;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "solicitations")
public class Solicitation extends Utility{
	
	@DatabaseField(generatedId = true, columnName = "solicitation_id")
	private int solicitationId;
	
	@DatabaseField(foreign = true, columnName = "user", foreignAutoCreate = true)
	public User user;
	
	@DatabaseField(foreign = true, columnName = "resource", foreignAutoCreate = true)
	public Resource resource;
	
	public Dao<Solicitation, Long> daoSolicitation;
	
	public boolean isBorrowed(Resource wanted) throws SQLException{
		System.out.println(this.getDaoSolicitation());
		List<Solicitation> resources = this.getDaoSolicitation().queryForEq("resource", wanted);
		if(resources.isEmpty()){
			return false;
		}
		return true;
	}
	
	public void MakeSolicitation(User user, Dao<?,Long> dao, Dao<Solicitation, Long> daoS, Dao<Permission, Long> daoP) throws SQLException{
		Boolean disponible = false, permission=false;
		this.setDaoSolicitation(daoS);
		List<?> resources = dao.queryForAll();
		for(Object resource : resources){
			Permission p = new Permission();
			Resource resourc = (Resource) resource;
			if(p.havePermission(user, resourc, daoP)){
				permission = true;
			}
			if(!isBorrowed(resourc) && permission){
				disponible = true;
				Borrow(user, resourc);
			}
		}
		if(!disponible){
			if(!permission){
				System.out.println("Oi, " + user.getName()+ "! Infelizmente, você não possui permissão para pegar o recurso do tipo " + ResourceType((Resource)resources.get(0)) + " emprestado");
			}else if(resources.size() != 0){
				System.out.println("Todos os " + ResourceType((Resource)resources.get(0)) + " estao emprestados" );
			}else{
				System.out.println("Não existem recursos deste tipo cadastrados" );
			}
			this.getDaoSolicitation().delete(this);
		}
	}
	
	public void Borrow(User user, Resource resource) throws SQLException{
		System.out.println("Emprestando para " +  user.getName() + " do tipo " + UserType(user));
		this.setUser(user);
		this.setResource(resource);
	}
	
	public void returnResource() throws SQLException{
		Resource resource =  this.getResource();
		User user = this.getUser();
		if(user == null || resource == null){
			System.out.println("Essa solicitacao esta vazia");
		}
		else if(isBorrowed(resource)){
			System.out.println("Recurso " + resource.getName() + " devolvido, obrigado(a)" + user.getName() + "!");
			this.getDaoSolicitation().delete(this);
		}else{
			System.out.println("Esse item não foi emprestado");
		}
	}

	public int getSolicitationId() {
		return solicitationId;
	}

	public void setSolicitationId(int solicitationId) {
		this.solicitationId = solicitationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Dao<Solicitation, Long> getDaoSolicitation() {
		return daoSolicitation;
	}

	public void setDaoSolicitation(Dao<Solicitation, Long> daoSolicitation) {
		this.daoSolicitation = daoSolicitation;
	}
	
}
