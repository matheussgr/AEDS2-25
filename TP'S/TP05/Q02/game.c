// Matheus Gouvêa Ramalho - TP04

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <locale.h>

#define TAM_MAX_LINHA 4096
#define TAM_MAX_CAMPO 1024

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

// Duplica uma string (malloc) e retorna NULL se falhar
static char* dupString(const char* s) {
    if (!s) return NULL;
    size_t n = strlen(s) + 1;
    char* nova = (char*)malloc(n);
    if (nova) memcpy(nova, s, n);
    return nova;
}

// Remove espaços à direita 
static void trimDireita(char* s) {
    int i = (int)strlen(s) - 1;
    while (i >= 0 && isspace((unsigned char)s[i])) s[i--] = '\0';
}

// Avança ponteiro até o primeiro caractere não espaço
static void pularEspacosEsquerda(char** s) {
    while (**s && isspace((unsigned char)**s)) (*s)++;
}

// Comparação case-insensitive (retorna 1 se igual)
static int equalsIgnoreCase(const char* a, const char* b) {
    if (!a || !b) return 0;
    while (*a && *b) {
        if (tolower((unsigned char)*a) != tolower((unsigned char)*b)) return 0;
        a++; b++;
    }
    return *a == *b;
}

// Verifica se 'texto' contém 'padrao' (case-insensitive)
static int containsIgnoreCase(const char *texto, const char *padrao) {
    if (!texto || !padrao) return 0;
    size_t n = strlen(texto), m = strlen(padrao);
    if (m == 0 || m > n) return 0;
    for (size_t i = 0; i + m <= n; i++) {
        size_t j = 0;
        while (j < m &&
               tolower((unsigned char)texto[i+j]) == tolower((unsigned char)padrao[j])) j++;
        if (j == m) return 1;
    }
    return 0;
}

// [ 'A', "B", C ]  ->  remove colchetes/aspas e normaliza ", "

static void normalizarListaTexto(char* s) {
    // remove [, ], ' e "
    char* w = s;
    for (char* r = s; *r; r++) {
        if (*r == '[' || *r == ']' || *r == '"' || *r == '\'') continue;
        *w++ = *r;
    }
    *w = '\0';

    // garante vírgula seguida de espaço (", ")
    char temp[TAM_MAX_CAMPO * 2];
    int j = 0;
    for (int i = 0; s[i] && j + 2 < (int)sizeof(temp); i++) {
        if (s[i] == ',') {
            temp[j++] = ',';
            if (s[i + 1] && s[i + 1] != ' ') temp[j++] = ' ';
        } else {
            temp[j++] = s[i];
        }
    }
    temp[j] = '\0';
    strcpy(s, temp);
}

// Divide uma string em uma lista de strings, retornando a lista e a quantidade
static char** splitLista(const char* texto, const char* delim, int* qtd) {
    *qtd = 0;
    if (!texto || !*texto) return NULL;

    char* copia = dupString(texto);
    char* token = strtok(copia, delim);
    char** lista = NULL;

    while (token) {
        char* p = token;
        pularEspacosEsquerda(&p);
        trimDireita(p);
        if (*p) {
            lista = (char**)realloc(lista, sizeof(char*) * (*qtd + 1));
            lista[*qtd] = dupString(p);
            (*qtd)++;
        }
        token = strtok(NULL, delim);
    }
    free(copia);
    return lista;
}

// Imprime uma lista de strings no formato [A, B, C]
static void imprimirLista(char** lista, int qtd) {
    printf("[");
    for (int i = 0; i < qtd; i++) {
        printf("%s%s", lista[i], (i == qtd - 1) ? "" : ", ");
    }
    printf("]");
}

// Libera uma lista alocada
static void liberarLista(char*** lista, int* qtd) {
    if (*lista) {
        for (int i = 0; i < *qtd; i++) free((*lista)[i]);
        free(*lista);
    }
    *lista = NULL;
    *qtd = 0;
}


