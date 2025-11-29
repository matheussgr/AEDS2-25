// Matheus Gouvêa Ramalho - TP07Q02 - Árvore Binária de Árvores em Java

import java.io.*;
import java.util.Scanner;
import java.util.Date;


// Nó da Árvore de Estimated Owners
class NoOw {
    public NoOw esq, dir;
    public int num;
    public ArvoreNome jogos;

    public NoOw(int ow) {
        this.num = ow;
        this.esq = this.dir = null;
        this.jogos = new ArvoreNome();
    }
}

// Estrutura da Árvore de Estimated Owners
class ArvoreOw {

    public NoOw raiz;
    private int comparacoes = 0;

    public ArvoreOw() {
        this.raiz = null;
    }

    // Monta a árvore com a ordem pre-definida
    public void construirInicial() {
        int[] ordem = { 7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14 };
        for (int z = 0; z < ordem.length; z++) {
            raiz = inserirOw(raiz, ordem[z]);
        }
    }

    // Insere um número na árvore de Estimated Owners
    private NoOw inserirOw(NoOw i, int ow) {
        if (i == null) {
            return new NoOw(ow);
        }

        if (ow < i.num) {
            i.esq = inserirOw(i.esq, ow);
        } else if (ow > i.num) {
            i.dir = inserirOw(i.dir, ow);
        }

        return i;
    }

    // Procura o nó cujo número de owners é igual à chave
    public NoOw buscarOw(int chave) {
        return buscarOwRec(raiz, chave);
    }

    private NoOw buscarOwRec(NoOw i, int chave) {
        if (i == null) {
            return null;
        }

        if (chave == i.num) {
            return i;
        } else if (chave < i.num) {
            return buscarOwRec(i.esq, chave);
        } else {
            return buscarOwRec(i.dir, chave);
        }
    }

    // Insere um Game na árvore de árvores:
    public void inserirGame(game g) {
        int chave = g.getEstimatedOwners() % 15; // estimatedOwners mod 15
        NoOw i = buscarOw(chave);
        if (i != null) {
            i.jogos.inserir(g); // insere na ArvoreNome daquele NoOw
        }
    }

    // Pesquisa um nome percorrendo a arvore de owners e, em cada nó, a arvore de nomes
    public boolean pesquisar(String nm) {
        System.out.print("raiz");
        boolean resp = pesquisarNomePorOwners(raiz, nm);
        System.out.println(resp ? " SIM" : " NAO");
        return resp;
    }

    private boolean pesquisarNomePorOwners(NoOw i, String nm) {
        if (i == null) {
            return false;
        }

        comparacoes++;

        if (i.jogos.pesquisarRec(i.jogos.raiz, nm)) {
            return true;
        }

        System.out.print("  ESQ");
        if (pesquisarNomePorOwners(i.esq, nm)) {
            return true;
        }

        System.out.print("  DIR");
        return pesquisarNomePorOwners(i.dir, nm);
    }

    // Funções para obter o total de comparações
    public int getComparacoes() {
        return comparacoes + somarComparacoesInternas(raiz);
    }

    private int somarComparacoesInternas(NoOw i) {
        if (i == null) return 0;
        return i.jogos.getComparacoes() + somarComparacoesInternas(i.esq) + somarComparacoesInternas(i.dir);
    }
}

// Nó da Árvore de Nomes
class NoNome {
    public NoNome esq, dir;
    public game gm;

    public NoNome(game g) {
        this.esq = this.dir = null;
        this.gm = g;
    }
}

// Árvore de Nomes
class ArvoreNome {
    public NoNome raiz;
    private int comparacoes = 0;

    public ArvoreNome() {
        this.raiz = null;
    }

    public int getComparacoes() {
        return comparacoes;
    }

    // Insere um game na árvore de nomes
    public void inserir(game g) {
        this.raiz = inserirRec(raiz, g);
    }

    public NoNome inserirRec(NoNome i, game g) {
        if (i == null) {
            return new NoNome(g);
        }

        String nmInserir = g.getName();
        int cmp = nmInserir.compareTo(i.gm.getName());

        if (cmp > 0) {
            i.dir = inserirRec(i.dir, g);
        } else if (cmp < 0) {
            i.esq = inserirRec(i.esq, g);
        } else {

        }
        return i;
    }

    // Pesquisa um nome na árvore
    public boolean pesquisar(String nm) {
        boolean resp = pesquisarRec(this.raiz, nm);
        System.out.println(resp ? " SIM" : " NAO");
        return resp;
    }

    public boolean pesquisarRec(NoNome i, String nm) {
        if (i == null) {
            return false;
        }

        comparacoes++;
        int cmp = nm.compareTo(i.gm.getName());

        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            System.out.print(" esq");
            return pesquisarRec(i.esq, nm);
        } else {
            System.out.print(" dir");
            return pesquisarRec(i.dir, nm);
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

     public static void main(String[] args) throws Exception {
        String caminhoArquivo = "/tmp/games.csv";
        int totalJogos = 0;

        // Conta quantas linhas tem no arquivo
        try (BufferedReader leitor = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), "UTF-8"))) {
            leitor.readLine();
            while (leitor.readLine() != null) {
                totalJogos++;
            }
        } catch (IOException e) {
            return;
        }

        game[] games = new game[totalJogos];
        int i = 0;

        // Lê o arquivo e guarda os jogos no vetor
        try (BufferedReader leitorArq = new BufferedReader(
                new InputStreamReader(new FileInputStream(caminhoArquivo), "UTF-8"))) {
            leitorArq.readLine();
            String linha;
            while ((linha = leitorArq.readLine()) != null) {
                if (i < totalJogos) {
                    games[i] = lerCSV(linha);
                    i++;
                }
            }
        } catch (IOException e) {
        }

        // Faz a busca dos jogos pelo id e salva eles dentro da Árvore
        ArvoreOw arvoreGame = new ArvoreOw();
        arvoreGame.construirInicial();
        Scanner entrada = new Scanner(System.in);
        while (entrada.hasNextLine()) {
            String linhaEntrada = entrada.nextLine();

            
            if (linhaEntrada.equalsIgnoreCase("FIM")) {
                break;
            }

            // Inserção dos Games
            int idBusca = Integer.parseInt(linhaEntrada);
            for (int k = 0; k < games.length; k++) {
                if (games[k] != null && games[k].getID() == idBusca) {
                    arvoreGame.inserirGame(games[k]);
                    break;
                }
            }

        }

        // Pesquisa dos nomes na árvore de árvores
        long inicio = System.currentTimeMillis();
        while (entrada.hasNextLine()) {
            String nmBusca;
            nmBusca = entrada.nextLine();

            
            if (nmBusca.equalsIgnoreCase("FIM")) {
                break;
            }

            System.out.print("=> " + nmBusca + " => ");
            arvoreGame.pesquisar(nmBusca);
        }
        long fim = System.currentTimeMillis();
        long tempo = fim - inicio;

        entrada.close();

        // Criação do arquivo log
        try {
            java.io.FileWriter log = new java.io.FileWriter("885473_arvoreArvore.txt");
            log.write("885473" + "\t" + tempo + "\t" + arvoreGame.getComparacoes());
            log.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
