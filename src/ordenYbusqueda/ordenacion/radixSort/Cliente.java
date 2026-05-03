package ordenacion.radixSort;

public class Cliente {

    public static void main(String[] args) {
        System.out.println("Radix sort");
        int[] a = {170, 45, 75, 90, 802, 24, 2, 66};
        System.out.print("antes:   ");
        mostrar(a);
        RadixSort.ordenar(a, 802);
        System.out.print("después: ");
        mostrar(a);

        System.out.println();

        System.out.println("Con traza");
        int[] b = {170, 45, 75, 90, 802, 24, 2, 66};
        RadixSort.ordenarConTraza(b, 802);
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
