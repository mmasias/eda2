package construccion.arrayConOrden;

public class Cliente {
    public static void main(String[] args) {
        ArrayConOrden array = new ArrayConOrden(10);
        int[] datos = {5, 3, 7, 1, 4, 6, 8};
        for (int valor : datos) {
            array.insertar(valor);
        }

        System.out.println(array.buscar(4));
        System.out.println(array.buscar(9));
    }
}
