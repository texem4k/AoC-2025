# Día 5 – Advent of Code 2025

## Analizador de alimentos

En este día se procesan rangos de IDs de ingredientes y listas de identificadores concretos para determinar cuáles son  
**ingredientes frescos válidos**. Deberá devolver tanto el número de ingredientes válidos (Parte A)
como la cantidad total de IDs cubiertos (Parte B).  

---


## ¿Cómo será el input según la página de AoC?

Será parecido al día 2. Una serie de string formadas por:

- número - número -> Rango de IDs alimentos frescos.
- número -> ID de un alimento

---


## Parte 1: Identificar ingredientes frescos válidos

La clase `FoodAnalyzer` mantiene dos estructuras principales: `ranges` (lista de rangos válidos) y `freshIngredients` (lista de IDs de ingredientes frescos válidos).  

El método `add(List<String> s)` procesa la entrada completa llamando a `setRanges(s)`, filtrando las string que contengan "-", para detectar y registrar todos los rangos `min-max`,  
y luego obtiene los IDs sueltos mediante `getIDs(s)`, que filtra las cadenas que no contienen `"-"` ni espacios y no están vacías, mapeandolas a `long` y aplicando `validID` en cada uno. 

La función `validID(long ID)` comprueba dos condiciones:  

- Que el ID pertenezca al menos a uno de los rangos (`ranges.stream().anyMatch(r -> ID >= r.min() && ID <= r.max())`).  
- Que aún no esté en la lista `freshIngredients`, para evitar duplicados.  

Si ambas se cumplen, se añade el ID a `freshIngredients`.   
El método `count()` devuelve el tamaño de `freshIngredients`, es decir, el número de ingredientes frescos válidos distintos encontrados en la entrada.

---

## Parte 2: Calcular la cobertura total de rangos

La segunda parte se centra en calcular cuántos IDs están cubiertos por la unión de todos los rangos, teniendo en cuenta solapamientos y rangos adyacentes. 

Gracias a la fluent API, tanto la parte 1 cómo la parte 2 usan la misma inicialización. Por lo que se evita la repetición de código.  

El cambio relevante es la llamada del método `getRangesIds()`, en lugar del `count()`. `getRangeIds()` hace lo siguiente:

- Crea una copia ordenada de los rangos por su mínimo (`sortRanges()` ordena por `Range::min`).
- Inicializa `curMin` y `curMax` con el primer rango de la lista.
- Recorre el resto de rangos:  
    - Si `next.min() <= curMax + 1`, los rangos se solapan o son adyacentes, así que se “fusionan” actualizando `curMax` al máximo entre ambos.  
    - Si no, se cierra el rango actual y se calcula la diferencia del rango actual y avanzamos al siguiente rango.  

Al final, se añade la longitud del último rango fusionado a `count` y se devuelve el número total de IDs cubiertos por la unión de todos los rangos.

---

## Estructura de Datos, Range

Es el mismo Range usado en el día 2. 

- Variables `min` y `max` que definen el inicio y final del rango.
- Método `getRange()` que devuelve un LongStream con todos los valores dentro del rango

La justificación es clara, un rango es un objeto propio con propiedades (min y max) y posee un rango de IDs intermedios.

---

## Principios SOLID aplicados

### SRP (Single Responsibility Principle)

- `FoodAnalyzer` se centra en una única área de responsabilidad: analizar ingredientes y rangos para determinar qué IDs son válidos y cuántos IDs cubren los rangos.  

### OCP (Open/Closed Principle)

- La lógica de fusión y conteo de rangos está encapsulada en `getRangesIds`/`rangesIDS` y `sortRanges`, lo que permite reutilizar y extender  
la funcionalidad alrededor de rangos sin tocar la forma en que se validan IDs individuales.  
- Si en futuros ejercicios cambiasen las reglas de “validez” de un ID (por ejemplo, incluir otro tipo de filtro), sería conveniente  
extraer la validación a una estrategia o método configurable en lugar de modificar `validID` directamente, favoreciendo la extensión sin modificación.  

### LSP, ISP y DIP

- No hay jerarquías ni interfaces en este diseño, así que LSP e ISP no se aplican explícitamente.  
- Para cumplir mejor DIP en un contexto más amplio, se podría definir una interfaz para la validación de IDs  
(p. ej. `IdValidator` con `boolean isValid(long id, List<Range> ranges)`) e inyectarla en `FoodAnalyzer`, permitiendo  
cambiar las reglas sin acoplar la clase a una implementación concreta.  

---

## Principios de ingeniería de software

### KISS (Keep It Simple, Stupid)

- La lógica de filtrado de rangos (`setRanges`) e IDs (`getIDs`) está expresada con streams sencillos, con  
condiciones claras, lo que sigue bien el principio KISS.  
- Si bien, una parte de `FoodAnalyzer` es algo compleja debido al método `getRangesIds`. Pero, no he encontrado formas más sencillas.  
aunque, de todas formas, sigue siendo un método que si bien a la primera no se entiende mucho, si se analiza paso a paso  
se puede entender bien.  

### DRY (Don’t Repeat Yourself)

- La lógica de ordenado de rangos está centralizada en `sortRanges`, reutilizada tanto por `getRangesIds` como por `rangesIDS`, evitando duplicación en el ordenado.
- No hay repetición explícita de código, por lo que el principio es aplicado correctamente.

### Clean Code (nombres, estructuras y side effects)

- Los nombres `FoodAnalyzer`, `ranges`, `freshIngredients`, `getIDs`, `sortRanges` y `validID` son bastante expresivos y ayudan a entender el código.  

---

## Patrones de diseño

### Fluent Interface y Factory Method

- La clase expone `FoodAnalyzer.create().add(lines).count()` como flujo de uso, devolviendo `this` en `add`, lo que encaja con  
un estilo fluido que encadena construcción, carga de datos y cómputo del resultado.
- Este enfoque hace que el código cliente sea conciso y legible, especialmente en el contexto de un puzzle donde cada día  
se resuelve con un pipeline claro de entrada → análisis → resultado.
  

#### By Texenery Bordón Rodríguez