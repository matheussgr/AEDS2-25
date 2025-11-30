import java.util.*;

public class Main {

       static int compareTo(String x, String y) {
        int n1 = x.length();
        int n2 = y.length();
        int n = Math.min(n1, n2);

        for (int i = 0; i < n; i++) {
            char c1 = x.charAt(i);
            char c2 = y.charAt(i);

            if (c1 > c2)
                return 1;
            if (c1 < c2)
                return -1;
        }

            if (n1 > n2)
                return 1;
            if (n1 < n2)
                return -1;

            return 0;
    }
        
    static boolean comparar(Camisa a, Camisa b){
        int compCor = compareTo(a.cor, b.cor);
        if (compCor == 1) {
            return true;
        } else if (compCor == -1) {
            return false;
        }
        else {
            int compTam = compareTo(a.tamanho, b.tamanho);
            compTam = -compTam;
            if (compTam == 1) {
                return true;
            } else if (compTam == -1) {
                return false;
            } else {
                int compNome = compareTo(a.nome, b.nome);
                if (compNome == 1) {
                    return true;
                } else {
                return false;
                }

            }
        }
    }

    static void ordenar(Camisa[] camisas, int qtd){
        for (int i = 0; i < (qtd - 1); i++){
            int menor = i;
            for(int j = i + 1; j < qtd; j++){
                if (comparar(camisas[menor], camisas[j]) == true){
                    menor = j;
                }
            }
            Camisa tmp = camisas[i];
            camisas[i] = camisas[menor];
            camisas[menor] = tmp;
            
        }
    }

    static class Camisa{
        String cor;
        String tamanho;
        String nome;

        public Camisa(String cor, String tamanho, String nome){
            this.cor = cor;
            this.tamanho = tamanho;
            this.nome = nome;
        }
    }
    public static void main(String[] args) {
         
         Scanner sc = new Scanner(System.in);
         
         int entrada = sc.nextInt();

         while(entrada != 0){
        
            Camisa[] camisas = new Camisa[entrada]; 
            sc.nextLine();
            
            for(int i = 0; i < entrada; i++){
                
                String nomeAtual = sc.nextLine(); // Lê o nome atual
                String linha = sc.nextLine();     // Lê cor e tamanho

                String[] partes = linha.split(" "); // Dá o split separando cor (partes[0]) e tamanho(partes[1])
                
                String corAtual = partes[0];      
                String tamahoAtual = partes[1];   

                Camisa camisa = new Camisa(corAtual, tamahoAtual, nomeAtual); // Cria um objeto Camisa
                camisas[i] = camisa; // Atribui ele ao vetor de camisas 
            }

            ordenar(camisas, entrada);
            for(int i = 0; i < entrada; i++){
                System.out.println(camisas[i].cor + " " + " " + camisas[i].tamanho + " " + " " + camisas[i].nome + " ");
            }
            System.out.println(" ");
            entrada = sc.nextInt();   
        } 
            sc.close();   
    }     
}

