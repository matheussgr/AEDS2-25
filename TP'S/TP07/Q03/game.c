// Matheus Gouvêa Ramalho - TP07Q03 - Arvore AVL em C

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>  
#include <locale.h> 
#include <time.h>

#define TAM_MAX_LINHA 4096 // Tamanho máximo de uma linha do CSV
#define TAM_MAX_CAMPO 1024 // Tamanho máximo de um campo do CSV



// Estrutura para armazenar os dados de um jogo
typedef struct {
    int   id;
    char *name;
    char *data;                  
    int   owners;                
    float price;                   
    char **languages;  int languagesCount;
    int   mScore;                 
    float uScore;                  
    int   conq;                    
    char **publisher;  int publisherCount;
    char **dev;        int devCount;
    char **categories; int categoriesCount;
    char **generos;    int generosCount;
    char **tags;       int tagsCount;
} Game;

// Métodos auxiliares

 
char* dupString(const char* s) { 
    if (!s) return NULL; // Verifica se o ponteiro passado é nulo, se 's' for NULL, não há string para copiar, então retorno NULL
    size_t n = strlen(s) + 1; //Calcula o tamanho da string original 
    char* nova = (char*)malloc(n); // Aloca memória suficiente para a nova string
    if (nova) memcpy(nova, s, n); // Se a alocação for bem-sucedida, copia o conteúdo da string original para a nova área de memória através de 'memcpy'
    return nova;
}

// Objetivo: remover todos os espaços em branco do final de uma string
void trimDireita(char* s) {
    int i = (int)strlen(s) - 1; // Calcula o tamanho da string, subtraímos 1 para pegar o último caractere válido
    while (i >= 0 && isspace((unsigned char)s[i])) s[i--] = '\0'; // Enquanto houver caracteres e o caractere atual for espaço em branco, substituímos por '\0' e decrementamos o índice
}                                                                 // O unsigned char é usado para garantir que a função isspace funcione corretamente com todos os valores de caractere

// Objetivo: avançar o ponteiro até o primeiro caractere que NÃO seja espaço em branco (removendo espaços do início)
void pularEspacosEsquerda(char** s) { // O parâmetro é 'char** s' — ou seja, um ponteiro para um ponteiro de char, ou seja, ele pode modificar o ponteiro original passado, fazendo ele "andar" dentro da string
    while (**s && isspace((unsigned char)**s)) (*s)++; // Enquanto o caractere atual não for o '\0' e for um espaço em branco a função incrementa o ponteiro (*s) para pular esse caractere.  
} 

// Objetivo: comparar duas strings sem considerar diferença entre letras maiúsculas e minúsculas .
int equalsIgnoreCase(const char* a, const char* b) {
    if (!a || !b) return 0; // Verifica se algum dos ponteiros é NULL
    while (*a && *b) { // Enquanto as duas strings ainda não chegaram ao final
        if (tolower((unsigned char)*a) != tolower((unsigned char)*b)) return 0; // Compara os caracteres em minúsculo, se forem diferentes retorna 0 (falso)
        a++; b++; // Avança para o próximo caractere em ambas as strings
    }
    return *a == *b; // Retorna 1 se ambas as strings chegaram ao final (ou seja, são iguais)
}

// Objetivo: verificar se uma string "padrao" aparece dentro de outra string "texto"
int containsIgnoreCase(const char *texto, const char *padrao) {
    if (!texto || !padrao) return 0; 
    size_t n = strlen(texto);
    size_t m = strlen(padrao);
    if (m == 0 || m > n) return 0;
    for (size_t i = 0; i + m <= n; i++) { // Percorre o texto posição por posição (de i = 0 até n - m), procurando uma sequência de caracteres que corresponda ao padrão.
        size_t j = 0; // Índice para percorrer o padrão
        while (j < m && tolower((unsigned char)texto[i+j]) == tolower((unsigned char)padrao[j])) j++; // Enquanto os caracteres forem iguais (ignorando maiúsculas/minúsculas)avança ambos os índices (j dentro do padrão, i+j dentro do texto).
        if (j == m) return 1; // Se percorremos todo o padrão (j == m), significa que o padrão foi encontrado dentro do texto → retorna 1 (verdadeiro).
    }
    return 0;
}


