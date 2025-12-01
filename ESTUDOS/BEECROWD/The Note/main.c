#include <stdio.h>

int main() {

    int N, K;
    scanf("%d %d", &N, &K);

    // freq[x] diz quantas vezes a nota x apareceu
    int freq[100001] = {0};

    // leitura das notas
    for (int i = 0; i < N; i++) {
        int nota;
        scanf("%d", &nota);

        if (nota >= 0 && nota <= 100000) {
            freq[nota]++;
        }
    }

    long long soma = 0;

    // percorre notas de cima para baixo
    for (int nota = 100000; nota >= 0 && K > 0; nota--) {
        while (freq[nota] > 0 && K > 0) {
            soma += nota;
            freq[nota]--;
            K--;
        }
    }

    printf("%lld\n", soma);

    return 0;
}
