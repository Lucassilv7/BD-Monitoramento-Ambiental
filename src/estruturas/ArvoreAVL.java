package estruturas;

import model.entidades.Registro;

public class ArvoreAVL {

    class No{

        private int chave;
        private Registro referencia;
        private No esquerda, direita;
        private int altura;

        public No(int chave, Registro referencia) {
            this.chave = chave;
            this.referencia = referencia;
        }
    }

    private No raiz;

    public ArvoreAVL() {
    }
    public ArvoreAVL(No raiz) {
        this.raiz = raiz;
    }

    public void inserir(int chave, Registro registro){
        raiz = inserir(raiz, chave, registro);
    }
    public void remover(int chave, Registro registro){
        raiz = remover(raiz, chave, registro);
    }

    private No inserir(No arvore, int chave, Registro registro){

        if (arvore == null)
            return new No(chave, registro);

        if (chave < arvore.chave)
            arvore.esquerda = inserir(arvore.esquerda, chave, registro);
        else if (chave > arvore.chave)
            arvore.direita = inserir(arvore.direita, chave, registro);
        else
            return arvore;

        arvore.altura = 1 + maior(altura(arvore.esquerda), altura(arvore.direita));

        int fator = fatorBalanceamento(arvore);
        int fatorSubarvoreEsquerda = fatorBalanceamento(arvore.esquerda);
        int fatorSubarvoreDireita = fatorBalanceamento(arvore.direita);

        if (fator > 1 && fatorSubarvoreEsquerda >=0)
            return rotacaoDireitaSimples(arvore);

        if (fator < -1 && fatorSubarvoreDireita <= 0)
            return rotacaoEsquerdaSimples(arvore);

        if (fator > 1 && fatorSubarvoreEsquerda < 0){
            arvore.esquerda = rotacaoEsquerdaSimples(arvore.esquerda);
            return rotacaoDireitaSimples(arvore);
        }

        if (fator < -1 && fatorSubarvoreDireita > 0){
            arvore.direita = rotacaoDireitaSimples(arvore.direita);
            return rotacaoEsquerdaSimples(arvore);
        }

        return arvore;
    }

    private No remover(No arvore, int chave, Registro registro){
        if (arvore == null)
            return arvore;
        if (chave < arvore.chave)
            arvore.esquerda = remover(arvore, chave, registro);
        else if (chave > arvore.chave)
            arvore.direita = remover(arvore, chave, registro);
        else{
            if (arvore.esquerda == null && arvore.direita == null)
                arvore = null;
            else if (arvore.esquerda == null) {
                No temporario = arvore;
                arvore = temporario.direita;
                temporario = null;
            } else if (arvore.direita == null) {
                No temporario = arvore;
                arvore = temporario.esquerda;
                temporario = null;
            }else {
                No temporario = menorChave(arvore.direita);
                arvore.chave = temporario.chave;
                arvore.referencia = temporario.referencia;
                temporario.chave = chave;

                arvore.direita = remover(arvore.direita, temporario.chave, registro);
            }
        }
        if (arvore == null)
            return arvore;

        arvore.altura = 1 + maior(altura(arvore.esquerda), altura(arvore.direita));

        int fator = fatorBalanceamento(arvore);
        int fatorSubarvoreEsquerda = fatorBalanceamento(arvore.esquerda);
        int fatorSubarvoreDireita = fatorBalanceamento(arvore.direita);

        if (fator > 1 && fatorSubarvoreEsquerda >= 0)
            return rotacaoDireitaSimples(arvore);

        if (fator < -1 && fatorSubarvoreDireita <= 0)
            return rotacaoEsquerdaSimples(arvore);

        if (fator > 1 && fatorSubarvoreEsquerda < 0){
            arvore.esquerda = rotacaoEsquerdaSimples(arvore.esquerda);
            return rotacaoDireitaSimples(arvore);
        }
        if (fator < -1 && fatorSubarvoreDireita > 0){
            arvore.direita = rotacaoDireitaSimples(arvore.direita);
            return rotacaoEsquerdaSimples(arvore);
        }

        return arvore;
    }

    private int altura(No arvore){
        if (arvore == null)
            return -1;
        return arvore.altura;
    }

    private int maior(int a, int b){
        return (a > b) ? a : b;
    }

    private int fatorBalanceamento(No arvore){
        if (arvore == null)
            return 0;
        return altura(arvore.esquerda) - altura(arvore.direita);
    }

    private No menorChave(No arvore){
        No temporario = arvore;
        if (temporario == null)
            return null;
        while (temporario.esquerda != null)
            temporario = temporario.esquerda;
        return temporario;
    }

    private No rotacaoDireitaSimples(No y){
        No x = y.esquerda;
        No z = x.direita;

        x.direita = y;
        y.esquerda = z;

        y.altura = maior(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = maior(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    private No rotacaoEsquerdaSimples(No x){
        No y = x.direita;
        No z = y.esquerda;

        y.esquerda = x;
        x.direita = z;

        x.altura = maior(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = maior(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }


}
