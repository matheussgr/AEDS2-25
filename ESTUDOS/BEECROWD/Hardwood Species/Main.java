import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine().trim()); // número de casos

        // depois do T tem uma linha em branco
        br.readLine();

        for (int caso = 0; caso < T; caso++) {

            // vetores para armazenar espécies e suas contagens
            // tamanho máximo: vamos chutar 10000 (ou outro valor grande)
            String[] especie = new String[10000];
            int[] cont = new int[10000];
            int nEspecies = 0;
            int total = 0;

            while (true) {
                String linha = br.readLine();
                if (linha == null) {
                    // fim do arquivo
                    break;
                }
                if (linha.length() == 0) {
                    // linha em branco -> fim do caso
                    break;
                }

                // procurar a espécie no vetor
                int pos = -1;
                for (int i = 0; i < nEspecies; i++) {
                    if (especie[i].equals(linha)) {
                        pos = i;
                        break;
                    }
                }

                if (pos == -1) {
                    // espécie nova
                    especie[nEspecies] = linha;
                    cont[nEspecies] = 1;
                    nEspecies++;
                } else {
                    // já existia
                    cont[pos]++;
                }

                total++;
            }

            // ordenar as espécies em ordem alfabética (selection sort)
            for (int i = 0; i < nEspecies - 1; i++) {
                int menor = i;
                for (int j = i + 1; j < nEspecies; j++) {
                    if (especie[j].compareTo(especie[menor]) < 0) {
                        menor = j;
                    }
                }

                // troca especie[i] com especie[menor]
                String tmpNome = especie[i];
                especie[i] = especie[menor];
                especie[menor] = tmpNome;

                // troca cont[i] com cont[menor] (pra manter alinhado)
                int tmpCont = cont[i];
                cont[i] = cont[menor];
                cont[menor] = tmpCont;
            }

            // linha em branco entre casos (menos antes do primeiro)
            if (caso > 0) {
                System.out.println();
            }

            // imprime resultado
            for (int i = 0; i < nEspecies; i++) {
                double perc = (cont[i] * 100.0) / total;
                System.out.printf("%s %.4f%n", especie[i], perc);
            }
        }
    }
}
