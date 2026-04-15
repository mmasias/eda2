package construccion.bstBalanceado;

class Nodo {
    private int valor;
    private Nodo izquierda;
    private Nodo derecha;
    private int altura;

    public Nodo(int valor) {
        this.valor = valor;
        this.izquierda = null;
        this.derecha = null;
        this.altura = 0;
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

    public int getAltura() {
        return altura;
    }

    public void setIzquierda(Nodo nodo) {
        izquierda = nodo;
    }

    public void setDerecha(Nodo nodo) {
        derecha = nodo;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
}
