#include <stdio.h>
#include <stdlib.h>

// ======================== ESTRUTURA DO NÓ DA ÁRVORE ========================
typedef struct No {
    int elemento;
    struct No *esq;
    struct No *dir;
} No;

// ======================== CRIAR NÓ =========================================
No* criarNo(int elemento) {
    No *novo = (No*) malloc(sizeof(No));
    novo->elemento = elemento;
    novo->esq = NULL;
    novo->dir = NULL;
    return novo;
}

// ======================== INSERIR NA ÁRVORE (ABB) ==========================
// Retorna a nova raiz da subárvore após a inserção
No* inserir(No *raiz, int x) {
    if (raiz == NULL) {
        raiz = criarNo(x);
    } else if (x < raiz->elemento) {
        raiz->esq = inserir(raiz->esq, x);
    } else if (x > raiz->elemento) {
        raiz->dir = inserir(raiz->dir, x);
    } else {
        // se x == raiz->elemento, não faz nada (igual ao Java)
    }
    return raiz;
}

// ======================== PERCURSO EM NÍVEL ================================
// numNos = quantidade máxima de nós (usamos para tamanho da fila)
void caminharNivel(No *raiz, int numNos) {
    if (raiz == NULL) {
        printf("\n");
        return;
    }

    // Fila de ponteiros para nós
    No **fila = (No**) malloc(numNos * sizeof(No*));
    int inicio = 0;
    int fim = 0;

    // coloca a raiz na fila
    fila[fim++] = raiz;

    int primeiro = 1; // para controlar os espaços

    while (inicio < fim) {
        No *atual = fila[inicio++]; // tira da fila

        if (!primeiro) {
            printf(" ");
        }
        printf("%d", atual->elemento);
        primeiro = 0;

        // coloca filhos na fila
        if (atual->esq != NULL) {
            fila[fim++] = atual->esq;
        }
        if (atual->dir != NULL) {
            fila[fim++] = atual->dir;
        }
    }

    printf("\n");
    free(fila);
}

// ======================== LIBERAR MEMÓRIA DA ÁRVORE ========================
void liberarArvore(No *raiz) {
    if (raiz != NULL) {
        liberarArvore(raiz->esq);
        liberarArvore(raiz->dir);
        free(raiz);
    }
}

// ======================== MAIN ============================================
int main() {
    int numCasos;
    scanf("%d", &numCasos);

    for (int caso = 1; caso <= numCasos; caso++) {

        int n;
        scanf("%d", &n);

        No *raiz = NULL; // árvore começa vazia

        // lê n números e insere na árvore
        for (int i = 0; i < n; i++) {
            int num;
            scanf("%d", &num);
            raiz = inserir(raiz, num);
        }

        printf("Case %d:\n", caso);
        caminharNivel(raiz, n); // n é o máximo de nós possíveis
        printf("\n");

        liberarArvore(raiz); // boa prática, libera memória
    }

    return 0;
}