// Objetivo: "higienizar" uma string que representa lista textual, remove colchetes e aspas: [ ], " e ' ,também garante vírgula seguida de espaço: ["PT","EN-US"]  ->  PT, EN-US
void normalizarListaTexto(char* s) {
    
    char* w = s; // 'w' (write) é o ponteiro de escrita, começa no início de 's'
    for (char* r = s; *r; r++) {   // 'r' (read) percorre a string original
        if (*r == '[' || *r == ']' || *r == '"' || *r == '\'') continue; // Se o caractere atual for [, ], " ou ' → pula (não copia)
        *w++ = *r; // // Caso contrário, copia o caractere para a posição de escrita e avança 'w'
    }
    *w = '\0'; 

    char temp[TAM_MAX_CAMPO * 2]; // Buffer temporário para montar a versão formatada, (tamanho 2x por segurança)
    
    int j = 0; // Índice de escrita em 'temp'
    
    for (int i = 0; s[i] && j + 2 < (int)sizeof(temp); i++) { // Percorre a string 's', o j + 2 é para garantir espaço para vírgula e espaço
        if (s[i] == ',') { 
            temp[j++] = ','; // Sempre coloca a vírgula
            if (s[i + 1] && s[i + 1] != ' ') temp[j++] = ' '; // Se o próximo caractere existir e NÃO for espaço, insere um espaço para padronizar como ", "
        } else {
            temp[j++] = s[i]; // Qualquer outro caractere é copiado normalmente
        }
    }
    
    temp[j] = '\0';

    strcpy(s, temp);  // Copia o resultado final de volta para 's'
}

// Objetivo: dividir uma string em várias partes (tokens) de acordo com um delimitador (ex: vírgula), limpando os espaços em volta de cada parte, ex: Entrada: "PT , EN-US , FR" -> Saída: lista = ["PT", "EN-US", "FR"], qtd = 3
char** splitLista(const char* texto, const char* delim, int* qtd) {
    *qtd = 0;
    if (!texto || !*texto) return NULL;

    char* copia = dupString(texto);  // Cria uma cópia da string original
    char* token = strtok(copia, delim); // Usa strtok para obter o primeiro token baseado no delimitador fornecido
    char** lista = NULL; // Cria um ponteiro para o array de ponteiros de strings (que será a lista de tokens)

    while (token) { // Percorre todos os tokens gerados por strtok.
        char* p = token; // Cria um ponteiro auxiliar 'p' apontando para o token atual.
        pularEspacosEsquerda(&p); // Remove espaços à esquerda do token
        trimDireita(p); // Remove espaços à direita do token
        if (*p) { // Se o token não estiver vazio após a limpeza
            lista = (char**)realloc(lista, sizeof(char*) * (*qtd + 1));  // Redimensiona o vetor 'lista' para caber mais um ponteiro. realloc mantém o conteúdo anterior e adiciona espaço para o novo
            lista[*qtd] = dupString(p); // Copia o texto para uma nova área de memória e guarda esse ponteiro dentro de 'lista'
            (*qtd)++; // Incrementa a contagem de tokens válidos encontrados
        }
        token = strtok(NULL, delim); // Obtém o próximo token
    }
    free(copia); // Libera a cópia temporária da string
    return lista;
}

// Imprime uma lista de strings no formato [A, B, C]
void imprimirLista(char** lista, int qtd) {
    printf("[");
    for (int i = 0; i < qtd; i++) {
        printf("%s%s", lista[i], (i == qtd - 1) ? "" : ", "); // Se for o último elemento não imprime a vírgula
    }
    printf("]");
}

// Libera uma lista alocada
void liberarLista(char*** lista, int* qtd) {
    if (*lista) {
        for (int i = 0; i < *qtd; i++) free((*lista)[i]); // Libera cada string individualmente
        free(*lista); // Libera o array de ponteiros
    }
    *lista = NULL; // Libera o ponteiro da lista
    *qtd = 0;
}


