package construccion.arraySinOrden;

public class Cliente {
    public static void main(String[] args) {
        ArraySinOrden array = new ArraySinOrden(10);
        int[] datos = {5, 3, 7, 1, 4, 6, 8};
        for (int valor : datos) {
            array.insertar(valor);
        }

        System.out.println(array.buscar(4));
        System.out.println(array.buscar(9));
    }
}
