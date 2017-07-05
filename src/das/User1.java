package das;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users_1")
public class User1 extends UserBase {
	
	@DatabaseField(columnName = "endereco", canBeNull = false)
	public String endereco;
	
	@DatabaseField(columnName = "idade", canBeNull = false)
	public Integer idade;

	public User1() {
	}

	public User1(String name, int functionalRegistration, String endereco, int idade) {
		super(functionalRegistration, name);
		this.setEndereco(endereco);
		this.setIdade(idade);
	}

	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	
}
