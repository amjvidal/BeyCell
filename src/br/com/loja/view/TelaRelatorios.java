package br.com.loja.view;

import br.com.loja.model.Celular;
import br.com.loja.service.CelularService;
import br.com.loja.service.VendaService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaRelatorios extends JPanel {

    private JTextArea areaTexto;
    private VendaService vendaService;
    private CelularService celularService;

    public TelaRelatorios(ActionListener acaoVoltar) {
        vendaService = new VendaService();
        celularService = new CelularService();

        setLayout(new BorderLayout());

        //topo
        JPanel panelTopo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnVoltar = new JButton("<< Voltar");
        btnVoltar.addActionListener(acaoVoltar);
        panelTopo.add(btnVoltar);
        add(panelTopo, BorderLayout.NORTH);

        //area do texto
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);
        
        carregarRelatorio();
    }

    public void carregarRelatorio() {
        // recarregando os services
        vendaService = new VendaService();
        celularService = new CelularService();

        //relatorios girls
        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO GERAL BEYCELL ===\n\n");
        sb.append("1. Faturamento Total: R$ ").append(String.format("%.2f", vendaService.getTotalVendido())).append("\n");
        sb.append("2. Ticket Médio: R$ ").append(String.format("%.2f", vendaService.getTicketMedio())).append("\n");
        sb.append("3. Produto Mais Vendido: ").append(vendaService.getProdutoMaisVendido()).append("\n");
        sb.append("4. Melhor Cliente: ").append(vendaService.getMelhorCliente()).append("\n\n");

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