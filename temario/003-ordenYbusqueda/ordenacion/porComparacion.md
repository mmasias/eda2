<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [Búsquedas](../busqueda/README.md) / [**Ordenación**](README.md) / [Índice externo](../indiceExterno.md)  
[**Por comparación**](porComparacion.md) / [Insertion sort](insertionSort.md) / [Merge sort](mergeSort.md) / [Quicksort](quickSort.md) / [Comparativa](comparativa.md) / [Con conocimiento](conConocimiento.md) / [Counting sort](countingSort.md) / [Radix sort](radixSort.md) / [Límite inferior](limiteInferior.md)</sub>

</div>

# Ordenación por comparación

El único instrumento disponible es una comparación: dado `a[i]` y `a[j]`, ¿cuál es menor? Cada comparación devuelve un bit de información sobre el orden relativo de dos elementos. El algoritmo no puede leer sus valores de otro modo: no puede indexarlos ni usarlos como posiciones. Solo puede compararlos.

De esa restricción nacen tres estrategias, cada una con su lógica propia:

## Las tres estrategias

<div align=center>

| Estrategia | Idea central | Algoritmo |
|-|-|-|
| Inserción | Mantener un prefijo ordenado que crece elemento a elemento | [Insertion sort](insertionSort.md) |
| Dividir y fusionar | Partir en mitades, ordenar cada una de forma independiente y combinar | [Merge sort](mergeSort.md) |
| Dividir por partición | Elegir un pivote, reorganizar en torno a él y recursar sobre cada parte | [Quicksort](quickSort.md) |

</div>

Las tres llegan al mismo resultado. Lo que las diferencia es cómo responden a las preguntas que el [README](README.md) dejó abiertas: ¿cuánta memoria puedo usar? ¿importa el orden de elementos iguales? Esos compromisos, una vez vistos los tres, se recogen en la [comparativa](comparativa.md).

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
