package model.dao;

import model.entidades.Registro;

import java.util.List;

public interface RegistroDao {

    void cadastrar(Registro registro);

    Registro buscar(int id);
    List<Registro> listar();
    void remover(Registro registro);
    void alterar(Registro registro);
    int quntidadeRegistros();
}
