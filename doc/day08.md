# Día 8 – Advent of Code 2025

## Circuitos de JunctionBoxes o JB

En este día se trabaja con **cajas de conexiones** (`JunctionBox`) en un espacio 3D, conectándolas en función de la distancia  
entre ellas para formar circuitos (componentes) cada vez más grandes.  

La solución genera todos los pares de cajas, los ordena por distancia euclídea y va uniendo circuitos según esos pares,  
calculando en la parte A el producto de los tamaños de los tres circuitos más grandes y en la parte B la posición de la última unión relevante.  

---

## Parte 1: Producto de los tres circuitos más grandes

El método `threeLargestCircuit(List<String> input)` delega en `operateA`, que realiza estos pasos:

- `generateJBList(input)`: convierte cada línea `"x,y,z"` en un `JunctionBox` usando `createJunctionBox`, que hace él parseo y la conversión a `long`.
- `generatePairs(junctionBoxes)`: crea una lista de `PairJunctionBox` con todos los pares posibles de cajas, recorriendo índices `i < j`.
- `sortPairsForA(...)`: ordena estos pares por distancia euclídea (método `euclideanDistance` de `PairJunctionBox`) y se queda con los 1000 pares más cercanos. 

Después se llama a `comparePairs(pair, junctionBoxes)`, que:

- Inicializa `circuits` creando un `Circuit` por cada `JunctionBox`, con un `Set` que contiene solo esa caja.
- Recorre los pares ordenados; para cada par `(A, B)` busca los circuitos que contienen `A` y `B` (`searchCircuit`), y si aún  
no están en el mismo circuito, fusiona ambos sets en uno nuevo y reemplaza los circuitos antiguos por el nuevo.  
- A medida que fusiona, va recordando el último par usado en `actual`.  

Al finalizar `operateA`, no se usa el valor devuelto por `comparePairs`, sino la lista `circuits` resultante:

- Se obtiene el tamaño de cada circuito (`junctionBoxSet().size()`), se ordenan de mayor a menor, se toman los tres primeros  
y se calcula el producto de esos tres tamaños con un `reduce(1, (a, b) -> a * b)`.  
- Ese producto es el resultado de la parte A.  

---

## Parte 2: Última unión de cajas (lastJunctionBox)

La parte B reutiliza prácticamente la misma lógica, pero con un criterio diferente de ordenación:  

- `lastJunctionBox(List<String> input)` delega en `operateB`, que genera la misma lista de `JunctionBox` y pares.  
- `sortPairsForB(...)` ordena todos los pares por distancia euclídea, sin limitar el número de pares (él `limit(Long.MAX_VALUE)` actúa como “sin límite”).  
- De nuevo se llama a `comparePairs(pair, junctionBoxes)`, que fusiona circuitos según los pares y mantiene en `actual` el último par utilizado para una fusión real.  

Al final, `comparePairs` devuelve `actual.get().A().x() * actual.get().B().x()`, es decir, el producto de las coordenadas `x` de las dos cajas  
del último par que produjo una fusión de circuitos. Ese producto es la respuesta de la parte B (`lastJunctionBox`).  

---
## Estructuras de Datos: JunctionBox, PairJunctionBox y Circuit

Estas estructuras simplifican en gran medida la complejidad del problema. Siendo todos `record` es decir, inmutables.  
La justificación es por sencillez y comodidad, sobre todo el record `PairJunctionBox` que aloja como método indispensable  
la distancia euclídea, que es el método por el que se ordena los pares de JB.  

Son record por la simple razón de que, un JB no es mutable, no puedes cambiarle su posición (que son sus atributos), al igual que  
cada par de JB tiene una distancia única por las coordenadas de ambos JunctionBoxes, inmutables.  

Dicho esto, **JunctionBox** contiene:
- Coordenadas XYZ cómo `x`, `y`, `z`.

