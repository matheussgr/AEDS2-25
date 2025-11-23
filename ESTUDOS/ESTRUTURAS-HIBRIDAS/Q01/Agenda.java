public class Agenda {
    
    public No raiz;
    
    // Construtor da agenda chama inserir 
    public Agenda(){
        inserir();
    }

    // Inserir todas as letras do alfabeto para popular a ávore
    public void inserir() {
        for (char letra = 'A'; letra <= 'Z'; letra++) {
            raiz = inserir(letra, raiz);
        }
    }

    public void inserir(char x, No i){
        if(i == null){
            i = new No(x);
        }
        else if(x < i.elemento){
            i.esq = inserir(x, i.esq);
        }
        else if(x > i.elemento){
            i.dir = inserir(x, i.dir);
        }
        else{
            System.out.println("Erro");
        }
        return i;
    }

    // Método de inserir contato
    public void inserir(Contato contato){
        raiz = inserir(contato, raiz);
    }

    public void inserir(Contato contato, No no){
        if (no == null){
            System.out.println("Erro");
        } else if(Character.toUpperCase(contato.nome.charAt(0)) == no.letra){
            no.ultimo.prox = new Celula(x);
            ultimo = ultimo.prox;
        } else if(Character.toUpperCase(contato.nome.charAt(0)) < no.letra){
            no.esq = inserir(contato, no.esq);
        } else if(Character.toUpperCase(contato.nome.charAt(0)) > no.letra){
            no.dir = inserir(contato, no.dir);
        } else {
            System.out.println("Erro");
        }
        return no;
    }

    // Método remover nome
    public void remover(String nome){
        char letra = Character.toUpperCase(nome.charAt(0));
        raiz = remover(nome, raiz, letra);
    }

    public void remover(String nome, No i, char letra){
        if(i == null){
            System.out.println("Erro");
        }
        else if(letra < i.elemento){
            i.esq = remover(nome, i.esq, letra);
        }
        else if(letra > i.elemento){
            i.dir = remover(nome, i.dir, letra);
        }
        else{
            // Achou o nó da letra 
            if(i.primeiro == i.ultimo){
                System.out.println("Erro");
            }
            Celula ant = i.primeiro;       
            Celula p   = i.primeiro.prox;  
            while (p != null) {
                ant = p;
                p = p.prox;
            }
            if (p == null) System.out.print("Contato não encontrado");

            ant.prox = p.prox;
            if (p == i.ultimo) i.ultimo = ant; 
            p.prox = null;

            if (i.primeiro.prox == null) {
                if (i.dir == null){
                    i = i.esq
                }
                else if (i.esq == null){
                    i = i.dir;
                }
                else {
                 i.esq = maiorEsq(i, i.esq);
                }
            }
        }
        return i;
    }

    No maiorEsq(No i, No j){
        if (j.dir == null){
            i.letra = j.letra;
            i.primeiro = j.primeiro; 
            i.ultimo = j.ultimo;
        } else{
            j.dir = maiorEsq(i, esq);
            return j;
        }
    }

}
    


