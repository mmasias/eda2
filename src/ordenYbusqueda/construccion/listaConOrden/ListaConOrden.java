package construccion.listaConOrden;

public class ListaConOrden {
    private Nodo cabeza;

    public ListaConOrden() {
        cabeza = null;
    }

    public void insertar(int valor) {
        Nodo nuevo = new Nodo(valor);
        if (cabeza == null || valor < cabeza.getValor()) {
            nuevo.setSiguiente(cabeza);
            cabeza = nuevo;
            return;
        }
        Nodo anterior = encontrarAnterior(valor);
        nuevo.setSiguiente(anterior.getSiguiente());
        anterior.setSiguiente(nuevo);
    }

    private Nodo encontrarAnterior(int valor) {
        Nodo actual = cabeza;
        while (actual.getSiguiente() != null && actual.getSiguiente().getValor() < valor) {
            actual = actual.getSiguiente();
        }
        return actual;
    }

    public boolean buscar(int valor) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.getValor() == valor) {
                return true;
            }
            if (actual.getValor() > valor) {
                return false;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
}
