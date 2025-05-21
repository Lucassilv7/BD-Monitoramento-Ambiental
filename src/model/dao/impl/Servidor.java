package model.dao.impl;

import estruturas.ArvoreAVL;
import estruturas.ListaEncadeada;
import model.dao.RegistroDao;
import model.entidades.Registro;

import java.util.List;

public class Servidor implements RegistroDao{

    private ListaEncadeada<Registro> bancoDados;
    private ArvoreAVL indexador;

    public Servidor() {
        this.bancoDados = new ListaEncadeada<>();
        this.indexador = new ArvoreAVL();
    }

    @Override
    public void cadastrar(Registro registro) {

    }

    @Override
    public Registro buscar(int id) {
        return null;
    }

    @Override
    public List<Registro> listar() {
        return null;
    }

    @Override
    public void remover(Registro registro) {

    }

    @Override
    public void alterar(Registro registro) {

    }

    @Override
    public int quntidadeRegistros() {
        return 0;
    }
}
