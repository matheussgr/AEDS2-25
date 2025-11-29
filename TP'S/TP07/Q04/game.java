// Matheus Gouvêa Ramalho - TP07Q01 - Árvore Alvinegra (RBT Invertida) em Java

import java.io.*;
import java.util.Scanner;
import java.util.Date;

// Nó da Árvore Alvinegra
class NoAN {
    public boolean cor; 
    public game elemento; 
    public NoAN esq, dir; 

    public NoAN() {
        this(null);
    }

    public NoAN(game elemento) {
        this(elemento, true, null, null); 
    }

    public NoAN(game elemento, boolean cor) {
        this(elemento, cor, null, null);
    }

    public NoAN(game elemento, boolean cor, NoAN esq, NoAN dir) {
        this.cor = cor;
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

// Classe Árvore Alvinegra
class Alvinegra {
    private NoAN raiz; 
    
    public int numComparacoes = 0;
    public String caminhoPesquisado = ""; 

    public Alvinegra() {
        raiz = null;
    }

    // Método auxiliar: retorna true se o nó é branco
    private boolean isCorBranca(NoAN i) {
        return (i == null) || (i.cor == false);
    }
    
    // Pesquisa 
    
    public game pesquisar(String nameBusca) {
        this.caminhoPesquisado = "raiz";
        this.numComparacoes = 0; 
        return pesquisar(nameBusca.trim(), this.raiz);
    }

    private game pesquisar(String nameBusca, NoAN i) {
        if (i == null) {
            return null;
        }

        this.numComparacoes++; 
        int comparacao = nameBusca.compareTo(i.elemento.getName());

        if (comparacao == 0) {
            return i.elemento;
        } else if (comparacao < 0) {
            this.caminhoPesquisado += " esq";
            return pesquisar(nameBusca, i.esq);
        } else {
            this.caminhoPesquisado += " dir";
            return pesquisar(nameBusca, i.dir);
        }
    }

    // Inserção 

    public void inserir(game novoGame) throws Exception {
        // Se a arvore estiver vazia
        if (raiz == null) {
            raiz = new NoAN(novoGame, false); 
        } 
        // Senao, se a arvore tiver um elemento 
        else if (raiz.esq == null && raiz.dir == null) {
            if (novoGame.getName().compareTo(raiz.elemento.getName()) < 0) {
                raiz.esq = new NoAN(novoGame);
            } else {
                raiz.dir = new NoAN(novoGame);
            }
        } 
        // Senao, se a arvore tiver dois elementos 
        else if (raiz.esq == null) {
             if (novoGame.getName().compareTo(raiz.elemento.getName()) < 0) {
                raiz.esq = new NoAN(novoGame);
             } else if (novoGame.getName().compareTo(raiz.dir.elemento.getName()) < 0) {
                raiz.esq = new NoAN(raiz.elemento, true);
                raiz.elemento = novoGame;
             } else {
                raiz.esq = new NoAN(raiz.elemento, true);
                raiz.elemento = raiz.dir.elemento;
                raiz.dir.elemento = novoGame;
             }
             raiz.esq.cor = raiz.dir.cor = true; // Ambos filhos são pretos
        } 
        // Senao, se a arvore tiver dois elementos 
        else if (raiz.dir == null) {
            if (novoGame.getName().compareTo(raiz.elemento.getName()) > 0) {
                raiz.dir = new NoAN(novoGame);
            } else if (novoGame.getName().compareTo(raiz.esq.elemento.getName()) > 0) {
                raiz.dir = new NoAN(raiz.elemento, true);
                raiz.elemento = novoGame;
            } else {
                raiz.dir = new NoAN(raiz.elemento, true);
                raiz.elemento = raiz.esq.elemento;
                raiz.esq.elemento = novoGame;
            }
            raiz.esq.cor = raiz.dir.cor = true; // Ambos filhos são pretos
        } 
        // Senao, a arvore tem tres ou mais elementos 
        else {
            inserir(novoGame, null, null, null, raiz);
        }
        raiz.cor = false; // Garante que a raiz é sempre BRANCA 
    }

    private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
        // Se o pai tambem e preto (true), reequilibrar a arvore, rotacionando o avo 
        if (pai.cor == true) { 
            // 4 tipos de reequilibrios e acoplamento
            if (pai.elemento.getName().compareTo(avo.elemento.getName()) > 0) { // rotacao a esquerda ou direita-esquerda (pai > avo)
                if (i.elemento.getName().compareTo(pai.elemento.getName()) > 0) {
                    avo = rotacaoEsq(avo);
                } else {
                    avo = rotacaoDirEsq(avo);
                }
            } else { // rotacao a direita ou esquerda-direita (pai < avo)
                if (i.elemento.getName().compareTo(pai.elemento.getName()) < 0) {
                    avo = rotacaoDir(avo);
                } else {
                    avo = rotacaoEsqDir(avo);
                }
            }
            
            // Reacoplar o avô (nova raiz da subárvore) ao bisavô
            if (bisavo == null) {
                raiz = avo;
            } else if (avo.elemento.getName().compareTo(bisavo.elemento.getName()) < 0) {
                bisavo.esq = avo;
            } else {
                bisavo.dir = avo;
            }
            
            // reestabelecer as cores apos a rotacao: Raiz da subárvore (avo) BRANCA, filhos PRETOS
            avo.cor = false;
            if (avo.esq != null) avo.esq.cor = true;
            if (avo.dir != null) avo.dir.cor = true;
        } 
    }

    private void inserir(game novoGame, NoAN bisavo, NoAN avo, NoAN pai, NoAN i) throws Exception {
        if (i == null) {
            if (novoGame.getName().compareTo(pai.elemento.getName()) < 0) {
                i = pai.esq = new NoAN(novoGame, true); // Insere PRETO (true)
            } else {
                i = pai.dir = new NoAN(novoGame, true); // Insere PRETO (true)
            }
            if (pai.cor == true) { // pai é PRETO (true)
                balancear(bisavo, avo, pai, i);
            }
        } else {
            // Achou um 4-nó: é preciso fragmentá-lo e reequilibrar a arvore
            if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
                i.cor = true; // Nó atual (pai) vira PRETO
                i.esq.cor = i.dir.cor = false; // Filhos viram BRANCOS
                
                if (i == raiz) {
                    i.cor = false; // Raiz é sempre BRANCA
                } else if (pai.cor == true) { // Se o pai é PRETO, há duplo preto (conflito)
                    balancear(bisavo, avo, pai, i);
                }
            }
            
            // Desce na arvore
            if (novoGame.getName().compareTo(i.elemento.getName()) < 0) {
                inserir(novoGame, avo, pai, i, i.esq);
            } else if (novoGame.getName().compareTo(i.elemento.getName()) > 0) {
                inserir(novoGame, avo, pai, i, i.dir);
            } else {
                throw new Exception("Erro inserir (elemento repetido)!");
            }
        }
    }

