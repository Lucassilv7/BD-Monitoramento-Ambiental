package model.dao;

import model.entidades.Registro;

import java.util.List;

public interface RegistroDao {

    void cadastrar(Registro registro);
    Registro buscar(int idRegistro);
    List<Registro> buscarPorDispositivo(int idDispositivo);
    List<Registro> listar();
    void remover(int id);
    void alterar(Registro registro);
    int quntidadeRegistros();
}
