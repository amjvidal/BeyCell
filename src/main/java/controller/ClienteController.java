package src.main.java.controller;

import java.util.List;

import src.main.java.model.Cliente;
import src.main.java.repository.ClienteRepository;

public class ClienteController {
private ClienteRepository clienteRepository = new ClienteRepository();


public void adicionarCliente(Cliente cliente) throws Exception {
if (cliente.getNome().isEmpty()) throw new Exception("Nome inválido");
if (cliente.getCpf().length() != 11) throw new Exception("CPF inválido");
clienteRepository.salvar(cliente);
}


public Cliente buscarPorId(int id) throws Exception {
Cliente c = clienteRepository.buscarPorId(id);
if (c == null) throw new Exception("Cliente não encontrado");
return c;
}


public Cliente buscarPorCpf(String cpf) throws Exception {
Cliente c = clienteRepository.buscarPorCpf(cpf);
if (c == null) throw new Exception("Cliente não encontrado");
return c;
}


public List<Cliente> buscarPorNome(String nome) {
return clienteRepository.buscarPorNome(nome);
}


public void removerCliente(int id) throws Exception {
Cliente c = buscarPorId(id);
clienteRepository.remover(c);
}


public List<Cliente> listarClientes() {
return clienteRepository.listarTodos();
}
}
