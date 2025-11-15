package src.main.java.model;

import java.io.Serializable;

public class ItemVenda implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private Produto produto;
    private int quantidade;
    private double subtotal;

    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.subtotal = produto.getPreco() * quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        atualizarSubtotal();
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        atualizarSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    private void atualizarSubtotal() {
        this.subtotal = produto.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return "ItemVenda {" +
                "produto=" + produto.getNome() +
                ", quantidade=" + quantidade +
                ", subtotal=" + subtotal +
                '}';
    }

}
