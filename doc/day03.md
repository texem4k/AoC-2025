# Día 3 – Advent of Code 2025

## Detector de Voltaje

En este ejercicio se nos pide detectar el par más alto de voltaje de una secuencia de dígitos.

El primer dígito del par debe ser predecesor del otro dígito.

Dada la secuencia **818181911112111**, el par resultante es **89**, no **98**.

**La parte 1 pide:**

* Iterar por todas las secuencias de dígitos, obtener el par mas alto de cada uno y sumar todo

**La parte 2 pide:**

* Parecido, pero cambia la búsqueda, debiendo detectar una secuencia de los 12 dígitos más grandes. 

---

## Parte 1: Máximo par de dígitos por línea

Tras crear VoltageDetector y llamar a `executeA/B(input)`, la clase realiza:

- Con el Stream input, se mapea para que con listas de Strings, convirtiendo cada string en una lista de enteros.
- Se vuelve a mapear el Stream, usando `generatePair(List<Integer>)`, crea los pares y elige el máximo.
- Para generar cada par dentro de `generatePair(List<Integer>)`, se crea un Stream<Pair> con todos los pares de una secuencia, creado con `createPair()`.
- El stream creado se mapea a int llamando al método `toInt()` para de cada Pair, de esta manera se usa el `max()` para obtener el par máximo 
- Por último, para cada valor máximo obtenido, se añade a la lista `maxPairs` y se realiza la suma con `count()`

---

## Parte 2: Construcción de un número largo

El patrón para la 2º parte es muy similar a la 1º, pero en lugar de elegir un par de dígitos, elegimos 12.

- Con el Stream input, se mapea para que con listas de Strings, convirtiendo cada string en una lista de enteros.
- Se vuelve a mapear el Stream, usando `generateSequenceDigits(List<Integer>)` con una búsqueda BFS, devuelve las secuencias y elige el máximo.  
- Una vez tenidos las secuencias de 12 dígitos, se hace un `forEach` y se añaden a `maxPairs`.
- Se realiza la suma con `count()`.


El método `count()` suma todos los valores almacenados en `values` y devuelve la suma final como `long`, que representa  
la respuesta agregada (tanto para parte A como B, según qué ejecución se haya hecho).

---
## Estructura de Datos, Pair

Son dos valores numéricos. Que se definen como un par de dígitos.

- Variables `num1` y `num2` que definen el dígito 1 y el 2.
- Método `toInt()` que devuelve el Pair a entero.  

Es una estructura de datos ideal en este problema ya que simplifica el acceso a los dígitos y a su forma decimal.

---

## Principios SOLID aplicados

### SRP (Single Responsibility Principle)

- `Pair` cumple muy bien SRP: solo representa un par de dígitos y sabe transformarse a entero con `toInt()`. Es inmutable y fácil de probar. 
- `VoltageDetector` agrupa varias responsabilidades: parseo de líneas a listas de dígitos (`toList`), lógica de cálculo de la  
parte A (`generatePair`, `createPair`), lógica de cálculo de la parte B (`generatePairList`) y agregación de resultados (`values`, `addToList`, `count`).  
Separar la lógica de cada parte en componentes específicos (p. ej. `VoltageDetectorA`, `VoltageDetectorB` o estrategias) mejoraría el cumplimiento de SRP.

### OCP (Open/Closed Principle)

- La API expone `executeA` y `executeB`, cada uno con su propia forma de procesar las listas de dígitos.  
- Añadir una nueva variante (parte C) podría hacerse añadiendo un nuevo método que reutilice el pipeline `Stream<String> -> List<Integer>` 
- y sólo cambie la función de transformación, lo cual es relativamente compatible con OCP.
- No obstante, toda la lógica está embebida directamente en `VoltageDetector`. Si se extrajeran las transformaciones a estrategias independientes,  
- se podrían añadir nuevos comportamientos sin modificar la clase principal, solo inyectando nuevas implementaciones.

### LSP, ISP y DIP

- No hay jerarquías ni interfaces, de modo que LSP e ISP no se aplican de forma explícita, aunque el diseño podría evolucionar  
hacia interfaces de “generación de valor” por línea.
- Introducir una interfaz como `LineProcessor` o `VoltageStrategy` y recibirla en `VoltageDetector` permitiría depender de  
abstracciones en lugar de implementaciones concretas (DIP), lo que simplificaría pruebas y extensibilidad para futuros días   
o variantes del problema.

---

## Principios de ingeniería de software

### KISS (Keep It Simple, Stupid)

- Métodos como `toList`, `addToList`, `count` y `toInt` son sencillos y directos, lo que cumple bien con KISS y hace el código fácil de seguir.
- La lógica de `generatePairList` es más sofisticada (cola monotónica y control con `n`), pero está contenida en un  
- solo método y encapsula un algoritmo claro para quien esté familiarizado con este tipo de técnicas; un comentario breve explicando  
- la intención (conseguir el máximo número de 12 dígitos) podría ayudar a mantener la simplicidad percibida.

### DRY (Don’t Repeat Yourself)

- El paso de parseo de cada línea (`Stream<String> -> List<Integer>`) está centralizado en `toList`, y tanto `executeA` como `executeB` lo reutilizan,  
- evitando duplicación de lógica de conversión.
- El almacenamiento y suma de resultados también está reutilizado (`addToList`, `count`), así que ambas partes comparten  
- la misma infraestructura de acumulación, cumpliendo el principio DRY.

### Clean Code (nombres, side effects y colecciones)

- Los nombres `executeA`, `executeB`, `generatePair`, `generatePairList` y `toList` dan una idea razonable de su finalidad,  
aunque podrían ser aún más específicos (por ejemplo, `generateMaxPairValue` y `generateMax12DigitValue`) para aclarar mejor la intención.
- El diseño fluido `VoltageDetector.create().executeA(stream).count()` es cómodo de usar, pero `VoltageDetector`  
tiene estado interno mutable en `values`, por lo que reutilizar la misma instancia con distintos streams acumularía resultados;  
documentar este comportamiento o devolver una nueva instancia en cada `executeX` mejoraría la claridad. 

---

## Patrones de diseño

### Fluent Interface para el procesamiento (Factory Method y Fluent API)

- El patrón de uso:
  ```java
  long sum = VoltageDetector.create()
      .executeA(stream)
      .count();
