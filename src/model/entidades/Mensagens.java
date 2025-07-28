package model.entidades;

import estruturas.Huffman;

import java.util.HashMap;
import java.util.Map;

public class Mensagens {

    private String identificador;
    private String conteudo;
    private Huffman.No raizIden, raizCont;
    private Map<Character, String> codigosIden, codigosCont;

    public Mensagens(String identificador, String conteudo) {
        this.identificador = _codificarIden(identificador);
        this.conteudo = _codificarConteudo(conteudo);
    }

    public Mensagens() {
    }

    public String getIdentificador() {
        return identificador;
    }

    public String getConteudo() {
        return conteudo;
    }


    public Huffman.No getRaizIden() {
        return raizIden;
    }

    public Huffman.No getRaizCont() {
        return raizCont;
    }

    public Map<Character, String> getCodigosIden() {
        return codigosIden;
    }

    public Map<Character, String> getCodigosCont() {
        return codigosCont;
    }

    private String _codificarIden(String texto) {
        this.raizIden = Huffman.construirArvoreHuffman(texto);
        this.codigosIden = new HashMap<>();
        Huffman.gerarCodigos(raizIden, "", codigosIden);
        return Huffman.codificar(texto, codigosIden);
    }
    private String _codificarConteudo(String texto) {
        this.raizCont = Huffman.construirArvoreHuffman(texto);
        this.codigosCont = new HashMap<>();
        Huffman.gerarCodigos(raizCont, "", codigosCont);
        return Huffman.codificar(texto, codigosCont);
    }
}
