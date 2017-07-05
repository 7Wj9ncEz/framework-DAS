package framework;

import com.j256.ormlite.field.DatabaseField;

/**
 * Class that is base to another user classes extends
 */
public class UserBase {

	public static final String USER_ID = "user_id";
	public static final String USER_NAME = "user_name";

	@DatabaseField(generatedId = true, columnName = USER_ID)
	private int functionalRegistration;

	@DatabaseField(columnName = USER_NAME, canBeNull = false)
	private String name;

	/**
	 * Constructor with all elements
	 * @param functionalRegistration - Long
	 * @param name - String
	 */
	public UserBase(int functionalRegistration, String name) {
		super();
		this.functionalRegistration = functionalRegistration;
		this.name = name;
	}

	/**
	 * Blank UserBase constructor
	 */
	public UserBase() {
	}

	/**
	 * Method that return the user base functional registration
	 * @return Long
	 */
	public int getFunctionalRegistration() {
		return functionalRegistration;
	}

	/**
	 * Method that return the user base functional registration
	 * @param functionalRegistration - Long
	 */
	public void setFunctionalRegistration(int functionalRegistration) {
		this.functionalRegistration = functionalRegistration;
	}

	/**
	 * Method that return the user base name
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method that set the user base name
	 * @param name - String
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}