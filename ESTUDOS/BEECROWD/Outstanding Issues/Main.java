import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine().trim();

        int abertos = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                abertos++;
            } else if (c == ')') {
                if (abertos > 0) {
                    abertos--;
                }
                // se abertos == 0 e vier ')' → apenas ignorar
            }
        }

        if (abertos == 0) {
            System.out.println("Partiu RU!");
        } else {
            System.out.println("Ainda temos " + abertos + " assunto(s) pendente(s)!");
        }

        sc.close();
    }
}
