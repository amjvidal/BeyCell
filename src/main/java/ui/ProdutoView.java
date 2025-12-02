package src.main.java.ui; // Pacote ajustado para 'ui'

import src.main.java.model.Produto;
import src.main.java.service.ProdutoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProdutoView extends JPanel {
    private ProdutoService produtoService;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JTextField txtId, txtNome, txtMarca, txtModelo, txtPreco, txtEstoque, txtMinimo;

    public ProdutoView() {
        this.produtoService = new ProdutoService();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunas = {"ID", "Nome", "Marca", "Preço", "Estoque", "Mínimo"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabela = new JTable(modeloTabela);
        atualizarTabela();
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createTitledBorder("Dados do Produto"));

        txtId = new JTextField(); 
        txtNome = new JTextField();
        txtMarca = new JTextField();
        txtModelo = new JTextField();
        txtPreco = new JTextField();
        txtEstoque = new JTextField();
        txtMinimo = new JTextField();

        painelForm.add(new JLabel("ID:")); painelForm.add(txtId);
        painelForm.add(new JLabel("Nome:")); painelForm.add(txtNome);
        painelForm.add(new JLabel("Marca:")); painelForm.add(txtMarca);
        painelForm.add(new JLabel("Modelo:")); painelForm.add(txtModelo);
        painelForm.add(new JLabel("Preço:")); painelForm.add(txtPreco);
        painelForm.add(new JLabel("Estoque:")); painelForm.add(txtEstoque);
        painelForm.add(new JLabel("Minimo:")); painelForm.add(txtMinimo);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = new JButton("Salvar");
        JButton btnRemover = new JButton("Remover");
        JButton btnLimpar = new JButton("Limpar");

        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnSalvar);

        JPanel painelSul = new JPanel(new BorderLayout());
        painelSul.add(painelForm, BorderLayout.CENTER);
        painelSul.add(painelBotoes, BorderLayout.SOUTH);
        add(painelSul, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvarProduto());
        btnRemover.addActionListener(e -> removerProduto());
        btnLimpar.addActionListener(e -> limparFormulario());

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() != -1) {
                preencherFormulario(tabela.getSelectedRow());
            }
        });
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Produto> produtos = produtoService.listarTodos();
        for (Produto p : produtos) {
            modeloTabela.addRow(new Object[]{
                p.getId(), p.getNome(), p.getMarca(), p.getPreco(), p.getEstoqueAtual(), p.getEstoqueMinimo()
            });
        }
    }

    private void salvarProduto() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nome = txtNome.getText();
            String marca = txtMarca.getText();
            String modelo = txtModelo.getText();
            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
            int estoque = Integer.parseInt(txtEstoque.getText());
            int minimo = Integer.parseInt(txtMinimo.getText());

            Produto produto = new Produto(id, nome, marca, modelo, preco, estoque, minimo);
            produtoService.cadastrarProduto(produto);

            JOptionPane.showMessageDialog(this, "Produto salvo!");
            limparFormulario();
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerProduto() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) return;
        int id = (int) tabela.getValueAt(linha, 0);
        try {
            produtoService.removerProduto(id);
            JOptionPane.showMessageDialog(this, "Produto removido.");
            atualizarTabela();
            limparFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void preencherFormulario(int linha) {
        Produto p = produtoService.buscarPorId((int) tabela.getValueAt(linha, 0));
        if (p != null) {
            txtId.setText(String.valueOf(p.getId()));
            txtNome.setText(p.getNome());
            txtMarca.setText(p.getMarca());
            txtModelo.setText(p.getModelo());
            txtPreco.setText(String.valueOf(p.getPreco()));
            txtEstoque.setText(String.valueOf(p.getEstoqueAtual()));
            txtMinimo.setText(String.valueOf(p.getEstoqueMinimo()));
        }
    }

    private void limparFormulario() {
        txtId.setText("");
        txtNome.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtPreco.setText("");
        txtEstoque.setText("");
        txtMinimo.setText("");
        tabela.clearSelection();
    }
}