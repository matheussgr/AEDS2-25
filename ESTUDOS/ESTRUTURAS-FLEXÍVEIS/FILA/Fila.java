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
    public Celula primeiro, ultimo;

    // Construtor da Pilha 
    public Pilha(){
        primeiro = ultimo = null;
    }

    public void Inserir(int x){

    }

    public int Remover(){

    }

    public void Mostrar(){
        System.out.print("[");
        for(Celula i = primeiro; i != null; i = i.prox){
            System.out.print(i.elemento + " ");
        }
        System.out.print("]");
    }

}
