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

    // carrega dados do arquivo
    private void carregarDados() {
        List<Object> dados = persistencia.carregarArquivo(ARQUIVO);
        for (Object obj : dados) {
            this.clientes.add((Cliente) obj);
        }
    }

    //salva os dados
    public void salvarDados() {
        persistencia.salvarArquivo(clientes, ARQUIVO);
    }

    //cadastra novo cliente
    public void cadastrar(Cliente cliente) {
        clientes.add(cliente);
        salvarDados();
    }

    //lista os cliente 
    public List<Cliente> listarTodos() {
        return clientes;
    }

    // metodo de busca pelo cpf
    public Cliente buscarPorCpf(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    // remover cliente na aba cliente
    public void remover(String cpf) {
        Cliente clienteParaRemover = null;
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                clienteParaRemover = c;
                break;
            }
        }
        
        if (clienteParaRemover != null) {
            clientes.remove(clienteParaRemover);
            salvarDados(); // salva os dados
        }
    }
}