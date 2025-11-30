import java.util.*;

public class Main {
    
    public static int mdc(int a, int b){
        while (b != 0) {
            int r = a % b;
            a = b;
            b = r;
        }
        return a;
    }
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        int numOp = sc.nextInt();

        while(numOp > 0){
            
            int Nresp;
            int Dresp;

            int n1 = sc.nextInt();
            sc.next();
            int d1 = sc.nextInt();

            char sinal = sc.next().charAt(0);

            int n2 = sc.nextInt();
            sc.next();
            int d2 = sc.nextInt();

            if (sinal == '+'){
                
                Nresp = (n1 * d2 + n2 * d1); 
                Dresp = (d1 * d2);

                System.out.print(Nresp + " " + "/" + Dresp + " " + " = ");

                int m = mdc(Nresp, Dresp);
                System.out.println((Nresp / m) + " " + "/" + (Dresp / m) + " ");
            } else if (sinal == '-' ){

                Nresp = (n1 * d2 - n2 * d1); 
                Dresp = (d1 * d2);

                System.out.print(Nresp + " " + "/" + Dresp + " " + " = ");

                int m = mdc(Nresp, Dresp);
                System.out.println((Nresp / m) + " " + "/" + (Dresp / m) + " ");

            } else if (sinal == '*' ){

                Nresp = (n1 * n2); 
                Dresp = (d1 * d2);

                System.out.print(Nresp + " " + "/" + Dresp + " " + " = ");

                int m = mdc(Nresp, Dresp);
                System.out.println((Nresp / m) + " " + "/" + (Dresp / m) + " ");

            } else if (sinal == '/'){

                Nresp = (n1 * d2); 
                Dresp = (n2 * d1);

                System.out.print(Nresp + " " + "/" + Dresp + " " + " = ");

                int m = mdc(Nresp, Dresp);
                System.out.println((Nresp / m) + " " + "/" + (Dresp / m) + " ");


            }

            numOp--;
        }
    }
}
