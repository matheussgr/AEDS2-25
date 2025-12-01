#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int comparaCodigos(char *s1, char *s2) {
    int n1 = (int)strlen(s1);
    int n2 = (int)strlen(s2);
    int n = n1 < n2 ? n1 : n2;

    for (int i = 0; i < n; i++) {
        char c1 = s1[i];
        char c2 = s2[i];
        if (c1 != c2) {
            return c1 - c2;
        }
    }

    return n1 - n2;
}

typedef struct {
    char codigo[16];
    char origem[64];
    char destino[64];
    char data[16];
    char hora[16];
} Voo;

typedef struct No {
    Voo elemento;
    struct No *esq, *dir;
} No;

No *inserirRec(No *i, Voo x) {
    if (i == NULL) {
        No *novo = (No *)malloc(sizeof(No));
        novo->elemento = x;
        novo->esq = novo->dir = NULL;
        return novo;
    } else if (comparaCodigos(i->elemento.codigo, x.codigo) > 0) {
        i->esq = inserirRec(i->esq, x);
    } else if (comparaCodigos(i->elemento.codigo, x.codigo) < 0) {
        i->dir = inserirRec(i->dir, x);
    }
    return i;
}

void caminharPos(No *i) {
    if (i != NULL) {
        caminharPos(i->esq);
        caminharPos(i->dir);
        printf("%s %s %s %s %s\n",
               i->elemento.codigo,
               i->elemento.origem,
               i->elemento.destino,
               i->elemento.data,
               i->elemento.hora);
    }
}

int main() {
    No *raiz = NULL;
    char linha[256];

    while (fgets(linha, sizeof(linha), stdin) != NULL) {
        linha[strcspn(linha, "\r\n")] = '\0';
        if (strcmp(linha, "FIM") == 0) {
            break;
        }

        Voo v;
        char *token = strtok(linha, ",");
        if (!token) continue;
        strcpy(v.codigo, token);

        token = strtok(NULL, ",");
        strcpy(v.origem, token);

        token = strtok(NULL, ",");
        strcpy(v.destino, token);

        token = strtok(NULL, ",");
        strcpy(v.data, token);

        token = strtok(NULL, ",");
        strcpy(v.hora, token);

        raiz = inserirRec(raiz, v);
    }

    caminharPos(raiz);
    return 0;
}