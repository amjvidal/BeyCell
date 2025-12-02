package src.main.java.repository;

import java.util.stream.Collectors;
import src.main.java.model.Cliente;
import java.util.List;

public class ClienteRepository extends GenericRepository<Cliente> {

    public ClienteRepository() {
        super("clientes.dat"); // Define o arquivo onde os clientes serão salvos
    }

    public Cliente buscarPorId(int id) {
        return registros.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return registros.stream()
                .filter(c -> c.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Cliente buscarPorCpf(String cpf) {
        return registros.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
}