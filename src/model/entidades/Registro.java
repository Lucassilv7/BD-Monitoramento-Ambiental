package model.entidades;

import java.time.LocalDateTime;
import java.util.Objects;

public class Registro {

    private int idRegistro;
    private MicroControlador Dispositivo;
    private LocalDateTime dataHora;
    private double temperatura;
    private double umidade;
    private double pressao;

    public Registro() {
    }

    public Registro(int idRegistro, MicroControlador Dispositivo, LocalDateTime dataHora, double temperatura, double umidade, double pressao) {
        this.idRegistro = idRegistro;
        this.Dispositivo = Dispositivo;
        this.dataHora = dataHora;
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.pressao = pressao;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public int getIdDispositivo() {
        return Dispositivo.getIdDispositivo();
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getUmidade() {
        return umidade;
    }

    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }

    public double getPressao() {
        return pressao;
    }

    public void setPressao(double pressao) {
        this.pressao = pressao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Registro registro)) return false;
        return getIdRegistro() == registro.getIdRegistro();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdRegistro());
    }

    @Override
    public String toString() {
        return "Id do Registro: " + idRegistro +
                "\nNome do dispositivo: " + Dispositivo.getNome() +
                "\nData e hora:" + dataHora +
                "\nTemperatura: " + temperatura +
                "\nUmidade: " + umidade +
                "\nPressao: " + pressao
                ;
    }
}
