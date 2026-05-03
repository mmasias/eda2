package ordenacion.mergeSort;

public class Cliente {

    public static void main(String[] args) {
        System.out.println("Recursivo (top-down)");
        int[] a = {8, 3, 5, 1, 4, 2};
        System.out.print("antes:   ");
        mostrar(a);
        MergeSort.ordenar(a, 0, a.length - 1);
        System.out.print("después: ");
        mostrar(a);

        System.out.println();

        System.out.println("Iterativo (bottom-up)");
        int[] b = {8, 3, 5, 1, 4, 2};
        System.out.print("antes:   ");
        mostrar(b);
        MergeSort.ordenarIterativo(b);
        System.out.print("después: ");
        mostrar(b);

        System.out.println();

        System.out.println("Con traza");
        int[] c = {8, 3, 5, 1, 4, 2};
        MergeSort.ordenarConTraza(c);
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
