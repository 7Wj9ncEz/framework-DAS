package das;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static das.Solicitation.SOLICITATIONS_TABLE;

@DatabaseTable(tableName = SOLICITATIONS_TABLE)
public class Solicitation{

	public static final String SOLICITATIONS_TABLE = "solicitations";
	public static final String SOLICITATION_ID = "solicitation_id";
	public static final String USER = "user";
	public static final String RESOURCE = "resource";
	
	@DatabaseField(generatedId = true, columnName = SOLICITATION_ID)
	private Long solicitationId;
	
	@DatabaseField(columnName = USER)
	public String userID;
	
	@DatabaseField(columnName = RESOURCE)
	public String resourceID;
	
	private String resourceName;
	private String userName;
	
	public Solicitation(){
		
	}
	
	public boolean isBorrowed(String wanted) throws SQLException{
		List<Solicitation> resources = SolicitationDaoSingleton.getDao().queryForEq(RESOURCE, wanted);
		if(resources.isEmpty()){
			return false;
		}
		return true;
	}
	
	public <T> void MakeSolicitation(UserBase user, Class<T> resourceClass) throws SQLException{
		Boolean isAvailable = false, hasPermission = false;
		
		List<?> resourcesList = ResourceDaoMultiton.getDao(resourceClass).queryForAll();

		for(Object resource : resourcesList){
			String currentResource = resource.getClass().getSimpleName() + ((ResourceBase) resource).getPatrimonyId();
			if(Permission.hasPermission(user, (ResourceBase) resource)){
				hasPermission = true;
			}
			if(!isBorrowed(currentResource) && hasPermission){
				isAvailable = true;
				this.resourceName = ((ResourceBase) resource).getName();
				this.userName = user.getName();
				borrow(user.getClass().getSimpleName() + user.getFunctionalRegistration(), currentResource);
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
			SolicitationDaoSingleton.getDao().deleteById(solicitationId);

		}
	}
	
	public void borrow(String user, String resource) throws SQLException{
		System.out.println("Emprestando " + resourceName + " para " +  userName);
		this.userID = user;
		this.resourceID = resource;
	}
	
	public void returnResource() throws SQLException{
		String resource =  this.getResource();
		String user = this.getUser();
		if(user == null || resource == null){
			System.out.println("Essa solicitacao não existe");
		}
		else if(isBorrowed(resource)){
			System.out.println("Recurso " + resourceName + " devolvido, obrigado(a), " + user + "!");
			SolicitationDaoSingleton.getDao().deleteById(this.getSolicitationId());
		}else{
			System.out.println("Esse item não foi emprestado");
		}
	}

	public Long getSolicitationId() {
		return solicitationId;
	}

	public void setSolicitationId(Long solicitationId) {
		this.solicitationId = solicitationId;
	}

	public String getUser() {
		return userID;
	}

	public void setUser(UserBase user) {
		this.userID = user.getClass().getSimpleName() + user.getFunctionalRegistration();
		this.userName = user.getName();
	}

	public String getResource() {
		return resourceID;
	}

	public void setResource(ResourceBase resource) {
		this.resourceID = resource.getClass().getSimpleName() + resource.getPatrimonyId();
		this.resourceName = resource.getName();
	}

	public Dao<Solicitation, Long> getDaoSolicitation() throws SQLException {
		return SolicitationDaoSingleton.getDao();
	}
}
