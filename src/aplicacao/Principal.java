package aplicacao;

import log.Logger;
import model.dao.impl.ServidorAVL;
import model.dao.impl.ServidorAVLProxy;
import model.dao.impl.ServidorHash;
import model.dao.impl.ServidorHashProxy;
import model.entidades.Cliente;
import model.entidades.MicroControlador;
import model.entidades.Registro;

import java.time.LocalDateTime;
import java.util.*;

public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        menuInicial();
        int estrutura = sc.nextInt();

        /**
         * Instancia dos objetos necessários para o funcionamento do sistema.
         * @param servidorProxy Objeto que implementa o padrão Proxy.
         * @param servidor Objeto que contém o banco de dados e o indexador.
         * @param logger Objeto para salvar log
         */

        Logger logger = new Logger("logs/registros.txt");
        Cliente cliente = new Cliente(7, "Lucas Silva");
        List<MicroControlador> microControladores = new ArrayList<>();
        List<Integer> numeros = new ArrayList<>();
        int tipoSistema;

        switch (estrutura) {
            case 1:
                System.out.println("Você escolheu a estrutura de dados: Árvore AVL");
                // Lista de 0 a 100 para ser usado como id do rergistro
                for (int i = 0; i < 100; i++) {
                    numeros.add(i);
                }
                // Embaralha a lista
                Collections.shuffle(numeros);

                // Instancia o servidor e o proxy
                ServidorAVL servidorAVL = new ServidorAVL();
                ServidorAVLProxy servidorAVLProxy = new ServidorAVLProxy(servidorAVL, logger);

                for (int i = 0; i < 10; i++) {
                    // Instancia um novo microcontrolador e adiciona na lista
                    microControladores.add(new MicroControlador(1110 + i, "Dispositivo " + i));
                    for (int j = 9; j > -1; j--){
                        Registro registro = new Registro(numeros.get(j),microControladores.get(i), LocalDateTime.now(), random.nextDouble(40) + 15, random.nextDouble(100) + 1, random.nextDouble(100) + 1);
                        microControladores.get(i).enviarRegistro(registro, servidorAVLProxy, null,false, null);
                        numeros.remove(j);
                    }
                }

                menu();
                tipoSistema = sc.nextInt();
                avl(tipoSistema, sc, servidorAVLProxy, microControladores, cliente, random);

                break;
            case 2:
                System.out.println("Você escolheu a estrutura de dados: Tabela Hash");

                // Lista de 0 a 10000 para ser usado como id do rergistro
                for (int i = 0; i < 10000; i++) {
                    numeros.add(i);
                }
                // Embaralha a lista
                Collections.shuffle(numeros);

                ServidorHash servidorHash = new ServidorHash<>();
                ServidorHashProxy servidorHashProxy = new ServidorHashProxy(servidorHash, logger);

                for (int i = 0; i < 100; i++) {
                    // Instancia um novo microcontrolador e adiciona na lista
                    microControladores.add(new MicroControlador(1110 + i, "Dispositivo " + i));
                    for (int j = 99; j > -1; j--){
                        Registro registro = new Registro(numeros.get(j),microControladores.get(i), LocalDateTime.now(), random.nextDouble(40) + 15, random.nextDouble(100) + 1, random.nextDouble(100) + 1);
                        microControladores.get(i).enviarRegistro(registro, null, servidorHashProxy,false, microControladores.get(i));
                        numeros.remove(j);
                    }
                }

                menu();
                tipoSistema = sc.nextInt();
                hash(tipoSistema, sc, servidorHashProxy, microControladores, cliente, random);

                break;
            default:
                System.out.println("Opção inválida! Encerrando o programa.");
                break;
        }
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

    public static void menuInicial(){
        System.out.println("O sistema possui duas formas de simulação a partir de estruturas de dados diferentes!!");
        System.out.println("Escolha qual estrutura deseja:");
        System.out.println("1 - Árvore AVL");
        System.out.println("2 - Tabela Hash");
        System.out.print("Opção: ");
    }

    public static void avl(int tipoSistema, Scanner sc, ServidorAVLProxy servidorProxy, List<MicroControlador> microControladores, Cliente cliente, Random random) {
        do {
            switch (tipoSistema){
                case 1:
                    cliente.menuCliente(sc, servidorProxy, null);
                    break;
                case 2:
                    System.out.print("\nCom qual microcontrolador deseja se conectar?\n");
                    for (int i = 0; i < microControladores.size(); i++) {
                        System.out.println(i + " - " + microControladores.get(i).getNome());
                    }
                    int microControladorEscolhido = sc.nextInt();
                    MicroControlador microControlador = microControladores.get(microControladorEscolhido);
                    microControlador.menuDispositivo(sc, servidorProxy, null,microControlador);
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
            if (tipoSistema != 4) {
                menu();
                tipoSistema = sc.nextInt();
            }
        }while (tipoSistema != 4);
    }

    public static void hash(int tipoSistema, Scanner sc, ServidorHashProxy servidorProxy, List<MicroControlador> microControladores, Cliente cliente, Random random) {
        do {
            switch (tipoSistema){
                case 1:
                    cliente.menuCliente(sc, null, servidorProxy);
                    break;
                case 2:
                    System.out.print("\nCom qual microcontrolador deseja se conectar?\n");
                    for (int i = 0; i < microControladores.size(); i++) {
                        System.out.println(i + " - " + microControladores.get(i).getNome());
                    }
                    int microControladorEscolhido = sc.nextInt();
                    MicroControlador microControlador = microControladores.get(microControladorEscolhido);
                    microControlador.menuDispositivo(sc, null, servidorProxy,microControlador);
                    break;
                case 3:
                    cadastrar10(servidorProxy,microControladores,cliente,random);
                    alterar10(servidorProxy,microControladores,cliente,random);
                    esperar(sc);
                    remover50(servidorProxy,cliente);
                    esperar(sc);
                    consultar10(servidorProxy,cliente,random);
                    esperar(sc);

                    break;
                case 4:
                    System.out.println("Sistema encerrado!");
                    servidorProxy.finalizar();
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.\n\n");
                    break;
            }
            if (tipoSistema != 4) {
                menu();
                tipoSistema = sc.nextInt();
            }
        }while (tipoSistema != 4);
    }

    // Crie e execute um método para fazer cinco cadastros por cinco dispositivos
    //diferentes. Após isso, faça uma listagem via cliente
    public static void cadastrar5(ServidorAVLProxy servidorProxy, List<MicroControlador> microControladores, Cliente cliente, Random random){
        for (int i = 0; i < 5; i++) {
            Registro registro = new Registro((100 + i), microControladores.get(i), LocalDateTime.now(), random.nextDouble(40) + 15, random.nextDouble(100) + 1, random.nextDouble(100) + 1);
            microControladores.get(i).enviarRegistro(registro, servidorProxy, null,true, null);
        }
        System.out.println("\nCinco registros cadastrados com sucesso!\n\n");
        System.out.println("===== Listando registros cadastrados =====");
        cliente.solicitarListaDeRegistros(servidorProxy, null);
    }

    // Crie e execute um método para fazer cinco alterações por cinco
    //dispositivos diferentes. Após isso, faça uma listagem via cliente.
    public static void alterar5(ServidorAVLProxy servidorProxy, List<MicroControlador> microControladores, Cliente cliente, Random random){
        for (int i = 0; i < 5; i++) {
            Registro registro = new Registro((100 + i), microControladores.get(i), LocalDateTime.now(), random.nextDouble(40) + 15, random.nextDouble(100) + 1, random.nextDouble(100) + 1);
            microControladores.get(i).alterarRegistro   (registro, servidorProxy, null,true);
        }
        System.out.println("\nCinco registros alterados com sucesso!\n\n");
        System.out.println("===== Listando registros cadastrados =====");
        cliente.solicitarListaDeRegistros(servidorProxy, null);
    }

    // Crie e execute um método para fazer cinco remoções por cinco dispositivos
    //diferentes. Após isso, faça uma listagem via cliente.
    public static void remover5(ServidorAVLProxy servidorProxy, Cliente cliente){
        for (int i = 0; i < 5; i++) {
            servidorProxy.remover(100 + i);
        }
        System.out.println("\nCinco registros removidos com sucesso!\n\n");
        System.out.println("===== Listando registros cadastrados =====");
        cliente.solicitarListaDeRegistros(servidorProxy, null);
    }

    // Esperar o usuário pressionar ENTER para continuar
    public static void esperar(Scanner sc) {
        sc.nextLine(); // Limpa o buffer do scanner
        System.out.print("Pressione ENTER para continuar...\n");
        sc.nextLine();
    }

    // Crie e execute um método para fazer 10 cadastros por 10 dispositivos
    //diferentes. Após isso, faça uma listagem via cliente
    public static void cadastrar10(ServidorHashProxy servidorHashProxy, List<MicroControlador> microControladores, Cliente cliente, Random random) {
        for (int i = 0; i < 10; i++) {
            Registro registro = new Registro((10700 + i), microControladores.get(i), LocalDateTime.now(), random.nextDouble(40) + 15, random.nextDouble(100) + 1, random.nextDouble(100) + 1);
            microControladores.get(i).enviarRegistro(registro, null, servidorHashProxy,true, microControladores.get(i));        }
        System.out.println("\nDez registros cadastrados com sucesso!\n\n");
        esperar(new Scanner(System.in));
        System.out.println("===== Listando registros cadastrados =====");
        cliente.solicitarListaDeRegistros(null, servidorHashProxy);
    }

    // Crie e execute um método para fazer 10 alterações por 10 dispositivos
    //diferentes. Após isso, faça uma listagem via cliente.
    public static void alterar10(ServidorHashProxy servidorHashProxy, List<MicroControlador> microControladores, Cliente cliente, Random random) {
        for (int i = 0; i < 10; i++) {
            Registro registro = new Registro((10700 + i), microControladores.get(i), LocalDateTime.now(), random.nextDouble(40) + 15, random.nextDouble(100) + 1, random.nextDouble(100) + 1);
            microControladores.get(i).alterarRegistro(registro, null, servidorHashProxy,true);
        }
        System.out.println("\nDez registros alterados com sucesso!\n\n");
        esperar(new Scanner(System.in));
        System.out.println("===== Listando registros cadastrados =====");
        cliente.solicitarListaDeRegistros(null, servidorHashProxy);
    }

    // Crie e execute um método para fazer 50 remoções por 5 usuários
    //diferentes. Após isso, faça uma listagem via cliente.
    public static void remover50(ServidorHashProxy servidorHashProxy, Cliente cliente) {

        // Instancia 5 clientes diferentes
        Cliente[] clientes = new Cliente[5];
        for (int i = 0; i < 5; i++) {
            clientes[i] = new Cliente(15 + i, "Cliente " + (i + 1));
        }
        // Primeiro cliente remove os 10 registros anteriores
        for (int i = 0; i < 10; i++) {
            System.out.println("Cliente " + clientes[0].getNome() + " removendo registro de nº " + (10700 + i));
            clientes[0].solicitarRemocaoDeRegistro(10700 + i, null, servidorHashProxy);
        }
        int contador = 0;
        // Os outros 4 clientes removem 10 registros cada
        for (int i = 1; i < 5; i++){
            for (int j = 0; j < 10; j++) {
                clientes[i].solicitarRemocaoDeRegistro(9000 + contador, null, servidorHashProxy);
                contador++;
            }
        }

        System.out.println("\nCinquenta registros removidos com sucesso!\n\n");
        System.out.println("===== Listando registros cadastrados =====");
        cliente.solicitarListaDeRegistros(null, servidorHashProxy);
        cliente.solicitarQuantidadeDeRegistros(null, servidorHashProxy);
    }

    //  Crie e execute um método para realizar 10 consultas (dentre elas , duas
    //inválidas).
    public static void consultar10(ServidorHashProxy servidorHashProxy, Cliente cliente, Random random){
        for (int i = 0; i < 8; i++) {
            int idRegistro = random.nextInt(10000);
            cliente.solicitarRegistro(idRegistro, null, servidorHashProxy);
        }
        cliente.solicitarRegistro(10500, null, servidorHashProxy); // Consulta inválida
        cliente.solicitarRegistro(10600, null, servidorHashProxy); // Consulta inválida
    }
}
