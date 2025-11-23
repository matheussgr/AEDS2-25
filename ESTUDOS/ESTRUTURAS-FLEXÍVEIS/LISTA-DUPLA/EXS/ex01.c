void inserir(){
    Celula* i = primeiro->prox;
    Celula* j = ultimo;

    while(i != j && j->prox != i){
        int tmp = i->elemento;
        i->elemento = j->elemento;
        j->elemento = tmp;
        j = j->ant
        i = i->prox;
    }
}