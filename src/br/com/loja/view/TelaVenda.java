package br.com.loja.view;

import br.com.loja.model.Celular;
import br.com.loja.model.Cliente;
import br.com.loja.model.Venda;
import br.com.loja.service.CelularService;
import br.com.loja.service.ClienteService;
import br.com.loja.service.VendaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TelaVenda extends JPanel {

    private VendaService vendaService;
    private CelularService celularService;
    private ClienteService clienteService;

    private JComboBox<Cliente> cbClientes;
    private JComboBox<Celular> cbProdutos;
    private DefaultTableModel modelCarrinho;
    private JTable tabelaCarrinho;
    private JLabel lblTotal;
    private List<Celular> carrinho; 
    private double totalVenda = 0.0;

    public TelaVenda(ActionListener acaoVoltar) {
        vendaService = new VendaService();
        celularService = new CelularService();
        clienteService = new ClienteService();
        carrinho = new ArrayList<>();

        setLayout(new BorderLayout());

        // topo da tela
        JPanel panelTopoGeral = new JPanel(new BorderLayout());
        JButton btnVoltar = new JButton("<< Voltar");
        btnVoltar.addActionListener(acaoVoltar);
        panelTopoGeral.add(btnVoltar, BorderLayout.NORTH);

        // area pra selecionar cliente e produto
        JPanel panelSelecao = new JPanel(new GridLayout(3, 2, 10, 10));
        cbClientes = new JComboBox<>();
        cbProdutos = new JComboBox<>();
        
        JButton btnAdicionar = new JButton("Adicionar ao Carrinho (+)");
        JButton btnRemover = new JButton("Remover Item (-)");

        panelSelecao.add(new JLabel("Selecione o Cliente:")); panelSelecao.add(cbClientes);
        panelSelecao.add(new JLabel("Selecione o Produto:")); panelSelecao.add(cbProdutos);
        panelSelecao.add(btnRemover); panelSelecao.add(btnAdicionar);

        panelTopoGeral.add(panelSelecao, BorderLayout.CENTER);
        add(panelTopoGeral, BorderLayout.NORTH);

        // tabela do carrinho
        modelCarrinho = new DefaultTableModel(new String[]{"Produto", "Preço"}, 0);
        tabelaCarrinho = new JTable(modelCarrinho);
        add(new JScrollPane(tabelaCarrinho), BorderLayout.CENTER);

        // valort total e botao de finalizar compra na parte de baixo
        JPanel panelBaixo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total: R$ 0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 20));
        JButton btnFinalizar = new JButton("FINALIZAR VENDA");
        
        panelBaixo.add(lblTotal);
        panelBaixo.add(btnFinalizar);
        add(panelBaixo, BorderLayout.SOUTH);

        // todos os eventos

        // adicionar ao carrinho
        btnAdicionar.addActionListener(e -> {
            Celular produto = (Celular) cbProdutos.getSelectedItem();
            if (produto != null) {
                if (produto.getQuantidadeEstoque() > 0) {
                    carrinho.add(produto);
                    modelCarrinho.addRow(new Object[]{produto.getMarca() + " " + produto.getModelo(), produto.getPreco()});
                    totalVenda += produto.getPreco();
                    lblTotal.setText("Total: R$ " + String.format("%.2f", totalVenda));
                } else {
                    JOptionPane.showMessageDialog(this, "Sem estoque!");
                }
            }
        });


        //remover do carrinho
        btnRemover.addActionListener(e -> {
            int linha = tabelaCarrinho.getSelectedRow();
            if (linha >= 0) {
                Celular item = carrinho.get(linha);
                totalVenda -= item.getPreco();
                carrinho.remove(linha);
                modelCarrinho.removeRow(linha);
                lblTotal.setText("Total: R$ " + String.format("%.2f", Math.max(0, totalVenda)));
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um item.");
            }
        });

        // finalizar compra
        btnFinalizar.addActionListener(e -> {
            if (carrinho.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Carrinho vazio!");
                return;
            }
            Cliente cliente = (Cliente) cbClientes.getSelectedItem();
            Venda venda = new Venda(0, cliente, new ArrayList<>(carrinho), totalVenda);
            vendaService.registrarVenda(venda);

            for (Celular c : carrinho) {
                celularService.baixarEstoque(c.getId());
                // Verifica estoque mínimo...
                Celular atualizado = null;
                for(Celular busca : celularService.listarTodos()) { if(busca.getId() == c.getId()) atualizado = busca; }
                if(atualizado != null && atualizado.getQuantidadeEstoque() < atualizado.getEstoqueMinimo()) {
                    JOptionPane.showMessageDialog(this, "ALERTA: Estoque baixo para " + atualizado.getModelo());
                }
            }

            JOptionPane.showMessageDialog(this, "Venda Realizada!");
            // Limpar tela
            carrinho.clear();
            modelCarrinho.setRowCount(0);
            totalVenda = 0;
            lblTotal.setText("Total: R$ 0.00");
        });
        
        atualizarDados();
    }

    // metodo usado no menu principal pra carregar combos
    public void atualizarDados() {
        cbClientes.removeAllItems();
        cbProdutos.removeAllItems();
        for (Cliente c : clienteService.listarTodos()) cbClientes.addItem(c);
        for (Celular c : celularService.listarTodos()) cbProdutos.addItem(c);
    }
}