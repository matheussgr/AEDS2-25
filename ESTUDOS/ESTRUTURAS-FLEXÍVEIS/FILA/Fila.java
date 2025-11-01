public class Fila {
    
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
    public Celula primeiro, ultimo;

    // Construtor da Pilha 
    public Pilha(){
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void Inserir(int x){
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    public int Remover(){
        if (primeiro == ultimo){
            System.out.println("Erro!");
            return -1;
        }
        Celula tmp = primeiro;
        primeiro = primeiro.prox;
        int resp = primeiro.elemento;
        tmp.prox = null;
        tmp = null;
        return resp;
    }

    public void Mostrar(){
        System.out.print("[");
        for(Celula i = primeiro; i != null; i = i.prox){
            System.out.print(i.elemento + " ");
        }
        System.out.print("]");
    }

}
