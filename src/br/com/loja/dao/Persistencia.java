package br.com.loja.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {

    // Método genérico para SALVAR qualquer tipo de lista (Celulares, Clientes, etc)
    public void salvarArquivo(List<?> dados, String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(dados);
            System.out.println("Dados salvos com sucesso em: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    // Método genérico para LER o arquivo e retornar a lista
    // Retorna uma lista vazia se o arquivo não existir (primeira execução)
    @SuppressWarnings("unchecked")
    public List<Object> carregarArquivo(String nomeArquivo) {
        List<Object> dados = new ArrayList<>();
        
        File arquivo = new File(nomeArquivo);
        // Se o arquivo não existe, retornamos a lista vazia para começar o cadastro do zero
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