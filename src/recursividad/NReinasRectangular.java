import java.util.Scanner;

public class NReinasRectangular {

    static boolean puedeColocar(int[][] tablero, int fila, int columna) {      
        // horizontal izquierda
        for (int c = 0; c < columna; c++) {
            if (tablero[fila][c] == 1) {
                return false;
            }
        }
        // diagonal arriba-izquierda
        int columnaDiagonal = columna;
        for (int f = fila; f >= 0 && columnaDiagonal >= 0; f--) {
            if (tablero[f][columnaDiagonal] == 1) {
                return false;
            }
            columnaDiagonal--;
        }
        // diagonal abajo-izquierda
        columnaDiagonal = columna;
        for (int f = fila; f < tablero.length && columnaDiagonal >= 0; f++) {
            if (tablero[f][columnaDiagonal] == 1) {
                return false;
            }
            columnaDiagonal--;
        }

        return true;
    }

    static boolean unaSolucion(int[][] tablero, int columna, int[] reinasEnTablero, int maximoDeReinas) {
        if (columna == tablero.length) {
            return true;
        }
        if (reinasEnTablero[0] == maximoDeReinas){
            return true;
        }

        for (int fila = 0; fila < tablero.length; fila = fila + 1) {
            if (reinasEnTablero[0] == maximoDeReinas) {
                return true; 
            }

            if (puedeColocar(tablero, fila, columna)){
                tablero[fila][columna] = 1;     // HACER
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

    static void  todasLasSoluciones(int[][] tablero, int columna, int[] reinasEnTablero, int maximoDeReinas) {
        if (columna == tablero.length) {
            mostrar(tablero);
            return;
        }
        if (reinasEnTablero[0] == maximoDeReinas) {
            mostrar(tablero);
            return;
        }
        for (int fila = 0; fila < tablero.length; fila = fila + 1) {

            if (puedeColocar(tablero, fila, columna)) {
                tablero[fila][columna] = 1; // HACER
                reinasEnTablero[0]+=1;
                todasLasSoluciones(tablero, columna + 1, reinasEnTablero, maximoDeReinas);
                tablero[fila][columna] = 0; // DESHACER
                reinasEnTablero[0]-=1;
            }
        }
    }

    static boolean unaSolucionConVisualizacion(int[][] tablero, int columna, int[] reinasEnTablero, int maximoDeReinas) {
        final String EXITO     = "-->";
        final String FALLO     = " x ";
        final String RETROCESO = "<--";
        if (columna == tablero.length) {
            return true;
        }
        if (reinasEnTablero[0] == maximoDeReinas){
            return true;
        }
        for (int fila = 0; fila < tablero.length; fila = fila + 1) {
            if (puedeColocar(tablero, fila, columna)) {
                System.out.println(EXITO + " Reina " + (columna + 1) + " en fila " + (fila + 1) + ": puede");
                tablero[fila][columna] = 1;
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
                System.out.print(celda == 1 ? " Q " : " . ");
            }
            System.out.println();
        }
        System.out.println("-".repeat(tablero.length * 10));
        System.out.println();
    }

    public static void main(String[] args) {
        int[] conteoDeReinas = {0};
        boolean continuar = true;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("¿Cuántas reinas?: ");
            int numeroReinas = scanner.nextInt();
            
            System.out.print("¿Cuanto deseas que sea el factor de crecimiento?(recomendamos un numero entre 1.1 y 1.5): ");
            String stringDeFactorDeCrecimiento = scanner.next();
            Float factorDeCrecimiento = Float.valueOf(stringDeFactorDeCrecimiento);
            int alturaMinima, anchuraMinima;
            int alturaMaxima, anchuraMaxima;

            alturaMinima = anchuraMinima = numeroReinas;

            if (Math.random() > 0.5){
                alturaMaxima = numeroReinas * (int)(Math.random() * ((numeroReinas * factorDeCrecimiento) - numeroReinas + 1)) + numeroReinas;
                anchuraMaxima = numeroReinas;
            }else{
                anchuraMaxima = numeroReinas * (int)(Math.random() * ((numeroReinas * factorDeCrecimiento) - numeroReinas + 1)) + numeroReinas;
                alturaMaxima = numeroReinas;
            }

            int altoDelTablero = numeroReinas * (int)(Math.random() * (alturaMaxima - alturaMinima) + alturaMinima) - 1;
            int anchoDelTablero = numeroReinas * (int)(Math.random() * (anchuraMaxima - anchuraMinima) + anchuraMinima) - 1;

            System.out.print("¿Qué mostrar?: 1: Una solución / 2: Todas / 3: Una con visualización / 4: Mostrar datos / 5: Salir ");
            switch (scanner.nextInt()) {
                case 1 -> {
                    System.out.println("Una solución (" + numeroReinas + " reinas):");
                    int[][] tablero = new int[altoDelTablero][anchoDelTablero];
                    if (unaSolucion(tablero, 0, conteoDeReinas, numeroReinas)) {
                        mostrar(tablero);
                    }
                }
                case 2 -> {
                    System.out.println("Todas las soluciones (" + numeroReinas + " reinas):");
                    int[][] tablero2 = new int[altoDelTablero][anchoDelTablero];
                    todasLasSoluciones(tablero2, 0, conteoDeReinas, numeroReinas);
                }
                case 3 -> {
                    System.out.println("Una solución con visualización (" + numeroReinas + " reinas):");
                    int[][] tablero3 = new int[altoDelTablero][anchoDelTablero];
                    unaSolucionConVisualizacion(tablero3, 0, conteoDeReinas, numeroReinas);
                }
                case 4 -> {
                    System.out.println("Los minimos son H: " + alturaMinima + " W: " + anchuraMinima);
                    System.out.println("Los maximos son H: " + alturaMaxima + " W: " + anchuraMaxima);
                    System.out.println("Y la dimension es ancho " + anchoDelTablero + " y su altura es " + altoDelTablero);
                }
                case 5 -> continuar = false;
            }
        } while (continuar);
        scanner.close();
    }
}
