// Matheus Gouvêa Ramalho - TP07Q01 - Árvore Binária em Java

import java.io.*;
import java.util.Scanner;
import java.util.Date;

// Nó da Árvore Binária
class No {
    public game elemento;
    public No esq;
    public No dir;

    public No(game elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
    }
}

// Árvore Binária de Busca
class ArvoreBinaria {
    
    private No raiz;
    
    public int numComparacoes = 0;
    public String caminhoPesquisado = ""; // Rastreio do caminho percorrido na pesquisa

    public ArvoreBinaria() {
        this.raiz = null;
    }


    public void inserir(game novoGame) {
        this.raiz = inserir(this.raiz, novoGame);
    }

    private No inserir(No i, game novoGame) {
        if (i == null) {
            i = new No(novoGame);
        }

        int comparacao = novoGame.getName().compareTo(i.elemento.getName());

        if (comparacao < 0) {
            i.esq = inserir(i.esq, novoGame);
        } else if (comparacao > 0) {
            i.dir = inserir(i.dir, novoGame);
        }
        // Se comparacao == 0, o nome já existe, não insere

        return i;
    }
    
    
    public game pesquisar(String nameBusca) {
        // Zera o rastreio antes de cada busca e inicia com "raiz"
        this.caminhoPesquisado = "raiz";
        
        return pesquisar(this.raiz, nameBusca.trim());
    }

    private game pesquisar(No i, String nameBusca) {
        if (i == null) {
            return null;
        }

        // Conta a comparação
        this.numComparacoes++;
        int comparacao = nameBusca.compareTo(i.elemento.getName());

        if (comparacao == 0) { // Nome encontrado
            return i.elemento;
        } else if (comparacao < 0) {
            this.caminhoPesquisado += " esq";
            return pesquisar(i.esq, nameBusca);
        } else {
            this.caminhoPesquisado += " dir";
            return pesquisar(i.dir, nameBusca);
        }
    }
}

public class game {

    // Atributos
    private int id;
    private String name;
    private String releaseDate;
    private int estimatedOwners;
    private float price;
    private String[] supportedLanguages;
    private int metacriticScores;
    private float userScore;
    private int achievements;
    private String[] publishers;
    private String[] developers;
    private String[] categories;
    private String[] genres;
    private String[] tags;

    // Construtores
    public game() {
    }

