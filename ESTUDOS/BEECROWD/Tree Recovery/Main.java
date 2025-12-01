import java.util.Scanner;

public class Main {

    static String in, pos;

    static void gerarPre(int inIni, int posIni, int tam) {
        if (tam <= 0) return;

        // raiz é o último caractere da pós-ordem
        char raiz = pos.charAt(posIni + tam - 1);

        // imprime raiz (pré-ordem)
        System.out.print(raiz);

        // encontrar índice da raiz na in-ordem
        int k = 0;
        while (k < tam && in.charAt(inIni + k) != raiz) {
            k++;
        }

        // esquerda
        gerarPre(inIni, posIni, k);

        // direita
        gerarPre(inIni + k + 1, posIni + k, tam - k - 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            in = sc.next();
            pos = sc.next();
            gerarPre(0, 0, in.length());
            System.out.println();
        }

        sc.close();
    }
}
