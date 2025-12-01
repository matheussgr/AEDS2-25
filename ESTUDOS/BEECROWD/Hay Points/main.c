#include <stdio.h>
#include <string.h>

int main() {

    int M, N; 
    scanf("%d %d", &M, &N);

    // vetores do dicionário
    char palavras[10000][25];  // cada palavra até 24 caracteres (padrão do problema)
    int valores[10000];

    // leitura do dicionário
    for (int i = 0; i < M; i++) {
        scanf("%s %d", palavras[i], &valores[i]);
    }

    // leitura das descrições
    for (int i = 0; i < N; i++) {

        int salario = 0;
        char w[25];

        while (1) {
            scanf("%s", w);

            if (strcmp(w, ".") == 0) {
                // fim desta descrição
                break;
            }

            // procura w no dicionário (busca linear)
            for (int j = 0; j < M; j++) {
                if (strcmp(palavras[j], w) == 0) {
                    salario += valores[j];
                    break; // já achou, pode parar
                }
            }
        }

        printf("%d\n", salario);
    }

    return 0;
}
