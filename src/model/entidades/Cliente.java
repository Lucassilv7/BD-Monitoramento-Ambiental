package model.entidades;

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

    public void solicitaraRegistro(int chave){}

    public void solicitarListaDeRegistros(){}

    public void solicitarRemocaoDeRegistro(int chave){}

    public void solicitarQuantidadeDeRegistros(){}
}
