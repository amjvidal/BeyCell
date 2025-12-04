package br.com.loja.service;

import br.com.loja.dao.Persistencia;
import br.com.loja.model.Celular;
import br.com.loja.model.Venda;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendaService {

    private List<Venda> vendas;
    private Persistencia persistencia;
    private final String ARQUIVO = "vendas.dat";

    public VendaService() {
        this.persistencia = new Persistencia();
        this.vendas = new ArrayList<>();
        carregarDados();
    }

    //carrega os dados
    private void carregarDados() {
        List<Object> dados = persistencia.carregarArquivo(ARQUIVO);
        for (Object obj : dados) {
            this.vendas.add((Venda) obj);
        }
    }

    //registra a venda e salva no arquivo e gera a fatura
    public void registrarVenda(Venda venda) {
        int novoId = vendas.isEmpty() ? 1 : vendas.get(vendas.size() - 1).getId() + 1;
        venda.setId(novoId);
        
        vendas.add(venda);
        persistencia.salvarArquivo(vendas, ARQUIVO);
        
        gerarFaturaTxt(venda);
    }

    // gera a fatura da venda
    private void gerarFaturaTxt(Venda venda) {
        String nomeArquivo = "Fatura_Venda_" + venda.getId() + ".txt";

        //formata a data
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = venda.getDataHora().format(formatador);

        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println("=== FATURA BEYCELL ===");
            writer.println("Venda: #" + venda.getId());
            writer.println("Data: " + dataFormatada);
            writer.println("Cliente: " + venda.getCliente().getNome());
            writer.println("CPF: " + venda.getCliente().getCpf());
            writer.println("----------------------");
            writer.println("ITENS:");
            for (Celular c : venda.getItensVendidos()) {
                writer.println("- " + c.getMarca() + " " + c.getModelo() + " : R$ " + c.getPreco());
            }
            writer.println("----------------------");
            writer.println("TOTAL: R$ " + venda.getValorTotal());
            System.out.println("Fatura gerada: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gerar fatura: " + e.getMessage());
        }
    }

    //lista todas as vendas
    public List<Venda> listarVendas() {
        return vendas;
    }

    // Metodos para a aba de relatorios

    // total vendido
    public double getTotalVendido() {
        double total = 0;
        for (Venda v : vendas) {
            total += v.getValorTotal();
        }
        return total;
    }

    // valor medio das compras
    public double getTicketMedio() {
        if (vendas.isEmpty()) return 0.0;
        return getTotalVendido() / vendas.size();
    }

    // produtos mais vendidos
    public String getProdutoMaisVendido() {
        Map<String, Integer> contagem = new HashMap<>();
        
        for (Venda v : vendas) {
            for (Celular c : v.getItensVendidos()) {
                String nomeProd = c.getMarca() + " " + c.getModelo();
                contagem.put(nomeProd, contagem.getOrDefault(nomeProd, 0) + 1);
            }
        }
        
        String maisVendido = "Nenhum";
        int max = 0;
        for (Map.Entry<String, Integer> entry : contagem.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                maisVendido = entry.getKey();
            }
        }
        return maisVendido + " (" + max + " un.)";
    }

    // cliente que gastou mais
    public String getMelhorCliente() {
        Map<String, Double> gastos = new HashMap<>();

        for (Venda v : vendas) {
            String nomeCli = v.getCliente().getNome();
            gastos.put(nomeCli, gastos.getOrDefault(nomeCli, 0.0) + v.getValorTotal());
        }

        String melhor = "Nenhum";
        double max = 0.0;
        for (Map.Entry<String, Double> entry : gastos.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                melhor = entry.getKey();
            }
        }
        return melhor + " (R$ " + max + ")";
    }
}