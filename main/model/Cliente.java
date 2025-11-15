package main.model;
import java.io.Serializable;

public class Cliente implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;
    private String email;


public Cliente(int id, String nome, String cpf, String endereco, String telefone, String email){
    this.id = id;
    this.nome = nome;
    this.cpf = cpf;
    this.endereco = endereco;
    this.telefone = telefone;
    this.email = email;
}
public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public String getNome() {
    return nome;
}

public void setNome(String nome) {
    this.nome = nome;
}

public String getCpf() {
    return cpf;
}

public void setCpf(String cpf) {
    this.cpf = cpf;
}

public String getEndereco() {
    return endereco;
}

public void setEndereco(String endereco) {
    this.endereco = endereco;
}

public String getTelefone() {
    return telefone;
}

public void setTelefone(String telefone) {
    this.telefone = telefone;
}


public void setEmail(String email) {
    this.email = email;
}

public String getEmail() {
    return email;
}

@Override
public String toString (){
    return "Cliente {" +
            "id=" + id +
            ", nome=" + nome +'\'' +
            ", cpf=" + cpf +'\'' +
            ", endereco=" + endereco + '\'' +
            ", telefone=" + telefone + '\'' +
            ", email=" + email + '\'' +
            '}';
}





}