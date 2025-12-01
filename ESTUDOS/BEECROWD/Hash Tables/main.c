#include <stdio.h>

int main() {

    int N;  // número de casos de teste
    scanf("%d", &N);

    for (int caso = 0; caso < N; caso++) {

        int M, C;
        scanf("%d %d", &M, &C); // M = tamanho da tabela, C = quantidade de chaves

        // tabela[i][j] = j-ésimo valor armazenado na posição i da hash
        int tabela[M][C];

        // tam[i] = quantidade de elementos já ocupados na posição i
        int tam[M];

        // inicializa os tamanhos com 0
        for (int i = 0; i < M; i++) {
            tam[i] = 0;
        }

        // lê C valores e insere na hash
        for (int k = 0; k < C; k++) {
            int valor;
            scanf("%d", &valor);

            // função hash (evitando índice negativo)
            int idx = valor % M;
            if (idx < 0) {
                idx += M;
            }

            // insere o valor no "vetor" da posição idx
            tabela[idx][tam[idx]] = valor;
            tam[idx]++;
        }

        // linha em branco entre casos (menos antes do primeiro)
        if (caso > 0) {
            printf("\n");
        }

        // imprime a tabela
        for (int i = 0; i < M; i++) {
            printf("%d -> ", i);

            // imprime todos os elementos já inseridos nessa posição
            for (int j = 0; j < tam[i]; j++) {
                printf("%d -> ", tabela[i][j]);
            }

            // imprime a barra invertida no final da linha
            printf("\\\n");
        }
    }

    return 0;
}
