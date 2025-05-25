package aplicacao;

import log.Logger;
import model.dao.impl.Servidor;
import model.dao.impl.ServidorProxy;
import model.entidades.Cliente;
import model.entidades.MicroControlador;
import model.entidades.Registro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        /**
         * Instancia dos objetos necessários para o funcionamento do sistema.
         * @param servidorProxy Objeto que implementa o padrão Proxy.
         * @param servidor Objeto que contém o banco de dados e o indexador.
         * @param logger Objeto para salvar log
         */
        Logger logger = new Logger("logs/registros.txt");
        Servidor servidor = new Servidor();
        ServidorProxy servidorProxy = new ServidorProxy(servidor, logger);

        Cliente cliente = new Cliente(7, "Lucas Silva");
        List<MicroControlador> microControladores = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            // Instancia um novo microcontrolador e adiciona na lista
            microControladores.add(new MicroControlador(1110 + i, "Dispositivo " + i));
            for (int j = 0; j < 10; j++){
                Registro registro = new Registro(random.nextInt(100) + 1,microControladores.get(i), LocalDateTime.now(), random.nextDouble(40) + 15, random.nextDouble(100) + 1, random.nextDouble(100) + 1);
                microControladores.get(i).enviarRegistro(registro, servidorProxy);
            }
        }
        menu();
        int tipoSistema = sc.nextInt();

        do {
            switch (tipoSistema){
                case 1:
                    cliente.menuCliente(sc, servidorProxy);
                    break;
                case 2:
                    System.out.print("\nCom qual microcontrolador deseja se conectar?\n");
                    for (int i = 0; i < microControladores.size(); i++) {
                        System.out.println(i + " - " + microControladores.get(i).getNome());
                    }
                    int microControladorEscolhido = sc.nextInt();
                    MicroControlador microControlador = microControladores.get(microControladorEscolhido);
                    microControlador.menuDispositivo(sc, servidorProxy, microControlador);
                    break;
                case 3:
                    cadastrar5(servidorProxy,microControladores,cliente,random);
                    esperar(sc);
                    alterar5(servidorProxy,microControladores,cliente,random);
                    esperar(sc);
                    remover5(servidorProxy,cliente);
                    break;
                case 4:
                    System.out.println("Sistema encerrado!");
                    servidorProxy.finalizar();
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.\n\n");
                    break;
            }
            menu();
            tipoSistema = sc.nextInt();
        }while (tipoSistema != 4);


    }

    public static void menu(){
        // Perguntar se é cliente ou microcontrolador
        System.out.println("\n\nEscolha o sistema: ");
        System.out.println("1. Cliente");
        System.out.println("2. Microcontrolador");
        System.out.println("3. Métodos do Trabalho");
        System.out.println("4. Sair");
        System.out.print("Digite a opção: ");
    }

    // Crie e execute um método para fazer cinco cadastros por cinco dispositivos
    //diferentes. Após isso, faça uma listagem via cliente
    public static void cadastrar5(ServidorProxy servidorProxy, List<MicroControlador> microControladores, Cliente cliente, Random random){
        for (int i = 0; i < 5; i++) {
            Registro registro = new Registro(100 + i, microControladores.get(i), LocalDateTime.now(), random.nextDouble(40) + 15, random.nextDouble(100) + 1, random.nextDouble(100) + 1);
            microControladores.get(i).enviarRegistro(registro, servidorProxy);
        }
        System.out.println("\nCinco registros cadastrados com sucesso!\n\n");
        System.out.println("===== Listando registros cadastrados =====");
        cliente.solicitarListaDeRegistros(servidorProxy);
    }
    // Crie e execute um método para fazer cinco alterações por cinco
    //dispositivos diferentes. Após isso, faça uma listagem via cliente.
    public static void alterar5(ServidorProxy servidorProxy, List<MicroControlador> microControladores, Cliente cliente, Random random){
        for (int i = 0; i < 5; i++) {
            Registro registro = new Registro(100 + i, microControladores.get(i), LocalDateTime.now(), random.nextDouble(40) + 15, random.nextDouble(100) + 1, random.nextDouble(100) + 1);
            microControladores.get(i).alterarRegistro(registro, servidorProxy);
        }
        System.out.println("\nCinco registros alterados com sucesso!\n\n");
        System.out.println("===== Listando registros cadastrados =====");
        cliente.solicitarListaDeRegistros(servidorProxy);
    }
    // Crie e execute um método para fazer cinco remoções por cinco dispositivos
    //diferentes. Após isso, faça uma listagem via cliente.
    public static void remover5(ServidorProxy servidorProxy, Cliente cliente){
        for (int i = 0; i < 5; i++) {
            servidorProxy.remover(100 + i);
        }
        System.out.println("\nCinco registros removidos com sucesso!\n\n");
        System.out.println("===== Listando registros cadastrados =====");
        cliente.solicitarListaDeRegistros(servidorProxy);
    }

    // Esperar o usuário pressionar ENTER para continuar
    public static void esperar(Scanner sc) {
        sc.nextLine();
        System.out.print("Pressione ENTER para continuar...");
        sc.nextLine();
    }

}
