# Día 10 – Advent of Code 2025

## Fábrica de Máquinas

Este día modela máquinas con **luces** y **botones**: cada botón invierte el estado de ciertas luces, y se busca el número  
mínimo de pulsaciones necesario para alcanzar un estado objetivo o para cumplir unos requisitos de “joltage” (contadores). 

La parte A resuelve un problema de alcance de estados mediante BFS sobre un espacio de estados booleanos, mientras que la 
parte B resuelve un sistema más algebraico sobre contadores usando combinaciones de botones y programación dinámica. 

---

## Parte 1: Mínimas pulsaciones para alcanzar un estado objetivo

`MachineManager` es la fachada principal y expone `fewestButtonPresses(List<String> input)`, que:  

- Construye una lista de `Machine` a partir de la entrada con `buildMachine`.
  - `buildMachine(...)` mapea el input a Machine usando el método `stringToMachine(s)`, método que divide la string y crea  
  la máquina con sus respectivas características.  
  - `stringToMachine` parsea cada línea de entrada:  

    - El primer token es el estado objetivo entre corchetes (por ejemplo `[##.#…]`), convertido a `List<Boolean>` por `createFinalState`.  
    - El último token es la lista de requisitos de joltage entre corchetes, convertido a `List<Integer>` por `getValuesFromString`.  
    - Los tokens intermedios representan botones, también como listas de índices de luces que ese botón alterna, creando `Button(List<Integer> alter)`.  
    - Finalmente se construye la máquina con `Machine.createMachine`, que inicializa `state` con todas las luces a `false`, define `buttons`, `finalState` y `joltageRequirements`.  

- Para cada `Machine`, llama a `bfsState(m)` y acumula el número mínimo de pulsaciones necesarias para alcanzar `finalState` desde el estado inicial (todas las luces apagadas).


En `bfsState(Machine m)` se aplica un BFS clásico sobre el espacio de estados:  

- Cada estado (es decir, las luces) se representa como `List<Boolean>`, true encendido y false apagado; se usa una `Queue<List<Boolean>>` y  
un `Map<List<Boolean>, Integer>` para registrar la distancia (número de pulsaciones) desde el estado inicial.  
- Se arranca con `m.state` (todas apagadas) a distancia 0.  
- Mientras haya estados en la cola:  
    - Se extrae `actual`, se asigna a `m.state` y se comprueba si `actual.equals(m.finalState)`; si es así, se devuelve el número de pulsaciones almacenado.  
    - Si no, se generan estados vecinos pulsando cada botón: `m.pressButton(b)` crea una nueva lista de booleanos con las luces correspondientes invertidas.  
    - Cada nuevo estado que no esté en el mapa se añade con distancia `dist(actual) + 1` y se encola.  

Si por alguna razón no se encuentra el estado final, se devuelve 0.    

---

## Parte 2: Requisitos de joltage y resolución combinatoria

En la parte B, en lugar de un estado booleano se trabaja con requisitos de voltaje o “_joltage_” que indican cuántas veces deben activarse ciertas posiciones (contadores).  
`fewestButtonPressesWithRequirement(List<String> input)` también construye las máquinas, pero para cada `Machine` instancia un `JoltageSolver` y llama a `solve()`, sumando los resultados. 

`JoltageSolver` reinterpreta `Machine.buttons` y `Machine.joltageRequirements` como un problema de combinaciones de botones:  

- Cada botón tiene una lista de índices `alter`, que indican a qué contadores suma 1 cada vez que se pulsa.  
- El objetivo es encontrar la mínima cantidad total de pulsaciones que, aplicadas a los botones, satisfacen exactamente el vector `target` de requisitos.  

La solución se basa en:  

Enumerar todos los patrones posibles de pulsación de botones** (máscara de bits sobre el conjunto de botones) con `buildParityMaps(int nCounters)`:
- Para cada máscara `mask`, `buildPatternForMask` construye un vector `result` donde cada posición es cuántas veces se ha  
incrementado ese contador al pulsar los botones indicados por la máscara, y cuenta `presses`.  
- Se calcula `parity = parityOf(result)` (cada componente módulo 2).
- Se almacena en un mapa `parityMaps` indexado por la paridad, donde cada entrada interna mapea un patrón `result` al número mínimo de pulsaciones que lo generan.


Resolver recursivamente el objetivo** con `findMinPresses(current, parityMaps, cache)`:
- Si todos los componentes de `current` son 0 (`allZero`), no se necesitan más pulsaciones.  
- Si no existe ninguna entrada en `parityMaps` con la paridad de `current`, no hay solución compatible y se retorna `Long.MAX_VALUE`.  
- Para cada patrón `pattern` con la misma paridad y que no excede componente a componente a `current` (`isComponentwiseLessOrEqual`),  
se calcula un estado reducido `next = computeNext(current, pattern)`, donde cada componente es \((current - pattern) / 2\).  
- Se llama recursivamente a `findMinPresses(next, ...)`; si es alcanzable, el coste es `presses + 2 * sub`, eligiendo el mínimo entre todas las opciones.  
- Se cachea el resultado para cada vector `current` para no recomputar subproblemas.  

