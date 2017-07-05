package example;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import framework.ResourceBase;

@DatabaseTable(tableName = "resources_1")
public class Resource1 extends ResourceBase{

	public Resource1(){
	}

	public Resource1(Long patrimonyId, String name, String description, String resolucao) {
		super(patrimonyId, name, description);
		this.resolucao = resolucao;
	}

	@DatabaseField(columnName = "resolucao", canBeNull = false)
	public String resolucao;

	public String getResolucao() {
		return resolucao;
	}

	public void setResolucao(String resolucao) {
		this.resolucao = resolucao;
	}

}