// Converte datas para DD/MM/YYYY
char* padronizarData(const char* dataCsv) {
    
    if (!dataCsv || !*dataCsv) return dupString("01/01/1900");

    // Remove aspas
    char tmp[TAM_MAX_CAMPO]; 
    int j = 0;
    for (int i = 0; dataCsv[i] && j + 1 < (int)sizeof(tmp); i++) { 
        if (dataCsv[i] != '"') tmp[j++] = dataCsv[i]; // Copia apenas caracteres que não sejam aspas
    }
    tmp[j] = '\0';

    // "Tokenização manual": separar mês, dia e ano.
    char buf[TAM_MAX_CAMPO]; 
    strcpy(buf, tmp); // Joga a string de tmp para buf 
    
    char *mes = NULL, *dia = NULL, *ano = NULL; 
    
    char* p = buf; 
    pularEspacosEsquerda(&p); // Pula espaços iniciais
    if (!*p) return dupString("01/01/1900"); // Se a string estiver vazia, retorna data padrão

    char* espaco = strchr(p, ' '); // Procura o primeiro espaço (separador entre mês e dia/ano), o str funciona retornando um ponteiro para a primeira ocorrência do caractere especificado
    if (espaco) { 
        *espaco = '\0'; // Separa o mês, substituindo o espaço por '\0'
        mes = p; // Mês é o início da string
        char* resto = espaco + 1; // Resto da string após o mês 
        pularEspacosEsquerda(&resto); // Pula espaços iniciais do resto

        char* virgula = strchr(resto, ','); // Procura vírgula (separador entre dia e ano)
        if (virgula) { 
            *virgula = '\0';
            dia = resto; // Dia é o início do resto
            ano = virgula + 1; // Ano é o que vem após a vírgula
            pularEspacosEsquerda(&ano); // Pula espaços iniciais do ano
        } else {       
            dia = (char*)"01"; // Se não houver vírgula, assume dia 01
            ano = resto; // Ano é o resto
        }
    } else {           
        mes = (char*)"Jan"; // Se não houver espaço, assume mês Jan
        dia = (char*)"01"; // Dia 01
        ano = p; // Ano é o que resta
    }

    // Converte mês textual para numérico
    int m = 1;
    if      (!strcmp(mes, "Jan")) m = 1;
    else if (!strcmp(mes, "Feb")) m = 2;
    else if (!strcmp(mes, "Mar")) m = 3;
    else if (!strcmp(mes, "Apr")) m = 4;
    else if (!strcmp(mes, "May")) m = 5;
    else if (!strcmp(mes, "Jun")) m = 6;
    else if (!strcmp(mes, "Jul")) m = 7;
    else if (!strcmp(mes, "Aug")) m = 8;
    else if (!strcmp(mes, "Sep")) m = 9;
    else if (!strcmp(mes, "Oct")) m = 10;
    else if (!strcmp(mes, "Nov")) m = 11;
    else if (!strcmp(mes, "Dec")) m = 12;
  
    // Converte dia e ano para inteiros
    int d = atoi(dia);
    int y = atoi(ano);
    
    // Formata data final em DD/MM/YYYY
    char out[16];
    sprintf(out, "%02d/%02d/%04d", d, m, y);
    
    // Retorna uma cópia alocada da string formatada
    return dupString(out);
}


// Extrai o primeiro número inteiro encontrado no campo "owners", ignorando separadores de milhar e parando em hífen ou espaço
int parseOwnersPrimeiroNumero(const char *campo) {
    char dig[TAM_MAX_CAMPO]; 
    int j = 0;

    // 1) Avança 'i' até achar o primeiro dígito
    int i = 0; while (campo[i] && !isdigit((unsigned char)campo[i])) i++;

    // Copia dígitos consecutivos para 'dig', ignorando vírgulas e parando em '-' ou espaço
    for (; campo[i] && j + 1 < (int)sizeof(dig); i++) {
        if (campo[i] == ',') continue;  // Ignora separador de milhar           
        if (campo[i] == '-' || campo[i] == ' ') break; // Terminou o 1º número
        if (!isdigit((unsigned char)campo[i])) break; // Qualquer coisa que não seja dígito encerra
        dig[j++] = campo[i];
    }
    dig[j] = '\0';
    return (j ? atoi(dig) : 0); // Retorna o número convertido ou 0 se nada foi encontrado
}

