package model.dao;

import model.entidades.Registro;

import java.util.List;

public interface RegistroDao {

    void cadastrar(Registro registro);

    Registro buscar(int id);
    Registro buscarPorDispositivo(String dispositivo);
    List<Registro> listar();
    void remover(int id);
    void alterar(Registro registro);
    int quntidadeRegistros();
}
