import java.util.*;

public class Main {

    static class No {
        int elemento;
        No esq, dir;

        public No(int elemento) {
            this(elemento, null, null);
        }

        public No(int elemento, No esq, No dir) {
            this.elemento = elemento;
            this.esq = esq;
            this.dir = dir;
        }
    }

    static class Arvore {
        No raiz;

        public Arvore() {
            this.raiz = null;
        }

        public void inserir(int elemento) {
            this.raiz = inserir(elemento, raiz);
        }

        private No inserir(int x, No i) {
            if (i == null) {
                i = new No(x);
            } else if (x < i.elemento) {
                i.esq = inserir(x, i.esq);
            } else if (x > i.elemento) {
                i.dir = inserir(x, i.dir);
            } else {
              
            }
            return i;
        }

        public void caminharNivel(int numNos){
           
            No[] fila = new No[numNos];
            
            int inicio = 0;
            int fim = 0;

            fila[fim++] = raiz;

            boolean primeiro = true;

            
            while (inicio < fim) {
                
                No atual = fila[inicio++];

                if (!primeiro) {
                    System.out.print(" ");
                }
                
                System.out.print(atual.elemento);
                primeiro = false;

                if (atual.esq != null) {
                    fila[fim++] = atual.esq;
                }
                if (atual.dir != null) {
                    fila[fim++] = atual.dir;
                }
            }
            
            System.out.println();

        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int numCasos = sc.nextInt();

        for(int caso = 1; caso <= numCasos; caso++){
            
            int n = sc.nextInt();
            Arvore ab = new Arvore();
            
            for (int i = 0; i < n; i++) {
                int num = sc.nextInt();
                ab.inserir(num);
            }

            System.out.println("Case " + caso + ":");
            ab.caminharNivel(n);  
            System.out.println();   

        }

        sc.close();
    }
}