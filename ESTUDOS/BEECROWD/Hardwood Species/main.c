#include <stdio.h>
#include <string.h>

int main() {
    char linha[200];
    int T;

    // Lê a primeira linha com o número de casos
    if (fgets(linha, sizeof(linha), stdin) == NULL) {
        return 0;
    }
    sscanf(linha, "%d", &T);

    // Depois de T vem uma linha em branco
    fgets(linha, sizeof(linha), stdin);

    for (int caso = 0; caso < T; caso++) {

        // Vetores para armazenar espécies e contagens
        // Máximo de 10000 espécies, cada nome com até 50 caracteres (pode ajustar)
        char especie[10000][51];
        int cont[10000];
        int nEspecies = 0;
        int total = 0;

        while (1) {
            if (fgets(linha, sizeof(linha), stdin) == NULL) {
                // Fim de arquivo
                break;
            }

            // Remove o '\n' do final, se existir
            int len = strlen(linha);
            if (len > 0 && linha[len - 1] == '\n') {
                linha[len - 1] = '\0';
                len--;
            }

            // Linha em branco -> fim do caso
            if (len == 0) {
                break;
            }

            // Procurar a espécie já existente no vetor
            int pos = -1;
            for (int i = 0; i < nEspecies; i++) {
                if (strcmp(especie[i], linha) == 0) {
                    pos = i;
                    break;
                }
            }

            if (pos == -1) {
                // Espécie nova
                strcpy(especie[nEspecies], linha);
                cont[nEspecies] = 1;
                nEspecies++;
            } else {
                // Já existia
                cont[pos]++;
            }

            total++;
        }

        // Ordenar as espécies em ordem alfabética (selection sort)
        for (int i = 0; i < nEspecies - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < nEspecies; j++) {
                if (strcmp(especie[j], especie[menor]) < 0) {
                    menor = j;
                }
            }

            // Troca os nomes
            if (menor != i) {
                char tmpNome[51];
                strcpy(tmpNome, especie[i]);
                strcpy(especie[i], especie[menor]);
                strcpy(especie[menor], tmpNome);

                // Troca também as contagens pra ficar alinhado
                int tmpCont = cont[i];
                cont[i] = cont[menor];
                cont[menor] = tmpCont;
            }
        }

        // Linha em branco entre casos (menos antes do primeiro)
        if (caso > 0) {
            printf("\n");
        }

        // Imprime resultado
        for (int i = 0; i < nEspecies; i++) {
            double perc = (cont[i] * 100.0) / total;
            printf("%s %.4f\n", especie[i], perc);
        }
    }

    return 0;
}
