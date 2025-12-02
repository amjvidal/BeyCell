package src.main.java.service;

import src.main.java.model.ItemVenda;
import src.main.java.model.Venda;
import src.main.java.repository.VendaRepository;

import java.io.IOException;
import java.util.List;

public class VendaService {
    private VendaRepository vendaRepository;
    private EstoqueService estoqueService;

    public VendaService() {
        this.vendaRepository = new VendaRepository();
        this.estoqueService = new EstoqueService();
    }

    public String realizarVenda(Venda venda) throws Exception {
        // 1. Validações básicas
        if (venda.getCliente() == null) throw new Exception("A venda deve ter um cliente vinculado.");
        if (venda.getItens().isEmpty()) throw new Exception("O carrinho de compras está vazio.");

        // 2. Verifica estoque de todos os itens antes de concretizar
        for (ItemVenda item : venda.getItens()) {
            if (!estoqueService.temEstoque(item.getProduto(), item.getQuantidade())) {
                throw new Exception("Estoque insuficiente para o produto: " + item.getProduto().getNome());
            }
        }

        // 3. Efetiva a baixa de estoque e coleta alertas
        StringBuilder alertas = new StringBuilder();
        for (ItemVenda item : venda.getItens()) {
            boolean isCritico = estoqueService.baixarEstoque(item.getProduto().getId(), item.getQuantidade());
            if (isCritico) {
                alertas.append("\n⚠ ALERTA: Estoque baixo para ").append(item.getProduto().getNome());
            }
        }

        // 4. Salva a venda
        vendaRepository.salvar(venda);

        // 5. Gera a fatura (arquivo txt)
        try {
            FaturaService.gerarFatura(venda);
        } catch (IOException e) {
            System.err.println("Erro ao gerar fatura física: " + e.getMessage());
            // Não impede a venda, apenas loga o erro
        }

        return "Venda realizada com sucesso!" + alertas.toString();
    }

    public List<Venda> listarVendas() {
        return vendaRepository.listarTodos();
    }

    public double obterFaturamentoTotal() {
        return vendaRepository.calcularFaturamentoTotal();
    }
}