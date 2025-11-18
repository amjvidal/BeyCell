package src.main.java.controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import src.main.java.model.ItemVenda;
import src.main.java.model.Produto;
import src.main.java.model.Venda;
import src.main.java.repository.ProdutoRepository;
import src.main.java.repository.VendaRepository;
import src.main.java.service.FaturaService;

public class VendaController {
private VendaRepository vendaRepository = new VendaRepository();
private ProdutoRepository produtoRepository = new ProdutoRepository();


public void registrarVenda(Venda venda) throws Exception {
validarVenda(venda);


// Atualiza estoque
for (ItemVenda item : venda.getItens()) {
Produto p = produtoRepository.buscarPorId(item.getProduto().getId());
int novoEstoque = p.getEstoqueAtual() - item.getQuantidade();


if (novoEstoque < 0) throw new Exception("Estoque insuficiente para " + p.getNome());


produtoRepository.atualizarEstoque(p.getId(), novoEstoque);


// Alerta estoque mínimo
if (novoEstoque <= p.getEstoqueMinimo()) {
System.out.println("⚠ ALERTA: Estoque baixo após venda: " + p.getNome());
}
}


vendaRepository.salvar(venda);


// Gera fatura automática
FaturaService.gerarFatura(venda);
}


private void validarVenda(Venda venda) throws Exception {
if (venda.getItens().isEmpty()) throw new Exception("Venda sem itens");
if (venda.getCliente() == null) throw new Exception("Cliente inválido");
}


public Venda buscarPorId(int id) throws Exception {
Venda v = vendaRepository.buscarPorId(id);
if (v == null) throw new Exception("Venda não encontrada");
return v;
}


public List<Venda> buscarPorCliente(int idCliente) {
return vendaRepository.buscarPorCliente(idCliente);
}


public List<Venda> buscarPorData(LocalDate data) {
return vendaRepository.buscarPorData(data);
}


public List<Venda> buscarPorIntervalo(LocalDate inicio, LocalDate fim) {
return vendaRepository.buscarPorIntervalo(inicio, fim);
}


public double faturamentoTotal() {
return vendaRepository.calcularFaturamentoTotal();
}


public Venda getVendaComMaiorTotal() {
return vendaRepository.listarTodos()
.stream()
.max(Comparator.comparing(Venda::getTotal))
.orElse(null);
}


public List<Venda> listarVendas() {
return vendaRepository.listarTodos();
}
}
