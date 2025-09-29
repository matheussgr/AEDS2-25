
// Matheus Gouvêa Ramalho - LAB05 
import java.util.*;

public class quicksort {

    // Função para trocar dois elementos em um array
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Função para preencher o array em ordem crescente (ordenado)
    public static void crescente(int array[]) {
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}
	}

    // Função para preencher o array de forma semi-ordenada
    public static void semiOrdenado(int array[]) {
        Random rand = new Random();

        crescente(array);

        // Embaralha 10% dos elementos do array
        for (int i = 0; i < array.length / 10; i++) {
            swap(array, Math.abs(rand.nextInt()) % array.length, Math.abs(rand.nextInt()) % array.length);
        }
    }

    // Função que preenche o array com número aleatórios
    public static void arrayAleatorio(int array[]) {
        Random rand = new Random();

        crescente(array);

        for (int i = 0; i < array.length; i++) {
            swap(array, i, Math.abs(rand.nextInt()) % array.length);
        }
    }

    

    // QuickSort com pivô no primeiro elemento
    public static void QuickSortFirstPivot(int[] array, int left, int right) {
        int i = left, j = right;
        int pivo = array[left];
        while (i <= j) {
            while (array[i] < pivo)
                i++;
            while (array[j] > pivo)
                j--;
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (left < j)
            QuickSortFirstPivot(array, left, j);
        if (i < right)
            QuickSortFirstPivot(array, i, right);
    }

    // QuickSort com pivô no último elemento
    public static void QuickSortLastPivot(int[] array, int left, int right) {
        int i = left, j = right;
        int pivo = array[right];
        while (i <= j) {
            while (array[i] < pivo)
                i++;
            while (array[j] > pivo)
                j--;
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (left < j)
            QuickSortLastPivot(array, left, j);
        if (i < right)
            QuickSortLastPivot(array, i, right);
    }

    // QuickSort com pivô aleatório
    public static void QuickSortRandomPivot(int[] array, int left, int right){
        int i = left, j = right;
        int indexAleatorio = (int)(Math.random() * (right + 1 - left) + left);
        int pivo = array[indexAleatorio];
        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (left < j)  QuickSortRandomPivot(array, left, j);
        if (i < right)  QuickSortRandomPivot(array, i, right);
    }

    // QuickSort com pivô na mediana de três
    public static void QuickSortMedianOfThree(int[] array, int left, int right) {
        int i = left, j = right, pivo;
        int meio = (left + right)/2;
        int a = array[left], b = array[meio], c = array[right];
        
        if((a > b && a < c) || (a > c && a < b)){
            pivo = array[left];
        }
        else if((b > a && b < c) || (b > c && b < a)){
            pivo = array[meio];
        }
        else{
            pivo = array[right];
        }
   
        while (i <= j) {
            while (array[i] < pivo)i++;
            while (array[j] > pivo)j--;
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (left < j)
            QuickSortMedianOfThree(array, left, j);
        if (i < right)
            QuickSortMedianOfThree(array, i, right);
    }

    public static void main(String[] args) {


        // Criando arrays de diferentes tamanhos
        int array1[] = new int[1000]; 
        int array2[] = new int[10000];
        int array3[] = new int[100000];

        // Métodos com o array de 1000 elementos:

        // 1) Ordenado
        //a) Pivô no primeiro elemento
            crescente(array1);
            long startTime = System.nanoTime();
            QuickSortFirstPivot(array1, 0, array1.length - 1);
            long endTime = System.nanoTime();
            long duracao = endTime - startTime;
            System.out.println("Array de 1000 elementos ordenado - Pivô no primeiro elemento: " + duracao + " nanosegundos");
        //b) Pivô no último elemento
            crescente(array1);
            startTime = System.nanoTime();
            QuickSortLastPivot(array1, 0, array1.length - 1);
            endTime = System.nanoTime();
            duracao = endTime - startTime;
            System.out.println("Array de 1000 elementos ordenado - Pivô no último elemento: " + duracao + " nanosegundos");
        //c) Pivô aleatório
            crescente(array1);
            startTime = System.nanoTime();
            QuickSortRandomPivot(array1, 0, array1.length - 1);
            endTime = System.nanoTime();
            duracao = endTime - startTime;
            System.out.println("Array de 1000 elementos ordenado - Pivô aleatório: " + duracao + " nanosegundos");
        //d) Mediana de três
            crescente(array1);
            startTime = System.nanoTime();
            QuickSortMedianOfThree(array1, 0, array1.length - 1);
            endTime = System.nanoTime();
            duracao = endTime - startTime;
            System.out.println("Array de 1000 elementos ordenado - Mediana de três: " + duracao + " nanosegundos");



        // 2) Semi-ordenado
        //a) Pivô no primeiro elemento
            semiOrdenado(array1);
            startTime = System.nanoTime();
            QuickSortFirstPivot(array1, 0, array1.length - 1);
            endTime = System.nanoTime();
            duracao = endTime - startTime;
            System.out.println("Array de 1000 elementos semi-ordenado - Pivô no primeiro elemento: " + duracao + " nanosegundos");
        //b) Pivô no último elemento
            semiOrdenado(array1);
            startTime = System.nanoTime();
            QuickSortLastPivot(array1, 0, array1.length - 1);
            endTime = System.nanoTime();
            duracao = endTime - startTime;
            System.out.println("Array de 1000 elementos semi-ordenado - Pivô no último elemento: " + duracao + " nanosegundos");
        //c) Pivô aleatório
            semiOrdenado(array1);  
            startTime = System.nanoTime();
            QuickSortRandomPivot(array1, 0, array1.length - 1);
            endTime = System.nanoTime();
            duracao = endTime - startTime;
            System.out.println("Array de 1000 elementos semi-ordenado - Pivô aleatório: " + duracao + " nanosegundos");
        //d) Mediana de três
            semiOrdenado(array1);
            startTime = System.nanoTime();
            QuickSortMedianOfThree(array1, 0, array1.length - 1);
            endTime = System.nanoTime();
            duracao = endTime - startTime;
            System.out.println("Array de 1000 elementos semi-ordenado - Mediana de três: " + duracao + " nanosegundos");

            
        // 3) Aleatório
        //a) Pivô no primeiro elemento
            arrayAleatorio(array1);
            startTime = System.nanoTime();
            QuickSortFirstPivot(array1, 0, array1.length - 1);
            endTime = System.nanoTime();
            duracao = endTime - startTime;
            System.out.println("Array de 1000 elementos aleatório - Pivô no primeiro elemento: " + duracao + " nanosegundos");
        //b) Pivô no último elemento
            arrayAleatorio(array1);
            startTime = System.nanoTime();
            QuickSortLastPivot(array1, 0, array1.length - 1);
            endTime = System.nanoTime();
            duracao = endTime - startTime;
            System.out.println("Array de 1000 elementos aleatório - Pivô no último elemento: " + duracao + " nanosegundos");
        //c) Pivô aleatório
            arrayAleatorio(array1);
            startTime = System.nanoTime();
            QuickSortRandomPivot(array1, 0, array1.length - 1);
            endTime = System.nanoTime();
            duracao = endTime - startTime;
            System.out.println("Array de 1000 elementos aleatório - Pivô aleatório: " + duracao + " nanosegundos");
        //d) Mediana de três
            arrayAleatorio(array1);
            startTime = System.nanoTime();
            QuickSortMedianOfThree(array1, 0, array1.length - 1);
            endTime = System.nanoTime();
            duracao = endTime - startTime;
            System.out.println("Array de 1000 elementos aleatório - Mediana de três: " + duracao + " nanosegundos");
        System.out.println("--------------------------------------------------");

        



    }
}