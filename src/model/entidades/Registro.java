package model.entidades;

import java.time.LocalDateTime;
import java.util.Objects;

public class Registro {

    private int idRegistro;
    private String idDispositivo;
    private LocalDateTime dataHora;
    private double temperatura;
    private double umidade;
    private double pressao;

    public Registro() {
    }

    public Registro(int idRegistro, String idDispositivo, LocalDateTime dataHora, double temperatura, double umidade, double pressao) {
        this.idRegistro = idRegistro;
        this.idDispositivo = idDispositivo;
        this.dataHora = dataHora;
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.pressao = pressao;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public String getIdDispositivo() {
        return idDispositivo;
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
        return idRegistro == registro.idRegistro;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRegistro);
    }
}
