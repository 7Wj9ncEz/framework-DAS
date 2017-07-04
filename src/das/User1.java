package das;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users_1")
public class User1 extends User{
	
	public String endereco;
	public Integer idade;
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
