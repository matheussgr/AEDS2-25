#include <stdio.h>
#include <string.h>

typedef struct {
    char cor[10];
    char tamanho[3];
    char nome[100];
    int nCor;
    int nTam;
} Camisa;

int nmrCor(char *cr) {
    if (strcmp(cr, "vermelho") == 0) {
        return 1;
    }
    return 0;
}

int nmrTam(char *cr) {
    if (strcmp(cr, "P") == 0) {
        return 0;
    } else if (strcmp(cr, "M") == 0) {
        return 1;
    }
    return 2;  // G
}

int comparaNome(char *a, char *b) {
    int i;
    int n = (int)(strlen(a) < strlen(b) ? strlen(a) : strlen(b));

    for (i = 0; i < n; i++) {
        char c1 = a[i];
        char c2 = b[i];
        if (c1 != c2) {
            return c1 - c2;
        }
    }

    return (int)strlen(a) - (int)strlen(b);
}

int comparar(Camisa *c1, Camisa *c2) {
    if (c1->nCor != c2->nCor) {
        return c1->nCor - c2->nCor;     
    }
    if (c1->nTam != c2->nTam) {
        return c1->nTam - c2->nTam;     
    }
    return comparaNome(c1->nome, c2->nome); 
}

void ordenar(Camisa cms[], int x) {
    int i, j;
    for (i = 0; i < x - 1; i++) {
        int menor = i;
        for (j = i + 1; j < x; j++) {
            if (comparar(&cms[j], &cms[menor]) < 0) {
                menor = j;
            }
        }
        if (menor != i) {
            Camisa aux = cms[i];
            cms[i] = cms[menor];
            cms[menor] = aux;
        }
    }
}

void printar(Camisa c[], int qtd) {
    int i;
    for (i = 0; i < qtd; i++) {
        printf("%s %s %s\n", c[i].cor, c[i].tamanho, c[i].nome);
    }
}

void ajustarString(char *s) {
    int len = (int)strlen(s);
    if (len > 0 && s[len - 1] == '\n') {
        s[len - 1] = '\0';
    }
}

int main() {
    int qtd;

    scanf("%d", &qtd);

    while (qtd != 0) {
        Camisa cms[1000];
        int aux;

        getchar();

        for (aux = 0; aux < qtd; aux++) {
            char nome[100];
            char corTam[50];
            char cor[10];
            char tam[3];

            fgets(nome, sizeof(nome), stdin);
            ajustarString(nome);

            fgets(corTam, sizeof(corTam), stdin);
            ajustarString(corTam);

            sscanf(corTam, "%s %s", cor, tam);

            cms[aux].nCor = nmrCor(cor);
            cms[aux].nTam = nmrTam(tam);
            strcpy(cms[aux].cor, cor);
            strcpy(cms[aux].tamanho, tam);
            strcpy(cms[aux].nome, nome);
        }

        ordenar(cms, qtd);
        printar(cms, qtd);

        printf("\n\n");

        scanf("%d", &qtd);
    }

    return 0;
}