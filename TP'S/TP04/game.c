#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

// Definição da struct Game
typedef struct{
    int id;
    char name[100];
    char releaseDate[20];
    int estimatedOwners;
    float price;
    char supportedLanguages[200];
    int metacriticScore;
    float userScore;
    int achievements;
    char publishers[100];
    char developers[100];
    char categories[200];
    char genres[200];
    char tags[300];
} Game;

// Método para imprimir os dados de um jogo
void imprimirGame(Game g) {
    printf("=> %d ## %s ## %s ## %d ## %.2f ## %s ## %d ## %.2f ## %d ## %s ## %s ## %s ## %s ## %s ##\n",
           g.id, g.name, g.releaseDate, g.estimatedOwners, g.price,
           g.supportedLanguages, g.metacriticScore, g.userScore,
           g.achievements, g.publishers, g.developers,
           g.categories, g.genres, g.tags);
}

// Método para ler o CSV
Game lerGame(char *linha) {

    Game g; // Cria um "objeto" do tipo Game
    char *atributo; // Ponteiro para armazenar cada atributo lido
    int index = 0; // Índice para percorrer a linha

    // Divide a linha em atributos usando vírgula como delimitador
    atributo = strtok(linha, ","); 
    while(atributo != NULL) { // Enquanto houver atributos
        switch(index) { // Atribui cada atributo ao campo correspondente do struct
            // ID
            case 0: g.id = atoi(atributo); break;
            // Name
            case 1: strcpy(g.name, atributo); break;
            // Release date
            case 2: strcpy(verificarData(g.releaseDate), atributo); break;
            // Estimated owners
            case 3: g.estimatedOwners = atoi(token); break;
            // Price
            case 4: g.price = atof(token); break;
            // Supported languages
            case 5: strcpy(g.supportedLanguages, token); break;
            // Metacritic score
            case 6: g.metacriticScore = atoi(token); break;
            // User score
            case 7: g.userScore = atof(token); break;
            // Achievements
            case 8: g.achievements = atoi(token); break;
            // Publishers
            case 9: strcpy(g.publishers, token); break;
            // Developers
            case 10: strcpy(g.developers, token); break;
            // Categories
            case 11: strcpy(g.categories, token); break;
            // Genres
            case 12: strcpy(g.genres, token); break;
            // Tags
            case 13: strcpy(g.tags, token); break;
        }
        token = strtok(NULL, ",");
        campo++;
    }
    return g;
}

// Caso o dia e/ou o mês estejam vazios, adiciona '01' à eles
void verificarData(char *data, char *saida) {
    char mes[4], dia[5] = "01", ano[10] = "1900";
    int n = sscanf(data, "%3s %4s %4s", mes, dia, ano);

    // se veio só ano
    if(n == 1) {
        strcpy(ano, mes);
        strcpy(mes, "01");
    }
    // se veio mês e ano
    else if(n == 2) {
        strcpy(ano, dia);
        strcpy(dia, "01");
    }

    // converter mês
    int mesNum = 1;
    if(strcmp(mes, "Jan") == 0) mesNum = 1;
    else if(strcmp(mes, "Feb") == 0) mesNum = 2;
    else if(strcmp(mes, "Mar") == 0) mesNum = 3;
    else if(strcmp(mes, "Apr") == 0) mesNum = 4;
    else if(strcmp(mes, "May") == 0) mesNum = 5;
    else if(strcmp(mes, "Jun") == 0) mesNum = 6;
    else if(strcmp(mes, "Jul") == 0) mesNum = 7;
    else if(strcmp(mes, "Aug") == 0) mesNum = 8;
    else if(strcmp(mes, "Sep") == 0) mesNum = 9;
    else if(strcmp(mes, "Oct") == 0) mesNum = 10;
    else if(strcmp(mes, "Nov") == 0) mesNum = 11;
    else if(strcmp(mes, "Dec") == 0) mesNum = 12;

    sprintf(saida, "%02d/%02d/%s", atoi(dia), mesNum, ano);
}




int main() {

    FILE *fp = fopen("C:\\Users\\Matheus\\Downloads\\games.csv", "r"); // Caminho do arquivo CSV
    if(fp == NULL) {
        printf("Erro ao abrir arquivo!\n");
        return 1;
    }

    Game jogos[MAX_JOGOS]; // Array para armazenar os jogos
    int qtd = 0; // Quantidade de jogos lidos
    char linha[2000]; // Buffer para ler cada linha do arquivo

    fgets(linha, sizeof(linha), fp); // Descarta cabeçalho

    while(fgets(linha, sizeof(linha), fp) != NULL && qtd < MAX_JOGOS) { // Lê cada linha do arquivo
        jogos[qtd++] = lerGame(linha); // Converte a linha para um struct Game e armazena no array
    }
    fclose(fp);

    // Busca por IDs
    char entrada[1000]; 

    while(1) {
        fgets(entrada, sizeof(entrada), stdin); // Lê a entrada do usuário
        if(entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M')
            break; 

        int idBuscado = stringParaInt(entrada); // Converte a entrada para um inteiro
        int encontrado = 0; // Flag para indicar se o jogo foi encontrado

        for(int i = 0; i < qtd; i++) { 
            if(jogos[i].id == idBuscado) {
                imprimirGame(jogos[i]); 
                encontrado = 1; 
                break;
            }
        }
        if(!encontrado) { 
            printf("Jogo não encontrado!\n");
        }
    }

    return 0;
}

