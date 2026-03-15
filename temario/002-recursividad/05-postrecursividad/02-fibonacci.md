<div align=right>

<sub>[RECURSIVIDAD](/temario/002-recursividad/README.md)  
[Inducción](/temario/002-recursividad/01-induccion/prePatrones.md) / [Estructuración](/temario/002-recursividad/02-estructuracion/README.md) / [Implementación](/temario/002-recursividad/03-implementacion/README.md) / [Aplicación](/temario/002-recursividad/04-aplicacion/README.md) / [**PostRecursividad**](/temario/002-recursividad/05-postrecursividad/README.md)</sub>  
[Inicio](README.md) / [Coste](01-coste-recursion.md) / [**Fibonacci**](02-fibonacci.md) / [Memoización](03-memoizacion.md)

</div>

# Fibonacci

## ¿Por qué?

El artículo anterior mostró que la ramificación determina el coste. Pero hay un caso más sutil: una función puede parecer que hace poco trabajo — solo dos llamadas, un problema bien definido — y resultar catastrófica.

Fibonacci es ese caso. No porque sea difícil de implementar, sino porque la implementación recursiva natural genera un trabajo **enormemente mayor** del que el problema requiere.

## ¿Qué?

La sucesión de Fibonacci: cada número es la suma de los dos anteriores.

```
0, 1, 1, 2, 3, 5, 8, 13, 21, 34, ...
```

La definición es recursiva por naturaleza:

- `fib(0) = 0`
- `fib(1) = 1`
- `fib(n) = fib(n-1) + fib(n-2)`

La traducción directa a código es inmediata:

```java
static long fib(int n) {
    if (n <= 1) {
        return n;
    }
    return fib(n - 1) + fib(n - 2);
}
```

## ¿Para qué?

- Entender que "recursión correcta" no garantiza "recursión eficiente"
- Reconocer el patrón de **subproblemas solapados**
- Motivar la necesidad de una estrategia mejor

## ¿Cómo?

### El árbol de llamadas de fib(5)

```
fib(5)
├── fib(4)
│   ├── fib(3)
│   │   ├── fib(2)
│   │   │   ├── fib(1) → 1
│   │   │   └── fib(0) → 0
│   │   └── fib(1) → 1
│   └── fib(2)
│       ├── fib(1) → 1
│       └── fib(0) → 0
└── fib(3)
    ├── fib(2)
    │   ├── fib(1) → 1
    │   └── fib(0) → 0
    └── fib(1) → 1
```

### El problema: subproblemas solapados

<div align=center>

|Subproblema|Veces calculado en fib(5)|
|-|-|
|fib(3)|2|
|fib(2)|3|
|fib(1)|5|
|fib(0)|3|

</div>

`fib(5)` hace **15 llamadas** para calcular un valor que tiene exactamente **6 subproblemas distintos** (fib(0) a fib(5)).

El árbol crece exponencialmente, pero el número de subproblemas distintos crece linealmente. Esa es la contradicción: el problema es lineal en complejidad inherente, pero la implementación ingenua lo resuelve en tiempo exponencial.

### El coste real

El número de llamadas de `fib(n)` crece aproximadamente como φ^n, donde φ ≈ 1.618 es el número áureo. En la práctica, **O(2^n)** es una cota suficientemente precisa.

<div align=center>

|n|fib(n)|Llamadas aproximadas|
|-|-|-|
|10|55|177|
|20|6.765|21.891|
|30|832.040|2.692.537|
|50|12.586.269.025|> 10^10|

</div>

`fib(50)` con la implementación ingenua requiere más de diez mil millones de llamadas. Para n=100 el universo no tiene tiempo suficiente.

### La raíz del problema

Cada subproblema se recalcula desde cero cada vez que aparece en el árbol. No hay ningún mecanismo que evite repetir trabajo ya hecho.

La solución no requiere cambiar el algoritmo. Requiere **recordar** lo que ya se calculó.

## *#2Think*

1. ¿Cuántas veces se calcula `fib(2)` al ejecutar `fib(6)`? ¿Y `fib(1)`?

2. Implementa `fib(n)` con un bucle `for`. ¿Cuántas operaciones hace? Compara con la versión recursiva.

> [Siguiente: Memoización](03-memoizacion.md)
