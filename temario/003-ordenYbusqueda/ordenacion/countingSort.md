<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [Búsquedas](../busqueda/README.md) / [**Ordenación**](README.md) / [Índice externo](../indiceExterno.md)  
[Por comparación](porComparacion.md) / [Insertion sort](insertionSort.md) / [Merge sort](mergeSort.md) / [Quicksort](quickSort.md) / [Comparativa](comparativa.md) / [Con conocimiento](conConocimiento.md) / [**Counting sort**](countingSort.md) / [Radix sort](radixSort.md) / [Límite inferior](limiteInferior.md)</sub>

</div>

# Counting sort

## ¿Por qué?

Si todos los valores del array están en el rango [0, k), se puede contar cuántos elementos tiene cada valor sin comparar ninguno entre sí. Una vez conocidos los conteos, se sabe exactamente cuántos elementos van antes de cada posición: los elementos con valor v ocupan las posiciones [prefijo, prefijo + conteo[v]) en el resultado ordenado. El array se reconstruye en una pasada adicional.

El coste es O(n + k): una pasada sobre el array de entrada (n elementos) y una pasada sobre el array de conteos (k valores posibles). Cuando k es pequeño respecto a n, esto supera a O(n log n). Para k = 10 (dígitos decimales), es lineal en n.

## ¿Qué?

El algoritmo tiene tres fases:

1. **Contar**: recorrer el array de entrada y acumular en `conteo[v]` cuántos elementos tienen el valor v.
2. **Acumular**: transformar `conteo` en un array de posiciones finales. Después de `conteo[i] += conteo[i-1]`, el valor `conteo[v]` indica cuántos elementos tienen valor menor o igual a v, lo que equivale a la posición de destino del último elemento con valor v en el array de salida.
3. **Colocar**: recorrer el array de entrada de derecha a izquierda, colocar cada elemento en la posición `conteo[v] - 1` y decrementar `conteo[v]`. Recorrer de derecha a izquierda garantiza que elementos con el mismo valor mantengan su orden relativo original: el algoritmo es estable.

## ¿Cómo?

El algoritmo opera en tres pasadas lineales con dependencias secuenciales: la acumulación necesita los conteos completos, la colocación necesita la acumulación completa. No hay un subproblema de tamaño reducido que resolver de forma independiente. A diferencia de insertion sort, donde "ordenar n elementos" se descompone en "ordenar n-1 e insertar el último", o de merge sort, donde el problema se divide genuinamente en mitades, counting sort no tiene una formulación recursiva que aporte comprensión adicional. No existe versión recursiva con sentido propio.

```java
public static void ordenar(int[] array, int valorMaximo) {
    int[] conteo = new int[valorMaximo + 1];
    for (int i = 0; i < array.length; i++) {
        conteo[array[i]]++;
    }
    for (int i = 1; i <= valorMaximo; i++) {
        conteo[i] += conteo[i - 1];
    }
    int[] resultado = new int[array.length];
    for (int i = array.length - 1; i >= 0; i--) {
        resultado[conteo[array[i]] - 1] = array[i];
        conteo[array[i]]--;
    }
    for (int i = 0; i < array.length; i++) {
        array[i] = resultado[i];
    }
}
```

Llamada: `ordenar(array, valorMaximo)`.

### Traza

Array: `[3, 1, 2, 4, 1, 3]`, valorMaximo = 4.

```
Fase 1 - contar:
  Recorrido de izquierda a derecha:
  conteo = [0, 2, 1, 2, 1]   (índices 0..4)

Fase 2 - acumular (conteo[i] += conteo[i-1]):
  conteo = [0, 2, 3, 5, 6]
  (conteo[v] = número de elementos con valor <= v = posición del último v en el resultado)

Fase 3 - colocar (de derecha a izquierda):
  i=5, valor=3: resultado[conteo[3]-1 = 4] = 3,  conteo[3] = 4
  i=4, valor=1: resultado[conteo[1]-1 = 1] = 1,  conteo[1] = 1
  i=3, valor=4: resultado[conteo[4]-1 = 5] = 4,  conteo[4] = 5
  i=2, valor=2: resultado[conteo[2]-1 = 2] = 2,  conteo[2] = 2
  i=1, valor=1: resultado[conteo[1]-1 = 0] = 1,  conteo[1] = 0
  i=0, valor=3: resultado[conteo[3]-1 = 3] = 3,  conteo[3] = 3

Resultado: [1, 1, 2, 3, 3, 4]
```

Los dos elementos con valor 1 aparecen en el resultado en el mismo orden relativo en que estaban en la entrada (posición 1 antes que posición 4). La estabilidad emerge del recorrido de derecha a izquierda en la fase de colocación.

### Algunas reflexiones

**El factor k como límite práctico**

Counting sort cuesta O(n + k) en tiempo y requiere O(n + k) de espacio. Si k es pequeño respecto a n, supera claramente a los algoritmos de comparación. Si k crece, el coste del array de conteos domina: para k = n², el coste es O(n²), peor que quicksort. La utilidad práctica está en rangos acotados y conocidos: códigos postales, calificaciones, edades, valores de bytes (k = 256).

**Solo para valores discretos**

El array de conteos requiere indexar directamente por valor. Eso solo es posible con enteros o con valores discretos mapeables a enteros mediante una función de clave. Counting sort no generaliza a números reales arbitrarios ni a objetos con criterios de comparación complejos.

**La estabilidad no es gratuita**

La estabilidad depende de recorrer el array de entrada de derecha a izquierda en la fase de colocación. Si se recorre de izquierda a derecha, el algoritmo sigue funcionando para valores simples, pero deja de ser estable: los elementos iguales se colocan en orden inverso al original. Cuando counting sort se usa como subrutina dentro de radix sort, la estabilidad es imprescindible.

Counting sort resuelve el problema cuando k es manejable. La pregunta es qué ocurre cuando k no lo es: con enteros de 32 bits, k = 4.294.967.296; el array de conteos requeriría 16 GB. Inviable. [Radix sort](radixSort.md) responde a ese caso.

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
