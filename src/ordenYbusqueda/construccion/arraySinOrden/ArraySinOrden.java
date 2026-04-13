package construccion.arraySinOrden;

public class ArraySinOrden {
    private int[] datos;
    private int cantidad;

    public ArraySinOrden(int capacidad) {
        datos = new int[capacidad];
        cantidad = 0;
    }

    public void insertar(int valor) {
        assert cantidad < datos.length : "Array lleno";
        datos[cantidad] = valor;
        cantidad++;
    }

    public boolean buscar(int valor) {
        for (int i = 0; i < cantidad; i++) {
            if (datos[i] == valor) {
                return true;
            }
        }
        return false;
    }
}
