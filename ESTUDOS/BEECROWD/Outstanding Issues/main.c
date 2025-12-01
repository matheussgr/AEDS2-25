#include <stdio.h>
#include <string.h>

int main() {

    char s[10000];
    fgets(s, sizeof(s), stdin);   // lê a linha inteira

    // remover o '\n' se existir
    int len = strlen(s);
    if (len > 0 && s[len - 1] == '\n') {
        s[len - 1] = '\0';
    }

    int abertos = 0;

    // percorre a string
    for (int i = 0; s[i] != '\0'; i++) {
        char c = s[i];

        if (c == '(') {
            abertos++;
        } else if (c == ')') {
            if (abertos > 0) {
                abertos--;
            }
            // se abertos == 0 e aparecer ')', simplesmente ignora (igual ao Java)
        }
    }

    // saída final
    if (abertos == 0) {
        printf("Partiu RU!\n");
    } else {
        printf("Ainda temos %d assunto(s) pendente(s)!\n", abertos);
    }

    return 0;
}
