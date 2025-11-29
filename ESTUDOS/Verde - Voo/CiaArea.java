public import java.util.*;

public class CiaArea {

    public static int compareTo(String x, String y) {
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

    

    static class Voo {
        public String codigoVoo;
        public String cidadeOrigem;
        public String cidadeDestino;
        public String data;
        public String hora;

        public Voo(String codigoVoo, String cidadeOrigem, String cidadeDestino, String data, String hora) {
            this.codigoVoo = codigoVoo;
            this.cidadeOrigem = cidadeOrigem;
            this.cidadeDestino = cidadeDestino;
            this.data = data;
            this.hora = hora;
        }

    }

    static class No {

        public Voo elemento;
        public No esq;
        public No dir;

        public No(Voo elemento) {
            this.elemento = elemento;
            this.esq = this.dir = null;
        }
    }

    static class Arvore {
        No raiz;

        public Arvore() {
            this.raiz = null;
        }

        public void inserir(Voo x) {
            raiz = inserir(x, raiz);
        }

        private No inserir(Voo x, No i) {
            if (i == null) {
                i = new No(x);
            }

            int cmp = CiaArea.compareTo(x.codigoVoo, i.elemento.codigoVoo);

            if (cmp < 0) {
                i.esq = inserir(x, i.esq);
            } else if (cmp > 0) {
                i.dir = inserir(x, i.dir);
            } else {

            }

            return i;
        }

        public void caminharPos() {
            caminharPos(raiz);
        }

        private void caminharPos(No i) {
            if (i != null) {
                caminharPos(i.esq);
                caminharPos(i.dir);
                System.out.println(i.elemento.codigoVoo + " " + i.elemento.cidadeOrigem + " " + i.elemento.cidadeDestino
                        + " " + i.elemento.data + " " + i.elemento.hora);
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Arvore arvore = new Arvore();
        String linha = sc.nextLine();

        while (!linha.equals("FIM")) {
            String[] partes = linha.split(",");
            linha = linha.trim();

            String codigo = partes[0];
            String origem = partes[1];
            String destino = partes[2];
            String data = partes[3];
            String hora = partes[4];

            Voo voo = new Voo(codigo, origem, destino, data, hora);

            arvore.inserir(voo);

            linha = sc.nextLine();
        }

        arvore.caminharPos();

    }

}



 {
    
}
