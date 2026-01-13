# Día 11 – Advent of Code 2025
## Red de Dispositivos

Este día trabaja con un grafo dirigido de servidores (nodos tipo you, dac, fft, out…) y conexiones entre ellos, donde se  
pide contar caminos desde un origen hasta out bajo diferentes restricciones.  

La **parte 1** requiere contar todas las rutas simples de you a out

La **parte 2** contar caminos que pasan obligatoriamente por dac y fft, usando una combinación de backtracking y programación 
dinámica con memoria de estado.  


---


## Parte 1: Todas las rutas simples de "you" a "out"


`PathFinder` mantiene un mapa graph de String a lista de String, donde cada entrada de la entrada de texto   
define un nodo y sus vecinos: se parsea con createNodes, que separa cada línea por espacios, toma la palabra inicial  
(sin el : final) como clave y el resto como lista de nodos alcanzables.  

El método `nWaysA(List<String> input)` es le método principal para la parte 1, con el input inyectado:    
- Inicializa el grafo llamando a `createNodes(...)`, el método formatea la cada string del input y genera un mapa  
con todas las conexiones entre nodos.  
- Crea una lista currentPath empezando en "you".  
- Llama a `backtracking(currentPath, "you")`, que explora recursivamente todos los caminos posibles:  
  - Si el nodo actual es "out", añade la ruta completa a allPaths.  
  - Si no, itera sobre los vecinos de `graph.get(actual)`; por cada vecino `s` que no esté ya en currentPath (evitando ciclos)  
  añade `s` al camino, recurre, y luego hace backtracking eliminando el último nodo.  

Al final, `nWaysA` devuelve `allPaths.size()`, que es el número total de caminos simples de **you** a **out**.


## Parte 2: Caminos que pasan por "dac" y "fft"

En la parte 2 se introduce una condición adicional: contar sólo los caminos desde un nodo de inicio (p. ej. `svr`) hasta  
out que pasen al menos una vez por los nodos `dac` y `fft` en cualquier orden.  

`countPathsThroughDacAndFftFrom(String start, List<String> input)` vuelve a construir el grafo con createNodes y llama a `countPathsWithFlags(start, false, false, new HashMap<>())`.

`countPathsWithFlags` aplica memoization, siendo en este tipo de casos más eficiente que el backtracking. Cada estado del algoritmo  
está dado por: (nodo actual, ¿hemos visto dac?, ¿hemos visto fft?).  

En cada llamada, se actualizan las banderas:

- `newDac = dac || current.equals("dac")`, verificando si el nodo actual es uno de los buscados.
- `newFft = fft || current.equals("fft")`

Se construye una clave de cache tipo **"current:dac:fft"**; si ya está en cache, se devuelve ese resultado directamente.  
Para resolver cualquier problema con memoization, hay que exponer los casos base:  
- Caso base: si `current.equals("out")`, se devuelve 1 si ambas banderas son verdaderas `(newDac && newFft)`, y 0 en caso contrario, almacenando el resultado en la cache.  
- Caso recursivo: se suman los resultados de todas las llamadas recursivas a los vecinos:  

Con esto asignamos un 1 a los caminos válidos y un 0 a los inválidos, así al final del recorrido sumamos todo y obtenemos  
el nº de caminos que han pasado por ambos nodos, sin importar el orden.



---



## Principios SOLID aplicados
### SRP (Single Responsibility Principle)

- PathFinder se centra en una responsabilidad clara: dada la descripción del grafo, contar caminos bajo distintas reglas,  
manteniendo el modelo del grafo (graph) y reutilizando la misma estructura para ambas partes.  

- Métodos como createNodes, backtracking y countPathsWithFlags separan razonablemente la construcción del grafo,  
la búsqueda exhaustiva de rutas simples y el conteo con restricciones y memoización.  

### OCP (Open/Closed Principle)
- El grafo se construye de forma genérica con createNodes, por lo que es posible introducir nuevas variantes de conteo  
(por ejemplo, rutas con otro conjunto de nodos obligatorios) implementando nuevos métodos de recuento sin tocar la construcción de graph.
- La función countPathsWithFlags encapsula bien la idea de “contar caminos con estado adicional”; para soportar más nodos  
especiales, se podría generalizar la clave de cache y las banderas, sin cambiar la estructura global de la solución.  

### LSP, ISP y DIP
No hay herencia ni interfaces, por lo que LSP e ISP no se aplican explícitamente; todas son clases concretas.

Como en el resto de días, se podría aplicar alguna interfaz pero como es una implementación muy específica, no le veo sentido (DIP).


---

## Principios de ingeniería de software
### KISS (Keep It Simple, Stupid)

- La parte 1 usa backtracking clásico con una lista mutable currentPath, añadiendo y removiendo el último elemento al  
avanzar/retroceder; el algoritmo es directo y refleja bien el enunciado “encontrar todos los caminos de you a out”.  


- La parte 2 mantiene una recursión simple donde la transición es “suma de caminos de los vecinos”, y el único extra son  
dos banderas booleanas y un mapa de cache, lo que encaja bien con el enfoque de DP mencionado en las soluciones oficiales.  

### DRY (Don’t Repeat Yourself)
- La lógica de construcción del grafo (createNodes) es compartida por ambos métodos públicos (nWaysA y countPathsThroughDacAndFftFrom), evitando duplicar parseo del input.

- El uso de key(current, dac, fft) unifica la forma de construir las claves para la cache en la parte 2, centralizando la lógica de “estado comprimido” en un solo método.

### Clean Code (nombres, claridad y cache)
Nombres como `nWaysA`, `countPathsThroughDacAndFftFrom`, `countPathsWithFlags`, `createNodes` y `backtracking` describen adecuadamente su intención en el contexto del puzzle.

El objeto allPaths se usa solo en la parte 1; reciclarlo o limpiarlo entre llamadas (o devolver directamente el recuento sin almacenarlos todos)   
podría reducir consumo de memoria cuando hay muchos caminos.  

## Patrones de diseño y mejoras sugeridas
### DFS/backtracking y DP con memoization
- La parte 1 es un DFS con backtracking puro: se exploran todas las rutas posibles, evitando revisitar nodos ya presentes en el camino,  
y se acumulan rutas completas en una lista.  
- La parte 2 combina DFS con memoization (un patrón de programación dinámica top‑down), recordando resultados de subproblemas  
(nodo, seenDac, seenFft) para no recalcularlos múltiples veces, lo que reduce drásticamente la complejidad en grafos con muchas rutas compartidas entre subcaminos.  
