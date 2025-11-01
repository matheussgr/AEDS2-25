#include <stdio.h>
#include <stdlib.h>
#define bool   short
#define true   1
#define false  0

typedef struct Celula {
    int elemento;
    struct Celula *prox;
} Celula;

Celula* novaCelula (int x){
    Celula* nova = (Celula*)malloc(sizeof(Celula));
    nova->elemento = x;
    nova->prox = NULL;
    return nova;
}

Celula* primeiro;
Celula* ultimo; 

void start(){
    primeiro = novaCelula(-1);
    ultimo = primeiro;
}

int tamanho(){
    int tam = 0;
    Celula* i;
    for(i = primeiro; i->prox != NULL; i = i->prox, tam++);
    return tam;
}

void inserirInicio(int x){
    Celula* tmp = novaCelula(x);
    tmp->prox = primeiro->prox;
    primeiro->prox = tmp;
    if(primeiro == ultimo){
        ultimo = tmp;
    }
    tmp = NULL;
}

void inserirFim(int x){
    ultimo->prox = novaCelula(x);
    ultimo = ultimo->prox;
}

void inserir(int x, int pos){
    int tam = tamanho();
    if(pos < 0 || pos > tam){
        printf("Erro!");
    }
    else if (pos == 0){inserirInicio(x);}
    else if (pos == tamanho){inserirFim(x);}
    else{
        Celula* i = primeiro;
        for(int j = 0; j < pos; j++, i = i->prox);
        Celula* tmp = novaCelula(x);
        tmp->prox = i->prox;
        i->prox = tmp;
        tmp = i = NULL;
    }
}

int removerInicio(){
    if(primeiro == ultimo){
        printf("Erro");
    }
    Celula* tmp = primeiro;
    primeiro = primeiro->prox;
    int elemento = primeiro->elemento;
    tmp->prox = NULL;
    free(tmp);
    tmp = NULL;
    return elemento;
}

int removerFim(){
    if (primeiro == ultimo){
        printf("Erro");
    }
    Celula* i;
    for(i = primeiro; i->prox != ultimo; i = i->prox);
    int elemento = ultimo->elemento;
    ultimo = i;
    ultimo->prox = i = NULL;
    return elemento;
}

int remover(int pos){
    int elemento, tam = tamanho();
    if(pos < 0 || pos > tam){
        printf("Erro");
    }
    else if(pos == 0){removerInicio();}
    else if(pos == tam){removerFim();}
    else{
        Celula* i = primeiro;
        for(int j = 0; j < pos; j++, i = i->prox);
        Celula* tmp = i->prox;
        elemento = tmp->elemento;
        i->prox = tmp->prox;
        tmp->prox = NULL;
        free(tmp);
        tmp = NULL;
        return elemento;
    }
}

void mostrar(){
    printf("[");
    for(Celula* i = primeiro; i != NULL; i = i->prox){
        printf("%d", i->elemento);
    }
    printf("]");
}


