# Día 1 – Advent of Code 2025

## Conteo de ceros

El 1º Día pide contar el nº de 0s tras aplicar una rotación en una especie de dial cuyos valores oscilan entre 0-99 es decir, módulo 100.  
Partimos desde la posición 50, y los movimientos disponibles son hacía a la izquierda (`L`) o a la derecha (`R`) con un  
número determinado de pasos.

**La parte 1 pide:**

* Obtener el nº de veces que se obtiene 0, tras aplicar un movimiento.

**La parte 2 pide:**

* El nº de 0s totales. Suponiendo que también cuentan los "reinicios" de cuenta tras sumar o restar, cuando  
superas 100 o llegas a valores negativos.

---

## ¿Cómo será el input según la página de AoC?

Es una serie de movimientos, definidos por `R` o `L` y un número. R20 es girar el dial 20 posiciones a la derecha (+20)  
y L20 es lo contrario (-20).

---


## Parte 1: Cálculo de la posición final

En la solución, cada instrucción se representa como un `Order(step)`, donde `step` ya incluye el signo: negativo para   
a la izquierda (`L`) y positivo para movimientos a la derecha (`R`). El método `execute(String orders)` recibe todas  
las órdenes separadas por saltos de línea, las convierte en objetos `Order` mediante `add(String... orders)` y las guarda   
internamente en la lista `orders`.

Para obtener la posición tras un movimiento:

- Tras inicializar la clase introduciendo el input, llamando a `execute()`.
- Se llama al método `count()` para iterar en la lista de `orders`.
- Se realiza un map llamando al método `sumPartial` donde se suman un determinado número de `orders`.
- Ese resultado se le pasa al método `normalize()`, que ajusta el valor entre 0-99 por la aritmética modular.
- De esta manera el stream estará formado por los resultados normalizados.
- Simplemente, queda hacer un `filter` al stream buscando los valores que sean == 0 y llamar a `count()`.

---

## Parte 2: Conteo de pasos por el cero

Para la segunda parte, el foco no está solo en la posición final, sino en cuántas veces se pasa exactamente por la  
posición 0 mientras se recorren todas las instrucciones. Esto lo implementa el método `countAllClicksZeros()`. 

La lógica es la siguiente:  

- Se parte de `pos = 50` como posición inicial.
- Se inicializa un contador `zeros`, que suma 1 si la posición inicial ya es 0 (en este caso no lo es).
- Para cada `Order` de la lista:
    - Se obtiene `step` (que puede ser positivo o negativo).
    - Se calcula la dirección `dir` como 1 si `step > 0` o -1 en caso contrario.
    - Se avanza paso a paso `abs(step)` veces, actualizando la posición con `(pos + dir + 100) % 100` para mantener la circularidad.
    - Cada vez que `pos == 0`, se incrementa `zeros`.

Al terminar de recorrer todas las órdenes, `countAllClicksZeros()` devuelve cuántas veces se ha pisado la posición 0 durante  
todo el recorrido, incluidas las posibles repeticiones.  

La clase también define un método `count()` que, en lugar de simular paso a paso, calcula la posición tras cada prefijo de  
la secuencia de órdenes: para cada tamaño de 1 a `orders.size()`, limita el stream a ese prefijo, suma los pasos, normaliza  
y comprueba si el resultado es 0. El campo `num` suma algunas situaciones de normalización específicas, y `countZeros()`  
devuelve `count() + num`, ofreciendo otra métrica relacionada con el cero. 

---

## Principios SOLID aplicados

### SRP (Single Responsibility Principle)

- `Order` cumple claramente SRP al encapsular únicamente el valor del desplazamiento (`step`) y exponerlo mediante `getStep()`.  
Es un modelo sencillo, inmutable y fácil de testear.
- `Dial` concentra varias responsabilidades: parseo de la entrada (`parse`, `signOf`, `valueOf`), almacenamiento de órdenes (`orders`),  
cálculo de posición final (`position`), conteo de ceros (`count`, `countZeros`, `countAllClicksZeros`) y normalización de posiciones (`normalize`).

### OCP (Open/Closed Principle)

- El parseo de instrucciones está encapsulado en métodos como `parse(String order)`, `signOf` y `valueOf`, de forma que  
la lógica de creación de `Order` está localizada. Esto facilita introducir nuevas formas de parseo o diferentes formatos  
de entrada modificando una sola zona.

### LSP, ISP y DIP

- No se utilizan jerarquías de herencia ni interfaces en este diseño, por lo que LSP e ISP no se explotan de forma explícita.
- Tampoco se realiza inversión de dependencias; el `Dial` se construye directamente con su implementación concreta y su  
estado interno. En un contexto más grande (por ejemplo, si hubiera distintos tipos de dial o distintas estrategias de conteo),  
se podrían introducir interfaces e inyección de dependencias para cumplir estos principios de forma más marcada. 

---

## Principios de ingeniería de software

### KISS (Keep It Simple, Stupid)

- Métodos como `signOf`, `valueOf` y `parse` son cortos, directos y fáciles de entender, lo que refleja bien el principio KISS. 
- Sin embargo, la interacción entre `normalize`, la variable `num` y los métodos `count`/`countZeros` añade cierta  
- complejidad oculta, al mezclar normalización con efectos laterales de conteo. Separar el conteo de cruces por 0 de  
- la función de normalización haría la lógica más simple y transparente.

### DRY (Don’t Repeat Yourself)

- La lógica de sumar pasos y normalizar está centralizada en `sum` y `normalize`, evitando repetir la misma aritmética    
- en varios puntos de la clase.
- Existe, no obstante, una duplicidad conceptual en la forma de contar ceros: `countZeros()` (basado en prefijos y en `num`)   
- y `countAllClicksZeros()` (basado en la simulación paso a paso) se solapan parcialmente en propósito. Unificar el criterio   
- o dejar muy documentado cuándo usar cada método ayudaría a cumplir mejor DRY a nivel de concepto. 

### Clean Code (nombres, side effects y constantes)

- Los nombres de métodos como `position`, `countAllClicksZeros`, `sumPartial` y `execute` describen bien su intención,  
lo que encaja con las recomendaciones de Clean Code.

---

## Patrones de diseño

### Fluent Interface para construcción del Dial

- El método estático `Dial.create()` combinado con `add(String... orders)` permite construir el objeto de forma fluida, p. ej.:
  ```java
  Dial dial = Dial.create()
      .add("L10", "R5", "R3");
