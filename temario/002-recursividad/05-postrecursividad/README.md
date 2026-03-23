<div align=right>

<sub>[RECURSIVIDAD](/temario/002-recursividad/README.md)<br>
[Inducción](/temario/002-recursividad/01-induccion/prePatrones.md) / [Estructuración](/temario/002-recursividad/02-estructuracion/README.md) / [Implementación](/temario/002-recursividad/03-implementacion/README.md) / [Aplicación](/temario/002-recursividad/04-aplicacion/README.md) / [**PostRecursividad**](/temario/002-recursividad/05-postrecursividad/README.md)</sub><br>
[**Inicio**](README.md) / [Coste](01-coste-recursion.md) / [Fibonacci](02-fibonacci.md) / [Memoización](03-memoizacion.md) / [Ejemplos prácticos](ejemplos/README.md)

</div>

# PostRecursividad

## ¿Por qué?

El módulo ha construido, paso a paso, la capacidad de escribir funciones recursivas correctas. Correcto, sin embargo, no implica eficiente.

Una función puede seguir todos los patrones — caso base, caso recursivo, hacer y deshacer — y aun así ser catastrófica en la práctica. Para cerrar el capítulo hace falta responder una pregunta que ha quedado pendiente:

## ¿Qué?

¿Qué más implica todo lo que hemos aprendido a hacer?

## ¿Para qué?

- Estimar el coste de una función recursiva antes de ejecutarla
- Reconocer cuándo la recursión directa es insuficiente
- Entender qué son los subproblemas solapados y por qué importan
- Conocer la memoización como primer paso hacia la programación dinámica

## ¿Cómo?

<div align=center>

|Artículo|Concepto|
|-|-|
|[El coste de la recursión](01-coste-recursion.md)|El árbol de llamadas como herramienta de análisis|
|[Fibonacci](02-fibonacci.md)|Correcto pero catastrófico: subproblemas solapados|
|[Memoización](03-memoizacion.md)|Recordar lo que ya calculaste|
|[Ejemplos prácticos](ejemplos/README.md)|Escaleras y caminos en cuadrícula|

</div>
