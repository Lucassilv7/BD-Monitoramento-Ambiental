package model.entidades;

import model.dao.impl.ServidorAVLProxy;
import model.dao.impl.ServidorHashProxy;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

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

    public void enviarRegistro(Registro registro, ServidorAVLProxy servidorAVL, ServidorHashProxy servidorHash, boolean exibirMensagem, MicroControlador dispositivo) {
        // Envia o registro a ser cadastrado
        if (servidorAVL != null)
            servidorAVL.cadastrar(registro, null);
        else if (servidorHash != null)
            servidorHash.cadastrar(registro, dispositivo);
        else
            throw new IllegalArgumentException("Servidor não configurado.");

        if (exibirMensagem)
            System.out.println("Registro de nº " + registro.getIdRegistro() + " enviado com sucesso!");

    }

    public void alterarRegistro(Registro registro, ServidorAVLProxy servidorAVL, ServidorHashProxy servidorHash, boolean exibirMensagem) {
        // Envia o registro a ser alterado
        if (servidorAVL != null)
            servidorAVL.alterar(registro, null);
        else if (servidorHash != null)
            servidorHash.alterar(registro, this);
        else
            throw new IllegalArgumentException("Servidor não configurado.");

        if (exibirMensagem)
            System.out.println("Registro de nº " + registro.getIdRegistro() + " alterado com sucesso!");
    }

    public void menuDispositivo(Scanner sc, ServidorAVLProxy servidorAVLProxy, ServidorHashProxy servidorHashProxy, MicroControlador microControlador) {
        Random random = new Random();
        int opcao;

        do {
            // Menu de opções para o microcontrolador
            System.out.println("\n===== MENU MICROCONTROLADOR =====");
            System.out.println("1. Enviar Registro");
            System.out.println("2. Alterar Registro");
            System.out.println("3. Voltar para menu inicial");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    System.out.println("\n==========");
                    Registro registro;
                    if (servidorAVLProxy != null){
                        registro = new Registro(random.nextInt(300) + 100, microControlador, LocalDateTime.now(), random.nextDouble(40) + 15, random.nextDouble(100) + 1, random.nextDouble(100) + 1);
                        enviarRegistro(registro, servidorAVLProxy, null, true, null);
                    }else if (servidorHashProxy != null) {
                        registro = new Registro(random.nextInt(300) + 10000, microControlador, LocalDateTime.now(), random.nextDouble(40) + 15, random.nextDouble(100) + 1, random.nextDouble(100) + 1);
                        enviarRegistro(registro, null, servidorHashProxy, true, microControlador);
                    }
                    break;
                case 2:
                    System.out.println("\n==========");
                    System.out.print("Informe o id do registro a ser alterado: ");
                    int idRegistro = sc.nextInt();
                    Registro registro1 = new Registro(idRegistro,microControlador, LocalDateTime.now(), (Math.random() * 40) + 15, (Math.random() * 100) + 1, (Math.random() * 100) + 1);
                    if (servidorAVLProxy != null)
                        alterarRegistro(registro1, servidorAVLProxy, null, true);
                    else if (servidorHashProxy != null)
                        alterarRegistro(registro1, null, servidorHashProxy, true);

                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MicroControlador that)) return false;
        return getIdDispositivo() == that.getIdDispositivo();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdDispositivo());
    }
}
