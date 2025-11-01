//  Crie uma classe ListaSimplesEncadeadaOrdenada, garantindo que os elementos sempre fiquem ordenados 

int inserirOrdenado(int x){
    Celula* tmp = novaCelula(x);
    Celula* i = primeiro;

    while(i->prox != NULL && i-> elemento < x){
        i = i->prox;
    }

    tmp->prox = i->prox;
    i->prox = tmp;

    if(i == ultimo){
        ultimo = tmp;
    }

    tmp = NULL;
}