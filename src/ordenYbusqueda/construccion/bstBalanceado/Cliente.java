package construccion.bstBalanceado;

public class Cliente {
    public static void main(String[] args) {
        BSTBalanceado arbol = new BSTBalanceado();
        int[] datos = {1, 2, 3, 4, 5};
        for (int valor : datos) {
            arbol.insertar(valor);
        }

        System.out.println(arbol.buscar(4));
        System.out.println(arbol.buscar(9));
    }
}
