package model.dao.impl;

import model.dao.RegistroDao;
import model.entidades.Registro;

import java.util.List;

public class ServidorProxy implements RegistroDao {

    private Servidor servidorReal;

    public ServidorProxy(Servidor servidorReal) {
        this.servidorReal = servidorReal;
    }

    @Override
    public void cadastrar(Registro registro) {
    }

    @Override
    public Registro buscar(int idRegistro) {
        return servidorReal.buscar(idRegistro);
    }

    @Override
    public List<Registro> buscarPorDispositivo(int Dispositivo) {
        return servidorReal.buscarPorDispositivo(Dispositivo);
    }

    @Override
    public List<Registro> listar() {
        return servidorReal.listar();
    }

    @Override
    public void remover(int id) {
    }
    @Override
    public void alterar(Registro registro) {
        servidorReal.alterar(registro);
    }

    @Override
    public int quntidadeRegistros() {
        return servidorReal.quntidadeRegistros();
    }
}
