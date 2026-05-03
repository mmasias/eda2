<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [Búsquedas](../busqueda/README.md) / [**Ordenación**](README.md) / [Índice externo](../indiceExterno.md)  
[Por comparación](porComparacion.md) / [Insertion sort](insertionSort.md) / [Merge sort](mergeSort.md) / [Quicksort](quickSort.md) / [**Comparativa**](comparativa.md) / [Con conocimiento](conConocimiento.md) / [Counting sort](countingSort.md) / [Radix sort](radixSort.md) / [Límite inferior](limiteInferior.md)</sub>

</div>

# Ordenación por comparación

Los tres algoritmos de ordenación por comparación estudiados definen un espacio de compromisos: complejidad garantizada frente a complejidad media, espacio adicional frente a operación *in situ* y estabilidad frente a velocidad práctica. Esto los sitúa en el espacio y sirve de referencia para posicionar cualquier otro algoritmo que se incorpore.

## Los tres algoritmos de referencia

### Núcleos algorítmicos

El fragmento que define a cada algoritmo:

**Insertion sort** - insertar el elemento actual en el prefijo ya ordenado:
```java
int actual = array[i];
int j = i - 1;
while (j >= 0 && array[j] > actual) {
    array[j + 1] = array[j];
    j--;
}
array[j + 1] = actual;
```

**Merge sort** - dividir, ordenar cada mitad y fusionar:
```java
int medio = izquierda + (derecha - izquierda) / 2;
ordenar(array, izquierda, medio);
ordenar(array, medio + 1, derecha);
fusionar(array, izquierda, medio, derecha);
```

**Quicksort** - particionar en torno al pivote y recursar:
```java
int indicePivote = particionar(array, izquierda, derecha);
ordenar(array, izquierda, indicePivote - 1);
ordenar(array, indicePivote + 1, derecha);
```

### Tabla de posicionamiento

<div align=center>

| Algoritmo | Mejor caso | Caso medio | Peor caso | Espacio extra | Estable | Cuándo |
|-|:-:|:-:|:-:|:-:|:-:|-|
| [Insertion sort](insertionSort.md) | O(n) | O(n²) | O(n²) | O(1) | Sí | Datos casi ordenados o n pequeño |
| [Merge sort](mergeSort.md) | O(n log n) | O(n log n) | O(n log n) | O(n) | Sí | Se necesita garantía o estabilidad |
| [Quicksort](quickSort.md) | O(n log n) | O(n log n) | O(n²) | O(log n)* | No | Caso general, grandes volúmenes |

</div>

*Espacio de pila de llamadas en el caso medio; O(n) en el peor caso.

La tabla responde a las dos preguntas que el README dejó abiertas:

<div align=center>

|¿Cuánta memoria puedo usar?|¿Importa el orden de elementos iguales?|
|-|-|
Si el espacio es limitado, merge sort queda fuera. Insertion sort y quicksort operan sobre el array original.|Si sí, insertion sort o merge sort. Quicksort puede alterar el orden relativo de elementos con el mismo valor.

</div>

## Otros algoritmos en este espacio

Los tres anteriores son el marco de referencia. Otros algoritmos de ordenación por comparación se posicionan en relación a ellos:

<div align=center>

| Algoritmo | Mejor caso | Caso medio | Peor caso | Espacio extra | Estable | Se diferencia en... |
|-|:-:|:-:|:-:|:-:|:-:|-|
| Heap sort | O(n log n) | O(n log n) | O(n log n) | O(1) | No | Garantía de merge sort sin su espacio; más lento en la práctica que quicksort |
| Timsort | O(n) | O(n log n) | O(n log n) | O(n) | Sí | Híbrido insertion + merge; explota runs ya ordenados; usado en Java y Python |
| Introsort | O(n log n) | O(n log n) | O(n log n) | O(log n) | No | Híbrido quicksort + heapsort; cambia a heapsort si la recursión se profundiza demasiado |

</div>

Cada algoritmo se entiende como una variación o combinación de los tres de referencia.

- Timsort es merge sort que aprovecha insertion sort en subarrays pequeños o casi ordenados.
- Introsort es quicksort con un seguro de O(n log n) en el peor caso.
