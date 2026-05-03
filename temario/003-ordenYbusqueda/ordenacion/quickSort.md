<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [Búsquedas](../busqueda/README.md) / [**Ordenación**](README.md) / [Índice externo](../indiceExterno.md)  
[Por comparación](porComparacion.md) / [Insertion sort](insertionSort.md) / [Merge sort](mergeSort.md) / [**Quicksort**](quickSort.md) / [Comparativa](comparativa.md) / [Con conocimiento](conConocimiento.md) / [Counting sort](countingSort.md) / [Radix sort](radixSort.md) / [Límite inferior](limiteInferior.md)</sub>

</div>

# Quicksort

## ¿Por qué?

Merge sort garantiza O(n log n) pero necesita O(n) de memoria adicional. La pregunta es si puede alcanzarse O(n log n) sin copias auxiliares, operando directamente sobre el array original.

Quicksort lo intenta mediante una estrategia distinta: en lugar de dividir el array por la mitad y fusionar, elige un elemento como pivote y reorganiza el array de modo que todos los menores queden a su izquierda y todos los mayores a su derecha. El pivote queda en su posición definitiva. Se recursa sobre las dos partes.

No hay fusión: la reorganización en torno al pivote ya produce el orden correcto en cada partición.

## ¿Qué?

El algoritmo tiene dos fases en cada llamada recursiva:

- **Partición**: reorganiza el subarray en torno al pivote. Después de la partición, el pivote está en su posición definitiva; todos los elementos a su izquierda son menores o iguales y todos a su derecha son mayores.
- **Recursión**: se ordena cada parte de forma independiente aplicando el mismo proceso.

La partición opera in-place mediante intercambios. No necesita arrays adicionales.

## ¿Cómo?

### Versión recursiva

Se toma el último elemento como pivote y se recorre el subarray de izquierda a derecha: los elementos menores o iguales al pivote se agrupan en la zona izquierda mediante intercambios. Al terminar el recorrido, el pivote se coloca en la frontera entre las dos zonas, quedando en su posición definitiva. Se repite el proceso de forma independiente sobre la parte izquierda y la derecha.

```java
public static void ordenar(int[] array, int izquierda, int derecha) {
    if (izquierda >= derecha) {
        return;
    }
    int indicePivote = particionar(array, izquierda, derecha);
    ordenar(array, izquierda, indicePivote - 1);    // el pivote ya está en su posición definitiva: se excluye
    ordenar(array, indicePivote + 1, derecha);
}

private static int particionar(int[] array, int izquierda, int derecha) {
    int pivote = array[derecha];        // pivote: último elemento (elección arbitraria, no obligatoria)
    int i = izquierda - 1;              // i: frontera de la zona <= pivote; empieza fuera del subarray
    for (int j = izquierda; j < derecha; j++) {     // j: cursor explorador; no llega al pivote
        if (array[j] <= pivote) {
            i++;                        // expande la zona <= pivote
            int temporal = array[i];
            array[i] = array[j];
            array[j] = temporal;
        }
    }
    // coloca el pivote en la frontera: primera posición de la zona > pivote
    int temporal = array[i + 1];
    array[i + 1] = array[derecha];
    array[derecha] = temporal;
    return i + 1;                       // i+1: posición definitiva del pivote
}
```

Llamada inicial: `ordenar(array, 0, array.length - 1)`.

### Algunas reflexiones

#### O(n log n) en promedio, no garantizado

Quicksort tiene O(n log n) en el caso medio pero O(n²) en el peor caso. El peor caso ocurre cuando el pivote cae sistemáticamente en un extremo, produciendo particiones de tamaño 0 y n-1 en lugar de aproximadamente n/2 y n/2.

Eso ocurre con el array ya ordenado si siempre se toma el último elemento como pivote: cada partición separa un solo elemento (el pivote) del resto. El árbol de recursión degenera en una lista de n niveles y el coste total es O(n²).

#### El pivote como problema de conocimiento

La elección del pivote es, en el fondo, un problema de conocimiento sobre los datos. Si se conociera el elemento mediano, la partición siempre dividiría en partes iguales y el coste sería O(n log n) garantizado. Como ese conocimiento no está disponible en general, se adoptan estrategias parciales:

- **Pivote aleatorio**: se elige una posición aleatoria. El peor caso existe pero requeriría una secuencia de elecciones adversas muy improbable.
- **Mediana de tres**: se toman el primer elemento, el central y el último, y se elige el del valor intermedio. Elimina el peor caso para arrays ya ordenados sin coste significativo.

Ambas estrategias son aplicaciones de conocimiento parcial: no se sabe cuál es el mediano, pero se puede evitar las elecciones más dañinas.

#### No es estable

La partición puede intercambiar elementos iguales que estaban en un orden relativo determinado. Si el orden de elementos iguales importa, merge sort es la alternativa.

#### Por qué suele ser más rápido en la práctica

Quicksort y merge sort tienen la misma complejidad media, O(n log n), pero quicksort suele ser más rápido en la práctica. La razón es la localidad de caché: la partición accede a la memoria de forma secuencial en ambas direcciones desde los extremos hacia el centro; la fusión de merge sort salta entre dos subarrays copiados en posiciones distintas. En hardware moderno, el acceso secuencial a memoria es significativamente más rápido que el acceso disperso.

## Con los tres algoritmos en perspectiva

Insertion sort, merge sort y quicksort cubren el espacio de la ordenación por comparación desde ángulos distintos. Es el momento de situarlos juntos y ver cómo se posicionan frente a otras alternativas. Ver [comparativa](comparativa.md).

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