    // Rotações

    private NoAN rotacaoDir(NoAN no) {
        NoAN noEsq = no.esq;
        NoAN noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        return noEsq;
    }

    private NoAN rotacaoEsq(NoAN no) {
        NoAN noDir = no.dir;
        NoAN noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;
        return noDir;
    }

    private NoAN rotacaoDirEsq(NoAN no) {
        no.dir = rotacaoDir(no.dir);
        return rotacaoEsq(no);
    }

    private NoAN rotacaoEsqDir(NoAN no) {
        no.esq = rotacaoEsq(no.esq);
        return rotacaoDir(no);
    }
}



public class game {
    // Atributos
    private int id; private String name; private String releaseDate; private int estimatedOwners; private float price; private String[] supportedLanguages; private int metacriticScores; private float userScore; private int achievements; private String[] publishers; private String[] developers; private String[] categories; private String[] genres; private String[] tags;

    // Construtores
    public game() {}
    public game(int id, String name, String releaseDate, int estimatedOwners, float price, String[] supportedLanguages,
                int metacriticScores, float userScore, int achievements, String[] publishers, String[] developers,
                String[] categories, String[] genres, String[] tags) {
        this.id = id; this.name = name; this.releaseDate = releaseDate; this.estimatedOwners = estimatedOwners; 
        this.price = price; this.supportedLanguages = supportedLanguages; this.metacriticScores = metacriticScores; 
        this.userScore = userScore; this.achievements = achievements; this.publishers = publishers; 
        this.developers = developers; this.categories = categories; this.genres = genres; this.tags = tags;
    }

