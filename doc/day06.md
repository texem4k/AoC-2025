# Día 6 – Advent of Code 2025

## Matemáticas  Cefalópodas

En este día se procesa un “problema matemático” dispuesto en columnas: una fila inferior con operadores y varias filas superiores con números alineados por columnas.  
La solución tiene dos partes: en la parte A se combinan números de cada columna según su operador asociado, y en la parte B  
se reconstruyen números columna a columna (de abajo arriba) respetando huecos y separadores, aplicando después los operadores en orden desde la derecha.   

---

## Parte 1: Combinar columnas por operador


En este ejercicio, se le pasa el input a los métodos `calculateA/B(List<String>)`:

Primero crea una instancia local de `MathProblem`, ejecutando el método `A(List<String>)`, encargado de obtener los  
operadores y operandos.  

Para ello, en `MathProblem.A(...)` se llaman a los métodos `getOperations(...)`y `getValues()`. Donde:  
- `getOperations(input)`: lee la última línea, la trocea por espacios y rellena `operators`.  
- `getValuesA(input)`: para todas las líneas salvo la última, llama a `formatValues` que divide cada línea en tokens numéricos  
y los distribuye por índice de columna (`putValueInMap`, `addElementoValueList`), añadiendo los valores en formato  
lista a un mapa, según la fila que corresponda.  

De esta manera, `MathProblem` contendrá el problema en sí.  

En `Calculator.calculateA(List<String> input)` se inicializa el problema con `problem.A(input)` y la variable `long count` que alojará el resultado final.  
Luego se crea un bucle que recorre cada índice de operador desde el final hasta el principio.  

En cada iteración, se llama método `reduceGroup(...)` que verifica que operador es ("+" o "*") y realiza un el reduce  
correspondiente a la lista de valores del mapa de `MathProblem`.

La suma de todos estos resultados por columna se acumula en `count` y se devuelve como solución de la parte A.  

---

## Parte 2: Reconstruir números por columnas y aplicar operadores

En la parte 2 cambia un poco la cosa. Al igual que la parte 1, se crea una instancia de `MathProblem`, obteniendo  
en esta parte, sólo los operadores. Además, se crea una lista que contendrá los valores de la columna, ya que  
a diferencia de la 1º Parte, se calculan directamente.

Como antes, se recorre desde el final hasta el inicio, usando el nº de columnas del input. Entonces:

- Si nos pasamos de la primera columna (-1) o la columna está vacía usando `allBlank(...)`
  - Entonces usamos `reduceGroup(...)` para hacer reduce de los valores de la columna, obteniendo la suma o el producto correspondiente.
- Si nos pasamos o es una columna vacía, juntamos la lista de valores de la columna usando `parseColumn(...)`, se reconstruye  
mapeando la lista de valores a carácter y juntandolos.
  
Al final del método, devolverá el resultado correspondiente.


---

## Estructura de Datos, MathProblem
Ayuda a resolver el problema, generando los operadores(A y B) y operandos (Solo A). Reutilizando código. Mi idea era  
no dejar demasiado complejo la clase principal, por eso hice esta clase.  

La clase `MathProblem` contiene:

-  `List<String> operators` -> Lista de los operadores.
-  `HashMap<Integer, List<Long>> values` -> Representa fila: valores. Donde cada fila tiene una lista de valores asociados.


---


## Principios SOLID aplicados
### SRP (Single Responsibility Principle)

- `MathProblem` encapsula la representación y el parseo del problema: sabe cómo leer operadores y valores desde el input  
y cómo almacenarlos en estructuras internas (operators, values).  

- `Calculator` se centra en usar un `MathProblem` para resolver las partes A y B, aunque ahora mezcla el uso del modelo  
(problem.A/B) con lógica específica de parseo vertical (allBlank, parseColumn) y agrupación (reduceGroup), que podría desplazarse  
a MathProblem o a un componente específico de parte B para tener responsabilidades más claras.

### OCP (Open/Closed Principle)

- La separación entre MathProblem.A/B y Calculator.calculateA/B permite reutilizar el modelo si quisieras añadir nuevas formas de  
combinar los valores (por ejemplo, otra operación por columna), cambiando sólo la lógica de combinación.  

- Sin embargo, los operadores se asumen binarios y sólo se interpretan como suma o producto en calculateA y reduceGroup.  
Si se quisieran operadores nuevos (resta, división, etc.), habría que modificar estos métodos; extraer un mecanismo de “aplicar operador”  
a una función o estrategia separada facilitaría extender sin modificar. 

### LSP, ISP y DIP
No hay herencia ni interfaces, por lo que LSP e ISP no se aplican explícitamente; MathProblem y Calculator son clases concretas.

Para mejorar el DIP, se podría introducir una interfaz de “resolutor de problema” o de “estrategia de cálculo” que Calculator reciba,  
especialmente para la parte B, reduciendo el acoplamiento entre Calculator y el formato exacto de entrada.  

## Principios de ingeniería de software
### KISS (Keep It Simple, Stupid)

- La parte A es muy directa: se lee la matriz de números por columnas, se combinan con suma o producto según el operador, 
y se acumula el resultado, con bucles claros y streams sencillos.

- La parte B es más compleja debido al formato en columnas y huecos de espacios, pero el código se mantiene en  
métodos razonablemente pequeños (allBlank, parseColumn, reduceGroup), lo que ayuda a controlar la complejidad.   

### DRY (Don’t Repeat Yourself)

- El parseo de operadores (getOperations) está centralizado en MathProblem y se reutiliza tanto en A como en B.

### Clean Code (nombres, side effects y estructuras)
Los nombres `Calculator`, `MathProblem`, `getOperations`, `getValuesA`, `getValuesB`, `parseColumn`, `reduceGroup` 
describen bien su propósito, lo que favorece la legibilidad.

La creación de un `MathProblem` por cálculo ayuda a evitar fallos en ejecuciones entrelazadas sin parar (A,B,B,A...).

## Patrones de diseño
### Encapsulación del “problema matemático” como dominio
MathProblem actúa como un pequeño modelo de dominio: conoce la estructura de filas/columnas de la entrada y cómo mapearla a 
operadores y valores.  

Esto ya separa bastante el dominio (estructura del puzzle) de la lógica de cálculo (en Calculator), y podría evolucionar  
hacia métodos más expresivos del tipo long evaluateRowWise() o long evaluateColumnwiseRightToLeft().  

Como patrones específicos, sólo tenemos el **Factory Method**:
```java
public static Calculator create() {
    return new Calculator();
}

    private Calculator() {}