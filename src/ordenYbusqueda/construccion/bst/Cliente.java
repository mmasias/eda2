package construccion.bst;

public class Cliente {
    public static void main(String[] args) {
        BST arbol = new BST();
        int[] datos = {5, 3, 7, 1, 4, 6, 8};
        for (int valor : datos) {
            arbol.insertar(valor);
        }

        System.out.println(arbol.buscar(4));
        System.out.println(arbol.buscar(9));
    }    
}
