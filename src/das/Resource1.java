package das;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "resources_1")
public class Resource1 extends ResourceBase{
	@DatabaseField(columnName = "resolucao", canBeNull = false)
	public String resolucao;

	public String getResolucao() {
		return resolucao;
	}

	public void setResolucao(String resolucao) {
		this.resolucao = resolucao;
	}

}
