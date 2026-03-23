<div align=right>

<sub>[RECURSIVIDAD](/temario/002-recursividad/README.md)<br>
[Inducción](/temario/002-recursividad/01-induccion/prePatrones.md) / [Estructuración](/temario/002-recursividad/02-estructuracion/README.md) / [Implementación](/temario/002-recursividad/03-implementacion/README.md) / [Aplicación](/temario/002-recursividad/04-aplicacion/README.md) / [**PostRecursividad**](/temario/002-recursividad/05-postrecursividad/README.md)</sub><br>
[Inicio](../README.md) / [Coste](../01-coste-recursion.md) / [Fibonacci](../02-fibonacci.md) / [Memoización](../03-memoizacion.md) / [**Ejemplos prácticos**](README.md)<br>
[Escaleras](escaleratres.md) / [**Caminos en una cuadrícula**](caminoscuadrícula.md)

</div>

# Caminos en una cuadrícula

|||||
|-|-|-|-|
|Contar caminos desde (0,0) hasta (m,n) moviéndose solo derecha y abajo.|Es el primer ejemplo con caché bidimensional: cada subproblema está identificado por dos coordenadas, no un solo entero.|El árbol de llamadas es idéntico en estructura a Fibonacci, pero la caché requiere un array 2D.|

## El problema

Una cuadrícula de m×n celdas. Estás en la esquina superior izquierda (0,0) y quieres llegar a la esquina inferior derecha (m-1, n-1). En cada paso solo puedes moverte hacia la derecha o hacia abajo. ¿Cuántos caminos distintos existen?

<div align=center>

```
(0,0) → (0,1) → (0,2) → ... → (0,n-1)
  ↓       ↓       ↓              ↓
(1,0) → (1,1) → (1,2) → ... → (1,n-1)
  ↓       ↓       ↓              ↓
(2,0) → (2,1) → (2,2) → ... → (2,n-1)
  ↓       ↓       ↓              ↓
 ...    ...    ...    →      (m-1,n-1)
```

</div>

## Casos pequeños

**2×2 (1,1)**:
- (0,0)→(0,1)→(1,1)
- (0,0)→(1,0)→(1,1)
Total: 2 caminos

**3×2 (2,1)**:
- (0,0)→(0,1)→(1,1)→(2,1)
- (0,0)→(1,0)→(1,1)→(2,1)
- (0,0)→(1,0)→(2,0)→(2,1)
Total: 3 caminos

**3×3 (2,2)**: 6 caminos

## Anatomía recursiva

La observación clave: para llegar a (i,j), puedes haber venido desde arriba (i-1,j) o desde la izquierda (i,j-1):

```
caminos(i, j) = caminos(i-1, j) + caminos(i, j-1)
```

Esto conecta con el **[Paso 06](../../03-implementacion/06-multiples-llamadas-ambas.md)**: dos llamadas recursivas, idénticas en estructura a Fibonacci, pero con parámetros bidimensionales.

### Casos base

- Si estás en el borde superior (i=0) o borde izquierdo (j=0), solo hay un camino: moverse en línea recta hasta (0,0).
- caminos(0,0) = 1 (ya estás en el destino)

### El árbol de llamadas para caminos(2,2)

```
caminos(2, 2)
├── caminos(1, 2)
│   ├── caminos(0, 2) → 1
│   └── caminos(1, 1)
│       ├── caminos(0, 1) → 1
│       └── caminos(1, 0) → 1
└── caminos(2, 1)
    ├── caminos(1, 1)       ← duplicado
    │   ├── caminos(0, 1) → 1
    │   └── caminos(1, 0) → 1
    └── caminos(2, 0) → 1
```

`caminos(1,1)` se calcula **dos veces**. `caminos(0,1)` y `caminos(1,0)` se calculan **dos veces**.

El patrón de subproblemas solapados es el mismo que Fibonacci: cada subproblema (i,j) aparece múltiples veces en el árbol.

## Implementación sin memoización

```java
static int caminos(int i, int j) {
    if (i == 0 || j == 0) {
        return 1;
    }
    return caminos(i - 1, j) + caminos(i, j - 1);
}
```

### El coste explosivo

<div align=center>

|Tamaño de cuadrícula|Resultado|Llamadas recursivas|
|-|-|-|
|5×5|70|251|
|10×10|48.620|167.960|
|(...)|||
|20×20|137.846.528.820|1.378.465.288.219|

</div>

