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
