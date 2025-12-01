#include <stdio.h>
#include <stdlib.h>

// ======================== ESTRUTURA DO NÓ DA ÁRVORE ========================
// Cada nó guarda:
// - um caractere (elemento)
// - ponteiro para filho da esquerda
// - ponteiro para filho da direita
typedef struct No {
    char elemento;
    struct No *esq;
    struct No *dir;
} No;

// ======================== FUNÇÃO PARA CRIAR UM NÓ ===========================
No* criarNo(char elemento) {
    No *novo = (No*) malloc(sizeof(No));
    novo->elemento = elemento;
    novo->esq = NULL;
    novo->dir = NULL;
    return novo;
}

// ======================== INSERIR NA ARVORE (RECURSIVO) =====================
// Recebe a raiz atual (raiz ou subárvore) e o caractere a ser inserido.
// Retorna a nova raiz dessa subárvore (pode mudar se estiver vazia).
No* inserir(No *raiz, char x) {
    if (raiz == NULL) {
        // se a árvore/subárvore está vazia, cria nó novo
        raiz = criarNo(x);
    } else if (x < raiz->elemento) {
        // vai para a esquerda
        raiz->esq = inserir(raiz->esq, x);
    } else if (x > raiz->elemento) {
        // vai para a direita
        raiz->dir = inserir(raiz->dir, x);
    } else {
        // se x == raiz->elemento, não faz nada (não insere duplicado)
    }
    return raiz;
}

// ======================== CAMINHAMENTO EM ORDEM (INFIXO) ====================
void caminharCentral(No *raiz) {
    if (raiz != NULL) {
        caminharCentral(raiz->esq);
        printf("%c ", raiz->elemento);
        caminharCentral(raiz->dir);
    }
}

// ======================== CAMINHAMENTO PRÉ-ORDEM ============================
void caminharPre(No *raiz) {
    if (raiz != NULL) {
        printf("%c ", raiz->elemento);
        caminharPre(raiz->esq);
        caminharPre(raiz->dir);
    }
}

// ======================== CAMINHAMENTO PÓS-ORDEM ============================
void caminharPos(No *raiz) {
    if (raiz != NULL) {
        caminharPos(raiz->esq);
        caminharPos(raiz->dir);
        printf("%c ", raiz->elemento);
    }
}

// ======================== PESQUISAR NA ÁRVORE (RECURSIVO) ===================
int pesquisar(No *raiz, char x) {
    if (raiz == NULL) {
        return 0;                   // não achou
    } else if (x == raiz->elemento) {
        return 1;                   // achou
    } else if (x < raiz->elemento) {
        return pesquisar(raiz->esq, x);
    } else {
        return pesquisar(raiz->dir, x);
    }
}

// ======================== PROGRAMA PRINCIPAL ================================
int main() {
    No *raiz = NULL;        // começa com árvore vazia
    char linha[100];

    // Lê linha por linha até o EOF (fim de arquivo)
    while (fgets(linha, sizeof(linha), stdin) != NULL) {

        // CASO "I x"  → inserir
        if (linha[0] == 'I' && linha[1] == ' ') {
            char letra = linha[2];          // caractere depois do espaço
            raiz = inserir(raiz, letra);

        // CASO "INFIXA" → caminhamento central
        } else if (linha[0] == 'I' && linha[1] == 'N') {
            caminharCentral(raiz);
            printf("\n");

        // CASO "PREFIXA" → caminhamento pré-ordem
        } else if (linha[0] == 'P' && linha[1] == 'R') {
            caminharPre(raiz);
            printf("\n");

        // CASO "POSFIXA" → caminhamento pós-ordem
        } else if (linha[0] == 'P' && linha[1] == 'O') {
            caminharPos(raiz);
            printf("\n");

        // CASO "P x" → pesquisar
        } else if (linha[0] == 'P' && linha[1] == ' ') {
            char letra = linha[2];
            int achou = pesquisar(raiz, letra);

            if (achou) {
                printf("%c existe\n", letra);
            } else {
                printf("%c nao existe\n", letra);
            }
        }
    }

    return 0;
}
