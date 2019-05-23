# es.upm.babel.cclib

cclib is a Java library used in concurrency related course at Universidad Politécnica de Madrid.

## Building the Library

Run `make` or `./gradlew tasks` and follow instructions.

## TODO

- Tryer.java to take advantage from generics (result type) and lambda.
- Check of mean times are really the mean of the indicated value: for
  instance, nextInt(2 * n) generates an integer from 0 to 2n-1, this
  means the mean is (2n-1)/2 that is not n.

## Classes in the cclib

### Semaphores and Monitors

Classes `es.upm.babel.cclib.Semaphore` and
`es.upm.babel.cclib.Monitor` are high integrity implementations of
classical concurrency mechanisms Semaphores and Monitors.

### Aunxiliary classes

- ConcIO: just a print function that informs about the thread that
  executes it
- Tryer: abstract class that monitors the execution of a method
  (toTry) that is launch in parallel

### Producer-Buffer-Consumer classes (names in Spanish!)

The rest of the classes are used in the class assignments. Most names are in Spanish:

- Almacen (warehouse): public interface for the implementation of
  warehouses (buffers)
- Consumidor (consumer): instances are threads that simulate
  consumption of products previously taken out from a warehouse
- Consumo (consume): just a function to simulate consumptions of a
  product
- Fabrica (factory): just a function to simulate manufacturing of
  products
- Multialmacen (multi-warehouse): public interface for the
  implementation of multi-warehouses (buffers with operations that
  allows to push and pull several products in one operation)
- Multiconsumidor (multi-consumer): instances are threads that
  simulate consumption of products products previously taken out from
  a warehouse (several products can be consumed in a shot).
- MultiProductor (multi-producer): instances are threads that simulate
  production and storage of products (several products can be produced
  in a shot).
- Productor (producer): instances are threads that simulate
  production and storage of products
