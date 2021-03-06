package example;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import framework.UserBase;

@DatabaseTable(tableName = "users_2")
public class User2 extends UserBase {
	
	@DatabaseField(columnName = "last_name", canBeNull = false)
	public String lastName;

	public User2() {
	}

	public User2(String name, int functionalRegistration, String lastName) {
		super(functionalRegistration, name);
		this.setLastName(lastName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
