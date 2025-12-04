package br.com.loja.view;

import br.com.loja.model.Celular;
import br.com.loja.service.CelularService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaCelular extends JPanel {

    private CelularService service;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private JTextField txtMarca, txtModelo, txtPreco, txtRam, txtArmaz, txtCor, txtEstoqueMin;

    //construtor só recebe botao voltar
    public TelaCelular(ActionListener acaoVoltar) {
        this.service = new CelularService();
        setLayout(new BorderLayout());

        //topo da tela
        JPanel panelTopo = new JPanel(new BorderLayout());
        JButton btnVoltar = new JButton("<< Voltar");
        btnVoltar.addActionListener(acaoVoltar); // linka o botão pra açao voltar
        JLabel lblTitulo = new JLabel("Gerenciar Celulares", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        panelTopo.add(btnVoltar, BorderLayout.WEST);
        panelTopo.add(lblTitulo, BorderLayout.CENTER);
        add(panelTopo, BorderLayout.NORTH);

        // Centro com formulario e tabela de produtos
        JPanel panelCentro = new JPanel(new BorderLayout());
        
        // formulario pra adicionar celular
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
        
        panelCentro.add(panelCadastro, BorderLayout.NORTH);

        //tabela de produtos
        String[] colunas = {"ID", "Marca", "Modelo", "Preço", "Estoque", "Minimo"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabela = new JTable(tableModel);
        panelCentro.add(new JScrollPane(tabela), BorderLayout.CENTER);
        
        add(panelCentro, BorderLayout.CENTER);

        //parte de baixo com os botões
        JPanel panelBotoes = new JPanel();
        JButton btnAddEstoque = new JButton("Adicionar Estoque");
        JButton btnRemover = new JButton("Excluir Celular");
        btnRemover.setForeground(Color.RED);

        panelBotoes.add(btnAddEstoque);
        panelBotoes.add(btnRemover);
        add(panelBotoes, BorderLayout.SOUTH);

        // Todos os Eventos: 

        // Evento para salvar
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
                c.setQuantidadeEstoque(0); 

                service.cadastrar(c);
                atualizarTabela();
                limparCampos();
                JOptionPane.showMessageDialog(this, "Celular cadastrado!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Verifique os números.");
            }
        });


        // evento para adicionar estoque ao produto
        btnAddEstoque.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                int id = (int) tabela.getValueAt(linha, 0);
                String qtdStr = JOptionPane.showInputDialog("Quantidade a adicionar?");
                if (qtdStr != null) {
                    try {
                        service.adicionarEstoque(id, Integer.parseInt(qtdStr));
                        atualizarTabela();
                    } catch(Exception ex) { JOptionPane.showMessageDialog(this, "Número inválido"); }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um celular.");
            }
        });

        //evento para remover produto
        btnRemover.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                int id = (int) tabela.getValueAt(linha, 0);
                if (JOptionPane.showConfirmDialog(this, "Confirmar exclusão?", "Excluir", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    service.remover(id);
                    atualizarTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um celular.");
            }
        });

        atualizarTabela();
    }

    // atualizar tabela ao modificar ela
    private void atualizarTabela() {
        tableModel.setRowCount(0); 
        List<Celular> lista = service.listarTodos();
        for (Celular c : lista) {
            tableModel.addRow(new Object[]{c.getId(), c.getMarca(), c.getModelo(), c.getPreco(), c.getQuantidadeEstoque(), c.getEstoqueMinimo()});
        }
    }

    //  limpar os campos, usado ao adicionar produtos
    private void limparCampos() {
        txtMarca.setText(""); txtModelo.setText(""); txtPreco.setText("");
        txtRam.setText(""); txtArmaz.setText(""); txtCor.setText(""); txtEstoqueMin.setText("");
    }
}