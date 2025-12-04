package br.com.loja.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {

    // Metodo para salvar todo tipo de List
    public void salvarArquivo(List<?> dados, String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(dados);
            System.out.println("Dados salvos com sucesso em: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    // Metodo pra ler o arquivo e retornar uma lista
    @SuppressWarnings("unchecked")
    public List<Object> carregarArquivo(String nomeArquivo) {
        List<Object> dados = new ArrayList<>();
        
        File arquivo = new File(nomeArquivo);
        // Se o arquivo não existir, retorna uma lista vazia
        if (!arquivo.exists()) {
            return dados;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            dados = (List<Object>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return dados;
    }
}