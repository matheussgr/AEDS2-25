// Matheus Gouvêa Ramalho - TP04

import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Locale;
import java.util.ArrayList;

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

    // --- Atributos Estáticos para o Log ---
    private static long comparacoes = 0;
    private static long movimentacoes = 0;
    private static long tempoExecucao = 0;
    private static final String MATRICULA = "885473"; 

    // Construtores
    public game() {}

    public game(int id, String name, String releaseDate, int estimatedOwners, float price, String[] supportedLanguages,
                int metacriticScores, float userScore, int achievements, String[] publishers, String[] developers,
                String[] categories, String[] genres, String[] tags) {
        this.id = id; this.name = name; this.releaseDate = releaseDate; this.estimatedOwners = estimatedOwners; this.price = price; this.supportedLanguages = supportedLanguages;
        this.metacriticScores = metacriticScores; this.userScore = userScore; this.achievements = achievements; this.publishers = publishers; this.developers = developers;
        this.categories = categories; this.genres = genres; this.tags = tags;
    }

    // Getters e Setters (Principais)
    public int getID() { return id; }
    public void setID(int id) { this.id = id; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public int getEstimatedOwners() { return estimatedOwners; }
    public void setEstimatedOwners(int estimatedOwners) { this.estimatedOwners = estimatedOwners; }
    public String[] getSupportedLanguages() { return supportedLanguages; }
    public void setSupportedLanguages(String[] supportedLanguages) { this.supportedLanguages = supportedLanguages; }
    public int getMetacriticScores() { return metacriticScores; }
    public void setMetacriticScores(int metacriticScores) { this.metacriticScores = metacriticScores; }
    public float getUserScore() { return userScore; }
    public void setUserScore(float userScore) { this.userScore = userScore; }
    public int getAchievements() { return achievements; }
    public void setAchievements(int achievements) { this.achievements = achievements; }
    public String[] getPublishers() { return publishers; }
    public void setPublishers(String[] publishers) { this.publishers = publishers; }
    public String[] getDevelopers() { return developers; }
    public void setDevelopers(String[] developers) { this.developers = developers; }
    public String[] getCategories() { return categories; }
    public void setCategories(String[] categories) { this.categories = categories; }
    public String[] getGenres() { return genres; }
    public void setGenres(String[] genres) { this.genres = genres; }
    public String[] getTags() { return tags; }
    public void setTags(String[] tags) { this.tags = tags; }


    // toString: Formata a saída
    @Override
    public String toString() {
        return "=> " + id + " ## " +
                name + " ## " +
                releaseDate + " ## " +
                estimatedOwners + " ## " +
                // Garante formato X.XX para preço
                String.format(Locale.US, "%.2f", price).replace(",", ".") + " ## " + 
                arrayToString(supportedLanguages) + " ## " +
                metacriticScores + " ## " +
                // Garante formato X.X para userScore
                String.format(Locale.US, "%.1f", userScore).replace(",", ".") + " ## " + 
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
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                resultado += array[i].trim();
                if (i < array.length - 1) {
                    resultado += ", ";
                }
            }
        }
        resultado += "]";
        return resultado;
    }

    // Método para ler o CSV
    public static game lerCSV(String linha) {

        String[] atributos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        game g = new game(); 
        
        // ID
        g.setID(Integer.parseInt(atributos[0].trim())); 

        // Name
        g.setName(atributos[1]);

        // Release date
        g.setReleaseDate(verificarData(atributos[2]));

        // Estimated owners
        g.setEstimatedOwners(removeVirgulas(atributos[3]));

        // Price
        g.setPrice(verificaPreco(atributos[4]));

        // Supported languages
        g.setSupportedLanguages(separarListaDeStringsLinguagens(atributos[5]));

        // Metacritic scores
        if (atributos[6].equals("")) { g.setMetacriticScores(-1); } else { g.setMetacriticScores(Integer.parseInt(atributos[6])); }
        if (atributos[7].equals("tbd") || atributos[7].equals("")) { g.setUserScore(-1.0f); } else { g.setUserScore(Float.parseFloat(atributos[7])); }
        if (atributos[8].equals("")) { g.setAchievements(0); } else { g.setAchievements(Integer.parseInt(atributos[8])); }
        
        // Publishers, Developers, Categories, Genres e Tags
        if (atributos.length > 9) g.setPublishers(separarListaDeStrings(atributos[9]));
        if (atributos.length > 10) g.setDevelopers(separarListaDeStrings(atributos[10]));
        if (atributos.length > 11) g.setCategories(separarListaDeStrings(atributos[11]));
        if (atributos.length > 12) g.setGenres(separarListaDeStrings(atributos[12]));
        if (atributos.length > 13) g.setTags(separarListaDeStrings(atributos[13]));

        return g;
    }

    // Métodos Auxiliares de Tratamento de Dados (mantidos)
    public static String verificarData(String data) {
        data = data.replace("\"", "").trim();
        String[] partes = data.split(" ");
        String dia = "01", mes = "01", ano = "1900";
        if (partes.length >= 3) { mes = converterMesParaNumero(partes[0]); dia = partes[1].replace(",", ""); ano = partes[2]; } 
        else if (partes.length == 2) { mes = converterMesParaNumero(partes[0]); ano = partes[1]; } 
        else if (partes.length == 1) { ano = partes[0]; }
        if (dia.length() == 1) dia = "0" + dia;
        if (mes.length() == 1) mes = "0" + mes;
        return dia + "/" + mes + "/" + ano;
    }
    public static String converterMesParaNumero(String mes) {
        switch (mes) {
            case "Jan": return "01"; case "Feb": return "02"; case "Mar": return "03"; case "Apr": return "04";
            case "May": return "05"; case "Jun": return "06"; case "Jul": return "07"; case "Aug": return "08";
            case "Sep": return "09"; case "Oct": return "10"; case "Nov": return "11"; case "Dec": return "12";
            default: return "01";
        }
    }
    public static int removeVirgulas(String numero) {
        String resultado = numero.replaceAll(",", "");
        return Integer.parseInt(resultado);
    }
    public static float verificaPreco(String preco) {
        if (preco.contains("Free to Play")) { return 0.00f; } else { return Float.parseFloat(preco); }
    }
    public static String[] separarListaDeStrings(String campo) {
        if (campo == null || campo.equals("")) return new String[0];
        campo = campo.replace("[", "").replace("]", "").replace("\"", "").trim();
        if (campo.equals("")) return new String[0];
        return campo.split(",");
    }
    public static String[] separarListaDeStringsLinguagens(String campo) {
        if (campo == null || campo.equals("")) return new String[0];
        campo = campo.replace("[", "").replace("]", "").replace("\"", "").replace("'", "").trim();
        if (campo.equals("")) return new String[0];
        return campo.split(",");
    }

    // --- Lógica de Comparação para Mergesort ---

    public static boolean isMenorOuIgual(game gameA, game gameB) {
        if (gameA == null || gameB == null) return gameA != null; 

        // 1. Comparação Principal: Price
        comparacoes++; 
        if (gameA.getPrice() != gameB.getPrice()) {
            return gameA.getPrice() < gameB.getPrice(); 
        }
        
        // 2. Comparação Desempate: ID (AppID)
        comparacoes++; 
        return gameA.getID() <= gameB.getID(); 
    }

    // --- Algoritmo Mergesort ---

    public static void mergesort(game[] array, int n) {
        mergesort(array, 0, n - 1);
    }

    private static void mergesort(game[] array, int esq, int dir) {
        if (esq < dir){
            int meio = (esq + dir) / 2;
            mergesort(array, esq, meio);
            mergesort(array, meio + 1, dir);
            intercalar(array, esq, meio, dir); 
        }
    }

    public static void intercalar(game[] array, int esq, int meio, int dir){
        int n1, n2, i, j, k;

        n1 = meio-esq+1;
        n2 = dir - meio;

        game[] a1 = new game[n1];
        game[] a2 = new game[n2];

        for(i = 0; i < n1; i++){
            a1[i] = array[esq+i];
            movimentacoes++; 
        }

        for(j = 0; j < n2; j++){
            a2[j] = array[meio+j+1];
            movimentacoes++; 
        }

        i = j = 0; 
        k = esq;

        while (i < n1 && j < n2) {
            if (isMenorOuIgual(a1[i], a2[j])) {
                array[k] = a1[i++];
            } else {
                array[k] = a2[j++];
            }
            movimentacoes++; 
            k++;
        }
        
        while (i < n1) {
            array[k++] = a1[i++];
            movimentacoes++; 
        }
        
        while (j < n2) {
            array[k++] = a2[j++];
            movimentacoes++; 
        }
    }

    // --- Métodos de Log ---

    public static void criarLog() {
        String nomeArquivo = MATRICULA + "_mergesort.txt";
        DecimalFormat df = new DecimalFormat("0.000");
        
        String logData = MATRICULA + "\t" + 
                         comparacoes + "\t" + 
                         movimentacoes + "\t" + 
                         df.format((double)tempoExecucao / 1000000.0);

        try (FileWriter fw = new FileWriter(nomeArquivo)) {
            fw.write(logData);
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo de log: " + e.getMessage());
        }
    }

    // --- Main (COM A ORDEM DE SAÍDA CORRIGIDA) ---

    public static void main(String[] args) {
        String arquivo = "C:\\Users\\Matheus\\Pictures\\PUC\\SEGUNDO SEMESTRE\\AEDS2\\AEDS2-25\\TP'S\\TP05\\Q02\\games.csv"; 
        game[] jogos = new game[5000]; 
        int qtdJogos = 0; 

        // Lendo o arquivo CSV (Tratamento de linhas vazias/sujas)
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            br.readLine(); // Descarta o cabeçalho
            String linha; 
            while ((linha = br.readLine()) != null && qtdJogos < jogos.length) { 
                linha = linha.trim();
                if (!linha.isEmpty()) {
                    jogos[qtdJogos++] = game.lerCSV(linha); 
                }
            }
        } catch (IOException e) { 
            System.out.println("Erro ao abrir arquivo ou ler CSV: " + e.getMessage());
        }

        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> idsBuscadosList = new ArrayList<>(); 

        // Lê os IDs para busca
        while (sc.hasNextLine()) {
            String entrada = sc.nextLine();
            if (entrada.equals("FIM"))
                break;
            try {
                idsBuscadosList.add(Integer.parseInt(entrada.trim()));
            } catch (NumberFormatException e) { }
        }
        sc.close();
        
        // --- PREPARAÇÃO ROBUSTA DO ARRAY DE ORDENAÇÃO ---

        ArrayList<game> jogosEncontradosList = new ArrayList<>();
        
        for (Integer idBuscado : idsBuscadosList) {
            game encontrado = null;
            
            for (int j = 0; j < qtdJogos; j++) {
                if (jogos[j] != null && jogos[j].getID() == idBuscado) {
                    encontrado = jogos[j];
                    break;
                }
            }
            
            if (encontrado != null) {
                jogosEncontradosList.add(encontrado);
            }
        }

        game[] arrayFinal = jogosEncontradosList.toArray(new game[0]);
        int qtdJogosEncontrados = arrayFinal.length;

        // --- ORDENAÇÃO MERGESORT E MEDIÇÃO DE TEMPO ---
        
        long inicio = System.nanoTime();
        if (qtdJogosEncontrados > 0) {
            mergesort(arrayFinal, qtdJogosEncontrados);
        }
        long fim = System.nanoTime();
        tempoExecucao = fim - inicio;
        
        // --- SAÍDA E GERAÇÃO DO LOG (ORDEM CORRIGIDA PARA O SEU PADRÃO) ---
        
        // 2. Imprime os 5 preços mais caros (final do array)
        System.out.println("| 5 preços mais caros |"); 
        int inicioExpensive = Math.max(0, qtdJogosEncontrados - 5);
        for (int i = inicioExpensive; i < qtdJogosEncontrados; i++) {
            System.out.println(arrayFinal[i]);
        }

        // 3. Imprime os 5 preços mais baratos (início do array)
        System.out.println("| 5 preços mais baratos |"); 
        int limiteCheapest = Math.min(5, qtdJogosEncontrados);
        for (int i = 0; i < limiteCheapest; i++) {
            System.out.println(arrayFinal[i]);
        }
        
        // Gera o arquivo de log
        criarLog();
    }
}