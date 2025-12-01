#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int prefixo_comum(char *s1, char *s2) {
    int i = 0;
    while (s1[i] != '\0' && s2[i] != '\0' && s1[i] == s2[i]) {
        i++;
    }
    return i;
}

void merge(char **v, char **aux, int esq, int meio, int dir) {
    int i = esq;
    int j = meio + 1;
    int k = esq;

    while (i <= meio && j <= dir) {
        if (strcmp(v[i], v[j]) <= 0) {
            aux[k++] = v[i++];
        } else {
            aux[k++] = v[j++];
        }
    }

    while (i <= meio) {
        aux[k++] = v[i++];
    }

    while (j <= dir) {
        aux[k++] = v[j++];
    }

    for (i = esq; i <= dir; i++) {
        v[i] = aux[i];
    }
}

void merge_sort(char **v, char **aux, int esq, int dir) {
    if (esq >= dir) return;

    int meio = (esq + dir) / 2;
    merge_sort(v, aux, esq, meio);
    merge_sort(v, aux, meio + 1, dir);
    merge(v, aux, esq, meio, dir);
}

int main() {
    int n;

    while (scanf("%d", &n) == 1) {
        char **telefones = (char **) malloc(n * sizeof(char *));
        char **aux = (char **) malloc(n * sizeof(char *));
        int i;

        for (i = 0; i < n; i++) {
            telefones[i] = (char *) malloc(201 * sizeof(char));
            scanf("%s", telefones[i]);   // aqui agora é só %s
        }

        merge_sort(telefones, aux, 0, n - 1);

        int economia = 0;
        for (i = 1; i < n; i++) {
            economia += prefixo_comum(telefones[i - 1], telefones[i]);
        }

        printf("%d\n", economia);

        for (i = 0; i < n; i++) {
            free(telefones[i]);
        }
        free(telefones);
        free(aux);
    }

    return 0;
}