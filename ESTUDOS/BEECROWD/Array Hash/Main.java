import java.util.*;

public class Main {
    public static void main(String[] args) {
            
        Scanner sc = new Scanner(System.in);
        int numCasos = sc.nextInt();

        for (int casos = 0; casos < numCasos; casos++) {

            int numLinhas = sc.nextInt();
            int hashTotal = 0;
            
            for (int linhas = 0; linhas < numLinhas; linhas++) {
                String s = sc.next();  

                for (int i = 0; i < s.length(); i++) {
                    char letraAtual = s.charAt(i);
                    int valorLetra = letraAtual - 'A';   

                    hashTotal += i + linhas + valorLetra;
                }
            }

            System.out.println(hashTotal);
        }

        sc.close();
    }
}
