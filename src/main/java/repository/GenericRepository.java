package src.main.java.repository;

import src.main.util.FileUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericRepository<T extends Serializable> {
    protected List<T> registros;
    private String nomeArquivo;

    public GenericRepository(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.registros = new ArrayList<>();
        // Ao iniciar o repositório, ele já tenta carregar o que existe no arquivo
        carregarDados();
    }

    // Carrega a lista do disco para a memória usando o FileUtils
    private void carregarDados() {
        this.registros = FileUtils.carregarArquivo(this.nomeArquivo);
    }

    // Salva a lista da memória para o disco usando o FileUtils
    protected void salvarDados() {
        FileUtils.salvarArquivo(this.nomeArquivo, this.registros);
    }

    public void salvar(T obj) {
        registros.add(obj);
        salvarDados(); // Salvamento automático após adicionar
    }

    public List<T> listarTodos() {
        return new ArrayList<>(registros);
    }

    public void remover(T obj) {
        registros.remove(obj);
        salvarDados(); // Salvamento automático após remover
    }
    
    // Método essencial para updates: se você alterar um objeto (ex: mudar estoque),
    // precisa chamar este método para garantir que a mudança vá para o arquivo.
    public void atualizarArquivo() {
        salvarDados();
    }

    public void removerPorIndice(int index) {
        if (index >= 0 && index < registros.size()) {
            registros.remove(index);
            salvarDados();
        }
    }

    public void limpar() {
        registros.clear();
        salvarDados();
    }

    public T buscarPorIndice(int index) {
        if (index >= 0 && index < registros.size()) {
            return registros.get(index);
        }
        return null;
    }

    public int tamanho() {
        return registros.size();
    }
}