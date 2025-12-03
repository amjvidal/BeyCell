package br.com.loja.view;

import br.com.loja.model.Celular;
import br.com.loja.service.CelularService;
import br.com.loja.service.VendaService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaRelatorios extends JFrame {

    public TelaRelatorios() {
        VendaService vendaService = new VendaService();
        CelularService celularService = new CelularService();

        setTitle("Relatórios e Estatísticas");
        setSize(600, 500);
        setLocationRelativeTo(null);

        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(areaTexto));

        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO GERAL BEYCELL ===\n\n");

        // 1. Total Vendido
        sb.append("1. Faturamento Total: R$ ").append(vendaService.getTotalVendido()).append("\n");
        
        // 2. Ticket Médio
        sb.append("2. Ticket Médio: R$ ").append(String.format("%.2f", vendaService.getTicketMedio())).append("\n");
        
        // 3. Produto Mais Vendido
        sb.append("3. Produto Mais Vendido: ").append(vendaService.getProdutoMaisVendido()).append("\n");
        
        // 4. Melhor Cliente
        sb.append("4. Melhor Cliente: ").append(vendaService.getMelhorCliente()).append("\n\n");

        // 5. Estoque Crítico (Alerta) [cite: 35]
        sb.append("=== ALERTA DE ESTOQUE BAIXO ===\n");
        List<Celular> criticos = celularService.buscarEstoqueCritico();
        if (criticos.isEmpty()) {
            sb.append("Nenhum produto com estoque crítico.\n");
        } else {
            for (Celular c : criticos) {
                sb.append("- ").append(c.getMarca()).append(" ").append(c.getModelo())
                  .append(" (Atual: ").append(c.getQuantidadeEstoque())
                  .append(" / Mín: ").append(c.getEstoqueMinimo()).append(")\n");
            }
        }

        areaTexto.setText(sb.toString());
    }
}