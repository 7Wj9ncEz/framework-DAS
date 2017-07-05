package example;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "resources_2")
public class Resource2 extends ResourceBase{
	
	@DatabaseField(columnName = "marca", canBeNull = false)
	public String marca;

	public Resource2(){
	}

	public Resource2(Long patrimonyId, String name, String description, String marca) {
		super(patrimonyId, name, description);
		this.marca = marca;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

}