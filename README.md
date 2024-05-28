# Java uso de Hilos



## Virtual HorseThread - Carrera de Caballos

Se debe desarrollar una aplicación ejecutable que simule una carrera de caballos de 1000 metros. Cada caballo avanzará de manera independiente y simultánea, informando en cada avance su nombre y la cantidad de metros total en ese instante (puede ser en la consola o en una página web). La carrera finaliza cuando 3 de los caballos llegan a la meta, para ello se deberá utilizar un "Semaphore" ya visto en el curso.
Se recibe como INPUT la cantidad de caballos que corren, y en base a esto se escoge en tiempo de ejecución si usar Threads tradicionales o Virtual Threads para que la performance sea óptima, justificando la elección en un comment.
Instrucción de Ayuda: "Runtime.getRuntime().availableProcessors()".
Los caballos se generan de manera aleatoria con los siguientes atributos, mostrando el listado antes de comenzar la carrera:
-Nombre
-Velocidad (entre 1 y 3)
-Resistencia (entre 1 y 3)
Todos los caballos arrancan al mismo tiempo y se mueven respetando la siguiente lógica:
- Etapa de Avance: Se multiplica la vel del caballo por un entero aleatorio entre 1 y 10 metros.
- Etapa de Espera: Se suspende el avance por una cantidad aleatoria de segs entre 1 y 5, restando la resistencia del caballo. Ej: Si le tocó 4 segs, con resistencia 3, espera 1 seg antes de avanzar.
  Potenciadores:
  Durante la carrera aparece un área potenciadora de 50 metros en cualquier lugar de la pista que se refresca cada 15 segs. Utilizar una Thread tradicional justificando si es daemon o no. Para que cada caballo conozca la posición del área deberán aplicar los conocimientos adquiridos de comunicación inter-threading.
  Si algún caballo pisa el área, espera 7 segundos y avanza 100 metros. Luego de ese tiempo el área vuelve a estar disponible para todos los caballos, antes debe estar "loqueado" por dichocaballo.

Pozos (opcional):
Antes de comenzar la carrera se generan 5 pozos de 10 metros en posiciones aleatorias. Si un caballo cae dentro del área del pozo no puede seguir moviéndose hasta que otro caballo lo "rescate". Para rescatar a otro caballo solo se debe pasarlo.

Finalizada la carrera se terminan TODAS las threads y se informan los resultados.

### Implementación
* Se implementa usando hilos:
  * Virtuales o Tradicionales, en función de la cantidad de procesador que se tenga
* Comunicación entre hilos:
  * Condicional
  * Productor consumidor

* A los fines prácticos de poder visualizar las distintas etapas de los caballos, se imprimen por consola:
  * Los metros avanzados
  * El tiempo de espera
    * Por etapa de avance
    * Por estar en una zona potenciada
  * Ingreso en zona potenciada
  * Espera a que se libere una zona potenciada
