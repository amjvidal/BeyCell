package src.main.java.model;

import java.io.Serializable;

public class Produto  implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private int id;
    private String nome; 
    private String marca;
    private String modelo;
    private double preco;
    private int estoqueAtual;
    private int estoqueMinimo;

    public Produto(int id, String nome, String marca, String modelo, double preco, int estoqueAtual, int estoqueMinimo) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.modelo = modelo;
        this.preco = preco;
        this.estoqueAtual = estoqueAtual;
        this.estoqueMinimo = estoqueMinimo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }


    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(int estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    @Override
    public String toString() {
        return "Produto {" + 
                "id= " + id +
                ", nome=' " + nome + '\'' +
                ", marca=' " + marca + '\'' +
                ", modelo=' " + modelo + '\'' +
                ", preço= " + preco + 
                ", estoqueAtual= " + estoqueAtual + 
                ", estoqueMinimo= " + estoqueMinimo +
                '}';
    }


}
