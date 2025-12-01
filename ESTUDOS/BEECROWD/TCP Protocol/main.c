#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef struct {
    char texto[20];  
    int numero;      
} Pacote;

void ordenar(Pacote p[], int n) {
    for (int i = 0; i < n - 1; i++) {
        int menor = i;
        for (int j = i + 1; j < n; j++) {
            if (p[j].numero < p[menor].numero) {
                menor = j;
            }
        }
        Pacote aux = p[i];
        p[i] = p[menor];
        p[menor] = aux;
    }
}

void printar(Pacote p[], int n) {
    for (int i = 0; i < n; i++) {
        printf("%s\n", p[i].texto);
    }
    printf("\n");  
}

int main() {
    char token[32];
    int first = 1;

    while (scanf("%s", token) == 1) {
       
        if (strcmp(token, "1") != 0) {
            break;
        }

        Pacote pac[1000];
        int qtd = 0;

        
        while (scanf("%s", token) == 1) {
            if (strcmp(token, "0") == 0) {
                break;
            }

            
            char numStr[8];
            if (scanf("%s", numStr) != 1) {
                return 0; 
            }

            int num = atoi(numStr);

            sprintf(pac[qtd].texto, "%s %s", token, numStr);
            pac[qtd].numero = num;
            qtd++;
        }

        ordenar(pac, qtd);
        printar(pac, qtd);
    }

    return 0;
}