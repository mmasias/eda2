package construccion.listaSinOrden;

public class ListaSinOrden {
    private Nodo cabeza;

    public ListaSinOrden() {
        cabeza = null;
    }

    public void insertar(int valor) {
        Nodo nuevo = new Nodo(valor);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
    }

    public boolean buscar(int valor) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getValor() == valor) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
}