// Converte uma linha CSV em um struct Game preenchendo cada atributo 
void parseLinhaParaGame(Game* g, const char* linha) {
    
    char campo[TAM_MAX_CAMPO]; // Campo é a cada parte da linha sendo lida atualmente
    int pos = 0, coluna = 0; // 'coluna' indica qual atributo está sendo preenchido e  'pos' indica a posição atual dentro do campo sendo lido
    bool emAspas = false; // Indica se estamos dentro de aspas

    // Zera o struct e inicializa valores sentinela
    memset(g, 0, sizeof(*g));
    g->mScore = -1; g->uScore = -1.0f; g->conq = 0;

    // Percorre cada caractere da linha CSV
    for (int i = 0;; i++) {
        char c = linha[i]; 
        bool fim = (c == '\0'); // Marco fim da linha

        // Alterna estado de "emAspas" ao encontrar aspas, não gravando aspas no campo
        if (!fim && c == '"') { emAspas = !emAspas; continue; }

        // Se encontrar vírgula fora de aspas ou fim da linha, processa a linha atual
        if (fim || (c == ',' && !emAspas)) {
            campo[pos] = '\0';
            
            // Atribui o campo lido ao atributo correto do struct Game
            switch (coluna) {
                case 0: g->id = atoi(campo); // Id          
                    break;
                case 1: g->name = dupString(campo); // Name      
                    break;
                case 2: g->data = padronizarData(campo); // Release Date: padroniza a data
                    break;
                case 3: g->owners = parseOwnersPrimeiroNumero(campo); // Owners - extrai o primeiro número inteiro
                    break;
                case 4: 
                    g->price = containsIgnoreCase(campo, "Free to Play") ? 0.0f : (float)atof(campo); // Price: "Free to Play" => 0.0
                    break;
                case 5: {
                    char tmp[TAM_MAX_CAMPO]; strncpy(tmp, campo, sizeof(tmp)); tmp[sizeof(tmp)-1] = '\0'; // Languages -> limpar e splitar por vírgula
                    normalizarListaTexto(tmp);
                    g->languages = splitLista(tmp, ",", &g->languagesCount);
                } break;
                case 6: g->mScore = (campo[0] ? atoi(campo) : -1); // Metacritic Score -> -1 se vazio
                    break;
                case 7: g->uScore = (!campo[0] || equalsIgnoreCase(campo, "tbd")) ? -1.0f : (float)atof(campo); // User Score -> -1.0 se vazio ou "tbd"
                    break;
                case 8: g->conq   = (campo[0] ? atoi(campo) : 0);  // Achievements Conquered -> 0 se vazio
                    break;
                case 9: {
                    char tmp[TAM_MAX_CAMPO]; strncpy(tmp, campo, sizeof(tmp)); tmp[sizeof(tmp)-1] = '\0'; // Publisher -> limpar e splitar por vírgula
                    normalizarListaTexto(tmp);
                    g->publisher = splitLista(tmp, ",", &g->publisherCount);
                } break;
                case 10:{
                    char tmp[TAM_MAX_CAMPO]; strncpy(tmp, campo, sizeof(tmp)); tmp[sizeof(tmp)-1] = '\0'; // Developer -> limpar e splitar por vírgula
                    normalizarListaTexto(tmp);
                    g->dev = splitLista(tmp, ",", &g->devCount);
                } break;
                case 11:{
                    char tmp[TAM_MAX_CAMPO]; strncpy(tmp, campo, sizeof(tmp)); tmp[sizeof(tmp)-1] = '\0'; // Categories -> limpar e splitar por vírgula
                    normalizarListaTexto(tmp);
                    g->categories = splitLista(tmp, ",", &g->categoriesCount);
                } break;
                case 12:{
                    char tmp[TAM_MAX_CAMPO]; strncpy(tmp, campo, sizeof(tmp)); tmp[sizeof(tmp)-1] = '\0'; // Genres -> limpar e splitar por vírgula
                    normalizarListaTexto(tmp);
                    g->generos = splitLista(tmp, ",", &g->generosCount);
                } break;
                case 13:{
                    char tmp[TAM_MAX_CAMPO]; strncpy(tmp, campo, sizeof(tmp)); tmp[sizeof(tmp)-1] = '\0'; // Tags -> limpar e splitar por vírgula
                    normalizarListaTexto(tmp);
                    g->tags = splitLista(tmp, ",", &g->tagsCount);
                } break;
            }

            coluna++; pos = 0; // Avança para a próxima coluna e reseta a posição do campo
            if (fim) break; // Se for fim da linha, sai do loop
        } else if (pos + 1 < TAM_MAX_CAMPO) { 
            campo[pos++] = c; 
        }
    }
}


