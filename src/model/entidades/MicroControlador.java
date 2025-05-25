package model.entidades;

public class MicroControlador {

    private int idDispositivo;
    private String nome;

    public MicroControlador() {
    }

    public MicroControlador(int idDispositivo, String nome) {
        this.idDispositivo = idDispositivo;
        this.nome = nome;
    }

    public int getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(int idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void enviarRegistro(Registro registro){}

    public void alterarRegistro(Registro registro){}
}
