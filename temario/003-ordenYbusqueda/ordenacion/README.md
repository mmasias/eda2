<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [Búsquedas](../busqueda/README.md) / [**Ordenación**](README.md) / [Índice externo](../indiceExterno.md)  
[Por comparación](porComparacion.md) / [Insertion sort](insertionSort.md) / [Merge sort](mergeSort.md) / [Quicksort](quickSort.md) / [Comparativa](comparativa.md) / [Con conocimiento](conConocimiento.md) / [Counting sort](countingSort.md) / [Radix sort](radixSort.md) / [Límite inferior](limiteInferior.md)</sub>

</div>

# Ordenación

## ¿Por qué?

- La **construcción** con orden garantiza búsqueda eficiente, pero exige controlar cómo llegan los datos.
- La **búsqueda** sobre datos sin orden es O(n). Con orden y acceso aleatorio, es O(log n).
- Cuando los datos llegaron sin orden y no hubo control sobre su llegada, aparece la pregunta: ***¿vale la pena ordenarlos?***

La respuesta depende de cuántas búsquedas se vayan a realizar: si son suficientes, el coste de ordenar una vez se amortiza frente a buscar linealmente cada vez.

Pero nuevamente: ¿cuánto cuesta ordenar? Tenemos una respuesta concreta desde el módulo de construcción: insertar n elementos en un BST, uno a uno, cuesta O(n log n). Si los datos llegan sin orden y queremos imponerles uno, podemos reconstruirlos en un BST y extraerlos en orden mediante un recorrido in-order. Eso ya funciona y sabemos su coste.

Por tanto, partimos desde el conocido O(n log n). Pero este escenario tiene un punto no menor: requiere crear otra estructura y espacio para construirla. Y nos podemos preguntar: ¿puede ordenarse sin necesidad de una estructura separada y manteniendo el coste? ¿Y reduciéndolo?

## ¿Qué?

||||
|-|-|-|
|Ordenar una colección es **imponer un invariante sobre datos que ya existen**.|A diferencia de la construcción, que mantiene el orden como propiedad emergente de cada inserción, la ordenación actúa sobre un conjunto completo y estático: toma lo que hay y **lo reorganiza**.|El resultado es siempre el mismo: una permutación de los elementos originales en la que cada elemento ocupa la posición que le corresponde según el criterio de comparación elegido.|

No todos los algoritmos llegan a ese resultado del mismo modo. Antes de elegir uno, hay tres preguntas sobre el contexto:

- **¿Qué sé de mis datos?**
- **¿Cuánta memoria puedo usar?**
- **¿Importa el orden de elementos iguales?**

La primera determina qué familia de algoritmos es aplicable. Las otras dos afinan la elección dentro de esa familia.

## ¿Para qué?

El beneficio directo es la búsqueda binaria: un array ordenado admite O(log n) por consulta. Pero la ordenación también habilita otras operaciones que la búsqueda no menciona:

- **Consultas por rango**: encontrar todos los elementos entre dos valores es O(log n + resultado) en un array ordenado, frente a O(n) sin orden.
- **Deduplicación eficiente**: los duplicados quedan adyacentes; detectarlos es O(n) tras ordenar.
- **Fusión de colecciones**: dos arrays ordenados se combinan en O(n + m); sin orden, no hay atajos.

El coste de ordenar se amortiza sobre todas estas operaciones, no solo sobre la búsqueda.

## ¿Cómo?

La pregunta organizadora es la misma que en búsqueda: **¿qué sé de mis datos?**

<div align=center>

| Conocimiento disponible | Estrategia | Complejidad | |
|-|-|:-:|-|
| Solo puedo comparar elementos | [Ordenación por comparación](porComparacion.md) | O(n log n) | Insertion sort, merge sort, quicksort |
| Conozco el rango o la estructura de los valores | [Ordenación con conocimiento](conConocimiento.md) | O(n) | Counting sort, radix sort |

</div>

### Por qué O(n log n) y no menos

Los algoritmos de comparación no pueden superar O(n log n) en el caso general. No es una limitación de los algoritmos conocidos: es un límite teórico del modelo. Cuándo y por qué, en el [anexo sobre el límite inferior](limiteInferior.md).

### Cuándo la ordenación no es la respuesta

La ordenación resuelve el problema cuando:

- Los datos caben en memoria.
- Existe un criterio de comparación natural.
- La colección no cambia con frecuencia tras ser ordenada.

Cuando alguna de estas condiciones falla, ordenar no es la herramienta adecuada. Si los datos no tienen un orden natural pero sí una clave de acceso, se puede construir un índice que mapee claves a posiciones sin imponer ningún orden sobre los datos. Si la colección es demasiado grande para caber en memoria, la reorganización física de los elementos tiene un coste que puede no justificarse. En esos casos, la pregunta no es *cómo ordenar mejor*, sino *si ordenar es el enfoque correcto*.