    // Getters
    public int getID() { return id; }
    public String getName() { return name; }
    public String getReleaseDate() { return releaseDate; }
    public int getEstimatedOwners() { return estimatedOwners; }
    public float getPrice() { return price; }
    public String[] getSupportedLanguages() { return supportedLanguages; }
    public int getMetacriticScores() { return metacriticScores; }
    public float getUserScore() { return userScore; }
    public int getAchievements() { return achievements; }
    public String[] getPublishers() { return publishers; }
    public String[] getDevelopers() { return developers; }
    public String[] getCategories() { return categories; }
    public String[] getGenres() { return genres; }
    public String[] getTags() { return tags; }

    // Setters
    public void setID(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public void setEstimatedOwners(int estimatedOwners) { this.estimatedOwners = estimatedOwners; }
    public void setPrice(float price) { this.price = price; }
    public void setSupportedLanguages(String[] supportedLanguages) { this.supportedLanguages = supportedLanguages; }
    public void setMetacriticScores(int metacriticScores) { this.metacriticScores = metacriticScores; }
    public void setUserScore(float userScore) { this.userScore = userScore; }
    public void setAchievements(int achievements) { this.achievements = achievements; }
    public void setPublishers(String[] publishers) { this.publishers = publishers; }
    public void setDevelopers(String[] developers) { this.developers = developers; }
    public void setCategories(String[] categories) { this.categories = categories; }
    public void setGenres(String[] genres) { this.genres = genres; }
    public void setTags(String[] tags) { this.tags = tags; }


    // toString: Formata a saída
    @Override
    public String toString() {
        return "=> " + id + " ## " + name + " ## " + releaseDate + " ## " + estimatedOwners + " ## " + price + " ## " + arrayToString(supportedLanguages) + " ## " + metacriticScores + " ## " + userScore + " ## " + achievements + " ## " + arrayToString(publishers) + " ## " + arrayToString(developers) + " ## " + arrayToString(categories) + " ## " + arrayToString(genres) + " ## " + arrayToString(tags) + " ##";
    }

    // Métodos Auxiliares
    public String arrayToString(String[] array) {
        String resultado = "[";
        for (int i = 0; i < array.length; i++) {
            resultado += array[i].trim();
            if (i < array.length - 1) {
                resultado += ", ";
            }
        }
        resultado += "]";
        return resultado;
    }
    
    // Métodos estáticos de leitura e conversão (mantidos do código original)
    public static game lerCSV(String linha) {
        String[] atributos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        game g = new game(); 
        try {
            g.setID(Integer.parseInt(atributos[0])); g.setName(atributos[1]); g.setReleaseDate(verificarData(atributos[2]));
            g.setEstimatedOwners(removeVirgulas(atributos[3])); g.setPrice(verificaPreco(atributos[4]));
            g.setSupportedLanguages(separarListaDeStringsLinguagens(atributos[5]));
            g.setMetacriticScores(atributos[6].equals("") ? -1 : Integer.parseInt(atributos[6]));
            g.setUserScore((atributos[7].equals("tbd") || atributos[7].equals("")) ? -1.0f : Float.parseFloat(atributos[7]));
            g.setAchievements(atributos[8].equals("") ? 0 : Integer.parseInt(atributos[8]));
            if (atributos.length > 9) g.setPublishers(separarListaDeStrings(atributos[9]));
            if (atributos.length > 10) g.setDevelopers(separarListaDeStrings(atributos[10]));
            if (atributos.length > 11) g.setCategories(separarListaDeStrings(atributos[11]));
            if (atributos.length > 12) g.setGenres(separarListaDeStrings(atributos[12]));
            if (atributos.length > 13) g.setTags(separarListaDeStrings(atributos[13]));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {}
        return g;
    }
    
    public static String verificarData(String data) {
        data = data.replace("\"", "").trim(); String[] partes = data.split(" ");
        String dia = "01", mes = "01", ano = "1900"; 
        if (partes.length == 3) { mes = converterMesParaNumero(partes[0]); dia = partes[1].replace(",", ""); ano = partes[2];
        } else if (partes.length == 2) { mes = converterMesParaNumero(partes[0]); ano = partes[1];
        } else if (partes.length == 1) { ano = partes[0]; }
        if (dia.length() == 1) dia = "0" + dia; if (mes.length() == 1) mes = "0" + mes;
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
        String resultado = "";
        for (int i = 0; i < numero.length(); i++) {
            if (numero.charAt(i) != ',') resultado += numero.charAt(i);
        }
        return Integer.parseInt(resultado);
    }
    public static float verificaPreco(String preco) {
        return preco.equals("Free to Play") ? 0.0f : Float.parseFloat(preco);
    }
    public static String[] separarListaDeStrings(String campo) {
        if (campo == null || campo.equals("")) return new String[0];
        campo = campo.replace("[", "").replace("]", "").replace("\"", "").trim();
        return campo.equals("") ? new String[0] : campo.split(",");
    }
    public static String[] separarListaDeStringsLinguagens(String campo) {
        if (campo == null || campo.equals("")) return new String[0];
        campo = campo.replace("[", "").replace("]", "").replace("\"", "").replace("'", "").trim();
        return campo.equals("") ? new String[0] : campo.split(",");
    }

    public static void main(String[] args) {
        String arquivo = "/tmp/games.csv"; 
        Alvinegra arn = new Alvinegra(); 
        Scanner sc = new Scanner(System.in);
        String entrada;

        // INSERÇÃO DOS REGISTROS
        try {
            while (!(entrada = sc.nextLine()).equals("FIM")) {
                int idBusca = Integer.parseInt(entrada.trim());
                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    String linha; br.readLine(); 
                    while ((linha = br.readLine()) != null) {
                        game g = game.lerCSV(linha);
                        if (g.getID() == idBusca) {
                            arn.inserir(g); 
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) { 
            System.out.println("Erro ao ler arquivo: " + e.getMessage()); return;
        } catch (NumberFormatException e) {}
        catch (Exception e) { System.out.println("Erro na inserção: " + e.getMessage()); }

        // PESQUISA DOS REGISTROS
        long inicioBusca = new Date().getTime();
        String matricula = "885473"; 
        
        try (PrintWriter log = new PrintWriter(new FileWriter(matricula + "_alvinegra.txt"))) { 
            while (sc.hasNextLine()) {
                entrada = sc.nextLine();
                if (entrada.equals("FIM")) break;
                
                String nomeBusca = entrada.trim();
                game resultado = arn.pesquisar(nomeBusca);
                
                String status = (resultado != null) ? "SIM" : "NAO";
                String caminhoFormatado = nomeBusca + ": " + arn.caminhoPesquisado.replace("raiz", "=>raiz") + " " + status;
                System.out.println(caminhoFormatado);
            }
            
            long fimBusca = new Date().getTime();
            long tempoExecucao = fimBusca - inicioBusca;
            log.println("Matrícula:" + matricula + "\t" + "Tempo de execução em ms:" + tempoExecucao + "\t" + "Número de comparações:" + arn.numComparacoes);
            
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de log: " + e.getMessage());
        }

        sc.close();
    }
}