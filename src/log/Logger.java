package log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

public class Logger {

    private BufferedWriter writer, writerMemoria;
    private StringWriter stringWriter;;

    /**
     * Construtor da classe Logger.
     * @param nomeArquivo Nome do arquivo de log.
     */
    public Logger(String nomeArquivo){
        try {
            this.writer = new BufferedWriter(new java.io.FileWriter(nomeArquivo, true));
            stringWriter = new StringWriter();
            writerMemoria = new BufferedWriter(stringWriter);
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
            writerMemoria.write("[" + java.time.LocalDateTime.now() + "] " + mensagem);
            writer.newLine();
            writerMemoria.newLine();
            writer.flush();
            writerMemoria.flush();
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
            writerMemoria.close();
            stringWriter.close();
        } catch (Exception e) {
            System.out.println("Erro ao fechar o arquivo de log: " + e.getMessage());
        }
    }
    /**
     * Método para obter o conteúdo do log em memória.
     * @return Conteúdo do log em memória.
     */
    public String getLogEmMemoria() {
        try {
            writerMemoria.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

}
