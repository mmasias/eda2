<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [**Búsquedas**](../busqueda/README.md) / [Ordenación](../ordenacion/README.md) / [Índice externo](../indiceExterno.md)  </sub>  
[Búsqueda lineal](lineal.md) / [**Búsqueda binaria**](binaria.md) / [Búsqueda en BST](bst.md)

</div>

# Búsqueda binaria

## ¿Por qué?

La búsqueda lineal visita cada elemento. Para n = 10⁶, eso puede ser 10⁶ comparaciones. La búsqueda binaria descarta la mitad de los elementos restantes en cada paso. Para n = 10⁶, son como máximo 20 comparaciones.

La diferencia no es de algoritmo: es de información. La búsqueda binaria no es más inteligente en abstracto. Es la estrategia correcta cuando se sabe que la colección está ordenada y se puede acceder a cualquier posición directamente.

## ¿Qué?

La búsqueda binaria trabaja sobre un intervalo activo `[izquierda, derecha]`. En cada paso, examina el elemento central y decide en qué mitad puede estar el objetivo:

- Si el central es el objetivo: encontrado.
- Si el central es menor que el objetivo: el objetivo solo puede estar a la derecha.
- Si el central es mayor que el objetivo: el objetivo solo puede estar a la izquierda.

El intervalo se reduce a la mitad en cada comparación. Tras k pasos, quedan n/2ᵏ elementos. El proceso termina cuando el intervalo está vacío o se encuentra el objetivo.

Precondiciones no negociables:

- **Orden**: los elementos deben estar ordenados por el mismo criterio que se usa para comparar.
- **Acceso aleatorio**: se debe poder acceder al elemento central en O(1). Un array lo garantiza, una lista enlazada no: llegar al centro requiere recorrerla, y el beneficio desaparece.

## ¿Para qué?

La búsqueda binaria es la elección correcta cuando:

- Se realizan múltiples búsquedas sobre la misma colección ordenada.
- La colección es grande y O(n) por búsqueda no es aceptable.
- El coste de ordenar (o de construir con invariante de orden) ya fue pagado o se amortizará.

No es adecuada cuando:

- Los datos no tienen orden garantizado.
- La estructura no ofrece acceso aleatorio.
- Se realiza una sola búsqueda: buscar linealmente es siempre más barato que ordenar y luego buscar binariamente.

## ¿Cómo?

### Versión iterativa

```java
public static int buscar(int[] array, int objetivo) {
    int izquierda = 0;
    int derecha = array.length - 1;

    while (izquierda <= derecha) {
        int medio = izquierda + (derecha - izquierda) / 2;  // evita desbordamiento

        if (array[medio] == objetivo) {
            return medio;
        } else if (array[medio] < objetivo) {
            izquierda = medio + 1;
        } else {
            derecha = medio - 1;
        }
    }
    return -1;
}
```

### Versión recursiva

```java
public static int buscar(int[] array, int objetivo, int izquierda, int derecha) {
    if (izquierda > derecha) {
        return -1;
    }

    int medio = izquierda + (derecha - izquierda) / 2;

    if (array[medio] == objetivo) {
        return medio;
    } else if (array[medio] < objetivo) {
        return buscar(array, objetivo, medio + 1, derecha);
    } else {
        return buscar(array, objetivo, izquierda, medio - 1);
    }
}
```

La llamada inicial: `buscar(array, objetivo, 0, array.length - 1)`.

Ambas versiones producen el mismo resultado. La iterativa es más eficiente en memoria (sin pila de llamadas); la recursiva expresa más directamente la estructura del problema.

La implementación real integrada en una estructura está en [`src/ordenYbusqueda/construccion/arrayConOrden/ArrayConOrden.java`](/src/ordenYbusqueda/construccion/arrayConOrden/ArrayConOrden.java). Vale la pena leerla: en el mismo archivo conviven las dos convenciones de intervalo. `buscar` usa el intervalo cerrado `[izquierda, derecha]`; `encontrarPosicion` (que localiza dónde insertar un nuevo elemento) usa el semiabierto `[izquierda, derecha)`. Es un ejemplo real de que ninguna convención es universal y de que la consistencia dentro de cada función es lo que importa.

### Algunas reflexiones

#### El cálculo del punto medio

