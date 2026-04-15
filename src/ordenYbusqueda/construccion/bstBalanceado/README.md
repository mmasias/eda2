# BSTBalanceado — proceso de rebalanceo

## Factor de balance

Cada nodo mantiene su altura. El factor de balance de un nodo es:

```
balance(nodo) = altura(subárbol izquierdo) - altura(subárbol derecho)
```

Un árbol AVL garantiza que, tras cada inserción, ningún nodo tiene `|balance| > 1`. Si lo tiene, se aplica una rotación para restaurar el invariante.

**Convención de altura:**
- Nodo hoja: altura 0
- Nodo null: altura -1 (valor centinela)
- Esto garantiza que `actualizarAltura` sobre una hoja devuelve 0: `1 + max(-1, -1) = 0`

## El balanceo como ordenación continua

El balanceo AVL y la ordenación son el mismo mecanismo en momentos distintos del ciclo de vida de la estructura.

| Estrategia | Cuándo se mantiene el invariante | Coste |
|---|---|---|
| Ordenar después | Nunca durante la construcción, una vez al final | O(n log n) amortizado |
| Inserción ordenada (array) | En cada inserción | O(n) por inserción |
| BST sin balanceo | En cada inserción, solo el invariante de valor | O(log n) promedio, O(n) peor caso |
| AVL | En cada inserción, invariante de valor y de altura | O(log n) garantizado |

La pregunta que los unifica: **¿cuándo y cuánto se paga por mantener el orden?** El AVL responde: en cada operación, un coste fijo y predecible.

## Cuándo se activa

`insertarEn` actualiza la altura del nodo al volver de la llamada recursiva y llama a `rebalancear`. Si el balance está en [-1, 1], el nodo se devuelve sin cambios. Si no, se determina cuál de los cuatro casos aplica.

## Traza con datos en orden creciente

Insertar `{1, 2, 3, 4, 5}` — el caso que destruye el BST sin balanceo.

**Insertar 1, 2:**
```
1
 \
  2
balance(1) = -1  → dentro del rango, sin rotación
```

**Insertar 3:**
```
1                    2
 \      →          /   \
  2               1     3
   \
    3
balance(1) = -2  → Right-Right → rotación izquierda sobre 1
```

**Insertar 4:**
```
    2                2
   / \      →      /   \
  1   3           1     3
       \                 \
        4                 4
balance(2) = -1  → dentro del rango, sin rotación
```

**Insertar 5:**
```
    2                  2                    2
   / \                / \                 /   \
  1   3      →       1   4      →        1     4
       \                / \                   / \
        4              3   5                 3   5
         \
          5
balance(3) = -2  → Right-Right → rotación izquierda sobre 3
balance(2) = -1  → dentro del rango, sin rotación
```

**Resultado final:**
```
    2
   / \
  1   4
     / \
    3   5
```

El BST sin balanceo habría producido una lista enlazada hacia la derecha. El AVL mantiene la altura en O(log n) en todo momento — no porque corrija el desorden al final, sino porque nunca lo acumula.

## Los cuatro casos

El caso se determina por el balance del nodo desbalanceado y el balance de su hijo en la dirección pesada.

### Left-Left — rotación derecha

**Condición:** `balance(nodo) > 1` y `balance(hijo izquierdo) >= 0`

```
    z                y
   / \             /   \
  y   T4          x     z
 / \      -->    / \   / \
x   T3          T1 T2 T3 T4
```

### Right-Right — rotación izquierda

**Condición:** `balance(nodo) < -1` y `balance(hijo derecho) <= 0`

```
  z                  y
 / \               /   \
T1   y    -->     z     x
    / \          / \   / \
   T2   x       T1 T2 T3 T4
```

### Left-Right — rotación izquierda sobre hijo + rotación derecha sobre nodo

**Condición:** `balance(nodo) > 1` y `balance(hijo izquierdo) < 0`

```
    z              z              x
   / \            / \           /   \
  y   T4         x   T4       y     z
 / \     -->    / \    -->   / \   / \
T1   x         y   T3      T1 T2 T3 T4
    / \        / \
   T2  T3    T1  T2
```

### Right-Left — rotación derecha sobre hijo + rotación izquierda sobre nodo

**Condición:** `balance(nodo) < -1` y `balance(hijo derecho) > 0`

```
  z                z                x
 / \              / \             /   \
T1   y    -->   T1   x    -->   z     y
    / \             / \        / \   / \
   x   T4          T2  y      T1 T2 T3 T4
  / \                 / \
 T2  T3             T3  T4
```

## Orden de operaciones en cada rotación

1. Reasignar los punteros.
2. Actualizar la altura del nodo que **baja** (pierde altura).
3. Actualizar la altura del nodo que **sube** (nueva raíz del subárbol).

El orden importa: la altura del nodo que sube depende de la altura actualizada del que baja.
