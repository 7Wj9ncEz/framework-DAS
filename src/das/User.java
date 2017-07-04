package das;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

public class User {

	public User(int functionalRegistration, String name) {
		super();
		this.functionalRegistration = functionalRegistration;
		this.name = name;
	}
	public static final String NAME_FIELD_NAME = "name";
	
	@DatabaseField(generatedId = true, columnName = "user_id")
	private int functionalRegistration;

	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	private String name;

	public User(String name2) {
		this.name = name2;
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