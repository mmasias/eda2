<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [Búsquedas](../busqueda/README.md) / [**Ordenación**](README.md) / [Índice externo](../indiceExterno.md)  
[Por comparación](porComparacion.md) / [Insertion sort](insertionSort.md) / [Merge sort](mergeSort.md) / [Quicksort](quickSort.md) / [Comparativa](comparativa.md) / [**Con conocimiento**](conConocimiento.md) / [Counting sort](countingSort.md) / [Radix sort](radixSort.md) / [Límite inferior](limiteInferior.md)</sub>

</div>

# Ordenación con conocimiento

Los algoritmos de comparación alcanzan O(n log n) porque ese es el mínimo inevitable del modelo de comparación:

- Comparar dos elementos da un bit de información
- Distinguir n! permutaciones requiere Ω(n log n) bits.

Ese límite no es una limitación de los algoritmos conocidos, es una propiedad del modelo.

Puede romperse saliendo de él. Si los datos tienen propiedades estructurales conocidas, esa información permite colocar cada elemento directamente en su posición sin compararlo con ningún otro.

## Counting sort y radix sort

<div align=center>

| Algoritmo | Complejidad | Espacio extra | Estable | Cuándo |
|-|:-:|:-:|:-:|-|
| [Counting sort](countingSort.md) | O(n + k) | O(n + k) | Sí | k pequeño respecto a n: bytes, calificaciones, rangos acotados |
| [Radix sort](radixSort.md) | O(d * (n + b)) | O(n + b) | Sí | k grande, valores de anchura fija: enteros, cadenas de longitud fija |

</div>

> *k = tamaño del rango de valores; d = número de dígitos; b = base.*

La estabilidad de counting sort depende del sentido del recorrido en la fase de colocación (derecha a izquierda). Radix sort la exige a su subrutina: sin esa garantía, cada pasada destruiría el orden acumulado por las anteriores.

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
