import java.util.Scanner;

public class Main {

    static String pre, in;

    // Função recursiva que imprime a pós-ordem
    static void gerarPosOrdem(int preIni, int inIni, int tam) {
        if (tam <= 0) return;

        char raiz = pre.charAt(preIni);

        // encontrar a posição da raiz na in-ordem
        int k = 0;
        while (k < tam && in.charAt(inIni + k) != raiz) {
            k++;
        }

        // parte esquerda
        gerarPosOrdem(preIni + 1, inIni, k);

        // parte direita
        gerarPosOrdem(preIni + k + 1, inIni + k + 1, tam - k - 1);

        // raiz no final
        System.out.print(raiz);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int C = sc.nextInt();

        while (C-- > 0) {
            pre = sc.next();
            in = sc.next();

            gerarPosOrdem(0, 0, pre.length());
            System.out.println();
        }

        sc.close();
    }
}
