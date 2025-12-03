package br.com.loja.service;

import br.com.loja.dao.Persistencia;
import br.com.loja.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    private List<Cliente> clientes;
    private Persistencia persistencia;
    private final String ARQUIVO = "clientes.dat";

    public ClienteService() {
        this.persistencia = new Persistencia();
        this.clientes = new ArrayList<>();
        carregarDados();
    }

    private void carregarDados() {
        List<Object> dados = persistencia.carregarArquivo(ARQUIVO);
        for (Object obj : dados) {
            this.clientes.add((Cliente) obj);
        }
    }

    public void salvarDados() {
        persistencia.salvarArquivo(clientes, ARQUIVO);
    }

    public void cadastrar(Cliente cliente) {
        clientes.add(cliente);
        salvarDados();
    }

    public List<Cliente> listarTodos() {
        return clientes;
    }

    public Cliente buscarPorCpf(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }
}