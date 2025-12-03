package br.com.loja.view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("BeyCell - Sistema de Gestão");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela
        setLayout(new GridLayout(5, 1, 10, 10)); // Layout em grade (5 linhas)

        // Título
        JLabel lblTitulo = new JLabel("BEYCELL", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitulo);

        // Botões do Menu
        JButton btnCelulares = new JButton("Gerenciar Celulares");
        JButton btnClientes = new JButton("Gerenciar Clientes");
        JButton btnVendas = new JButton("Nova Venda");
        JButton btnRelatorios = new JButton("Relatórios");

        add(btnCelulares);
        add(btnClientes);
        add(btnVendas);
        add(btnRelatorios);

        // Ações dos Botões
        btnCelulares.addActionListener(e -> new TelaCelular().setVisible(true));
        
        // Vamos criar estas telas nas próximas etapas, por isso deixei comentado ou com aviso
        btnClientes.addActionListener(e -> new TelaCliente().setVisible(true)); 
        btnVendas.addActionListener(e -> new TelaVenda().setVisible(true));
        btnRelatorios.addActionListener(e -> new TelaRelatorios().setVisible(true));
    }

    // O método MAIN que inicia tudo
    public static void main(String[] args) {
        // Estilo visual nativo do sistema operacional (fica mais bonito)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}