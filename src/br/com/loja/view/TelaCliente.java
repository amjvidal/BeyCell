package br.com.loja.view;

import br.com.loja.model.Cliente;
import br.com.loja.service.ClienteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaCliente extends JFrame {

    private ClienteService service;
    private DefaultTableModel tableModel;
    private JTextField txtNome, txtCpf, txtTelefone, txtEndereco;

    public TelaCliente() {
        this.service = new ClienteService();
        setTitle("Gerenciar Clientes");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- CADASTRO ---
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
        
        add(panelForm, BorderLayout.NORTH);

        // --- LISTAGEM ---
        String[] colunas = {"CPF", "Nome", "Telefone"};
        tableModel = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // --- AÇÃO SALVAR ---
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

        atualizarTabela();
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        List<Cliente> clientes = service.listarTodos();
        for (Cliente c : clientes) {
            tableModel.addRow(new Object[]{c.getCpf(), c.getNome(), c.getTelefone()});
        }
    }
}