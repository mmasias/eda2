<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [Búsquedas](../busqueda/README.md) / [**Ordenación**](README.md) / [Índice externo](../indiceExterno.md)  
[Por comparación](porComparacion.md) / [Insertion sort](insertionSort.md) / [Merge sort](mergeSort.md) / [Quicksort](quickSort.md) / [Comparativa](comparativa.md) / [Con conocimiento](conConocimiento.md) / [Counting sort](countingSort.md) / [**Radix sort**](radixSort.md) / [Límite inferior](limiteInferior.md)</sub>

</div>

# Radix sort

## ¿Por qué?

Un entero de seis dígitos decimales puede tener hasta 999.999 valores distintos: k demasiado grande para counting sort directo. Pero cada uno de sus dígitos solo puede valer entre 0 y 9. Si se ordena el array por el dígito de las unidades (k = 10), después por el de las decenas (k = 10) y así hasta el dígito más significativo, usando en cada pasada un counting sort estable, el resultado final es el array completamente ordenado.

El coste es O(d * (n + b)), donde d es el número de dígitos y b es la base (10 para dígitos decimales). Si d y b son constantes respecto a n, el coste es O(n).

## ¿Qué?

Radix sort procesa los valores posición a posición, de la menos significativa a la más significativa. En cada posición aplica counting sort sobre el dígito en esa posición. La estabilidad de counting sort es imprescindible: garantiza que el orden establecido por pasadas anteriores se preserve dentro de cada grupo de elementos con el mismo dígito actual.

## ¿Cómo?

El algoritmo es una secuencia de pasadas, una por posición de dígito. Las pasadas son independientes en cuanto a diseño pero acumulativas en resultado: el orden parcial construido en cada pasada sirve de base para la siguiente, gracias a la estabilidad. No hay subestructura recursiva: el algoritmo no descompone el problema en subproblemas de menor tamaño, sino que lo refina en rondas sucesivas. No existe versión recursiva con sentido propio por la misma razón que en counting sort.

```java
public static void ordenar(int[] array, int valorMaximo) {
    for (int posicion = 1; valorMaximo / posicion > 0; posicion *= 10) {
        ordenarPorDigito(array, posicion);
    }
}

private static void ordenarPorDigito(int[] array, int posicion) {
    int base = 10;
    int[] conteo = new int[base];
    int[] resultado = new int[array.length];
    for (int i = 0; i < array.length; i++) {
        conteo[(array[i] / posicion) % base]++;
    }
    for (int i = 1; i < base; i++) {
        conteo[i] += conteo[i - 1];
    }
    for (int i = array.length - 1; i >= 0; i--) {
        int digito = (array[i] / posicion) % base;
        resultado[conteo[digito] - 1] = array[i];
        conteo[digito]--;
    }
    for (int i = 0; i < array.length; i++) {
        array[i] = resultado[i];
    }
}
```

Llamada: `ordenar(array, valorMaximo)`.

### Traza

Array: `[170, 45, 75, 90, 802, 24, 2, 66]`, valorMaximo = 802 (3 dígitos).

```
Pasada 1 - dígito de las unidades (posicion = 1):
  Dígitos: 170->0  45->5  75->5  90->0  802->2  24->4  2->2  66->6
  Resultado: [170, 90, 802, 2, 24, 45, 75, 66]

Pasada 2 - dígito de las decenas (posicion = 10):
  Dígitos: 170->7  90->9  802->0  2->0  24->2  45->4  75->7  66->6
  Resultado: [802, 2, 24, 45, 66, 170, 75, 90]

Pasada 3 - dígito de las centenas (posicion = 100):
  Dígitos: 802->8  2->0  24->0  45->0  66->0  170->1  75->0  90->0
  Resultado: [2, 24, 45, 66, 75, 90, 170, 802]
```

Después de la pasada 1, los elementos están agrupados por unidades. Dentro del grupo de unidades = 0 aparecen 170 y 90, en ese orden. La pasada 2 los ordena por decenas (7 vs 9) y los separa correctamente porque el counting sort estable respeta el orden previo entre elementos con el mismo dígito actual. Sin estabilidad en la pasada 2, ese orden relativo podría haberse invertido y el resultado final sería incorrecto.

### Algunas reflexiones

**La estabilidad no es opcional**

Si counting sort no fuera estable, el orden establecido en una pasada podría destruirse en la siguiente. El ejemplo lo muestra con 170 y 90: coinciden en el dígito de las unidades (ambos terminan en 0). La pasada 1 los coloca en ese orden. La pasada 2 los ordena por decenas (7 vs 9) y los separa porque respeta el orden previo dentro del grupo de decenas = 0. Un sort inestable podría haberlos reordenado al azar, produciendo un resultado erróneo.

**Por qué de menos significativo a más significativo**

Procesar de menos a más significativo (LSD, least significant digit first) permite que cada pasada refine el orden sin destruir lo anterior: la estabilidad garantiza que dentro de un grupo de igual dígito actual, el orden de la pasada previa se preserva. Procesar de más a menos significativo (MSD, most significant digit first) requeriría una estrategia diferente: particionar el array por el dígito más significativo y recursar sobre cada partición de forma independiente. Eso convierte el algoritmo en algo más parecido a quicksort por dígitos, con mayor complejidad de implementación.

**La base como parámetro de ajuste**

Con base 10 y enteros de hasta 3 dígitos, radix sort hace 3 pasadas con k = 10. Con base 256 (un byte) y enteros de 32 bits, hace 4 pasadas con k = 256. Aumentar la base reduce el número de pasadas pero aumenta el array de conteos. El punto óptimo depende del tamaño de caché del hardware y de n. En implementaciones de alto rendimiento, base 256 es habitual.

**El coste real**

O(d * (n + b)). Para enteros de 32 bits con base 256: d = 4, b = 256. Para n grande, el término dominante es 4n, es decir O(n). Para n pequeño, la constante oculta puede hacer que quicksort sea más rápido en la práctica, incluso con peor complejidad asintótica.

Counting sort y radix sort comparten algo esencial: ninguno compara elementos entre sí. Operan sobre la representación de los valores, no sobre su orden relativo. Eso es precisamente lo que les permite romper el límite de O(n log n). Y eso plantea la pregunta inversa: si la única herramienta disponible es la comparación, sin ningún conocimiento adicional sobre los datos, ¿puede hacerse mejor que O(n log n)? La respuesta es no: la demostración es más elegante que la mayoría de los resultados en algoritmia. Ver [límite inferior](limiteInferior.md).

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
