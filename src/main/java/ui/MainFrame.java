package src.main.java.ui; // Pacote ajustado para 'ui'

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sistema de Loja de Celulares");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Vendas", new VendaView());
        tabbedPane.addTab("Produtos", new ProdutoView());
        tabbedPane.addTab("Clientes", new ClienteView());

        tabbedPane.addChangeListener(e -> {
            int index = tabbedPane.getSelectedIndex();
            if (tabbedPane.getComponentAt(index) instanceof VendaView) {
                ((VendaView) tabbedPane.getComponentAt(index)).atualizarCombos();
            }
        });

        add(tabbedPane);
    }
}