**PairJunctionBox** contiene:
- JunctionBox `A` y JunctionBox `B`.
- Método `euclideanDistance()`, que representa la fórmula de distancia euclidea -> $\sqrt{(x_1-x_2)^2+(y_1-y_2)^2+(z_1-z_2)^2}$  
- Método `pqDistance(...)`  busca simplificar el método anterior, calculado las diferencias de las coordenadas.  

**Position**, como otros días contiene una variable `x` e `y`.

---


## Principios SOLID aplicados

### SRP (Single Responsibility Principle)

- `JunctionBox` representa una caja en el espacio; al ser `record`, encapsula de forma concisa  
su identidad y coordenadas, sin lógica adicional relevante.  
- `PairJunctionBox` representa un par de cajas y sabe calcular su distancia euclídea (`euclideanDistance`) y la distancia  
cuadrática parcial (`pqDistance`), lo que separa claramente la idea de “relación entre dos cajas” de la lógica de unión de circuitos.
- `ConnectionManager` agrupa varias responsabilidades: parseo de entrada, generación de pares, ordenación, unión de circuitos  
y cálculo de resultados para A y B. En un diseño más estricto, podrías separar un componente para la lógica de unión/clusterización y otro para él parseo.   

### OCP (Open/Closed Principle)

- La distancia entre cajas se centraliza en `PairJunctionBox.euclideanDistance()`. Cambiar el criterio de ordenación  
(por ejemplo, usar distancia Manhattan) se podría hacer modificando solo ese método o el comparador, sin tocar el resto de la lógica.  
- La lógica de unión de circuitos está encapsulada en `comparePairs`, de modo que para probar distintos subconjuntos de pares  
(como se hace en A y B con `sortPairsFora` y `sortPairsForB`) no es necesario reescribir el algoritmo de unión, solo cambiar qué lista de pares se pasa.  

### LSP, ISP y DIP
No hay herencia ni interfaces, por lo que LSP e ISP no se aplican explícitamente; todas son clases concretas.

Como en el resto de días, se podría aplicar alguna interfaz, pero como es una implementación muy específica, no le veo sentido (DIP).

---

## Principios de ingeniería de software

### KISS (Keep It Simple, Stupid)

- La construcción de pares (`generatePairs`) usa dos bucles sobre índices y es directa de leer: se generan todas las combinaciones `(i, j)` con `i < j`.  
- La unión de circuitos en `comparePairs` sigue un patrón claro: buscar circuitos, fusionar sets, reemplazar en la lista.  
Aunque podría ser más eficiente con una estructura tipo Union-Find, la implementación actual es sencilla y fácil de entender.  

### DRY (Don’t Repeat Yourself)

- El código de generación de `JunctionBox` a partir de las líneas de entrada está centralizado en `createJunctionBox` y  
`generateJBList`, evitando duplicar el parseo de `"x,y,z"`.  
- La lógica de unión de circuitos se implementa una sola vez en `comparePairs` y se reutiliza tanto para la parte A como para la B,  
variando solo la lista de pares que se le pasa (`sortPairsForA` vs. `sortPairsForB`).   

### Clean Code (nombres, estructuras y pequeños detalles)

- Los nombres `ConnectionManager`, `Circuit`, `JunctionBox`, `PairJunctionBox`, `largestCircuit`, `lastJunctionBox` y `euclideanDistance`  
son expresivos y ayudan a entender el dominio del problema.

---

## Patrones de diseño 

### Value Object para cajas y circuitos

- `JunctionBox` y `Circuit` como `record` son ejemplos de value objects: representan entidades del dominio con igualdad por valor y sin lógica mutable, ideales para usarse en colecciones (`Set`, `Map`).  
- `Circuit` encapsula el conjunto de cajas que forman un componente conectado, lo que hace que el tipo sea semánticamente claro (`Circuit(Set<JunctionBox>)`).  

### Factory Method
- `ConnectionManager` se crea mediante un Factory Method, si bien para clases pequeñas o específicas no es muy necesario,  
es útil para controlar el n.º de instancias o **esconder** lo que hace el constructor.