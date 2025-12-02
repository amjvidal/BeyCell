package src;

import src.main.java.ui.MainFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Tenta aplicar o visual nativo do sistema operacional (Windows/Mac/Linux)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Se falhar, usa o padrão do Java, sem problemas
            e.printStackTrace();
        }

        // Inicia a thread da interface gráfica
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}