La forma intuitiva de calcular el punto medio es `(izquierda + derecha) / 2`. Es incorrecta cuando `izquierda + derecha` supera el rango de `int` (puede ocurrir con arrays grandes). La forma correcta es:

```
medio = izquierda + (derecha - izquierda) / 2
```

Produce el mismo resultado aritmético sin riesgo de desbordamiento.

#### La precondición no es gratuita

O(log n) es la recompensa de una inversión previa. Alguien pagó ese coste:

- Si el array se ordenó antes de buscar: O(n log n) de ordenación.
- Si se construyó con un invariante de orden (BST, árbol balanceado): O(log n) por inserción.

La búsqueda binaria no elimina ese coste: lo presupone. La decisión de cuándo pagarlo depende del número de búsquedas previstas.

#### Orden roto, resultado silenciosamente incorrecto

Si el array no está ordenado y se aplica búsqueda binaria, el algoritmo termina sin error. Devuelve un índice o -1. El resultado puede ser incorrecto y no hay ninguna señal de ello.

La precondición de orden no es una sugerencia. Es una condición necesaria para que el resultado sea correcto.

#### Búsqueda binaria sobre listas enlazadas

En una lista enlazada, no existe acceso aleatorio. Llegar al elemento central requiere recorrer n/2 nodos. En cada paso del algoritmo, el coste de encontrar el punto medio es O(n), lo que convierte la búsqueda binaria en O(n log n) en la práctica: peor que lineal. La estructura determina qué algoritmos son aplicables.

#### Intervalo cerrado vs semiabierto

El intervalo `[izquierda, derecha]` que recorre el algoritmo puede definirse de dos formas. La elección afecta directamente la condición del bucle y la actualización de los extremos:

<div align=center>

| Convención | Condición de bucle | Actualización de `derecha` |
|-|:-:|:-:|
| Cerrado: `[izquierda, derecha]` | `izquierda <= derecha` | `derecha = medio - 1` |
| Semiabierto: `[izquierda, derecha)` | `izquierda < derecha` | `derecha = medio` |

</div>

En el intervalo cerrado, `derecha` apunta al último elemento candidato. En el semiabierto, `derecha` apunta a la primera posición excluida, como en los rangos de Java (`subList`, `for (int i = 0; i < n; i++)`).

Ninguna es mejor. El error aparece al mezclarlas: condición de una con actualización de la otra produce bucles infinitos o elementos no visitados. Los ejemplos de este tema usan el intervalo cerrado.

## Extensiones

La búsqueda binaria siempre va al centro del intervalo. Esa decisión es segura pero ignora información que a veces está disponible: el valor del objetivo y la distribución de los datos. Dos variantes explotan esa información.

### Búsqueda por interpolación

En lugar de calcular `medio = izquierda + (derecha - izquierda) / 2`, estima la posición del objetivo proporcionalmente a su valor:

```java
public static int buscarPorInterpolacion(int[] array, int objetivo) {
    int izquierda = 0;
    int derecha = array.length - 1;

    while (izquierda <= derecha && objetivo >= array[izquierda] && objetivo <= array[derecha]) {
        if (izquierda == derecha) {
            if (array[izquierda] == objetivo) {
                return izquierda;
            }
            return -1;
        }
        int medio = izquierda + ((objetivo - array[izquierda]) * (derecha - izquierda))
                                / (array[derecha] - array[izquierda]);
        if (array[medio] == objetivo) {
            return medio;
        }
        if (array[medio] < objetivo) {
            izquierda = medio + 1;
        } else {
            derecha = medio - 1;
        }
    }
    return -1;
}
```

Si los datos están uniformemente distribuidos, la estimación es precisa y el algoritmo converge en O(log log n). Si la distribución es irregular, la estimación puede ser mala y el rendimiento degenera hasta O(n).

Es conocimiento del dominio aplicado al algoritmo: funciona bien cuando se sabe que los datos son uniformes. Sin esa garantía, la búsqueda binaria es más segura.

**Array óptimo:** distribución uniforme, gaps iguales entre elementos.

<div align=center>

|0|1|2|3|4|5|6|7|8|9|
|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
|10|20|30|40|50|60|70|80|90|100|

</div>

Buscando 70:

- `medio = 0 + ((70-10) * 9) / (100-10) = 6`. `array[6] = 70`. Encontrado en 1 paso.

