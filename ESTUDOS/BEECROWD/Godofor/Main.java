package ESTUDOS.BEECROWD.Godofor;

public import java.util.*;

public class Main {

    // Função que verifica se o ser "novo" é melhor do que o "atual"
    // Retorna true  -> novo é melhor
    // Retorna false -> atual continua sendo o melhor
    public static boolean ehMelhor(String nomeNovo, int nivelNovo, int deusNovo, int mortesNovo,
                                   String nomeMelhor, int nivelMelhor, int deusMelhor, int mortesMelhor) {

        // 1) Maior nível vence
        if (nivelNovo != nivelMelhor) {
            return nivelNovo > nivelMelhor;
        }

        // 2) Se nível empata, maior valor de "deus" vence
        if (deusNovo != deusMelhor) {
            return deusNovo > deusMelhor;
        }

        // 3) Se ainda empata, menor quantidade de mortes vence
        if (mortesNovo != mortesMelhor) {
            return mortesNovo < mortesMelhor;
        }

        // 4) Se tudo acima empatar, compara o nome em ordem alfabética
        // nomeNovo.compareTo(nomeMelhor) < 0 significa que nomeNovo vem antes
        return nomeNovo.compareTo(nomeMelhor) < 0;
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        // O problema pode ter vários casos de teste.
        // Então, enquanto ainda houver um inteiro pra ler (N), a gente processa um caso.
        while (entrada.hasNextInt()) {
            int quantidadeSeres = entrada.nextInt();

            String melhorNome = null;
            int melhorNivel = 0;
            int melhorDeus = 0;
            int melhorMortes = 0;

            for (int i = 0; i < quantidadeSeres; i++) {

                // Lê os dados do ser atual
                String nome = entrada.next();   // sem espaços
                int nivel = entrada.nextInt();
                int deus = entrada.nextInt();
                int mortes = entrada.nextInt();

                if (melhorNome == null) {
                    // Primeiro ser lido vira o "melhor" automaticamente
                    melhorNome = nome;
                    melhorNivel = nivel;
                    melhorDeus = deus;
                    melhorMortes = mortes;
                } else if (ehMelhor(nome, nivel, deus, mortes,
                                    melhorNome, melhorNivel, melhorDeus, melhorMortes)) {
                    // Se o ser atual for melhor, atualiza o campeão
                    melhorNome = nome;
                    melhorNivel = nivel;
                    melhorDeus = deus;
                    melhorMortes = mortes;
                }
            }

            // Imprime o melhor ser deste caso de teste
            System.out.println(melhorNome);
        }

        entrada.close();
    }
}
 {
    
}
