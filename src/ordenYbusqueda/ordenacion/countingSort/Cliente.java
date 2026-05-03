package ordenacion.countingSort;

public class Cliente {

    public static void main(String[] args) {
        System.out.println("Counting sort");
        int[] a = {3, 1, 2, 4, 1, 3};
        System.out.print("antes:   ");
        mostrar(a);
        CountingSort.ordenar(a, 4);
        System.out.print("después: ");
        mostrar(a);

        System.out.println();

        System.out.println("Con traza");
        int[] b = {3, 1, 2, 4, 1, 3};
        CountingSort.ordenarConTraza(b, 4);
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
