package br.com.loja.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private LocalDateTime dataHora;
    private Cliente cliente;
    private List<Celular> itensVendidos; // Lista de celulares nesta venda
    private double valorTotal;

    public Venda() {
        // Inicializa a lista para não dar erro se adicionar itens depois
        this.itensVendidos = new ArrayList<>();
    }

    public Venda(int id, Cliente cliente, List<Celular> itensVendidos, double valorTotal) {
        this.id = id;
        this.dataHora = LocalDateTime.now(); // Pega a hora atual automaticamente
        this.cliente = cliente;
        this.itensVendidos = itensVendidos;
        this.valorTotal = valorTotal;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<Celular> getItensVendidos() { return itensVendidos; }
    public void setItensVendidos(List<Celular> itensVendidos) { this.itensVendidos = itensVendidos; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
    
    @Override
    public String toString() {
        return "Venda #" + id + " - " + cliente.getNome() + " - R$ " + valorTotal;
    }
}