void inverter(){
    Celula* i = primeiro->prox;
    Celula* j = ultimo;

    while(i != j && j->prox != i){
        int tmp = i->elemento
        i->elemento = j->elemento
        j->elemento = tmp;

        for(Celula* k = primeiro; k->prox != j; k = k->prox);
        
        j = k;
        ultimo = j;
    }
}