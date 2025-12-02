package src.main.java.service;

import src.main.java.model.Cliente;
import src.main.java.repository.ClienteRepository;

import java.util.List;

public class ClienteService {
    private ClienteRepository clienteRepository;

    public ClienteService() {
        this.clienteRepository = new ClienteRepository();
    }

    public void cadastrarCliente(Cliente cliente) throws Exception {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new Exception("O nome do cliente é obrigatório.");
        }
        if (cliente.getCpf() == null || cliente.getCpf().length() != 11) {
            throw new Exception("CPF inválido. Deve conter 11 dígitos.");
        }
        
        // Verifica se já existe CPF igual
        if (clienteRepository.buscarPorCpf(cliente.getCpf()) != null) {
            throw new Exception("Já existe um cliente cadastrado com este CPF.");
        }

        clienteRepository.salvar(cliente);
    }

    public void atualizarCliente(Cliente cliente) throws Exception {
        if (cliente.getId() <= 0) throw new Exception("ID inválido para atualização.");
        clienteRepository.salvar(cliente); // No GenericRepository, salvar e atualizar pode ser a mesma lógica se sobrescrever
        clienteRepository.atualizarArquivo();
    }

    public void removerCliente(int id) throws Exception {
        Cliente c = clienteRepository.buscarPorId(id);
        if (c == null) throw new Exception("Cliente não encontrado.");
        clienteRepository.remover(c);
    }

    public Cliente buscarPorId(int id) {
        return clienteRepository.buscarPorId(id);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.listarTodos();
    }
}