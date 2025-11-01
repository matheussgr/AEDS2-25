class ArvoreBinaria{
    
    class No {
        
        // Atributos do Nó
        public int elemento;
        public No esq, dir;
        
        // Construtor do Nó
        public No(int x){
            this(x, null, null);
        }
        public No(int x, No esq, No dir){
            this.elemento = x;
            this.esq = esq;
            this.dir = dir;
        }
    }

    // Atributos da Árvore
    No raiz;

    // Construtor da Árvore
    public Arvore(){
        raiz = null;
    }

    // Métodos

    // Inserção
    void inserir(int x){
        raiz = inserir(x, raiz);
    }
    No inserir(int x, No i){
        if (i == null){
            i = new No(x);
        }
        else if(x < i.elemento){
            i.esq = inserir(x, i.esq);
        }
        else if(x < i.elemento){
            i.dir = inserir(x, i.dir);
        }
        else{
            System.out.println("Erro");
        }
        return i;
    }

    // Inserção com dois ponteiros
    

}