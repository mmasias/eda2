package ordenacion.radixSort;

public class RadixSort {

    public static void ordenar(int[] array, int valorMaximo) {
        for (int posicion = 1; valorMaximo / posicion > 0; posicion *= 10) {
            ordenarPorDigito(array, posicion);
        }
    }

    private static void ordenarPorDigito(int[] array, int posicion) {
        int base = 10;
        int[] conteo = new int[base];
        int[] resultado = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            conteo[(array[i] / posicion) % base]++;
        }
        for (int i = 1; i < base; i++) {
            conteo[i] += conteo[i - 1];
        }
        for (int i = array.length - 1; i >= 0; i--) {
            int digito = (array[i] / posicion) % base;
            resultado[conteo[digito] - 1] = array[i];
            conteo[digito]--;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = resultado[i];
        }
    }

    public static void ordenarConTraza(int[] array, int valorMaximo) {
        int pasada = 1;
        for (int posicion = 1; valorMaximo / posicion > 0; posicion *= 10) {
            ordenarPorDigito(array, posicion);
            System.out.print("pasada " + pasada + " (pos=" + posicion + "): ");
            mostrar(array);
            pasada++;
        }
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
