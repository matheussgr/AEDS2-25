import ESTUDOS.ESTRUTURAS-HIBRIDAS.Q01.No;

public class Doidona {
    
    final int tam1 = 100;
    final int tam3 = 100;
    final int NULO = -1;

    int[] t1;
    int[] t3;

    Celula primeiroListaT2, ultimoListaT2;
    No raizT2, raizT3;

    public Doidona(){
        
        for(int i = 0; i < tam1; i++){
            t1[i] = NULO;
        }

        for(int i = 0; i < tam3; i++){
            t3[i] = NULO;
        }

        primeiroListaT2 = ultimoListaT2 = new Celula();
        raizT2 = raizT3 = null;
    }

    public int hashT1(int elemento){ return elemento % tam1;}
    public int hashT2(int elemento){ return elemento % 3;}
    public int hashT3(int elemento){ return elemento % tam3;}
    public int rehashT3(int elemento){ return ++elemento % tam1;}

    public void inserir(int elemento){
        if(elemento != NULO){
            int pos = hashT1(elemento);
            if (t1[pos] == NULO){
                t1[pos] = elemento;
            } else {
                pos = hashT2(elemento);
                if(pos == 0){
                    inserirT3(elemento);
                } else if(pos == 1) {
                    inserirLista(elemento);
                } else if(pos == 2) {
                    raizT2 = inserirArvore(raizT2, elemento);
                } else {
                    System.out.println("Erro");
                }
            }
        }
    }

    public void inserirT3(int elemento){
        int pos = hashT3(elemento);
        if (t3[pos] == NULO){
            t3[pos] = elemento;
        } else if {
            pos = rehashT3(elemento);
            if(t3[pos] == NULO){
                t3[pos] = elemento;
            } else {
                raizT3 = inserirArvore(raizT3, elemento);
            }
        }
    }

    public void inserirLista(int elemento){
        ultimoListaT2.prox = new Celula(elemento);
        ultimoListaT2 = ultimoListaT2.prox;
    }

    public No inserirArvore(No i, int elemento){
        if(i == null){
            i = new No(elemento);
        } else if (elemento < i.elemento){
            i.esq = new No(elemento);
        } else if (elemento > i.elemento){
            i.dir = new No(elemento);
        } else {
            System.out.println("Erro");
        }
        return i;
    }

    public boolean pesquisar(int elemento){
        boolean resp = false;
        int pos = hashT1(elemento);
        if(t1[pos] == elemento){
            resp = true;
        } else if (t1[pos] != NULO){
            pos = hashT2(elemento);
            if(pos == 0){
                resp = pesquisarT3(elemento);
            } else if(pos == 1) {
                resp = pesquisarLista(elemento);
            } else if(pos == 2) {
                resp = pesquisarArvore(raizT2, elemento);
            } else {
                System.out.println("Erro");
            }
            return resp;
        }
    }

    public boolean pesquisarT3(int elemento){
        boolean resp = false;
        int pos = hashT3(elemento);
        if (t3[pos] == elemento){
            resp = true;
        } else if(t3[pos] != NULO){
            pos = rehashT3(elemento);
            if(t3[pos] == elemento){
                resp = true;
            } else {
                resp = pesquisarArvore(raizT3, elemento);
            }
        }
        return resp;
    }

    public boolean pesquisarLista(int elemento){
        boolean resp = false;
        for(int i = primeiroListaT2;; i != null; i = i.prox){
            if(i.elemento == elemento){
                resp = true;
                break;
            }
        }
        return resp;
    }

    public boolean pesquisarArvore(No i, int elemento){
        boolean resp = false;
        if(i == null){
            resp = false;
        } else if(i.elemento == elemento){
            resp = true;
        } else if (elemento < i.elemento){
            resp = pesquisar(i.esq, elemento);
        } else if (elemento > i.elemento){
            resp = pesquisar(i.dir, elemento);
        } else {
            System.out.println("Erro");
        }
        return resp;
    }

    public boolean remover(int elemento){
        boolean resp = false;
        if(elemento != NULO){
            int pos = hashT1(elemento);
            if(t1[pos] == elemento){
                t1[pos] = NULO;
                resp = true;
            } else if (t1[pos] != NULO){
                pos = hashT2(elemento);
                if(pos == 0){
                    resp = removerT3(elemento);
                } else if (pos == 1){
                    resp = removerLista(elemento);
                } else if (pos == 2){
                    if ((resp = pesquisarArvore(raizT2, elemento)) == true){
                        resp = true;
                        raizT2 = removerArvore(raizT2, elemento);
                    } else{
                        resp = false;
                    }
                } else {
                    System.out.println("Erro");
                }
            }
        }
        return resp;
    }

    public boolean removerT3(int elemento){
        boolean resp = false;
        int pos = hashT3(elemento);
        if (t3[pos] == elemento){
            t3[pos] = NULO;
            resp = true;
        } else if (t3[pos] != NULO){
            pos = rehashT3(elemento);
            if(t3[pos] == elemento){
                t3[pos] = NULO;
                resp = true;
            } else{
                if (pesquisarArvore(raizT3, elemento)){
                    resp = true;
                    raizT3 = removerArvore(raizT3, elemento);
                }
            }
        }
        return resp;
    }

    public boolean removerLista(int elemento){
        boolean resp = false;
        Celula ant = primeiroListaT2;
        Celula i = primeiroListaT2.prox;
        for(Celula tmp = i; tmp != null; tmp++){
            if (i.elemento = elemento){
                ant.prox = i.prox;
                if(atual == ultimoListaT2){
                    ultimoListaT2 = ant;
                }
                resp = true;
                break;
            }
        }
        return resp;
    }

    public No removerArvore(No i, int elemento){
        if(i == null){
            System.out.println("Erro");
        } else if (elemento < i.elemento){
            i.esq = removerArvore(i.esq, elemento);
        } else if (elemento > i.elemento){
            i.dir = removerArvore(i.dir, elemento);
        } else if (i.dir == null){
            i = i.esq;
        } else if (i.esq == null){
            i = i.dir;
        } else{
            i.esq = maiorEsq(i, i.esq);
        }
        return i;
    }

    public No maiorEsq(No i, No j){
        if (j.dir == null){
            i.elemento = j.elemento;
            j = j.esq;
        } else {
            j.dir = maiorEsq(i, j.dir);
        }
    }

    public void mostrar(){
        
        System.out.println("T1");
        for(int i =0; i < tam1; i++){
            System.out.print(t1[i] + " " + "," );
        }

        System.out.println("T3");
        for(int i =0; i < tam3; i++){
            System.out.print(t1[i] + " " + "," );
        }

        System.out.println("T3 - Árvore");
        caminharCentral(raizT3);

        System.out.println("T2 - Lista");
        for(Celula i = primeiroListaT2; i != null; i = i.prox){
            System.out.print(i.elemento + " ");
        }

        System.out.println("T2 - Árvore");
        caminharCentral(raizT2);
 
    }

    public void caminharCentral(No i){
        if (i != null){
            caminharCentral(i.esq);
             System.out.println(i.elemento + " ");
            caminharCentral(i.dir);
        }
    }
}