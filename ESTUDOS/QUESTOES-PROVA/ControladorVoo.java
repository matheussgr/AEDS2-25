import java.util.Scanner;

// Classe célula
class Celula {
    public int elemento;
    public Celula prox;

    public Celula() {
        this(0);
    }

    public Celula(int x) {
        elemento = x;
        prox = null;
    }
}

// Classe fila dinâmica (modelo do Prof. Max)
class Fila {
    private Celula primeiro, ultimo;

    public Fila() {
        primeiro = new Celula(); // nó cabeça
        ultimo = primeiro;
    }

    // Insere no fim
    public void inserir(int x) {
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    // Remove do início
    public int remover() throws Exception {
        if (primeiro == ultimo)
            throw new Exception("Erro ao remover! (fila vazia)");

        Celula tmp = primeiro.prox;
        primeiro.prox = tmp.prox;

        if (tmp == ultimo)
            ultimo = primeiro;

        return tmp.elemento;
    }

    // Verifica se está vazia
    public boolean vazia() {
        return (primeiro == ultimo);
    }
}

// Programa principal
public class ControladorVoo {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        // 4 filas, uma pra cada direção
        Fila oeste = new Fila(); // 1
        Fila sul   = new Fila(); // 2
        Fila norte = new Fila(); // 3
        Fila leste = new Fila(); // 4

        int direcao = 0;

        // Lê os comandos
        while (true) {
            String entrada = sc.next();

            if (entrada.equals("0")) break; // fim da entrada

            // Se for número, muda direção atual
            if (ehNumero(entrada)) {
                direcao = Integer.parseInt(entrada);
            }
            // Se for avião (A<num>), adiciona na fila certa
            else if (entrada.charAt(0) == 'A') {
                int num = Integer.parseInt(entrada.substring(1));
                if (direcao == -1) oeste.inserir(num);
                else if (direcao == -2) sul.inserir(num);
                else if (direcao == -3) norte.inserir(num);
                else if (direcao == -4) leste.inserir(num);
            }
        }
        sc.close();

        // Intercala as filas na ordem O → N → S → L
        boolean primeira = true;
        while (!oeste.vazia() || !norte.vazia() || !sul.vazia() || !leste.vazia()) {

            if (!oeste.vazia()) {
                if (!primeira) System.out.print(" ");
                System.out.print("A" + oeste.remover());
                primeira = false;
            }

            if (!norte.vazia()) {
                if (!primeira) System.out.print(" ");
                System.out.print("A" + norte.remover());
                primeira = false;
            }

            if (!sul.vazia()) {
                if (!primeira) System.out.print(" ");
                System.out.print("A" + sul.remover());
                primeira = false;
            }

            if (!leste.vazia()) {
                if (!primeira) System.out.print(" ");
                System.out.print("A" + leste.remover());
                primeira = false;
            }
        }

        System.out.println();
    }

    // Verifica se a string é um número
    public static boolean ehNumero(String s) {
        for (int i = 0; i < s.length(); i++)
            if (!Character.isDigit(s.charAt(i)))
                return false;
        return true;
    }
}
