package br.com.loja.view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MenuPrincipal() {
        setTitle("BeyCell - Sistema Integrado");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //criando o gerenciador de telas
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        //criando as telas
        
        //tela menu
        JPanel panelMenu = criarPainelMenu();
        
        // telas do sistema da loja, todas recebem a ação para voltar pro menu
        TelaCelular telaCelular = new TelaCelular(e -> cardLayout.show(mainPanel, "MENU"));
        TelaCliente telaCliente = new TelaCliente(e -> cardLayout.show(mainPanel, "MENU"));
        TelaVenda telaVenda = new TelaVenda(e -> cardLayout.show(mainPanel, "MENU"));
        TelaRelatorios telaRelatorios = new TelaRelatorios(e -> cardLayout.show(mainPanel, "MENU"));

        //colocando as telas no painel
        mainPanel.add(panelMenu, "MENU");
        mainPanel.add(telaCelular, "CELULAR");
        mainPanel.add(telaCliente, "CLIENTE");
        mainPanel.add(telaVenda, "VENDA");
        mainPanel.add(telaRelatorios, "RELATORIOS");

        add(mainPanel);
    }

    private JPanel criarPainelMenu() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100)); // Margens

        JLabel lblTitulo = new JLabel("BEYCELL", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        
        JButton btnCelulares = new JButton("Gerenciar Celulares");
        JButton btnClientes = new JButton("Gerenciar Clientes");
        JButton btnVendas = new JButton("Nova Venda");
        JButton btnRelatorios = new JButton("Relatórios");

        // Ações para trocar de tela
        btnCelulares.addActionListener(e -> cardLayout.show(mainPanel, "CELULAR"));
        btnClientes.addActionListener(e -> cardLayout.show(mainPanel, "CLIENTE"));
        btnVendas.addActionListener(e -> {
            cardLayout.show(mainPanel, "VENDA");
            // Truque: Força a atualização dos combos box quando abre a tela de venda
            Component[] comps = mainPanel.getComponents();
            for(Component c : comps) {
                if(c instanceof TelaVenda) ((TelaVenda)c).atualizarDados(); 
            }
        });
        btnRelatorios.addActionListener(e -> {
            cardLayout.show(mainPanel, "RELATORIOS");
             // Atualiza relatórios ao abrir
            Component[] comps = mainPanel.getComponents();
            for(Component c : comps) {
                if(c instanceof TelaRelatorios) ((TelaRelatorios)c).carregarRelatorio(); 
            }
        });

        panel.add(lblTitulo);
        panel.add(btnCelulares);
        panel.add(btnClientes);
        panel.add(btnVendas);
        panel.add(btnRelatorios);

        return panel;
    }

    // A main né pai
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }

        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}