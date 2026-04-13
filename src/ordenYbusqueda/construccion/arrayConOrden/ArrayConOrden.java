package construccion.arrayConOrden;

public class ArrayConOrden {
    private int[] datos;
    private int cantidad;

    public ArrayConOrden(int capacidad) {
        datos = new int[capacidad];
        cantidad = 0;
    }

    public void insertar(int valor) {
        assert cantidad < datos.length : "Array lleno";
        int posicion = encontrarPosicion(valor);
        desplazar(posicion);
        datos[posicion] = valor;
        cantidad++;
    }

    private int encontrarPosicion(int valor) {
        int izquierda = 0;
        int derecha = cantidad;
        while (izquierda < derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;
            if (datos[medio] < valor) {
                izquierda = medio + 1;
            } else {
                derecha = medio;
            }
        }
        return izquierda;
    }

    private void desplazar(int posicion) {
        for (int i = cantidad; i > posicion; i--) {
            datos[i] = datos[i - 1];
        }
    }

    public boolean buscar(int valor) {
        int izquierda = 0;
        int derecha = cantidad - 1;
        while (izquierda <= derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;
            if (datos[medio] == valor) {
                return true;
            }
            if (datos[medio] < valor) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }
        return false;
    }
}
