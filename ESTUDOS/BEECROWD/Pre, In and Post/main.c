#include <stdio.h>
#include <string.h>

char pre[55], in[55];

void gerarPosOrdem(int preIni, int inIni, int tam) {
    if (tam <= 0) return;

    char raiz = pre[preIni];

    // Achar posição da raiz na in-ordem
    int k = 0;
    while (k < tam && in[inIni + k] != raiz) {
        k++;
    }

    // parte esquerda
    gerarPosOrdem(preIni + 1, inIni, k);

    // parte direita
    gerarPosOrdem(preIni + k + 1, inIni + k + 1, tam - k - 1);

    // imprime a raiz
    printf("%c", raiz);
}

int main() {
    int C;
    scanf("%d", &C);

    while (C--) {
        scanf("%s %s", pre, in);
        gerarPosOrdem(0, 0, strlen(pre));
        printf("\n");
    }

    return 0;
}
