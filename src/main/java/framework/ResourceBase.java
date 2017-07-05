package framework;

import com.j256.ormlite.field.DatabaseField;

/**
 * Class that is base to another resource classes extends
 */
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

	/**
	 * Blank UserBase constructor
	 */
	public ResourceBase(){
	}

	/**
	 * Constructor with all elements
	 * @param patrimonyId - Long
	 * @param name - String
	 * @param description - String
	 */
	public ResourceBase(Long patrimonyId, String name, String description) {
		super();
		this.patrimonyId = patrimonyId;
		this.name = name;
		this.description = description;
	}

	/**
	 * Method that return the resource base name
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method that return the resource base name
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Method that return the resource base description
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Method that return the resource base description
	 * @param description - String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Method that return the resource base patrimony id
	 * @return Long
	 */
	public Long getPatrimonyId() {
		return patrimonyId;
	}

	/**
	 * Method that return the resource base patrimony id
	 * @param patrimonyId - Long
	 */
	public void setPatrimonyId(Long patrimonyId) {
		this.patrimonyId = patrimonyId;
	}
}