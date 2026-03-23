<div align=right>

<sub>[RECURSIVIDAD](/temario/002-recursividad/README.md)<br>
[Inducción](/temario/002-recursividad/01-induccion/prePatrones.md) / [Estructuración](/temario/002-recursividad/02-estructuracion/README.md) / [Implementación](/temario/002-recursividad/03-implementacion/README.md) / [Aplicación](/temario/002-recursividad/04-aplicacion/README.md) / [**PostRecursividad**](/temario/002-recursividad/05-postrecursividad/README.md)</sub><br>
[Inicio](../README.md) / [Coste](../01-coste-recursion.md) / [Fibonacci](../02-fibonacci.md) / [Memoización](../03-memoizacion.md) / [**Ejemplos prácticos**](README.md)<br>
[Escaleras](escaleratres.md) / [Caminos en una cuadrícula](caminoscuadrícula.md)

</div>

# Ejemplos prácticos de memoización

Dos problemas que ilustran cómo la memoización transforma una recursión ineficiente en un algoritmo práctico: caché 1D y caché 2D.

## [Escaleras](escaleratres.md)

Variante directa de Fibonacci donde se pueden tomar 1, 2 o 3 pasos. El árbol de llamadas se triplica en cada nivel, haciendo la recursión aún más explosiva que Fibonacci. Es el ejemplo más simple para mostrar cómo el número de ramas afecta el colapso del árbol con memoización.

> *Subproblemas solapados 1D, tres ramas de recursión.*

## [Caminos en una cuadrícula](caminoscuadrícula.md)

Contar caminos desde (0,0) hasta (m,n) moviéndose solo derecha y abajo. Es el primer ejemplo con caché bidimensional: cada subproblema está identificado por dos coordenadas, no un solo entero.

> *Subproblemas solapados 2D, estructura espacial.*

---

## Patrones de caché

<div align=center>

|Tipo de caché|Ejemplo|Estructura de clave|Cuándo usar|
|-|-|-|-|
|Array 1D|Escaleras|Entero simple|Subproblemas identificados por un parámetro entero|
|Array 2D|Caminos en cuadrícula|Par ordenado (i, j)|Subproblemas con dos dimensiones|

</div>
