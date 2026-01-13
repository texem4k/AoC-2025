# Dia 2 | Advent of Code
## Id Inválido 
  
 
En este ejercicio se nos pide detectar IDs inválidos, dados unos **rangos**. Un ID inválido será aquel que  
si se parte en 2, ambas partes tengan los mismos valores: Un ejemplo podría ser 1111.    
  
Si partemos **1111** en dos, tendríamos **11** y **11**. En este caso es inválido, ya que ambas partes son  
iguales. Por ello, los valores de longitud impar (**111** por ejemplo) o aquellos que empiecen por **0**  
no serán inválidos. 

Por otro lado, para la 2º Parte cambia un poco la definición de un ID inválido. Ahora se define cómo ID inválido  
todo aquel ID que esté compuesto por uan secuencia de dígitos, la menos 2 veces.  

**La parte 1 pide:**

* Iterar por todos los rangos de IDs y sumar todos los rangos inválidos

**La parte 2 pide:**

* Lo mismo básicamente  


---

## ¿Cómo será el input según la página de AoC?

En principio será todo una cadena de texto cuyo formato es _**n0-n1,n2-n3,n4-n5...**_  

---

## Parte 1: Validación por mitades iguales


Tras inicializar la clase y pasar el input a `executeA/B(input)`:

- Se llama a `formatInput()` para dividir el Stream por "," y se mapea el stream para que contenga rangos. 
- Seguidamente, se crean rangos para cada par de secuencias numéricas y se genera su iterable (LongStream) de IDs de ese rango.    
- Para cada ID del `LongStream`, se verifica que ambas "mitades" de la secuencia sean iguales, si es así, es inválido, añadiendose a la lsita de inválidos    
- Como `executeA/B` retorna la instancia, se puede ejecutar `count()`, que devolverá la suma de todos los IDs inválidos.



---

## Parte 2: Validación por repetición del mismo patrón

Muy parecido al A, pero cambiando la detección de IDs inválidos.

Tras inicializar la clase y pasar el input a `executeA/B(input)`:

- Se llama a `formatInput()` para dividir el Stream por "," y se mapea el stream para que contenga rangos.
- Seguidamente, se crean rangos para cada par de secuencias numéricas y se genera su iterable (LongStream) de IDs de ese rango.
- Aquí es donde cambia, en esta parte en lugar de comparar ambas mitades, pasamos la secuencia a String y hacemos Regex
buscando que la repetición de un bloque de dígitos. Si hay match, se añade a la lista de IDs inválidos
- Como `executeA/B` retorna la instancia, se puede ejecutar `count()`, que devolverá la suma de todos los IDs inválidos.

---

## Estructura de Datos, Range

Son dos valores numéricos separados por un "-". Donde ambos representan un límite inferior/superior  
respectivamente. Un ejemplo de rango desde el 10 hasta el 100 sería ***10-100***. Está formado por:

- Variables `min` y `max` que definen el inicio y final del rango.
- Método `stream()` que devuelve un LongStream con todos los valores dentro del rango

Para este problema, resulta extremadamente útil esta estructura de datos. Pudiendo acceder al iterable 
que contiene todos los valores del rango fácilmente.

## Principios SOLID aplicados

### SRP (Single Responsibility Principle)

- `Range` tiene una responsabilidad muy clara: representar un rango numérico y proporcionar un `stream()` de sus valores.  
Es un value object sencillo y centrado.  
- `IdValidator` presenta entre otros, la responsabilidad principal de validar los rangos obtenidos.

### OCP (Open/Closed Principle)

- El flujo de procesamiento (`formatInput` → `executeA/executeB` → `validateIdXPart`) está bien encapsulado, y añadir un  
nuevo tipo de validación (p. ej. parte C) podría hacerse mediante un nuevo método `executeC` y una nueva función de validación,  
sin modificar el parseo de rangos.  
- Sin embargo, ambas validaciones están incrustadas en la misma clase, lo que puede llevar a crecerla rápidamente si se añaden más reglas.  
- Extraer las reglas de validación a componentes independientes (p. ej. estrategias) facilitaría extender el  
comportamiento sin tocar la clase principal.  

### LSP, ISP y DIP

- No hay jerarquías de clases ni interfaces en el código actual, por lo que LSP e ISP no se explotan explícitamente, aunque  
el diseño podría evolucionar hacia interfaces de validación.  
- La clase está fuertemente acoplada a una implementación concreta de validación; introducir una interfaz como `IdRule` o `IdValidationStrategy`.  

---

## Principios de ingeniería de software

### KISS (Keep It Simple, Stupid)

- La lógica de expansión de rangos y validación se expresa con streams y métodos pequeños como `createRange`,   
- `validateIdAPart` y `validateIdBPart`, lo que mantiene el código relativamente simple y fácil de leer.    
- El uso de una expresión regular en la parte B encapsula una regla compleja en una sola línea, pero convendría  
documentarla brevemente o extraerla a una constante con nombre descriptivo para que sea más entendible de un vistazo.    

### DRY (Don’t Repeat Yourself)

- El parseo de la entrada y la expansión de rangos se centralizan en `formatInput(String s)`, por lo que tanto `executeA`  
- como `executeB` reutilizan exactamente el mismo pipeline de entrada y solo difieren en la función de validación aplicada.  
- Esto reduce duplicación y sigue bien DRY. 
- El método `toLong` se reutiliza en `createRange`, evitando repetir lógica de conversión; de forma similar,  
- `toStr` encapsula la conversión a `String`, aunque es un wrapper muy delgado. 

### Clean Code (nombres, side effects y diseño de la API)

- Nombres como `Range`, `IdValidator`, `executeA`, `executeB` y `count` transmiten de forma razonable su propósito,  
aunque `invalid_ranges` podría ser más expresivo (por ejemplo, `invalidIds`).
- El diseño fluido `IdValidator.create().executeA(input).count()` es claro para el consumidor, pero el objeto mantiene  
estado interno mutable (lista de inválidos). Si se reutiliza la misma instancia para varias entradas, los resultados  
se mezclarán; documentar este comportamiento o hacerlo inmutable (devolver nuevos objetos) mejoraría la claridad.  

---

## Patrones de diseño

### Fluent Interface para el uso del validador (Factory Method y Fluent API)

- El método estático `create()` y la devolución de `this` en `executeA` y `executeB` implementan un estilo fluido, p. ej.:
  ```java
  long sum = IdValidator.create()
      .executeA(input)
      .count();