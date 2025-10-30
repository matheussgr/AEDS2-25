#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

// Struct Nó
typedef struct No{ 
    int elemento;
    struct No* esq;
    struct No* dir;
}No;

// "Construtor" do Nó
No* novoNo (int x){
    No* novo = (No*)malloc(sizeof(No));
    novo->elemento = x;
    novo->esq = NULL;
    novo->dir = NULL;
    return novo;
}

// Struct Árvore
typedef struct Arvore{
    No* raiz;
}Arvore;

// "Construtor" da Árvore
Arvore* novaArvore (){
    Arvore* nova = (Arvore*)malloc(sizeof(Arvore));
    nova->raiz = NULL;
    return nova;
}


// Inserir 
No* inserirRec(int x, No* i){
    if(i == NULL){
        i = novoNo(x);
    }
    else if (x < i->elemento){
        i->esq = inserirRec(x, i->esq);
    } else {
        i->dir = inserirRec(x, i->dir);
}
    return i;
}

void inserir(int x, Arvore* arvore){
    arvore->raiz = inserirRec(x,arvore->raiz);
}

// Caminhar Central
void caminharCentral(No* i){
    if(i != NULL){
        caminharCentral(i->esq);
        printf("%d ",i->elemento);
        caminharCentral(i->dir);
    }
}


int main(){
    Arvore* a = novaArvore();
    
    int array[] = {4,1,7,2,11};
    
    for(int i = 0; i < 5; i++){
        inserir(array[i], a);
    }

    printf("[");
    caminharCentral(a->raiz);
    printf("]");

    return 0;
}