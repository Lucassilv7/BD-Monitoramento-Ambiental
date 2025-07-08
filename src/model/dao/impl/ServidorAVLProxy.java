package model.dao.impl;

import log.Logger;
import model.dao.RegistroDao;
import model.entidades.MicroControlador;
import model.entidades.Registro;

import java.util.List;

public class ServidorAVLProxy <E> implements RegistroDao<E> {

    private ServidorAVL servidorAVLReal;
    private Logger logger;

    public ServidorAVLProxy(ServidorAVL servidorAVLReal, Logger logger) {
        this.servidorAVLReal = servidorAVLReal;
        this.logger = logger;
    }

    public void finalizar(){
        logger.fechar();
    }

    @Override
    public void cadastrar(Registro registro, MicroControlador dispositivo) {
        servidorAVLReal.cadastrar(registro, null);
        logger.registrar("Inserção | Chave: " + registro.getIdRegistro() +
                " | Altura Atual: " + servidorAVLReal.getAlturaArvore() +
                " | Rotação: " + servidorAVLReal.getUltimaRotacao());
    }

    @Override
    public Registro buscar(int idRegistro) {
        return servidorAVLReal.buscar(idRegistro);
    }

    @Override
    public List<E> buscarPorDispositivo(int Dispositivo) {
        return servidorAVLReal.buscarPorDispositivo(Dispositivo);
    }

    @Override
    public List<Registro> listar() {
        return servidorAVLReal.listar();
    }

    @Override
    public void remover(int id) {
        servidorAVLReal.remover(id);
        logger.registrar("Remoção | Chave: " + id +
                " | Altura Atual: " + servidorAVLReal.getAlturaArvore() +
                " | Rotação: " + servidorAVLReal.getUltimaRotacao());
    }
    @Override
    public void alterar(Registro registro, MicroControlador dispositivo) {
        servidorAVLReal.alterar(registro, null);
    }

    @Override
    public int quntidadeRegistros() {
        return servidorAVLReal.quntidadeRegistros();
    }
}
