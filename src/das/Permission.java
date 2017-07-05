package das;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static das.Permission.PERMISSIONS_TABLE;

@DatabaseTable(tableName = PERMISSIONS_TABLE)
public class Permission extends Utility{

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
	
	public boolean havePermission(User user, Resource resource) throws SQLException{
		Map<String, Object> p = new HashMap<String, Object>();
		p.put(USER_TYPE, UserType(user));
		p.put(RESOURCE_TYPE, ResourceType(resource));

		List<Permission> resources = PermissionDaoSingleton.getDao().queryForFieldValues(p);
		if(resources.isEmpty()){
			return false;
		}
		return true;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
}
