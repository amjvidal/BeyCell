package src.main.java.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Cliente cliente;
    private LocalDate data;
    private List<ItemVenda> itens;
    private double total;

    public Venda (int id, Cliente cliente){
        this.id = id;
        this.cliente = cliente;
        this.data = LocalDate.now();
        this.itens = new ArrayList <>();
        this.total = 0;
    }

    //Adicionar Itens
    public void adicioanrItem(Produto produto, int quantidade){
        ItemVenda item = new ItemVenda(produto, quantidade);
        itens.add(item);
        calcularTotal();
    }

    //RemoverItem
    public void RemoverItem(ItemVenda item){
        itens.remove(item);
        calcularTotal();
    }

    //Recalcula o total
    private void calcularTotal(){
        this.total = 0;
        for (ItemVenda item :itens){
            this.total += item.getSubtotal();
        }
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getData() {
        return data;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString(){
        return "Venda{" +
        "id=" + id +
        ", cliente=" + cliente.getNome() +
        ", data=" + data +
        ", total=" + total +
        ", itens=" + itens +
        '}';
    }
    }
