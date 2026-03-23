<div align=right>

<sub>[RECURSIVIDAD](/temario/002-recursividad/README.md)<br>
[Inducción](/temario/002-recursividad/01-induccion/prePatrones.md) / [Estructuración](/temario/002-recursividad/02-estructuracion/README.md) / [Implementación](/temario/002-recursividad/03-implementacion/README.md) / [Aplicación](/temario/002-recursividad/04-aplicacion/README.md) / [**PostRecursividad**](/temario/002-recursividad/05-postrecursividad/README.md)</sub><br>
[Inicio](../README.md) / [Coste](../01-coste-recursion.md) / [Fibonacci](../02-fibonacci.md) / [Memoización](../03-memoizacion.md) / [**Ejemplos prácticos**](README.md)<br>
[**Escaleras**](escaleratres.md) / [Caminos en una cuadrícula](caminoscuadrícula.md)

</div>

# Escaleras: subir 1, 2 o 3 pasos

|||||
|-|-|-|-|
|Variante directa de Fibonacci donde se pueden tomar 1, 2 o 3 pasos.|El árbol de llamadas se triplica en cada nivel.|Es el ejemplo más simple para mostrar cómo el número de ramas afecta el colapso del árbol con memoización.|

## El problema

Una escalera de n escalones. En cada paso puedes subir 1, 2 o 3 escalones. ¿De cuántas formas distintas puedes llegar al tope?

```
n = 1:    1 forma  (subir 1)
n = 2:    2 formas (1+1, 2)
n = 3:    4 formas (1+1+1, 1+2, 2+1, 3)
n = 4:    7 formas
```

La sucesión comienza: 1, 2, 4, 7, 13, 24, 44, 81, 149, 274, ...

## Anatomía recursiva

Para llegar al escalón n, puedes haber venido desde n-1 (subiendo 1), desde n-2 (subiendo 2) o desde n-3 (subiendo 3):

```
subir(n) = subir(n-1) + subir(n-2) + subir(n-3)
```

Esto conecta con el **[Paso 06](../../03-implementacion/06-multiples-llamadas-ambas.md)**: tres llamadas recursivas en lugar de dos. La estructura del árbol cambia drásticamente:

```
subir(4)
├── subir(3)
│   ├── subir(2)
│   │   ├── subir(1) → 1
│   │   ├── subir(0) → 1
│   │   └── subir(-1) → 0
│   ├── subir(1) → 1
│   └── subir(0) → 1
├── subir(2)
│   ├── subir(1) → 1
│   ├── subir(0) → 1
│   └── subir(-1) → 0
└── subir(1) → 1
```

`subir(0) = 1` porque llegar al escalón 0 desde cualquier punto significa que llegaste exactamente al destino: hay una sola forma de hacer eso. `subir(-1) = 0` es el centinela que descarta los saltos que sobrepasarían el tope.

Cada nivel del árbol se triplica. La ramificación r = 3 determina el coste: O(3^n).

### Diferencia con Fibonacci

| | Fibonacci | Escaleras |
|---|---|---|
| Ramas por nodo | 2 | 3 |
| Ramificación | r = 2 | r = 3 |
| Casos base | fib(0)=0, fib(1)=1 | subir(0)=1, subir(-1)=0 |
| Subproblemas solapados | ✓ | ✓ |
| Efecto de memoización | O(2^n) → O(n) | O(3^n) → O(n) |

El patrón de subproblemas solapados es idéntico: subir(2) se calcula múltiples veces en subir(4). Lo que cambia es cuánto más explosiva es la recursión sin memoización.

## Implementación sin memoización

```java
static int subir(int n) {
    if (n < 0) {
        return 0;
    }
    if (n <= 1) {
        return 1;
    }
    if (n == 2) {
        return 2;
    }
    return subir(n - 1) + subir(n - 2) + subir(n - 3);
}
```

### El coste explosivo

<div align=center>

