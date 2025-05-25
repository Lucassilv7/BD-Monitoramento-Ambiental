package log;

import java.io.BufferedWriter;

public class Logger {

    private BufferedWriter writer;

    /**
     * Construtor da classe Logger.
     * @param nomeArquivo Nome do arquivo de log.
     */
    public Logger(String nomeArquivo){
        try {
            this.writer = new BufferedWriter(new java.io.FileWriter(nomeArquivo, true));
        } catch (Exception e) {
            System.out.println("Erro ao abrir o arquivo de log: " + e.getMessage());
        }
    }
    /**
     * Método para registrar uma mensagem no arquivo de log.
     * @param mensagem Mensagem a ser registrada.
     */
    public void registrar(String mensagem){
        try {
            writer.write("[" + java.time.LocalDateTime.now() + "] " + mensagem);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println("Erro ao registrar no log: " + e.getMessage());
        }
    }
    /**
     * Método para fechar o arquivo de log.
     */
    public void fechar() {
        try {
            writer.close();
        } catch (Exception e) {
            System.out.println("Erro ao fechar o arquivo de log: " + e.getMessage());
        }
    }
}
