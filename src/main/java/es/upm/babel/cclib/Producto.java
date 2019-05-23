package es.upm.babel.cclib;

/**
 * Las instancias de Producto simulan un producto con un número de
 * serie y un contenido.
 */
public class Producto {
    /**
     * Lock para la exclusión mutua del generador de códigos
     * consecutivos de producto.
     */
    static private java.util.concurrent.locks.Lock lock =
        new java.util.concurrent.locks.ReentrantLock();

    /**
     * Código del siguiente producto.
     */
    static private int sig = 1;

    /**
     * Generador de números aleatorios (contenidos).
     */
    static private java.util.Random random = new java.util.Random(0);

    /**
     * Código interno del producto.
     */
    private final int cod;

    /**
     * Contenido interno del producto.
     */
    private final int cont;

    /**
     * Crea un producto con el siguiente número de serie.
     */
    public Producto() {
        lock.lock();
        cod = sig;
        sig++;
        lock.unlock();
        cont = random.nextInt();
    }

    /**
     * Devuelve la representación del producto.
     */
    public String toString() {
        return "Producto {cod = " + cod + ", cont = " + cont + "}";
    }
}
