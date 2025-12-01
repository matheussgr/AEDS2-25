#include <stdio.h>
#include <stdlib.h>

typedef struct No {
    int elemento;
    struct No *esq;
    struct No *dir;
} No;

typedef struct {
    No *raiz;
} ABB;

No* novoNo(int x) {
    No *n = (No*) malloc(sizeof(No));
    n->elemento = x;
    n->esq = NULL;
    n->dir = NULL;
    return n;
}

void initABB(ABB *arvore) {
    arvore->raiz = NULL;
}

No* inserirRec(No *i, int x) {
    if (i == NULL) {
        i = novoNo(x);
    } else if (i->elemento > x) {
        i->esq = inserirRec(i->esq, x);
    } else if (i->elemento < x) {
        i->dir = inserirRec(i->dir, x);
    } else {
        // elemento já existe, não faz nada
    }
    return i;
}

void inserir(ABB *arvore, int x) {
    arvore->raiz = inserirRec(arvore->raiz, x);
}

void caminharPre(No *i) {
    if (i != NULL) {
        printf("%d ", i->elemento);
        caminharPre(i->esq);
        caminharPre(i->dir);
    }
}

void caminharCentral(No *i) {
    if (i != NULL) {
        caminharCentral(i->esq);
        printf("%d ", i->elemento);
        caminharCentral(i->dir);
    }
}

void caminharPos(No *i) {
    if (i != NULL) {
        caminharPos(i->esq);
        caminharPos(i->dir);
        printf("%d ", i->elemento);
    }
}

int main() {
    int casos;
    if (scanf("%d", &casos) != 1) return 0;

    int aux = casos;
    int x = 1;

    while (aux > 0) {
        ABB arvore;
        initABB(&arvore);

        int qtd;
        scanf("%d", &qtd);

        while (qtd > 0) {
            int valor;
            scanf("%d", &valor);
            inserir(&arvore, valor);
            qtd--;
        }

        printf("Case %d:\n", x);
        printf("Pre.: ");
        caminharPre(arvore.raiz);
        printf("\n");

        printf("In..: ");
        caminharCentral(arvore.raiz);
        printf("\n");

        printf("Post: ");
        caminharPos(arvore.raiz);
        printf("\n\n");

        aux--;
        x++;
    }

    return 0;
}