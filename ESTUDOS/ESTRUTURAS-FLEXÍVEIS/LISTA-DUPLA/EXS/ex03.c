void quicksortRec(Celula esq, Celula dir) {
   
    Celula i = esq, j = dir;
    int iCount = 0
    
    int tam = tamanho();
    
    Celula *k = primeiro;
    for(int w = 1; w < tam/2; w++, k = k->prox);
    
    int pivo = k->elemento
    
    while (i <= j) {
        while (i->elemento < pivo) i++;
        while (array[j] > pivo) j--;
        if (i <= j) {
            swap(array + i, array + j);
            i++;
            j--;
        }
    }
    if (esq < j)  quicksortRec(array, esq, j);
    if (i < dir)  quicksortRec(array, i, dir);
}

void quicksort() {
    quicksortRec(primeiro->prox, ultimo);
}

int tamanho(){
    int tam = 0;
    for(CelulaDupla* i = primeiro->prox; i->prox != NULL; i = i->prox, tam++);
    return tam;
}
