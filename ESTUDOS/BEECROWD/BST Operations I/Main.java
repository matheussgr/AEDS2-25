import java.util.*;

public class Main {
    static class No {
        char elemento;
        No esq, dir;

        public No(char elemento) {
            this(elemento, null, null);
        }

        public No(char elemento, No esq, No dir) {
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

        public void inserir(char elemento) {
            this.raiz = inserir(elemento, raiz);
        }

        private No inserir(char x, No i) {
            if (i == null)
                i = new No(x);
            else if (x < i.elemento)
                i.esq = inserir(x, i.esq);
            else if (x > i.elemento)
                i.dir = inserir(x, i.dir);
            else {
            }
            return i;
        }

        public void caminharCentral() {
            caminharCentral(raiz);
        }

        private void caminharCentral(No i) {
            if (i != null) {
                caminharCentral(i.esq);
                System.out.print(i.elemento + " ");
                caminharCentral(i.dir);
            }
        }

        public void caminharPre() {
            caminharPre(raiz);
        }

        private void caminharPre(No i) {
            if (i != null) {
                System.out.print(i.elemento + " ");
                caminharPre(i.esq);
                caminharPre(i.dir);
            }
        }

        public void caminharPos() {
            caminharPos(raiz);
        }

        private void caminharPos(No i) {
            if (i != null) {
                caminharPos(i.esq);
                caminharPos(i.dir);
                System.out.print(i.elemento + " ");
            }
        }

        public boolean pesquisar(char x) {
            return pesquisar(x, raiz);
        }

        private boolean pesquisar(char x, No i) {
            if (i == null) {
                return false;
            } else if (x == i.elemento) {
                return true;
            } else if (x < i.elemento) {
                return pesquisar(x, i.esq);
            } else {
                return pesquisar(x, i.dir);
            }
        }

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Arvore ab = new Arvore();

        while (sc.hasNextLine()) {

            String entrada = sc.nextLine();
            if (entrada.charAt(0) == 'I' && entrada.charAt(1) == ' ') {
                String[] partes = entrada.split(" ");
                char letra = partes[1].charAt(0);
                ab.inserir(letra);
            } else if (entrada.charAt(0) == 'I' && entrada.charAt(1) == 'N') {
                ab.caminharCentral();
                System.out.println();
            } else if (entrada.charAt(0) == 'P' && entrada.charAt(1) == 'R') {
                ab.caminharPre();
                System.out.println();
            } else if (entrada.charAt(0) == 'P' && entrada.charAt(1) == 'O') {
                ab.caminharPos();
                System.out.println();
            } else {
                String[] partes = entrada.split(" ");
                char letra = partes[1].charAt(0);
                boolean resp = ab.pesquisar(letra);
                if (resp) {
                    System.out.println(letra + " existe");
                } else {
                    System.out.println(letra + " nao existe");
                }
            }
           
         }
          sc.close();
    }
}
