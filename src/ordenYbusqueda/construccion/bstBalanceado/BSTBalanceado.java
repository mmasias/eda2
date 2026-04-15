package construccion.bstBalanceado;

public class BSTBalanceado {
    private Nodo raiz;

    public BSTBalanceado() {
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
        } else {
            return nodo;
        }
        actualizarAltura(nodo);
        return rebalancear(nodo);
    }

    private Nodo rebalancear(Nodo nodo) {
        int balance = balance(nodo);

        // Left-Left
        if (balance > 1 && balance(nodo.getIzquierda()) >= 0) {
            return rotarDerecha(nodo);
        }
        // Left-Right
        if (balance > 1 && balance(nodo.getIzquierda()) < 0) {
            nodo.setIzquierda(rotarIzquierda(nodo.getIzquierda()));
            return rotarDerecha(nodo);
        }
        // Right-Right
        if (balance < -1 && balance(nodo.getDerecha()) <= 0) {
            return rotarIzquierda(nodo);
        }
        // Right-Left
        if (balance < -1 && balance(nodo.getDerecha()) > 0) {
            nodo.setDerecha(rotarDerecha(nodo.getDerecha()));
            return rotarIzquierda(nodo);
        }
        return nodo;
    }

    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.getIzquierda();
        Nodo t = x.getDerecha();
        x.setDerecha(y);
        y.setIzquierda(t);
        actualizarAltura(y);
        actualizarAltura(x);
        return x;
    }

    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.getDerecha();
        Nodo t = y.getIzquierda();
        y.setIzquierda(x);
        x.setDerecha(t);
        actualizarAltura(x);
        actualizarAltura(y);
        return y;
    }

    private void actualizarAltura(Nodo nodo) {
        nodo.setAltura(1 + Math.max(altura(nodo.getIzquierda()), altura(nodo.getDerecha())));
    }

    private int altura(Nodo nodo) {
        return nodo == null ? -1 : nodo.getAltura();
    }

    private int balance(Nodo nodo) {
        return nodo == null ? 0 : altura(nodo.getIzquierda()) - altura(nodo.getDerecha());
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