|n|Resultado|Llamadas recursivas|
|-|-|-|
|5|13|41|
|10|274|1.741|
|15|5.768|73.711|
|20|121.415|3.122.423|
|30|26.392.835|> 5.6×10^8|

</div>

subir(30) requiere más de quinientos millones de llamadas. Cada paso adicional triplica el trabajo: subir(31) requiere ~1.7×10^9 llamadas.

## Implementación con memoización

```java
static int subirMemo(int n, int[] memo) {
    if (n < 0) {
        return 0;
    }
    if (n <= 1) {
        return 1;
    }
    if (n == 2) {
        return 2;
    }
    if (memo[n] != -1) {
        return memo[n];
    }
    memo[n] = subirMemo(n - 1, memo) + subirMemo(n - 2, memo) + subirMemo(n - 3, memo);
    return memo[n];
}

// Uso:
int[] memo = new int[n + 1];
Arrays.fill(memo, -1);
System.out.println(subirMemo(n, memo));
```

### Cómo funciona la caché

Hay dos momentos clave:

1. **Recuperar de caché**: cuando se verifica `if (memo[n] != -1)`, si el valor ya está calculado, se retorna inmediatamente sin hacer llamadas recursivas.
2. **Guardar en caché**: cuando se asigna `memo[n] = ...`, el resultado se almacena para futuras consultas.

La primera vez que se llama a `subirMemo(k)`, `memo[k]` es `-1` (centinela), por lo que se calcula el valor recursivamente y se guarda. Las siguientes veces, `memo[k]` ya tiene un valor válido, por lo que se recupera sin recalcular.

### El árbol colapsado

Con memoización, subir(4) genera:

```
subir(4)
└── subir(3)
    └── subir(2)
        ├── subir(1) → 1
        ├── subir(0) → 1
        └── subir(-1) → 0
    subir(2) → caché              ← no se recalcula
subir(3) → caché                  ← no se recalcula
```

Cada subproblema se calcula exactamente una vez y se guarda en caché. El árbol exponencial se convierte en una cadena lineal.

### Comparación de llamadas

<div align=center>

|Versión|Llamadas para subir(10)|Llamadas para subir(20)|Llamadas para subir(30)|
|-|-|-|-|
|Sin memo|1.741|3.122.423|> 5.6×10^8|
|Con memo|11|21|31|

</div>

La memoización reduce el coste de O(3^n) a O(n). El número de llamadas con memoización es n+1 (los subproblemas de 0 a n).

## Análisis de la caché

### Tamaño de la caché

El array `memo` tiene tamaño n+1, lo que significa O(n) de memoria adicional. Este es el intercambio explícito de espacio por tiempo:

- **Sin memo**: O(3^n) tiempo, O(n) espacio (stack de llamadas)
- **Con memo**: O(n) tiempo, O(n) espacio (stack + caché)

Para subir(30), el intercambio es extraordinario: pasar de > 5.6×10^8 llamadas a 31 llamadas a cambio de 30 enteros de memoria.

### Centinela vs valor real

Usamos `-1` como centinela de "no calculado". Ningún resultado válido es negativo en problemas de conteo, por lo que `-1` garantiza que nunca se confundirá con un resultado real.

## Extensión: k pasos

¿Qué pasa si puedes subir 1, 2, ..., k pasos?

```java
static int subirK(int n, int k, int[] memo) {
    if (n < 0) {
        return 0;
    }
    if (n == 0) {
        return 1;
    }
    if (memo[n] != -1) {
        return memo[n];
    }
    int suma = 0;
    for (int i = 1; i <= k; i++) {
        suma += subirK(n - i, k, memo);
    }
    memo[n] = suma;
    return memo[n];
}
```

La ramificación es k en lugar de 3. Sin memoización: O(k^n). Con memoización: O(kn).

> [Siguiente: Caminos en una cuadrícula](caminoscuadrícula.md)
