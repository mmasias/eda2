package ordenacion.countingSort;

public class CountingSort {

    public static void ordenar(int[] array, int valorMaximo) {
        int[] conteo = new int[valorMaximo + 1];
        for (int i = 0; i < array.length; i++) {
            conteo[array[i]]++;
        }
        for (int i = 1; i <= valorMaximo; i++) {
            conteo[i] += conteo[i - 1];
        }
        int[] resultado = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            resultado[conteo[array[i]] - 1] = array[i];
            conteo[array[i]]--;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = resultado[i];
        }
    }

    public static void ordenarConTraza(int[] array, int valorMaximo) {
        int[] conteo = new int[valorMaximo + 1];
        for (int i = 0; i < array.length; i++) {
            conteo[array[i]]++;
        }
        System.out.print("conteo:      ");
        mostrarConteo(conteo);

        for (int i = 1; i <= valorMaximo; i++) {
            conteo[i] += conteo[i - 1];
        }
        System.out.print("acumulado:   ");
        mostrarConteo(conteo);

        int[] resultado = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            resultado[conteo[array[i]] - 1] = array[i];
            conteo[array[i]]--;
        }
        System.out.print("resultado:   ");
        mostrar(resultado);

        for (int i = 0; i < array.length; i++) {
            array[i] = resultado[i];
        }
    }

    private static void mostrarConteo(int[] conteo) {
        System.out.print("[");
        for (int i = 0; i < conteo.length; i++) {
            System.out.print(i + ":" + conteo[i]);
            if (i < conteo.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
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
