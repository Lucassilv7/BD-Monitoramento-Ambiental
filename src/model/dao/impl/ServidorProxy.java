package model.dao.impl;

import log.Logger;
import model.dao.RegistroDao;
import model.entidades.Registro;

import java.util.List;

public class ServidorProxy implements RegistroDao {

    private Servidor servidorReal;
    private Logger logger;

    public ServidorProxy(Servidor servidorReal, Logger logger) {
        this.servidorReal = servidorReal;
        this.logger = logger;
    }

    public void finalizar(){
        logger.fechar();
    }

    @Override
    public void cadastrar(Registro registro) {
        servidorReal.cadastrar(registro);
        logger.registrar("Inserção | Chave: " + registro.getIdRegistro() +
                " | Altura Atual: " + servidorReal.getAlturaArvore() +
                " | Rotação: " + servidorReal.getUltimaRotacao());
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
        servidorReal.remover(id);
        logger.registrar("Remoção | Chave: " + id +
                " | Altura Atual: " + servidorReal.getAlturaArvore() +
                " | Rotação: " + servidorReal.getUltimaRotacao());
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
