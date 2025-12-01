import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        while (entrada.hasNextLine()) {
            String linha = entrada.nextLine();

            int balance = 0;
            boolean ok = true;

            for (int i = 0; i < linha.length(); i++) {
                char c = linha.charAt(i);

                if (c == '(') {
                    balance++;
                } else if (c == ')') {
                    if (balance == 0) {
                        ok = false;
                        break;
                    } else {
                        balance--;
                    }
                }
            }

            if (ok && balance == 0) {
                System.out.println("correct");
            } else {
                System.out.println("incorrect");
            }
        }

        entrada.close();
    }
}
