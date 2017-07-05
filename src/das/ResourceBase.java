package das;

import com.j256.ormlite.field.DatabaseField;

public class ResourceBase {

	public static final String RESOURCE_ID = "resource_id";
	public static final String RESOURCE_NAME = "resource_name";
	public static final String RESOURCE_DESCRIPTION = "resource_description";

	@DatabaseField(generatedId = true, columnName = RESOURCE_ID)
	private Long patrimonyId;
	
	@DatabaseField(columnName = RESOURCE_NAME)
	private String name;
	
	@DatabaseField(columnName = RESOURCE_DESCRIPTION)
	private String description;
	 
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
 
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Long getPatrimonyId() {
		return patrimonyId;
	}
	
	public void setPatrimonyId(Long patrimonyId) {
		this.patrimonyId = patrimonyId;
	}
}