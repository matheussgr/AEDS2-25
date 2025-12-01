#include <stdio.h>
#include <string.h>

char in[55], pos[55];

void gerarPre(int inIni, int posIni, int tam) {
    if (tam <= 0) return;

    // raiz = último da pós-ordem
    char raiz = pos[posIni + tam - 1];

    // imprime a raiz primeiro (pré-ordem)
    printf("%c", raiz);

    // encontrar raiz na in-ordem
    int k = 0;
    while (k < tam && in[inIni + k] != raiz) {
        k++;
    }

    // esquerda
    gerarPre(inIni, posIni, k);

    // direita
    gerarPre(inIni + k + 1, posIni + k, tam - k - 1);
}

int main() {
    while (scanf("%s %s", in, pos) != EOF) {
        gerarPre(0, 0, strlen(in));
        printf("\n");
    }
    return 0;
}