// Impressão
void imprimirGame(const Game* g) {
    printf("=> %d ## %s ## %s ## %d ## %.2f ## ", g->id, g->name, g->data, g->owners, g->price);
    imprimirLista(g->languages, g->languagesCount);
    printf(" ## %d ## %.1f ## %d ## ", g->mScore, g->uScore, g->conq);
    imprimirLista(g->publisher, g->publisherCount);
    printf(" ## ");
    imprimirLista(g->dev, g->devCount);
    printf(" ## ");
    imprimirLista(g->categories, g->categoriesCount);
    printf(" ## ");
    imprimirLista(g->generos, g->generosCount);
    printf(" ## ");
    imprimirLista(g->tags, g->tagsCount);
    printf(" ##\n");
}

// Liberação de memória
void liberarGame(Game* g) {
    free(g->name);
    free(g->data);
    liberarLista(&g->languages,  &g->languagesCount);
    liberarLista(&g->publisher,  &g->publisherCount);
    liberarLista(&g->dev,        &g->devCount);
    liberarLista(&g->categories, &g->categoriesCount);
    liberarLista(&g->generos,    &g->generosCount);
    liberarLista(&g->tags,       &g->tagsCount);
}



// Estrutura do Nó para a Árvore AVL
typedef struct No {
    Game *game; 
    struct No *esq, *dir;
    int nivel; 
} No;

// Variáveis globais/de estrutura para contagem e rastreio de busca
long long numComparacoes = 0;
char caminhoPesquisado[TAM_MAX_LINHA * 2]; 


// Retorna a altura de um nó 
int getNivel(No *no) {
    return (no == NULL) ? 0 : no->nivel;
}

// Atualiza a altura de um nó
void setNivel(No *no) {
    if (no != NULL) {
        int nivelEsq = getNivel(no->esq);
        int nivelDir = getNivel(no->dir);
        no->nivel = 1 + (nivelEsq > nivelDir ? nivelEsq : nivelDir);
    }
}

// (Rotação Simples à Direita)
No *rotacionarDir(No *no) {
    No *noEsq = no->esq;
    No *noEsqDir = noEsq->dir;

    noEsq->dir = no;
    no->esq = noEsqDir;
    
    setNivel(no);    
    setNivel(noEsq); 

    return noEsq; 
}

// Rotação Simples à Esquerda
No *rotacionarEsq(No *no) {
    No *noDir = no->dir;
    No *noDirEsq = noDir->esq;

    noDir->esq = no;
    no->dir = noDirEsq;

    setNivel(no);   
    setNivel(noDir);

    return noDir; 
}

// Balanceamento do nó
No *balancear(No *no) {
    if (no != NULL) {
        setNivel(no);
        int fator = getNivel(no->dir) - getNivel(no->esq);

        // Desbalanceado para a direita (fator == 2)
        if (fator == 2) {
            int fatorFilhoDir = getNivel(no->dir->dir) - getNivel(no->dir->esq);
            // Rotação Dupla à Esquerda (simples direita no filho + simples esquerda no nó)
            if (fatorFilhoDir == -1) {
                no->dir = rotacionarDir(no->dir);
            }
            // Rotação Simples à Esquerda
            no = rotacionarEsq(no);
        }
        // Desbalanceado para a esquerda (fator == -2)
        else if (fator == -2) {
            int fatorFilhoEsq = getNivel(no->esq->dir) - getNivel(no->esq->esq);
            // Rotação Dupla à Direita (simples esquerda no filho + simples direita no nó)
            if (fatorFilhoEsq == 1) {
                no->esq = rotacionarEsq(no->esq);
            }
            // Rotação Simples à Direita
            no = rotacionarDir(no);
        }
    }
    return no;
}


No *inserirRec(No *i, Game *novoGame) {
    if (i == NULL) {
        No *novo = (No*)malloc(sizeof(No));
        novo->game = novoGame;
        novo->esq = novo->dir = NULL;
        novo->nivel = 1;
        return novo;
    }

    int comparacao = strcmp(novoGame->name, i->game->name);

    if (comparacao < 0) {
        i->esq = inserirRec(i->esq, novoGame);
    } else if (comparacao > 0) {
        i->dir = inserirRec(i->dir, novoGame);
    } 
    // Se comparacao == 0 (nome duplicado), não faz nada

    return balancear(i);
}

// Pesquisa recursiva na árvore AVL
Game *pesquisarRec(No *i, const char *nameBusca) {
    if (i == NULL) {
        return NULL;
    }
    
    numComparacoes++;
    int comparacao = strcmp(nameBusca, i->game->name);

    if (comparacao == 0) {
        return i->game; // Encontrado
    } else if (comparacao < 0) {
        strcat(caminhoPesquisado, " esq");
        return pesquisarRec(i->esq, nameBusca);
    } else {
        strcat(caminhoPesquisado, " dir");
        return pesquisarRec(i->dir, nameBusca);
    }
}

