// Matheus Gouvêa Ramalho - TP05 - Q04 - Selection Sort

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>  
#include <locale.h> 
#include <time.h>

#define TAM_MAX_LINHA 4096 // Tamanho máximo de uma linha do CSV
#define TAM_MAX_CAMPO 1024 // Tamanho máximo de um campo do CSV

long long comparacoes = 0;
long long movimentacoes = 0;
const char* MATRICULA = "885473"; 

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

// Cria uma cópia exata de uma string em nova área de memória
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



// Converte uma linha CSV em um struct Game preenchendo cada atributo 

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


int isMenor(const Game* a, const Game* b) {
    // Comparação de Data 

    // Primeiro compara o ano
    int cmp_ano = strncmp(&a->data[6], &b->data[6], 4); // Compara os indices 6 à 9
    comparacoes++;
    if (cmp_ano != 0) return cmp_ano; // Retorna < 0 se a < b ou > 0 se a > b

    // Depois o mês
    int cmp_mes = strncmp(&a->data[3], &b->data[3], 2);
    comparacoes++;
    if (cmp_mes != 0) return cmp_mes;

    // E por fim o dia
    int cmp_dia = strncmp(&a->data[0], &b->data[0], 2);
    comparacoes++;
    if (cmp_dia != 0) return cmp_dia;

    // Desempate por AppID 
    comparacoes++; 
    if (a->id < b->id) return -1;
    if (a->id > b->id) return 1;

    // Se tudo for igual
    return 0;
}


void swap(Game** array, int i, int j) {
    Game* temp = array[i];
    array[i] = array[j];
    array[j] = temp;
    movimentacoes += 3; 
}

// Ordenação Quick Sort
static void quicksortRec(Game** array, int esq, int dir) {
    int i = esq, j = dir;
    
    Game* pivo = array[(dir+esq)/2]; 
 
    
    while (i <= j) {
       
    while (isMenor(array[i], pivo) < 0) i++;
        
    while (isMenor(array[j], pivo) > 0) j--;
        
   
    if (i <= j) {
    swap(array, i, j);
    i++;
    j--;
 }
}
    
    
if (esq < j) quicksortRec(array, esq, j);
if (i < dir) quicksortRec(array, i, dir);
}


static void quicksort(Game** array, int n) {
quicksortRec(array, 0, n-1);
}


// Main
int main(void) {
    setlocale(LC_NUMERIC, "C"); // Garante que o ponto seja usado como separador decimal

    const char* caminhoCSV = "/tmp/games.csv";  

    // Conta linhas (exceto cabeçalho) para alocar vetor

    FILE* f = fopen(caminhoCSV, "r");
    if (!f) { perror("Erro ao abrir /tmp/games.csv"); return 1; }

    char linha[TAM_MAX_LINHA];
    int total = 0;

    if (fgets(linha, sizeof(linha), f)) { // Pula cabeçalho
        while (fgets(linha, sizeof(linha), f)) total++; // Conta linhas
    }
    fclose(f);

    Game* jogos = (Game*)malloc(sizeof(Game) * (total > 0 ? total : 1)); // Aloca vetor de structs Game
    int usados = 0; // Contador de jogos efetivamente carregados

    // Reabre e carrega efetivamente
    f = fopen(caminhoCSV, "r");
    if (!f) { perror("Erro ao reabrir /tmp/games.csv"); free(jogos); return 1; }

    fgets(linha, sizeof(linha), f); // Pula cabeçalho
    while (fgets(linha, sizeof(linha), f)) { // Lê cada linha
        linha[strcspn(linha, "\r\n")] = 0; // Remove \r e \n do final da linha
        if (usados < total) { 
            parseLinhaParaGame(&jogos[usados], linha); // Converte linha e armazena no vetor jogos
            usados++; // Incrementa contador de jogos carregados
        }
    }
    fclose(f);

    // Cria um array de ponteiros de ponteiros para Games para armazenar os jogos a serem ordenados
    
    Game** jogosParaOrdenar = (Game**)malloc(sizeof(Game*) * 10);
    
    int countParaOrdenar = 0; // Contador de jogos a serem ordenados
    int capacidade = 10; 
    char entrada[TAM_MAX_CAMPO]; // Array para ler entradas 
    
    // Leitura dos IDs até "FIM"
    while (fgets(entrada, sizeof(entrada), stdin)) {
        entrada[strcspn(entrada, "\r\n")] = '\0'; // "strcspn" procura o primeiro \r ou \n na string "entrada", depois ele substitui esse caractere por '\0', cortando a string nesse ponto, eliminando a parte que vem depois 
        if (entrada[0] == '\0') continue; // Ignora linhas vazias
        if (strcmp(entrada, "FIM") == 0) break; 

        int idBuscado = atoi(entrada); // Converte a entrada para inteiro
        
        // Busca o Game correspondente no array principal
        for (int i = 0; i < usados; i++) {
            if (jogos[i].id == idBuscado) { // Encontrou o jogo com o ID correspondente
                // Realoca o array de ponteiros se a capacidade for excedida
                if (countParaOrdenar == capacidade) { 
                    capacidade *= 2;
                    Game** novoArray = (Game**)realloc(jogosParaOrdenar, sizeof(Game*) * capacidade); 
                    if (!novoArray) {
                        perror("Erro ao realocar array de ponteiros.");
                        free(jogosParaOrdenar);
                        for (int k = 0; k < usados; k++) liberarGame(&jogos[k]);
                        free(jogos);
                        return 1; 
                    }
                    jogosParaOrdenar = novoArray;
                }
                
                jogosParaOrdenar[countParaOrdenar++] = &jogos[i]; // Adiciona o ponteiro do jogo ao array de ordenação, formando um array de ponteiros correspondentes aos jogos inseridos, e incrementa o contador
                break;
            }
        }
    }

    // Medição de tempo e contagem de comparações/movimentações
    comparacoes = 0;
    movimentacoes = 0;
    clock_t inicio, fim;
    double tempoExecucao = 0.0;

    // Ordenação dos jogos fornecidos na pub.in usando QuickSort
    if (countParaOrdenar > 0) {
        inicio = clock();
        quicksort(jogosParaOrdenar, countParaOrdenar);
        fim = clock();
        tempoExecucao = (double)(fim - inicio) / CLOCKS_PER_SEC;
    }

    // Impressão dos jogos ordenados
    for (int i = 0; i < countParaOrdenar; i++) {
        imprimirGame(jogosParaOrdenar[i]);
    }

    // Criação do Arquivo de Log 
    const char* matricula = "885473"; 
    char nomeLog[64];
    sprintf(nomeLog, "%s_quicksort.txt", matricula);

     FILE* logFile = fopen(nomeLog, "w");
     if (logFile) { 
        fprintf(logFile, "Matrícula: %s\tComparações: %lld\tMovimentações: %lld\tTempo (s): %lf\n",
            matricula, 
            comparacoes, 
            movimentacoes,  
            tempoExecucao);
        fclose(logFile);
    } else {
        perror("Erro ao criar arquivo de log");
    }

    // Liberação de memória
    free(jogosParaOrdenar); // Libera o vetor de ponteiros
    for (int i = 0; i < usados; i++) liberarGame(&jogos[i]); // Libera campos internos
    free(jogos); // Libera o vetor de structs
    
    return 0;
}
