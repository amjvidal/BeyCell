package src.main.java.ui; // Pacote ajustado para 'ui'

import src.main.java.model.Cliente;
import src.main.java.service.ClienteService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteView extends JPanel {
    private ClienteService clienteService;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    
    private JTextField txtId, txtNome, txtCpf, txtEndereco, txtTelefone, txtEmail;

    public ClienteView() {
        this.clienteService = new ClienteService();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabela
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Email"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tabela = new JTable(modeloTabela);
        atualizarTabela();
        
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Formulário
        JPanel painelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));

        txtId = new JTextField(); txtId.setEditable(false);
        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtEndereco = new JTextField();
        txtTelefone = new JTextField();
        txtEmail = new JTextField();

        painelForm.add(new JLabel("ID:")); painelForm.add(txtId);
        painelForm.add(new JLabel("Nome:")); painelForm.add(txtNome);
        painelForm.add(new JLabel("CPF:")); painelForm.add(txtCpf);
        painelForm.add(new JLabel("Endereço:")); painelForm.add(txtEndereco);
        painelForm.add(new JLabel("Telefone:")); painelForm.add(txtTelefone);
        painelForm.add(new JLabel("Email:")); painelForm.add(txtEmail);

        // Botões
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

        // Ações
        btnSalvar.addActionListener(e -> salvarCliente());
        btnRemover.addActionListener(e -> removerCliente());
        btnLimpar.addActionListener(e -> limparFormulario());
        
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() != -1) {
                preencherFormulario(tabela.getSelectedRow());
            }
        });
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Cliente> clientes = clienteService.listarTodos();
        for (Cliente c : clientes) {
            modeloTabela.addRow(new Object[]{
                c.getId(), c.getNome(), c.getCpf(), c.getTelefone(), c.getEmail()
            });
        }
    }

    private void salvarCliente() {
        try {
            int id = txtId.getText().isEmpty() ? (int)(System.currentTimeMillis() / 100000) : Integer.parseInt(txtId.getText());
            
            Cliente cliente = new Cliente(
                id,
                txtNome.getText(),
                txtCpf.getText(),
                txtEndereco.getText(),
                txtTelefone.getText(),
                txtEmail.getText()
            );

            clienteService.cadastrarCliente(cliente); 
            JOptionPane.showMessageDialog(this, "Cliente salvo!");
            limparFormulario();
            atualizarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerCliente() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            return;
        }

        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        try {
            clienteService.removerCliente(id);
            JOptionPane.showMessageDialog(this, "Cliente removido!");
            atualizarTabela();
            limparFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    private void preencherFormulario(int linha) {
        txtId.setText(tabela.getValueAt(linha, 0).toString());
        txtNome.setText(tabela.getValueAt(linha, 1).toString());
        txtCpf.setText(tabela.getValueAt(linha, 2).toString());
        
        Cliente c = clienteService.buscarPorId((int) tabela.getValueAt(linha, 0));
        if(c != null) {
            txtEndereco.setText(c.getEndereco());
            txtTelefone.setText(c.getTelefone());
            txtEmail.setText(c.getEmail());
        }
    }

    private void limparFormulario() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtEndereco.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        tabela.clearSelection();
    }
}