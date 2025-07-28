package aplicacao;

import estruturas.Huffman;

import java.util.Map;

public class testet {
    public static void main(String[] args) {
        String texto = "lucas silva";

        Huffman.No raiz = Huffman.construirArvoreHuffman(texto);
        Map<Character, String> codigos = new java.util.HashMap<>();
        Huffman.gerarCodigos(raiz, "", codigos);

        System.out.println("Tabela de Códigos: " + codigos);

        String codificado = Huffman.codificar(texto, codigos);
        System.out.println("Codificado: " + codificado);

        String decodificado = Huffman.decodificar(codificado, raiz);
        System.out.println("Decodificado: " + decodificado);

    }
}
