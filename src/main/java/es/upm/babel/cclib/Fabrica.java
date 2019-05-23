package es.upm.babel.cclib;

/**
 * Clase <em>singleton</em> (sin instancias) para simular la
 * producción de productos.
 */
public class Fabrica {
    /**
     * Tiempo medio de producción.
     */
    static private int tiempo_medio_prod_ms = 1000;

    /**
     * Generador de números aleatorios (tiempo de producción).
     */
    static private java.util.Random random = new java.util.Random(1);

    /**
     * No es posible generar objetos de la clase Fabrica.
     */
    private Fabrica() {
    }

    /**
     * Establece el tiempo medio de producción de cada producto (en
     * milisegundos). Lo establece a 0 si el parámetro es negativo.
     */
    static public void establecerTiempoMedioProd(int tmp_ms) {
       tiempo_medio_prod_ms = tmp_ms < 0 ? 0 :tmp_ms;
    }

    /**
     * Simula una producción de un producto.
     */
    static public Producto producir() {
        Producto prod;
        int t;
        ConcIO.printfnl("inicio producción...");
        t = random.nextInt(2 * tiempo_medio_prod_ms);
        try {
            Thread.sleep(t);
        } catch (Exception ex) {
            ConcIO.printfnl("excepción capturada: " + ex);
            ex.printStackTrace();
        } finally {
            prod = new Producto();
            ConcIO.printfnl("fin producción: " + prod + " en " + t + "ms.");
        }
        return prod;
    }

    /**
     * Simula la producción de un <em>paquete</em> de productos.
     */
    static public Producto[] producir(int n) {
        Producto[] prods = new Producto[n];
        ConcIO.printfnl("inicio producción de "
                        + prods.length + " productos...");
        for (int i = 0; i < prods.length; i++) {
           int t;
           t = random.nextInt(2 * tiempo_medio_prod_ms);
           try {
              Thread.sleep(t);
           } catch (Exception ex) {
              ConcIO.printfnl("excepción capturada: " + ex);
              ex.printStackTrace();
           } finally {
              prods[i] = new Producto();
              ConcIO.printfnl("producido producto: "
                              + prods[i] + " en " + t + "ms.");
           }
        }
        ConcIO.printfnl("fin producción del paquete de "
                        + prods.length + " productos");
        return prods;
    }
}
