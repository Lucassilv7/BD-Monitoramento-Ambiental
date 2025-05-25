package model.entidades;

import model.dao.impl.ServidorProxy;

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

    public void enviarRegistro(Registro registro, ServidorProxy servidor){
        // Envia o registro a ser cadastrado
        servidor.cadastrar(registro);
        System.out.println("Registro de nº " + registro.getIdRegistro() + " enviado com sucesso!");
    }

    public void alterarRegistro(Registro registro, ServidorProxy servidor){
        // Envia o registro a ser alterado
        servidor.alterar(registro);
        System.out.println("Registro de nº " + registro.getIdRegistro() + " alterado com sucesso!");
    }

    public void menuDispositivo(Scanner sc, ServidorProxy servidorProxy, MicroControlador microControlador) {
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
                    Registro registro = new Registro(random.nextInt(300) + 100, microControlador, LocalDateTime.now(), random.nextDouble(40) + 15, random.nextDouble(100) + 1, random.nextDouble(100) + 1);
                    enviarRegistro(registro, servidorProxy);
                    break;
                case 2:
                    System.out.println("\n==========");
                    System.out.print("Informe o id do registro a ser alterado: ");
                    int idRegistro = sc.nextInt();
                    Registro registro1 = new Registro(idRegistro,microControlador, LocalDateTime.now(), (Math.random() * 40) + 15, (Math.random() * 100) + 1, (Math.random() * 100) + 1);
                    alterarRegistro(registro1, servidorProxy);
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
