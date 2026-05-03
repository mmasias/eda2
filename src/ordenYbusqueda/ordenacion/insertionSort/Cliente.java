package ordenacion.insertionSort;

public class Cliente {

    public static void main(String[] args) {
        System.out.println("Iterativo");
        int[] a = {5, 2, 4, 1, 3};
        System.out.print("antes:   ");
        mostrar(a);
        InsertionSort.ordenar(a);
        System.out.print("después: ");
        mostrar(a);

        System.out.println();

        System.out.println("Recursivo");
        int[] b = {5, 2, 4, 1, 3};
        System.out.print("antes:   ");
        mostrar(b);
        InsertionSort.ordenar(b, b.length);
        System.out.print("después: ");
        mostrar(b);

        System.out.println();

        System.out.println("Con traza");
        int[] c = {5, 2, 4, 1, 3};
        InsertionSort.ordenarConTraza(c);
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
