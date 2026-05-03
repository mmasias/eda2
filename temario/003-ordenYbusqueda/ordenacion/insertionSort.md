<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [Búsquedas](../busqueda/README.md) / [**Ordenación**](README.md) / [Índice externo](../indiceExterno.md)  
[Por comparación](porComparacion.md) / [**Insertion sort**](insertionSort.md) / [Merge sort](mergeSort.md) / [Quicksort](quickSort.md) / [Comparativa](comparativa.md) / [Con conocimiento](conConocimiento.md) / [Counting sort](countingSort.md) / [Radix sort](radixSort.md) / [Límite inferior](limiteInferior.md)</sub>

</div>

# Insertion sort

## ¿Por qué?

Partimos de O(n log n) como coste de referencia conocido: insertar n elementos en un BST uno a uno cuesta eso. La pregunta es si puede ordenarse un array existente con un enfoque más directo, sin construir ninguna estructura auxiliar.

Insertion sort es ese enfoque. No divide, no construye nada aparte: toma cada elemento y lo coloca en su posición correcta dentro de la parte ya ordenada. Es el algoritmo más cercano a lo que una persona hace intuitivamente al ordenar un mazo de cartas.

## ¿Qué?

Mantiene un prefijo ordenado que crece de izquierda a derecha. En cada paso, toma el primer elemento del segmento sin ordenar y lo inserta en la posición correcta dentro del prefijo, desplazando hacia la derecha los elementos que lo superan.

## ¿Cómo?

### Versión iterativa

Se recorre el array desde el segundo elemento. Por cada elemento, se guarda su valor y se desplazan una posición a la derecha todos los elementos del prefijo que lo superan. Cuando no quedan más elementos que desplazar, se coloca el valor guardado en el hueco resultante.

```java
public static void ordenar(int[] array) {
    for (int i = 1; i < array.length; i++) {
        int actual = array[i];           // se guarda antes de que los desplazamientos sobreescriban array[i]
        int j = i - 1;                   // cursor sobre el prefijo ordenado, empieza en su extremo derecho
        while (j >= 0 && array[j] > actual) {  // > y no >= : iguales no se desplazan, lo que garantiza estabilidad
            array[j + 1] = array[j];           // desplazamiento a la derecha; no es un intercambio
            j--;
        }
        array[j + 1] = actual;           // j quedó una posición a la izquierda del hueco
    }
}
```

### Versión recursiva

Ordenar los primeros n elementos equivale a ordenar los primeros n-1 e insertar el n-ésimo en su posición. Se ordena el subarray reducido en una llamada recursiva, se guarda el último elemento y se desplazan hacia la derecha los del prefijo que lo superan; el elemento guardado ocupa el hueco resultante.

```java
public static void ordenar(int[] array, int n) {
    if (n <= 1) {
        return;
    }
    ordenar(array, n - 1);
    int ultimo = array[n - 1];    // se guarda antes de que los desplazamientos sobreescriban array[n-1]
    int j = n - 2;                // cursor sobre el prefijo ya ordenado [0..n-2]
    while (j >= 0 && array[j] > ultimo) {
        array[j + 1] = array[j];
        j--;
    }
    array[j + 1] = ultimo;        // j quedó una posición a la izquierda del hueco
}
```

Llamada inicial: `ordenar(array, array.length)`.

### Algunas reflexiones

#### La complejidad no es fija

Insertion sort no tiene una complejidad única: depende del estado de los datos de entrada.

<div align=center>

| Estado de los datos | Comparaciones | Complejidad |
|-|:-:|:-:|
| Ordenados | n - 1 | O(n) |
| Inversamente ordenados | n(n-1)/2 | O(n²) |
| Aleatorios | ~n²/4 en promedio | O(n²) |

</div>

Si los datos están casi ordenados, insertion sort puede superar en la práctica a algoritmos asintóticamente mejores. La complejidad no es una propiedad del algoritmo: es una función del algoritmo y los datos.

#### El desplazamiento es el coste real

El bucle interno no solo compara: desplaza. Cada elemento mayor que `actual` se mueve una posición a la derecha. En el peor caso, cada inserción desplaza todos los elementos ya ordenados. Ese desplazamiento, O(n) por elemento en el peor caso, es lo que eleva el coste total a O(n²).

#### Es estable sin esfuerzo adicional

Dos elementos con el mismo valor no se intercambian nunca: el bucle interno solo desplaza cuando el elemento del prefijo es estrictamente mayor. El orden relativo original se preserva de forma automática.

## El techo del enfoque simple

Insertion sort actúa sobre el array de uno en uno. No hay modo de evitar que, en el caso general, cada inserción cueste O(n): la posición correcta puede estar al principio del prefijo. Multiplicado por n elementos, el resultado es O(n²).

Para romper ese techo se necesita una estrategia distinta: en lugar de tratar los elementos uno a uno, dividir el problema en partes y combinar los resultados. Eso es lo que hace [merge sort](mergeSort.md).
