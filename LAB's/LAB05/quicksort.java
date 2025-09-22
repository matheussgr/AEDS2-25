
// Matheus Gouvêa Ramalho - LAB05 
import java.util.*;

public class quicksort {

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void crescente(int array[]) {
		for (int i = 0; i < array.length; i++) {
			array[i] = i;
		}
	}

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

    public static void QuickSortMedianOfThree(int[] array, int left, int right) {
        int i = left, j = right;
        int pivo;
        int meio = (left + right)/2;
        int a = array[left], b = array[meio], c = array[right];
        
        if((a > b && a < c) || (a > c)){
            pivo = array[left]
        }
   
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
            QuickSortMedianOfThree(array, left, j);
        if (i < right)
            QuickSortMedianOfThree(array, i, right);
    }

    public static void main(String[] args) {

        Random rand = new Random();

        int array1[] = new int[1000];
        int array2[] = new int[10000];
        int array3[] = new int[100000];


        crescente(array1);
        crescente(array2);
        crescente(array3);
        

        for (int i = 0; i < array1.length; i++) {
            swap(array1, i, Math.abs(rand.nextInt()) % array1.length);
        }
        for (int i = 0; i < array2.length; i++) {
            swap(array2, i, Math.abs(rand.nextInt()) % array2.length);
        }
        for (int i = 0; i < array3.length; i++) {
            swap(array3, i, Math.abs(rand.nextInt()) % array3.length);
        }



    }
}