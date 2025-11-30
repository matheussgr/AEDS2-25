import java.util.*;

public class Main {
    static class No{
        int elemento;
        No esq, dir;

        public No(int elemento){
            this(elemento, null, null);
        }

        public No(int elemento, No esq, No dir){
            this.elemento = elemento;
            this.esq = esq;
            this.dir = dir;
        }
    }
    static class Arvore{
        No raiz;
        public Arvore(){
            this.raiz = null;
        }
        public void inserir(int elemento){
            this.raiz = inserir(elemento, raiz);
        }
        private No inserir(int x, No i){
            if (i == null) i = new No(x);
            else if(x < i.elemento) i.esq = inserir(x, i.esq);
            else if(x > i.elemento) i.dir = inserir(x , i.dir);
            else{}
            return i;
        }

        public void caminharCentral(){
            caminharCentral(raiz);
        }
        private void caminharCentral(No i){
            if (i != null){
                caminharCentral(i.esq);
                System.out.print(" " + i.elemento);
                caminharCentral(i.dir);
            }
        }

        public void caminharPre(){
            caminharPre(raiz);
        }
        private void caminharPre(No i){
            if (i != null){
                System.out.print(" " + i.elemento);
                caminharPre(i.esq);
                caminharPre(i.dir);
            }
        }
        
        public void caminharPos(){
            caminharPos(raiz);
        }
        private void caminharPos(No i){
            if (i != null){
                caminharPos(i.esq);
                caminharPos(i.dir);
                System.out.print(" " + i.elemento);
            }
        }
    }
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        int numCasos = sc.nextInt();

        for(int i = 0; i < numCasos; i++){
            
            sc.nextLine();
            
            int numInsercoes = sc.nextInt();
            Arvore ab = new Arvore();
            
            for(int j = 0; j < numInsercoes; j++){ 
                int num = sc.nextInt();
                ab.inserir(num);
            }

            System.out.println("Case " + (i + 1) + " " + ":");
            System.out.print("Pre.:");
            ab.caminharPre();
            System.out.println(" ");
            System.out.print("In..:");
            ab.caminharCentral();
            System.out.println(" ");
            System.out.print("Post:");
            ab.caminharPos();
            System.out.println(" ");      
            System.out.println(" ");          

            
        }
        sc.close();
    }
}
