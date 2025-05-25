package estruturas;

import java.util.ArrayList;
import java.util.List;

public class ListaEncadeada <E>{

    class No{
        private E valor;
        private No proximo;

        public No(E valor) {
            this.valor = valor;
            this.proximo = null;
        }
    }

    private No cabecote;
    private int tamanho;

    public ListaEncadeada() {
        this.cabecote = null;
        this.tamanho = 0;
    }

    public void adicionarPrimeiro(E valor) {
        No novo = new No(valor);

        if (cabecote == null)
            cabecote = novo;
        else{
            novo.proximo = cabecote;
            cabecote = novo;
        }

        tamanho++;
    }

    public void adicionarUltimo(E valor) {
        No novo = new No(valor);

        if (cabecote == null)
            cabecote = novo;
        else{
            No ponteiro = cabecote;
            while (ponteiro.proximo != null)
                ponteiro = ponteiro.proximo;
            ponteiro.proximo = novo;
        }

        tamanho++;
    }

    public boolean adicionarDepois(E valor, E criterio) {
        No ponteiro = procurarNo(criterio);

        if (ponteiro == null)
            return false;
        else{
            No novo = new No(valor);
            novo.proximo = ponteiro.proximo;
            ponteiro.proximo = novo;
            tamanho++;
            return true;
        }
    }

    public E pegarPrimeiro() {
        if (cabecote == null)
            return null;
        else{
            return cabecote.valor;
        }
    }

    public List<E> listar(){
        No ponteiro = cabecote;
        List<E> lista = new ArrayList<>();

        if (cabecote == null)
            return null;
        else{
            while (ponteiro != null){
                lista.add(ponteiro.valor);
                ponteiro = ponteiro.proximo;
            }
        }

        return lista;
    }

    public E pegarUltimo() {
        if (cabecote == null)
            return null;
        else{
            No ponteiro = cabecote;
            while (ponteiro.proximo != null)
                ponteiro = ponteiro.proximo;
            return ponteiro.valor;
        }
    }

    public E procurar(E criterio) {
        No ponteiro = procurarNo(criterio);

        if (ponteiro == null)
            return null;
        else
            return ponteiro.valor;
    }

    public No procurarNo(E criterio){
        No ponteiro = cabecote;

        while (ponteiro != null){
            if (ponteiro.valor.equals(criterio))
                return ponteiro;
            ponteiro = ponteiro.proximo;
        }
        return null;
    }

    public E removerPrimeiro() {
        No ponteiro = cabecote;
        E dadoRetorno = null;

        if(cabecote != null){
            dadoRetorno = cabecote.valor;
            cabecote = cabecote.proximo;
            ponteiro.proximo = null;
            tamanho--;
        }
        return dadoRetorno;
    }


    public E removerUltimo() {
        E dadoRetorno = null;

        if (cabecote.proximo == null){
            dadoRetorno = cabecote.valor;
            cabecote = null;
            tamanho--;
        } else {
            No ponteiro = cabecote;
            while (ponteiro.proximo.proximo != null)
                ponteiro = ponteiro.proximo;
            dadoRetorno = ponteiro.proximo.valor;
            ponteiro.proximo = null;
            tamanho--;
        }

        return dadoRetorno;
    }

    public E remover(E criterio) {
        No ponteiro = procurarNo(criterio);

        if (ponteiro == null)
            return null;
        else{
            E dadoRetorno = ponteiro.valor;
            cabecote = ponteiro.proximo;
            ponteiro.proximo = null;
            tamanho--;
            return dadoRetorno;
        }
    }
    public int quantidade() {
        return tamanho;
    }
}
