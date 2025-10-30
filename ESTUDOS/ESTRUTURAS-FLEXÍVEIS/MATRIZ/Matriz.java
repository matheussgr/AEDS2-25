public class Matriz {

    // Class Celula
    public class Celula {
    
        // Atributos
        public int elemento;
        public Celula esq, dir, inf, sup;

        // Construtores
        public Celula(){
            this(0);
        }

        public Celula(int x){
            this.elemento = x;
            this.esq = this.dir = this.sup = this.inf = null;
        }

    }

    // Atributos da Matriz
    public Celula inicio;
    public int linhas, colunas;

    // Construtores da Matriz
    public Matriz(){
        this(3,3);
    }

    public Matriz(int c, int l){
        this.colunas = c;
        this.linhas = l;
        
        if (c <= 0 || l <= 0){
            System.out.println("Erro!");
        }
        
        inicio = new Celula(0);
        
        // Criando a primeira linha
        
        Celula i = inicio;
        for(int j = 0; j < c; j++){
            Celula tmp = new Celula(0);
            tmp.esq = i;
            i.dir = tmp;
            i = tmp;
        }

        // Criando as demais linhas
        
        Celula linhaAcima = inicio;
        
        //Cria a 1ª Célula da nova linha
        for(int j = 1; j < l; j++){
            Celula primeiroLinha = new Celula(0);
            primeiroLinha.sup = linhaAcima;
            linhaAcima.inf = primeiroLinha;
        

            Celula esquerda = primeiroLinha;
            Celula acima = linhaAcima.dir;

            for(int k = 0; k < c; k++){
                Celula novo = new Celula(0);
                // Conecto horizontalmente
                novo.esq = esquerda;
                esquerda.dir = novo;
                // Conecto verticalmente
                novo.sup = acima;
                acima.inf = novo;
                // Avança os ponteiros
                esquerda = novo;
                acima = acima.dir;
            }
            // Passa pra próxima linha
            linhaAcima = linhaAcima.inf;
        }
    }
}
