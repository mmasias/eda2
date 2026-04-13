# Estructuras de datos y algoritmos II

<div align=right>

|<sub>**Construir (PRG1/PRG2) → organizar (EDA1) → optimizar (EDA2)**</sub>|
|-:|
|<sup>[Algoritmos](temario/001-analisisAlgoritmos/README.md) / [Recursividad](temario/002-recursividad/README.md) / Orden y búsqueda / Hashing / Inmutabilidad</sup>|

</div>

## ¿Por qué?

Habiendo visto los fundamentos de la programación imperativa, estructurada y orientada a objetos (PRG1, PRG2); y luego de ver la manera de organizar la información en estructuras de datos eficientes (EDA1), **es pertinente abordar el siguiente paso: aprender a operar sobre esas estructuras de manera eficiente**.

No basta con que un programa funcione correctamente: es necesario que lo haga aprovechando óptimamente los recursos disponibles. Un algoritmo que resuelve un problema para cientos de elementos puede volverse inusable cuando la entrada crece a millones.

## ¿Qué?

La algorítmica es la disciplina que estudia los métodos para resolver problemas de manera sistemática y eficiente. A diferencia de lo visto en asignaturas anteriores, donde el foco estaba en **cómo organizar el código** (PRG1, PRG2) o **cómo organizar los datos** (EDA1), en EDA2 el foco está en **cómo operar sobre esos datos organizados**.

<div align=center>

|PRG1|PRG2|EDA1|EDA2|
|-|-|-|-|
|***Fundamentos mecánicos***|***Expansión conceptual***|***Restricción disciplinada***|***Aplicación algorítmica***
|Cómo hacer que la máquina haga X|Se puede modelar cualquier cosa|No todo objeto es igual: algunos son más eficientes para ciertos usos|Ahora que conocemos las herramientas, usémoslas con eficiencia
|Variables, control de flujo, funciones|Abstracción mediante objetos|Las abstracciones tienen coste|Dada la estructura correcta, ¿cómo la uso óptimamente?
|Pensamiento secuencial|Encapsulación, herencia, polimorfismo|Cada estructura garantiza propiedades específicas|Ordenar, buscar, transformar con garantías de complejidad
||Libertad de diseño|Elección/restricción deliberada: pila vs. cola vs. árbol|

</div>

<div align=center>

|![](/images/modelosUML/jerarquiaTecnicas.svg)
|-:
|<sub>[Código fuente](modelosUML/jerarquiasTecnicas.puml)</sub>

</div>

## ¿Para qué?

<div align=center>

|Capacidad de análisis|Selección informada|Optimización justificada|Fundamento para especialización|
|-|-|-|-|
Evaluar si un algoritmo es viable para el problema en cuestión. Distinguir entre algo aceptable para 100 elementos vs. inviable para 1.000.000.|Elegir la técnica correcta según el contexto.|Identificar cuándo vale la pena optimizar. No todo código necesita ser optimizado: algunos problemas se resuelven adecuadamente con soluciones simples.|Base conceptual para cursos avanzados (programación funcional, programación dinámica, teoría de grafos, machine learning) donde la eficiencia algorítmica es crítica.

</div>

<div align=right>

<sub>*EDA2 busca la concientización algorítmica: dar el salto de los patrones operativos —identificados e implementados en asignaturas anteriores—<br>a **soluciones algorítmicas formales**, valorables en términos de complejidad, eficiencia y garantías de rendimiento.*
</sub>

</div>

## ¿Cómo?

### [Temario](/temario/README.md)

- Bloque instrumental: las herramientas
  - [x] [Análisis de algoritmos](temario/001-analisisAlgoritmos/README.md)
  - [x] [Recursividad](temario/002-recursividad/README.md)
- Bloque de acceso eficiente
  - [ ] [Orden y búsqueda](temario/003-ordenYbusqueda/README.md)
  - [ ] [Hashing](temario/004-hashing/README.md)
- Abstracción avanzada
  - [ ] [Estructuras de datos avanzadas](temario/005-estructurasDatosAvanzadas/README.md)
- Paradigmas complementarios
  - [ ] [Inmutabilidad](temario/006-inmutabilidad/README.md)