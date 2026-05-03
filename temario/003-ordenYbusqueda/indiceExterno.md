<div align=right>

<sub>[**ORDEN Y BÚSQUEDA**](README.md)  
[Construcción](construccion.md) / [Búsquedas](busqueda/README.md) / [Ordenación](ordenacion/README.md) / [**Índice externo**](indiceExterno.md)</sub>

</div>

# Índice externo

## ¿Por qué?

La ordenación impone un invariante sobre la colección completa. Ese invariante tiene dos limitaciones prácticas.

La primera: **una colección solo puede estar físicamente ordenada de una manera**. Si los registros de estudiantes están ordenados por apellido, buscar por identificador obliga a reordenar o a buscar linealmente. Cada criterio de búsqueda adicional exige su propio orden, y ordenar cuesta O(n log n).

La segunda: **mover registros es caro cuando son grandes**. Un registro con veinte campos ocupa mucho más que su clave. Ordenar la colección completa mueve esos registros enteros, aunque para la búsqueda solo interese la clave.

## ¿Qué?

Un índice externo es una estructura separada que almacena pares (clave, puntero) y mantiene su propio orden o disposición, sin tocar la colección original. Los datos no se mueven: solo se reorganiza el índice.

Eso resuelve las dos limitaciones:

- Varios índices sobre la misma colección, uno por criterio de búsqueda.
- El índice es pequeño (solo clave y puntero) y barato de reorganizar.

## ¿Cómo?

La estructura del índice determina el coste de búsqueda:

<div align=center>

| Estrategia | Estructura | Búsqueda | Cuándo |
|-|-|:-:|-|
| Ordenado | Claves ordenadas + punteros | O(log n) | Se necesita orden o consultas por rango |
| Hashing | Tabla de dispersión | O(1) | Solo se busca por clave exacta |

</div>

El índice ordenado mantiene las claves en orden y permite búsqueda binaria, recorrido ordenado y consultas por rango: "todos los registros entre A y B". Es el modelo detrás de los índices de las bases de datos relacionales.

El índice con hashing renuncia al orden para conseguir acceso en tiempo constante. No responde a consultas por rango ni a recorridos ordenados. Solo responde a una pregunta: dado este valor de clave, ¿en qué posición está el registro?

Esa segunda estrategia y el mecanismo que la hace posible, es lo que desarrolla el siguiente módulo.

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
