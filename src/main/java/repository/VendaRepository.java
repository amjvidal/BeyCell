package src.main.java.repository;

import src.main.java.model.Venda;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.List;

public class VendaRepository extends GenericRepository<Venda> {

    public VendaRepository() {
        super("vendas.dat"); // Define o arquivo onde as vendas serão salvas
    }

    public Venda buscarPorId(int id) {
        return registros.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Venda> buscarPorCliente(int idCliente) {
        return registros.stream()
                .filter(v -> v.getCliente().getId() == idCliente)
                .collect(Collectors.toList());
    }

    public List<Venda> buscarPorData(LocalDate data) {
        return registros.stream()
                .filter(v -> v.getData().equals(data))
                .collect(Collectors.toList());
    }

    public double calcularFaturamentoTotal() {
        return registros.stream()
                .mapToDouble(Venda::getTotal)
                .sum();
    }

    public List<Venda> buscarPorIntervalo(LocalDate inicio, LocalDate fim) {
        return registros.stream()
                .filter(v -> !v.getData().isBefore(inicio) && !v.getData().isAfter(fim))
                .collect(Collectors.toList());
    }
}