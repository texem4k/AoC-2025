# Día 7 – Advent of Code 2025
## Rayos de Taquiones
En este día se simulan rayos que caen desde un punto de inicio en una cuadrícula y que, al encontrarse con splitters,  
se bifurcan en dos, calculando tanto cuántas veces se produce una división como cuántos caminos distintos alcanzan el final.

Por otro lado, la parte 2 pide el nº total de caminos disponibles.

---

## Parte 1: Contar divisiones de rayos

Para inicializar la clase `TachyonDiagram`, simplemente se le pasa el input al `create(...)`.  

En la inicialización, se crean varias variables mutables:

- Set<Position> cells -> Contiene la posición de todos los splitters
- List<String> grid -> Representa la cuadrícula (Es viable hacer una clase Grid, cómo en el día 4).  
- Map<Position, Long> mem ->  Representa una caché para la parte 2, que usa Memoization.  

Para empezar, se llama al método `splitTimes()`, a grosso modo es un algoritmo de búsqueda BFS que busca en amplitud (nivel por nivel),  
por ello, no se ha simplificado mucho.  

El algoritmo empieza creando una cola `queue` y añadiendo el primer rayo que sale, cuya posición inicial es (x,1), donde  
x representa el índice donde se encuentra `S`, la salida.

Como es un BFS, se recorre hasta que la cola no tenga más nodos...
- Si tiene nodos, se verifica que no se han salido de los límites fila/columna.
- Seguidamente, nos preguntamos si en la posición actual hay un splitter.
  - Si es así, el rayo se parte en 2 y crea 2 rayos NUEVOS, si ambos no se pasan de los límites, se añade a la cola.
- En caso de no detectar ningún splitter, avanzamos con `goDeeper()`, actualizando la posición del rayo en y+1 y añadiéndolo a la cola.


Si bien no es muy Clean Code que digamos, es un método que funciona y es eficiente, sin entrar en demasiada complejidad.

---

## Parte 2: Número total de caminos
A diferencia de la 1º Parte, aquí ya no funciona el BFS, ya que fuerza a recorrer todos los posibles. Por ello había que usar alguna  
estrategia más eficiente.  

En esta parte, se usa `mem` para almacenar soluciones ya calculadas y evitar repetir el cálculo.

El memoization realiza:
- Casos base, devuelve 1 si ha llegado a la última fila o 0 si no.
- Crea una posición nueva usando los parámetros
- Si el mapa contiene la posición, se devuelve.
- Si no, se verifica si estamos en un splitter, si es así, realiza recursividad de los dos rayos creados. Si no, se hace  
recursividad avanzando hacia abajo.  

Como el algoritmo devuelve 1 y 0, se deben agrupar todos los resultados, para eso se crea una variable dentro del algoritmo  
que toma el valor cuando se hace recursividad.  


---

## Estructuras de Datos, Position, Ray

Position ayuda a simplificar el uso de indices, pudiendo agruparlos en un objeto.
Ray representa un rayo persé. Esta clase es esencial ya que no sólo almacena su posición, sino que varía usando `goDeeper()`.  
Además, de poder crear nuevos rayos con sus atributos respectivos, lo cual a nivel abstracción, tiene todo el sentido que  
al dividirse un rayo, se creen otros nuevos.

Position tiene:
- Variables `x` y `y`, que representan la columna y la fila respectivamente.

Por otro lado, Ray:
- Formado por int `x` e `y` (Podría haber sido un position también, pero el position se usa para posiciones del diagrama)
- `splitRight(...)` y `splitLeft(...)` -> métodos que devuelven un nuevo rayo con atributos actualizados
- `goDeeper()` -> actualiza la posición `y` del rayo a +1.

---


## Principios SOLID aplicados
### SRP (Single Responsibility Principle)

- `Position` es un value object inmutable que representa una coordenada (x, y) y se usa como clave de estructuras (Set, Map), cumpliendo una responsabilidad clara.

- `Ray` encapsula el estado y movimiento de un único rayo (avanzar, dividirse en izquierda/derecha), separado de la lógica de la cuadrícula,  
aunque su equals es parcial (no comprueba el tipo ni hashCode), lo que podría mejorarse si se usa en colecciones.

- `TachyonDiagram` concentra la lógica de simulación (BFS para divisiones y DFS con memo para caminos) y el acceso a la cuadrícula,  
lo que es razonable como “servicio de dominio” del puzzle, aunque podría extraerse la lógica de caminos en una clase/servicio separado si creciera.

### OCP (Open/Closed Principle)
- El uso de métodos auxiliares (isSplitter, inLimits) aísla ciertos detalles y facilita cambios futuros en los símbolos o reglas de límites sin tocar toda la lógica de recorrido.

- Si el puzzle introdujera nuevos tipos de celdas (por ejemplo, reflejos o bloqueos), se podría ampliar isSplitter o añadir  
nuevos métodos de comportamiento sin cambiar la estructura básica de splitTimes y paths, aunque ahora mismo todo está codificado alrededor de '^'.

### LSP, ISP y DIP
No hay herencia ni interfaces, por lo que LSP e ISP no se aplican explícitamente; todas son clases concretas.

Como en el resto de días, se podría aplicar alguna interfaz pero como es una implementación muy específica, no le veo sentido (DIP).

---

## Principios de ingeniería de software
### KISS (Keep It Simple, Stupid)

- El algoritmo de `splitTimes` sigue un BFS sencillo con comprobaciones de límites claras y una condición simple para decidir si dividir o seguir bajando, lo que facilita su comprensión.
- `paths` expresa la recursión de manera directa: casos base (salir de la rejilla) y un caso recursivo que suma los caminos horizontales o  
continúa hacia abajo, con memoización mínima; añadir comentarios sobre qué representan row y col ayudaría aún más.  

### DRY (Don’t Repeat Yourself)
- La lógica de comprobación de límites se centraliza en inLimits para las ramas nuevas y en las primeras condiciones de paths para la recursión, evitando duplicar comprobaciones dispersas.
- El uso de Position como value object evita duplicar la representación de coordenadas (no se usan pares sueltos de enteros para las claves del Map y del Set, sino un tipo dedicado).

### Clean Code (nombres, side effects y estructuras)
- Nombres como splitTimes, allPaths, getStartRay, isSplitter, goDeeper y splitLeft/Right describen bien su intención, alineados con las ideas del enunciado (rayos, divisores, caminos).

## Patrones de diseño (Factory Method)

- Position como record es un ejemplo claro de value object inmutable: igualdad por valor, uso seguro en Set y Map, código muy conciso.  
Esto encaja muy bien en problemas de rejilla, donde las coordenadas son parte del dominio y se comparan con frecuencia.

- Como el resto de días, está implementado el Factory Method.