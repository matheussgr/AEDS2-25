#include <stdio.h>
#include <string.h>

// ---------------------------------------------------------------------
// Função que verifica se o ser "novo" é melhor do que o "atual" (melhor)
// Retorna 1  -> novo é melhor
// Retorna 0  -> atual continua sendo o melhor
// ---------------------------------------------------------------------
int ehMelhor(char nomeNovo[], int nivelNovo, int deusNovo, int mortesNovo,
             char nomeMelhor[], int nivelMelhor, int deusMelhor, int mortesMelhor) {

    // 1) Maior nível vence
    if (nivelNovo != nivelMelhor) {
        return (nivelNovo > nivelMelhor);
    }

    // 2) Se nível empata, maior valor de "deus" vence
    if (deusNovo != deusMelhor) {
        return (deusNovo > deusMelhor);
    }

    // 3) Se ainda empata, menor quantidade de mortes vence
    if (mortesNovo != mortesMelhor) {
        return (mortesNovo < mortesMelhor);
    }

    // 4) Se tudo acima empatar, compara o nome em ordem alfabética
    // strcmp < 0  -> nomeNovo vem antes de nomeMelhor
    if (strcmp(nomeNovo, nomeMelhor) < 0) {
        return 1;
    } else {
        return 0;
    }
}

int main() {
    int quantidadeSeres;

    // Enquanto ainda for possível ler um inteiro (N), processa um caso de teste
    while (scanf("%d", &quantidadeSeres) == 1) {

        char melhorNome[105];
        int melhorNivel = 0;
        int melhorDeus = 0;
        int melhorMortes = 0;

        // Vamos usar uma flag pra saber se já temos um "melhor" inicial
        int temMelhor = 0;

        for (int i = 0; i < quantidadeSeres; i++) {

            char nome[105];
            int nivel, deus, mortes;

            // Lê os dados do ser atual
            // nome (string sem espaço) + 3 inteiros
            scanf("%s %d %d %d", nome, &nivel, &deus, &mortes);

            if (!temMelhor) {
                // Primeiro ser sempre vira o melhor inicialmente
                strcpy(melhorNome, nome);
                melhorNivel = nivel;
                melhorDeus = deus;
                melhorMortes = mortes;
                temMelhor = 1;
            } else if (ehMelhor(nome, nivel, deus, mortes,
                                melhorNome, melhorNivel, melhorDeus, melhorMortes)) {
                // Se o ser atual for melhor, substitui o campeão
                strcpy(melhorNome, nome);
                melhorNivel = nivel;
                melhorDeus = deus;
                melhorMortes = mortes;
            }
        }

        // Imprime o melhor ser deste caso de teste
        printf("%s\n", melhorNome);
    }

    return 0;
}
