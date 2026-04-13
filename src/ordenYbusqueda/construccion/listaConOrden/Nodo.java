package construccion.listaConOrden;

class Nodo {
    private int valor;
    private Nodo siguiente;

    public Nodo(int valor) {
        this.valor = valor;
        this.siguiente = null;
    }

    public int getValor() {
        return valor;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo nodo) {
        siguiente = nodo;
    }
}
