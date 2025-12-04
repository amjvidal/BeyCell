package br.com.loja.service;

import br.com.loja.dao.Persistencia;
import br.com.loja.model.Celular;
import java.util.ArrayList;
import java.util.List;

public class CelularService {
    
    private List<Celular> celulares;
    private Persistencia persistencia;
    private final String ARQUIVO = "celulares.dat";

    public CelularService() {
        this.persistencia = new Persistencia();
        this.celulares = new ArrayList<>();
        carregarDados();
    }

    // carrega do arquivo ao iniciar
    private void carregarDados() {
        List<Object> dadosCarregados = persistencia.carregarArquivo(ARQUIVO);
        for (Object obj : dadosCarregados) {
            this.celulares.add((Celular) obj);
        }
    }

    // salva no arquivo
    public void salvarDados() {
        persistencia.salvarArquivo(celulares, ARQUIVO);
    }

    //cadastra novo celular
    public void cadastrar(Celular celular) {
        // Gera um ID simples (pega o ultimo ID + 1)
        int novoId = celulares.isEmpty() ? 1 : celulares.get(celulares.size() - 1).getId() + 1;
        celular.setId(novoId);
        celulares.add(celular);
        salvarDados();
    }

    //lista todos os celulares
    public List<Celular> listarTodos() {
        return celulares;
    }

    //adicionar unidade ao estoque
    public void adicionarEstoque(int idCelular, int quantidade) {
        for (Celular c : celulares) {
            if (c.getId() == idCelular) {
                c.setQuantidadeEstoque(c.getQuantidadeEstoque() + quantidade);
                salvarDados();
                break;
            }
        }
    }

    //diminuir no estoque quando realiza venda
    public void baixarEstoque(int idCelular) {
        for (Celular c : celulares) {
            if (c.getId() == idCelular) {
                c.setQuantidadeEstoque(c.getQuantidadeEstoque() - 1);
                salvarDados();
                break;
            }
        }
    }
    
    // usa no relatorio pra avisar se estoque baixo
    public List<Celular> buscarEstoqueCritico() {
        List<Celular> criticos = new ArrayList<>();
        for (Celular c : celulares) {
            if (c.getQuantidadeEstoque() <= c.getEstoqueMinimo()) {
                criticos.add(c);
            }
        }
        return criticos;
    }

    // remover o celular na aba de produto
    public void remover(int id) {
        Celular celularParaRemover = null;
        for (Celular c : celulares) {
            if (c.getId() == id) {
                celularParaRemover = c;
                break;
            }
        }
        
        if (celularParaRemover != null) {
            celulares.remove(celularParaRemover);
            salvarDados(); // salva no arquivo
        }
    }
}