package model.entidades;

public class MicroControlador {

    private String idDispositivo;

    public MicroControlador() {
    }

    public MicroControlador(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public void enviarRegistro(Registro registro){}

    public void alterarRegistro(Registro registro){}
}
