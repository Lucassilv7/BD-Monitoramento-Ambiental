package model.dao;

import model.entidades.MicroControlador;
import model.entidades.Registro;

import java.util.List;

public interface RegistroDao <E>{

    void cadastrar(Registro registro, MicroControlador dispositivo);
    Registro buscar(int idRegistro);
    List<E> buscarPorDispositivo(int idDispositivo);
    List<Registro> listar();
    void remover(int id);
    void alterar(Registro registro, MicroControlador dispositivo);
    int quntidadeRegistros();
}
