package src.main.java.ui; // Pacote ajustado para 'ui'

import src.main.java.model.Cliente;
import src.main.java.model.ItemVenda;
import src.main.java.model.Produto;
import src.main.java.model.Venda;
import src.main.java.service.ClienteService;
import src.main.java.service.ProdutoService;
import src.main.java.service.VendaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VendaView extends JPanel {
    private VendaService vendaService;
    private ClienteService clienteService;
    private ProdutoService produtoService;

    private JComboBox<String> comboClientes;
    private JComboBox<String> comboProdutos;
    private List<Cliente> listaClientesCache;
    private List<Produto> listaProdutosCache;

    private DefaultTableModel modeloCarrinho;
    private JLabel lblTotal;
    private List<ItemVenda> carrinho;

    public VendaView() {
        this.vendaService = new VendaService();
        this.clienteService = new ClienteService();
        this.produtoService = new ProdutoService();
        this.carrinho = new ArrayList<>();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelSelecao = new JPanel(new GridLayout(3, 2, 5, 5));
        painelSelecao.setBorder(BorderFactory.createTitledBorder("Nova Venda"));

        comboClientes = new JComboBox<>();
        comboProdutos = new JComboBox<>();
        JTextField txtQuantidade = new JTextField("1");
        JButton btnAdicionar = new JButton("Adicionar Item");

        painelSelecao.add(new JLabel("Cliente:"));
        painelSelecao.add(comboClientes);
        painelSelecao.add(new JLabel("Produto:"));
        painelSelecao.add(comboProdutos);
        painelSelecao.add(new JLabel("Quantidade:"));
        
        JPanel painelQtdBtn = new JPanel(new BorderLayout(5,0));
        painelQtdBtn.add(txtQuantidade, BorderLayout.CENTER);
        painelQtdBtn.add(btnAdicionar, BorderLayout.EAST);
        painelSelecao.add(painelQtdBtn);

        add(painelSelecao, BorderLayout.NORTH);

        String[] colunas = {"Produto", "Qtd", "Unitário", "Subtotal"};
        modeloCarrinho = new DefaultTableModel(colunas, 0);
        JTable tabelaCarrinho = new JTable(modeloCarrinho);
        add(new JScrollPane(tabelaCarrinho), BorderLayout.CENTER);

        JPanel painelFinal = new JPanel(new BorderLayout());
        lblTotal = new JLabel("Total: R$ 0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JButton btnFinalizar = new JButton("FINALIZAR VENDA");
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFinalizar.setBackground(new Color(100, 200, 100));
        
        painelFinal.add(lblTotal, BorderLayout.CENTER);
        painelFinal.add(btnFinalizar, BorderLayout.EAST);
        painelFinal.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        add(painelFinal, BorderLayout.SOUTH);

        atualizarCombos();

        btnAdicionar.addActionListener(e -> adicionarAoCarrinho(txtQuantidade.getText()));
        btnFinalizar.addActionListener(e -> finalizarVenda());
    }

    public void atualizarCombos() {
        comboClientes.removeAllItems();
        comboProdutos.removeAllItems();

        listaClientesCache = clienteService.listarTodos();
        listaProdutosCache = produtoService.listarTodos();

        for (Cliente c : listaClientesCache) {
            comboClientes.addItem(c.getNome() + " (CPF: " + c.getCpf() + ")");
        }

        for (Produto p : listaProdutosCache) {
            comboProdutos.addItem(p.getNome() + " (Estoque: " + p.getEstoqueAtual() + ")");
        }
    }

    private void adicionarAoCarrinho(String qtdStr) {
        if (comboProdutos.getSelectedIndex() < 0) return;

        try {
            int qtd = Integer.parseInt(qtdStr);
            if (qtd <= 0) {
                JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que 0");
                return;
            }

            Produto produtoSelecionado = listaProdutosCache.get(comboProdutos.getSelectedIndex());
            
            if (qtd > produtoSelecionado.getEstoqueAtual()) {
                JOptionPane.showMessageDialog(this, "Estoque insuficiente! Disponível: " + produtoSelecionado.getEstoqueAtual());
                return;
            }

            ItemVenda item = new ItemVenda(produtoSelecionado, qtd);
            carrinho.add(item);

            modeloCarrinho.addRow(new Object[]{
                produtoSelecionado.getNome(),
                qtd,
                String.format("R$ %.2f", produtoSelecionado.getPreco()),
                String.format("R$ %.2f", item.getSubtotal())
            });

            atualizarTotal();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
        }
    }

    private void atualizarTotal() {
        double total = carrinho.stream().mapToDouble(ItemVenda::getSubtotal).sum();
        lblTotal.setText(String.format("Total: R$ %.2f   ", total));
    }

    private void finalizarVenda() {
        if (comboClientes.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            return;
        }
        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O carrinho está vazio.");
            return;
        }

        Cliente clienteSelecionado = listaClientesCache.get(comboClientes.getSelectedIndex());
        int idVenda = (int) (System.currentTimeMillis() / 1000); 
        
        Venda venda = new Venda(idVenda, clienteSelecionado);
        for (ItemVenda item : carrinho) {
            venda.adicioanrItem(item.getProduto(), item.getQuantidade());
        }

        try {
            String mensagem = vendaService.realizarVenda(venda);
            JOptionPane.showMessageDialog(this, mensagem);
            
            carrinho.clear();
            modeloCarrinho.setRowCount(0);
            atualizarTotal();
            atualizarCombos();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao finalizar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}