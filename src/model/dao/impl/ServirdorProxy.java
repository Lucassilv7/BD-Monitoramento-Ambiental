package model.dao.impl;

import model.dao.RegistroDao;
import model.entidades.Registro;

import java.util.List;

public class ServirdorProxy implements RegistroDao {

    private Servidor servidorReal;

    public ServirdorProxy(Servidor servidorReal) {
        this.servidorReal = servidorReal;
    }

    @Override
    public void cadastrar(Registro registro) {

    }

    @Override
    public Registro buscar(int id) {
        return null;
    }

    @Override
    public Registro buscarPorDispositivo(String dispositivo) {
        return null;
    }

    @Override
    public List<Registro> listar() {
        return null;
    }

    @Override
    public void remover(int id) {

    }

    @Override
    public void alterar(Registro registro) {

    }

    @Override
    public int quntidadeRegistros() {
        return 0;
    }
}
