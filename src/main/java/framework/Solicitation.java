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

/**
 * Class that do the allocation of a resource to a user
 */
@DatabaseTable(tableName = SOLICITATIONS_TABLE)
public class Solicitation{

	public static final String SOLICITATIONS_TABLE = "solicitations";
	public static final String SOLICITATION_ID = "solicitation_id";
	public static final String USER = "user";
	public static final String RESOURCE = "resource";

    private static final Logger LOGGER = Logger.getLogger(Solicitation.class.getName());
	
	@DatabaseField(generatedId = true, columnName = SOLICITATION_ID)
	private Long solicitationId;
	
	@DatabaseField(columnName = USER)
	public String userID;
	
	@DatabaseField(columnName = RESOURCE)
	public String resourceID;
	
	private String resourceName;
	private String userName;

	/**
	 * Blank solicitation constructor
	 */
	public Solicitation(){
	}

	/**
	 * Method that verify if a resource type was borrowed
	 * @param wanted - String
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isBorrowed(String wanted) throws SQLException{
		List<Solicitation> resources = SolicitationDaoSingleton.getDao().queryForEq(RESOURCE, wanted);
		if(resources.isEmpty()){
			return false;
		}
		return true;
	}

	/**
	 * Method that do allocation of a resource type to a user
	 * @param user - UserBase
	 * @param resourceClass - Class<T>
	 * @param <T> - Generic type of class that was extend from resource
	 * @throws SQLException
	 * @throws NoPermissionException
	 * @throws NoSuchFieldException
	 */
	public <T> void MakeSolicitation(UserBase user, Class<T> resourceClass) throws SQLException, NoPermissionException, NoSuchFieldException {
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
            this.getDaoSolicitation().delete(this);
			SolicitationDaoSingleton.getDao().deleteById(solicitationId);

		    if(!hasPermission){
				throw new NoPermissionException("User " + user.getName() + " do not have permission to get " + resourcesList.get(0).getClass().getSimpleName() + " resource type");
			}else if(resourcesList.size() != 0){
				throw new NoSuchElementException("All " + resourcesList.get(0).getClass().getSimpleName() + " resources are borrowed, " + user.getName());
			}else {
				throw new NoSuchFieldException("Does not have resource from this type registered");
			}
		}
	}

	/**
	 * Method that do the association for user and resource types
	 * @param user - String
	 * @param resource - String
	 * @throws SQLException
	 */
	public void borrow(String user, String resource) throws SQLException{
		LOGGER.log(Level.INFO, "Borrowed to " +   resourceName + " of " + userName + " type");
		this.userID = user;
		this.resourceID = resource;
	}

	/**
	 * Method that do the return of a resource
	 * @throws SQLException
	 */
	public void returnResource() throws SQLException{
		String resource =  this.getResource();
		String user = this.getUser();
		if(user == null || resource == null){
		    throw new NullPointerException("This solicitation was empty");
		}
		else if(isBorrowed(resource)){
			LOGGER.log(Level.INFO, "Resource " + resourceName + " returned. Thanks, " + user + "!");
			SolicitationDaoSingleton.getDao().deleteById(this.getSolicitationId());
		}else{
            throw new NoSuchElementException("This item was not borrowed");
		}
	}

	/**
	 * Method that return the solicitation id
	 * @return Long
	 */
	public Long getSolicitationId() {
		return solicitationId;
	}

	/**
	 * Method that get the solicitation id
	 * @param solicitationId - Long
	 */
	public void setSolicitationId(Long solicitationId) {
		this.solicitationId = solicitationId;
	}

	/**
	 * Method that return the user type
	 * @return String
	 */
	public String getUser() {
		return userID;
	}

	/**
	 * Method that set the user type
	 * @param user UserBase
	 */
	public void setUser(UserBase user) {
		this.userID = user.getClass().getSimpleName() + user.getFunctionalRegistration();
		this.userName = user.getName();
	}

	/**
	 * Method that return the resource type
	 * @return String
	 */
	public String getResource() {
		return resourceID;
	}

	/**
	 * Method that return the resource type
	 * @param resource - ResourceBase
	 */
	public void setResource(ResourceBase resource) {
		this.resourceID = resource.getClass().getSimpleName() + resource.getPatrimonyId();
		this.resourceName = resource.getName();
	}

	/**
	 * Method that return the singleton for user dao
	 * @return Dao<Solicitation, Long>
	 */
	public Dao<Solicitation, Long> getDaoSolicitation() throws SQLException {
		return SolicitationDaoSingleton.getDao();
	}
}
