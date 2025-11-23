public class Celula {
    public Contato contato;
    public Celula prox;

    public Celula(){
        this(null, null)
    }
    public Celula(Contato contato){
        this(contato, null);
    }

    public Celula(Contato contato, Celula prox){
        this.contato = contato;
        this.prox = null;
    }
}
