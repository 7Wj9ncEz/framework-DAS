package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "userstest")
public class UserTest extends User{

	public UserTest(String name2) {
		super(name2);
	}
	
	public UserTest() {
	}

	public static final String GENDER_FIELD_NAME = "gender";
	
	@DatabaseField(columnName = GENDER_FIELD_NAME)
	private String gender;	
}
