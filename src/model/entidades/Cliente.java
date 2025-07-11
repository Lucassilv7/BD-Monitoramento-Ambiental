package model.entidades;

import model.dao.impl.ServidorAVLProxy;
import model.dao.impl.ServidorHashProxy;

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

    public void solicitarRegistro(int chave, ServidorAVLProxy servidorAVLProxy, ServidorHashProxy servidorHashProxy){
        // Solicita o registro ao servidor
        Registro registro;
        if (servidorAVLProxy != null)
            registro = (Registro) servidorAVLProxy.buscar(chave);
        else if (servidorHashProxy != null)
            registro = servidorHashProxy.buscar(chave);
        else
            throw new IllegalArgumentException("Servidor não configurado.");

        if (registro != null){
            // Exibir o registro solicitado
            System.out.println("------- Informações sosbre o registro -------");
            System.out.println("Registro de nº " + chave + " solicitado");
            System.out.println(registro.toString());
            System.out.println("---------------------------------------------");
        }

    }

    public void solicitarRegistroPorDispositivo(int chave, ServidorAVLProxy servidorAVLProxy, ServidorHashProxy servidorHashProxy){
        // Solicita o registro ao servidor que está relacionado ao dispositivo
        List<Registro> registros;
        if (servidorAVLProxy != null)
            registros = servidorAVLProxy.buscarPorDispositivo(chave);
        else if (servidorHashProxy != null)
            registros = servidorHashProxy.buscarPorDispositivo(chave);
        else
            throw new IllegalArgumentException("Servidor não configurado.");

        for (Registro registro : registros) {
            // Exibir o registro solicitado
            System.out.println("------- Registros ligado ao Dispositivo -------");
            System.out.println("Registro de nº " + registro.getIdRegistro() + " solicitado");
            System.out.println(registro);
            System.out.println("-----------------------------------------------");
        }
    }

    public void solicitarListaDeRegistros(ServidorAVLProxy servidorAVLProxy, ServidorHashProxy servidorHashProxy){
        // Solicita a lista de registros ao servidor
        List<Registro> registros;
        if (servidorAVLProxy != null)
            registros = servidorAVLProxy.listar();
        else if (servidorHashProxy != null)
            registros = servidorHashProxy.listar();
        else
            throw new IllegalArgumentException("Servidor não configurado.");

        for (Registro registro : registros) {
            // Exibir o registro solicitado
            System.out.println("------- Lista de Registros -------");
            System.out.println(registro);
            System.out.println("---------------------------------");
        }
    }

    public void solicitarRemocaoDeRegistro(int chave, ServidorAVLProxy servidorAVLProxy, ServidorHashProxy servidorHashProxy){
        // Solicita a remoção do registro ao servidor
        if (servidorAVLProxy != null)
            servidorAVLProxy.remover(chave);
        else if (servidorHashProxy != null)
            servidorHashProxy.remover(chave);
        else
            throw new IllegalArgumentException("Servidor não configurado.");

        // Exibir o registro solicitado
        System.out.println("------- Registro removido -------");
        System.out.println("Registro de nº " + chave + " removido");
        System.out.println("---------------------------------");
    }

    public void solicitarQuantidadeDeRegistros(ServidorAVLProxy servidorAVLProxy, ServidorHashProxy servidorHashProxy){
        // Solicita a quantidade de registros ao servidor
        int quantidade;
        if (servidorAVLProxy != null)
            quantidade = servidorAVLProxy.quntidadeRegistros();
        else if (servidorHashProxy != null)
            quantidade = servidorHashProxy.quntidadeRegistros();
        else
            throw new IllegalArgumentException("Servidor não configurado.");

        // Exibir a quantidade de registros
        System.out.println("------- Quantidade de Registros -------");
        System.out.println("Quantidade de registros: "  + quantidade);
        System.out.println("---------------------------------------");
    }

    public void menuCliente(Scanner sc, ServidorAVLProxy servidorAVLProxy, ServidorHashProxy servidorHashProxy) {
        int opcao;

        do {
            // Opções do menu
            System.out.println("\n===== Menu Cliente =====");
            System.out.println("1 - Consultar Registro");
            if (servidorAVLProxy != null)
                System.out.println("2 - Consultar Registros por Dispositivo");
            else if (servidorHashProxy != null)
                System.out.println("2 - Consultar Registros por Dispositivo (não disponível para Hash)");
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
                    if (servidorAVLProxy != null)
                        solicitarRegistro(id, servidorAVLProxy, null);
                    else if (servidorHashProxy != null)
                        solicitarRegistro(id, null, servidorHashProxy);

                    break;
                case 2:
                    if (servidorAVLProxy != null){
                        System.out.println("\n==========");
                        System.out.println("*OBS: Dispositivos tem um id padrão começando em 111 sendo seu próximo dígito o que diferencia");
                        System.out.print("Informe o id do dispositivo: ");
                        int idDispositivo = sc.nextInt();
                        solicitarRegistroPorDispositivo(idDispositivo, servidorAVLProxy, null);
                    }
                    else if (servidorHashProxy != null)
                        System.out.println("\n==========\n*OBS: Consultar registros por dispositivo não está disponível para o servidor Hash.");

                    break;
                case 3:
                    System.out.println("\n==========");
                    if (servidorAVLProxy != null)
                        solicitarListaDeRegistros(servidorAVLProxy, null);
                    else if (servidorHashProxy != null)
                        solicitarListaDeRegistros(null, servidorHashProxy);

                    break;
                case 4:
                    System.out.println("\n==========");
                    System.out.print("Informe o id do registro a ser removido: ");
                    int idRegistro = sc.nextInt();
                    if (servidorAVLProxy != null)
                        solicitarRemocaoDeRegistro(idRegistro, servidorAVLProxy, null);
                    else if (servidorHashProxy != null)
                        solicitarRemocaoDeRegistro(idRegistro, null, servidorHashProxy);

                    break;
                case 5:
                    System.out.println("\n==========");
                    if (servidorAVLProxy != null)
                        solicitarQuantidadeDeRegistros(servidorAVLProxy, null);
                    else if (servidorHashProxy != null)
                        solicitarQuantidadeDeRegistros(null, servidorHashProxy);

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