// converte "MMM d, yyyy" | "MMM yyyy" | "yyyy"  -> "dd/mm/aaaa"
static char* padronizarData(const char* dataCsv) {
    if (!dataCsv || !*dataCsv) return dupString("01/01/1900");

    // tira aspas
    char tmp[TAM_MAX_CAMPO];
    int j = 0;
    for (int i = 0; dataCsv[i] && j + 1 < (int)sizeof(tmp); i++) {
        if (dataCsv[i] != '"') tmp[j++] = dataCsv[i];
    }
    tmp[j] = '\0';

    // tokeniza manualmente
    char buf[TAM_MAX_CAMPO];
    strcpy(buf, tmp);
    char *mes = NULL, *dia = NULL, *ano = NULL;
    char* p = buf;
    pularEspacosEsquerda(&p);
    if (!*p) return dupString("01/01/1900");

    char* espaco = strchr(p, ' ');
    if (espaco) {
        *espaco = '\0';
        mes = p;
        char* resto = espaco + 1;
        pularEspacosEsquerda(&resto);

        char* virgula = strchr(resto, ',');
        if (virgula) { // Mon DD, YYYY
            *virgula = '\0';
            dia = resto;
            ano = virgula + 1;
            pularEspacosEsquerda(&ano);
        } else {       // Mon YYYY
            dia = (char*)"01";
            ano = resto;
        }
    } else {           // YYYY
        mes = (char*)"Jan";
        dia = (char*)"01";
        ano = p;
    }

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

    int d = atoi(dia);
    int y = atoi(ano);
    char out[16];
    sprintf(out, "%02d/%02d/%04d", d, m, y);
    return dupString(out);
}



// Leitura e parsing de CSV

// Extrai o primeiro número de 'owners' (ignora vírgulas e para em '-' ou espaço)
static int parseOwnersPrimeiroNumero(const char *campo) {
    char dig[TAM_MAX_CAMPO];
    int j = 0;

    // pula até primeiro dígito
    int i = 0; while (campo[i] && !isdigit((unsigned char)campo[i])) i++;

    for (; campo[i] && j + 1 < (int)sizeof(dig); i++) {
        if (campo[i] == ',') continue;            // separador de milhar
        if (campo[i] == '-' || campo[i] == ' ') break; // fim do primeiro número
        if (!isdigit((unsigned char)campo[i])) break;
        dig[j++] = campo[i];
    }
    dig[j] = '\0';
    return (j ? atoi(dig) : 0);
}

// Converte uma linha CSV (com aspas) em um Game
static void parseLinhaParaGame(Game* g, const char* linha) {
    char campo[TAM_MAX_CAMPO];
    int pos = 0, coluna = 0;
    bool emAspas = false;

    memset(g, 0, sizeof(*g));
    g->mScore = -1; g->uScore = -1.0f; g->conq = 0;

    // varredura char a char: quebra em vírgula APENAS se fora de aspas
    for (int i = 0;; i++) {
        char c = linha[i];
        bool fim = (c == '\0');

        if (!fim && c == '"') { emAspas = !emAspas; continue; }

        if (fim || (c == ',' && !emAspas)) {
            campo[pos] = '\0';
            switch (coluna) {
                case 0: g->id   = atoi(campo);           break;
                case 1: g->name = dupString(campo);       break;
                case 2: g->data = padronizarData(campo);  break;
                case 3: g->owners = parseOwnersPrimeiroNumero(campo); break;
                case 4: // "Free to Play" => 0.0
                    g->price = containsIgnoreCase(campo, "Free to Play") ? 0.0f : (float)atof(campo);
                    break;
                case 5: {
                    char tmp[TAM_MAX_CAMPO]; strncpy(tmp, campo, sizeof(tmp)); tmp[sizeof(tmp)-1] = '\0';
                    normalizarListaTexto(tmp);
                    g->languages = splitLista(tmp, ",", &g->languagesCount);
                } break;
                case 6: g->mScore = (campo[0] ? atoi(campo) : -1); break;
                case 7: g->uScore = (!campo[0] || equalsIgnoreCase(campo, "tbd")) ? -1.0f : (float)atof(campo); break;
                case 8: g->conq   = (campo[0] ? atoi(campo) : 0);  break;
                case 9: {
                    char tmp[TAM_MAX_CAMPO]; strncpy(tmp, campo, sizeof(tmp)); tmp[sizeof(tmp)-1] = '\0';
                    normalizarListaTexto(tmp);
                    g->publisher = splitLista(tmp, ",", &g->publisherCount);
                } break;
                case 10:{
                    char tmp[TAM_MAX_CAMPO]; strncpy(tmp, campo, sizeof(tmp)); tmp[sizeof(tmp)-1] = '\0';
                    normalizarListaTexto(tmp);
                    g->dev = splitLista(tmp, ",", &g->devCount);
                } break;
                case 11:{
                    char tmp[TAM_MAX_CAMPO]; strncpy(tmp, campo, sizeof(tmp)); tmp[sizeof(tmp)-1] = '\0';
                    normalizarListaTexto(tmp);
                    g->categories = splitLista(tmp, ",", &g->categoriesCount);
                } break;
                case 12:{
                    char tmp[TAM_MAX_CAMPO]; strncpy(tmp, campo, sizeof(tmp)); tmp[sizeof(tmp)-1] = '\0';
                    normalizarListaTexto(tmp);
                    g->generos = splitLista(tmp, ",", &g->generosCount);
                } break;
                case 13:{
                    char tmp[TAM_MAX_CAMPO]; strncpy(tmp, campo, sizeof(tmp)); tmp[sizeof(tmp)-1] = '\0';
                    normalizarListaTexto(tmp);
                    g->tags = splitLista(tmp, ",", &g->tagsCount);
                } break;
            }
            coluna++; pos = 0;
            if (fim) break;
        } else if (pos + 1 < TAM_MAX_CAMPO) {
            campo[pos++] = c;
        }
    }
}


