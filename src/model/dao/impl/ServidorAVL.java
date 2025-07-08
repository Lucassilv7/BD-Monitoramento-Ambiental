package model.dao.impl;

import estruturas.ArvoreAVL;
import estruturas.ListaEncadeada;
import model.dao.RegistroDao;
import model.entidades.MicroControlador;
import model.entidades.Registro;
import util.TipoRotacao;

import java.util.ArrayList;
import java.util.List;

public class ServidorAVL <E> implements RegistroDao<E>{

    private ListaEncadeada<Registro> bancoDados;
    private ArvoreAVL indexador;

    public ServidorAVL() {
        this.bancoDados = new ListaEncadeada<>();
        this.indexador = new ArvoreAVL();
    }

    public int getAlturaArvore() {
        // Retorna a altura da árvore
        return indexador.alturaArvore();
    }
    public TipoRotacao getUltimaRotacao() {
        // Retorna o tipo de rotação da árvore
        return indexador.getUltimaRotacao();
    }

    @Override
    public void cadastrar(Registro registro, MicroControlador dispositivo) {
        // Execeção para verificar se o registro já existe ou é nulo
        try{
            if (indexador.isEmpty()){
                // Adiciona o registro no banco de dados
                bancoDados.adicionarPrimeiro(registro);
                // Adiciona o registro no indexador
                indexador.inserir(registro.getIdRegistro(), registro);
            } else if (!indexador.buscar(registro.getIdRegistro())) {
                // Adiciona o registro no banco de dados
                bancoDados.adicionarUltimo(registro);
                // Adiciona o registro no indexador
                indexador.inserir(registro.getIdRegistro(), registro);
            } else {
                throw new Exception("Registro já existe ou é nulo");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Registro buscar(int idRegistro) {
        try{
            // Verifica se o registro existe
            Registro ponteiro = indexador.buscarPorIdRegistro(idRegistro);
            if (ponteiro != null){
                // Retorna o registro
                return bancoDados.procurar(ponteiro);
            } else {
                throw new Exception("Registro não encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> buscarPorDispositivo(int idDispositivo) {
        try {
            // Verfica se o banco de dados está vazio
            if (indexador.isEmpty()) {
                throw new RuntimeException("O banco de dados está vazio.");
            }

            List<Registro> ponteiro = indexador.buscarPorIdDispositivo(idDispositivo);
            List<Registro> retorno = new ArrayList<>();

            for (Registro registro : ponteiro) {
                // Adiciona o registro na lista de retorno
                retorno.add(bancoDados.procurar(registro));
            }

            if (retorno.isEmpty()) {
                throw new RuntimeException("Dispositivo não encontrado.");
            }

            // Retorna o registro
            return (List<E>) retorno;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Registro> listar() {
        try {
            // Verifica se o banco de dados está vazio
            if (!indexador.isEmpty()){
                // Cria uma lista para armazenar os registros
                List<Registro> list = new ArrayList<>();
                list = bancoDados.listar();
                // Retorna a lista de registros
                return list;
            }else
                throw new Exception("Não há registros cadastrados");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remover(int id) {
        try {
            // Verifica se há registros
            if (!indexador.isEmpty()){
                Registro ponteiro = indexador.buscarPorIdRegistro(id);
                if (ponteiro == null){
                    throw new Exception("Registro não encontrado");
                }else{
                    // Remove do banco de dados
                    bancoDados.remover(ponteiro);
                    // Remove do indexador
                    indexador.remover(id, ponteiro);
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
            if (!indexador.isEmpty()){
                Registro ponteiro = indexador.buscarPorIdRegistro(registro.getIdRegistro());
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
            if (!indexador.isEmpty()){
                // Retorna a quantidade de registros
                return bancoDados.quantidade();
            } else {
                throw new Exception("Quantidade de registros é 0");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
