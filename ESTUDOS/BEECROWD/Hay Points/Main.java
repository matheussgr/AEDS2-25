import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int M = sc.nextInt(); // número de palavras no dicionário
        int N = sc.nextInt(); // número de descrições

        // vetores do dicionário
        String[] palavras = new String[M];
        int[] valores = new int[M];

        // leitura do dicionário
        for (int i = 0; i < M; i++) {
            palavras[i] = sc.next();   // palavra
            valores[i] = sc.nextInt(); // valor da palavra
        }

        // leitura das descrições
        for (int i = 0; i < N; i++) {
            int salario = 0;

            while (true) {
                String w = sc.next(); // próxima "palavra" da descrição

                if (w.equals(".")) {
                    // fim desta descrição
                    break;
                }

                // procurar w no dicionário (busca linear)
                for (int j = 0; j < M; j++) {
                    if (palavras[j].equals(w)) {
                        salario += valores[j];
                        break; // achou, não precisa continuar procurando
                    }
                }
            }

            System.out.println(salario);
        }

        sc.close();
    }
}
