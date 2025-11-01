// Este método remove o nó na posição 1 (o segundo elemento válido) lista.

int removeSeg(){
    if(primeiro-> prox = NULL|| primeiro->prox->prox == NULL){
        printf("Erro!");
    }
    Celula* tmp = primeiro->prox->prox;
    int elemento = tmp->elemento
    primeiro->prox->prox = tmp->prox;

    if (tmp == ultimo){
        ultimo = primeiro->prox;
    }

    tmp->prox = NULL;
    free(tmp);
    tmp = NULL;
    return elemento;
}