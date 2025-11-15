package src.main.java.service;

import src.main.java.model.Cliente;
import src.main.java.model.ItemVenda;
import src.main.java.model.Venda;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class Fatura {

    /**
     * Gera um arquivo de fatura em formato TXT.
     */
    public static void gerarFatura(Venda venda) {
        String nomeArquivo = "fatura_" + venda.getId() + ".txt";

        try (FileWriter writer = new FileWriter(nomeArquivo)) {

            writer.write("===============================================\n");
            writer.write("                 LOJA DE CELULARES            \n");
            writer.write("                  *** F A T U R A ***         \n");
            writer.write("===============================================\n\n");

            // Dados da venda
            writer.write("Fatura nº: " + venda.getId() + "\n");
            writer.write("Data: " + venda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n\n");

            // Dados do cliente
            Cliente c = venda.getCliente();
            writer.write("CLIENTE:\n");
            writer.write("Nome: " + c.getNome() + "\n");
            writer.write("Email: " + c.getEmail() + "\n");
            writer.write("Telefone: " + c.getTelefone() + "\n\n");

            // Itens
            writer.write("ITENS DA COMPRA:\n");
            writer.write("-----------------------------------------------\n");

            for (ItemVenda item : venda.getItens()) {
                writer.write(item.getProduto().getNome() + " (" + item.getProduto().getModelo() + ")\n");
                writer.write("Quantidade: " + item.getQuantidade() + "\n");
                writer.write(String.format("Preço Unitário: R$ %.2f\n", item.getProduto().getPreco()));
                writer.write(String.format("Subtotal: R$ %.2f\n", item.getSubtotal()));
                writer.write("-----------------------------------------------\n");
            }

            // Total
            writer.write(String.format("\nTOTAL DA VENDA: R$ %.2f\n", venda.getTotal()));
            writer.write("\n===============================================\n");
            writer.write(" Obrigado pela preferência! Volte sempre! 😊 \n");
            writer.write("===============================================\n");

            System.out.println("Fatura gerada com sucesso: " + nomeArquivo);

        } catch (IOException e) {
            System.out.println("Erro ao gerar fatura: " + e.getMessage());
        }
    }
}
