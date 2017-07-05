package framework;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.naming.NoPermissionException;

import static framework.Solicitation.SOLICITATIONS_TABLE;

@DatabaseTable(tableName = SOLICITATIONS_TABLE)
public class Solicitation{

	public static final String SOLICITATIONS_TABLE = "solicitations";
	public static final String SOLICITATION_ID = "solicitation_id";
	public static final String USER = "user";
	public static final String RESOURCE = "resource";

    private static final Logger LOGGER = Logger.getLogger( Solicitation.class.getName() );
	
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
	
	public <T> void MakeSolicitation(UserBase user, Class<T> resourceClass) throws SQLException, NoPermissionException, NoSuchFieldException {
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
            this.getDaoSolicitation().delete(this);

		    if(!hasPermission){
				throw new NoPermissionException("User " + user.getName() + " do not have permission to get " + resourcesList.get(0).getClass().getSimpleName() + " resource type");
			}else if(resourcesList.size() != 0){
				throw new NoSuchElementException("All " + resourcesList.get(0).getClass().getSimpleName() + " resources are borrowed");
			}else {
                throw new NoSuchFieldException("Does not have resource from this type registered");
            }
		}
	}
	
	public void borrow(UserBase user, ResourceBase resource) throws SQLException{
	    LOGGER.log(Level.INFO, "Borrowed to " +  user.getName() + " of " + user.getClass().getSimpleName()  + " type");
		this.setUser(user);
		this.setResource(resource);
	}
	
	public void returnResource() throws SQLException{
		ResourceBase resource =  this.getResource();
		UserBase user = this.getUser();
		if(user == null || resource == null){
		    throw new NullPointerException("This solicitation was empty");
		}
		else if(isBorrowed(resource)){
            LOGGER.log(Level.INFO, "Resource " + resource.getName() + " returned. Thanks, " + user.getName() + "!");
			this.getDaoSolicitation().delete(this);
		}else{
            throw new NoSuchElementException("This item was not borrow");
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
