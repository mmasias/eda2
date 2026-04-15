package construccion.bst;

public class BST {
    private Nodo raiz;

    public BST() {
        raiz = null;
    }

    public void insertar(int valor) {
        raiz = insertarEn(raiz, valor);
    }

    private Nodo insertarEn(Nodo nodo, int valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }
        if (valor < nodo.getValor()) {
            nodo.setIzquierda(insertarEn(nodo.getIzquierda(), valor));
        } else if (valor > nodo.getValor()) {
            nodo.setDerecha(insertarEn(nodo.getDerecha(), valor));
        }
        return nodo;
    }

    public boolean buscar(int valor) {
        return buscarEn(raiz, valor);
    }

    private boolean buscarEn(Nodo nodo, int valor) {
        if (nodo == null) {
            return false;
        }
        if (valor == nodo.getValor()) {
            return true;
        }
        if (valor < nodo.getValor()) {
            return buscarEn(nodo.getIzquierda(), valor);
        }
        return buscarEn(nodo.getDerecha(), valor);
    }
}
