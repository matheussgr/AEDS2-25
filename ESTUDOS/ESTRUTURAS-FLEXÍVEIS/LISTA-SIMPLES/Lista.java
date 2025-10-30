
public class Lista {
    
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

    // Atributos da Lista Simples
    public Celula primeiro;
    public Celula ultimo;

    // Construtor da Lista Simples
    public Lista(){
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void InserirInicio(int x){
        Celula tmp = new Celula(x);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if (primeiro == ultimo){
            ultimo = tmp;
        }
        tmp = null;
    }

    public void InserirFim(int x){
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }
    }
    public void Inserir(int x, int pos){
        int tamanho = tamanho();
        if (pos < 0 || pos > tamanho){
            System.out.println("Erro!");
        } else if (pos == 0){
            InserirInicio(x);
        } else if (pos == tamanho){
            InserirFim(x);
        } else {
            Celula i = primeiro;
            for(int j = 0; j < pos; j++, i = i.prox);
            Celula tmp = new Celula(x);
            tmp.prox = i.prox;
            i.prox = tmp;
            i = tmp = null;
        }
    }
    public int RemoverInicio(){
    }
    public int RemoverFim(){
        if(primeiro == ultimo){
            System.out.println("Erro!");
        }
        Celula i;
        for(i = primeiro; i.prox != null; i = i.prox);
        int elemento = ultimo.elemento;
        ultimo = i;
        ultimo.prox = i = null;
        return elemento;
    }
    public int Remover(int pos) throws Exception{   
        int tamanho = tamanho();
        if (pos < 0 || pos > tamanho){
           throw new Exception("Erro!");
        } else if (pos == 0){
            RemoverInicio();
        } else if (pos == tamanho){
            RemoverFim();
        } else {
            Celula i = primeiro;
            for(int j = 0; j < pos; j++, i = i.prox);
            Celula tmp = i.prox;
            int elemento = i.elemento; 
            i.prox = tmp.prox;
            i = tmp = null;
            return elemento;
        }
    }


    public void Mostrar(){
        System.out.print("[");
        for(Celula i = primeiro; i != null; i = i.prox){
            System.out.print(i.elemento + " ");
        }
        System.out.print("]");
    }
    
    public int tamanho(){
        int tamanho = 0;
        for(Celula i = primeiro; i != ultimo; i = i.prox, tamanho++);
        return tamanho;
    }

}
