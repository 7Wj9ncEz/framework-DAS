package das;

import com.j256.ormlite.field.DatabaseField;

public class User {

	public static final String USER_ID = "user_id";
	public static final String USER_NAME = "user_name";

	@DatabaseField(generatedId = true, columnName = USER_ID)
	private int functionalRegistration;

	@DatabaseField(columnName = USER_NAME, canBeNull = false)
	private String name;

	public User(int functionalRegistration, String name) {
		super();
		this.functionalRegistration = functionalRegistration;
		this.name = name;
	}

	public User(String name) {
		this.name = name;
	}
	
	public User() {
	}
	
	public int getFunctionalRegistration() {
		return functionalRegistration;
	}

	public void setFunctionalRegistration(int functionalRegistration) {
		this.functionalRegistration = functionalRegistration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}