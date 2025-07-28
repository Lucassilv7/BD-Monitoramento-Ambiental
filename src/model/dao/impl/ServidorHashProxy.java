package model.dao.impl;

import estruturas.Huffman;
import log.Logger;
import model.dao.RegistroDao;
import model.entidades.Mensagens;
import model.entidades.MicroControlador;
import model.entidades.Registro;
import util.TipoOperacao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServidorHashProxy implements RegistroDao {

    private ServidorHash servidorHashReal;
    private Logger loggerHash, loggerHuffman;

    public ServidorHashProxy(ServidorHash servidorHashReal, Logger loggerHash, Logger loggerHuffman) {
        this.servidorHashReal = servidorHashReal;
        this.loggerHash = loggerHash;
        this.loggerHuffman = loggerHuffman;
    }

    public void finalizar(){
        Huffman.No raizBD = Huffman.construirArvoreHuffman(servidorHashReal.getBancoDados());
        Huffman.No raizLogReg = Huffman.construirArvoreHuffman(loggerHash.getLogEmMemoria());
        Huffman.No raizLogCom = Huffman.construirArvoreHuffman(loggerHuffman.getLogEmMemoria());
        Map<Character, String> codigosBD = new HashMap<>(), codigosLogReg = new HashMap<>(), codigosLogCom = new HashMap<>();
        Huffman.gerarCodigos(raizBD, "", codigosBD);
        Huffman.gerarCodigos(raizLogReg, "", codigosLogReg);
        Huffman.gerarCodigos(raizLogCom, "", codigosLogCom);
        String codBD, codLogReg, codLogCom;
        codBD = Huffman.codificar(servidorHashReal.getBancoDados(), codigosBD);
        codLogReg = Huffman.codificar(loggerHash.getLogEmMemoria(), codigosLogReg);
        codLogCom = Huffman.codificar(loggerHuffman.getLogEmMemoria(), codigosLogCom);

        double taxaBD = Huffman.taxaDeCompressao(servidorHashReal.getBancoDados(), codBD);
        double taxaLogReg = Huffman.taxaDeCompressao(loggerHash.getLogEmMemoria(), codLogReg);
        double taxaLogCom = Huffman.taxaDeCompressao(loggerHuffman.getLogEmMemoria(), codLogCom);

        Logger loggerBD = new Logger("logs/comprimidos/banco.txt");
        Logger loggerReg = new Logger("logs/comprimidos/log_registros.txt");
        Logger loggerCom = new Logger("logs/comprimidos/log_compressão.txt");

        loggerBD.registrar("=== Banco de Dados Comprimido === \n" +
                "Taxa de Compressão: "+ taxaBD + "\n" + codBD);
        loggerReg.registrar("Log de Registros Comprimido:\n" +
                "Taxa de Compressão: "+ taxaLogReg + "\n" + codLogReg);
        loggerCom.registrar("Log de Compressão Comprimido:\n" +
                "Taxa de Compressão: "+ taxaLogCom + "\n" + codLogCom);

        loggerBD.fechar();
        loggerReg.fechar();
        loggerCom.fechar();

        loggerHash.fechar();
        loggerHuffman.fechar();
    }
    public List<Mensagens> requisicaoCLiente(Mensagens mensagem){
        String identificador = Huffman.decodificar(mensagem.getIdentificador(), mensagem.getRaizIden());
        String conteudo = Huffman.decodificar(mensagem.getConteudo(), mensagem.getRaizCont());
        List<Mensagens> respostas = new ArrayList<>();

        TipoOperacao op = TipoOperacao.valueOf(identificador.toUpperCase());

        loggerHuffman.registrar("Requisição recebida: " + identificador + " - Conteúdo: " + conteudo + "\n");

        switch (op){
            case BUSCAR -> {
                int idRegistro = Integer.parseInt(conteudo);
                Registro registro = buscar(idRegistro);
                respostas.add(new Mensagens(String.valueOf(idRegistro), registro.toString()));
                loggerHuffman.registrar("=== Resposta enviada === \n Tabela de Códigos do Identificador: " + respostas.get(0).getCodigosIden() + "\n " +
                        "Identificador codificado: " + respostas.get(0).getIdentificador() + "\n Tabela de Códigos do Conteúdo: " + respostas.get(0).getCodigosCont() + "\n " +
                "Conteúdo codificado " + respostas.get(0).getConteudo());
                return respostas;
            }
            case LISTAR -> {
                List<Registro> registros = listar();
                for (Registro registro : registros) {
                    respostas.add(new Mensagens(String.valueOf(registro.getIdRegistro()), registro.toString()));
                }
                return respostas;
            }
            case REMOVER -> {
                int idRegistro = Integer.parseInt(conteudo);
                remover(idRegistro);
            }
            case QUANTIDADE_REGISTROS -> {
                int quantidade = quntidadeRegistros();
                respostas.add(new Mensagens(String.valueOf(quantidade), ""));
                loggerHuffman.registrar("=== Resposta enviada === \n Tabela de Códigos do Identificador: " + respostas.get(0).getCodigosIden() + "\n " +
                        "Identificador codificado: " + respostas.get(0).getIdentificador() + "\n Tabela de Códigos do Conteúdo: " + respostas.get(0).getCodigosCont() + "\n " +
                        "Conteúdo codificado: " + respostas.get(0).getConteudo());
                return respostas;
            }
            default ->
                throw new IllegalArgumentException("Operação inválida: " + identificador);
        }
        return null;
    }
    public Mensagens requisicaoMicro(Mensagens mensagem, Registro registro, MicroControlador dispositivo){
        String identificador = Huffman.decodificar(mensagem.getIdentificador(), mensagem.getRaizIden());
        Mensagens respostas;

        TipoOperacao op = TipoOperacao.valueOf(identificador.toUpperCase());
        switch (op){
            case CADASTRAR -> {
                cadastrar(registro, dispositivo);
                respostas = new Mensagens(String.valueOf(registro.getIdRegistro()), "Registro de nº " + registro.getIdRegistro() + " enviado com sucesso!");
                loggerHuffman.registrar("=== Resposta enviada === \n Tabela de Códigos do Identificador: " + respostas.getCodigosIden() + "\n " +
                        "Identificador codificado: " + respostas.getIdentificador() + "\n Tabela de Códigos do Conteúdo: " + respostas.getCodigosCont() + "\n " +
                        "Conteúdo codificado: " + respostas.getConteudo());
                return respostas;
            }
            case AlTERAR -> {
                alterar(registro, dispositivo);
                respostas = new Mensagens(String.valueOf(registro.getIdRegistro()), "Registro de nº " + registro.getIdRegistro() + " alterado com sucesso!");
                loggerHuffman.registrar("=== Resposta enviada === \n Tabela de Códigos do Identificador: " + respostas.getCodigosIden() + "\n " +
                        "Identificador codificado: " + respostas.getIdentificador() + "\n Tabela de Códigos do Conteúdo: " + respostas.getCodigosCont() + "\n " +
                        "Conteúdo codificado: " + respostas.getConteudo());
                return respostas;
            }
            default ->
                throw new IllegalArgumentException("Operação inválida: " + identificador);
        }
    }

    @Override
    public void cadastrar(Registro registro, MicroControlador dispositivo) {
        servidorHashReal.cadastrar(registro, dispositivo);
        int[] infosReg = servidorHashReal.infosReg();
        int[] infosDis = servidorHashReal.infosDis();
        loggerHash.registrar("Tamanho do BD: " + servidorHashReal.quntidadeRegistros() +
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
        loggerHash.registrar("Tamanho do BD: " + servidorHashReal.quntidadeRegistros() +
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
