<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [**Búsquedas**](../busqueda/README.md) / [Ordenación](../ordenacion/README.md) / [Índice externo](../indiceExterno.md)  </sub>  
[**Búsqueda lineal**](lineal.md) / [Búsqueda binaria](binaria.md) / [Búsqueda en BST](bst.md)

</div>

# Búsqueda lineal

## ¿Por qué?

Toda búsqueda parte de una pregunta previa: ¿qué sé de mis datos? La búsqueda lineal es la respuesta cuando la respuesta a esa pregunta es: nada o no lo suficiente.

No es la estrategia de último recurso: **es la estrategia correcta cuando no se puede garantizar ninguna precondición**. Y en muchos contextos reales, esa es exactamente la situación.

## ¿Qué?

La búsqueda lineal recorre la colección elemento a elemento, comparando cada uno con el objetivo. No asume nada sobre el orden ni la distribución de los datos.

Su comportamiento depende de lo que se sepa sobre la colección:

<div align=center>

| Colección | Comportamiento | Complejidad |
|-|-|:-:|
| Sin orden conocido | Recorre hasta el final si no encuentra | O(n) peor caso |
| Con orden conocido | Puede detenerse al superar el objetivo | O(n) peor caso, mejor promedio |

</div>

La segunda fila es la más instructiva: el orden no habilita búsqueda binaria en una lista (sin acceso aleatorio no se puede saltar al centro), pero sí permite parar antes. El invariante hace algo, aunque no lo suficiente para cambiar la complejidad asintótica.

## ¿Para qué?

La búsqueda lineal es la elección correcta cuando:

- Los datos no tienen orden conocido y el coste de ordenar no se amortiza.
- La colección es suficientemente pequeña para que O(n) sea aceptable.
- Se busca una sola vez: ordenar para buscar una vez es siempre más caro que buscar linealmente.
- Se trabaja sobre una estructura sin acceso aleatorio (lista enlazada): la búsqueda binaria no es aplicable.

En estos casos no es una concesión: es la decisión óptima.

## ¿Cómo?

### Sobre colección sin orden

Versión iterativa:

```java
public static int buscar(int[] array, int objetivo) {
    for (int i = 0; i < array.length; i++) {
        if (array[i] == objetivo) {
            return i;
        }
    }
    return -1;
}
```

Versión recursiva:

```java
public static int buscar(int[] array, int objetivo, int indice) {
    if (indice >= array.length) {
        return -1;
    }
    if (array[indice] == objetivo) {
        return indice;
    }
    return buscar(array, objetivo, indice + 1);
}
```

La llamada inicial: `buscar(array, objetivo, 0)`.

Ambas versiones recorren la colección completa en el peor caso. La recursiva expresa directamente la estructura del problema: caso base (agotado el array) y paso recursivo (avanzar un elemento).

### Sobre colección con orden

Si se sabe que la colección está ordenada, se puede añadir una condición de parada temprana.

Versión iterativa:

```java
public static int buscarEnOrdenada(int[] array, int objetivo) {
    for (int i = 0; i < array.length; i++) {
        if (array[i] == objetivo) {
            return i;
        }
        if (array[i] > objetivo) {
            return -1;
        }
    }
    return -1;
}
```

Versión recursiva:

```java
public static int buscarEnOrdenada(int[] array, int objetivo, int indice) {
    if (indice >= array.length || array[indice] > objetivo) {
        return -1;
    }
    if (array[indice] == objetivo) {
        return indice;
    }
    return buscarEnOrdenada(array, objetivo, indice + 1);
}
```

La llamada inicial: `buscarEnOrdenada(array, objetivo, 0)`.

Si el elemento actual supera al objetivo, el objetivo no puede estar más adelante. La búsqueda termina sin recorrer el resto. La complejidad sigue siendo O(n) en el peor caso, pero el promedio mejora.

### Algunas reflexiones

#### El orden ayuda, pero no cambia la complejidad

En una lista ordenada, la búsqueda lineal con parada temprana encuentra la ausencia de un elemento más rápido que sin orden. Pero el elemento buscado puede estar al final: O(n) sigue siendo el peor caso. El orden es una mejora práctica, no una garantía asintótica.

#### Una sola búsqueda siempre justifica la lineal

Si solo se busca una vez, cualquier inversión previa (ordenar, construir un árbol) cuesta más que la búsqueda lineal directa. La pregunta sobre el número de búsquedas previstas es determinante antes de elegir estrategia.

#### La lineal no es ingenua

En colecciones pequeñas, la búsqueda binaria puede ser más lenta en la práctica que la lineal: la localidad de caché, el coste de los saltos y la sobrecarga de la aritmética de índices hacen que el acceso secuencial sea más eficiente de lo que el análisis asintótico sugiere. Big O describe el comportamiento a gran escala; para n pequeño, las constantes importan.
