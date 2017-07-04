package das;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users_2")
public class User2 extends User{
	
	@DatabaseField(columnName = "blabla", canBeNull = false)
	public String blabla;

	public String getBlabla() {
		return blabla;
	}

	public void setBlabla(String blabla) {
		this.blabla = blabla;
	}
}
