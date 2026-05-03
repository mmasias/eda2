<div align=right>

<sub>[**ORDEN Y BÚSQUEDA**](README.md)  
[Construcción](construccion.md) / [Búsquedas](busqueda/README.md) / [Ordenación](ordenacion/README.md) / [Índice externo](indiceExterno.md)</sub>  

</div>

# Orden y búsqueda

## ¿Por qué?

Encontrar un elemento en una colección es una operación elemental y ubicua. La estrategia más directa -recorrer uno a uno- funciona siempre, pero su coste crece linealmente con el tamaño: O(n). Para n = 10⁶, eso puede ser la diferencia entre una respuesta instantánea y un sistema inutilizable.

Existe una estrategia radicalmente más eficiente: O(log n). Pero exige una precondición: que los datos estén ordenados.

El orden, por tanto, no es un objetivo en sí mismo. Es la precondición del acceso eficiente. Todo lo que sigue en este módulo se deriva de esa relación.

## ¿Qué?

El módulo estudia el problema del acceso eficiente a datos desde cuatro ángulos que forman una cadena causal:

- **Construcción**: si el mecanismo de inserción puede garantizar orden, el coste posterior desaparece. El orden como invariante, no como tarea pendiente.
- **Búsqueda**: el problema real. Aquí aparece la demanda de orden. Sin este nodo, la ordenación carece de motivación.
- **Ordenación**: la respuesta cuando no se construyó ordenado y se necesita buscar eficientemente. Los algoritmos son el cuerpo técnico de esa respuesta.
- **Índice externo**: cuando ordenar el dato es caro, imposible o destructivo. El orden vive en una estructura separada.

## ¿Para qué?

El tratamiento conjunto de búsqueda y ordenación resuelve el problema de secuenciación que cada módulo por separado deja sin responder:

- La búsqueda binaria exige orden como precondición pero no explica cómo satisfacerla.
- La ordenación mejora la búsqueda pero, presentada sola, carece de motivación concreta.

Presentados en cadena causal, cada concepto justifica el siguiente y es justificado por el anterior.

## ¿Cómo?

| | |
|-|-|
| [**Construcción**](construccion.md) | Orden como invariante de diseño. Inserción ordenada, BST. Coste de construir ordenado frente a ordenar después. |
| [**Búsqueda**](busqueda/README.md) | Lineal: O(n), sin precondiciones. Binaria: O(log n), exige orden. La comparación entre ambas es la motivación de todo lo demás. |
| [**Ordenación**](ordenacion/README.md) | Algoritmos clásicos y no comparativos. Su lugar en la cadena: coste que se amortiza con búsquedas posteriores. |
| [***Índice externo***](indiceExterno.md) | Separar clave de dato. Preludio a B-trees, índices invertidos, bases de datos. |