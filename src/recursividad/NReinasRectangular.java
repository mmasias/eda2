import java.util.Scanner;

public class NReinasRectangular {

    static boolean puedeColocar(int[][] tablero, int fila, int columna) {
        int cantidadColumnas = tablero[0].length;
        int cantidadFilas = tablero.length;
        // horizontal
        for (int c = 0; c < cantidadColumnas; c++) {
            if (tablero[fila][c] == 1 || tablero[fila][c] == 2) {
                return false;
            }
        }
        // vertical
        for (int f = 0; f < cantidadFilas; f++){
            if(tablero[f][columna] == 1 || tablero[f][columna] == 2){
                return false;
            }
        }
        // diagonal arriba-izquierda
        for (int f = fila, c = columna; f >= 0 && c >= 0; f--, c--) {
            if (tablero[f][c] == 1 || tablero[f][c] == 2) {
                return false;
            }
        }
        // diagonal arriba derecha
        for (int f = fila, c = columna; f >= 0 && c < cantidadColumnas; f--, c++) {
            if (tablero[f][c] == 1 || tablero[f][c] == 2) {
                return false;
            }
        }

        // diagonal abajo-izquierda
        for (int f = fila, c = columna; f < cantidadFilas && c >= 0; f++, c--) {
            if (tablero[f][c] == 1 || tablero[f][c] == 2) {
                return false;
            }
        }

        // diagonal abajo derecha
        for (int f = fila, c = columna; f < cantidadFilas && c < cantidadColumnas; f++, c++) {
            if (tablero[f][c] == 1 || tablero[f][c] == 2) {
                return false;
            }
        }
        return true;
    }

    static boolean unaSolucion(int[][] tablero, int columna, int[] reinasEnTablero, int maximoDeReinas) {
        if (columna == tablero.length) {
            return true;
        }

        for (int fila = 0; fila < tablero.length; fila = fila + 1) {
            if (reinasEnTablero[0] == maximoDeReinas) {
                return true; 
            }

            if (puedeColocar(tablero, fila, columna)){
                tablero[fila][columna] = reinasEnTablero[0] == 0 ? 2 : 1;     // HACER
                reinasEnTablero[0]+=1;
                if (unaSolucion(tablero, columna + 1, reinasEnTablero, maximoDeReinas)) {
                    return true;                // propagar éxito
                }
                tablero[fila][columna] = 0;     // DESHACER
                reinasEnTablero[0]-=1;
            }
        }
        return false;
    }

    static void  todasLasSoluciones(int[][] tablero, int[] reinasEnTablero, int maximoDeReinas) {
        for (int fila = 0; fila < tablero.length; fila++) {
            for(int columna = 0; columna < tablero[0].length; columna++){
                if (reinasEnTablero[0] == maximoDeReinas) {
                    mostrar(tablero);
                    return;
                }
                if (columna == tablero[0].length || fila == tablero.length) {
                    mostrar(tablero);
                    return;
                }
                if (puedeColocar(tablero, fila, columna)) {
                    tablero[fila][columna] = reinasEnTablero[0] == 0 ? 2 : 1;
                    reinasEnTablero[0]+=1;
                    todasLasSoluciones(tablero, reinasEnTablero, maximoDeReinas);
                    tablero[fila][columna] = 0; // DESHACER
                    reinasEnTablero[0]-=1;
                }
            }
        }
    }

    static boolean unaSolucionConVisualizacion(int[][] tablero, int columna, int[] reinasEnTablero, int maximoDeReinas) {
        final String EXITO     = "-->";
        final String FALLO     = " x ";
        final String RETROCESO = "<--";
        if (columna == tablero[0].length) {
            return true;
        }
        if (reinasEnTablero[0] == maximoDeReinas){
            return true;
        }
        for (int fila = 0; fila < tablero.length; fila = fila + 1) {
            if (puedeColocar(tablero, fila, columna)) {
                System.out.println(EXITO + " Reina " + (columna + 1) + " en fila " + (fila + 1) + ": puede");
                tablero[fila][columna] = reinasEnTablero[0] == 0 ? 2 : 1;
                reinasEnTablero[0]+=1;
                mostrar(tablero);
                pausar();

                if (unaSolucionConVisualizacion(tablero, columna + 1, reinasEnTablero, maximoDeReinas)) {
                    return true;                                     // propagar éxito
                }
                tablero[fila][columna] = 0;
                reinasEnTablero[0]-=1;
                System.out.println(RETROCESO + " Reina " + (columna + 1) + " retirada de fila " + (fila + 1));
                mostrar(tablero);
                pausar();
                
            } else {
                System.out.println(FALLO + " Reina " + (columna + 1) + " en fila " + (fila + 1) + ": no puede");
            }
        }
        System.out.println("    Reina " + (columna + 1) + " sin posición. Retrocedo a reina " + columna + ".");
        return false;
    }

    static void pausar() {
        System.out.println("Pulse una tecla para continuar");
        new Scanner(System.in).nextLine();
    }

    static void mostrar(int[][] tablero) {
        System.out.println("-".repeat(tablero.length * 10));
        for (int[] fila : tablero) {
            for (int celda : fila) {
                System.out.print(celda == 1 ? " Q " : celda == 2 ? " R " : " . ");
            }
            System.out.println();
        }
        System.out.println("-".repeat(tablero.length * 10));
        System.out.println();
    }

    public static void main(String[] args) {
        boolean continuar = true;
        Scanner scanner = new Scanner(System.in);
        do {
            int[] conteoDeReinas = {0};

            System.out.print("¿Cuántas reinas?: ");
            int numeroReinas = scanner.nextInt();
            
            System.out.print("¿Cuanto deseas que sea el factor de crecimiento, no recomendamos un numero muy grande: ");
            int factorDeCrecimiento = scanner.nextInt();
            int alto, ancho;

            if (Math.random() > 0.5){
                alto = numeroReinas + factorDeCrecimiento;
                ancho = numeroReinas;
            }else{
                ancho = numeroReinas + factorDeCrecimiento;
                alto = numeroReinas;
            }

            System.out.print("¿Qué mostrar?: 1: Una solución / 2: Todas / 3: Una con visualización / 4: Salir ");
            switch (scanner.nextInt()) {
                case 1 -> {
                    System.out.println("Una solución (" + numeroReinas + " reinas):");
                    int[][] tablero = new int[alto][ancho];
                    if (unaSolucion(tablero, 0, conteoDeReinas, numeroReinas)) {
                        mostrar(tablero);
                    }
                }
                case 2 -> {
                    System.out.println("Todas las soluciones (" + numeroReinas + " reinas):");
                    int[][] tablero2 = new int[alto][ancho];
                    todasLasSoluciones(tablero2, conteoDeReinas, numeroReinas);
                }
                case 3 -> {
                    System.out.println("Una solución con visualización (" + numeroReinas + " reinas):");
                    int[][] tablero3 = new int[alto][ancho];
                    unaSolucionConVisualizacion(tablero3, 0, conteoDeReinas, numeroReinas);
                }
                case 4 -> continuar = false;
            }
        } while (continuar);
        scanner.close();
    }
}