    public game(int id, String name, String releaseDate, int estimatedOwners, float price, String[] supportedLanguages,
            int metacriticScores, float userScore, int achievements, String[] publishers, String[] developers,
            String[] categories, String[] genres, String[] tags) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.estimatedOwners = estimatedOwners;
        this.price = price;
        this.supportedLanguages = supportedLanguages;
        this.metacriticScores = metacriticScores;
        this.userScore = userScore;
        this.achievements = achievements;
        this.publishers = publishers;
        this.developers = developers;
        this.categories = categories;
        this.genres = genres;
        this.tags = tags;
    }

    // Getters e Setters
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getEstimatedOwners() {
        return estimatedOwners;
    }

    public void setEstimatedOwners(int estimatedOwners) {
        this.estimatedOwners = estimatedOwners;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String[] getSupportedLanguages() {
        return supportedLanguages;
    }

    public void setSupportedLanguages(String[] supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    public int getMetacriticScores() {
        return metacriticScores;
    }

    public void setMetacriticScores(int metacriticScores) {
        this.metacriticScores = metacriticScores;
    }

    public float getUserScore() {
        return userScore;
    }

    public void setUserScore(float userScore) {
        this.userScore = userScore;
    }

    public int getAchievements() {
        return achievements;
    }

    public void setAchievements(int achievements) {
        this.achievements = achievements;
    }

    public String[] getPublishers() {
        return publishers;
    }

    public void setPublishers(String[] publishers) {
        this.publishers = publishers;
    }

    public String[] getDevelopers() {
        return developers;
    }

    public void setDevelopers(String[] developers) {
        this.developers = developers;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    // toString: Formata a saída
    @Override
    public String toString() {
        return "=> " + id + " ## " +
                name + " ## " +
                releaseDate + " ## " +
                estimatedOwners + " ## " +
                price + " ## " +
                arrayToString(supportedLanguages) + " ## " +
                metacriticScores + " ## " +
                userScore + " ## " +
                achievements + " ## " +
                arrayToString(publishers) + " ## " +
                arrayToString(developers) + " ## " +
                arrayToString(categories) + " ## " +
                arrayToString(genres) + " ## " +
                arrayToString(tags) + " ##";
    }

    // Método auxiliar para imprimir arrays
    public String arrayToString(String[] array) {
        String resultado = "[";
        for (int i = 0; i < array.length; i++) {
            resultado += array[i];
            if (i < array.length - 1) {
                resultado += ", ";
            }
        }
        resultado += "]";
        return resultado;
    }

    // Método para ler o CSV
    public static game lerCSV(String linha) {

        // "atributos" é um vetor que recebe a linha, nele, cada atributo do game é um índice do vetor
        String[] atributos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Regex para considerar vírgulas dentro de aspas

        game g = new game(); // Cria um novo objeto da classe game para cada linha lida

        // Preenchendo os atributos do objeto da classe game, usando os índices do vetor "atributos"
        
        // ID
        g.setID(Integer.parseInt(atributos[0])); // Converte a String do índice 0 para "int" e atribui ao ID do game

        // Name
        g.setName(atributos[1]);

        // Release date
        g.setReleaseDate(verificarData(atributos[2])); // Verifica se o dia e o mês estão vazios, se estiverem adiciona '01' a eles

        // Estimated owners
        g.setEstimatedOwners(removeVirgulas(atributos[3])); // Remove as vírgulas do número e converte para "int"

        // Price
        g.setPrice(verificaPreco(atributos[4])); // Verifica se o preço é "Free to Play" e se for atribui 0.0

        // Supported languages
        g.setSupportedLanguages(separarListaDeStringsLinguagens(atributos[5]));

        // Metacritic scores
        if (atributos[6].equals("")) { // Se o campo estiver vazio, atribui -1
            g.setMetacriticScores(-1);
        } else {
            g.setMetacriticScores(Integer.parseInt(atributos[6])); // Converte para "int" e atribui à Metacritic Score
        }
        
        // User score
        if (atributos[7].equals("tbd") || atributos[7].equals("")) { // Se o campo estiver vazio ou for "tbd", atribui -1.0
            g.setUserScore(-1.0f);
        } else {
            g.setUserScore(Float.parseFloat(atributos[7])); // Converte para float e atribui à User Score
        }
        
        // Achievements
        if (atributos[8].equals("")) { // Se o campo estiver vazio, atribui 0
            g.setAchievements(0);
        } else {
            g.setAchievements(Integer.parseInt(atributos[8])); // Converte para "int" e atribui à Achievements
        }
        
        // Publishers, Developers, Categories, Genres e Tags
        
        if (atributos.length > 9)
            g.setPublishers(separarListaDeStrings(atributos[9]));
        if (atributos.length > 10)
            g.setDevelopers(separarListaDeStrings(atributos[10]));
        if (atributos.length > 11)
            g.setCategories(separarListaDeStrings(atributos[11]));
        if (atributos.length > 12)
            g.setGenres(separarListaDeStrings(atributos[12]));
        if (atributos.length > 13)
            g.setTags(separarListaDeStrings(atributos[13]));

        return g;

    }

    // Caso o dia e/ou o mês estejam vazios, adiciona '01' à eles
    public static String verificarData(String data) {
           
        data = data.replace("\"", "").trim(); // Remove aspas e espaços em branco

        // Como a data vem numa string no formato "Oct 18, 2018" devemos separar o mês, dia e ano
        String[] partes = data.split(" ");
        String dia = "01", mes = "01", ano = "1900"; // Inicializa dia e mês com '01' e ano com '1900' para caso estejam vazios
        if (partes.length == 3) { // Se tiver dia, mês e ano
            mes = converterMesParaNumero(partes[0]); // Mês é a primeira parte, convertendo para número
            dia = partes[1].replace(",", ""); // Dia é a segunda parte, removendo a vírgula
            ano = partes[2]; // Ano é a terceira parte
        } else if (partes.length == 2) { // Se a data tiver apenas mês e ano
            mes = converterMesParaNumero(partes[0]);
            ano = partes[1];
        } else if (partes.length == 1) { // Se a data tiver apenas o ano
            ano = partes[0];
        }

        if (dia.length() == 1)
            dia = "0" + dia;
        if (mes.length() == 1)
            mes = "0" + mes;

        return dia + "/" + mes + "/" + ano;
    }

    // Converte o mês de string para número
    public static String converterMesParaNumero(String mes) {
        switch (mes) {
            case "Jan":
                return "01";
            case "Feb":
                return "02";
            case "Mar":
                return "03";
            case "Apr":
                return "04";
            case "May":
                return "05";
            case "Jun":
                return "06";
            case "Jul":
                return "07";
            case "Aug":
                return "08";
            case "Sep":
                return "09";
            case "Oct":
                return "10";
            case "Nov":
                return "11";
            case "Dec":
                return "12";
            default:
                return "01";
        }
    }

    // Método para remover as vírgulas dos números
    public static int removeVirgulas(String numero) {
        String resultado = "";
        for (int i = 0; i < numero.length(); i++) {
            if (numero.charAt(i) != ',') {
                resultado += numero.charAt(i);
            }
        }
        return Integer.parseInt(resultado); // Converte o resultado para "int" e retorna
    }

    // Verifica se o preço é "Free to Play", se for retorna 0.0, se não apenas converte para float e retorna
    public static float verificaPreco(String preco) {
        if (preco.equals("Free to Play")) {
            return 0.0f;
        } else {
            return Float.parseFloat(preco);
        }
    }

    // Separa uma lista de strings que estão no formato ["str1", "str2", "str3"] em um array de strings
    public static String[] separarListaDeStrings(String campo) {
        if (campo == null || campo.equals(""))
            return new String[0];
        campo = campo.replace("[", "").replace("]", "").replace("\"", "").trim();
        if (campo.equals(""))
            return new String[0];
        return campo.split(",");
    }

    // Separa uma lista de strings que estão no formato ["str1", "str2", "str3"] em um array de strings, porém também tira o ''
    public static String[] separarListaDeStringsLinguagens(String campo) {
        if (campo == null || campo.equals(""))
            return new String[0];
        campo = campo.replace("[", "").replace("]", "").replace("\"", "").replace("'", "").trim();
        if (campo.equals(""))
            return new String[0];
        return campo.split(",");
    }

    public static void main(String[] args) {
        
        String arquivo = "/tmp/games.csv"; 
        ArvoreBinaria abb = new ArvoreBinaria();
        Scanner sc = new Scanner(System.in);
        String entrada;

        // Leitura dos IDs e inserção na ABB
        try {
            while (!(entrada = sc.nextLine()).equals("FIM")) { // Lê até encontrar "FIM"
                
                int idBusca = Integer.parseInt(entrada.trim()); // Id atual a ser buscado
                
                // Busca linear no CSV para encontrar o jogo pelo ID e inseri-lo na ABB
                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    br.readLine(); // Pula cabeçalho
                    
                    while ((linha = br.readLine()) != null) {
                        game g = game.lerCSV(linha); // Lê a linha e cria o objeto game
                        if (g.getID() == idBusca) {  // Se o ID do jogo lido for igual ao ID buscado, insere na ABB
                            abb.inserir(g);
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) { 
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            
        }

 
        // Leitura dos nomes para busca na ABB
        
        long inicioBusca = new Date().getTime();
        String matricula = "885473"; 
        
        try (PrintWriter log = new PrintWriter(new FileWriter(matricula + "_arvoreBinaria.txt"))) {  // Criação do arquivo log com matrícula
            
            while (sc.hasNextLine()) { 
                entrada = sc.nextLine();
                if (entrada.equals("FIM")) break;
                
                String nomeBusca = entrada.trim(); // Nome do jogo a ser buscado
                
                game resultado = abb.pesquisar(nomeBusca); // Retorna o objeto game se encontrado, ou null se não encontrado
                
                String status = (resultado != null) ? "SIM" : "NAO"; // Define o status da busca
                
                String caminhoFormatado = nomeBusca + ": " + abb.caminhoPesquisado.replace("raiz", "=>raiz") + " " + status; // Formata o caminho percorrido
                System.out.println(caminhoFormatado);
                
            }
            
            long fimBusca = new Date().getTime();
            long tempoExecucao = fimBusca - inicioBusca;
            
            // Saída no Arquivo de Log 
            log.println("Matrícula:" + matricula + "\t" + "Tempo de execução em ms:" + tempoExecucao + "\t" + "Número de comparações:" + abb.numComparacoes);
            
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de log: " + e.getMessage());
        }

        sc.close();
    }
}