//  Modifique o método inserirInicio de tal forma que o novo valor seja inserido no nó cabeça e, em seguida, criamos uma nova célula como nó cabeça 

void inserirInicio(int x){
    
    Celula* tmp = novaCelula(-1);
    tmp->prox = primeiro;

    primeiro->elemento = x;

    if(primeiro == ultimo){
        ultimo = primeiro;
    }

    primeiro = tmp;
    
    tmp = NULL;
}