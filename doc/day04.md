# Día 4 – Advent of Code 2025
## Detector de rollos accesibles

En este ejercicio se pide detectar que rollos de papel son accesibles en una cuadrícula.
Los rollos están representados por @. El grid de ejemplo es el siguiente:

..@@.@@@@.  
@@@.@.@.@@  
@@@@@.@.@@  
@.@@@@..@.  
@@.@@@@.@@  
.@@@@@@@.@  
.@.@.@.@@@  
@.@@@.@@@@  
.@@@@@@@@.  
@.@.@@@.@.  

Un rollo es accesible cuando a su alrededor (lados y diagonales, 8 posiciones adyacentes) hay menos de 4 rollos.


## ¿Cómo será el input según la página de AoC?

Será una cuadrícula como la anterior.

---


## Parte 1: Contar y eliminar rollos accesibles


Se inicializa `RollsGetter(List<String> input)`, crear un objeto `Grid` y la lista `dropedPositions` en el constructor de `RollsGetter`


La clase `Grid` recibe la entrada como `List<String>` y la almacena en `gridScheme`, detectando todas las posiciones de rollos '@'  
en el constructor mediante `setPositions()`, que rellena el `HashSet<Position> positions`.

Seguidamente, se llama a `countRolls()`, que recorre cada posición del Grid y verifica si es un rollo y si hay menos de 4 rollos alrededor.  
En caso de ser accesible, aumenta el contador y se añade la posición a la lista de `dropedPositions`.  

Una vez que termina de recorrer el grid, elimina las posiciones alojadas en dropedPositions. Para ello, se llama al método  
`eliminatePosition(Position)`, el cuál usa el grid para actualizar el estado y remover el rollo del esquema de Grid.  


---

## Parte 2: Iterar hasta la estabilidad

La segunda parte introduce un bucle que repite el proceso de eliminación hasta que ya no se puedan eliminar más rollos.

En `RollsGetter`, el método `loop()` llama inicialmente a `countRolls()` para obtener cuántos rollos se eliminan en la primera  
pasada y guarda ese valor en count. Después, mientras el resultado de `countRolls()` siga siendo mayor que 0, continúa llamándolo y  
acumulando en count el número total de rollos eliminados a lo largo de todas las iteraciones.


Cada llamada a `countRolls()` actualiza la rejilla eliminando los rollos marcados (pasando '@' a 'x' con `grid.setChar` y eliminando  
la posición del HashSet `grid.positions`), de modo que en la siguiente iteración los vecinos se recalculan ya sobre el estado actualizado.  

Al finalizar el bucle, `loop()` devuelve el número total de rollos eliminados, que corresponde a la respuesta de la parte B.  

---

## Estructura de Datos, Grid y Position

A efectos prácticos y de sencillez, vi lógico representar una cuadrícula (`Grid`), principalmente por su mutabilidad en la  
parte 2, evitando la clase principal aloje la propia cuadrícula.  

Por otro lado, `Position` representa como dice su nombre, una posición. Con atributos enteros `row`e `col`. Aunque si bien
creo que no es necesario, si simplifica bastante la complejidad.


Clase mutable `Grid`:

- `List<String> gridScheme` -> Representación del estado actual de la cuadrícula
- `HashSet<Position> positions` -> Set que aloja todas las posiciones de los rollos, accesibles o no.


Clase record `Position`:

- Atributos int `row` y `col`.  

---

## Principios SOLID aplicados

### SRP (Single Responsibility Principle)
- `Position` es un value object simple e inmutable, que representa una coordenada (row, col) y cumple perfectamente SRP.
- `Grid` tiene varias responsabilidades: almacenar la rejilla (`gridScheme`), gestionar el conjunto de posiciones de rollos (`positions`),  
actualizar caracteres y contar vecinos. Podría dividirse en una capa de datos (sólo la rejilla) y otra de lógica de “vecindad” o de “ocupación”  
para aligerar responsabilidades.  
 
 
- `RollsGetter` se encarga tanto de orquestar el proceso (bucle de eliminación) como de aplicar la lógica de eliminación  
(`isRoll`, `eliminatePosition`), lo que mezcla coordinación y reglas de negocio. Extraer la estrategia de eliminación a  
una clase o interfaz separada reforzaría SRP.


### OCP (Open/Closed Principle)
El diseño actual permite extender el comportamiento a nuevas reglas de accesibilidad modificando countRolls(Position) o  
el criterio de eliminación en RollsGetter (< 4 vecinos) sin tener que cambiar la representación de la rejilla o las posiciones.  
 
Sin embargo, la lógica está acoplada a un único tipo de entidad ('@') y a una regla fija de vecinos < 4. Si en futuros puzzles  
quisieras cambiar el símbolo, el umbral o la forma de contar vecinos, sería mejor encapsular esos parámetros  
(por ejemplo, ROLL_CHAR, MIN_NEIGHBORS_TO_SURVIVE) o extraer una estrategia configurable.  


### LSP, ISP y DIP
No se emplean jerarquías de clases ni interfaces, así que LSP e ISP no entran en juego de forma directa,  
pero Position y Grid se comportan como value objects y servicios concretos de dominio.

---

## Principios de ingeniería de software
### KISS (Keep It Simple, Stupid)
- El conteo de vecinos en `Grid.countRolls` usa dos bucles anidados simples sobre offsets de -1 a 1, con condiciones claras  
para límites, siguiendo el patrón clásico para vecinos en una rejilla 2D.
  

- La función `loop()` expresa el algoritmo de forma directa: llamar a `countRolls` hasta que no queda nada por eliminar,  
acumulando la suma. Es una estructura de bucle sencilla y fácil de entender, lo que respeta KISS.

### DRY (Don’t Repeat Yourself)
El cálculo de vecinos está centralizado en `Grid.countRolls(Position)`, de modo que cualquier sitio que quiera saber los  
vecinos se apoya en ese único método, evitando duplicar lógica de vecindad.

La lógica de eliminación (eliminateTakenPositions y eliminatePosition) se reutiliza en cada iteración y mantiene en un  
solo lugar la sincronización entre el gridScheme y el HashSet positions, evitando duplicidad de código de actualización.

### Clean Code (nombres, side effects y estructuras)
Los nombres `Grid`, `Position`, `RollsGetter`, `countRolls`, `loop` y `eliminateTakenPositions` describen razonablemente su intención,  
aunque `cols()` y `rows()` parecen estar invertidos respecto a lo habitual: `cols()` devuelve `gridScheme.size()` (número de filas)  
y `rows()` la longitud de la primera cadena (número de columnas). Ajustar nombres o implementación mejoraría la claridad.

`Grid.positions` es public, lo que rompe un poco el encapsulamiento y permite modificar el set desde fuera; encapsularlo  
mediante métodos y hacerlo privado mejoraría la robustez y seguiría las recomendaciones de Clean Code.

## Patrones de diseño

### Encapsulación y separación de responsabilidades
- `Grid.positions` podría hacerse privado y exponerse a través de métodos como hasRoll(Position) y removeRoll(Position),  
encapsulando la estructura interna. Esto evitaría que código externo manipule el set directamente y mantendría Grid como  
única fuente de verdad sobre qué posiciones están ocupadas.
- La clase `RollsGetter` actúa como un orquestador del proceso; separar explícitamente un servicio de “simulación” (el bucle)   
de otro de “contabilización” permitiría reutilizar Grid y Position en otros contextos sin arrastrar la lógica de eliminación.