package model.entidades;

import model.dao.impl.ServidorProxy;

public class Cliente {

    private int id;
    private String nome;

    public Cliente() {
    }

    public Cliente(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void solicitarRegistro(int chave, ServidorProxy servidor){
        // Solicita o registro ao servidor
        servidor.buscar(chave);
        // Exibe o registro
    }

    public void solicitarRegistroPorDispositivo(int chave, ServidorProxy servidor){
        // Solicita o registro ao servidor que está relacionado ao dispositivo
        servidor.buscarPorDispositivo(chave);
    }

    public void solicitarListaDeRegistros(){}

    public void solicitarRemocaoDeRegistro(int chave){}

    public void solicitarQuantidadeDeRegistros(){}
}
