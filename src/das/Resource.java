package das;

import com.j256.ormlite.field.DatabaseField;

public class Resource {
	@DatabaseField(generatedId = true, columnName = "resource_id")
	private Long patrimonyId;
	
	@DatabaseField(columnName = "resource_name")
	private String name;
	
	@DatabaseField(columnName = "resource_description")
	private String description;
	
	@DatabaseField(columnName = "resource_borrowed", defaultValue="false")
	private Boolean borrowed;
 
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

	public Boolean getBorrowed() {
		return borrowed;
	}

	public void setBorrowed(Boolean borrowed) {
		this.borrowed = borrowed;
	}
}