class CaminosCuadricula {

    static long caminos(long i, long j) {
        if (i == 0 || j == 0) {
            return 1;
        }
        return caminos(i - 1, j) + caminos(i, j - 1);
    }

    static long caminosMemo(long i, long j, long[][] memo) {
        if (i == 0 || j == 0) {
            return 1;
        }
        if (memo[(int) (i)][(int) (j)] != -1) {
            return memo[(int) (i)][(int) (j)];
        }
        memo[(int) (i)][(int) (j)] = caminosMemo(i - 1, j, memo) + caminosMemo(i, j - 1, memo);
        return memo[(int) (i)][(int) (j)];
    }

    static long caminosIterativo(long m, long n) {
        long[][] dp = new long[(int) (m + 1)][(int) (n + 1)];
        for (long i = 0; i <= m; i++) {
            for (long j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    dp[(int) i][(int) j] = 1;
                } else {
                    dp[(int) (i)][(int) (j)] = dp[(int) (i - 1)][(int) (j)] + dp[(int) (i)][(int) (j - 1)];
                }
            }
        }
        return dp[(int) (m)][(int) (n)];
    }

    static long[] contarLlamadasRecursivo(long i, long j, long[] contador) {
        if (i == 0 || j == 0) {
            return new long[] { 1 };
        }
        contador[0]++;
        long resultado = contarLlamadasRecursivo(i - 1, j, contador)[0]
                + contarLlamadasRecursivo(i, j - 1, contador)[0];
        return new long[] { resultado };
    }

    static long[] contarLlamadasMemo(long i, long j, long[][] memo, long[] contador) {
        if (i == 0 || j == 0) {
            return new long[] { 1 };
        }
        if (memo[(int) (i)][(int) (j)] != -1) {
            return new long[] { memo[(int) (i)][(int) (j)] };
        }
        contador[0]++;
        long resultado = contarLlamadasMemo(i - 1, j, memo, contador)[0] + contarLlamadasMemo(i, j - 1, memo, contador)[0];
        memo[(int) (i)][(int) (j)] = (long) resultado;
        return new long[] { resultado };
    }

    static long caminosConObstaculos(long i, long j, boolean[][] bloqueado, long[][] memo) {
        if (i < 0 || j < 0) {
            return 0;
        }
        if (bloqueado[(int) (i)][(int) (j)]) {
            return 0;
        }
        if (i == 0 && j == 0) {
            return 1;
        }
        if (memo[(int) (i)][(int) (j)] != -1) {
            return memo[(int) (i)][(int) (j)];
        }
        memo[(int) (i)][(int) (j)] = caminosConObstaculos(i - 1, j, bloqueado, memo) + caminosConObstaculos(i, j - 1, bloqueado, memo);
        return memo[(int) (i)][(int) (j)];
    }

    public static void main(String[] args) {
        System.out.println("COMPARACIÓN DE VERSIONES");
        long[] tamañoCuadricula = { 1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (long i = 0; i < tamañoCuadricula.length; i++) {
            long n = tamañoCuadricula[(int) (i)];
            System.out.println();
            System.out.println("Cuadrícula " + (n + 1) + "×" + (n + 1) + ":");
            System.out.println("Sin memo:   " + caminos(n, n));

            long[][] memo = new long[(int) (n + 1)][(int) (n + 1)];
            for (long j = 0; j < memo.length; j++) {
                java.util.Arrays.fill(memo[(int) (j)], -1);
            }
            System.out.println("Con memo:   " + caminosMemo(n, n, memo));
            System.out.println("Iterativo:  " + caminosIterativo(n, n));
        }

        System.out.println();
        System.out.println("CONTEO DE LLAMADAS");
        for (int i = 0; i < tamañoCuadricula.length; i++) {
            long n = tamañoCuadricula[(int) (i)];
            long[] contador = { 0 };
            contarLlamadasRecursivo(n, n, contador);
            System.out.println("Llamadas sin memo para " + n + "×" + n + ": " + contador[0]);

            long[][] memo = new long[(int) (n + 1)][(int) (n + 1)];
            for (int j = 0; j < memo.length; j++) {
                java.util.Arrays.fill(memo[j], -1);
            }
            contador = new long[] { 0 };
            contarLlamadasMemo(n, n, memo, contador);
            System.out.println("Llamadas con memo para " + n + "×" + n + ": " + contador[0]);
        }
    }
}