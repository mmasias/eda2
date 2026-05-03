<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [Búsquedas](../busqueda/README.md) / [**Ordenación**](README.md) / [Índice externo](../indiceExterno.md)  
[Por comparación](porComparacion.md) / [Insertion sort](insertionSort.md) / [**Merge sort**](mergeSort.md) / [Quicksort](quickSort.md) / [Comparativa](comparativa.md) / [Con conocimiento](conConocimiento.md) / [Counting sort](countingSort.md) / [Radix sort](radixSort.md) / [Límite inferior](limiteInferior.md)</sub>

</div>

# Merge sort

## ¿Por qué?

Insertion sort revela el techo del enfoque elemento a elemento: O(n²) en el caso general. Para romperlo se necesita una estrategia distinta. La observación clave es que dos subarrays ya ordenados pueden combinarse en un array ordenado en tiempo O(n), simplemente recorriéndolos en paralelo.

Eso abre una posibilidad: si se divide el array en mitades, se ordena cada mitad de forma independiente y se fusionan los resultados, el coste de cada fusión es O(n) y el número de niveles de división es O(log n). El resultado es O(n log n), independientemente del estado inicial de los datos.

## ¿Qué?

Merge sort es una aplicación directa del esquema de divide y vencerás estudiado en el módulo de recursividad: caso base (subarray de un elemento, ya ordenado por definición), reducción (dividir en dos mitades) y combinación (fusionar las dos mitades ya ordenadas).

La operación de fusión es el núcleo del algoritmo. Dados dos subarrays ordenados, se comparan sus primeros elementos y se toma el menor; se avanza en ese subarray y se repite hasta agotar ambos. El resultado es un array ordenado construido en una sola pasada.

## ¿Cómo?

### Versión recursiva (top-down)

Se calcula el punto medio, se ordena recursivamente la mitad izquierda, se ordena recursivamente la mitad derecha y se fusionan las dos mitades en el array original. La fusión recorre ambas mitades en paralelo tomando siempre el menor de los dos elementos en curso hasta agotar las dos.

```java
public static void ordenar(int[] array, int izquierda, int derecha) {
    if (izquierda >= derecha) {
        return;
    }
    int medio = izquierda + (derecha - izquierda) / 2;
    ordenar(array, izquierda, medio);
    ordenar(array, medio + 1, derecha);
    fusionar(array, izquierda, medio, derecha);
}

private static void fusionar(int[] array, int izquierda, int medio, int derecha) {
    int tamanoIzquierda = medio - izquierda + 1;         // elementos en [izquierda..medio]
    int tamanoDerecha = derecha - medio;                 // elementos en [medio+1..derecha]
    int[] mitadIzquierda = new int[tamanoIzquierda];
    int[] mitadDerecha = new int[tamanoDerecha];
    for (int i = 0; i < tamanoIzquierda; i++) {
        mitadIzquierda[i] = array[izquierda + i];
    }
    for (int i = 0; i < tamanoDerecha; i++) {
        mitadDerecha[i] = array[medio + 1 + i];
    }
    int i = 0;                  // cursor sobre mitadIzquierda
    int j = 0;                  // cursor sobre mitadDerecha
    int k = izquierda;          // posición de escritura en array, inicia en izquierda

    // Bucle principal: mientras ambos cursores tengan elementos pendientes,
    // se compara mitadIzquierda[i] con mitadDerecha[j].
    // El menor (o el izquierdo en caso de empate, garantizando estabilidad) se escribe en array[k]
    // y se avanza su cursor y k.
    while (i < tamanoIzquierda && j < tamanoDerecha) {
        if (mitadIzquierda[i] <= mitadDerecha[j]) {
            array[k] = mitadIzquierda[i];
            i++;
        } else {
            array[k] = mitadDerecha[j];
            j++;
        }
        k++;
    }

    // residuo de mitadIzquierda
    while (i < tamanoIzquierda) {
        array[k] = mitadIzquierda[i];
        i++;
        k++;
    }

    // residuo de mitadDerecha
    while (j < tamanoDerecha) {
        array[k] = mitadDerecha[j];
        j++;
        k++;
    }
}
```

Llamada inicial: `ordenar(array, 0, array.length - 1)`.

### Versión iterativa (bottom-up)

La versión recursiva desciende hasta los elementos individuales y fusiona al remontar. La versión iterativa invierte ese recorrido: en cada ronda se fusionan subarrays adyacentes del tamaño actual, empezando por pares de un elemento, luego de dos, luego de cuatro, duplicando en cada vuelta hasta superar el array completo. No hay pila de llamadas: el orden de procesamiento es explícito en los bucles.

```java
public static void ordenarIterativo(int[] array) {
    int n = array.length;
    for (int tamano = 1; tamano < n; tamano *= 2) {
        for (int izquierda = 0; izquierda < n - tamano; izquierda += 2 * tamano) {
            int medio = izquierda + tamano - 1;
            int derecha = Math.min(izquierda + 2 * tamano - 1, n - 1);
            fusionar(array, izquierda, medio, derecha);
        }
    }
}
```

La comparación entre las dos versiones confirma algo que el módulo de recursividad ya apuntaba: la recursión es un modo de expresar el algoritmo, no el algoritmo en sí. El proceso de fusionar subarrays de tamaño creciente existe con independencia de cómo se implemente.

### Algunas reflexiones

#### La garantía tiene un precio

Merge sort garantiza O(n log n) independientemente del estado de los datos. El precio es el espacio: fusionar dos mitades ordenadas requiere copias temporales de ambas, lo que supone O(n) de memoria adicional. No existe modo de fusionar en su lugar manteniendo la garantía de tiempo lineal en la fusión.

#### Es estable por construcción

En la fusión, cuando dos elementos son iguales (`mitadIzquierda[i] <= mitadDerecha[j]`), se toma primero el de la mitad izquierda. Eso preserva el orden relativo original de elementos iguales sin ningún esfuerzo adicional: la estabilidad emerge de la condición de comparación.

#### La versión iterativa revela la estructura real

La versión bottom-up hace explícito lo que la recursión oculta: el algoritmo es una secuencia de rondas de fusión sobre subarrays de tamaño creciente. La recursión es una forma elegante de expresar esa estructura, pero no es la estructura en sí. Implementar ambas versiones y verificar que producen el mismo resultado es un ejercicio que vale la pena.

## El coste del espacio

Merge sort resuelve la garantía de O(n log n) pero introduce O(n) de memoria adicional. En muchos contextos eso es aceptable. En otros, como sistemas embebidos o colecciones muy grandes, puede ser inaceptable.

La pregunta que abre el siguiente algoritmo: ¿puede alcanzarse O(n log n) sin ese coste de espacio? La respuesta es sí, con matices. Eso es [quicksort](quickSort.md).
