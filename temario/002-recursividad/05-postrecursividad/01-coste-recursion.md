<div align=right>

<sub>[RECURSIVIDAD](/temario/002-recursividad/README.md)
[Inducción](/temario/002-recursividad/01-induccion/prePatrones.md) / [Estructuración](/temario/002-recursividad/02-estructuracion/README.md) / [Implementación](/temario/002-recursividad/03-implementacion/README.md) / [Aplicación](/temario/002-recursividad/04-aplicacion/README.md) / [**PostRecursividad**](/temario/002-recursividad/05-postrecursividad/README.md)</sub>
[Inicio](README.md) / [**Coste**](01-coste-recursion.md) / [Fibonacci](02-fibonacci.md) / [Memoización](03-memoizacion.md)

</div>

# El coste de la recursión

## ¿Por qué?

Hasta ahora el criterio ha sido: ¿funciona? Si tiene caso base, caso recursivo y tiende al caso base, la función es correcta.

Pero correcto no implica eficiente. Dos funciones pueden producir el mismo resultado y diferir en órdenes de magnitud en el número de operaciones que realizan. Para tener criterio completo hace falta saber **cuántas llamadas genera** una función recursiva.

## ¿Qué?

El [árbol de decisiones](../03-implementacion/10-el-arbol-de-decisiones.md) no es solo una herramienta para visualizar el backtracking. Es también la herramienta para analizar coste: cada nodo es una llamada, y el número de nodos es el número de llamadas.

El coste de un árbol de llamadas depende de dos dimensiones:

<div align=center>

|Dimensión|Pregunta|
|-|-|
|**Profundidad**|¿Cuántos niveles tiene el árbol?|
|**Ramificación**|¿Cuántas llamadas hace cada nodo?|

**Coste total = trabajo por nodo × número de nodos**

</div>

## ¿Para qué?

- Estimar el coste de una función recursiva a partir de su estructura
- Distinguir entre recursión lineal y ramificada
- Entender por qué la poda importa aunque no cambie el peor caso teórico

## ¿Cómo?

### Recursión lineal: factorial

```java
static int factorial(int n) {
    if (n == 0) {
        return 1;
    }
    return n * factorial(n - 1);
}
```

Cada llamada genera exactamente **una** llamada recursiva. El árbol es una cadena:

```
factorial(4)
└── factorial(3)
    └── factorial(2)
        └── factorial(1)
            └── factorial(0) → 1
```

<div align=center>

|Profundidad|Ramificación|Nodos totales|Trabajo por nodo|Coste|
|-|-|-|-|-|
|n|1|n + 1|O(1)|**O(n)**|

</div>

### Recursión ramificada: subconjuntos

```java
static void fabricar(int[] nums, int indice, List<Integer> actual) {
    if (indice == nums.length) {
        System.out.println(actual);
        return;
    }
    actual.add(nums[indice]);
    fabricar(nums, indice + 1, actual);
    actual.remove(actual.size() - 1);
    fabricar(nums, indice + 1, actual);
}
```

Cada llamada genera **dos** llamadas recursivas. El árbol se duplica en cada nivel:

```
fabricar([1,2,3])
├── fabricar([2,3])                 ← con 1
│   ├── fabricar([3])               ← con 1,2
│   │   ├── fabricar([]) → [1,2,3]
│   │   └── fabricar([]) → [1,2]
│   └── fabricar([3])               ← con 1
│       ├── fabricar([]) → [1,3]
│       └── fabricar([]) → [1]
└── fabricar([2,3])                 ← sin 1
    ├── fabricar([3])               ← con 2
    │   ├── fabricar([]) → [2,3]
    │   └── fabricar([]) → [2]
    └── fabricar([3])               ← sin 2
        ├── fabricar([]) → [3]
        └── fabricar([]) → []
```

<div align=center>

|Profundidad|Ramificación|Nodos totales|Trabajo por nodo|Coste|
|-|-|-|-|-|
|n|2|2^(n+1) − 1|O(1)|**O(2^n)**|

</div>

### La relación general

La fórmula de nodos totales para un árbol de profundidad n y ramificación k es la serie geométrica: (k^(n+1) − 1) / (k − 1).

<div align=center>

|Ramificación|Profundidad|Nodos totales|Coste|
|-|-|-|-|
|1|n|n + 1|O(n)|
|2|n|2^(n+1) − 1|O(2^n)|
|k|n|(k^(n+1) − 1) / (k − 1)|O(k^n)|

</div>

Pasar de 1 a 2 llamadas por nodo transforma un algoritmo lineal en uno exponencial. La ramificación domina el coste.

### La poda revisitada

El backtracking es inherentemente exponencial. La poda no cambia el **peor caso** — el árbol sigue creciendo exponencialmente — pero puede reducir drásticamente el **caso promedio** eliminando ramas enteras antes de entrar en ellas.

En un espacio de 2^30 hojas, podar la mitad del árbol no es un detalle menor.

## *#2Think*

1. La búsqueda binaria recursiva descarta la mitad del problema en cada llamada y solo hace **una** llamada recursiva. ¿Cuál es su árbol? ¿Cuál es su coste?

2. El recorrido inorder de un árbol binario balanceado con n nodos hace **dos** llamadas por nodo, pero la profundidad del árbol es log(n). ¿Cuántos nodos tiene su árbol de llamadas? ¿Cuál es su coste?

> [Siguiente: Fibonacci](02-fibonacci.md)
