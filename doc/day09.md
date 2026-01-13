# Día 9 – Advent of Code 2025

## Rectángulo de mayor área

En este día se trabaja con puntos que forman un polígono (generalmente una frontera de baldosas) y se buscan rectángulos donde
al menos dos esquinas opuestas deben ser una baldosa.

La solución tiene dos partes: en la parte 1 se considera el área del rectángulo inclusivo definido por dos puntos, y en la  
parte 2 se busca el rectángulo con mayor área dentro de una región formada por baldosas verdes que tienen límite. 

---

## Parte 1: Mayor área de rectángulo con puntos opuestos

Tras inicializar la clase `AreaResolver`, se le pasa el input al método `findLargestAreaA(List<String> input)`, que usando  
varios métodos devuelve el área obtenida.

Primero, se obtienen las coordenadas de cada baldosa roja, para ello se llama al método `createPointList(...)`, que llama a `createPoint()` y a su vez 
este llama a `formatString(s)` para dividir la string y devolver un `Point` con las coordenadas. Creando una lista de puntos.  

Seguidamente, obtenemos la lista de rectángulos posibles, llamando a `setRectangles(List<Point> points)` que a partir de una lista  
de puntos, genera rectángulos para los puntos i e i+1.

Finalmente, con la lista de rectángulos, se hace un sort para ordenarlos de menor a mayor en función de su área, y se elige  
el último elemento.


---

## Parte 2: Mayor rectángulo dados unos límites

En esta parte 2 cambia totalmente el procedimiento, ya que ahora hay que generar el rectángulo de mayor área encerrado dentro de
unos límites.

Para iniciar, se llama al método `findLargestAreaB(List<String> input)` y le inyectamos el input, para comenzar a trabajar.


Al igual que antes llamamos a `createPointList(..)` para obtener los puntos y creamos los rectangulos con `setRectangles(...)`.

Aquí es donde cambia la cosa, para cada rectángulo, se valida con `isValidRectangle(...)`, que llamando a `boundaryCrossesInterior(...)`  
verifica si:

- Está dentro de la zona formada por las baldosas verdes, usando puntos y la posición del rectangulo.

Usando `isPointInPolygon(...)` verifica si el centro del rectángulo está dentro de la región. Sirve para podar rápidamente los rectángulos  
inválidos.

Para todos los rectángulos que cumplan con lo anterior, se les calcula su área y se escoge el máximo.

---
## Estructura de Datos, Rectangle y Point
`Rectangle` representa un rectángulo en sí, pudiendo obtener su área, y sus valores centrales. Indispensable para este ejercicio.

Por otro lado, `Point` sirve para simplificar el uso de estructuras de 2 elementos.

`Rectangle` contiene:

- `minX`, `maxX`, `minY`, `maxY`, representa cada punto de la figura.
- `area()`, devuelve el área de la figura
- `centerX/Y()`, devuelve el punto céntrico X e Y. 
- `of(Point a, Point b)`, crea la figura usando los máximos y mínimos de cada punto para representar el rectángulo.

## Principios SOLID aplicados

### SRP (Single Responsibility Principle)

- `Point` y `Rectangle` son value objects inmutables que encapsulan coordenadas y cálculos sencillos (como `areaInclusive` y los centros `centerX/centerY`),  
manteniendo una responsabilidad clara cada uno.
- `AreaResolver` tiene una responsabilidad de alto nivel: a partir de una lista de puntos, calcular áreas máximas según las  
reglas de cada parte. No obstante, mezcla parseo de entrada, generación de combinaciones y lógica geométrica avanzada  
(punto en polígono y cruces de segmentos), que podrían extraerse a servicios auxiliares para un SRP más estricto.  

### OCP (Open/Closed Principle)

- Las operaciones geométricas clave (`isPointInPolygon`, `boundaryCrossesInterior`, `intervalsOverlapOpen`) están encapsuladas  
en métodos privados, lo que facilita ajustar el criterio geométrico sin tocar la estructura principal del flujo de cálculo.  
- Si en un futuro cambian las reglas (por ejemplo, permitir que el rectángulo toque los bordes de cierta forma), basta con  
ajustar estos métodos sin reescribir `findLargestValidRectangleArea` o `allBoxes`.  

### LSP, ISP y DIP
No hay herencia ni interfaces, por lo que LSP e ISP no se aplican explícitamente; todas son clases concretas.

Como en el resto de días, se podría aplicar alguna interfaz pero como es una implementación muy específica, no le veo sentido (DIP).


---

## Principios de ingeniería de software

### KISS (Keep It Simple, Stupid)

- La parte 1 se plantea de forma muy directa: generar todos los rectángulos posibles a partir de pares de puntos,  
calcular su área inclusiva y seleccionar el máximo, lo que es fácil de entender.  
- En parte 2, aunque la lógica geométrica es más compleja por naturaleza, está separada en funciones  
`boundaryCrossesInterior`, `intervalsOverlapOpen` e `isPointInPolygon`, lo que ayuda a que cada pieza sea manejable y reducible mentalmente.  

### DRY (Don’t Repeat Yourself)

- La generación de cajas/rectángulos a partir de pares de puntos se encapsula en `setRectangles` (parte 1) y `allBoxes` (parte 2), evitando repetir bucles anidados dispersos.  
- El test de solapamiento de intervalos `intervalsOverlapOpen` se usa tanto en el caso vertical como en el  
horizontal dentro de `boundaryCrossesInterior`, centralizando la lógica de detección de solapamiento estricto.  

### Clean Code (nombres, estructuras y claridad)

- Nombres como `calculateBest`, `findLargestValidRectangleArea`, `isPointInPolygon`, `boundaryCrossesInterior`, `intervalsOverlapOpen` y `areaInclusive` 
describen con bastante fidelidad lo que hacen, lo que facilita leer el código.  
- La firma de `isPointInPolygon(double x, double y, boolean inside)` sugiere que `inside` debería ser un estado interno, 
pero siempre se llama con `false`; simplificar la firma (sin el parámetro `inside`) o documentar su uso mejoraría la claridad.  

---

## Patrones de diseño

### Value Objects para geometría básica

- `Point` y `Rectangle` como `record` son ejemplos claros de value objects: igualdad por valor, inmutabilidad y  
encapsulación de operaciones asociadas (como `areaInclusive` y centros).  
- En parte 2, aunque no se muestra el `record Box`, la forma de usarlo (`Box.of`, `areaInclusive`, `centerX`, `centerY`, `minX`, `maxX`, etc.)  
sigue el mismo patrón, lo que apunta a una buena modelización del dominio geométrico.  

### Factory Method
- Cómo en el resto de días, hay un método static dedicado a la creación del AreaResolver, `create()`.