El resultado de `solve()` es el mínimo número de pulsaciones totales necesarias para satisfacer `target` según estas reglas.  

---
## Estructura de Datos, Machine y Button

`Machine`, como su nombre indica, es la representación clave de una máquina, esta clase contiene todo lo necesario para
poder realizar el ejercicio. Sin una clase Machine que contenga lo su estado inicial/final, los botones y el voltaje 
requerido, el ejercicio es muy difícil de completar. Además de que ayuda a entender y a interiorizar el código.

Por otra parte, `Button` representa una lista con los índices de las luces que alterna estado al ser presionado el botón.

`Machine` contiene:
- `List<Boolean> state`, estado actual y mutable de la máquina;
- `List<Button> buttons`, Lista de botones asociados a esa máquina;
- `public List<Boolean> finalState`, Estado final;
- `public List<Integer> joltageRequirements`, Lista con los valores requeridos de voltaje;

`Button` contiene:
- `List<Integer> alter`, lista con los índices de las luces afectadas por el botón.  


---


## Principios SOLID aplicados

### SRP (Single Responsibility Principle)

- `Machine` modela una máquina concreta (estado de luces, botones, estado final y requisitos de joltage) y proporciona  
la operación `pressButton`, sin mezclar lógica de búsqueda ni resolución algebraica.  
- `MachineManager` coordina la lectura del input, creación de máquinas y elección del algoritmo (BFS de estados o `JoltageSolver`), actuando como capa de orquestación.
- `JoltageSolver` se centra en el problema de joltage: a partir de los botones y el vector objetivo de contadores, calcula  
el mínimo número de pulsaciones mediante generación de patrones y recursión con memoization.  

### OCP (Open/Closed Principle)

- Las dos estrategias para resolver el puzzle (BFS de estados y solver algebraico) están separadas: `fewestButtonPresses` usa `bfsState`, y `fewestButtonPressesWithRequirement` usa `JoltageSolver`.  
Añadir una tercera variante implicaría añadir un nuevo método en `MachineManager` sin modificar los ya existentes.  
- La construcción de `Machine` a partir del input está encapsulada en `stringToMachine`, de forma que un cambio en el formato de  
entrada afectaría principalmente a este método, manteniendo intactos los algoritmos de resolución.  

  
### LSP, ISP y DIP
No hay herencia ni interfaces, por lo que LSP e ISP no se aplican explícitamente; todas son clases concretas.

Como en el resto de días, se podría aplicar alguna interfaz pero como es una implementación muy específica, no le veo sentido (DIP).


---


## Principios de ingeniería de software

### KISS (Keep It Simple, Stupid)

- El BFS de `bfsState` es un patrón estándar: cola, mapa de distancias y expansión por vecinos, con estructura clara y sin optimizaciones prematuras.  
- `JoltageSolver` es necesariamente más complejo por la naturaleza del problema, pero la lógica está dividida en funciones específicas  
(`buildParityMaps`, `buildPatternForMask`, `parityOf`, `findMinPresses`, `computeNext`), lo que facilita entender cada paso por separado.  

### DRY (Don’t Repeat Yourself)

- El parseo de listas numéricas desde el input (`getValuesFromString`) se reutiliza tanto para el vector de joltage final  
como para las definiciones de botones, evitando duplicar la conversión de cadenas a `List<Integer>`.  
- Dentro de `JoltageSolver`, la construcción de patrones para cada máscara está centralizada en `buildPatternForMask`, y la  
lógica de paridad se centraliza en `parityOf`, evitando repetición en varios puntos.  

### Clean Code (nombres, estructuras y estado)

- Nombres como `MachineManager`, `fewestButtonPresses`, `fewestButtonPressesWithRequirement`, `JoltageSolver`,  
`buildParityMaps`, `findMinPresses`, `allZero`, `computeNext` describen bien su intención.  
- `Machine` expone sus campos (`state`, `buttons`, `finalState`, `joltageRequirements`) como públicos y mutables;  
encapsularlos con getters y hacerlos finales donde sea posible mejoraría la seguridad de tipo y evitaría cambios inesperados desde fuera. 

---

## Patrones de diseño

### Separación de estrategias de resolución

- La estructura ya insinúa un patrón **Strategy**: según la parte del ejercicio, se elige un método de resolución distinta (BFS de estados o solver de joltage).
