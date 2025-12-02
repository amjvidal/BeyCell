package src.main.java.repository;

import java.util.stream.Collectors;
import src.main.java.model.Produto;
import java.util.List;

public class ProdutoRepository extends GenericRepository<Produto> {

    public ProdutoRepository() {
        super("produtos.dat"); // Define o arquivo onde os produtos serão salvos
    }

    public Produto buscarPorId(int id) {
        return registros.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Produto> buscarPorNome(String nome) {
        return registros.stream()
                .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void atualizarEstoque(int id, int novoEstoque) {
        Produto produto = buscarPorId(id);
        if (produto != null) {
            produto.setEstoqueAtual(novoEstoque);
            atualizarArquivo(); // IMPORTANTE: Salva a alteração do estoque no arquivo .dat
        }
    }
}