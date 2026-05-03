package ordenacion.quickSort;

public class Cliente {

    public static void main(String[] args) {
        System.out.println("Quicksort");
        int[] a = {4, 2, 7, 1, 5, 3};
        System.out.print("antes:   ");
        mostrar(a);
        QuickSort.ordenar(a, 0, a.length - 1);
        System.out.print("después: ");
        mostrar(a);

        System.out.println();

        System.out.println("Con traza");
        int[] b = {4, 2, 7, 1, 5, 3};
        QuickSort.ordenarConTraza(b);
    }

    private static void mostrar(int[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
