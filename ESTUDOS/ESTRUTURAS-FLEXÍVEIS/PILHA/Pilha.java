public class Pilha {
    
    // Class Celula
    public class Celula {
    
        // Atributos
        public int elemento;
        public Celula prox;

        // Construtores
        public Celula(){
            this(0);
        }

        public Celula(int x){
            this.elemento = x;
            this.prox = null;
        }

    }

    // Atributos da Pilha
    public Celula topo;

    // Construtor da Pilha 
    public Pilha(){
        topo = null;
    }

    public void Inserir(int x){
        Celula tmp = new Celula(x);
        tmp.prox = topo;
        topo = tmp;
        tmp = null;
    }

    public int Remover(){
        if(topo == null){
            System.out.println("Erro ao remover: Pilha vazia");
            return -1;
        }
        int resp = topo.elemento;
        Celula tmp = topo;
        topo = topo.prox;
        tmp.prox = null;
        tmp = null;
        return resp;
    }

    public void Mostrar(){
        System.out.print("[");
        for(Celula i = topo; i != null; i = i.prox){
            System.out.print(i.elemento + " ");
        }
        System.out.print("]");
    }

}
