package construccion.listaSinOrden;

public class Cliente {
    public static void main(String[] args) {
        ListaSinOrden lista = new ListaSinOrden();
        int[] datos = {5, 3, 7, 1, 4, 6, 8};
        for (int valor : datos) {
            lista.insertar(valor);
        }

        System.out.println(lista.buscar(4));
        System.out.println(lista.buscar(9));
    }
}
