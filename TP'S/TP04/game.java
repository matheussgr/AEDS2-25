// Matheus Gouvêa Ramalho - TP04
import java.util.Scanner;

class game{
    
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
    public game(){}
    public game(int id, String name, String releaseDate, int estimatedOwners, float price, String[] supportedLanguages, int metacriticScores, float userScore, int achievements, String[] publishers, String[] developers, String[] categories, String[] genres, String[] tags){
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
    public int getID(){return id;}
    public void setID(int id){this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getReleaseDate(){return releaseDate;}
    public void setReleaseDate(String releaseDate){this.releaseDate = releaseDate;}

    public int getEstimatedOwners(){return estimatedOwners;}
    public void setEstimatedOwners(int estimatedOwners){this.estimatedOwners = estimatedOwners;}

    public float getPrice(){return price;}
    public void setPrice(float price){this.price = price;}

    public String[] getSupportedLanguages(){return supportedLanguages;}
    public void setSupportedLanguages(String[] supportedLanguages){this.supportedLanguages = supportedLanguages;}

    public int getMetacriticScores(){return metacriticScores;}
    public void setMetacriticScores(int metacriticScores){this.metacriticScores = metacriticScores;}

    public float getUserScore(){return userScore;}
    public void setUserScore(float userScore){this.userScore = userScore;}

    public int getAchievements(){return achievements;}
    public void setAchievements(int achievements){this.achievements = achievements;}

    public String[] getPublishers(){return publishers;}
    public void setPublishers(String[] publishers){this.publishers = publishers;}

    public String[] getDevelopers(){return developers;}
    public void setDevelopers(String[] developers){this.developers = developers;}

    public String[] getCategories(){return categories;}
    public void setCategories(String[] categories){this.categories = categories;}

    public String[] getGenres(){return genres;}
    public void setGenres(String[] genres){this.genres = genres;}

    public String[] getTags(){return tags;}
    public void setTags(String[] tags){this.tags = tags;}

    // toString: Formatação de saída
    public String toString(game g){
        String resultado;
        resultado = g.getID() + " " + 
                    g.getName() + " " + 
                    g.getReleaseDate() + " " + 
                    g.getEstimatedOwners() + " " + 
                    g.getPrice() + " " + 
                    arrayToString(g.getSupportedLanguages()) + " " + 
                    g.getMetacriticScores() + " " +
                    g.getUserScore() + " " +
                    g.getAchievements() + " " +
                    arrayToString(g.getPublishers()) + " " +
                    arrayToString(g.getDevelopers()) + " " +
                    arrayToString(g.getCategories()) + " " +
                    arrayToString(g.getGenres()) + " " +
                    arrayToString(g.getTags());
        return resultado;
    }

    // Método auxiliar para imprimir arrays
    public String arrayToString(String[] array){
        String resultado = "[";
        for(int i = 0; i < array.length; i++){
            resultado += array[i];
            if(i < array.length - 1){
                resultado += ", ";
            }
        }
        resultado += "]";
        return resultado;
    }

    // Método para ler o CSV
    public static game lerCSV(String linha){

        // "atributos" é um vetor que recebe a linha, nele cada atributo é separado por vírgula, ou seja cada atributo é um índice do vetor
        String[] atributos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); // Regex para considerar vírgulas dentro de aspas

        game g = new game(); // Cria um novo objeto game para cada linha lida

        // Preenchendo os atributos do objeto game, usando os índices do vetor "atributos"
        // ID
        g.setID(Integer.parseInt(atributos[0])); // Converte o String do índice 0 para int e atribui ao ID
        
        // Name
        g.setName(atributos[1]);

        // Release date
        g.setReleaseDate(verificarData(atributos[2])); // Verifica se o dia e o mês estão vazios, se estiverem adiciona '01' a eles

        // Estimated owners
        g.setEstimatedOwners(removeVirgulas(atributos[3])); // Remove as vírgulas do número e converte para int

        // Price

        // Supported languages
        // Metacritic scores
        // User score
        // Achievements
        // Publishers
        // Developers
        // Categories
        // Genres
        // Tags

        
        game g = new game(); // Cria um novo objeto game

        return g;

    }

    // Caso o dia e/ou o mês estejam vazios, adiciona '01' à eles, sem replace e nem substring
    public static String verificarData(String data){
        // Como a data vem numa string no formato "Oct 18, 2018" devemos separar o mês, dia e ano
        String[] partes = data.split(" ");
        
        String mes = partes[0];
        String dia = partes[1].replace(",", ""); // Remove a vírgula do dia
        String ano = partes[2];

        // Verifica se o dia está vazio
        if(dia.equals("")){
            dia = "01";
        }
        // Verifica se o mês está vazio
        if(mes.equals("")){
            mes = "Jan"; // Se o mês estiver vazio, atribui "Jan" (Janeiro) a ele
        }

        // Retorna a data no formato dd/mm/yyyy
        return dia + "/" + mes + "/" + ano;

    }

    // Método para remover as vírgulas dos números, sem usar replace e nem substring
    public static int removeVirgulas(String numero){
        String resultado = "";
        for(int i = 0; i < numero.length(); i++){
            if(numero.charAt(i) != ','){
                resultado += numero.charAt(i);
            }
        }
        return Integer.parseInt(resultado); // Converte o resultado para int e retorna
    }









}