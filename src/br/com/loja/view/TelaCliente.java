package br.com.loja.view;

import br.com.loja.model.Cliente;
import br.com.loja.service.ClienteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaCliente extends JPanel {

    private ClienteService service;
    private DefaultTableModel tableModel;
    private JTable tabela;
    private JTextField txtNome, txtCpf, txtTelefone, txtEndereco;

    //construtor so recebe a ação voltar
    public TelaCliente(ActionListener acaoVoltar) {
        this.service = new ClienteService();
        setLayout(new BorderLayout());

        // parte de cima
        JPanel panelTopo = new JPanel(new BorderLayout());
        JButton btnVoltar = new JButton("<< Voltar");
        btnVoltar.addActionListener(acaoVoltar);
        JLabel lblTitulo = new JLabel("Gerenciar Clientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        
        panelTopo.add(btnVoltar, BorderLayout.WEST);
        panelTopo.add(lblTitulo, BorderLayout.CENTER);
        add(panelTopo, BorderLayout.NORTH);

        // meio da tela com formulario e tabela
        JPanel panelCentro = new JPanel(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtTelefone = new JTextField();
        txtEndereco = new JTextField();

        panelForm.add(new JLabel("Nome:")); panelForm.add(txtNome);
        panelForm.add(new JLabel("CPF:")); panelForm.add(txtCpf);
        panelForm.add(new JLabel("Telefone:")); panelForm.add(txtTelefone);
        panelForm.add(new JLabel("Endereço:")); panelForm.add(txtEndereco);
        JButton btnSalvar = new JButton("Salvar Cliente");
        panelForm.add(btnSalvar);
        
        panelCentro.add(panelForm, BorderLayout.NORTH);

        String[] colunas = {"CPF", "Nome", "Telefone"};
        tableModel = new DefaultTableModel(colunas, 0);
        tabela = new JTable(tableModel);
        panelCentro.add(new JScrollPane(tabela), BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        // botçoes da parte inferior
        JPanel panelBotoes = new JPanel();
        JButton btnRemover = new JButton("Excluir Cliente");
        btnRemover.setForeground(Color.RED);
        panelBotoes.add(btnRemover);
        add(panelBotoes, BorderLayout.SOUTH);

        //Tdoso os eventos:

        //adicionar o cliente
        btnSalvar.addActionListener(e -> {
            if (txtCpf.getText().isEmpty() || txtNome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha Nome e CPF!");
                return;
            }
            Cliente c = new Cliente(txtCpf.getText(), txtNome.getText(), txtTelefone.getText(), txtEndereco.getText());
            service.cadastrar(c);
            atualizarTabela();
            txtNome.setText(""); txtCpf.setText(""); txtTelefone.setText(""); txtEndereco.setText("");
            JOptionPane.showMessageDialog(this, "Cliente Salvo!");
        });

        //remover cliente da loja
        btnRemover.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                String cpf = (String) tabela.getValueAt(linha, 0);
                if (JOptionPane.showConfirmDialog(this, "Excluir cliente?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    service.remover(cpf);
                    atualizarTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            }
        });

        atualizarTabela();
    }



    // para atualizar a tabela quando precisar
    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Cliente> clientes = service.listarTodos();
        for (Cliente c : clientes) {
            tableModel.addRow(new Object[]{c.getCpf(), c.getNome(), c.getTelefone()});
        }
    }
}