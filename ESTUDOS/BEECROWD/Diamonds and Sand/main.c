#include <stdio.h>

int main() {
    int n;
    scanf("%d", &n);
    getchar(); // consome o \n

    while (n--) {
        char linha[1100];
        if (fgets(linha, sizeof(linha), stdin) == NULL) break;

        int abertura = 0;
        int diamantes = 0;

        for (int i = 0; linha[i] != '\0' && linha[i] != '\n'; i++) {
            if (linha[i] == '<') {
                abertura++;
            } else if (linha[i] == '>' && abertura > 0) {
                abertura--;
                diamantes++;
            }
        }

        printf("%d\n", diamantes);
    }

    return 0;
}