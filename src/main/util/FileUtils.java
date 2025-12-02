package src.main.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    // Grava uma lista de objetos genéricos em um arquivo .dat
    public static <T> void salvarArquivo(String nomeArquivo, List<T> dados) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(dados);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo " + nomeArquivo + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Lê uma lista de objetos genéricos de um arquivo .dat
    @SuppressWarnings("unchecked")
    public static <T> List<T> carregarArquivo(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        
        // Se o arquivo não existir, retorna uma lista vazia para evitar erros
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler o arquivo " + nomeArquivo + ": " + e.getMessage());
            // Em caso de erro (arquivo corrompido, classe mudou, etc), retorna lista vazia
            return new ArrayList<>();
        }
    }
    
    // Método utilitário para garantir que o diretório de dados exista (opcional, se quiser salvar em subpasta)
    public static void garantirDiretorio(String caminho) {
        File diretorio = new File(caminho);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }
}