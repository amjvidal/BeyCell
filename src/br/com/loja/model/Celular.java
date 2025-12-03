package br.com.loja.model;

import java.io.Serializable;

public class Celular implements Serializable {
    // ID para serialização (boa prática)
    private static final long serialVersionUID = 1L;

    private int id; // Identificador único
    private String marca;
    private String modelo;
    private double preco;
    private int ram; // Em GB
    private int armazenamento; // Em GB
    private String cor;
    private int quantidadeEstoque;
    private int estoqueMinimo; // Para o alerta de estoque baixo

    // Construtor Vazio
    public Celular() {}

    // Construtor Completo
    public Celular(int id, String marca, String modelo, double preco, int ram, int armazenamento, String cor, int quantidadeEstoque, int estoqueMinimo) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.preco = preco;
        this.ram = ram;
        this.armazenamento = armazenamento;
        this.cor = cor;
        this.quantidadeEstoque = quantidadeEstoque;
        this.estoqueMinimo = estoqueMinimo;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getRam() { return ram; }
    public void setRam(int ram) { this.ram = ram; }

    public int getArmazenamento() { return armazenamento; }
    public void setArmazenamento(int armazenamento) { this.armazenamento = armazenamento; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public int getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    public int getEstoqueMinimo() { return estoqueMinimo; }
    public void setEstoqueMinimo(int estoqueMinimo) { this.estoqueMinimo = estoqueMinimo; }

    // O toString ajuda a mostrar o nome bonito nas listas da interface gráfica
    @Override
    public String toString() {
        return marca + " " + modelo + " (" + armazenamento + "GB) - R$ " + preco;
    }
}