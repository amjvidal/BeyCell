package src.main.java.controller;

import java.util.List;

import src.main.java.model.Produto;
import src.main.java.repository.ProdutoRepository;



public class ProdutoController {
private ProdutoRepository produtoRepository = new ProdutoRepository();


public void adicionarProduto(Produto produto) throws Exception {
if (produto.getPreco() < 0) throw new Exception("Preço não pode ser negativo");
if (produto.getEstoqueAtual() < 0) throw new Exception("Estoque não pode ser negativo");
produtoRepository.salvar(produto);
}


public Produto buscarProdutoPorId(int id) throws Exception {
Produto p = produtoRepository.buscarPorId(id);
if (p == null) throw new Exception("Produto não encontrado");
return p;
}


public List<Produto> buscarPorNome(String nome) {
return produtoRepository.buscarPorNome(nome);
}


public void atualizarEstoque(int id, int novoEstoque) throws Exception {
if (novoEstoque < 0) throw new Exception("Estoque não pode ser negativo");
Produto p = buscarProdutoPorId(id);
p.setEstoqueAtual(novoEstoque);


if (p.getEstoqueAtual() <= p.getEstoqueMinimo()) {
System.out.println("⚠ ALERTA: Estoque baixo para o produto: " + p.getNome());
}
}


public void removerProduto(int id) throws Exception {
Produto p = buscarProdutoPorId(id);
produtoRepository.remover(p);
}


public List<Produto> listarProdutos() {
return produtoRepository.listarTodos();
}
}
