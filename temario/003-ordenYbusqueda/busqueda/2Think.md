# *#2Think*

Para cada escenario:

- **Qué se tiene**
- **¿Qué se necesita?**

<div align=center>

|¿Qué sé sabe de los datos?|¿Qué estrategia es óptima?|¿Qué complejidad tiene cada búsqueda?|
|-|-|-|
||**¿Qué estrategia se descarta?**|**¿Por qué?**|

</div>

<div align=center>

|Tenemos|¿Qué se necesita?|
|-|-|
|Un fichero de logs con millones de entradas ordenadas por timestamp|Se buscan todos los eventos de un minuto concreto
|Una lista de usuarios registrados en orden de llegada|Se busca por nombre
|Un catálogo de precios actualizado en tiempo real (inserciones y eliminaciones frecuentes)|Se busca si un producto existe
|Un array de 8 enteros sin orden conocido|Se realiza una única búsqueda
|Un array de temperaturas horarias del último año (8760 valores, orden temporal garantizado)|Se realizan 10.000 consultas por valor de temperatura
|Una tabla de códigos postales de España (47.000 entradas, ordenadas por código)|Se busca la provincia correspondiente a un código postal
|Un log de accesos web: 10 millones de IPs registradas en orden de llegada|Se comprueba si una IP concreta accedió hoy
|Un BST construido insertando los enteros 1, 2, 3, ..., 10.000 en orden creciente|Se busca un valor en el árbol
|Una colección de 1 millón de enteros sin orden conocido|Se realizan exactamente 2 búsquedas
|Un catálogo de productos con nombre y precio|Se busca por nombre en unas consultas y por precio en otras, de forma independiente
