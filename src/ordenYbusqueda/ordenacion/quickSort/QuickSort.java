package ordenacion.quickSort;

public class QuickSort {

    public static void ordenar(int[] array, int izquierda, int derecha) {
        if (izquierda >= derecha) {
            return;
        }
        int indicePivote = particionar(array, izquierda, derecha);
        ordenar(array, izquierda, indicePivote - 1);
        ordenar(array, indicePivote + 1, derecha);
    }

    private static int particionar(int[] array, int izquierda, int derecha) {
        int pivote = array[derecha];
        int i = izquierda - 1;
        for (int j = izquierda; j < derecha; j++) {
            if (array[j] <= pivote) {
                i++;
                int temporal = array[i];
                array[i] = array[j];
                array[j] = temporal;
            }
        }
        int temporal = array[i + 1];
        array[i + 1] = array[derecha];
        array[derecha] = temporal;
        return i + 1;
    }

    public static void ordenarConTraza(int[] array) {
        ordenarConTraza(array, 0, array.length - 1);
    }

    private static void ordenarConTraza(int[] array, int izquierda, int derecha) {
        if (izquierda >= derecha) {
            return;
        }
        int indicePivote = particionar(array, izquierda, derecha);
        System.out.print("pivote en " + indicePivote + ": ");
        mostrar(array);
        ordenarConTraza(array, izquierda, indicePivote - 1);
        ordenarConTraza(array, indicePivote + 1, derecha);
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
