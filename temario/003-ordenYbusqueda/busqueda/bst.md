<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [**Búsquedas**](../busqueda/README.md) / [Ordenación](../ordenacion/README.md) / [Índice externo](../indiceExterno.md)  </sub>  
[Búsqueda lineal](lineal.md) / [Búsqueda binaria](binaria.md) / [**Búsqueda en BST**](bst.md)

</div>

# Búsqueda en BST

## ¿Por qué?

La búsqueda binaria sobre array es O(log n), pero exige que el array esté ordenado. Si los datos cambian (inserciones, eliminaciones), mantener el orden requiere reorganizar el array: O(n) por inserción en el peor caso. Para datos dinámicos, el array ordenado es una estructura cara de mantener.

Un árbol binario de búsqueda (BST) resuelve ese problema: el invariante de orden no es una propiedad que se verifica ni se restaura. Es la consecuencia directa de cómo se insertan los elementos. Cada inserción coloca el nuevo nodo exactamente donde debe estar. El orden no se impone; emerge.

## ¿Qué?

Un BST es un árbol binario donde cada nodo cumple:

- Todos los valores en su subárbol izquierdo son menores que su valor.
- Todos los valores en su subárbol derecho son mayores que su valor.

Este invariante se aplica recursivamente a cada nodo, no solo a la raíz.

```
        8
       / \
      3   10
     / \    \
    1   6    14
       / \   /
      4   7 13
```

Para buscar el valor 6: se compara con 8 (menor, ir izquierda), con 3 (mayor, ir derecha), con 6 (encontrado). Tres comparaciones para un árbol de 9 elementos.

La búsqueda en BST y la búsqueda binaria en array son el mismo proceso de descarte por mitades, aplicado a estructuras distintas.

## ¿Para qué?

El BST es la elección correcta cuando:

- Los datos son dinámicos: se insertan y eliminan elementos con frecuencia.
- Se necesita mantener orden sin el coste de reorganizar un array.
- La distribución de los datos de entrada es suficientemente aleatoria.

No es adecuado cuando:

- Los datos llegan en orden creciente o decreciente: el árbol degenera (ver reflexiones).
- Se necesita garantía de O(log n) en el peor caso: un BST no balanceado no la ofrece.

## ¿Cómo?

El código completo está en [`src/ordenYbusqueda/construccion/bst/`](/src/ordenYbusqueda/construccion/bst/). El núcleo de la búsqueda, extraído de `BST.java`:

```java
private boolean buscarEn(Nodo nodo, int valor) {
    if (nodo == null) {
        return false;
    }
    if (valor == nodo.getValor()) {
        return true;
    }
    if (valor < nodo.getValor()) {
        return buscarEn(nodo.getIzquierda(), valor);
    }
    return buscarEn(nodo.getDerecha(), valor);
}
```

Y su uso desde `Cliente.java`:

```java
BST arbol = new BST();
int[] datos = {5, 3, 7, 1, 4, 6, 8};
for (int valor : datos) {
    arbol.insertar(valor);
}
System.out.println(arbol.buscar(4));  // true
System.out.println(arbol.buscar(9));  // false
```

### Decisiones de diseño en el código

**`buscar` devuelve `boolean`, no el nodo.** A diferencia de la búsqueda en array (que devuelve el índice), en el BST la interfaz pública devuelve solo si el elemento existe. La posición no tiene significado externo en un árbol: el nodo es un detalle de implementación. Esta decisión simplifica la interfaz sin perder utilidad.

**`Nodo` está encapsulado.** Los atributos son privados y accesibles solo por getters y setters. La estructura interna del árbol no se expone al exterior.

**Sin `else if` al final.** La última rama no necesita condición: si no es null, no es igual y no es menor, solo puede ser mayor. El código lo expresa cayendo directamente al `return` final.

### Algunas reflexiones

#### O(log n) en promedio, no garantizado

La altura del BST determina el coste de búsqueda. En un árbol equilibrado, la altura es O(log n) y la búsqueda también. Pero la altura depende del orden de inserción: si los datos llegan en orden creciente, el árbol degenera en una lista enlazada y la búsqueda vuelve a ser O(n).

Este problema y su solución están desarrollados en [construcción - la trampa del BST no balanceado](../construccion.md#la-trampa-del-bst-no-balanceado), con traza completa de inserción en [`src/ordenYbusqueda/construccion/bstBalanceado/README.md`](/src/ordenYbusqueda/construccion/bstBalanceado/README.md).

#### La búsqueda no cambia al balancear; el coste se paga en la inserción

El método `buscarEn` de `BSTBalanceado.java` es idéntico al de `BST.java`. La búsqueda no sabe ni le importa si el árbol está balanceado: solo sigue el invariante.

El coste del balanceo no desaparece; se paga en la inserción. Cada vez que se inserta un elemento, el árbol AVL comprueba si algún nodo ha perdido el equilibrio y aplica rotaciones para restaurarlo. Eso tiene un coste adicional respecto al BST simple. La ganancia está en la búsqueda: al garantizar altura O(log n), la búsqueda deja de depender del orden de inserción.

Es un intercambio explícito: inserción más cara a cambio de búsqueda garantizada.

#### El invariante roto produce resultados incorrectos

Si el invariante del BST se viola (por una inserción incorrecta o manipulación directa de los nodos), la búsqueda termina sin error y devuelve un resultado que puede ser incorrecto. El mismo problema que la búsqueda binaria sobre array desordenado: fallo silencioso. El algoritmo confía en la precondición; no la verifica.