// Pesquisa na árvore AVL com rastreio do caminho
Game *pesquisar(No *raiz, const char *nameBusca) {
    caminhoPesquisado[0] = '\0'; // Zera o rastreio
    strcat(caminhoPesquisado, "raiz");
    numComparacoes = 0;
    return pesquisarRec(raiz, nameBusca);
}

// Função para liberar a memória da árvore
void liberarArvore(No *i) {
    if (i != NULL) {
        liberarArvore(i->esq);
        liberarArvore(i->dir);
        free(i); 
    }
}


// Main
int main(void) {
    setlocale(LC_NUMERIC, "C"); 

    const char* caminhoCSV = "/tmp/games.csv";  

    FILE* f = fopen(caminhoCSV, "r");
    if (!f) { perror("Erro ao abrir /tmp/games.csv"); return 1; }

    char linha[TAM_MAX_LINHA];
    int total = 0;
    if (fgets(linha, sizeof(linha), f)) { 
        while (fgets(linha, sizeof(linha), f)) total++; 
    }
    fclose(f);

    Game* jogos = (Game*)malloc(sizeof(Game) * (total > 0 ? total : 1));
    int usados = 0; 

    f = fopen(caminhoCSV, "r");
    if (!f) { perror("Erro ao reabrir /tmp/games.csv"); free(jogos); return 1; }

    fgets(linha, sizeof(linha), f); // Pula cabeçalho
    while (fgets(linha, sizeof(linha), f)) { 
        linha[strcspn(linha, "\r\n")] = 0;
        if (usados < total) { 
            parseLinhaParaGame(&jogos[usados], linha); 
            usados++;
        }
    }
    fclose(f);
    
    // Inserção na AVL a partir dos IDs de Entrada 
    
    No *raiz = NULL; 
    char entrada[TAM_MAX_CAMPO]; 
    
    // Leitura dos IDs até "FIM"
    while (fgets(entrada, sizeof(entrada), stdin)) {
        entrada[strcspn(entrada, "\r\n")] = '\0';
        if (entrada[0] == '\0') continue;
        if (strcmp(entrada, "FIM") == 0) break; 

        int idBuscado = atoi(entrada);
        
        // Busca linear no array principal para obter o Game
        for (int i = 0; i < usados; i++) {
            if (jogos[i].id == idBuscado) {
                // Inserir o ponteiro para o Game na AVL
                raiz = inserirRec(raiz, &jogos[i]); 
                break;
            }
        }
    }

    // Pesquisa na AVL 

    clock_t inicio, fim;
    double tempoExecucao = 0.0;
    
    inicio = clock();

    while (fgets(entrada, sizeof(entrada), stdin)) {
        entrada[strcspn(entrada, "\r\n")] = '\0';
        if (entrada[0] == '\0') continue;

       
        if (strcmp(entrada, "FIM") == 0) break; 
      

        char nomeBusca[TAM_MAX_CAMPO];
        strcpy(nomeBusca, entrada); // A entrada é o nome do jogo a ser buscado

        // Executa a pesquisa na AVL
        Game *resultado = pesquisar(raiz, nomeBusca);

        // Formatação da Saída Padrão
        char *status = (resultado != NULL) ? "SIM" : "NAO";
        
        // Imprime o resultado formatado
        printf("%s: %s %s\n", nomeBusca, caminhoPesquisado, status);
    }
    
    fim = clock();
    tempoExecucao = (double)(fim - inicio) / CLOCKS_PER_SEC;

    // Criação do Arquivo de Log e Liberação 
    
    const char* matricula = "885473"; 
    char nomeLog[64];
    sprintf(nomeLog, "%s_arvoreAVL.txt", matricula);

    FILE* logFile = fopen(nomeLog, "w");
    if (logFile) { 
        fprintf(logFile, "Matrícula: %s\tComparações: %lld\tTempo (s): %lf\n",
            matricula, 
            numComparacoes, 
            tempoExecucao);
        fclose(logFile);
    } else {
        perror("Erro ao criar arquivo de log");
    }

    // Liberação de memória
    liberarArvore(raiz);
    for (int i = 0; i < usados; i++) liberarGame(&jogos[i]);
    free(jogos); 
    
    return 0;
}