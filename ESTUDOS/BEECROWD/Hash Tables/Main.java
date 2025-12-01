import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // número de casos de teste

        for (int caso = 0; caso < N; caso++) {

            int M = sc.nextInt(); // tamanho da tabela hash
            int C = sc.nextInt(); // quantidade de chaves

            // tabela[i] = vetor de valores na posição i da hash
            int[][] tabela = new int[M][C];
            // tam[i] = quantidade de valores já ocupados em tabela[i]
            int[] tam = new int[M];

            // lê C valores e insere na hash
            for (int k = 0; k < C; k++) {
                int valor = sc.nextInt();

                // função hash (ajustada para não dar índice negativo)
                int idx = valor % M;
                if (idx < 0) {
                    idx += M;
                }

                tabela[idx][tam[idx]] = valor;
                tam[idx]++;
            }

            // imprime linha em branco entre casos (menos antes do primeiro)
            if (caso > 0) {
                System.out.println();
            }

            // imprime a tabela
            for (int i = 0; i < M; i++) {
                System.out.print(i + " -> ");

                // imprime todos os elementos da lista dessa posição
                for (int j = 0; j < tam[i]; j++) {
                    System.out.print(tabela[i][j] + " -> ");
                }

                // imprime a barra invertida no final da linha
                System.out.println("\\");
            }
        }

        sc.close();
    }
}
