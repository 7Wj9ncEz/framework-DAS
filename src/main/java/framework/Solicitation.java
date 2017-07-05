package framework;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static framework.Solicitation.SOLICITATIONS_TABLE;

@DatabaseTable(tableName = SOLICITATIONS_TABLE)
public class Solicitation{

	public static final String SOLICITATIONS_TABLE = "solicitations";
	public static final String SOLICITATION_ID = "solicitation_id";
	public static final String USER = "user";
	public static final String RESOURCE = "resource";
	
	@DatabaseField(generatedId = true, columnName = SOLICITATION_ID)
	private int solicitationId;
	
	@DatabaseField(foreign = true, columnName = USER, foreignAutoCreate = true)
	public UserBase user;
	
	@DatabaseField(foreign = true, columnName = RESOURCE, foreignAutoCreate = true)
	public ResourceBase resource;
	
	public Solicitation(){
		
	}
	
	public boolean isBorrowed(ResourceBase wanted) throws SQLException{
		List<Solicitation> resources = this.getDaoSolicitation().queryForEq(RESOURCE, wanted);
		if(resources.isEmpty()){
			return false;
		}
		return true;
	}
	
	public <T> void MakeSolicitation(UserBase user, Class<T> resourceClass) throws SQLException{
		Boolean isAvailable = false, hasPermission = false;
		List<?> resourcesList = ResourceDaoMultiton.getDao(resourceClass).queryForAll();

		List<?> allPermissionsList = PermissionDaoSingleton.getDao().queryForAll();

		int numberOfPermissions = allPermissionsList.size();

		for(Object resource : resourcesList){
			ResourceBase currentResource = (ResourceBase) resource;

			if(Permission.hasPermission(user, currentResource) || numberOfPermissions == 0){
				hasPermission = true;
			}
			if(!isBorrowed(currentResource) && hasPermission){
				isAvailable = true;
				borrow(user, currentResource);
				break;
			}
		}

		if(!isAvailable){
			if(!hasPermission){
				System.out.println("Oi, " + user.getName()+ "! Infelizmente, você não possui permissão para pegar o recurso do tipo " + resourcesList.get(0).getClass().getSimpleName() + " emprestado");
			}else if(resourcesList.size() != 0){
				System.out.println("Todos os " + resourcesList.get(0).getClass().getSimpleName() + " estao emprestados, " + user.getName()+ "!" );
			}else{
				System.out.println("Não existem recursos deste tipo cadastrados" );
			}
			this.getDaoSolicitation().delete(this);
		}
	}
	
	public void borrow(UserBase user, ResourceBase resource) throws SQLException{
		System.out.println("Emprestando " + resource.getName() + " para " +  user.getName() + " do tipo " + user.getClass().getSimpleName());
		this.setUser(user);
		this.setResource(resource);
	}
	
	public void returnResource() throws SQLException{
		ResourceBase resource =  this.getResource();
		UserBase user = this.getUser();
		if(user == null || resource == null){
			System.out.println("Essa solicitacao não existe");
		}
		else if(isBorrowed(resource)){
			System.out.println("Recurso " + resource.getName() + " devolvido, obrigado(a), " + user.getName() + "!");
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

	public UserBase getUser() {
		return user;
	}

	public void setUser(UserBase user) {
		this.user = user;
	}

	public ResourceBase getResource() {
		return resource;
	}

	public void setResource(ResourceBase resource) {
		this.resource = resource;
	}

	public Dao<Solicitation, Long> getDaoSolicitation() throws SQLException {
		return SolicitationDaoSingleton.getDao();
	}
}
