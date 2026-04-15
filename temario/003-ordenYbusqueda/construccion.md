# Construcción [con orden]

## ¿Por qué?

La forma natural de construir una colección es insertar elementos sin considerar su posición relativa. El coste de inserción es mínimo; el coste de cada búsqueda posterior es O(n).

Existe una alternativa: pagar un coste mayor en la inserción para garantizar que la colección mantiene un invariante de orden en todo momento. El resultado es que cada búsqueda posterior puede explotar ese invariante y operar en O(log n), sin fase de ordenación explícita.

La pregunta concreta: si se van a realizar k búsquedas sobre n elementos, ¿cuándo compensa pagar el coste de mantener orden durante la construcción?

## ¿Qué?

Una estructura construida con orden es aquella cuyo mecanismo de inserción garantiza que el invariante se mantiene en cada operación, sin necesidad de reorganizar la colección después.

Comparar cuatro estructuras sobre el mismo problema -construir una colección y buscar en ella- revela que mantener un invariante de orden no es suficiente: la estructura también debe permitir explotar ese orden.

<div align=center>

| Estructura | Inserción | Búsqueda | Por qué |
|-|:-:|:-:|-|
| Array sin orden | O(1) | O(n) | Sin invariante; búsqueda lineal obligatoria |
| Lista sin orden | O(1) | O(n) | Sin invariante; búsqueda lineal obligatoria |
| Array ordenado | O(n) | O(log n) | Acceso aleatorio: se puede saltar al centro |
| Lista ordenada | O(n) | O(n) | Sin acceso aleatorio: ordenar no basta |
| BST | O(log n) | O(log n) | La estructura es el orden; cada nodo es el centro de su rango |

</div>

La fila de la lista ordenada es la más instructiva. Paga el coste de insertar manteniendo el invariante y no obtiene ninguna ventaja en la búsqueda: sin acceso aleatorio, no es posible saltar al elemento central. La búsqueda sigue siendo O(n).

El O(n) de inserción tiene, además, naturaleza distinta en el array y en la lista:

<div align=center>

| | Encontrar posición de inserción | Insertar |
|-|:-:|:-:|
| Array ordenado | O(log n)<br>búsqueda binaria posible | O(n)<br>desplazamiento físico de elementos |
| Lista ordenada | O(n)<br>travesía obligatoria | O(1)<br>solo cambian punteros |

</div>

El cuello de botella está en sitios opuestos: el array encuentra rápido y desplaza lento; la lista no localiza la posición sin recorrer, pero una vez allí inserta sin mover datos.

El BST cierra el argumento: no hay desplazamiento ni travesía ciega. El invariante convierte cada nodo en el centro natural de su subrango. Insertar y buscar explotan esa propiedad en O(log n).

## ¿Para qué?

El argumento es de amortización. Sean n inserciones y k búsquedas posteriores:

<div align=center>

| Estrategia | Coste construcción | Coste k búsquedas | Total |
|-|:-:|:-:|:-:|
| Sin orden, búsqueda lineal | O(n) | O(k·n) | O(kn) |
| Ordenar tras construir, búsqueda binaria | O(n log n) | O(k·log n) | O(n log n + k log n) |
| BST balanceado desde el principio | O(n log n) | O(k·log n) | O(n log n + k log n) |

</div>

Para k >> log n -el caso habitual en cualquier sistema consultado con frecuencia- las dos últimas estrategias dominan a la primera por varios órdenes de magnitud.

Consecuencia directa: si se sabe de antemano que una colección va a ser consultada muchas veces, el coste de construirla ordenada se amortiza rápidamente. Si se consulta una sola vez, esa inversión es innecesaria.

## ¿Cómo?

<div align=center>

| Estructura | Inserción | Búsqueda | Por qué |
|-|-|-|-|
| [Array sin orden](/src/ordenYbusqueda/construccion/arraySinOrden/) | O(1) | O(n) | Sin invariante; búsqueda lineal obligatoria |
| [Lista sin orden](/src/ordenYbusqueda/construccion/listaSinOrden/) | O(1) | O(n) | Sin invariante; búsqueda lineal obligatoria |
| [Array ordenado ](/src/ordenYbusqueda/construccion/arrayConOrden/)| O(n) | O(log n) | Acceso aleatorio: se puede saltar al centro |
| [Lista ordenada ](/src/ordenYbusqueda/construccion/listaConOrden/)| O(n) | O(n) | Sin acceso aleatorio: ordenar no basta |
| [BST](/src/ordenYbusqueda/construccion/bst/) | O(log n) | O(log n) | La estructura es el orden; cada nodo es el centro de su rango |

</div>

### Algunas reflexiones

#### Inserciones en arrays

Recordemos que los arrays son elementos de tamaño fijo, por lo que es importante mantener la atención en el tamaño al momento de la inserción. Se podría hacer dinámico el tamaño del array -dado que en la implementación propuesta el array está encapsulado-, pero eso tiene una repercusión en la eficiencia.

#### Inserción en listas

El O(1) de `listaSinOrden.insertar` no es una propiedad de la estructura: es una decisión de implementación. Insertar en la cabeza es O(1); insertar en la cola sin puntero de cola requiere recorrer toda la lista - O(n). El lugar de inserción importa.

#### Búsqueda en lista ordenada

La lista ordenada puede parar antes de recorrerla completa: si el elemento actual supera al buscado, el elemento no está. El orden sí hace algo - pero no lo suficiente. La complejidad sigue siendo O(n).

#### La lista no es un array con acceso por índice

Una lista a la que se le añade acceso por índice no es una lista: es un array disfrazado, con peor rendimiento. La lista es la estructura adecuada para acceso secuencial; forzarle eficiencia de búsqueda traiciona su naturaleza.

#### La trampa del BST no balanceado

Si los elementos se insertan en orden creciente, el BST degenera en una lista enlazada: cada nodo solo tiene hijo derecho. La búsqueda vuelve a ser O(n).

```
Insertar 1, 2, 3, 4, 5:

                1
                 \
                  2
                   \
                    3
                     \
                      4
                       \
                        5
```

Las variantes balanceadas (AVL, Red-Black) resuelven este problema añadiendo rotaciones tras cada inserción para mantener la altura en O(log n), garantizando el comportamiento esperado incluso con datos en orden.

<div align=center>

| | Datos aleatorios | Datos en orden |
|-|-|-|
| BST | O(log n) | O(n) |
| BST balanceado | O(log n) | O(log n) |

</div>

### Cuándo no construir ordenado

- Una sola búsqueda: para n pequeño, el coste de ordenar supera al de buscar linealmente.
- Colección que se modifica muy frecuentemente y se consulta poco: el mantenimiento del invariante no se amortiza.
- Datos que llegan ya ordenados en un array: la inserción en posición correcta requiere desplazamiento O(n); un BST balanceado es la alternativa natural.
