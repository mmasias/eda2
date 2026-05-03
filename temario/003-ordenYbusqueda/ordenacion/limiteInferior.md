<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [Búsquedas](../busqueda/README.md) / [**Ordenación**](README.md) / [Índice externo](../indiceExterno.md)  
[Por comparación](porComparacion.md) / [Insertion sort](insertionSort.md) / [Merge sort](mergeSort.md) / [Quicksort](quickSort.md) / [Comparativa](comparativa.md) / [Con conocimiento](conConocimiento.md) / [Counting sort](countingSort.md) / [Radix sort](radixSort.md) / [**Límite inferior**](limiteInferior.md)</sub>

</div>

# Límite inferior

## Perspectiva de conjunto

Llegados a este punto, los cinco algoritmos del módulo:

<div align=center>

| Algoritmo | Familia | Mejor | Caso medio | Peor | Espacio | Estable | Cuándo |
|-|-|:-:|:-:|:-:|:-:|:-:|-|
| Insertion sort | Comparación | O(n) | O(n²) | O(n²) | O(1) | Sí | Datos casi ordenados o n pequeño |
| Merge sort | Comparación | O(n log n) | O(n log n) | O(n log n) | O(n) | Sí | Se necesita garantía o estabilidad |
| Quicksort | Comparación | O(n log n) | O(n log n) | O(n²) | O(log n)* | No | Caso general, grandes volúmenes |
| Counting sort | Conocimiento | O(n + k) | O(n + k) | O(n + k) | O(n + k) | Sí | k pequeño respecto a n |
| Radix sort | Conocimiento | O(d·(n + b)) | O(d·(n + b)) | O(d·(n + b)) | O(n + b) | Sí | Enteros o cadenas de longitud fija |

</div>

*Espacio de pila en el caso medio; O(n) en el peor caso. k = rango de valores; d = número de dígitos; b = base.

Los algoritmos de comparación convergen en O(n log n). Los de conocimiento llegan a O(n). La pregunta que surge al verlos juntos: ¿es ese O(n log n) un techo inevitable para cualquier algoritmo de comparación, o podría existir uno mejor aún no descubierto?

## ¿Por qué?

Insertion sort, merge sort y quicksort alcanzan O(n log n). La coincidencia puede interpretarse de dos formas: o son los mejores algoritmos conocidos hasta ahora y quizás existe uno mejor aún no descubierto, o O(n log n) es el mínimo inevitable del modelo y ningún algoritmo de comparación puede hacer menos.

Es la segunda. Este anexo demuestra por qué.

## El modelo: árbol de decisión

Cualquier algoritmo que ordena solo mediante comparaciones puede representarse como un árbol binario llamado árbol de decisión:

- Cada nodo interno es una pregunta: ¿a[i] <= a[j]?
- Cada rama es la respuesta: sí (izquierda) o no (derecha).
- Cada hoja es un orden completo de todos los elementos: la permutación que el algoritmo devuelve para esa secuencia de respuestas.

El algoritmo sigue un camino desde la raíz hasta una hoja. La longitud de ese camino es el número de comparaciones realizadas. La altura del árbol es el número de comparaciones en el peor caso.

**Caso concreto: n = 3**

Para tres elementos [a, b, c] hay 3! = 6 permutaciones posibles. Un árbol correcto debe tener al menos 6 hojas distintas, una por cada posible ordenación de la entrada. Un árbol binario de altura 2 tiene como máximo 4 hojas, lo que no es suficiente. La altura mínima es 3, ya que 2³ = 8 >= 6. Ningún algoritmo puede ordenar 3 elementos con menos de 3 comparaciones en el peor caso. Merge sort lo hace en exactamente 3.

## La cota

El argumento general sigue la misma lógica. Para n elementos hay n! permutaciones, luego el árbol necesita al menos n! hojas. Un árbol binario de altura h tiene a lo sumo 2^h hojas. Combinando:

```
2^h >= n!
h >= log2(n!)
```

Solo falta probar que log2(n!) = Ω(n log n).

```
log2(n!) = log2(1) + log2(2) + ... + log2(n)
```

Tomando solo los n/2 términos mayores (la segunda mitad de la suma):

```
log2(n!) >= log2(n/2 + 1) + log2(n/2 + 2) + ... + log2(n)
          >= (n/2) * log2(n/2)
           = (n/2) * (log2(n) - 1)
           = Ω(n log n)
```

Por tanto h = Ω(n log n). La altura del árbol es el número de comparaciones en el peor caso. Cualquier algoritmo de comparación tiene un árbol de decisión; ese árbol tiene altura al menos Ω(n log n); luego el peor caso de cualquier algoritmo de comparación es Ω(n log n).

## Qué dice y qué no dice

**Lo que dice**: para cualquier algoritmo que ordena solo por comparaciones, existe al menos una entrada de tamaño n que obliga a realizar Ω(n log n) comparaciones. No hay escapatoria en el peor caso.

**Lo que no dice**: no afirma que todas las entradas sean igualmente difíciles. Insertion sort tarda O(n) en datos casi ordenados; eso es completamente compatible con el resultado, porque ese resultado habla del peor caso. Lo que el límite garantiza es que no puede existir ningún algoritmo de comparación que evite el peor caso Ω(n log n) para toda entrada posible.

**Por qué counting sort y radix sort no lo violan**: el argumento se construye sobre el modelo de comparación, donde la única información que el algoritmo extrae de los datos es el resultado de comparar dos elementos. Counting sort y radix sort no comparan elementos entre sí: operan sobre la representación de los valores, que contiene más información que una comparación. Al salir del modelo, el límite deja de aplicar.

El resultado no condena los algoritmos con conocimiento; simplemente delimita con precisión qué puede y qué no puede conseguirse dentro del modelo de comparación.

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