// Impressão e liberação
static void imprimirGame(const Game* g) {
    // OBS: saída exatamente no formato do enunciado
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

static void liberarGame(Game* g) {
    free(g->name);
    free(g->data);
    liberarLista(&g->languages,  &g->languagesCount);
    liberarLista(&g->publisher,  &g->publisherCount);
    liberarLista(&g->dev,        &g->devCount);
    liberarLista(&g->categories, &g->categoriesCount);
    liberarLista(&g->generos,    &g->generosCount);
    liberarLista(&g->tags,       &g->tagsCount);
}

static void selectionSort(Game** array, int n) {
    for (int i = 0; i < (n - 1); i++) {
        int menor = i;
        for (int j = (i + 1); j < n; j++) {
            if (strcmp(array[j]->name, array[menor]->name) < 0) {
                menor = j;
            }
        }
        if (menor != i) {
            Game* temp = array[i];
            array[i] = array[menor];
            array[menor] = temp;
        }
    }
}

// Main
int main(void) {
    setlocale(LC_NUMERIC, "C"); // garante ponto decimal em floats

    const char* caminhoCSV = "/tmp/games.csv";  

    // 1) Conta linhas (exceto cabeçalho) para alocar vetor
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

    // 2) Reabre e carrega efetivamente
    f = fopen(caminhoCSV, "r");
    if (!f) { perror("Erro ao reabrir /tmp/games.csv"); free(jogos); return 1; }

    fgets(linha, sizeof(linha), f); // pula cabeçalho
    while (fgets(linha, sizeof(linha), f)) {
        linha[strcspn(linha, "\r\n")] = 0;
        if (usados < total) {
            parseLinhaParaGame(&jogos[usados], linha);
            usados++;
        }
    }
    fclose(f);

// 2) Carrega ponteiros para jogos a serem ordenados (leitura da stdin)
    
    // Alocação dinâmica para o array de ponteiros (Game**)
    Game** jogosParaOrdenar = (Game**)malloc(sizeof(Game*) * 10);
    int countParaOrdenar = 0;
    int capacidade = 10;
    char entrada[TAM_MAX_CAMPO];
    
    // Leitura dos IDs até "FIM"
    while (fgets(entrada, sizeof(entrada), stdin)) {
        entrada[strcspn(entrada, "\r\n")] = 0;
        if (entrada[0] == '\0') continue;
        if (strcmp(entrada, "FIM") == 0) break;

        int idBuscado = atoi(entrada);
        
        // Busca o Game correspondente no array principal
        for (int i = 0; i < usados; i++) {
            if (jogos[i].id == idBuscado) {
                // Realoca o array de ponteiros se a capacidade for excedida
                if (countParaOrdenar == capacidade) {
                    capacidade *= 2;
                    Game** novoArray = (Game**)realloc(jogosParaOrdenar, sizeof(Game*) * capacidade);
                    if (!novoArray) {
                        perror("Erro ao realocar array de ponteiros.");
                        // Limpeza e saída
                        free(jogosParaOrdenar);
                        for (int k = 0; k < usados; k++) liberarGame(&jogos[k]);
                        free(jogos);
                        return 1; 
                    }
                    jogosParaOrdenar = novoArray;
                }
                
                // Armazena o ponteiro para a struct Game
                jogosParaOrdenar[countParaOrdenar++] = &jogos[i];
                break;
            }
        }
    }
    
    // 3) Ordenação dos jogos carregados por 'name' usando Selection Sort
    if (countParaOrdenar > 0) {
        selectionSort(jogosParaOrdenar, countParaOrdenar);
    }

    // 4) Impressão dos jogos ordenados
    for (int i = 0; i < countParaOrdenar; i++) {
        imprimirGame(jogosParaOrdenar[i]);
    }

    // 5) Liberação de memória
    free(jogosParaOrdenar); // Libera o vetor de ponteiros
    for (int i = 0; i < usados; i++) liberarGame(&jogos[i]); // Libera campos internos
    free(jogos); // Libera o vetor de structs
    
    return 0;
}
