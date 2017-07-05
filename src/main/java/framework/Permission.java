package framework;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static framework.Permission.PERMISSIONS_TABLE;

/**
 * Class that represents permissions between user type and resource type
 */
@DatabaseTable(tableName = PERMISSIONS_TABLE)
public class Permission{

	public static final String PERMISSIONS_TABLE = "permissions";
	public static final String PERMISSION_ID = "permission_id";
	public static final String USER_TYPE = "user_type";
	public static final String RESOURCE_TYPE = "resource_type";

	@DatabaseField(generatedId = true, columnName = PERMISSION_ID)
	private int permissionId;
	
	@DatabaseField(columnName = USER_TYPE, canBeNull = false)
	public String userType;
	
	@DatabaseField(columnName = RESOURCE_TYPE, canBeNull = false)
	public String resourceType;

	/**
	 * Blank permission constructor
	 */
	public Permission(){
	}

	/**
	 * Constructor that receive User and Resource classes
	 * @param userClass - Class<? extends UserBase>
	 * @param resourceClass - Class<? extends ResourceBase>
	 */
	public Permission(Class<? extends UserBase> userClass, Class<? extends ResourceBase> resourceClass){
		this.setResourceType(resourceClass);
		this.setUserType(userClass);
	}

	/**
	 * Method that verify if a User have permission to allocate a Resource
	 * @param user - Class UserBase
	 * @param resource - Class ResourceBase
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean hasPermission(UserBase user, ResourceBase resource) throws SQLException{
		Map<String, Object> p = new HashMap<String, Object>();
		p.put(USER_TYPE, user.getClass().getName());
		p.put(RESOURCE_TYPE, resource.getClass().getName());

		List<Permission> resources = PermissionDaoSingleton.getDao().queryForFieldValues(p);
		if(resources.isEmpty()){
			return false;
		}
		return true;
	}

	/**
	 * Method that return the user type name
	 * @return String
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * Method that set the user type name
	 * @param userClass - Class<? extends UserBase>
	 */
	public void setUserType(Class<? extends UserBase> userClass) {
		this.userType = userClass.getName();
	}

	/**
	 * Method that return the resource type name
	 * @return String
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * Method that set the resource type name
	 * @return String - Class<? extends ResourceBase>
	 */
	public void setResourceType(Class<? extends ResourceBase> resourceClass) {
		this.resourceType = resourceClass.getName();
	}
}
