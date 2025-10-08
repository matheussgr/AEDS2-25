#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Estrutura Célula
typedef struct {
  int elemento;
  Celula *ant;
  Celula *prox;  
} Celula;

// "Construtor" da Célula
Celula* novaCelula(int elemento){ // "Celula*" porque a fução retorna um ponteiro para uma struct Celula
    Celula* nova = (Celula*) malloc(sizeof(Celula)); // nova é um ponteiro que aponta pra área alocada, e o (Celula*) antes do malloc transforma o ponteiro genérico "void*" do malloc em um ponteiro de Celula
    nova->elemento = elemento;
    nova->ant = nova->prox = NULL;
 }

// Ponteiros de Último e Primeiro da Fila Dupla
Celula* primeiro;
Celula* ultimo;

// "Construtor" da Lista Dupla (somente o nó cabeça)
void criarLista(){
    primeiro = novaCelula(-1); // -1 porque é um valor que não será usado
    ultimo = primeiro;
} 

void inserirInicio(int elemento){
    Celula* tmp = novaCelula(elemento);
    tmp->ant = primeiro;
    tmp->prox = primeiro->prox;
    primeiro->prox = tmp;
    if(primeiro == ultimo){
        ultimo = tmp;
    }
    else{
        tmp->prox->ant = tmp;
    }
    tmp = NULL;
}

void inserirFim(int elemento){
    ultimo->prox = criarCelula(elemento);
    ultimo = ultimo->prox;
}

int removerInicio(){
    if(primeiro == ultimo){
        printf("Lista vazia!\n");
    }
    Celula* tmp = primeiro;
    primeiro = primeiro->prox;
    int elemento = primeiro->elemento;
    tmp->prox = primeiro->ant = NULL;
    free(tmp);
    return elemento;
}

int removerFim(){
    if(primeiro == ultimo){
        printf("Lista vazia!\n");        
    }

    int elemento = ultimo->elemento;
    ultimo = ultimo->ant;
    ultimo->prox->ant = NULL;
    free(ultimo->prox);
    ultimo->prox = NULL;
    return elemento;
}

int tamanho(){
    int tamanho = 0;
    Celula* i = primeiro;
    for(; i != NULL; i = i->prox, tamanho++);
    return tamanho;

}

void inseir(int x, int pos){
    int tam = tamanho();
    if (pos < 0 || pos > tamanho) {
        printf("")
    }
}







