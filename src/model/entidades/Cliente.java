package model.entidades;

import model.dao.impl.ServidorProxy;

import java.util.List;
import java.util.Scanner;

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
        Registro registro = servidor.buscar(chave);
        // Exibir o registro solicitado
        System.out.println("------- Informações sosbre o registro -------");
        System.out.println("Registro de nº " + chave + " solicitado");
        System.out.println(registro.toString());
        System.out.println("---------------------------------------------");

    }

    public void solicitarRegistroPorDispositivo(int chave, ServidorProxy servidor){
        // Solicita o registro ao servidor que está relacionado ao dispositivo
        List<Registro> registros = servidor.buscarPorDispositivo(chave);
        for (Registro registro : registros) {
            // Exibir o registro solicitado
            System.out.println("------- Registros ligado ao Dispositivo -------");
            System.out.println("Registro de nº " + registro.getIdRegistro() + " solicitado");
            System.out.println(registro);
            System.out.println("-----------------------------------------------");
        }
    }

    public void solicitarListaDeRegistros(ServidorProxy servidor){
        // Solicita a lista de registros ao servidor
        List<Registro> registros = servidor.listar();
        for (Registro registro : registros) {
            // Exibir o registro solicitado
            System.out.println("------- Lista de Registros -------");
            System.out.println(registro);
            System.out.println("---------------------------------");
        }
    }

    public void solicitarRemocaoDeRegistro(int chave, ServidorProxy servidor){
        // Solicita a remoção do registro ao servidor
        servidor.remover(chave);
        // Exibir o registro solicitado
        System.out.println("------- Registro removido -------");
        System.out.println("Registro de nº " + chave + " removido");
        System.out.println("---------------------------------");
    }

    public void solicitarQuantidadeDeRegistros(ServidorProxy servidor){
        // Solicita a quantidade de registros ao servidor
        int quantidade = servidor.quntidadeRegistros();
        // Exibir a quantidade de registros
        System.out.println("------- Quantidade de Registros -------");
        System.out.println("Quantidade de registros: "  + quantidade);
        System.out.println("---------------------------------------");
    }

    public void menuCliente(Scanner sc, ServidorProxy servidorProxy){
        int opcao;

        do {
            // Opções do menu
            System.out.println("\n===== Menu Cliente =====");
            System.out.println("1 - Consultar Registro");
            System.out.println("2 - Consultar Registros do Dispositivo");
            System.out.println("3 - Listar Registros");
            System.out.println("4 - Remover Registro");
            System.out.println("5 - Quantidade de Registros");
            System.out.println("6 - Voltar para menu inicial");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            switch (opcao){
                case 1:
                    System.out.println("\n==========");
                    System.out.print("Informe o id do registro: ");
                    int id = sc.nextInt();
                    solicitarRegistro(id, servidorProxy);
                    break;
                case 2:
                    System.out.println("\n==========");
                    System.out.println("*OBS: Dispositivos tem um id padrão começando em 111 sendo seu próximo dígito o que diferencia");
                    System.out.print("Informe o id do dispositivo: ");
                    int idDispositivo = sc.nextInt();
                    solicitarRegistroPorDispositivo(idDispositivo, servidorProxy);
                    break;
                case 3:
                    System.out.println("\n==========");
                    solicitarListaDeRegistros(servidorProxy);
                    break;
                case 4:
                    System.out.println("\n==========");
                    System.out.print("Informe o id do registro a ser removido: ");
                    int idRegistro = sc.nextInt();
                    solicitarRemocaoDeRegistro(idRegistro, servidorProxy);
                    break;
                case 5:
                    System.out.println("\n==========");
                    solicitarQuantidadeDeRegistros(servidorProxy);
                    break;
                case 6:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 6);
    }
}
