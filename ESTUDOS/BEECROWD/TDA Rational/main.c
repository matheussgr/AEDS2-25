#include <stdio.h>
#include <stdlib.h>

int mdc(int a, int b) {
    if (a < 0) a = -a;
    if (b < 0) b = -b;

    while (b != 0) {
        int resto = a % b;
        a = b;
        b = resto;
    }
    return a;
}

int main() {
    int num;
    scanf("%d", &num);

    while (num > 0) {
        int N1, D1, N2, D2;
        int nmr1, nmr2;
        char lx1, lx2, op;

        scanf("%d %c %d %c %d %c %d",
              &N1, &lx1, &D1,
              &op,
              &N2, &lx2, &D2);

        if (op == '+') {
            nmr1 = N1 * D2 + N2 * D1;
            nmr2 = D1 * D2;
        } else if (op == '-') {
            nmr1 = N1 * D2 - N2 * D1;
            nmr2 = D1 * D2;
        } else if (op == '*') {
            nmr1 = N1 * N2;
            nmr2 = D1 * D2;
        } else { // '/'
            nmr1 = N1 * D2;
            nmr2 = N2 * D1;
        }

        int md = mdc(nmr1, nmr2);

        int simpNum = nmr1 / md;
        int simpDen = nmr2 / md;

        if (simpDen < 0) {
            simpDen = -simpDen;
            simpNum = -simpNum;
        }

        printf("%d/%d = %d/%d\n", nmr1, nmr2, simpNum, simpDen);

        num--;
    }

    return 0;
}