package src.main.java.repository;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericRepository<T> {
protected List<T> registros = new ArrayList<>();


public void salvar(T obj) {
registros.add(obj);
}


public List<T> listarTodos() {
return new ArrayList<>(registros);
}


public void remover(T obj) {
registros.remove(obj);
}


public void removerPorIndice(int index) {
if (index >= 0 && index < registros.size()) {
registros.remove(index);
}
}


public void limpar() {
registros.clear();
}


public T buscarPorIndice(int index) {
if (index >= 0 && index < registros.size()) {
return registros.get(index);
}
return null;
}


public int tamanho() {
return registros.size();
}
}
