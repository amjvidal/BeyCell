package br.com.loja.view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MenuPrincipal() {
        setTitle("BeyCell - Sistema Integrado");
        setSize(850, 800); // Aumentei a altura para caber a logo triplicada
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
        // Mudei o Layout para BorderLayout para a logo não deformar os botões
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 100, 50, 100)); 

        // --- 1. PAINEL DO TOPO (LOGO + TÍTULO) ---
        JPanel panelTopo = new JPanel();
        panelTopo.setLayout(new BoxLayout(panelTopo, BoxLayout.Y_AXIS)); // Empilha verticalmente

        JLabel lblLogo = new JLabel();
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza a imagem
        
        try {
            ImageIcon imagemOriginal = new ImageIcon(getClass().getResource("logo.png"));
            // Redimensiona para 360x360 pixels (TRIPLO do anterior)
            Image imagemRedimensionada = imagemOriginal.getImage().getScaledInstance(360, 360, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(imagemRedimensionada));
        } catch (Exception e) {
            lblLogo.setText("[BeyCell Logo]");
        }

        JLabel lblTitulo = new JLabel("BEYCELL", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o texto
        
        // Adiciona um espaçamento entre a logo e o título
        panelTopo.add(lblLogo);
        panelTopo.add(Box.createRigidArea(new Dimension(0, 20))); 
        panelTopo.add(lblTitulo);
        panelTopo.add(Box.createRigidArea(new Dimension(0, 30))); // Espaço antes dos botões

        // --- 2. PAINEL DOS BOTÕES ---
        JPanel panelBotoes = new JPanel(new GridLayout(4, 1, 10, 10)); // Grade só para os botões

        JButton btnCelulares = new JButton("Gerenciar Celulares");
        JButton btnClientes = new JButton("Gerenciar Clientes");
        JButton btnVendas = new JButton("Nova Venda");
        JButton btnRelatorios = new JButton("Relatórios");

        // Ações para trocar de tela
        btnCelulares.addActionListener(e -> cardLayout.show(mainPanel, "CELULAR"));
        btnClientes.addActionListener(e -> cardLayout.show(mainPanel, "CLIENTE"));
        btnVendas.addActionListener(e -> {
            cardLayout.show(mainPanel, "VENDA");
            Component[] comps = mainPanel.getComponents();
            for(Component c : comps) {
                if(c instanceof TelaVenda) ((TelaVenda)c).atualizarDados(); 
            }
        });
        btnRelatorios.addActionListener(e -> {
            cardLayout.show(mainPanel, "RELATORIOS");
            Component[] comps = mainPanel.getComponents();
            for(Component c : comps) {
                if(c instanceof TelaRelatorios) ((TelaRelatorios)c).carregarRelatorio(); 
            }
        });

        panelBotoes.add(btnCelulares);
        panelBotoes.add(btnClientes);
        panelBotoes.add(btnVendas);
        panelBotoes.add(btnRelatorios);

        // Junta tudo no painel principal
        panel.add(panelTopo, BorderLayout.NORTH);
        panel.add(panelBotoes, BorderLayout.CENTER);

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