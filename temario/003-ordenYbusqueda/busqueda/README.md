<div align=right>

<sub>[ORDEN Y BÚSQUEDA](../README.md)  
[Construcción](../construccion.md) / [**Búsquedas**](../busqueda/README.md) / [Ordenación](../ordenacion/README.md) / [Índice externo](../indiceExterno.md)  </sub>  
[Búsqueda lineal](lineal.md) / [Búsqueda binaria](binaria.md) / [Búsqueda en BST](bst.md)

</div>

# Búsqueda

## ¿Por qué?

Encontrar un elemento en una colección es la operación que justifica todo lo anterior. La construcción tiene sentido porque algo se va a buscar. La pregunta no es abstracta: tengo datos, necesito uno.

La eficiencia de la búsqueda no depende solo del algoritmo. Depende de lo que se sabe sobre los datos antes de empezar. Dos colecciones con los mismos elementos pueden admitir estrategias radicalmente distintas según sus propiedades conocidas.

La pregunta central no es *¿cómo busco?* Es **¿qué sé de mis datos?**

> *[2Think...](2Think.md)*

## ¿Qué?

El escenario de búsqueda se define por dos variables:

- **La consulta**: qué elemento se busca.
- **El conocimiento previo**: qué se sabe sobre la colección antes de buscar.

El conocimiento previo tiene tres fuentes:

1. **Construcción propia** Se sabe exactamente qué invariante se mantuvo porque se tomó esa decisión. Si se construyó con orden (array ordenado, BST), el conocimiento es perfecto y gratuito.
1. **Verificación algorítmica** Si los datos llegaron de fuera, se puede comprobar si la colección está ordenada recorriéndola una vez: O(n). El conocimiento es adquirible, pero tiene coste. Y solo describe el estado actual, no el origen.
1. **Conocimiento del dominio** Los logs del sistema siempre llegan en orden temporal. Los identificadores son secuenciales. Los precios no siguen ningún patrón predecible. Este conocimiento no es algoritmable: proviene del problema, no de los datos. Cae fuera del ámbito de esta asignatura, pero ignorarlo equivale a elegir algoritmos a ciegas.

Estas tres fuentes no son equivalentes. La primera es gratuita y perfecta. La segunda tiene coste. La tercera no tiene coste algorítmico pero exige comprensión del dominio, y equivocarse en ella tiene consecuencias que ningún algoritmo puede corregir.

## ¿Para qué?

El conocimiento previo determina qué estrategia es óptima:

<div align=center>

| Conocimiento previo | Estrategia | Complejidad |
|-|:-:|:-:|
| Sin información | Búsqueda lineal | O(n) |
| Datos ordenados + acceso aleatorio | Búsqueda binaria | O(log n) |
| Estructura construida con invariante | Búsqueda en árbol | O(log n) |

</div>

La diferencia entre O(n) y O(log n) no es de algoritmo: es de información. El algoritmo más eficiente disponible es el que puede explotarse con el conocimiento que se tiene.

Aplicar búsqueda binaria sobre datos no ordenados no produce un error obvio: produce resultados incorrectos silenciosamente. La precondición no es una recomendación.

## ¿Cómo?

### Convenciones

Antes de ver cada estrategia, conviene fijar convenciones que comparten todas.

- **`-1` como señal de "no encontrado"** Los índices válidos son siempre >= 0. El valor -1 no puede confundirse con ninguna posición real, lo que lo convierte en la señal estándar de ausencia en búsquedas sobre arrays. No es arbitrario: es la consecuencia directa de que los índices sean naturales.
- **Devolver el índice, no un booleano** La pregunta "¿está el elemento?" tiene como respuesta natural `true` o `false`. Sin embargo, las implementaciones devuelven el índice. La razón: el índice contiene más información. Si el llamador necesita la posición además de saber si existe, devolverle un booleano le obliga a buscar de nuevo. El índice responde a ambas preguntas a la vez; el booleano, solo a una.

### Estrategias

<div align=center>

| Estrategia | Precondición | Complejidad | Cuándo |
|-|-|:-:|-|
| [Búsqueda lineal](lineal.md) | Ninguna | O(n) | Datos sin orden conocido o colección pequeña |
| [Búsqueda binaria](binaria.md) | Orden + acceso aleatorio | O(log n) | Array ordenado o datos verificados |
| [Búsqueda en BST](bst.md) | Invariante de árbol | O(log n) promedio | Construido con invariante |

</div>

### Algunas reflexiones

#### Cuando los datos llegan ordenados

Si los datos provienen de una fuente externa ya ordenada, construir a partir de ellos una estructura que mantenga orden puede ser innecesario o contraproducente. Un BST sobre entrada en orden creciente degenera en lista enlazada (O(n) en búsqueda) porque el invariante que debía garantizar eficiencia trabaja contra sí mismo. La estructura asume distribución arbitraria; los datos tienen distribución extrema.

El conocimiento del origen de los datos es tan relevante como el algoritmo de búsqueda.

#### Verificar si hay orden tiene coste

Comprobar si una colección está ordenada requiere recorrerla: O(n). Si se realiza una sola búsqueda, verificar más buscar linealmente (O(n) + O(n)) es siempre más caro que buscar linealmente sin verificar (O(n)). La verificación solo se amortiza si el resultado se reutiliza en múltiples búsquedas posteriores.

#### El conocimiento del dominio no es opcional

Un programador que elige su estrategia de búsqueda sin entender la naturaleza de sus datos está eligiendo en el vacío. El análisis de complejidad describe el comportamiento del algoritmo; no responde si ese algoritmo es el adecuado para esos datos concretos. Esa pregunta requiere respuesta antes de escribir código y su respuesta está en el problema, no en la librería.

### La pregunta que lleva a ordenación

Si los datos no tienen orden conocido y se van a realizar múltiples búsquedas, aparece una decisión: buscar linealmente k veces (O(k·n)) u ordenar una vez y buscar eficientemente cada vez (O(n log n + k·log n)).

<div align=center>

| Estrategia | Coste total |
|-|:-:|
| k búsquedas lineales | O(k·n) |
| Ordenar + k búsquedas binarias | O(n log n + k·log n) |

</div>

La respuesta depende de k. Para k grande, ordenar se amortiza. Para k pequeño o igual a 1, es un coste innecesario.

Esta es la pregunta que da sentido a la ordenación: no se ordena porque sí, sino porque múltiples búsquedas eficientes lo justifican.

Pero hay una condición previa que esta tabla no explicita: la decisión solo existe porque los datos llegaron todos de golpe. Si hubiera habido control sobre su llegada, se habría construido ordenado desde el principio y el problema no existiría. La ordenación es la respuesta cuando ese control no estuvo disponible: los datos ya están, no se puede volver atrás, solo queda actuar sobre el conjunto.
