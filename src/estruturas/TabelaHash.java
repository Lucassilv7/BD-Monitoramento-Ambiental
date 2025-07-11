package estruturas;

import java.util.List;
import java.util.ArrayList;

public class TabelaHash <E> {
    static class No <E>{
        private E referencia;
        private final int chave;

        public No(E referencia, int chave) {
            this.referencia = referencia;
            this.chave = chave;
        }
    }

    private int m, n;
    private final int TAM_BASE = 7;
    private No<E>[] tabela;

    private final No<E> DELETD = new No<>(null, -1);

    private int redimensionamentos = 0;
    private int colisoes = 0;

    @SuppressWarnings("unchecked")
    public TabelaHash() {
        this.m = TAM_BASE;
        this.tabela = (No<E>[]) new No[TAM_BASE];
        this.n = 0;
    }

    public void inserir(E v, int ch) {
        _inserir(v, ch, false);
    }
    public E buscar(int id){
        return _buscar(id);
    }
    public void remover(int ch){
        _remover(ch);
    }

    public boolean isEmpty() {
        if (n == 0)
            return true;
        else
            return false;
    }

    private int hash(int ch, int k){
        return (ch % this.m + k * (1 + ch %  (this.m - 2))) % this.m;
    }

    private void _inserir(E referencia, int ch, boolean reinserir) {
        int tentativa = 0;
        int h = this.hash(ch, tentativa);
        int primeiroSlotDeletado = -1;


        while(this.tabela[h] != null) {
            if (this.tabela[h].chave == ch)
                return; // Registro já existe
            if (this.tabela[h] == DELETD && primeiroSlotDeletado == -1) {
                primeiroSlotDeletado = h; // Guarda o primeiro slot deletado que encontrar
            }
            colisoes++;
            h = this.hash(ch, ++tentativa);
        }

        if (primeiroSlotDeletado != -1)
            this.tabela[primeiroSlotDeletado] = new No<>(referencia, ch); // Reutiliza o primeiro slot deletado
        else
            this.tabela[h] = new No<>(referencia, ch);

        n++;

        if (!reinserir){
            examinarCarga();
        }
    }

    private E _buscar(int id){

        int tentativa = 0;
        int h = hash(id, tentativa);

        while (this.tabela[h] != null){
            if (this.tabela[h].chave == id) {
                return this.tabela[h].referencia; // Referencia encontrado
            }
            h = hash(id, ++tentativa);
        }

        return null; // Referencia não encontrado
    }

    private void _remover(int ch) {
        int tentativa = 0;
        int h = hash(ch, tentativa);

        while (this.tabela[h] != null) {
            if (this.tabela[h].chave == ch) {
                this.tabela[h] = DELETD; // Registro removido
                n--;
                examinarCarga();
                return;
            }
            h = hash(ch, ++tentativa);
        }
    }

    private void examinarCarga() {
        double carga = (double) n / m;

        if (carga > 0.7)
            reogarnizar(proximoPrimo(m * 2));
        else if (carga < 0.3 && m > TAM_BASE)
            reogarnizar(Math.max(proximoPrimo(m / 2), TAM_BASE));
    }

    @SuppressWarnings("unchecked")
    private void reogarnizar(int novoTamanho) {
        redimensionamentos++;
        No[] antiga = tabela;

        m = novoTamanho;
        tabela = new No[m];
        n = 0;

        for (No no : antiga) {
            if (no != null) {
                _inserir((E) no.referencia, no.chave, true);
            }
        }
    }

    private int proximoPrimo(int num) {
        if (num % 2 == 0) {
            num++;
        }

        while (!ehPrimo(num)) {
            num += 2;
        }
        return num;
    }

    private boolean ehPrimo(int num) {
        if (num < 2) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    public int _getTamnho(){
        return m;
    }
    public double _getCarga() {
        return (double) n /m;
    }
    public int _getRedimensionamentos() {
        return redimensionamentos;
    }
    public int _getColisoes() {
        return colisoes;
    }

}
