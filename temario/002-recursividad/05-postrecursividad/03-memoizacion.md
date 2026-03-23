<div align=right>

<sub>[RECURSIVIDAD](/temario/002-recursividad/README.md)
[Inducción](/temario/002-recursividad/01-induccion/prePatrones.md) / [Estructuración](/temario/002-recursividad/02-estructuracion/README.md) / [Implementación](/temario/002-recursividad/03-implementacion/README.md) / [Aplicación](/temario/002-recursividad/04-aplicacion/README.md) / [**PostRecursividad**](/temario/002-recursividad/05-postrecursividad/README.md)</sub>
[Inicio](README.md) / [Coste](01-coste-recursion.md) / [Fibonacci](02-fibonacci.md) / [**Memoización**](03-memoizacion.md)

</div>

# Memoización

## ¿Por qué?

El problema de Fibonacci no es la recursión. Es que la misma llamada se repite múltiples veces y cada vez hace el mismo trabajo desde cero.

La solución es directa: la primera vez que se calcula `fib(k)`, se guarda el resultado. Las siguientes veces, se devuelve directamente sin recalcular.

## ¿Qué?

**Memoización**: guardar el resultado de cada llamada en una caché y consultarla antes de calcular.

El nombre viene del latín *memorandum* — algo que debe recordarse. No es un error tipográfico de "memorización".

## ¿Para qué?

- Eliminar el recálculo de subproblemas solapados
- Reducir el coste de `fib(n)` de O(2^n) a O(n)
- Entender el principio que subyace a la programación dinámica

## ¿Cómo?

### Implementación

```java
static long fib(int n, long[] cache) {
    if (n <= 1) {
        return n;
    }
    if (cache[n] != -1) {
        return cache[n];
    }
    cache[n] = fib(n - 1, cache) + fib(n - 2, cache);
    return cache[n];
}
```

```java
long[] cache = new long[n + 1];
Arrays.fill(cache, -1);
System.out.println(fib(10, cache));
```

La caché es un array inicializado a `-1` (centinela de "no calculado"). La estructura recursiva no cambia — solo se añade la consulta antes de calcular y el guardado antes de retornar.

### El árbol colapsado

Con memoización, `fib(5)` genera este árbol:

```
fib(5)
└── fib(4)
    └── fib(3)
        └── fib(2)
            ├── fib(1) → 1
            └── fib(0) → 0
        fib(1) → caché                ← no se recalcula
    fib(2) → caché                    ← no se recalcula
fib(3) → caché                        ← no se recalcula
```

Cada subproblema se calcula exactamente una vez. El árbol exponencial se convierte en una cadena lineal.

### Comparación

<div align=center>

|Versión|Llamadas para fib(10)|Llamadas para fib(30)|Coste|
|-|-|-|-|
|Ingenua|177|2.692.537|O(2^n)|
|Memoizada|19|59|O(n)|

</div>

### El coste de la caché

La memoización no es gratuita: requiere O(n) de memoria adicional para almacenar los resultados. Es un intercambio explícito de **espacio por tiempo**.

Para Fibonacci, el intercambio es razonable: pasar de O(2^n) a O(n) en tiempo a cambio de O(n) en espacio es una mejora extraordinaria.

### Cuándo aplica

La memoización es efectiva cuando se cumplen dos condiciones:

<div align=center>

|Condición|Descripción|
|-|-|
|**Subproblemas solapados**|Los mismos subproblemas aparecen múltiples veces en el árbol de llamadas|
|**Determinismo**|El subproblema siempre devuelve el mismo resultado para los mismos argumentos|

</div>

Si los subproblemas no se repiten — como en el backtracking, donde cada rama explora candidatos distintos — la memoización no ayuda.

### La puerta a la programación dinámica

La memoización es recursión con caché: se parte del problema grande y se desciende hacia los subproblemas. Es **top-down**.

La programación dinámica construye la solución en sentido contrario: parte de los casos base y construye hacia arriba con un bucle. Es **bottom-up**.

```java
static long fibDP(int n) {
    if (n <= 1) {
        return n;
    }
    long[] dp = new long[n + 1];
    dp[0] = 0;
    dp[1] = 1;
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }
    return dp[n];
}
```

Mismo resultado, mismo coste O(n), sin recursión.

## *#2Think*

1. La función `contar(Nodo nodo)` que cuenta los nodos de una lista enlazada, ¿tiene subproblemas solapados? ¿Tiene sentido memoizarla?

2. Si reutilizas el mismo array `cache` para calcular `fib(10)` y luego `fib(8)`, ¿cuántas llamadas hace la segunda? ¿Es eso un problema o una ventaja?

> [Ejemplos prácticos](ejemplos/README.md)

> [Volver al inicio del módulo](../README.md)
