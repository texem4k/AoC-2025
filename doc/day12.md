# Día 12 – Advent of Code 2025
## PresentFitter

Este día trata de distribuir regalos en distintas regiones, comprobando si cada región tiene suficiente área para albergar  
todos los regalos que le corresponden.  

La solución parsea primero los regalos, después las regiones, y para cada región compara su área disponible con el área  
necesaria para los regalos, sumando cuántas regiones son válidas.  

En este último día solo hay parte 1.

Si bien es cierto que se puede hacer con algoritmos heavys, este día tiene una solución relativamente sencilla.

---


## Cálculo de regiones válidas

PresentDistributor expone un método principal `execute(List<String> input)` que orquesta todo el flujo de cálculo:

- `createPresent(input)` obtiene la lista de Present a partir de la parte del input que describe los regalos.
- `setRegions(input, presents)` construye la lista de Region a partir de las líneas que representan regiones  
(aquellas que contienen "x"), pasando también la lista de Present al constructor de Region.  

Finalmente, recorre la lista de `Region` y suma, para cada una, el resultado de `isValidRegion(r, presents)` (1 si la región es válida, 0 en caso contrario).

El método `isValidRegion(Region r, List<Present> presents)` encapsula el criterio de validez:  
- Una región se considera válida si su `area()` es mayor o igual que el resultado de `r.areaNecessary(presents)`,  
es decir, si el área de la región puede albergar el área requerida por los regalos relevantes.
- Devuelve 1 o 0 según se cumpla la condición, y execute suma estos valores para obtener el número total de regiones válidas.

---

## Estructura de Datos, Region y Present
La justificación es sencilla, es necesario tener representada la región para saber que regalos, debe contener, cuantos debe contener  
y saber si puede o no, usando su área.  

En el lado de los `Present`, es era necesario tener su área, el nº de `#` que tiene.  

`Region` posee:  
- Atributos varios como altura, anchura y la lista con el nº de regalos por cada tipo de regalo.  
- `area()`, devuelve el área de la región  
- `setNeededPresents(s)`,se formatea la string de input para obtener la ristra de regalos necesarios por tipo de regalo.  
- `areaNecessary(List<Present> p)`, realiza un recorrido y obtiene necesaria por cada tipo de regalo.  
- `areaPerPresent(Present p, int i)`, realiza la operación, area del regalo * nº de regalos de ese tipo.  

## Principios SOLID aplicados
### SRP (Single Responsibility Principle)
- `PresentDistributor` actúa como servicio de alto nivel para el puzzle: se encarga de orquestar el parseo del input,  
la creación de Present y Region, y el cómputo del número de regiones válidas, pero no contiene la lógica de cálculo de  
área ni de requisitos de regalos 

- Métodos como `createPresent`, `setRegions`, `formatInput` y `isValidRegion` están razonablemente separados por responsabilidad:  
parseo de regalos, parseo de regiones, formateo del bloque de regalos y criterio de validación.  

### OCP (Open/Closed Principle)
- El criterio de validez de regiones está aislado en `isValidRegion`, lo que facilita ajustar la condición  
(p. ej. añadir márgenes, restricciones adicionales) sin tener que modificar otras partes de execute.

- La forma de construir Present y Region está encapsulada en sus constructores (Present::new, new Region(s, presents));  
si el formato del input cambia, se tocaría principalmente formatInput y/o esos constructores, manteniendo el flujo general.  

### LSP, ISP y DIP

- No hay jerarquías ni interfaces, de modo que LSP e ISP no se aplican de forma explícita, aunque el diseño podría evolucionar  
  hacia interfaces de “generación de valor” por línea.

--- 

## Principios de ingeniería de software
### KISS (Keep It Simple, Stupid)
- El método execute es muy directo: parsear regalos, parsear regiones y sumar una condición booleana (convertida en 1/0),  
lo que hace que el flujo principal sea sencillo y claro.  

- `isValidRegion` expresa de forma muy simple la lógica de negocio: comparar dos áreas (area y areaNecessary),  
devolviendo 1 o 0 según la condición; no tiene efectos secundarios ni lógica oculta.  

### DRY (Don’t Repeat Yourself)
- El parseo de la parte de regalos se centraliza en formatInput, evitando repetir lógica de concatenación y split en otros métodos;  
createPresent se limita a filtrar y mapear a objetos Present.  

- La creación de Region está concentrada en setRegions, por lo que cualquier cambio en cómo se inicializan las regiones se hace en un único punto.

### Clean Code (nombres, claridad y posibles mejoras)
- Nombres como `PresentDistributor`, `createPresent`, `setRegions`, `execute`, `isValidRegion`, `formatInput` describen  
claramente su intención en el contexto de distribución de regalos en regiones.  

- La expresión de `formatInput` es algo densa (encadenamiento de stream(), reduce, stream() y getFirst()),  
lo que puede hacerla menos legible; extraer pasos intermedios con variables con nombre (por ejemplo, el string concatenado de regalos)  
mejoraría la comprensión inmediata.  

## Patrones de diseño
### Orquestador simple de dominio
`PresentDistributor` funciona como un orquestador del dominio: no implementa cálculos de área ni lógica interna de  
regalos/regiones, sino que coordina la creación de objetos y el cálculo del resultado final, alineándose con la idea de  
servicio de aplicación o “use case” en diseño orientado a dominio.  