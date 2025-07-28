package estruturas;

import util.ModoHash;

public class TabelaHash<E> {
    static class No <E>{
        private E referencia;
        private final int chave;
        private No<E> proximo;

        public No(E referencia, int chave, No<E> proximo) {
            this.referencia = referencia;
            this.chave = chave;
            if (proximo != null) {
                this.proximo = proximo;
            } else {
                this.proximo = null;
            }
        }
    }

    private int m, n;
    private final int TAM_BASE = 7;
    private No<E>[] tabela;

    private final No<E> DELETD = new No<>(null, -1, null);

    private int redimensionamentos = 0;
    private int colisoes = 0;
    private ModoHash modoHash;

    @SuppressWarnings("unchecked")
    public TabelaHash(ModoHash modoHash) {
        this.m = TAM_BASE;
        this.tabela = (No<E>[]) new No[TAM_BASE];
        this.n = 0;
        this.modoHash = modoHash;
    }

    public void inserirDuplo(E v, int ch) {
        _inserir(v, ch, false);
    }
    public void inserirInicioExterior(E v, int ch) {
        __inserirInicio(v, ch, false);
    }
    public void inserirFinalExterior(E v, int ch) {
        __inserirFinal(v, ch, false);
    }
    public E buscarDuplo(int id){
        return _buscar(id);
    }
    public E buscarExterior(int id){
        return __buscar(id);
    }
    public void removerDuplo(int ch){
        _remover(ch);
    }
    public void removerExterior(int ch){
        __remover(ch);
    }

    public boolean isEmpty() {
        if (n == 0)
            return true;
        else
            return false;
    }

    private int hashDuplo(int ch, int k){
        return (ch % this.m + k * (1 + ch %  (this.m - 2))) % this.m;
    }
    private int hashExterior(int k){return k % this.m;}

    private void _inserir(E referencia, int ch, boolean reinserir) {
        int tentativa = 0;
        int h = this.hashDuplo(ch, tentativa);
        int primeiroSlotDeletado = -1;


        while(this.tabela[h] != null) {
            if (this.tabela[h].chave == ch)
                return; // Registro já existe
            if (this.tabela[h] == DELETD && primeiroSlotDeletado == -1) {
                primeiroSlotDeletado = h; // Guarda o primeiro slot deletado que encontrar
            }
            colisoes++;
            h = this.hashDuplo(ch, ++tentativa);
        }

        if (primeiroSlotDeletado != -1)
            this.tabela[primeiroSlotDeletado] = new No<>(referencia, ch, null); // Reutiliza o primeiro slot deletado
        else
            this.tabela[h] = new No<>(referencia, ch, null);

        n++;

        if (!reinserir){
            examinarCarga();
        }
    }
    private void __inserirInicio(E referencia, int ch, boolean reinserir){
        int h = hashExterior(ch);
        No<E> no = tabela[h];

        while (no != null){
            if (no.chave == ch)
                return;
            colisoes++;
            no = no.proximo;
        }
        if (no == null){
            no = new No<>(referencia, ch, tabela[h]);
            tabela[h] = no;
        }
        n++;

        if (!reinserir) {
            examinarCarga();
        }
    }
    private void __inserirFinal(E referencia, int ch, boolean reinserir){
        int c = hashExterior(ch);

        No<E> atual = tabela[c];
        No<E> anterior = null;

        if(atual == null)
            tabela[c] = new No<>(referencia, ch, null);
        else {
            while (atual != null) {
                if (atual.chave == ch)
                    return; // Registro já existe
                colisoes++;
                anterior = atual;
                atual = atual.proximo;
            }
            if (atual == null){
                No<E> novo = new No<>(referencia, ch, null);
                anterior.proximo = novo;
            }
        }
        n++;
        if (!reinserir) {
            examinarCarga();
        }
    }

    private E _buscar(int id){

        int tentativa = 0;
        int h = hashDuplo(id, tentativa);

        while (this.tabela[h] != null){
            if (this.tabela[h].chave == id) {
                return this.tabela[h].referencia; // Referencia encontrado
            }
            h = hashDuplo(id, ++tentativa);
        }

        return null; // Referencia não encontrado
    }
    private E __buscar(int id){
        int h = hashExterior(id);
        No<E> no = tabela[h];

        while (no != null) {
            if (no.chave == id) {
                return no.referencia; // Referencia encontrado
            }
            no = no.proximo;
        }
        return null;
    }

    private void _remover(int ch) {
        int tentativa = 0;
        int h = hashDuplo(ch, tentativa);

        while (this.tabela[h] != null) {
            if (this.tabela[h].chave == ch) {
                this.tabela[h] = DELETD; // Registro removido
                n--;
                examinarCarga();
                return;
            }
            h = hashDuplo(ch, ++tentativa);
        }
    }
    private void __remover(int ch) {
        int h = hashExterior(ch);
        No<E> no = tabela[h];
        No<E> anterior = null;

        while (no != null) {
            if (no.chave == ch) {
                if (anterior == null) {
                    tabela[h] = no.proximo; // Remove o primeiro nó
                } else {
                    anterior.proximo = no.proximo; // Remove o nó do meio ou final
                }
                n--;
                examinarCarga();
                return;
            }
            anterior = no;
            no = no.proximo;
        }
    }

    private void examinarCarga() {
        double carga = (double) n / m;

        if (this.modoHash == ModoHash.DUPLO) {
            if (carga > 0.7)
                reorganizar(proximoPrimo(m * 2));
            else if (carga < 0.3 && m > TAM_BASE)
                reorganizar(Math.max(proximoPrimo(m / 2), TAM_BASE));
        } else if (this.modoHash == ModoHash.EXTERIOR) {
            if (carga > 1.5) {
                reorganizar(proximoPrimo(m * 2));
            } else if (carga < 0.5 && m > TAM_BASE) {
                reorganizar(Math.max(proximoPrimo(m / 2), TAM_BASE));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void reorganizar(int novoTamanho) {
        redimensionamentos++;
        No[] antiga = tabela;

        m = novoTamanho;
        tabela = new No[m];
        n = 0;

        for (No no : antiga) {
            // Se for encadeamento exterior, precisa percorrer a lista
            if (no != null && this.modoHash != ModoHash.DUPLO) {
                No<E> atual = no;
                while(atual != null) {
                    __inserirInicio(atual.referencia, atual.chave, true);
                    atual = atual.proximo;
                }
            }
            else if (no != null) {
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
