package br.com.loja.view;

import br.com.loja.model.Celular;
import br.com.loja.service.CelularService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaCelular extends JFrame {

    private CelularService service;
    private JTable tabela;
    private DefaultTableModel tableModel;

    // Campos de texto para cadastro
    private JTextField txtMarca, txtModelo, txtPreco, txtRam, txtArmaz, txtCor, txtEstoqueMin;

    public TelaCelular() {
        this.service = new CelularService();
        
        setTitle("Gerenciar Celulares");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- PAINEL DE CADASTRO (TOPO) ---
        JPanel panelCadastro = new JPanel(new GridLayout(4, 4, 5, 5));
        
        txtMarca = new JTextField();
        txtModelo = new JTextField();
        txtPreco = new JTextField();
        txtRam = new JTextField();
        txtArmaz = new JTextField();
        txtCor = new JTextField();
        txtEstoqueMin = new JTextField();
        
        panelCadastro.add(new JLabel("Marca:")); panelCadastro.add(txtMarca);
        panelCadastro.add(new JLabel("Modelo:")); panelCadastro.add(txtModelo);
        panelCadastro.add(new JLabel("Preço (R$):")); panelCadastro.add(txtPreco);
        panelCadastro.add(new JLabel("RAM (GB):")); panelCadastro.add(txtRam);
        panelCadastro.add(new JLabel("Armaz. (GB):")); panelCadastro.add(txtArmaz);
        panelCadastro.add(new JLabel("Cor:")); panelCadastro.add(txtCor);
        panelCadastro.add(new JLabel("Estoque Mín:")); panelCadastro.add(txtEstoqueMin);
        
        JButton btnSalvar = new JButton("Cadastrar Novo");
        panelCadastro.add(btnSalvar);

        add(panelCadastro, BorderLayout.NORTH);

        // --- TABELA DE LISTAGEM (CENTRO) ---
        String[] colunas = {"ID", "Marca", "Modelo", "Preço", "Estoque", "Minimo"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        // --- PAINEL DE AÇÕES (BAIXO) ---
        JPanel panelBotoes = new JPanel();
        JButton btnAddEstoque = new JButton("Adicionar Estoque ao Selecionado");
        panelBotoes.add(btnAddEstoque);
        add(panelBotoes, BorderLayout.SOUTH);

        // --- EVENTOS (CLIQUES) ---
        
        // 1. Botão Salvar
        btnSalvar.addActionListener(e -> {
            try {
                Celular c = new Celular();
                c.setMarca(txtMarca.getText());
                c.setModelo(txtModelo.getText());
                c.setPreco(Double.parseDouble(txtPreco.getText()));
                c.setRam(Integer.parseInt(txtRam.getText()));
                c.setArmazenamento(Integer.parseInt(txtArmaz.getText()));
                c.setCor(txtCor.getText());
                c.setEstoqueMinimo(Integer.parseInt(txtEstoqueMin.getText()));
                c.setQuantidadeEstoque(0); // Começa com 0, adiciona depois

                service.cadastrar(c);
                atualizarTabela();
                limparCampos();
                JOptionPane.showMessageDialog(this, "Celular cadastrado!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Verifique se os números estão corretos.");
            }
        });

        // 2. Botão Adicionar Estoque
        btnAddEstoque.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int id = (int) tabela.getValueAt(linhaSelecionada, 0);
                String qtdStr = JOptionPane.showInputDialog("Quantos itens deseja adicionar?");
                if (qtdStr != null) {
                    int qtd = Integer.parseInt(qtdStr);
                    service.adicionarEstoque(id, qtd);
                    atualizarTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um celular na tabela.");
            }
        });

        atualizarTabela(); // Carrega dados ao abrir
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa tabela
        List<Celular> lista = service.listarTodos();
        for (Celular c : lista) {
            Object[] linha = {
                c.getId(), c.getMarca(), c.getModelo(), 
                c.getPreco(), c.getQuantidadeEstoque(), c.getEstoqueMinimo()
            };
            tableModel.addRow(linha);
        }
    }

    private void limparCampos() {
        txtMarca.setText(""); txtModelo.setText(""); txtPreco.setText("");
        txtRam.setText(""); txtArmaz.setText(""); txtCor.setText(""); txtEstoqueMin.setText("");
    }
}