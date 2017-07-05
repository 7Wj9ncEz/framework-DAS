package das;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "permissions")
public class Permission extends Utility{
	@DatabaseField(generatedId = true, columnName = "permission_id")
	private int permissionId;
	
	@DatabaseField(columnName = "userType", canBeNull = false)
	public String userType;
	
	@DatabaseField(columnName = "resourceType", canBeNull = false)
	public String resourceType;
	
	public boolean havePermission(User user, Resource resource) throws SQLException{
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userType", UserType(user));
		p.put("resourceType", ResourceType(resource));
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
