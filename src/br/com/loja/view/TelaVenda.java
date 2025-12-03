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
import java.util.ArrayList;
import java.util.List;

public class TelaVenda extends JFrame {

    private VendaService vendaService;
    private CelularService celularService;
    private ClienteService clienteService;

    private JComboBox<Cliente> cbClientes;
    private JComboBox<Celular> cbProdutos;
    private DefaultTableModel modelCarrinho;
    private JLabel lblTotal;
    
    // Lista temporária (carrinho de compras)
    private List<Celular> carrinho; 
    private double totalVenda = 0.0;

    public TelaVenda() {
        vendaService = new VendaService();
        celularService = new CelularService();
        clienteService = new ClienteService();
        carrinho = new ArrayList<>();

        setTitle("Nova Venda");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- TOPO: SELEÇÃO ---
        JPanel panelTopo = new JPanel(new GridLayout(3, 2, 10, 10));
        
        cbClientes = new JComboBox<>();
        carregarClientes();
        
        cbProdutos = new JComboBox<>();
        carregarProdutos();

        JButton btnAdicionar = new JButton("Adicionar ao Carrinho (+)");

        panelTopo.add(new JLabel("Selecione o Cliente:"));
        panelTopo.add(cbClientes);
        panelTopo.add(new JLabel("Selecione o Produto:"));
        panelTopo.add(cbProdutos);
        panelTopo.add(new JLabel("")); // Espaço vazio
        panelTopo.add(btnAdicionar);

        add(panelTopo, BorderLayout.NORTH);

        // --- CENTRO: CARRINHO ---
        modelCarrinho = new DefaultTableModel(new String[]{"Produto", "Preço"}, 0);
        JTable tabelaCarrinho = new JTable(modelCarrinho);
        add(new JScrollPane(tabelaCarrinho), BorderLayout.CENTER);

        // --- BAIXO: TOTAL E FINALIZAR ---
        JPanel panelBaixo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total: R$ 0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 20));
        JButton btnFinalizar = new JButton("FINALIZAR VENDA");
        
        panelBaixo.add(lblTotal);
        panelBaixo.add(btnFinalizar);
        add(panelBaixo, BorderLayout.SOUTH);

        // --- AÇÕES ---
        
        // Botão Adicionar
        btnAdicionar.addActionListener(e -> {
            Celular produto = (Celular) cbProdutos.getSelectedItem();
            if (produto != null) {
                // Verifica estoque antes de adicionar
                if (produto.getQuantidadeEstoque() > 0) {
                    carrinho.add(produto);
                    modelCarrinho.addRow(new Object[]{produto.getMarca() + " " + produto.getModelo(), produto.getPreco()});
                    totalVenda += produto.getPreco();
                    lblTotal.setText("Total: R$ " + totalVenda);
                } else {
                    JOptionPane.showMessageDialog(this, "Produto sem estoque!");
                }
            }
        });

        // Botão Finalizar
        btnFinalizar.addActionListener(e -> {
            if (carrinho.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Carrinho vazio!");
                return;
            }
            Cliente cliente = (Cliente) cbClientes.getSelectedItem();
            
            // Cria a venda
            Venda venda = new Venda(0, cliente, new ArrayList<>(carrinho), totalVenda);
            vendaService.registrarVenda(venda);

            // Baixa o estoque de cada item
            for (Celular c : carrinho) {
                celularService.baixarEstoque(c.getId());
                // Verifica se ficou abaixo do mínimo (Alerta do PDF) [cite: 35]
                Celular atualizado = null;
                for(Celular busca : celularService.listarTodos()) { if(busca.getId() == c.getId()) atualizado = busca; }
                
                if(atualizado != null && atualizado.getQuantidadeEstoque() < atualizado.getEstoqueMinimo()) {
                    JOptionPane.showMessageDialog(this, "ALERTA: O estoque de " + atualizado.getModelo() + " está abaixo do mínimo!");
                }
            }

            JOptionPane.showMessageDialog(this, "Venda Realizada! Fatura gerada na pasta do projeto.");
            dispose(); // Fecha a janela
        });
    }

    private void carregarClientes() {
        for (Cliente c : clienteService.listarTodos()) {
            cbClientes.addItem(c);
        }
    }

    private void carregarProdutos() {
        for (Celular c : celularService.listarTodos()) {
            cbProdutos.addItem(c);
        }
    }
}