**Array desfavorable:** distribución muy irregular, valores agrupados en un extremo.

<div align=center>

|0|1|2|3|4|5|6|7|8|9|
|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
|1|2|3|4|5|6|7|8|9|100|

</div>

Buscando 9:

- `medio = 0 + ((9-1) * 9) / (100-1) = 0`. `array[0] = 1`. La estimación falla y el algoritmo avanza de uno en uno hasta encontrarlo.

### Búsqueda por saltos

Avanza en bloques de tamaño √n hasta superar el objetivo, luego hace búsqueda lineal hacia atrás dentro del bloque:

```java
public static int buscarPorSaltos(int[] array, int objetivo) {
    int n = array.length;
    int bloque = (int) Math.sqrt(n);
    int inicio = 0;

    while (inicio < n && array[Math.min(inicio + bloque, n) - 1] < objetivo) {
        inicio += bloque;
    }

    for (int i = inicio; i < Math.min(inicio + bloque, n); i++) {
        if (array[i] == objetivo) {
            return i;
        }
        if (array[i] > objetivo) {
            return -1;
        }
    }
    return -1;
}
```

Complejidad: O(√n). Es un punto intermedio entre lineal (O(n)) y binaria (O(log n)), útil cuando el acceso aleatorio existe pero tiene coste (por ejemplo, en disco), porque minimiza el número de saltos largos.

**Array óptimo:** n elementos con bloque de √n. Con n = 16, bloque = 4.

<div align=center>

|0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|
|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
|2|5|8|12|16|23|38|42|55|72|83|91|98|105|112|120

</div>

Buscando 72:

- Salto a pos 3 (array[3]=12 < 72)
- Salto a pos 7 (array[7]=42 < 72)
- Salto a pos 11 (array[11]=91 >= 72)
- Búsqueda lineal en el bloque [8..11]: array[9]=72. 

3 saltos + 2 pasos lineales.

### Búsqueda exponencial

Resuelve un problema distinto: ¿qué pasa cuando no se conoce el tamaño del array, o cuando el elemento probablemente está cerca del principio?

Funciona en dos fases:

- Primero localiza el rango doblando el índice (1, 2, 4, 8, 16...) hasta superar el objetivo. 
- Luego aplica búsqueda binaria dentro de ese rango

```java
public static int buscarExponencial(int[] array, int objetivo) {
    if (array[0] == objetivo) {
        return 0;
    }

    int indice = 1;
    while (indice < array.length && array[indice] <= objetivo) {
        indice *= 2;
    }
    return buscar(array, objetivo, indice / 2, Math.min(indice, array.length - 1));
}
```

Complejidad: O(log n). Pero si el elemento está en la posición `p`, la fase de localización cuesta O(log p): para elementos cerca del inicio es mucho mejor que O(log n).

**Array óptimo:** cualquier array ordenado cuando el elemento buscado está cerca del principio.

<div align=center>

|0|1|2|3|4|5|...|999|
|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
|3|7|12|15|21|30|...|9999|

</div>

Buscando 7 (posición 1):

- indice=1: `array[1]=7 <= 7`. indice=2.
- indice=2: `array[2]=12 > 7`. Rango `[1, 2]`.
- Binaria en 2 elementos. Encontrado en 2 pasos, independientemente de que n sea 1000.

**Dónde brilla:** cuando el tamaño es desconocido (stream, fichero sin longitud en cabecera). La binaria estándar necesita conocer el límite superior; la exponencial lo construye sobre la marcha.

<div align=center>

| Algoritmo | Complejidad | Cuándo aporta |
|-|:-:|-|
| Binaria | O(log n) | Caso general con orden y tamaño conocido |
| Por interpolación | O(log log n) promedio | Distribución uniforme conocida |
| Por saltos | O(√n) | Acceso aleatorio con coste (disco) |
| Exponencial | O(log p) | Tamaño desconocido o elemento cerca del inicio |

</div>

Todas exigen orden. La diferencia es qué información adicional se puede explotar y a qué precio. La binaria es la más robusta precisamente porque no asume nada más allá del orden: el array desfavorable para interpolación (`{1, 2, 3, 4, 5, 6, 7, 8, 9, 100}`) es perfectamente válido para ella.