caminos(20,20) requiere más de 1.3 billones de llamadas. La cuadrícula crece linealmente (m=n), pero el número de llamadas crece exponencialmente: O(2^(m+n)).

## Implementación con memoización

Aquí aparece la diferencia crucial: la caché es **bidimensional**.

```java
static int caminosMemo(int i, int j, int[][] memo) {
    if (i == 0 || j == 0) {
        return 1;
    }
    if (memo[i][j] != -1) {
        return memo[i][j];
    }
    memo[i][j] = caminosMemo(i - 1, j, memo) + caminosMemo(i, j - 1, memo);
    return memo[i][j];
}

// Uso:
int[][] memo = new int[m + 1][n + 1];
for (int i = 0; i < memo.length; i++) {
    Arrays.fill(memo[i], -1);
}
System.out.println(caminosMemo(m, n, memo));
```

### Cómo funciona la caché

Hay dos momentos clave:

1. **Recuperar de caché**: cuando se verifica `if (memo[i][j] != -1)`, si el valor ya está calculado, se retorna inmediatamente sin hacer llamadas recursivas.
2. **Guardar en caché**: cuando se asigna `memo[i][j] = ...`, el resultado se almacena para futuras consultas.

La primera vez que se llama a `caminosMemo(i, j)`, `memo[i][j]` es `-1` (centinela), por lo que se calcula el valor recursivamente y se guarda. Las siguientes veces, `memo[i][j]` ya tiene un valor válido, por lo que se recupera sin recalcular.

### El árbol colapsado

Con memoización, caminos(2,2) genera:

```
caminos(2, 2)
└── caminos(1, 2)
    └── caminos(1, 1)
        ├── caminos(0, 1) → 1
        └── caminos(1, 0) → 1
    caminos(2, 1) → caché          ← no se recalcula
caminos(1, 2) → caché              ← no se recalcula
```

Cada subproblema (i,j) se calcula exactamente una vez y se guarda en caché.

### Comparación de llamadas

<div align=center>

|Versión|Llamadas para 5×5|Llamadas para 10×10|Llamadas para 20×20|
|-|-|-|-|
|Sin memo|251|167.960|1.378.465.288.219|
|Con memo|15|55|231|

</div>

La memoización reduce el coste de O(2^(m+n)) a O(m×n).

## Análisis de la caché 2D

### Tamaño de la caché

El array `memo` tiene tamaño (m+1)×(n+1). Esto significa O(mn) de memoria adicional.

- **Sin memo**: O(2^(m+n)) tiempo, O(m+n) espacio (stack de llamadas)
- **Con memo**: O(mn) tiempo, O(mn) espacio (stack + caché)

Para caminos(20,20), el intercambio es extraordinario: pasar de 1.3 billones de llamadas a 231 llamadas a cambio de 441 enteros de memoria (21×21).

### Estructura de acceso

La clave de caché es el par ordenado (i, j). El orden importa: memo[i][j] es diferente de memo[j][i] si m ≠ n.

Este es el primer ejemplo donde la clave no es un solo entero. El concepto de "subproblema" se extiende de "qué valor quiero calcular" a "qué parámetros definen este subproblema".

## Extensión: cuadrículas con obstáculos

¿Qué pasa si algunas celdas están bloqueadas?

```java
static int caminosConObstaculos(int i, int j, boolean[][] bloqueado, int[][] memo) {
    if (i < 0 || j < 0) {
        return 0;
    }
    if (bloqueado[i][j]) {
        return 0;
    }
    if (i == 0 && j == 0) {
        return 1;
    }
    if (memo[i][j] != -1) {
        return memo[i][j];
    }
    memo[i][j] = caminosConObstaculos(i - 1, j, bloqueado, memo)
               + caminosConObstaculos(i, j - 1, bloqueado, memo);
    return memo[i][j];
}
```

La estructura de memoización es idéntica. La caché sigue siendo 2D. Solo cambian las condiciones de contorno: algunos caminos no son transitables.

## Cuándo NO aplica

Si el movimiento puede ser en cualquier dirección (incluyendo hacia arriba o hacia la izquierda), pueden aparecer ciclos: la función llamaría a (i-1,j), que llamaría de nuevo a (i,j), y así indefinidamente. La memoización simple no es suficiente para grafos con ciclos — hacen falta técnicas de detección de ciclos o algoritmos específicos para grafos.

> [Volver a ejemplos prácticos](README.md)
