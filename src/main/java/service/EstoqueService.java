package src.main.java.service;

import src.main.java.model.Produto;
import src.main.java.repository.ProdutoRepository;

public class EstoqueService {
    private ProdutoRepository produtoRepository;

    public EstoqueService() {
        this.produtoRepository = new ProdutoRepository();
    }

    // Verifica se tem quantidade suficiente sem alterar nada
    public boolean temEstoque(Produto produto, int quantidadeDesejada) {
        Produto pAtualizado = produtoRepository.buscarPorId(produto.getId());
        return pAtualizado != null && pAtualizado.getEstoqueAtual() >= quantidadeDesejada;
    }

    // Realiza a baixa do estoque e retorna TRUE se atingiu o nível crítico
    public boolean baixarEstoque(int idProduto, int quantidade) throws Exception {
        Produto p = produtoRepository.buscarPorId(idProduto);
        
        if (p == null) throw new Exception("Produto não encontrado.");
        if (p.getEstoqueAtual() < quantidade) {
            throw new Exception("Estoque insuficiente para o produto: " + p.getNome());
        }

        int novoEstoque = p.getEstoqueAtual() - quantidade;
        produtoRepository.atualizarEstoque(p.getId(), novoEstoque);

        return verificarNivelCritico(p);
    }

    // Verifica se o produto está abaixo do estoque mínimo
    public boolean verificarNivelCritico(Produto produto) {
        return produto.getEstoqueAtual() <= produto.getEstoqueMinimo();
    }

    public void reporEstoque(int idProduto, int quantidade) throws Exception {
        if (quantidade <= 0) throw new Exception("Quantidade de reposição deve ser positiva.");
        
        Produto p = produtoRepository.buscarPorId(idProduto);
        if (p != null) {
            produtoRepository.atualizarEstoque(p.getId(), p.getEstoqueAtual() + quantidade);
        }
    }
}