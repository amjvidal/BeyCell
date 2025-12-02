package src.main.java.service;

import src.main.java.model.Produto;
import src.main.java.repository.ProdutoRepository;

import java.util.List;

public class ProdutoService {
    private ProdutoRepository produtoRepository;

    public ProdutoService() {
        this.produtoRepository = new ProdutoRepository();
    }

    public void cadastrarProduto(Produto produto) throws Exception {
        if (produto.getPreco() < 0) throw new Exception("O preço não pode ser negativo.");
        if (produto.getEstoqueAtual() < 0) throw new Exception("O estoque inicial não pode ser negativo.");
        if (produto.getNome() == null || produto.getNome().isEmpty()) throw new Exception("Nome do produto é obrigatório.");

        produtoRepository.salvar(produto);
    }

    public Produto buscarPorId(int id) {
        return produtoRepository.buscarPorId(id);
    }

    public List<Produto> listarTodos() {
        return produtoRepository.listarTodos();
    }

    public void removerProduto(int id) throws Exception {
        Produto p = produtoRepository.buscarPorId(id);
        if (p == null) throw new Exception("Produto não encontrado para remoção.");
        produtoRepository.remover(p);
    }
}