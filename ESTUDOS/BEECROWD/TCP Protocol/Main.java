import java.util.*;

public class Main {

    public static void ordenar(int[] array, int qtd){
        for(int i = 0; i < (qtd - 1); i++){
            int menor = i;
            for(int j = (i + 1); j < qtd; j++){
                if (array[menor] > array[j]){
                    menor = j;
                }
            }
            int temp = array[i];
            array[i] = array[menor];
            array[menor] = temp;

        }
    }
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        int[] pacote = new int[100];
        int qtd = 0;


        while(sc.hasNextLine()){

            String linha = sc.nextLine();

            if(linha.charAt(0) == '1'){
                continue;
            } else if(linha.charAt(0) == '0'){
                ordenar(pacote, qtd);
                for(int i = 0; i < qtd; i++){
                    if(pacote[i] < 10){
                        System.out.println("Package" + " " + "00" + pacote[i] + " ");
                    } else if (pacote[i] < 100){
                        System.out.println("Package" + " " + "0" +pacote[i] + " ");
                    } else{
                        System.out.println("Package" + " " + pacote[i] + " ");
                    }
                }
                System.out.println(" ");
                qtd = 0;

            } else {
                String[] partes = linha.split(" ");
                String numStr = partes[1];
                int num = Integer.parseInt(numStr);
                pacote[qtd++] = num;
            }
        }

        sc.close();
    }

}
