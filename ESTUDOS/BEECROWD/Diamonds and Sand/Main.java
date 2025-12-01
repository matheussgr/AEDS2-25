import java.util.*;

public class Main {

    public static int contaDiamantes(String s) {
        int abertura = 0;
        int diamantes = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '<') {
                abertura++;
            } else if (c == '>' && abertura > 0) {
                abertura--;
                diamantes++;
            }
        }

        return diamantes;
    }

    public static void main(String[] args) {
        Scanner ent = new Scanner(System.in);

        int casos = ent.nextInt();
        ent.nextLine(); // consome o \n

        for (int i = 0; i < casos; i++) {
            String linha = ent.nextLine();
            System.out.println(contaDiamantes(linha));
        }

        ent.close();
    }
}