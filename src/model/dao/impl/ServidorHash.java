package model.dao.impl;

import estruturas.ListaEncadeada;
import estruturas.TabelaHash;
import model.dao.RegistroDao;
import model.entidades.MicroControlador;
import model.entidades.Registro;

import java.util.ArrayList;
import java.util.List;

public class ServidorHash <E> implements RegistroDao<E> {

    private ListaEncadeada<Registro> bancoDados;
    private TabelaHash indexadorRegistros, indexadorDispositivos;

    public ServidorHash() {
        this.bancoDados = new ListaEncadeada<>();
        this.indexadorRegistros = new TabelaHash<>();
        this.indexadorDispositivos = new TabelaHash<>();
    }
    @SuppressWarnings("unchecked")
    @Override
    public void cadastrar(Registro registro, MicroControlador dispositivo) {
        // Execeção para verificar se o registro já existe ou é nulo
        try {
            if (indexadorRegistros.isEmpty()){
                // Adiciona o registro no banco de dados
                bancoDados.adicionarPrimeiro(registro);
                // Adiciona o registro no indexador
                indexadorRegistros.inserir(registro, registro.getIdRegistro());
                indexadorDispositivos.inserir(dispositivo, dispositivo.getIdDispositivo());
            } else if (indexadorRegistros.buscar(registro.getIdRegistro()) == null) {
                // Adiciona o registro no banco de dados
                bancoDados.adicionarUltimo(registro);
                // Adiciona o registro no indexador
                indexadorRegistros.inserir(registro, registro.getIdRegistro());
            } else {
                throw new Exception("Registro já existe ou é nulo");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Registro buscar(int idRegistro) {
        try {
            Registro ponteiro = (Registro) indexadorRegistros.buscar(idRegistro);
            // Verifica se o registro existe
            if (ponteiro != null) {
                // Retorna o registro
                return bancoDados.procurar(ponteiro);
            } else {
                System.out.println("Registro não encontrado");
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<E> buscarPorDispositivo(int idDispositivo) {
        return null;
    }

    @Override
    public List<Registro> listar() {
        try{
            // Verifica se o banco de dados está vazio
            if (!indexadorRegistros.isEmpty()){
                // Retorna a lista de registros
                return bancoDados.listar();
            }else {
                throw new RuntimeException("O banco de dados está vazio.");
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remover(int id) {
        try {
            // Verifica se há registros
            if (!indexadorRegistros.isEmpty()){
                Registro ponteiro = (Registro) indexadorRegistros.buscar(id);
                if (ponteiro == null){
                    throw new Exception("Registro não encontrado");
                }else{
                    // Remove do banco de dados
                    bancoDados.remover(ponteiro);
                    // Remove do indexador
                    indexadorRegistros.remover(id);
                    indexadorDispositivos.remover(ponteiro.getIdDispositivo());
                    System.out.println("Registro removido com sucesso");
                }
            }else {
                throw new Exception("Não há registros cadastrados");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void alterar(Registro registro, MicroControlador dispositivo) {
        try {
            // Verifica se o registro existe
            if (!indexadorRegistros.isEmpty()){
                Registro ponteiro = (Registro) indexadorRegistros.buscar(registro.getIdRegistro());
                // Altera o registro
                if (ponteiro != null) {
                    // Atualiza os atributos do ponteiro
                    ponteiro.setDataHora(registro.getDataHora());
                    ponteiro.setTemperatura(registro.getTemperatura());
                    ponteiro.setPressao(registro.getPressao());
                    ponteiro.setUmidade(registro.getUmidade());
                } else {
                    throw new Exception("Registro não encontrado.");
                }
            }else {
                throw new Exception("Não há registros cadastrados");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int quntidadeRegistros() {
        // Verifica se o banco de dados está vazio
        try{
            if (!indexadorRegistros.isEmpty()){
                // Retorna a quantidade de registros
                return bancoDados.quantidade();
            } else {
                throw new Exception("Quantidade de registros é 0");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int[] infosReg(){
        int[] info = new int[3];
        info[0] = indexadorRegistros._getTamnho();
        info[1] = indexadorRegistros._getRedimensionamentos();
        info[2] = indexadorRegistros._getColisoes();

        return info;
    }
    public double getCargaReg(){
        return indexadorRegistros._getCarga();
    }
    public int[] infosDis(){
        int[] info = new int[3];
        info[0] = indexadorDispositivos._getTamnho();
        info[1] = indexadorDispositivos._getRedimensionamentos();
        info[2] = indexadorDispositivos._getColisoes();

        return info;
    }
    public double getCargaDis() {
        return indexadorDispositivos._getCarga();
    }
}
