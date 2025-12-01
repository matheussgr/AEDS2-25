#include <stdio.h>
#include <string.h>

int main() {

    int numCasos;
    scanf("%d", &numCasos);   // lê o número de casos

    for (int caso = 0; caso < numCasos; caso++) {

        int numLinhas;
        scanf("%d", &numLinhas);   // quantidade de linhas deste caso

        int hashTotal = 0;

        // Para cada linha deste caso
        for (int linha = 0; linha < numLinhas; linha++) {

            char palavra[200];     // pode ajustar se quiser maior
            scanf("%s", palavra);  // lê a string

            int tamanho = strlen(palavra);

            // Para cada caractere da palavra
            for (int i = 0; i < tamanho; i++) {

                char letraAtual = palavra[i];

                // valor da letra: 'A' = 65 → 'A' - 'A' = 0, 'B' - 'A' = 1, etc.
                int valorLetra = letraAtual - 'A';

                // soma do hash exatamente como no Java
                hashTotal += i + linha + valorLetra;
            }
        }

        printf("%d\n", hashTotal);
    }

    return 0;
}
