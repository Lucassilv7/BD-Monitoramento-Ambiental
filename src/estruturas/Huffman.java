package estruturas;

import java.util.HashMap;
import java.util.Map;

public class Huffman {

    public static class No{
        char caractere;
        int frequencia;
        No esq, dir;

        public No(char caractere, int frequencia) {
            this.caractere = caractere;
            this.frequencia = frequencia;
        }

        public No(int frequencia, No esquerdo, No direito) {
            this.caractere = '\0'; // nó interno
            this.frequencia = frequencia;
            this.esq = esquerdo;
            this.dir = direito;
        }

        public boolean isFolha() {
            return esq == null && dir == null;
        }
    }
    static class MinHeap{
        No[] heap;
        int tamanho;

        public MinHeap(int capacidade) {
            heap = new No[capacidade];
            tamanho = 0;
        }

        public int _getTamanho() {
            return tamanho;
        }

        // Constrói o heap usando o algoritmo dos nós internos
        public void construir(){
            for (int i = (tamanho / 2) - 1; i >= 0; i--) {
                descer(i);
            }
        }

        public void inserir(No no){
            heap[tamanho] = no;
            subir(tamanho);
            tamanho++;
        }
        public No removerMin() {
            if (tamanho == 0)
                return null;
            No min = heap[0];
            heap[0] = heap[--tamanho];
            descer(0);
            return min;
        }
        private void subir(int i) {
            while (i > 0) {
                int pai = (i - 1) / 2;
                if (heap[i].frequencia < heap[pai].frequencia) {
                    trocar(i, pai);
                    i = pai;
                } else {
                    break;
                }
            }
        }
        private void descer(int i){
            int j = 2 * i + 1; // filho esquerdo
            if (j < tamanho) { // existe filho esquerdo
                // se há filho direito também, pegar o menor dos dois
                if (j < tamanho - 1 && heap[j + 1].frequencia < heap[j].frequencia) {
                    j++;
                }
                // se o filho for menor que o pai, trocar
                if (heap[j].frequencia < heap[i].frequencia) {
                    trocar(i, j);
                    descer(j); // chamada recursiva
                }
            }
        }
        private void trocar(int i, int j) {
            No temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }

    public static Map<Character, Integer> calcularFreqencias(String texto){
        Map<Character, Integer> frequencias = new HashMap<>();

        for (char caracteres : texto.toCharArray()){
            // Verifica se o caractere já existe no mapa
            if (frequencias.containsKey(caracteres)) {
                // Se já existe, incrementa o valor associado
                int valorAtual = frequencias.get(caracteres);
                frequencias.put(caracteres, valorAtual + 1);
            } else {
                // Se não existe, coloca o valor inicial 1
                frequencias.put(caracteres, 1);
            }
        }

        return frequencias;
    }

    public static No construirArvoreHuffman(String texto){
        Map<Character, Integer> frequencias = calcularFreqencias(texto);

        // Cria heap com capacidade igual ao número de caracteres únicos
        MinHeap heap = new MinHeap(frequencias.size());

        for (Map.Entry<Character, Integer> entry : frequencias.entrySet())
            heap.inserir(new No(entry.getKey(), entry.getValue()));

        // Constrói o heap com os nós internos
        heap.construir();

        // Combina (em z) os dois menores repetidamente
        while (heap._getTamanho() > 1) {
            No x = heap.removerMin();
            No y = heap.removerMin();

            No z = new No(x.frequencia + y.frequencia, x, y);
            heap.inserir(z);
        }

        // Retorna a raiz da árvore Huffman
        return heap.removerMin();
    }
    public static void gerarCodigos(No no, String codigo, Map<Character, String> codigos) {

        if (no == null) {
            return;
        }
        if (no.isFolha()) {
            codigos.put(no.caractere, codigo);
            return;
        }
        gerarCodigos(no.esq, codigo + "0", codigos);
        gerarCodigos(no.dir, codigo + "1", codigos);

    }
    public static String codificar(String texto, Map<Character, String> codigos) {
        StringBuilder codificada = new StringBuilder();
        for (char c : texto.toCharArray()) {
            codificada.append(codigos.get(c));
        }
        return codificada.toString();
    }
    public static String decodificar(String codificado, No raiz) {
        StringBuilder decodificada = new StringBuilder();
        No atual = raiz;

        for (char bit : codificado.toCharArray()) {
            atual = (bit == '0') ? atual.esq : atual.dir;

            if (atual.isFolha()) {
                decodificada.append(atual.caractere);
                atual = raiz; // Volta para a raiz
            }
        }

        return decodificada.toString();
    }

    public static double taxaDeCompressao(String textoOriginal, String textoCodificado) {
        if (textoOriginal.isEmpty()) {
            return 0.0; // Evita divisão por zero
        }

        double tamanhoOriginal = textoOriginal.length() * 16; // Tamanho original em bits
        double tamanhoCodificado = textoCodificado.length() * 16; // Tamanho codificado em bits
        return tamanhoCodificado / tamanhoOriginal;
    }
}
