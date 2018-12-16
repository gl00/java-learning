package basic.generics.exercise;

/*
exercise 3
my answer
 */
public class Exchanger {
    public static <T> void swap(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        Integer[] arrayOfIntegers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        swap(arrayOfIntegers, 0, 9);
        for (int n : arrayOfIntegers) {
            System.out.print(n + " ");
        }
        System.out.println();
    }
}
