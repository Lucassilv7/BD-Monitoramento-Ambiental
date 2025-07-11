package model.dao.impl;

import log.Logger;
import model.dao.RegistroDao;
import model.entidades.MicroControlador;
import model.entidades.Registro;

import java.util.List;

public class ServidorHashProxy implements RegistroDao {

    private ServidorHash servidorHashReal;
    private Logger logger;

    public ServidorHashProxy(ServidorHash servidorHashReal, Logger logger) {
        this.servidorHashReal = servidorHashReal;
        this.logger = logger;
    }

    public void finalizar(){
        logger.fechar();
    }

    @Override
    public void cadastrar(Registro registro, MicroControlador dispositivo) {
        servidorHashReal.cadastrar(registro, dispositivo);
        int[] infosReg = servidorHashReal.infosReg();
        int[] infosDis = servidorHashReal.infosDis();
        logger.registrar("Tamanho do BD: " + servidorHashReal.quntidadeRegistros() +
                "\n === Informações Hash do Registro ===\n" +
                "Tamanho Atual: " + infosReg[0] +
                " - Redimensionamentos: " + infosReg[1] +
                " - Colisões: " + infosReg[2] +
                " - Fator de Carga: " + servidorHashReal.getCargaReg() +
                "\n === Informações Hash do Dispositivo ===\n" +
                "Tamanho Atual: " + infosDis[0] +
                " - Redimensionamentos: " + infosDis[1] +
                " - Colisões: " + infosDis[2] +
                " - Fator de Carga: " + servidorHashReal.getCargaDis());
    }

    @Override
    public Registro buscar(int idRegistro) {
        return servidorHashReal.buscar(idRegistro);
    }

    @Override
    public List buscarPorDispositivo(int idDispositivo) {
        return null;
    }

    @Override
    public List<Registro> listar() {
        return servidorHashReal.listar();
    }

    @Override
    public void remover(int id) {
        servidorHashReal.remover(id);
        int[] infosReg = servidorHashReal.infosReg();
        int[] infosDis = servidorHashReal.infosDis();
        logger.registrar("Tamanho do BD: " + servidorHashReal.quntidadeRegistros() +
                "\n === Informações Hash do Registro ===\n" +
                "Tamanho Atual: " + infosReg[0] +
                " - Redimensionamentos: " + infosReg[1] +
                " - Colisões: " + infosReg[2] +
                " - Fator de Carga: " + servidorHashReal.getCargaReg() +
                "\n === Informações Hash do Dispositivo ===\n" +
                "Tamanho Atual: " + infosDis[0] +
                " - Redimensionamentos: " + infosDis[1] +
                " - Colisões: " + infosDis[2] +
                " - Fator de Carga: " + servidorHashReal.getCargaDis());
    }

    @Override
    public void alterar(Registro registro, MicroControlador dispositivo) {
        servidorHashReal.alterar(registro, dispositivo);
    }

    @Override
    public int quntidadeRegistros() {
        return servidorHashReal.quntidadeRegistros();
    }
}
