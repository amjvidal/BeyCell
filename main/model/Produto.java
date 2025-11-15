package main.model;

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






}
