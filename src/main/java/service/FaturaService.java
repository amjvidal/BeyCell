package src.main.java.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import src.main.java.model.ItemVenda;
import src.main.java.model.Venda;


public class FaturaService {


public static void gerarFatura(Venda venda) throws IOException {
String nomeArquivo = "fatura_" + venda.getId() + ".txt";
File arquivo = new File(nomeArquivo);


try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {


writer.write("==============================\n");
writer.write(" LOJA DE CELULARES \n");
writer.write(" FATURA DE VENDA \n");
writer.write("==============================\n\n");


writer.write("ID da Venda: " + venda.getId() + "\n");
writer.write("Data: " + venda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");


writer.write("\nCliente:\n");
writer.write("Nome: " + venda.getCliente().getNome() + "\n");
writer.write("CPF: " + venda.getCliente().getCpf() + "\n\n");


writer.write("Itens da Venda:\n");
writer.write("---------------------------------------------\n");


for (ItemVenda item : venda.getItens()) {
writer.write("Produto: " + item.getProduto().getNome() + "\n");
writer.write("Marca: " + item.getProduto().getMarca() + "\n");
writer.write("Modelo: " + item.getProduto().getModelo() + "\n");
writer.write("Quantidade: " + item.getQuantidade() + "\n");
writer.write(String.format("Preço Unitário: R$ %.2f\n", item.getProduto().getPreco()));
writer.write(String.format("Subtotal: R$ %.2f\n", item.getSubtotal()));
writer.write("---------------------------------------------\n");
}


writer.write(String.format("TOTAL DA VENDA: R$ %.2f\n", venda.getTotal()));
writer.write("==============================\n");
}
}
}
