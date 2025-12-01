import java.util.*;

public class Main {

    public static int prefixoComum(String a, String b) {
        int limite = Math.min(a.length(), b.length());
        int i = 0;
        while (i < limite && a.charAt(i) == b.charAt(i)) {
            i++;
        }
        return i;
    }

    public static void main(String[] args) {
        Scanner ent = new Scanner(System.in);

        while (ent.hasNextInt()) {
            int n = ent.nextInt();
            String[] telefones = new String[n];

            for (int i = 0; i < n; i++) {
                telefones[i] = ent.next();
            }

            Arrays.sort(telefones);

            int economia = 0;
            for (int i = 1; i < n; i++) {
                economia += prefixoComum(telefones[i - 1], telefones[i]);
            }

            System.out.println(economia);
        }

        ent.close();
    }
}