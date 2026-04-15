package construccion.bst;

class Nodo {
    private int valor;
    private Nodo izquierda;
    private Nodo derecha;

    public Nodo(int valor) {
        this.valor = valor;
        this.izquierda = null;
        this.derecha = null;
    }

    public int getValor() {
        return valor;
    }

    public Nodo getIzquierda() {
        return izquierda;
    }

    public Nodo getDerecha() {
        return derecha;
    }

    public void setIzquierda(Nodo nodo) {
        izquierda = nodo;
    }

    public void setDerecha(Nodo nodo) {
        derecha = nodo;
    }
}
