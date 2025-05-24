package model.dao.impl;

import estruturas.ArvoreAVL;
import estruturas.ListaEncadeada;
import model.dao.RegistroDao;
import model.entidades.Registro;

import java.util.ArrayList;
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
        // Execeção para verificar se o registro já existe ou é nulo
        try{
            if (!indexador.isEmpyt()){
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
                throw new Exception("Registro já existe");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Registro buscar(int id) {
        try{
            // Verifica se o registro existe
            Registro ponteiro = indexador.buscarReferencia(id);
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
    public Registro buscarPorDispositivo(String dispositivo) {
        return null;
    }

    @Override
    public List<Registro> listar() {
        try {
            // Verifica se o banco de dados está vazio
            if (!indexador.isEmpyt()){
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
            if (!indexador.isEmpyt()){
                Registro ponteiro = indexador.buscarReferencia(id);
                // Remove do banco de dados
                bancoDados.remover(ponteiro);
                // Remove do indexador
                indexador.remover(id, ponteiro);
            }else {
                throw new Exception("Não há registros cadastrados");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void alterar(Registro registro) {
        try {
            // Verifica se o registro existe
            if (!indexador.isEmpyt()){
                Registro ponteiro = indexador.buscarReferencia(registro.getIdRegistro());
                // Altera o registro
                ponteiro = registro;
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
            if (!indexador.isEmpyt()){
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
